package com.deepcode.jiaming.admin.utils;

import com.deepcode.jiaming.admin.vo.RouteVo;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 菜单路由辅助类
 *
 * @author winmanboo
 * @date 2023/7/5 21:38
 */
@UtilityClass
public class RouteHelper {

    /**
     * 生成路由树结构
     *
     * @param menus 菜单列表
     * @return 路由树
     */
    public static List<RouteVo> generateRouteTree(List<RouteVo> menus) {
        List<RouteVo> routes = new ArrayList<>();
        menus.forEach(menu -> {
            if (menu.getParentId() == 0) {
                routes.add(childrenRoute(menu, menus));
            }
        });
        return routes;
    }

    /**
     * 递归获取子路由
     *
     * @param route  路由
     * @param routes 路由列表
     * @return 子路由
     */
    private static RouteVo childrenRoute(RouteVo route, List<RouteVo> routes) {
        List<RouteVo> children = routes.stream().filter(item -> {
            if (Objects.equals(route.getId(), item.getParentId())) {
                childrenRoute(item, routes);
                return true;
            }
            return false;
        }).toList();
        route.setChildren(children);
        return route;
    }
}
