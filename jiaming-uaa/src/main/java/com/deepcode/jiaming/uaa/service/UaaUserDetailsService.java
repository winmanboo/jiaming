package com.deepcode.jiaming.uaa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author winmanboo
 * @date 2023/5/23 14:56
 */
@Component
@RequiredArgsConstructor
public class UaaUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername("winmanboo").password(passwordEncoder.encode("123")).authorities("ADMIN").build();
    }
}
