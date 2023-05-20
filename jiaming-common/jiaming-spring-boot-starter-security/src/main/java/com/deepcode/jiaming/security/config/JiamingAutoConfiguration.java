package com.deepcode.jiaming.security.config;

import com.deepcode.jiaming.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author winmanboo
 * @date 2023/5/20 22:12
 */
@AutoConfiguration
@EnableConfigurationProperties(value = SecurityProperties.class)
public class JiamingAutoConfiguration {
}
