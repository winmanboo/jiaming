package com.deepcode.jiaming.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.uaa.entity.OAuth2Token;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author winmanboo
 * @date 2023/7/19 21:20
 */
public interface OAuth2TokenService extends IService<OAuth2Token> {
    /**
     * 被访问令牌信息
     *
     * @param oAuth2AccessToken o auth2访问令牌
     * @return {@link OAuth2Token}
     */
    OAuth2Token getInfoByAccessToken(OAuth2AccessToken oAuth2AccessToken);

    /**
     * 根据自定义令牌删除令牌信息
     *
     * @param token 自定义令牌
     */
    void removeByToken(String token);
}
