package com.deepcode.jiaming.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author winmanboo
 * @date 2023/5/22 13:38
 */
@Slf4j
@Component
public class TokenAuthenticationFilter implements GlobalFilter, Ordered {
    /*private final AsyncLoadingCache<Long, UserInfo> userInfoCache = Caffeine.newBuilder()
            // 写入数据后多久过期,只阻塞当前数据加载线程,其他线程返回旧值
            .refreshAfterWrite(Duration.ofMinutes(1))
            .buildAsync(new AsyncCacheLoader<>() {
                @Override
                public @NonNull CompletableFuture<UserInfo> asyncLoad(@NonNull Long key, @NonNull Executor executor) {
                    return CompletableFuture.supplyAsync(() -> {
                        // TODO: 2023/5/22 远程调用根据 token 获取用户信息
                        return null;
                    });
                }
            });*/

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
