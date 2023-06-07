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
     * 默认的 oauth2 授权码模式重定向的前端地址
     */
    private String redirectUri;

    /**
     * redis 存储 oauth2 信息的相关配置
     *
     * @deprecated 使用 redis 存储 OAuth2 相关信息的能力可能在未来会删除
     */
    @Deprecated(since = "6/7", forRemoval = true)
    private Redis redis;

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

    /**
     * @deprecated 使用 redis 存储 OAuth2 相关信息的能力可能在未来会删除
     */
    @Data
    @Deprecated(since = "6/7", forRemoval = true)
    public static class Redis {
        /**
         * Redis 存储授权信息的超时时间，单位为秒
         */
        private long authorizationTimeout = 300;
    }
}
