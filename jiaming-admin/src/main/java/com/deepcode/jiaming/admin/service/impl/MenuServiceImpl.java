package com.deepcode.jiaming.admin.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.entity.Menu;
import com.deepcode.jiaming.admin.mapper.MenuMapper;
import com.deepcode.jiaming.admin.service.MenuService;
import com.deepcode.jiaming.admin.service.TenantService;
import com.deepcode.jiaming.admin.utils.RouteHelper;
import com.deepcode.jiaming.admin.vo.RouteMetaVo;
import com.deepcode.jiaming.admin.vo.RouteVo;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.security.context.UserInfoContext;
import com.deepcode.jiaming.security.domain.UserInfo;
import com.deepcode.jiaming.utils.BooleanNumUtil;
import com.deepcode.jiaming.utils.SystemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    private final TenantService tenantService;

    @Override
    public List<RouteVo> loadRouteList(String name, Integer enable) {
        UserInfo userInfo = UserInfoContext.get();
        Long tenantId = userInfo.getTenantId();

        List<Menu> menus; // 菜单权限
        if (SystemUtil.isPlatformAdmin(tenantId)) { // 如果是平台管理员获取全量菜单
            LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(Objects.nonNull(name) && CharSequenceUtil.isNotEmpty(name),
                                Menu::getName, name)
                        .eq(Objects.nonNull(enable), Menu::getEnable, enable);
            menus = list(queryWrapper);
        } else { // 如果不是，获取用户可见菜单
            List<Long> menuIds = tenantService.loadPackageMenuIds(tenantId);
            if (Boolean.TRUE.equals(userInfo.getIsAdmin())) { // 如果是租户管理员，则查出租户可见的全部菜单
                menus = baseMapper.loadMenuListByMenuIds(menuIds, name, enable);
            } else { // 如果不是租户管理员，只是租户的用户，根据用户角色拿到可见的菜单
                menus = baseMapper.loadMenuListByUserId(userInfo.getUserId(), name, enable);
                boolean match = menus.stream().map(Menu::getId).allMatch(menuIds::contains);
                if (!match) {
                    throw new JiamingException("菜单数据异常");
                }
            }
        }

        List<RouteVo> routes = menus.stream().map(menu -> {
            RouteVo route = new RouteVo();
            route.setType(menu.getType());
            route.setIcon(menu.getIcon());
            route.setPermission(menu.getPermission());
            route.setEnable(menu.getEnable());
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
        }).toList();

        List<RouteVo> tree = RouteHelper.generateRouteTree(routes);
        return tree.isEmpty() ? routes : tree;
    }
}
