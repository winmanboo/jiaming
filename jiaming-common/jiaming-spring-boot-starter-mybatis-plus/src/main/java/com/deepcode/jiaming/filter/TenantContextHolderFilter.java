package com.deepcode.jiaming.filter;

import cn.hutool.core.util.StrUtil;
import com.deepcode.jiaming.context.TenantContextHolder;
import com.deepcode.jiaming.security.context.UserInfoContext;
import com.deepcode.jiaming.security.domain.UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            String tenantId = request.getHeader("tenant_id");

            // 如果请求头中没有 从用户信息中获取
            if (StrUtil.isBlank(tenantId)) {
                UserInfo userInfo = UserInfoContext.get();
                if (userInfo != null) {
                    tenantId = userInfo.getTenantId().toString();
                }
            }
            log.info("获取到的租户ID为:{}", tenantId);

            if (StrUtil.isBlank(tenantId)) {
                tenantId = "0";
            }
            TenantContextHolder.setTenantId(tenantId);

            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }

    }
}
