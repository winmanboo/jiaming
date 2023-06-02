package com.deepcode.jiaming.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.uaa.entity.Role;

import java.util.List;

/**
 * @author winmanboo
 * @date 2023/6/1 21:00
 */
public interface RoleService extends IService<Role> {
    /**
     * 查询所有的角色信息
     *
     * @param tenantId 租户id
     * @return 角色列表
     */
    List<Role> loadAllRoles(Long tenantId);

    /**
     * 查询用户的角色信息
     *
     * @param userId   用户id
     * @param tenantId 租户id
     * @return 角色列表
     */
    List<Role> loadRolesByUserIdAndTenantId(Long userId, Long tenantId);
}
