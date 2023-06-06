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
