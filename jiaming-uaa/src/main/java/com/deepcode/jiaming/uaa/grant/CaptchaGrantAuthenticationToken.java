package com.deepcode.jiaming.uaa.grant;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 自定义验证码登录Token类
 *
 * @author winmanboo
 * @date 2023/7/6 20:35
 */
public class CaptchaGrantAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;

    @Getter
    private final Map<String, Object> additionalParameters;

    @Getter
    private final Set<String> scopes;

    /**
     * 创建验证码令牌
     *
     * @param principal            主要
     * @param scopes               作用域
     * @param additionalParameters 请求的参数
     */
    public CaptchaGrantAuthenticationToken(Object principal,
                                           Set<String> scopes,
                                           Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        this.principal = principal;
        this.additionalParameters = additionalParameters;
        this.scopes = scopes;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
