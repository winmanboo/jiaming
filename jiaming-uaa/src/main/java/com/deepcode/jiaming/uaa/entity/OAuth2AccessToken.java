package com.deepcode.jiaming.uaa.entity;

import lombok.Data;

/**
 * 接收访问令牌实体
 *
 * @author winmanboo
 * @date 2023/6/6 17:38
 */
@Data
public class OAuth2AccessToken {
    /**
     * 访问令牌
     */
    private String access_token;

    /**
     * 刷新令牌
     */
    private String refresh_token;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 令牌类型
     */
    private String token_type;

    /**
     * 过期时间
     */
    private Long expires_in;
}
