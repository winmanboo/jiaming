package com.deepcode.jiaming.uaa.constants;

import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * OAuth2 授权类型
 *
 * @author winmanboo
 * @date 2023/7/7 14:38
 */
@UtilityClass
public class OAuth2GrantTypes {
    /**
     * 图形验证码模式（密码模式+图形验证码）
     */
    public static final AuthorizationGrantType CAPTCHA = new AuthorizationGrantType("captcha");

    /**
     * 手机验证码
     */
    public static final AuthorizationGrantType SMS_CODE = new AuthorizationGrantType("sms_code");
}
