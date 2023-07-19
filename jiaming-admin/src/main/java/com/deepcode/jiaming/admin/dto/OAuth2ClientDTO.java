package com.deepcode.jiaming.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author winmanboo
 * @date 2023/7/18 19:56
 */
@Data
public class OAuth2ClientDTO {
    @ApiModelProperty("客户端 id")
    private Long clientId;

    @ApiModelProperty("状态")
    private Integer status;
}
