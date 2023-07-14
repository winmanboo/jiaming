package com.deepcode.jiaming.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author winmanboo
 * @date 2023/7/14 13:27
 */
@Data
public class UserDTO {
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
