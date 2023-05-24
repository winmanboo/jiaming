package com.deepcode.jiaming.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author winmanboo
 * @date 2023/5/24 16:40
 */
@FeignClient("jiaming-uaa")
public interface UaaOpenFeignClient {
    /**
     * 获取 RSA Key
     *
     * @return JWTSet.toJSONObject()
     * @see com.nimbusds.jose.jwk.JWKSet
     * @see java.security.interfaces.RSAPublicKey
     * @see java.security.interfaces.RSAKey
     */
    @GetMapping("/uaa/rsa/publicKey")
    String getPublicKey();
}
