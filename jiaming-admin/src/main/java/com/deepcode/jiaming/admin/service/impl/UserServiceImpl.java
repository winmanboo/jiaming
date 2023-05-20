package com.deepcode.jiaming.admin.service.impl;

import com.deepcode.jiaming.admin.entity.User;
import com.deepcode.jiaming.admin.mapper.UserMapper;
import com.deepcode.jiaming.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
