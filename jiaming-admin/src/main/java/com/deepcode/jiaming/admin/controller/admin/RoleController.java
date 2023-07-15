package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统角色 前端控制器
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/role")
public class RoleController {
    private final RoleService roleService;


}
