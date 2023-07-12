package com.deepcode.jiaming.admin.service.impl;

import com.deepcode.jiaming.admin.entity.Dept;
import com.deepcode.jiaming.admin.mapper.DeptMapper;
import com.deepcode.jiaming.admin.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.utils.DeptHelper;
import com.deepcode.jiaming.admin.vo.DeptVo;
import com.deepcode.jiaming.security.context.UserInfoContext;
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
    public List<DeptVo> listDeptVo() {
        Long tenantId = UserInfoContext.get().getTenantId();
        List<DeptVo> deptVoList = baseMapper.listDeptVo(tenantId);
        return DeptHelper.generateDeptTree(deptVoList);
    }
}
