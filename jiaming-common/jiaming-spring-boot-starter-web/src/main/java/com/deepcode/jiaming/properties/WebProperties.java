package com.deepcode.jiaming.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author winmanboo
 * @date 2023/12/13 16:55
 */
@Data
@ConfigurationProperties("jiaming.web")
public class WebProperties {
}
