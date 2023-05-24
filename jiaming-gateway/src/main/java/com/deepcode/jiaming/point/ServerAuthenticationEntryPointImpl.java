package com.deepcode.jiaming.point;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.UserResultStatus;
import com.deepcode.jiaming.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author winmanboo
 * @date 2023/5/24 17:30
 */
@Slf4j
@Component
public class ServerAuthenticationEntryPointImpl implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        // ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

//        String requestId = request.getId();

        Result<?> error = Result.fail(UserResultStatus.A0302);

        if (ex instanceof AuthenticationCredentialsNotFoundException) {
            error = Result.fail(UserResultStatus.A0301);
        } else if (ex instanceof InvalidBearerTokenException) {
            InvalidBearerTokenException invalidBearerTokenException = (InvalidBearerTokenException) ex;
            OAuth2Error oauth2Error = invalidBearerTokenException.getError();

            if (oauth2Error instanceof BearerTokenError) {
                error = Result.fail(UserResultStatus.A0303);
            } else {
                error = Result.fail(UserResultStatus.A0304);
            }
        } else {
            // 其他异常待补充
        }
        return ResponseUtil.writeWith(response, error);
    }
}
