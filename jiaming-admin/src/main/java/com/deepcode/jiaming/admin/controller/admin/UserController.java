package com.deepcode.jiaming.admin.controller.admin;

import com.deepcode.jiaming.admin.dto.UserDTO;
import com.deepcode.jiaming.admin.service.UserService;
import com.deepcode.jiaming.admin.vo.UserVo;
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
 * 系统用户 前端控制器
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {
    private final UserService userService;

    // FIXME: 2023/7/5 使用 OIDC 获取用户信息：https://docs.spring.io/spring-authorization-server/docs/1.0.3/reference/html/guides/how-to-userinfo.html
    //  并且必须在 uaa 服务下
    @GetMapping("/info")
    @ApiOperation(value = "用户信息", notes = "获取当前登录的用户信息")
    public Result<UserVo> userInfo() {
        UserVo userVo = userService.getCurrentUserInfo();
        return Result.ok(userVo);
    }

    @GetMapping("/page")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public Result<PageList<UserVo>> page(@Valid PageParam pageParam, UserDTO userDTO) {
        PageList<UserVo> pageList = userService.pageList(pageParam, userDTO);
        return Result.ok(pageList);
    }
}
