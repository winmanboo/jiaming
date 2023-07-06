package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface TenantService extends IService<Tenant> {

    /**
     * 加载套餐绑定的菜单id
     *
     * @param tenantId 租户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> loadPackageMenuIds(Long tenantId);
}
