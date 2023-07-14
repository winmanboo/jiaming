package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.service.PostService;
import com.deepcode.jiaming.admin.vo.PostVo;
import com.deepcode.jiaming.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 岗位表 前端控制器
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "岗位列表", notes = "岗位列表")
    public Result<List<PostVo>> list() {
        List<PostVo> postList = postService.listVo();
        return Result.ok(postList);
    }
}
