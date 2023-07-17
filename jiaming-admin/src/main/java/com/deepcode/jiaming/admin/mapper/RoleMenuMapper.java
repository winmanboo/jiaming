package com.deepcode.jiaming.admin.mapper;

import com.deepcode.jiaming.admin.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 角色关联的菜单 id 列表
     *
     * @param roleId 角色id
     * @return {@link List}<{@link Long}>
     */
    List<Long> assignMenuIdList(@Param("roleId") Long roleId);
}
