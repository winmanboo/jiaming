package com.deepcode.jiaming.admin.dto;

import com.deepcode.jiaming.valid.StatusGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author winmanboo
 * @date 2023/7/14 13:27
 */
@Data
public class UserDTO {
    @ApiModelProperty("用户编号")
    @NotNull(message = "用户编号不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("状态")
    @NotNull(message = "用户状态不能为空", groups = StatusGroup.class)
    private Integer status;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
