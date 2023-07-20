package com.deepcode.jiaming.config;

import com.deepcode.jiaming.auth.AuthorizationManager;
import com.deepcode.jiaming.constants.AuthConstant;
import com.deepcode.jiaming.constants.Constants;
import com.deepcode.jiaming.filter.JmtkFilter;
import com.deepcode.jiaming.point.ServerAuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author winmanboo
 * @date 2023/5/23 16:28
 */
@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // 开启鉴权服务
public class ResourceServerConfig {
    private final AuthorizationManager authorizationManager;

    private final ServerAuthenticationEntryPointImpl serverAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain httpSecurityFilterChain(ServerHttpSecurity httpSecurity,
                                                          RedisTemplate<String, Object> redisTemplate,
                                                          Converter<Jwt, Mono<AbstractAuthenticationToken>> converter) {
        httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers(Constants.OAUTH2_JWK_ENDPOINT).permitAll()
                .anyExchange()
                .access(authorizationManager)
                .and()
                .addFilterAt(new JmtkFilter(redisTemplate), SecurityWebFiltersOrder.AUTHENTICATION)
                .oauth2ResourceServer(customizer -> {
                    customizer.jwt()
                            .jwtAuthenticationConverter(converter);
                            // .publicKey(((RSAPublicKey) new RSA(null, jwkPublicKey).getPublicKey()));
                    customizer.authenticationEntryPoint(serverAuthenticationEntryPoint);
                });

        // 使用不记名令牌，指的是是否可以通过 curl 命令将令牌作为参数传输
        ServerBearerTokenAuthenticationConverter bearerTokenAuthenticationConverter =
                new ServerBearerTokenAuthenticationConverter();
        bearerTokenAuthenticationConverter.setAllowUriQueryParameter(true); // 默认为 false
        httpSecurity.oauth2ResourceServer().bearerTokenConverter(bearerTokenAuthenticationConverter);

        return httpSecurity.build();
    }

    /**
     * 用于将 jwt 转换为 AuthenticationToken
     *
     * @return Converter
     */
    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
