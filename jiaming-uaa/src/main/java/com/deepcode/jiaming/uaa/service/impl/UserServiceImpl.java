package com.deepcode.jiaming.uaa.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.uaa.entity.User;
import com.deepcode.jiaming.uaa.mapper.UserMapper;
import com.deepcode.jiaming.uaa.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author winmanboo
 * @date 2023/6/1 20:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User loadUserByUsername(String username) {
        verityUsername(username);

        return lambdaQuery().eq(User::getUsername, username)
                .ne(User::getDeleted, 1)
                .one();
    }

    private void verityUsername(String username) {
        // 校验用户名
        if (CharSequenceUtil.isEmpty(username)) {
            throw new JiamingException("用户名不能为空");
        }
        // ...
    }
}
