package com.deepcode.jiaming.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.admin.dto.UserDTO;
import com.deepcode.jiaming.admin.entity.User;
import com.deepcode.jiaming.admin.vo.UserVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;

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
     * 页面列表
     *
     * @param pageParam 页面参数
     * @param userDTO   用户dto
     * @return {@link PageList}<{@link UserVo}>
     */
    PageList<UserVo> pageList(PageParam pageParam, UserDTO userDTO);

    /**
     * 改变用户状态
     *
     * @param id     id
     * @param status 状态
     */
    void changeUserStatus(Long id, Integer status);

    /**
     * 添加用户
     *
     * @param userDTO 用户dto
     */
    boolean addUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param userDTO 用户dto
     * @return boolean
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return {@link UserVo}
     */
    UserVo getUserInfo(Long userId);

    /**
     * 重置密码
     *
     * @param userId   用户id
     * @param password 密码
     * @return boolean
     */
    boolean resetPassword(Long userId, String password);
}
