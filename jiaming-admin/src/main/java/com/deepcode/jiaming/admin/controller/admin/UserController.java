package com.deepcode.jiaming.admin.controller.admin;

import cn.hutool.core.text.CharSequenceUtil;
import com.deepcode.jiaming.admin.dto.UserDTO;
import com.deepcode.jiaming.admin.service.UserService;
import com.deepcode.jiaming.admin.vo.UserVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.security.context.UserInfoContext;
import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.StatusGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
        UserVo userVo = userService.getUserInfo(UserInfoContext.get().getUserId());
        return Result.ok(userVo);
    }

    @GetMapping("/userinfo/{userId}")
    @ApiOperation(value = "获取用户信息", notes = "根据用户 id 获取用户信息")
    public Result<UserVo> userInfoByUserId(@PathVariable("userId") Long userId) {
        UserVo userVo = userService.getUserInfo(userId);
        return Result.ok(userVo);
    }

    @GetMapping("/page")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public Result<PageList<UserVo>> page(@Valid PageParam pageParam, UserDTO userDTO) {
        PageList<UserVo> pageList = userService.pageList(pageParam, userDTO);
        return Result.ok(pageList);
    }

    @GetMapping("/leader_list")
    @ApiOperation(value = "负责人列表", notes = "负责人列表")
    public Result<List<UserVo>> list() {
        List<UserVo> userList = userService.leaderList();
        return Result.ok(userList);
    }

    @PostMapping("/status")
    @ApiOperation(value = "改变用户状态", notes = "改变用户状态")
    public Result<Void> status(@Validated(value = {UpdateGroup.class, StatusGroup.class}) @RequestBody UserDTO userDTO) {
        userService.changeUserStatus(userDTO.getId(), userDTO.getStatus());
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public Result<Void> update(@Validated(value = UpdateGroup.class) @RequestBody UserDTO userDTO) {
        return Result.valid(userService.updateUser(userDTO));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public Result<Void> add(@Validated(value = AddGroup.class) @RequestBody UserDTO userDTO) {
        return Result.valid(userService.addUser(userDTO));
    }

    @DeleteMapping("/remove/{userId}")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public Result<Void> remove(@PathVariable Long userId) {
        return Result.valid(userService.removeById(userId));
    }

    @PutMapping("/reset")
    @ApiOperation(value = "重置密码", notes = "根据用户 id 重置密码")
    public Result<Void> reset(@RequestBody UserDTO userDTO) {
        if (Objects.isNull(userDTO.getId())) {
            throw new JiamingException("用户 id 不能为空");
        }
        if (Objects.isNull(userDTO.getPassword()) || CharSequenceUtil.isEmpty(userDTO.getPassword())) {
            throw new JiamingException("密码不能为空");
        }
        if (userDTO.getPassword().length() < 6) {
            throw new JiamingException("密码长度必须大于等于 6");
        }
        return Result.valid(userService.resetPassword(userDTO.getId(), userDTO.getPassword()));
    }
}
