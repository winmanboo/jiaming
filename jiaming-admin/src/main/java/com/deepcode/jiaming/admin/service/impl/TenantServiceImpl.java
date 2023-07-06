package com.deepcode.jiaming.admin.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.admin.entity.Tenant;
import com.deepcode.jiaming.admin.mapper.TenantMapper;
import com.deepcode.jiaming.admin.service.TenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Override
    public List<Long> loadPackageMenuIds(Long tenantId) {
        String menuIds = baseMapper.loadPackageMenuIds(tenantId);
        if (CharSequenceUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        return Arrays.stream(menuIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
    }
}
