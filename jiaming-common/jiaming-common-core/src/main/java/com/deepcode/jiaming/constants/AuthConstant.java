package com.deepcode.jiaming.constants;

import lombok.experimental.UtilityClass;

/**
 * @author winmanboo
 * @date 2023/5/25 21:33
 */
@UtilityClass
public class AuthConstant {
    /**
     * 用于 jwt 中存权限信息的字段
     */
    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 权限前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";
}
