package com.deepcode.jiaming.security.filter;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.deepcode.jiaming.constants.AuthConstant;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.UserResultStatus;
import com.deepcode.jiaming.security.context.UserInfoContext;
import com.deepcode.jiaming.security.domain.UserInfo;
import com.deepcode.jiaming.security.properties.SecurityProperties;
import com.deepcode.jiaming.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;

/**
 * 解析 token 并存储用户信息到当前线程的过滤器
 *
 * @author winmanboo
 * @date 2023/5/20 22:28
 */
@Log
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) {
        String token = getToken(request);
        if (CharSequenceUtil.isNotEmpty(token)) {
            // 根据 token 拿到用户信息
            try {
                UserInfo userInfo = parseUserInfo(token);
                UserInfoContext.set(userInfo);
                filterChain.doFilter(request, response);
                UserInfoContext.clear();
            } catch (Exception e) {
                ResponseUtil.out(response, Result.fail("用户信息获取异常"));
                return;
            }
        }

        ResponseUtil.out(response, UserResultStatus.A0306);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(securityProperties.getTokenHeader());
        String[] tokenSplit = token.split(" ");
        if (tokenSplit.length != 2) {
            log.info("token format error : " + token);
            return null;
        }
        String tokenType = tokenSplit[0];
        return CharSequenceUtil.removePrefixIgnoreCase(token, tokenType);
    }

    /**
     * 解析 jwt 并封装成 UserInfo 返回
     *
     * @param token jwt
     * @return 用户信息
     */
    @SuppressWarnings("unchecked")
    private UserInfo parseUserInfo(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        JWTPayload payload = jwt.getPayload();
        long tenantId = ((NumberWithFormat) payload.getClaim(AuthConstant.TENANT_CLAIM_NAME)).longValue();
        JSONArray authorities = (JSONArray) payload.getClaim(AuthConstant.AUTHORITY_CLAIM_NAME);
        Boolean isAdmin = (Boolean) payload.getClaim(AuthConstant.IS_ADMIN_CLAIM_NAME);
        long userId = ((NumberWithFormat) payload.getClaim(AuthConstant.USER_ID_CLAIM_NAME)).longValue();
        String username = (String) payload.getClaim(AuthConstant.USER_NAME_CLAIM_NAME);
        return UserInfo.builder()
                .tenantId(tenantId)
//                .authorities(authorities)
                .isAdmin(isAdmin)
                .userId(userId)
                .username(username)
                .build();
    }
}
