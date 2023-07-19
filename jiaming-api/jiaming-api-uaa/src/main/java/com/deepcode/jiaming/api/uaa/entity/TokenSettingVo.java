package com.deepcode.jiaming.api.uaa.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author winmanboo
 * @date 2023/7/19 10:04
 */
@Data
public class TokenSettingVo {
    @ApiModelProperty("访问令牌有效时间")
    private Double accessTokenTimeToLive;

    @ApiModelProperty("刷新令牌有效期")
    private Double refreshTokenTimeToLive;
}
