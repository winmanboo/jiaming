package com.deepcode.jiaming.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.uaa.entity.User;

/**
 * @author winmanboo
 * @date 2023/6/1 20:40
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User loadUserByUsername(String username);
}
