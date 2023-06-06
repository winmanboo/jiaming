package com.deepcode.jiaming.admin.feign;

import com.deepcode.jiaming.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author winmanboo
 * @date 2023/6/4 19:41
 */
@FeignClient("jiaming-uaa")
public interface OAuth2Api {
    @PostMapping("/uaa/oauth2/token")
    Result<Map<String, Object>> requestAccessToken(@RequestBody Map<String, Object> oAuth2TokenDto);
}
