package com.deepcode.jiaming.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 路由源信息
 * @author winmanboo
 * @date 2023/6/8 18:44
 */
@Data
public class RouteMetaVo {
    @ApiModelProperty("设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    @ApiModelProperty("设置该路由的图标")
    private String icon;

    @ApiModelProperty("如果设置为true，则不会被 <keep-alive> 缓存，默认为 false")
    private Boolean noCache;

    @ApiModelProperty("如果设置为false，则不会在面包屑中显示，默认为true")
    private Boolean breadcrumb;

    @ApiModelProperty("如果设置为true，它则会固定在tags-view中，默认为 false")
    private Boolean affix;

    @ApiModelProperty("当路由设置了该路径，则会高亮相对应的侧边栏")
    private String activeMenu;
}
