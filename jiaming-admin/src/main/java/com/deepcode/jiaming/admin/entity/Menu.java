package com.deepcode.jiaming.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deepcode.jiaming.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Menu对象", description = "系统菜单")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("父菜单id")
    private Long parentId;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("1:Layout 不会跳转界面 2:demo/demo 会跳转到该界面")
    private String component;

    @ApiModelProperty("当设置noRedirect时，该路由在面包屑导航中不能被点击")
    private String redirect;

    @ApiModelProperty("是否一直现实根路由")
    private Integer alwaysShow;

    @ApiModelProperty("当设置未true时，该路由不会出现侧边栏，用于一些编辑界面")
    private Integer hidden;

    @ApiModelProperty("设置路由在侧边栏和面包屑中展示的名字")
    private String title;

    @ApiModelProperty("设置该路由的图标，支持 svg-class，也支持 el-icon-x")
    private String icon;

    @ApiModelProperty("如果设置为true，则不会被缓存，默认false")
    private Integer noCache;

    @ApiModelProperty("如果设置为false，则不会在面包屑中展示（默认true）")
    private Integer breadcrumb;

    @ApiModelProperty("如果设置为true，则会固定在 tags-view 中（默认false）")
    private Integer affix;

    @ApiModelProperty("当路由设置该属性，则会高亮相对应的侧边栏")
    private String activeMenu;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;

    @ApiModelProperty("是否启用 1：启用 0：禁用")
    private Integer enable;

    @ApiModelProperty("菜单类型 0：目录 1：菜单 2：按钮")
    private Integer type;
}
