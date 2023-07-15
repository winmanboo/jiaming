package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.dto.UserRoleDTO;
import com.deepcode.jiaming.admin.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户与角色关联表 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 分配角色
     *
     * @param userRoleDTO 用户角色dto
     */
    void assign(UserRoleDTO userRoleDTO);
}
