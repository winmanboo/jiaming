package com.deepcode.jiaming.api.uaa;

import com.deepcode.jiaming.api.uaa.entity.TokenSettingVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.Instant;

/**
 * @author winmanboo
 * @date 2023/7/19 12:14
 */
@Data
public class OAuth2ClientVo {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("客户端 id")
    private String clientId;

    @ApiModelProperty("客户端 id 签发时间")
    private Instant clientIdIssuedAt;

    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    @ApiModelProperty("客户端密钥过期时间")
    private Instant clientSecretExpiresAt;

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
    private TokenSettingVo tokenSettingVo;
}
