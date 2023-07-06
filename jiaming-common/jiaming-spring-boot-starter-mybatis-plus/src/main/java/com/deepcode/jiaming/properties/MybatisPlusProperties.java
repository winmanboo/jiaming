package com.deepcode.jiaming.properties;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author winmanboo
 * @date 2023/7/6 12:30
 */
@Data
@ConfigurationProperties(prefix = Constants.MYBATIS_PLUS)
public class MybatisPlusProperties {
    /**
     * 多租户需要忽略的表
     */
    private List<String> tenantIgnoreTables = List.of(
            "sys_menu", "sys_tenant", "sys_tenant_package", "oauth2_authorization",
            "oauth2_authorization_consent", "oauth2_registered_client"
    );
}
