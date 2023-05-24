package com.deepcode.jiaming.config;

import cn.hutool.crypto.asymmetric.RSA;
import com.deepcode.jiaming.auth.AuthorizationManager;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.feign.UaaOpenFeignClient;
import com.deepcode.jiaming.point.ServerAuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.interfaces.RSAPublicKey;

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

    private final UaaOpenFeignClient uaaOpenFeignClient;

    private final ServerAuthenticationEntryPointImpl serverAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain httpSecurityFilterChain(ServerHttpSecurity httpSecurity) {


        httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .authorizeExchange().anyExchange().access(authorizationManager)
                .and()
                .oauth2ResourceServer().jwt(jwtSpec -> {
                    // 网络请求获取授权服务器的公钥
                    String publicKey = uaaOpenFeignClient.getPublicKey();

                    if (publicKey.isEmpty()) {
                        throw new JiamingException("public key get failed");
                    }

                    log.debug("get publicKey: {}", publicKey);
                    jwtSpec.publicKey(((RSAPublicKey) new RSA(null, publicKey).getPublicKey()));
                })
                .authenticationEntryPoint(serverAuthenticationEntryPoint);
                // 配置资源服务器的无权限，无认证拦截器等 以及JWT验证
                // .oauth2ResourceServer().jwt().publicKey()
                // .oauth2ResourceServer().jwt().jwkSetUri("http://127.0.0.1:9000/rsa/publickKey")

        // 使用不记名令牌，指的是是否可以通过 curl 命令将令牌作为参数传输
        ServerBearerTokenAuthenticationConverter bearerTokenAuthenticationConverter =
                new ServerBearerTokenAuthenticationConverter();
        bearerTokenAuthenticationConverter.setAllowUriQueryParameter(true);
        httpSecurity.oauth2ResourceServer().bearerTokenConverter(bearerTokenAuthenticationConverter);

        return httpSecurity.build();
    }

}
