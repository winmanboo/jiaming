package com.deepcode.jiaming.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.Instant;

/**
 * @author winmanboo
 * @date 2023/7/18 20:44
 */
@Data
@TableName("oauth2_registered_client")
public class OAuth2Client {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("客户端 id")
    private String clientId;

    @ApiModelProperty("客户端 id 签发时间")
    private Instant clientIdIssuedAt;

    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    @ApiModelProperty("客户端密钥过期时间")
    private String clientSecretExpiresAt;

    @ApiModelProperty("应用名")
    private String clientName;

    @ApiModelProperty("认证方式（逗号分隔）")
    private String clientAuthenticationMethods;

    @ApiModelProperty("授权类型（逗号分隔）")
    private String authorizationGrantTypes;

    @ApiModelProperty("重定向地址（逗号分隔）")
    private String redirectUris;

    @ApiModelProperty("作用域（逗号分隔）")
    private String scopes;

    @ApiModelProperty("令牌配置")
    private String tokenSettings;
}
