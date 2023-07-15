package com.deepcode.jiaming.admin.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.deepcode.jiaming.security.context.UserInfoContext;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author winmanboo
 * @date 2023/7/15 10:01
 */
@Configuration
public class TenantConfig {
    @Bean
    public TenantLineHandler tenantLineHandler() {
        return () -> new LongValue(UserInfoContext.get().getTenantId());
    }
}
