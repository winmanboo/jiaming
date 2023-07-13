package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.entity.User;
import com.deepcode.jiaming.admin.mapper.UserMapper;
import com.deepcode.jiaming.admin.mapping.UserMapping;
import com.deepcode.jiaming.admin.service.UserService;
import com.deepcode.jiaming.admin.vo.UserVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.security.context.UserInfoContext;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UserVo getCurrentUserInfo() {
        Long userId = UserInfoContext.get().getUserId();
        User user = lambdaQuery().eq(Objects.nonNull(userId), User::getId, userId).one();
        return userMapping.toUserVo(user);
    }

    @Override
    public PageList<UserVo> pageList(PageParam pageParam) {
        Long tenantId = UserInfoContext.get().getTenantId();
        IPage<UserVo> page = baseMapper.pageList(pageParam.toPage(), tenantId);
        return PageList.turnTo(page);
    }
}
