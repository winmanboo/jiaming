package com.deepcode.jiaming.uaa.repository;

import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 使用 Redis 存储授权信息
 *
 * @author winmanboo
 * @date 2023/6/7 14:10
 * @deprecated 暂时不全部使用 Redis 进行存储授权信息，因为用户根据 refreshToken 获取访问令牌的时候授权数据会过期，
 * 而且不能对数据的过期时间进行动态配置，因为用户配置的 token 过期时间不同
 */
@Slf4j
//@Component
@RequiredArgsConstructor
@Deprecated(since = "6/7", forRemoval = true)
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {
    /**
     * key 为 authorizationId 存储全量授权数据
     */
    private static final String OAUTH2_AUTHORIZATION_ID = "jiaming:oauth2_authorization:id:%s";

    /**
     * {0}：TokenType -> AccessToken or RefreshToken
     * <br/>
     * {1}：TokenValue
     */
    private static final String OAUTH2_TOKEN_TYPE = "jiaming:oauth2_authorization:token_type:%s:%s";

    private final RedisTemplate<String, Object> redisTemplate;

    private final OAuth2Properties oAuth2Properties;

    @Override
    public void save(OAuth2Authorization authorization) {
        if (authorization == null) {
            log.info("save: authorization is null");
            return;
        }

        internalSave(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        if (authorization == null) {
            log.info("remove: authorization is null");
            return;
        }

        internalRemove(authorization);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return (OAuth2Authorization) redisTemplate.opsForValue().get(String.format(OAUTH2_AUTHORIZATION_ID, id));
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return (OAuth2Authorization) redisTemplate.opsForValue().get(String.format(OAUTH2_TOKEN_TYPE, tokenType.getValue(), token));
    }

    private void internalSave(OAuth2Authorization authorization) {
        OAuth2Properties.Redis redis = oAuth2Properties.getRedis();
        long timeout = redis.getAuthorizationTimeout();

        TimeUnit seconds = TimeUnit.SECONDS;
        redisTemplate.opsForValue().set(String.format(OAUTH2_AUTHORIZATION_ID, authorization.getId()),
                authorization,
                timeout,
                seconds);

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (Objects.nonNull(accessToken)) {
            OAuth2AccessToken token = accessToken.getToken();
            if (Objects.nonNull(token)) {
                String tokenValue = token.getTokenValue();
                redisTemplate.opsForValue().set(String.format(OAUTH2_TOKEN_TYPE,
                        OAuth2TokenType.ACCESS_TOKEN.getValue(), tokenValue), authorization, timeout, seconds);
            }
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            OAuth2RefreshToken token = refreshToken.getToken();
            if (Objects.nonNull(token)) {
                String tokenValue = token.getTokenValue();
                redisTemplate.opsForValue().set(String.format(OAUTH2_TOKEN_TYPE,
                        OAuth2TokenType.REFRESH_TOKEN.getValue(), tokenValue), authorization, timeout, seconds);
            }
        }
    }

    private void internalRemove(OAuth2Authorization authorization) {
        redisTemplate.delete(String.format(OAUTH2_AUTHORIZATION_ID, authorization.getId()));

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (Objects.nonNull(accessToken)) {
            OAuth2AccessToken token = accessToken.getToken();
            String tokenValue = token.getTokenValue();
            redisTemplate.delete(String.format(OAUTH2_TOKEN_TYPE, OAuth2TokenType.ACCESS_TOKEN.getValue(), tokenValue));
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            OAuth2RefreshToken token = refreshToken.getToken();
            String tokenValue = token.getTokenValue();
            redisTemplate.delete(String.format(OAUTH2_TOKEN_TYPE, OAuth2TokenType.REFRESH_TOKEN.getValue(), tokenValue));
        }
    }
}
