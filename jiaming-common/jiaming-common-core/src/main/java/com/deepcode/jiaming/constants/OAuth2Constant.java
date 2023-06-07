package com.deepcode.jiaming.constants;

import lombok.experimental.UtilityClass;

/**
 * @author winmanboo
 * @date 2023/6/6 16:23
 */
@UtilityClass
public class OAuth2Constant {
    /**
     * jmtk 存储的自定义 token 对应的 accessToken
     * <br/>
     * token -> {"accessToken", "refreshToken", "scope", "expiresIn", "type"}
     */
    public static final String JMTK_KEY_FORMAT = "jiaming:jmtk:%s"; // %s -> custom token

    /**
     * OAuth2 认证请求头
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";
}
