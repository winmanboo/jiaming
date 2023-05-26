package com.deepcode.jiaming.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author winmanboo
 * @date 2023/5/26 17:00
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "gateway.white-list")
public class WhiteListProperties {
    /**
     * 白名单列表
     */
    private List<String> ignoreUrls = Collections.emptyList();
}
