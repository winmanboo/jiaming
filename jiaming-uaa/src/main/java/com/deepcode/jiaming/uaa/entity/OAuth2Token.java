package com.deepcode.jiaming.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author winmanboo
 * @date 2023/7/19 21:21
 */
@Data
@TableName("oauth2_authorization")
public class OAuth2Token {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("客户端的 id")
    private String registeredClientId;

    @ApiModelProperty("客户端 id")
    private String principalName;

    @ApiModelProperty("授权类型")
    private String authorizationGrantType;

    @ApiModelProperty("授权作用域")
    private String authorizedScopes;

    @ApiModelProperty("自定义 token")
    private String token;

    @ApiModelProperty("访问令牌值")
    private String accessTokenValue;

    @ApiModelProperty("访问令牌签发时间")
    private String accessTokenIssuedAt;

    @ApiModelProperty("访问令牌过期时间")
    private String accessTokenExpiresAt;

    @ApiModelProperty("访问令牌类型")
    private String accessTokenType;

    @ApiModelProperty("刷新令牌值")
    private String refreshTokenValue;

    @ApiModelProperty("刷新令牌签发时间")
    private String refreshTokenIssuedAt;

    @ApiModelProperty("刷新令牌过期时间")
    private String refreshTokenExpiresAt;
}
