package com.deepcode.jiaming.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author winmanboo
 * @date 2023/7/15 09:57
 */
@Data
public class RoleDTO {
    @ApiModelProperty("角色 id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色权限标识")
    private String code;

    @ApiModelProperty("角色状态 0:停用 1:启用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
