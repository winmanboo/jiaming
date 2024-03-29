package com.deepcode.jiaming.uaa.config;

import com.deepcode.jiaming.uaa.handler.AccessDeniedHandlerImpl;
import com.deepcode.jiaming.uaa.point.AuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author winmanboo
 * @date 2023/5/23 16:23
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class WebSecurityConfig implements WebMvcConfigurer {
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    private final AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, UserDetailsService userDetailsService) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf()
                .disable()
                .cors()
                .and()
                .formLogin()
                // .loginPage("/uaa/auth/login")
                // loginProcessingUrl 只是改变了登录认证的地址，并不代表认证交由你来处理，认证的流程还是 security 内部控制
                // .loginProcessingUrl("/uaa/auth/authenticate")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/uaa/jwk/**",
                        "/uaa/oauth2-client/**",
                        "/uaa/oauth2-token/**",
                        "/uaa/auth/captcha",
                        "/uaa/auth/code").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                // .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // FIXME: 2023/5/26 配置 /favicon.ico 请求时依然会走 filter
        return web -> web.ignoring().requestMatchers(
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
