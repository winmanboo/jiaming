package com.deepcode.jiaming.auth;

import cn.hutool.core.text.AntPathMatcher;
import com.deepcode.jiaming.properties.WhiteListProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 处理白名单的授权管理器
 *
 * @author winmanboo
 * @date 2023/5/23 20:07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final WhiteListProperties whiteListProperties;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        URI uri = request.getURI();

        // TODO 日志中放入请求 ID、主机名

        log.debug("访问：{}：{}", method, uri);

        boolean whited = whiteList(exchange);
        if (whited) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return authentication
                .map(requestAuthentication ->
                        new AuthorizationDecision(requestAuthentication.isAuthenticated()))
                .defaultIfEmpty(new AuthorizationDecision(false));

        /*return authentication.filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(s -> s.contains("ROLE_"))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));*/
    }

    private boolean whiteList(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String path = request.getURI().getPath();

        if (method.matches(HttpMethod.OPTIONS.name())) {
            log.debug("放行：{}：{}", method, path);
            return true;
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        for (String ignoreUrl : whiteListProperties.getIgnoreUrls()) {
            if (antPathMatcher.match(ignoreUrl, path)) {
                return true;
            }
        }

        return false;
    }
}
