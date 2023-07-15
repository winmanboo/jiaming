package com.deepcode.jiaming.admin.dto;

import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author winmanboo
 * @date 2023/7/15 09:57
 */
@Data
public class RoleDTO {
    @ApiModelProperty("角色 id")
    @NotNull(message = "角色 id 不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("角色名称")
    @NotEmpty(message = "角色名称不能为空", groups = AddGroup.class)
    private String name;

    @ApiModelProperty("角色权限标识")
    @NotEmpty(message = "角色权限标识", groups = AddGroup.class)
    private String code;

    @ApiModelProperty("角色状态 0:停用 1:启用")
    @NotEmpty(message = "角色状态不能为空", groups = AddGroup.class)
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
