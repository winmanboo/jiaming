package com.deepcode.jiaming.uaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.uaa.entity.Role;
import com.deepcode.jiaming.uaa.mapper.RoleMapper;
import com.deepcode.jiaming.uaa.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author winmanboo
 * @date 2023/6/1 21:00
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<Role> loadAllRoles(Long tenantId) {
        return lambdaQuery().eq(Objects.nonNull(tenantId), Role::getTenantId, tenantId).list();
    }

    @Override
    public List<Role> loadRolesByUserIdAndTenantId(Long userId, Long tenantId) {
        return baseMapper.loadRolesByUserIdAndTenantId(userId, tenantId);
    }
}
