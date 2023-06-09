package com.deepcode.jiaming.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/**
 * @author winmanboo
 * @date 2023/5/20 22:13
 */
@Data
@Validated
@ConfigurationProperties(prefix = "jiaming.security")
public class SecurityProperties {
    /**
     * token 请求头
     */
    private String tokenHeader = "Authorization";

    /**
     * 不需要授权的白名单列表
     */
    private List<String> whiteList = Collections.emptyList();
}
