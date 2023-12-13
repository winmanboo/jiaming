package com.deepcode.jiaming.uaa.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.util.IdUtil;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.constants.Keys;
import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.Map;

/**
 * @author winmanboo
 * @date 2023/7/6 19:46
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/uaa/auth")
public class AuthController {

    private static final String OAUTH2_TOKEN_ENDPOINT = "/jiaming/uaa/oauth2/token";

    private final RedisTemplate<String, Object> redisTemplate;

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuth2Properties oAuth2Properties;

    @ResponseBody
    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public Result<Map<String, Object>> captcha() {
        // TODO: 2023/7/7 验证码可配置化
        GifCaptcha captcha = CaptchaUtil.createGifCaptcha(200, 30, 4);
        String code = captcha.getCode();
        String key = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(Keys.CAPTCHA_KEY + key, code, Duration.ofMinutes(5));
        return Result.ok(Map.of("key", key, "captchaUrl", captcha.getImageBase64Data()));
    }

    @GetMapping("/login")
    public String loginPage() {
        log.info("to login page");
        return "login";
    }
}
