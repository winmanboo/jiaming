package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.admin.vo.UserVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface UserService extends IService<User> {

    /**
     * 得到当前登录用户的信息
     *
     * @return {@link UserVo}
     */
    UserVo getCurrentUserInfo();

    /**
     * 用户列表
     *
     * @return {@link List}<{@link UserVo}>
     */
    PageList<UserVo> pageList(PageParam pageParam);
}
