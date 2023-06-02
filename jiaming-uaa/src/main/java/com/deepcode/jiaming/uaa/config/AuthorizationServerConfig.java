package com.deepcode.jiaming.uaa.config;

import com.deepcode.jiaming.constants.AuthConstant;
import com.deepcode.jiaming.uaa.deserializer.LongMixin;
import com.deepcode.jiaming.uaa.deserializer.SecurityUserMixin;
import com.deepcode.jiaming.uaa.entity.SecurityUser;
import com.deepcode.jiaming.uaa.properties.OAuth2JwkProperties;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author winmanboo
 * @date 2023/5/23 14:40
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = OAuth2JwkProperties.class)
public class AuthorizationServerConfig {
    private final OAuth2JwkProperties oAuth2JwkProperties;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();

        httpSecurity.securityMatcher(endpointsMatcher)
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        return httpSecurity.build();
    }

    // 管理注册的 client
    @Bean
    public JdbcRegisteredClientRepository jdbcRegisteredClientRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        /*RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("wzm")
                .scope("message.read")
                .redirectUri("https://www.baidu.com")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 授权码模式
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // 刷新 token 模式
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // 客户端模式
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER) // 这种模式其实就是简化模式
                .authorizationGrantType(AuthorizationGrantType.PASSWORD) // 密码模式
                .clientSecret(passwordEncoder.encode("123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        jdbcRegisteredClientRepository.save(registeredClient);
        return jdbcRegisteredClientRepository;*/
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService authorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper =
                new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationParametersMapper parametersMapper =
                new JdbcOAuth2AuthorizationService.OAuth2AuthorizationParametersMapper();

        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        // objectMapper.registerModule(new CoreJackson2Module());
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        // 用于解决 JackSon 反序列化问题
        // If you believe this class is safe to deserialize, please provide an explicit mapping using Jackson annotations or by providing a Mixin.
        // If the serialization is only done by a trusted source, you can also enable default typing.
        // https://stackoverflow.com/questions/70919216/jwtauthenticationtoken-is-not-in-the-allowlist-jackson-issue
        objectMapper.addMixIn(SecurityUser.class, SecurityUserMixin.class);
        objectMapper.addMixIn(Long.class, LongMixin.class);

        rowMapper.setObjectMapper(objectMapper);
        parametersMapper.setObjectMapper(objectMapper);

        authorizationService.setAuthorizationRowMapper(rowMapper);
        authorizationService.setAuthorizationParametersMapper(parametersMapper);
        return authorizationService;
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                         RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        OAuth2JwkProperties.Rsa rsa = oAuth2JwkProperties.getRsa();
        RSAKey rsaKey = new RSAKey.Builder(rsa.getPublicKey())
                .privateKey(rsa.getPrivateKey())
                // FIXME: 2023/5/25 keyId 不要每次都用 UUID 生成
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 配置 oauth2 的端点路径
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .build();
    }

    /**
     * 负责 JWT 自定义处理
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication authentication = context.getPrincipal();
            // 用户的权限（角色）
            Set<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            Object principal = authentication.getPrincipal();

            JwtClaimsSet.Builder claimsBuilder = context.getClaims().claim(AuthConstant.AUTHORITY_CLAIM_NAME, authorities);

            if (principal instanceof String) {
                // 客户端模式 principal 为 clientId
                String clientId = (String) principal;
            } else if (principal instanceof SecurityUser) {
                // 授权码模式 principal 为 SecurityUser
                SecurityUser securityUser = (SecurityUser) principal;
                // 添加权限信息
                claimsBuilder.claim(AuthConstant.IS_ADMIN_CLAIM_NAME, securityUser.getIsAdmin())
                        .claim(AuthConstant.TENANT_CLAIM_NAME, securityUser.getTenantId())
                        .claim(AuthConstant.USER_ID_CLAIM_NAME, securityUser.getUserId());
            }
        };
    }
}
