package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.admin.vo.DeptVo;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface DeptService extends IService<Dept> {

    /**
     * 部门列表
     *
     * @return {@link List}<{@link DeptVo}>
     */
    List<DeptVo> listDeptVo();
}
