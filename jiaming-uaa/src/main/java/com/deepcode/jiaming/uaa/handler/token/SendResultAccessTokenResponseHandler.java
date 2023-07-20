package com.deepcode.jiaming.uaa.handler.token;

import com.deepcode.jiaming.constants.OAuth2Constant;
import com.deepcode.jiaming.entity.OAuth2Token;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import com.deepcode.jiaming.uaa.service.OAuth2TokenService;
import com.deepcode.jiaming.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 对 AccessToken 的响应包装一层系统的响应体 Result
 *
 * @author winmanboo
 * @date 2023/7/8 12:46
 * @see org.springframework.security.oauth2.server.authorization.web.OAuth2TokenEndpointFilter
 */
@RequiredArgsConstructor
public class SendResultAccessTokenResponseHandler implements AuthenticationSuccessHandler {

    private final Converter<OAuth2AccessTokenResponse, Map<String, Object>> responseMapConverter =
            new DefaultOAuth2AccessTokenResponseMapConverter();

    private final OAuth2Properties oAuth2Properties;

    private final RedisTemplate<String, Object> redisTemplate;

    private final OAuth2TokenService oAuth2TokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication =
                (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder =
                OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                        .tokenType(accessToken.getTokenType())
                        .scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        OAuth2AccessTokenResponse accessTokenResponse = builder.build();

        // get token from db
        com.deepcode.jiaming.uaa.entity.OAuth2Token tokenInfo = oAuth2TokenService.getInfoByAccessToken(accessToken);
        String token = tokenInfo.getToken();

        // save token to cache
        saveToCache(accessTokenResponse, token, oAuth2Properties.getToken().getAccessTokenTimeout());

        Map<String, Object> tokenResponseParameters = responseMapConverter.convert(accessTokenResponse);
        if (Objects.nonNull(tokenResponseParameters)) { // there is no need to judge.
            // replace token value to custom token
            tokenResponseParameters.put(OAuth2ParameterNames.ACCESS_TOKEN, token);
        }

        // 对默认的输出结果包装一层系统的响应体
        ResponseUtil.out(response, Result.ok(tokenResponseParameters));
    }

    /**
     * 保存到缓存
     *
     * @param accessTokenResponse 访问令牌反应
     * @param token               自定义令牌
     * @param timeout             超时时间
     */
    protected void saveToCache(OAuth2AccessTokenResponse accessTokenResponse, String token, long timeout) {
        if (Objects.nonNull(accessTokenResponse)) {

            OAuth2Token oAuth2Token = new OAuth2Token();
            oAuth2Token.setAccessToken(accessTokenResponse.getAccessToken().getTokenValue());
            oAuth2Token.setTokenType(accessTokenResponse.getAccessToken().getTokenType().getValue());
            oAuth2Token.setRefreshToken(Objects.nonNull(accessTokenResponse.getRefreshToken()) ?
                    accessTokenResponse.getRefreshToken().getTokenValue() : null);
            oAuth2Token.setExpiresIn(getExpiresIn(accessTokenResponse));

            // save to redis
            redisTemplate.opsForValue().set(String.format(OAuth2Constant.JMTK_KEY_FORMAT, token),
                    oAuth2Token,
                    timeout,
                    TimeUnit.SECONDS);
        }
    }

    private static long getExpiresIn(OAuth2AccessTokenResponse tokenResponse) {
        if (tokenResponse.getAccessToken().getExpiresAt() != null) {
            return ChronoUnit.SECONDS.between(Instant.now(), tokenResponse.getAccessToken().getExpiresAt());
        }
        return -1;
    }
}
