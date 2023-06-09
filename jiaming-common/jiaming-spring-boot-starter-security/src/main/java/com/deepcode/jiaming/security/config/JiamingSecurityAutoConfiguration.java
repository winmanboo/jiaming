package com.deepcode.jiaming.security.config;

/**
 * @author winmanboo
 * @date 2023/5/20 22:20
 */
/*@AutoConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(value = SecurityProperties.class)
@EnableMethodSecurity(prePostEnabled = true)
public class JiamingSecurityAutoConfiguration implements ApplicationContextAware {

    private final SecurityProperties securityProperties;

    private ApplicationContext applicationContext;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    *//*@Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(securityService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }*//*

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        Multimap<HttpMethod, String> ignoreAuthMap = getIgnoreAuthAnno();
        return httpSecurity.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(securityProperties.getWhiteList().toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.GET, ignoreAuthMap.get(HttpMethod.GET).toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.POST, ignoreAuthMap.get(HttpMethod.POST).toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.DELETE, ignoreAuthMap.get(HttpMethod.DELETE).toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.PUT, ignoreAuthMap.get(HttpMethod.PUT).toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(securityProperties.getTokenHeader()),
                        UsernamePasswordAuthenticationFilter.class)
                // .addFilter(new SecurityLoginFilter(objectMapper, authenticationManager, mapperFacade, tokenHolder))
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
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
}*/
