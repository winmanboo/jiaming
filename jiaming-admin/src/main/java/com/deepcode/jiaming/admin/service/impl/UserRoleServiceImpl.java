package com.deepcode.jiaming.admin.service.impl;

import com.deepcode.jiaming.admin.entity.UserRole;
import com.deepcode.jiaming.admin.mapper.UserRoleMapper;
import com.deepcode.jiaming.admin.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色关联表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
