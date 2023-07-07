package com.deepcode.jiaming.uaa.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.constants.Keys;
import com.deepcode.jiaming.uaa.constants.OAuth2Constant;
import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

/**
 * @author winmanboo
 * @date 2023/7/6 19:46
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/uaa/auth")
public class AuthController {

    private static final String OAUTH2_TOKEN_ENDPOINT = "/jiaming/uaa/oauth2/token";

    private final RedisTemplate<String, Object> redisTemplate;

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuth2Properties oAuth2Properties;

    @ResponseBody
    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public Result<Map<String, Object>> captcha() {
        // TODO: 2023/7/7 验证码可配置化
        GifCaptcha captcha = CaptchaUtil.createGifCaptcha(200, 100, 4);
        String code = captcha.getCode();
        String key = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(Keys.CAPTCHA_KEY + key, code, Duration.ofMinutes(5));
        return Result.ok(Map.of("key", key, "captchaUrl", captcha.getImageBase64Data()));
    }

    @GetMapping("/login")
    public String loginPage() {
        log.info("to login page");
        return "login";
    }

    /**
     * 接收授权码，并向授权服务器（也就是 uaa 本身）请求令牌
     *
     * @param code 授权码
     * @return String 返回的就是包装过的令牌 json 字符串
     */
    @ResponseBody
    @GetMapping("/code")
    @ApiOperation(value = "接收授权码", notes = "接收授权码，并向授权服务器（也就是 uaa 本身）请求令牌")
    public String code(@RequestParam("code") String code) {
        if (CharSequenceUtil.isEmpty(code)) {
            throw new JiamingException("授权码为空");
        }

        RegisteredClient client = registeredClientRepository.findByClientId(OAuth2Constant.DEFAULT_CLIENT_ID);

        if (Objects.isNull(client)) {
            throw new JiamingException("客户端未注册到系统中");
        }

        String requestUrl = oAuth2Properties.getGatewayUri() + OAUTH2_TOKEN_ENDPOINT;
        RestTemplate restTemplate = new RestTemplate();
        String basicHeader = "Basic " + Base64.encode(OAuth2Constant.DEFAULT_CLIENT_ID + ":" + OAuth2Constant.DEFAULT_CLIENT_SECRET);
        RequestEntity<String> body = RequestEntity.post(requestUrl)
                // 如果客户端注册时配置了 auth basic 认证方式，那必须在请求头里面添加这个
                // 如果客户端注册了其他认证方式，得逐一配置，参考 ClientAuthenticationMethod
                .header("authorization", basicHeader)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(MessageFormat.format("grant_type={0}&code={1}&redirect_uri={2}&scope={3}",
                        "authorization_code",
                        code,
                        client.getRedirectUris().toArray(new String[0])[0],
                        client.getScopes().toArray(new String[0])[0]));

        ResponseEntity<String> responseEntity = restTemplate.exchange(body, String.class);
        return responseEntity.getBody();
    }
}
