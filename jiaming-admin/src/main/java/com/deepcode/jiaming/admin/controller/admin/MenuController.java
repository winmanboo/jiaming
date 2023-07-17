package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.dto.MenuDTO;
import com.deepcode.jiaming.admin.dto.RoleMenuAssignDTO;
import com.deepcode.jiaming.admin.entity.Menu;
import com.deepcode.jiaming.admin.mapping.MenuMapping;
import com.deepcode.jiaming.admin.service.MenuService;
import com.deepcode.jiaming.admin.service.RoleMenuService;
import com.deepcode.jiaming.admin.vo.RouteVo;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private final RoleMenuService roleMenuService;

    private final MenuMapping menuMapping;

    @GetMapping("/route")
    @ApiOperation(value = "路由菜单", notes = "获取当前用户可用的菜单资源，携带租户 id")
    public Result<List<RouteVo>> route(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer enable) {
        List<RouteVo> routes = menuService.loadRouteList(name, enable);
        return Result.ok(routes);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    public Result<Void> add(@RequestBody MenuDTO menuDTO) {
        Menu menu = menuMapping.toMenu(menuDTO);
        menu.setTitle(menuDTO.getName());
        if (menu.getParentId() == 0) {
            menu.setComponent("Layout");
        }
        return Result.valid(menuService.save(menu));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新菜单", notes = "根据菜单 id 更新菜单")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody MenuDTO menuDTO) {
        Menu menu = menuMapping.toMenu(menuDTO);
        menu.setTitle(menuDTO.getName());
        return Result.valid(menuService.updateById(menu));
    }

    @GetMapping("/info/{menuId}")
    @ApiOperation(value = "获取菜单信息", notes = "根据 id 获取菜单信息")
    public Result<RouteVo> info(@PathVariable Long menuId) {
        Menu menu = menuService.getById(menuId);
        RouteVo routeVo = menuMapping.toRouteVo(menu);
        return Result.ok(routeVo);
    }

    @DeleteMapping("/remove/{menuId}")
    @ApiOperation(value = "删除菜单", notes = "根据 id 删除菜单")
    public Result<Void> remove(@PathVariable Long menuId) {
        return Result.valid(menuService.removeById(menuId));
    }

    @GetMapping("/assign_list/{roleId}")
    @ApiOperation(value = "角色关联的菜单 id 列表", notes = "角色关联的菜单 id 列表")
    public Result<List<Long>> assignList(@PathVariable Long roleId) {
        return Result.ok(roleMenuService.assignMenuIdList(roleId));
    }

    @PostMapping("/assign_menu")
    @ApiOperation(value = "角色关联菜单", notes = "角色关联菜单")
    public Result<Void> assignMenu(@RequestBody RoleMenuAssignDTO roleMenuAssignDTO) {
        roleMenuService.assignMenu(roleMenuAssignDTO);
        return Result.ok();
    }
}
