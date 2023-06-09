package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.service.MenuService;
import com.deepcode.jiaming.admin.vo.RouteVo;
import com.deepcode.jiaming.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单 前端控制器
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/route")
    @ApiOperation(value = "路由菜单", notes = "获取当前用户可用的菜单资源，携带租户 id")
    public Result<List<RouteVo>> route(@RequestParam("tenantId") Integer tenantId) {

        return Result.ok();
    }
}
