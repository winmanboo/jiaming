package com.deepcode.jiaming.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 路由
 * @author winmanboo
 * @date 2023/6/8 18:43
 */
@Data
public class RouteVo implements Serializable {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("parent id")
    private Long parentId;

    @ApiModelProperty("路径，例如 uri")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("状态")
    private Integer enable;

    @ApiModelProperty("菜单类型 0：目录 1：菜单 2：按钮")
    private Integer type;

    @ApiModelProperty("组件：1:Layout 不会跳转界面 2:demo/demo 会跳转到该界面")
    private String component;

    @ApiModelProperty("当设置 noRedirect 时，该路由在棉鞋到航中不能被点击")
    private String redirect;

    @ApiModelProperty("是否一直显示根路由")
    private Boolean alwaysShow;

    @ApiModelProperty("当设置为 true 时，该路由不会在侧边栏出现，如 login、401 等界面，或者一些编辑界面")
    private Boolean hidden;

    @ApiModelProperty("设定路由的名字")
    private String name;

    @ApiModelProperty("路由的元信息")
    private RouteMetaVo meta;

    @ApiModelProperty("子路由")
    private List<RouteVo> children;
}
