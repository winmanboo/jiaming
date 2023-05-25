package com.deepcode.jiaming.admin.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author winmanboo
 * @date 2023/5/23 16:35
 */
@RestController
@RequestMapping("/admin")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
