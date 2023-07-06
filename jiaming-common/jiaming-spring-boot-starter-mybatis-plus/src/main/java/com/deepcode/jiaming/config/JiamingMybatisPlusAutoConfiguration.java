package com.deepcode.jiaming.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.deepcode.jiaming.context.UserInfoContext;
import com.deepcode.jiaming.exception.TenantException;
import com.deepcode.jiaming.properties.MybatisPlusProperties;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@RequiredArgsConstructor
@MapperScan(basePackages = "com.deepcode.jiaming.**.mapper")
@EnableConfigurationProperties(value = MybatisPlusProperties.class)
public class JiamingMybatisPlusAutoConfiguration {

    private final TenantLineInnerInterceptor tenantLineInnerInterceptor;

    private final MybatisPlusProperties mybatisPlusProperties;

    /**
     * mybatis拦截器
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加租户插件
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 多租户插件
     *
     * @return {@link TenantLineInnerInterceptor}
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineHandler() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                Long tenantId = UserInfoContext.get().getTenantId();
                if (tenantId != null) {
                    return new LongValue(tenantId);
                }
                throw new TenantException("租户 id 不能为空");
            }

            @Override
            public boolean ignoreTable(String tableName) {
                return mybatisPlusProperties.getTenantIgnoreTables().stream()
                        .anyMatch(item -> item.equalsIgnoreCase(tableName));
            }

        });
    }
}
