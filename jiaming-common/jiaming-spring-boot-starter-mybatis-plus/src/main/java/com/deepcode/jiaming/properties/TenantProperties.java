package com.deepcode.jiaming.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author winmanboo
 * @date 2023/7/15 10:10
 */
@Data
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {
    private List<String> ignoreTables;
}
