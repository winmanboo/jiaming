package com.deepcode.jiaming.security.config;

import com.deepcode.jiaming.security.exception.AccessDeniedHandlerImpl;
import com.deepcode.jiaming.security.exception.AuthenticationEntryPointImpl;
import com.deepcode.jiaming.security.filter.TokenAuthenticationFilter;
import com.deepcode.jiaming.security.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author winmanboo
 * @date 2023/5/20 22:20
 */
@AutoConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JiamingSecurityAutoConfiguration {
    private final UserDetailsService userDetailsService;

    private final SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(securityProperties);
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
                                                   TokenAuthenticationFilter tokenAuthenticationFilter,
                                                   AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getWhiteList().toArray(new String[]{})).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
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
