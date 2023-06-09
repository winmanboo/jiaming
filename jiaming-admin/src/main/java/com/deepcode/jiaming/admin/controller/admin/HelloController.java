package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.security.context.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author winmanboo
 * @date 2023/5/23 16:35
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return UserInfoContext.get().getUsername();
    }
}