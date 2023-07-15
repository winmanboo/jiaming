package com.deepcode.jiaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author winmanboo
 * @date 2023/5/20 21:15
 */
@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients(basePackages = "com.deepcode.jiaming.admin.feign")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
