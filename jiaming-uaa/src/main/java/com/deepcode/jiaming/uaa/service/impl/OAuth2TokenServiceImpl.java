package com.deepcode.jiaming.uaa.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.constants.OAuth2Constant;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.uaa.entity.OAuth2Token;
import com.deepcode.jiaming.uaa.mapper.OAuth2TokenMapper;
import com.deepcode.jiaming.uaa.service.OAuth2TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author winmanboo
 * @date 2023/7/19 21:32
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2TokenServiceImpl extends ServiceImpl<OAuth2TokenMapper, OAuth2Token> implements OAuth2TokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public OAuth2Token getInfoByAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        return lambdaQuery()
                .eq(Objects.nonNull(oAuth2AccessToken), OAuth2Token::getAccessTokenValue, oAuth2AccessToken.getTokenValue())
                .one();
    }

    @Override
    public void removeByToken(String token) {
        if (Objects.isNull(token) || CharSequenceUtil.isEmpty(token)) {
            throw new JiamingException("token不能为空");
        }
        lambdaUpdate().eq(OAuth2Token::getToken, token).remove();
        redisTemplate.delete(String.format(OAuth2Constant.JMTK_KEY_FORMAT, token));
    }
}
