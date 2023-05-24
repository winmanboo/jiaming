package com.deepcode.jiaming.uaa.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.exception.JiamingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.PublicKey;

/**
 * @author winmanboo
 * @date 2023/5/24 16:55
 */
@RestController
@RequestMapping("/uaa")
@RequiredArgsConstructor
public class KeyPairController {
    private final KeyPair keyPair;

    /**
     * 获取 rsa 的 PublicKey
     */
    @GetMapping("/rsa/publicKey")
    public String getPublicKey() {
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = publicKey.toString();
        if (CharSequenceUtil.isEmpty(publicKeyStr)) {
            throw new JiamingException("public key return failed");
        }
        return publicKeyStr;
    }
}
