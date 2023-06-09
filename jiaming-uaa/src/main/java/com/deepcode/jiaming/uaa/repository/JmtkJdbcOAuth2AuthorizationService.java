package com.deepcode.jiaming.uaa.repository;

import com.deepcode.jiaming.constants.OAuth2Constant;
import com.deepcode.jiaming.uaa.properties.OAuth2Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author winmanboo
 * @date 2023/6/8 22:46
 */
@Slf4j
public class JmtkJdbcOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final OAuth2Properties oAuth2Properties;

    public JmtkJdbcOAuth2AuthorizationService(JdbcOperations jdbcOperations,
                                              RegisteredClientRepository registeredClientRepository,
                                              RedisTemplate<String, Object> redisTemplate,
                                              OAuth2Properties oAuth2Properties) {
        super(jdbcOperations, registeredClientRepository);
        this.redisTemplate = redisTemplate;
        this.oAuth2Properties = oAuth2Properties;
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        super.remove(authorization);

        removeJmtk(authorization.getAccessToken().getToken().getTokenValue());
    }

    /**
     * 保存 jmtk 对应的访问令牌到 redis 中
     *
     * @param accessToken 访问令牌
     * @param jmtk        jm-tk 自定义 token
     */
    public void saveJmtk(String accessToken, String jmtk) {
        // 存储 accessToken 对应的 jmtk
        // 为什么这么做？因为在删除令牌的时候需要根据 accessToken 反查 jmtk 然后删除对应的 jmtk
        // 设置超时时间为访问令牌的超时时间
        redisTemplate.opsForValue().set(accessToken, jmtk,
                oAuth2Properties.getToken().getAccessTokenTimeout(), TimeUnit.SECONDS);
    }

    /**
     * 删除 jmtk
     *
     * @param accessToken 访问令牌
     */
    public void removeJmtk(String accessToken) {
        String jmtk = (String) redisTemplate.opsForValue().get(accessToken);
        if (Objects.nonNull(jmtk)) {
            // 删除 jmtk 对应的 accessToken 信息
            redisTemplate.delete(String.format(OAuth2Constant.JMTK_KEY_FORMAT, jmtk));
        }
        // 删除 accessToken 对应的 jmtk 信息
        redisTemplate.delete(accessToken);
    }
}
