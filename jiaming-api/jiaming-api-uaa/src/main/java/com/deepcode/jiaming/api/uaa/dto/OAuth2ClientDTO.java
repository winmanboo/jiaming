package com.deepcode.jiaming.api.uaa.dto;

import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author winmanboo
 * @date 2023/7/18 21:10
 */
@Data
public class OAuth2ClientDTO {
    @ApiModelProperty("编号")
    @NotEmpty(message = "编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String id;

    @ApiModelProperty("客户端编号")
    @NotEmpty(message = "客户端编号不能为空", groups = AddGroup.class)
    private String clientId;

    @ApiModelProperty("客户端密钥")
    @NotEmpty(message = "客户端密钥不能为空", groups = AddGroup.class)
    private String clientSecret;

    @ApiModelProperty("应用名")
    @NotEmpty(message = "应用名不能为空", groups = AddGroup.class)
    private String clientName;

    @ApiModelProperty("客户端认证方式")
    @NotEmpty(message = "客户端认证方式不能为空", groups = AddGroup.class)
    private String clientAuthenticationMethods;

    @ApiModelProperty("授权类型（逗号分隔）")
    @NotEmpty(message = "授权类型不能为空", groups = AddGroup.class)
    private String authorizationGrantTypes;

    @ApiModelProperty("可重定向的地址（逗号分隔）")
    @NotEmpty(message = "可重定向的地址不能为空", groups = AddGroup.class)
    private String redirectUris;

    @ApiModelProperty("作用域（逗号分隔）")
    private String scopes;

    @ApiModelProperty("访问令牌的有效时间（单位：秒）")
    @NotNull(message = "访问令牌的有效时间不能为空", groups = AddGroup.class)
    private Long accessTokenTimeToLive;

    @ApiModelProperty("刷新令牌的有效时间（单位：秒）")
    @NotNull(message = "刷新令牌的有效时间不能为空", groups = AddGroup.class)
    private Long refreshTokenTimeToLive;
}
