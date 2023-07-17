package com.deepcode.jiaming.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author winmanboo
 * @date 2023/7/17 21:09
 */
@Data
public class RoleMenuAssignDTO {
    @ApiModelProperty("角色 id")
    @NotNull(message = "角色 id 不能为空")
    private Long roleId;

    @ApiModelProperty("菜单 id 列表")
    private List<Long> menuIds;
}
