package com.deepcode.jiaming.utils;

import lombok.experimental.UtilityClass;

/**
 * 租户工具类
 *
 * @author winmanboo
 * @date 2023/7/5 21:27
 */
@UtilityClass
public class TenantUtil {
    /**
     * 是否是平台管理员
     *
     * @param tenantId 租户 id
     * @return true 是平台管理员
     */
    public static boolean isPlatformAdmin(Long tenantId) {
        return tenantId != null && tenantId == 0;
    }
}
