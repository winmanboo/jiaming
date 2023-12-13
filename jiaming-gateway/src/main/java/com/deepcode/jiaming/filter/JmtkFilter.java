package com.deepcode.jiaming.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import com.deepcode.jiaming.constants.AuthConstant;
import com.deepcode.jiaming.constants.Constants;
import com.deepcode.jiaming.constants.OAuth2Constant;
import com.deepcode.jiaming.entity.OAuth2Token;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * 处理 jm-tk 到 Authorization 请求头的转换，提前在认证前添加 Authorization 请求头
 *
 * @author winmanboo
 * @date 2023/6/6 19:08
 * @see org.springframework.security.web.server.authentication.AuthenticationWebFilter
 */
@Slf4j
@RequiredArgsConstructor
public class JmtkFilter implements WebFilter {
    private final RedisTemplate<String, Object> redisTemplate;

    @Nonnull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        log.info("Jmtk Filter Invoke");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> tokenValues = headers.get(AuthConstant.AUTH_TOKEN_HEADER);
        if (CollUtil.isEmpty(tokenValues)) {
            return chain.filter(exchange);
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 获取 jwk 公钥的地址不能携带 Authorization 请求头，否则会抛出 Could not obtain the keys
        if (antPathMatcher.match(request.getURI().getPath(), Constants.OAUTH2_JWK_ENDPOINT)) {
            return chain.filter(exchange);
        }

        String jmtk = tokenValues.get(0);

        OAuth2Token oAuth2Token = (OAuth2Token) redisTemplate.opsForValue()
                .get(String.format(OAuth2Constant.JMTK_KEY_FORMAT, jmtk));

        if (Objects.isNull(oAuth2Token)) {
            return chain.filter(exchange);
        }

        ServerHttpRequest newRequest = request.mutate()
                .header(OAuth2Constant.AUTHORIZATION_HEADER,
                        MessageFormat.format("{0} {1}",
                                oAuth2Token.getTokenType(),
                                oAuth2Token.getAccessToken())
                )
                .build();

        return chain.filter(exchange.mutate().request(newRequest).build());
    }
}
