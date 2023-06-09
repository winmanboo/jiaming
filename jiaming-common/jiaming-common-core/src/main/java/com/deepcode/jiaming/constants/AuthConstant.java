package com.deepcode.jiaming.constants;

import lombok.experimental.UtilityClass;

/**
 * @author winmanboo
 * @date 2023/5/25 21:33
 */
@UtilityClass
public class AuthConstant {
    /**
     * 前端携带的 token 请求头
     */
    public static final String AUTH_TOKEN_HEADER = "jm-tk";

    /**
     * 用于 jwt 中存权限信息的字段
     */
    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 权限前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";

    /**
     * 用于 jwt 中存租户id的字段
     */
    public static final String TENANT_CLAIM_NAME = "tenant";

    /**
     * 用于 jwt 中存管理员字段
     */
    public static final String IS_ADMIN_CLAIM_NAME = "admin";

    /**
     * 用于 jwt 中存用户id的字段
     */
    public static final String USER_ID_CLAIM_NAME = "uid";

    /**
     * 用于 jwt 中存用户名的字段
     */
    public static final String USER_NAME_CLAIM_NAME = "username";
}
