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
    /**
     * 重定向获取授权码接口的地址
     */
    // private static final String REDIRECT_SERVER_LOCATION = "/jiaming/uaa/client/redirect_code";

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();

        /*Mono<Void> checkedHeader = checkJmtkHeader(exchange, ex);

        if (checkedHeader != null) {
            return checkedHeader;
        }*/

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

    /*private Mono<Void> checkJmtkHeader(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> authorizationValues = headers.get(OAuth2Constant.AUTHORIZATION_HEADER);
        if (CollUtil.isNotEmpty(authorizationValues)) { // 携带了 access token 直接返回
            return null;
        }
        List<String> values = headers.get(AuthConstant.AUTH_TOKEN_HEADER);

        return CollUtil.isEmpty(values) ?
                // 重定向到 uaa 服务发起获取授权码
                new RedirectServerAuthenticationEntryPoint(REDIRECT_SERVER_LOCATION).commence(exchange, e) :
                null;
    }*/
}
