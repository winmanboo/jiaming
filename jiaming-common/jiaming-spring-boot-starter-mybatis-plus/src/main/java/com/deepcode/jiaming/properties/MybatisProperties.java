package com.deepcode.jiaming.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author winmanboo
 * @date 2023/12/13 19:32
 */
@Data
@ConfigurationProperties(value = "jiaming.mybatis")
public class MybatisProperties {
    private String basePackage;
}
