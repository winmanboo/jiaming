package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.dto.RoleMenuAssignDTO;
import com.deepcode.jiaming.admin.entity.RoleMenu;
import com.deepcode.jiaming.admin.mapper.RoleMenuMapper;
import com.deepcode.jiaming.admin.service.RoleMenuService;
import com.deepcode.jiaming.exception.JiamingException;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {


    @Override
    public List<Long> assignMenuIdList(Long roleId) {
        if (Objects.isNull(roleId)) {
            throw new JiamingException("角色 Id 不能为空");
        }
        return baseMapper.assignMenuIdList(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenu(RoleMenuAssignDTO roleMenuAssignDTO) {
        lambdaUpdate().eq(RoleMenu::getRoleId, roleMenuAssignDTO.getRoleId()).remove();

        List<RoleMenu> roleMenuList = roleMenuAssignDTO.getMenuIds().stream().map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleMenuAssignDTO.getRoleId());
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).toList();

        ((RoleMenuServiceImpl) AopContext.currentProxy()).saveBatch(roleMenuList);
    }
}
