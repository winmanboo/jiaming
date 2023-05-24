package com.deepcode.jiaming.security.service;

import com.deepcode.jiaming.security.domain.SecurityUserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author winmanboo
 * @date 2023/5/22 12:38
 */
public interface SecurityService extends UserDetailsService {
    /**
     * 根据 token 获取用户信息
     *
     * @param token token
     * @return 用户信息
     * @see SecurityUserInfo
     */
    SecurityUserInfo getUserInfoByToken(String token);
}
