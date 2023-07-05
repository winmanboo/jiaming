package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.entity.Menu;
import com.deepcode.jiaming.admin.mapper.MenuMapper;
import com.deepcode.jiaming.admin.service.MenuService;
import com.deepcode.jiaming.admin.vo.RouteMetaVo;
import com.deepcode.jiaming.admin.vo.RouteVo;
import com.deepcode.jiaming.utils.BooleanNumUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    // @Cacheable(cacheNames = CacheConstants.ROUTE_CACHE_NAME, key = "#root.method")
    public List<RouteVo> loadRouteList() {
        List<Menu> menus = list();

        return menus.stream().map(menu -> {
            RouteVo route = new RouteVo();
            route.setAlwaysShow(BooleanNumUtil.isTrue(menu.getAlwaysShow()));
            route.setComponent(menu.getComponent());
            route.setHidden(BooleanNumUtil.isTrue(menu.getHidden()));
            route.setName(menu.getName());
            route.setPath(menu.getPath());
            route.setRedirect(menu.getRedirect());
            route.setId(menu.getId());
            route.setParentId(menu.getParentId());

            RouteMetaVo meta = new RouteMetaVo();
            meta.setActiveMenu(menu.getActiveMenu());
            meta.setAffix(BooleanNumUtil.isTrue(menu.getAffix()));
            meta.setBreadcrumb(BooleanNumUtil.isTrue(menu.getBreadcrumb()));
            meta.setIcon(menu.getIcon());
            meta.setNoCache(BooleanNumUtil.isTrue(menu.getNoCache()));
            meta.setTitle(menu.getTitle());

            route.setMeta(meta);
            return route;
        }).collect(Collectors.toList());
    }
}
