package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.dto.UserRoleDTO;
import com.deepcode.jiaming.admin.entity.UserRole;
import com.deepcode.jiaming.admin.mapper.UserRoleMapper;
import com.deepcode.jiaming.admin.service.UserRoleService;
import com.deepcode.jiaming.security.context.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户与角色关联表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assign(UserRoleDTO userRoleDTO) {
        lambdaUpdate().eq(UserRole::getUserId, userRoleDTO.getUserId()).remove();

        Long tenantId = UserInfoContext.get().getTenantId();
        String creator = UserInfoContext.get().getUsername();
        List<UserRole> userRoleList = userRoleDTO.getRoleIds().stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userRoleDTO.getUserId());
            userRole.setRoleId(roleId);
            userRole.setTenantId(tenantId);
            userRole.setCreator(creator);
            return userRole;
        }).toList();

        ((UserRoleServiceImpl) AopContext.currentProxy()).saveBatch(userRoleList);
    }
}
