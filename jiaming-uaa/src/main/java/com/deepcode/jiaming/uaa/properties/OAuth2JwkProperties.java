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
@ConfigurationProperties(prefix = "oauth2.jwk")
public class OAuth2JwkProperties {
    private Rsa rsa;

    @Data
    public static class Rsa {
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
