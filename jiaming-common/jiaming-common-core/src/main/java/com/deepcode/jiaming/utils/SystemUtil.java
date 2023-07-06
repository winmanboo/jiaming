package com.deepcode.jiaming.utils;

import lombok.experimental.UtilityClass;

/**
 * @author winmanboo
 * @date 2023/7/6 11:46
 */
@UtilityClass
public class SystemUtil {
    /**
     * 是否是管理员
     *
     * @param adminFlag 管理员标识
     * @return boolean
     */
    public static boolean isAdmin(Integer adminFlag) {
        return adminFlag != null && adminFlag == 1;
    }

    /**
     * 是否是平台管理员
     *
     * @param tenantId 租户 id
     * @return boolean
     */
    public static boolean isPlatformAdmin(Long tenantId) {
        return tenantId != null && tenantId == 0;
    }
}
