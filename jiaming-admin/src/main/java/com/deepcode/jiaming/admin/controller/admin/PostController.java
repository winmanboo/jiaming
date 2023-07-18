package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.dto.PostDTO;
import com.deepcode.jiaming.admin.entity.Post;
import com.deepcode.jiaming.admin.service.PostService;
import com.deepcode.jiaming.admin.vo.PostVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private final com.deepcode.jiaming.admin.mapping.PostMapping postMapping;

    @GetMapping("/list")
    @ApiOperation(value = "岗位列表", notes = "岗位列表")
    public Result<List<PostVo>> list() {
        List<PostVo> postList = postService.listVo();
        return Result.ok(postList);
    }

    @GetMapping("/info/{postId}")
    @ApiOperation(value = "获取岗位信息", notes = "根据 id 获取岗位信息")
    public Result<PostVo> info(@PathVariable Long postId) {
        Post post = postService.getById(postId);
        PostVo postVo = postMapping.toPostVo(post);
        return Result.ok(postVo);
    }

    @GetMapping("/page")
    @ApiOperation(value = "岗位列表（分页）", notes = "分页获取岗位列表")
    public Result<PageList<PostVo>> page(PageParam pageParam, PostDTO postDTO) {
        PageList<PostVo> pageList = postService.pageVo(pageParam, postDTO);
        return Result.ok(pageList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增岗位", notes = "新增岗位")
    public Result<Void> add(@Validated(AddGroup.class) @RequestBody PostDTO postDTO) {
        Post post = postMapping.toPost(postDTO);
        return Result.valid(postService.save(post));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新岗位", notes = "更新岗位")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody PostDTO postDTO) {
        Post post = postMapping.toPost(postDTO);
        return Result.valid(postService.updateById(post));
    }

    @DeleteMapping("/remove/{postId}")
    @ApiOperation(value = "删除岗位", notes = "根据 id 删除岗位")
    public Result<Void> remove(@PathVariable Long postId) {
        return Result.valid(postService.removeById(postId));
    }
}
