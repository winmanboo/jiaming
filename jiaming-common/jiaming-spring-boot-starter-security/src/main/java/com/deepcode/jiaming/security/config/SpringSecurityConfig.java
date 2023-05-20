package com.deepcode.jiaming.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author winmanboo
 * @date 2023/5/20 22:20
 */
@AutoConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   ObjectMapper objectMapper,
                                                   MapperFacade mapperFacade,
                                                   TokenHolder tokenHolder,
                                                   AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(AuthUrl.FIND_USER, AuthUrl.LIST_PERMISSION, AuthUrl.LIST_ALL_PERMISSION).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new SecurityAuthenticationFilter(objectMapper, tokenHolder), UsernamePasswordAuthenticationFilter.class)
                // .addFilter(new SecurityLoginFilter(objectMapper, authenticationManager, mapperFacade, tokenHolder))
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl(objectMapper))
                .accessDeniedHandler(new AccessDeniedHandlerImpl(objectMapper))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(
                "/auth/login",
                "/admin/modeler/**",
                "/diagram-viewer/**",
                "/editor-app/**",
                "/*.html",
                "/favicon.ico",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/**",
                "/swagger-ui.html/**");
    }
}
