package com.deepcode.jiaming.constants;

import lombok.experimental.UtilityClass;

/**
 * @author winmanboo
 * @date 2023/6/6 22:03
 */
@UtilityClass
public class Constants {
    /**
     * oauth2 获取 jwk 信息的端点路径（对应 UAA 服务的 JwkController
     */
    public static final String OAUTH2_JWK_ENDPOINT = "/jiaming/uaa/jwk/publicKey";
}
