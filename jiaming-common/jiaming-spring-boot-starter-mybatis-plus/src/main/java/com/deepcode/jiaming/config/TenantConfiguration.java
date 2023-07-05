package com.deepcode.jiaming.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.deepcode.jiaming.context.TenantContextHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 多租户配置
 */
@Configuration
public class TenantConfiguration {


    private final static String TENANT_ID = "tenant_id";

    private final List<String> ignoreTables = Arrays.asList(
            "sys_tenant", "sys_tenant_package", "oauth2_authorization",
            "oauth2_authorization_consent", "oauth2_registered_client"
    );

    @Bean
    public TenantLineInnerInterceptor tenantLineHandler() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                String tenantId = TenantContextHolder.getTenantId();
                if (tenantId != null) {
                    return new StringValue(tenantId);
                }
                return new NullValue();
            }

            @Override
            public boolean ignoreTable(String tableName) {
                return ignoreTables.stream().anyMatch(item -> item.equalsIgnoreCase(tableName));
            }

        });
    }
}
