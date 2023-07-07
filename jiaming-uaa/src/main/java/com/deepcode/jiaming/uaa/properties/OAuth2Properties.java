package com.deepcode.jiaming.uaa.properties;

import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author winmanboo
 * @date 2023/5/26 16:25
 */
@Data
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {
    /**
     * json web key（jwt 的加密 key）
     */
    private Jwk jwk;

    /**
     * 配置网关的地址
     */
    private String gatewayUri;

    /**
     * oauth2 令牌配置
     */
    private Token token;

    @Data
    public static class Token {
        /**
         * 默认访问令牌超时时间 1 小时，以秒为单位
         */
        private long accessTokenTimeout = 3600;

        /**
         * 默认刷新令牌超时时间 12 小时，以秒为单位
         */
        private long refreshTokenTimeout = 43200;
    }

    @Data
    public static class Jwk {
        private String privateKey;

        private String publicKey;

        public RSAPrivateKey getPrivateKey() {
            return (RSAPrivateKey) new RSA(privateKey, null).getPrivateKey();
        }

        public RSAPublicKey getPublicKey() {
            return (RSAPublicKey) new RSA(null, publicKey).getPublicKey();
        }
    }
}
