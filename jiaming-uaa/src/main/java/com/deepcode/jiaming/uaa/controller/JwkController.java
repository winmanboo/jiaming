package com.deepcode.jiaming.uaa.controller;

import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author winmanboo
 * @date 2023/5/26 16:34
 */
@RestController
@RequestMapping("/uaa/jwk")
@RequiredArgsConstructor
public class JwkController {
    private final JWKSource<SecurityContext> jwkSet;

    /**
     * jwk 公钥
     *
     * @return jwk 公钥
     */
    @GetMapping("/publicKey")
    public Map<String, Object> jwkPublicKey() {
        return ((ImmutableJWKSet<SecurityContext>) jwkSet).getJWKSet().toPublicJWKSet().toJSONObject();
    }
}
