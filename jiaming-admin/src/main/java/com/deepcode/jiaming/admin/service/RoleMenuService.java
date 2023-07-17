package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.dto.RoleMenuAssignDTO;
import com.deepcode.jiaming.admin.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 角色关联的菜单 id 列表
     *
     * @param roleId 角色id
     * @return {@link List}<{@link Long}>
     */
    List<Long> assignMenuIdList(Long roleId);

    /**
     * 分配菜单
     *
     * @param roleMenuAssignDTO 角色菜单分配dto
     */
    void assignMenu(RoleMenuAssignDTO roleMenuAssignDTO);
}
