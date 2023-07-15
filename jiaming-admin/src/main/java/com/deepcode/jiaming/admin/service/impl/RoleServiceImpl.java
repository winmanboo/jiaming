package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepcode.jiaming.admin.dto.RoleDTO;
import com.deepcode.jiaming.admin.entity.Role;
import com.deepcode.jiaming.admin.mapper.RoleMapper;
import com.deepcode.jiaming.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.vo.RoleVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public PageList<RoleVo> pageList(PageParam pageParam, RoleDTO roleDTO) {
        IPage<RoleVo> page = baseMapper.pageList(pageParam.toPage(), roleDTO);
        return PageList.turnTo(page);
    }
}
