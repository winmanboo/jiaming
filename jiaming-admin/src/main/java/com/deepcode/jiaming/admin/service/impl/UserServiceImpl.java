package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.dto.UserDTO;
import com.deepcode.jiaming.admin.entity.User;
import com.deepcode.jiaming.admin.mapper.UserMapper;
import com.deepcode.jiaming.admin.mapping.UserMapping;
import com.deepcode.jiaming.admin.service.UserService;
import com.deepcode.jiaming.admin.vo.UserVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.security.context.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapping userMapping;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public PageList<UserVo> pageList(PageParam pageParam, UserDTO userDTO) {
        Long tenantId = UserInfoContext.get().getTenantId();
        IPage<UserVo> page = baseMapper.pageList(pageParam.toPage(), tenantId, userDTO);
        return PageList.turnTo(page);
    }

    @Override
    public void changeUserStatus(Long id, Integer status) {
        lambdaUpdate().eq(User::getId, id).set(User::getStatus, status).update();
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        User dbUser = lambdaQuery().eq(User::getUsername, userDTO.getUsername()).one();
        if (Objects.nonNull(dbUser)) {
            throw new JiamingException("用户名已存在");
        }

        User user = userMapping.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreator(UserInfoContext.get().getUsername());
        user.setTenantId(UserInfoContext.get().getTenantId());

        return this.save(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User user = userMapping.toUser(userDTO);
        return lambdaUpdate().eq(User::getId, userDTO.getId())
                .set(User::getUpdater, UserInfoContext.get().getUsername())
                .update(user);
    }

    @Override
    public UserVo getUserInfo(Long userId) {
        if (Objects.isNull(userId)) {
            throw new JiamingException("用户 id 不能为空");
        }
        return baseMapper.getUserInfo(userId);
    }

    @Override
    public boolean resetPassword(Long userId, String password) {
        User user = this.getById(userId);
        if (Objects.isNull(user)) {
            throw new JiamingException("用户不存在");
        }
        return lambdaUpdate().eq(User::getId, userId).set(User::getPassword, passwordEncoder.encode(password)).update();
    }
}
