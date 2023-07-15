package com.deepcode.jiaming.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.deepcode.jiaming.properties.TenantProperties;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@RequiredArgsConstructor
@MapperScan(basePackages = "com.deepcode.jiaming.**.mapper")
@EnableConfigurationProperties(value = TenantProperties.class)
public class JiamingMybatisPlusAutoConfiguration {
    private static final TenantLineHandler NONE_TENANT = () -> null;

    private final TenantProperties tenantProperties;

    @Bean
    @ConditionalOnMissingBean
    public TenantLineHandler tenantLineHandler() {
        return NONE_TENANT;
    }

    /**
     * mybatis拦截器
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(TenantLineHandler tenantLineHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        if (tenantLineHandler != NONE_TENANT) {
            TenantLineHandler proxy = new TenantLineHandler() {
                @Override
                public Expression getTenantId() {
                    return tenantLineHandler.getTenantId();
                }

                @Override
                public boolean ignoreTable(String tableName) {
                    return tenantProperties.getIgnoreTables().contains(tableName);
                }
            };
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(proxy));
        }
        return interceptor;
    }
}
