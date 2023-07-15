package com.deepcode.jiaming.admin.mapper;

import com.deepcode.jiaming.admin.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据菜单id列表查询菜单列表
     *
     * @param menuIds 菜单id 列表
     * @param name    菜单名称
     * @param enable  菜单状态
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> loadMenuListByMenuIds(@Param("menuIds") List<Long> menuIds, @Param("name") String name, @Param("enable") Integer enable);

    /**
     * 通过用户id加载菜单列表
     *
     * @param userId 用户id
     * @param name   菜单名称
     * @param enable 菜单状态
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> loadMenuListByUserId(@Param("userId") Long userId, @Param("name") String name, @Param("enable") Integer enable);
}
