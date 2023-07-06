package com.deepcode.jiaming.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author winmanboo
 * @date 2023/5/22 12:46
 */
@Data
@Builder
public class UserInfo {
    /**
     * 租户 id
     */
    private Long tenantId;

    /**
     * 权限（角色）
     */
    private Set<String> authorities;

    /**
     * 是否是管理员
     */
    private Boolean isAdmin;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;
}
