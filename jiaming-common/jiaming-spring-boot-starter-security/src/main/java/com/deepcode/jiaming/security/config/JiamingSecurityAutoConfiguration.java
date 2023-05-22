package com.deepcode.jiaming.security.config;

import com.deepcode.jiaming.security.annotation.IgnoreAuth;
import com.deepcode.jiaming.security.exception.AccessDeniedHandlerImpl;
import com.deepcode.jiaming.security.exception.AuthenticationEntryPointImpl;
import com.deepcode.jiaming.security.filter.TokenAuthenticationFilter;
import com.deepcode.jiaming.security.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;


/**
 * @author winmanboo
 * @date 2023/5/20 22:20
 */
@AutoConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JiamingSecurityAutoConfiguration implements ApplicationContextAware {
    private final UserDetailsService userDetailsService;

    private final SecurityProperties securityProperties;

    private ApplicationContext applicationContext;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, ObjectMapper objectMapper,
                                                   TokenAuthenticationFilter tokenAuthenticationFilter) throws Exception {
        Multimap<HttpMethod, String> ignoreAuthMap = getIgnoreAuthAnno();
        return httpSecurity.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getWhiteList().toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.GET, ignoreAuthMap.get(HttpMethod.GET).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.POST, ignoreAuthMap.get(HttpMethod.POST).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.DELETE, ignoreAuthMap.get(HttpMethod.DELETE).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PUT, ignoreAuthMap.get(HttpMethod.PUT).toArray(new String[0])).permitAll()
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

    private Multimap<HttpMethod, String> getIgnoreAuthAnno() {
        Multimap<HttpMethod, String> result = HashMultimap.create();
        // 获取接口对应的 HandlerMethod 集合
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        // 获取带有 @IgnoreAuth 的接口
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            PatternsRequestCondition patternsCondition = entry.getKey().getPatternsCondition();
            if (!handlerMethod.hasMethodAnnotation(IgnoreAuth.class) || patternsCondition == null) {
                continue;
            }
            Set<String> urls = patternsCondition.getPatterns();
            // 根据请求方法，添加到 result
            entry.getKey().getMethodsCondition().getMethods().forEach(requestMethod -> {
                switch (requestMethod) {
                    case GET:
                        result.putAll(HttpMethod.GET, urls);
                        break;
                    case POST:
                        result.putAll(HttpMethod.POST, urls);
                        break;
                    case PUT:
                        result.putAll(HttpMethod.PUT, urls);
                        break;
                    case DELETE:
                        result.putAll(HttpMethod.DELETE, urls);
                        break;
                    default:
                        break;
                }
            });
        }
        return result;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
