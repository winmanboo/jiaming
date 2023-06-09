package com.deepcode.jiaming.uaa.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.constants.OAuth2Constant;
import com.deepcode.jiaming.entity.OAuth2Token;
import com.deepcode.jiaming.uaa.entity.OAuth2AccessToken;
import com.deepcode.jiaming.entity.OAuth2Error;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.constants.Oauth2Constant;
import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import com.deepcode.jiaming.uaa.repository.JmtkJdbcOAuth2AuthorizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author winmanboo
 * @date 2023/6/4 19:50
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/uaa/oauth2")
public class OAuth2Controller {
    private final JdbcRegisteredClientRepository registeredClientRepository;

    private final LoadBalancerClient loadBalancerClient;

    @Value("${spring.application.name}")
    private String serviceName;

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final OAuth2Properties oAuth2Properties;

    private final JmtkJdbcOAuth2AuthorizationService oAuth2AuthorizationService;

    /**
     * 根据授权码请求获取访问令牌
     *
     * @param oAuth2TokenDto oauth2 请求传输对象
     * @return 访问令牌或错误信息
     * @throws JsonProcessingException 解析响应异常抛出
     */
    @PostMapping("/access_token")
    @ResponseBody
    public Result<Map<String, Object>> requestAccessToken(@RequestBody Map<String, Object> oAuth2TokenDto) throws JsonProcessingException {
        String code = (String) oAuth2TokenDto.get("code");

        if (CharSequenceUtil.isEmpty(code)) {
            throw new JiamingException("授权码不能为空");
        }

        RegisteredClient client = registeredClientRepository.findByClientId(Oauth2Constant.DEFAULT_CLIENT_ID);

        if (client == null) {
            throw new JiamingException("无法获取客户端信息");
        }

        ServiceInstance instance = loadBalancerClient.choose(serviceName);

        // 认证方式，暂不做兼容，全部以 basic 来
        Set<ClientAuthenticationMethod> methods = client.getClientAuthenticationMethods();

        String requestUrl = instance.getUri() + "/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> body = RequestEntity.post(requestUrl)
                // 如果客户端注册时配置了 auth basic 认证方式，那必须在请求头里面添加这个
                // 如果客户端注册了其他认证方式，得逐一配置，参考 ClientAuthenticationMethod
                .header("authorization", "Basic " +
                        Base64.encode(Oauth2Constant.DEFAULT_CLIENT_ID + ":" + Oauth2Constant.DEFAULT_CLIENT_SECRET))
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(MessageFormat.format("grant_type={0}&code={1}&redirect_uri={2}&scope={3}",
                        "authorization_code",
                        code,
                        client.getRedirectUris().toArray(new String[0])[0],
                        client.getScopes().toArray(new String[0])[0]));

        ResponseEntity<String> responseEntity = restTemplate.exchange(body, String.class);
        String result = responseEntity.getBody();
        try {
            OAuth2AccessToken oAuth2AccessToken = objectMapper.readValue(result, OAuth2AccessToken.class);

            // 生成自定义 token 返回给用户
            String jmtk = generateToken();

            // store custom token -> accessToken info
            redisTemplate.opsForValue().set(String.format(OAuth2Constant.JMTK_KEY_FORMAT, jmtk),
                    new OAuth2Token(oAuth2AccessToken.getAccess_token(), oAuth2AccessToken.getToken_type()),
                    oAuth2Properties.getToken().getAccessTokenTimeout(),
                    TimeUnit.SECONDS);

            // 存储 accessToken 对应 jmtk，为了删除 token 时能反查 jmtk，进而删除 jmtk
            oAuth2AuthorizationService.saveJmtk(oAuth2AccessToken.getAccess_token(), jmtk);

            return Result.ok(Map.of(
                    "accessToken", jmtk,
                    "refreshToken", oAuth2AccessToken.getRefresh_token(),
                    "scope", oAuth2AccessToken.getScope(),
                    "tokenType", oAuth2AccessToken.getToken_type(),
                    "expiresIn", oAuth2AccessToken.getExpires_in()
            ));
        } catch (Exception e) {
            OAuth2Error oAuth2Error = objectMapper.readValue(result, OAuth2Error.class);

            return Result.ok(Map.of(
                    "errorDescription", oAuth2Error.getError_description(),
                    "error", oAuth2Error.getError(),
                    "errorUri", oAuth2Error.getError_uri()
            ));
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        log.info("to login page");
        return "login";
    }

    /**
     * 生成令牌
     *
     * @return 令牌
     */
    private String generateToken() {
        // FIXME: 2023/6/8 暂定 UUID 作为自定义 token
        return UUID.fastUUID().toString();
    }

}
