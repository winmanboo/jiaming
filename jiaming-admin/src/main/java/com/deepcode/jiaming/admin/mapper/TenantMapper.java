package com.deepcode.jiaming.admin.mapper;

import com.deepcode.jiaming.admin.entity.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 租户表 Mapper 接口
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 加载套餐绑定的菜单id
     *
     * @param tenantId 租户id
     * @return {@link String}
     */
    String loadPackageMenuIds(@Param("tenantId") Long tenantId);
}
