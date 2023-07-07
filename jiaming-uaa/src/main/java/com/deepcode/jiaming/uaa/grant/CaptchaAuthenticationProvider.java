package com.deepcode.jiaming.uaa.grant;

import cn.hutool.core.collection.CollUtil;
import com.deepcode.jiaming.uaa.constants.OAuth2GrantTypes;
import com.deepcode.jiaming.uaa.exception.CaptchaAuthenticationException;
import com.deepcode.jiaming.uaa.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author winmanboo
 * @date 2023/7/6 20:48
 */
@Slf4j
public class CaptchaAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final AuthenticationManager authenticationManager;

    private final OAuth2TokenGenerator<?> tokenGenerator;

    private final OAuth2AuthorizationService authorizationService;

    public CaptchaAuthenticationProvider(AuthenticationManager authenticationManager,
                                         OAuth2TokenGenerator<?> tokenGenerator,
                                         OAuth2AuthorizationService authorizationService) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.authorizationService = authorizationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CaptchaGrantAuthenticationToken authenticationToken = (CaptchaGrantAuthenticationToken) authentication;

        // 确保客户端已经认证过
        OAuth2ClientAuthenticationToken clientPrincipal =
                SecurityUtil.getAuthenticatedClientElseThrowInvalidClient(authenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        // 确保客户端配置为使用此授权授予类型
        if (registeredClient == null || !registeredClient.getAuthorizationGrantTypes().contains(OAuth2GrantTypes.CAPTCHA)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        Set<String> authorizedScopes = getAuthorizedScopes(registeredClient, authenticationToken.getScopes());

        // 认证
        Authentication authenticate = getAuthentication(authenticationToken);

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(authenticate)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizationGrantType(OAuth2GrantTypes.CAPTCHA)
                .authorizationGrant(authenticationToken);

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(clientPrincipal.getName())
                .authorizationGrantType(OAuth2GrantTypes.CAPTCHA);

        // ----- Access Token -----
        DefaultOAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        if (Objects.isNull(generatedAccessToken)) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        if (log.isTraceEnabled()) {
            log.trace("Generated access token");
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor claimAccessor) {
            authorizationBuilder.token(accessToken, metadata ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // ----- Refresh Token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            if (log.isTraceEnabled()) {
                log.trace("Generated refresh token");
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        // ----- ID Token -----
        OidcIdToken idToken;
        if (authorizedScopes.contains(OidcScopes.OPENID)) {
            tokenContext = tokenContextBuilder
                    .tokenType(new OAuth2TokenType(OidcParameterNames.ID_TOKEN))
                    // ID token customizer may need access to the access token and/or refresh token
                    .authorization(authorizationBuilder.build())
                    .build();
            // @formatter:on
            OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof Jwt)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the ID token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            if (log.isTraceEnabled()) {
                log.trace("Generated id token");
            }

            idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                    generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
            authorizationBuilder.token(idToken, metadata ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
        } else {
            idToken = null;
        }

        OAuth2Authorization authorization = authorizationBuilder.build();

        // 保存授权信息
        this.authorizationService.save(authorization);

        Map<String, Object> additionalParameters = new HashMap<>();
        if (Objects.nonNull(idToken)) {
            // 放入 id token
            additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
        }

        return new OAuth2AccessTokenAuthenticationToken(registeredClient,
                clientPrincipal,
                accessToken,
                refreshToken,
                additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 进行认证
     *
     * @param authenticationToken 身份验证令牌
     * @return {@link Authentication}
     */
    private Authentication getAuthentication(CaptchaGrantAuthenticationToken authenticationToken) {
        Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
        String username = (String) additionalParameters.get("username");
        String password = (String) additionalParameters.get("password");

        Authentication unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(unauthenticated);
        } catch (AuthenticationException e) {
            throw new CaptchaAuthenticationException("认证失败");
        }
        return authenticate;
    }

    /**
     * 获得授权范围
     *
     * @param registeredClient 注册客户端
     * @param requestedScopes  要求范围
     * @return {@link Set}<{@link String}>
     */
    private Set<String> getAuthorizedScopes(RegisteredClient registeredClient, Set<String> requestedScopes) {
        Set<String> authorizedScopes = registeredClient.getScopes();
        if (CollUtil.isNotEmpty(requestedScopes)) {
            Set<String> unauthorizedScopes = requestedScopes.stream()
                    .filter(requestedScope -> !registeredClient.getScopes().contains(requestedScope))
                    .collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(unauthorizedScopes)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST,
                        "OAuth 2.0 Parameter: " + OAuth2ParameterNames.SCOPE,
                        ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            authorizedScopes = new LinkedHashSet<>(requestedScopes);
        }

        if (log.isTraceEnabled()) {
            log.trace("Validated token request parameters");
        }
        return authorizedScopes;
    }
}
