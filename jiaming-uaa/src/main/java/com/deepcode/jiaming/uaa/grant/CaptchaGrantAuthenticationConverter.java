package com.deepcode.jiaming.uaa.grant;

import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.uaa.constants.Keys;
import com.deepcode.jiaming.uaa.constants.OAuth2GrantTypes;
import com.deepcode.jiaming.uaa.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 验证码登录转换器，参考：https://juejin.cn/post/7246409673565372475
 *
 * @author winmanboo
 * @date 2023/7/6 18:21
 */
public class CaptchaGrantAuthenticationConverter implements AuthenticationConverter {
    private static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final RedisTemplate<String, Object> redisTemplate;

    public CaptchaGrantAuthenticationConverter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!OAuth2GrantTypes.CAPTCHA.getValue().equals(grantType)) {
            return null;
        }
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        MultiValueMap<String, String> parameters = SecurityUtil.getParameters(request);

        String key = request.getHeader("key");
        if (CharSequenceUtil.isEmpty(key)) {
            SecurityUtil.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    "parameter key can not be null",
                    null);
        }

        // 删除验证码缓存
        redisTemplate.delete(Keys.CAPTCHA_KEY + key);

        String code = request.getHeader("code");
        if (CharSequenceUtil.isEmpty(code)) {
            SecurityUtil.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    "parameter code can not be null",
                    null);
        }

        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if (CharSequenceUtil.isEmpty(username)) {
            SecurityUtil.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    "OAuth 2.0 Parameter: " + OAuth2ParameterNames.USERNAME,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (CharSequenceUtil.isEmpty(password)) {
            SecurityUtil.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    "OAuth 2.0 Parameter: " + OAuth2ParameterNames.PASSWORD,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (CharSequenceUtil.isNotEmpty(scope) &&
                parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            SecurityUtil.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    "OAuth 2.0 Parameter: " + OAuth2ParameterNames.SCOPE,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        Set<String> requestedScopes = null;
        if (CharSequenceUtil.isNotEmpty(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        // 提取附加参数
        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((k, v) -> {
            if (!k.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                    !k.equals(OAuth2ParameterNames.CLIENT_ID)) {
                additionalParameters.put(k, v.get(0));
            }
        });

        return new CaptchaGrantAuthenticationToken(clientPrincipal, requestedScopes, additionalParameters);
    }
}
