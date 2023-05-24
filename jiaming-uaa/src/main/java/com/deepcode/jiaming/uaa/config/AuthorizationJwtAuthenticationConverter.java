package com.deepcode.jiaming.uaa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 将 jwt 中的权限信息转换为 AuthenticationToken
 *
 * @author winmanboo
 * @date 2023/5/24 17:15
 * @see JwtAuthenticationToken
 */
@Component
@RequiredArgsConstructor
public class AuthorizationJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final OAuth2AuthorizationService authorizationService;

    // 用于取出 jwt 中的 authorities 信息
    private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthorityConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String tokenValue = source.getTokenValue();
        // 查看数据库中是否存在 token
        OAuth2Authorization authorization = authorizationService.findByToken(tokenValue, OAuth2TokenType.REFRESH_TOKEN);
        if (null == authorization) {
            throw new InvalidBearerTokenException(tokenValue + " not found in OAuth2AuthorizationService");
        }

        Collection<GrantedAuthority> authorities = jwtGrantedAuthorityConverter.convert(source);
        return new JwtAuthenticationToken(source, authorities);
    }
}
