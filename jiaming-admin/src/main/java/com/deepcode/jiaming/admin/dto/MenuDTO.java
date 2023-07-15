package com.deepcode.jiaming.admin.dto;

import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 菜单dto
 *
 * @author winmanboo
 * @date 2023/7/15 17:15
 */
@Data
public class MenuDTO {

    @ApiModelProperty("菜单 id")
    @NotNull(message = "菜单 id 不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("父菜单 id")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单类型")
    private Integer type;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("菜单状态")
    private Integer enable;

    @ApiModelProperty("显示状态")
    private Integer hidden;

    @ApiModelProperty("始终显示")
    private Integer alwaysShow;

    @ApiModelProperty("组件地址")
    private String component;

    @ApiModelProperty("权限标识")
    private String permission;
}
