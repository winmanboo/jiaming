package com.deepcode.jiaming.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepcode.jiaming.admin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepcode.jiaming.admin.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户列表
     *
     * @param tenantId 租户 id
     * @return {@link List}<{@link UserVo}>
     */
    IPage<UserVo> pageList(@Param("page") IPage<UserVo> page, @Param("tenantId") Long tenantId);
}
