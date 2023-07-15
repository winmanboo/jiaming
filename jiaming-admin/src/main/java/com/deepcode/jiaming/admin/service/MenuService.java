package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.admin.vo.RouteVo;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface MenuService extends IService<Menu> {

    /**
     * 加载当前用户可见的路由列表
     *
     * @param name   菜单名称
     * @param enable 状态
     * @return 当前用户可见的路由
     */
    List<RouteVo> loadRouteList(String name, Integer enable);
}
