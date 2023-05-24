package com.deepcode.jiaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author winmanboo
 * @date 2023/5/20 21:14
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.deepcode.jiaming.feign")
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
