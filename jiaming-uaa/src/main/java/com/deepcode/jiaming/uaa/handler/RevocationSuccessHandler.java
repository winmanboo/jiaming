package com.deepcode.jiaming.uaa.handler;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.repository.JmtkJdbcOAuth2AuthorizationService;
import com.deepcode.jiaming.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenRevocationAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Objects;

/**
 * 撤销令牌成功处理器
 * <br/>
 * 主要功能就是删除数据库中的授权信息和 redis 中的 jmtk 信息，这样撤销令牌后用户请求资源将直接表现为未通过认证和授权,
 * 简而言之就是踢人功能，并返回成功的响应体（默认是没有的）
 *
 * @author winmanboo
 * @date 2023/6/9 21:38
 */
@RequiredArgsConstructor
public class RevocationSuccessHandler implements AuthenticationSuccessHandler {
    private final JmtkJdbcOAuth2AuthorizationService authorizationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2TokenRevocationAuthenticationToken authenticationToken =
                (OAuth2TokenRevocationAuthenticationToken) authentication;
        // 查询并删除对应的 authorization 信息，同时会删除 jmtk
        OAuth2Authorization authorization =
                authorizationService.findByToken(authenticationToken.getToken(), OAuth2TokenType.ACCESS_TOKEN);
        if (Objects.nonNull(authorization)) {
            authorizationService.remove(authorization);
        }
        ResponseUtil.out(response, Result.ok());
    }
}
