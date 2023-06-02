package com.deepcode.jiaming.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepcode.jiaming.uaa.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author winmanboo
 * @date 2023/6/1 21:00
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id和租户id查询用户角色信息
     *
     * @param userId   用户id
     * @param tenantId 租户id
     * @return 角色列表
     */
    List<Role> loadRolesByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);
}
