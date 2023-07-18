package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.dto.DeptDTO;
import com.deepcode.jiaming.admin.entity.Dept;
import com.deepcode.jiaming.admin.mapper.DeptMapper;
import com.deepcode.jiaming.admin.service.DeptService;
import com.deepcode.jiaming.admin.utils.DeptHelper;
import com.deepcode.jiaming.admin.vo.DeptVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<DeptVo> listDeptVo(DeptDTO deptDTO) {
        List<DeptVo> deptVoList = baseMapper.listDeptVo(deptDTO);
        List<DeptVo> tree = DeptHelper.generateDeptTree(deptVoList);
        return tree.isEmpty() ? deptVoList : tree;
    }
}
