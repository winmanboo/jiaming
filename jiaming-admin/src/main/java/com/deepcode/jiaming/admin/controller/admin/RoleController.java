package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.dto.RoleDTO;
import com.deepcode.jiaming.admin.entity.Role;
import com.deepcode.jiaming.admin.mapping.RoleMapping;
import com.deepcode.jiaming.admin.service.RoleService;
import com.deepcode.jiaming.admin.vo.RoleVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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

    private final RoleMapping roleMapping;

    @GetMapping("/list")
    @ApiOperation(value = "角色列表", notes = "角色列表（不分页）")
    public Result<List<RoleVo>> list() {
        List<RoleVo> roleList = roleService.listVo();
        return Result.ok(roleList);
    }

    @GetMapping("/page")
    @ApiOperation(value = "角色分页信息", notes = "角色分页信息")
    public Result<PageList<RoleVo>> page(@Valid PageParam pageParam, RoleDTO roleDTO) {
        PageList<RoleVo> pageList = roleService.pageList(pageParam, roleDTO);
        return Result.ok(pageList);
    }

    @GetMapping("/info/{roleId}")
    @ApiOperation(value = "角色信息", notes = "根据 id 获取角色")
    public Result<RoleVo> info(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        RoleVo roleVo = roleMapping.toRoleVo(role);
        return Result.ok(roleVo);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增角色", notes = "新增角色")
    public Result<Void> add(@RequestBody RoleDTO roleDTO) {
        Role role = roleMapping.toRole(roleDTO);
        return Result.valid(roleService.save(role));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新角色", notes = "根据角色 id 更新角色")
    public Result<Void> update(@RequestBody RoleDTO roleDTO) {
        Role role = roleMapping.toRole(roleDTO);
        return Result.valid(roleService.updateById(role));
    }

    @DeleteMapping("/remove/{roleId}")
    @ApiOperation(value = "删除角色", notes = "根据 id 删除角色")
    public Result<Void> remove(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        if (Objects.isNull(role)) {
            throw new JiamingException("角色不存在");
        }
        return Result.valid(roleService.removeById(roleId));
    }
}
