package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.service.DeptService;
import com.deepcode.jiaming.admin.vo.DeptVo;
import com.deepcode.jiaming.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/dept")
public class DeptController {

    private final DeptService deptService;

    @GetMapping("/list")
    @ApiOperation(value = "部门列表", notes = "部门列表（树型结构）")
    public Result<List<DeptVo>> depts() {
        List<DeptVo> depts = deptService.listDeptVo();
        return Result.ok(depts);
    }
}
