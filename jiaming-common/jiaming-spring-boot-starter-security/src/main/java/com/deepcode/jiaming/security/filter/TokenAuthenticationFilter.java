package com.deepcode.jiaming.security.filter;

import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.security.domain.SecurityUserInfo;
import com.deepcode.jiaming.security.properties.SecurityProperties;
import com.deepcode.jiaming.security.service.SecurityService;
import com.deepcode.jiaming.security.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author winmanboo
 * @date 2023/5/20 22:28
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityProperties securityProperties;

    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getToken(request);
        SecurityUserInfo userInfo = null;
        if (CharSequenceUtil.isNotEmpty(token)) {
            // 根据 token 拿到用户信息
            try {
                userInfo = securityService.getUserInfoByToken(token);
                if (null == userInfo) {

                }
            } catch (Exception e) {
                ResponseUtil.out(response, new ObjectMapper(), Result.fail("用户信息获取异常"));
                return;
            }
        }

        if (null != userInfo) {

        }
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(securityProperties.getTokenHeader());
    }
}
