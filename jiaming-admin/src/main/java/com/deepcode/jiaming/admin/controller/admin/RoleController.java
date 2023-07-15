package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.dto.RoleDTO;
import com.deepcode.jiaming.admin.service.RoleService;
import com.deepcode.jiaming.admin.vo.RoleVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @GetMapping("/page")
    @ApiOperation(value = "角色分页信息", notes = "角色分页信息")
    public Result<PageList<RoleVo>> page(@Valid PageParam pageParam, RoleDTO roleDTO) {
        PageList<RoleVo> pageList = roleService.pageList(pageParam, roleDTO);
        return Result.ok(pageList);
    }
}
