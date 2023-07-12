package com.deepcode.jiaming.admin.mapper;

import com.deepcode.jiaming.admin.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepcode.jiaming.admin.vo.DeptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 部门列表
     *
     * @param tenantId 租户 id
     * @return {@link List}<{@link DeptVo}>
     */
    List<DeptVo> listDeptVo(@Param("tenantId") Long tenantId);
}
