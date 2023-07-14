package com.deepcode.jiaming.admin.dto;

import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.StatusGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author winmanboo
 * @date 2023/7/14 13:27
 */
@Data
public class UserDTO {
    @ApiModelProperty("用户编号")
    @NotNull(message = "用户编号不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空", groups = AddGroup.class)
    @Size(message = "用户名长度 3-15 个字符", groups = AddGroup.class, min = 3, max = 15)
    private String username;

    @ApiModelProperty("用户昵称")
    @NotEmpty(message = "用户昵称不能为空", groups = AddGroup.class)
    private String nickname;

    @ApiModelProperty("归属部门 id")
    private Long deptId;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户密码")
    @NotEmpty(message = "用户密码不能为空", groups = AddGroup.class)
    @Size(message = "用户密码长度不能小于 6 个字符", groups = AddGroup.class, min = 6)
    private String password;

    @ApiModelProperty("用户性别 0：女 1：男")
    private Integer sex;

    @ApiModelProperty("岗位 id 列表")
    private List<Long> postIds;

    @ApiModelProperty("状态")
    @NotNull(message = "用户状态不能为空", groups = StatusGroup.class)
    private Integer status;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("备注")
    private String remark;
}
