package com.deepcode.jiaming.uaa.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.constants.AuthConstant;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.uaa.constants.OAuth2Constant;
import com.deepcode.jiaming.uaa.constants.OAuth2GrantTypes;
import com.deepcode.jiaming.uaa.deserializer.LongMixin;
import com.deepcode.jiaming.uaa.deserializer.SecurityUserMixin;
import com.deepcode.jiaming.uaa.entity.SecurityUser;
import com.deepcode.jiaming.uaa.grant.CaptchaAuthenticationProvider;
import com.deepcode.jiaming.uaa.grant.CaptchaGrantAuthenticationConverter;
import com.deepcode.jiaming.uaa.handler.RevocationSuccessHandler;
import com.deepcode.jiaming.uaa.handler.token.SendResultAccessTokenResponse;
import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import com.deepcode.jiaming.uaa.repository.JmtkJdbcOAuth2AuthorizationService;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;
import java.util.List;

/**
 * @author winmanboo
 * @date 2023/5/23 14:40
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = OAuth2Properties.class)
public class AuthorizationServerConfig {
    private final OAuth2Properties oAuth2Properties;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity,
                                                                      JmtkJdbcOAuth2AuthorizationService authorizationService,
                                                                      AuthenticationManager authenticationManager,
                                                                      OAuth2TokenGenerator<?> tokenGenerator,
                                                                      RedisTemplate<String, Object> redisTemplate) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();
        // revoke oauth2 access token success handler
        authorizationServerConfigurer.tokenRevocationEndpoint(config ->
                config.revocationResponseHandler(new RevocationSuccessHandler(authorizationService)));

        /*authorizationServerConfigurer.oidc(oidc -> {
            oidc.userInfoEndpoint(userInfoEndpoint -> {
                userInfoEndpoint
            })
        })*/

        CaptchaGrantAuthenticationConverter authenticationConverter = new CaptchaGrantAuthenticationConverter(redisTemplate);
        CaptchaAuthenticationProvider authenticationProvider =
                new CaptchaAuthenticationProvider(authenticationManager, tokenGenerator, authorizationService);

        authorizationServerConfigurer.authorizationServerMetadataEndpoint(metadata ->
                        // 让认证服务器元数据中有自定义的认证方式
                        metadata.authorizationServerMetadataCustomizer(customizer ->
                                customizer.grantType(OAuth2GrantTypes.CAPTCHA.getValue())))
                // 添加自定义认证类型
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .accessTokenRequestConverter(authenticationConverter)
                        .authenticationProvider(authenticationProvider)
                        .accessTokenResponseHandler(new SendResultAccessTokenResponse()));

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
        JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcTemplate);
        RegisteredClient defaultClient = repository.findByClientId(OAuth2Constant.DEFAULT_CLIENT_ID);
        if (defaultClient == null) {
            RegisteredClient client = createDefaultClient(passwordEncoder);
            repository.save(client);
        }
        return repository;
    }

    @Bean
    public JmtkJdbcOAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                                   RegisteredClientRepository registeredClientRepository,
                                                                   RedisTemplate<String, Object> redisTemplate) {
        JmtkJdbcOAuth2AuthorizationService authorizationService = new JmtkJdbcOAuth2AuthorizationService(jdbcTemplate,
                registeredClientRepository,
                redisTemplate,
                oAuth2Properties);
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
        OAuth2Properties.Jwk jwk = oAuth2Properties.getJwk();
        RSAKey rsaKey = new RSAKey.Builder(jwk.getPublicKey())
                .privateKey(jwk.getPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JwtEncoder jwtEncoder,
                                                  OAuth2TokenCustomizer<JwtEncodingContext> customizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(customizer);
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, // access token
                new OAuth2RefreshTokenGenerator() // refresh token
        );
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
            /*Set<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());*/

            Object principal = authentication.getPrincipal();

            if (principal instanceof String clientId) {
                // 客户端模式 principal 为 clientId
            } else if (principal instanceof SecurityUser securityUser) {
                // 授权码模式 principal 为 SecurityUser
                // 添加权限信息
                List<String> authorities = securityUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                context.getClaims().claim(AuthConstant.AUTHORITY_CLAIM_NAME, authorities)
                        .claim(AuthConstant.IS_ADMIN_CLAIM_NAME, securityUser.getIsAdmin() == 1)
                        .claim(AuthConstant.TENANT_CLAIM_NAME, securityUser.getTenantId())
                        .claim(AuthConstant.USER_ID_CLAIM_NAME, securityUser.getUserId())
                        .claim(AuthConstant.USER_NAME_CLAIM_NAME, securityUser.getUsername());
            }
        };
    }

    /**
     * 创建默认的客户端信息
     *
     * @param passwordEncoder 密码加密
     * @return 默认的客户端
     */
    private RegisteredClient createDefaultClient(PasswordEncoder passwordEncoder) {
        String redirectUri = oAuth2Properties.getGatewayUri() + "/jiaming/uaa/auth/code";

        if (CharSequenceUtil.isEmpty(redirectUri)) {
            throw new JiamingException("redirect uri can not be null");
        }

        return RegisteredClient.withId(OAuth2Constant.DEFAULT_ID)
                .clientId(OAuth2Constant.DEFAULT_CLIENT_ID)
//                .scope(OidcScopes.OPENID) // 需要支持 oidc
                .scope(OAuth2Constant.DEFAULT_SCOPE)
                .redirectUri(redirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 授权码模式
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // 刷新 token 模式
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // 客户端模式
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER) // 这种模式其实就是简化模式
                .authorizationGrantType(OAuth2GrantTypes.CAPTCHA) // 验证码模式
                .clientSecret(passwordEncoder.encode(OAuth2Constant.DEFAULT_CLIENT_SECRET))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(oAuth2Properties.getToken().getAccessTokenTimeout()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(oAuth2Properties.getToken().getRefreshTokenTimeout()))
                        .build())
                .build();
    }
}
