package com.deepcode.jiaming.uaa.controller;

import com.deepcode.jiaming.uaa.properties.OAuth2JwkProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

/**
 * @author winmanboo
 * @date 2023/5/26 16:34
 */
@RestController
@RequestMapping("/uaa")
@RequiredArgsConstructor
public class JwkController {
    private final OAuth2JwkProperties properties;

    /**
     * jwk 公钥
     *
     * @return jwk 公钥
     */
    @GetMapping("/jwk/publicKey")
    public PublicKey jwkPublicKey() {
        return properties.getRsa().getPublicKey();
    }
}
