package com.deepcode.jiaming.admin.vo;

import com.deepcode.jiaming.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author winmanboo
 * @date 2023/7/5 13:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends BaseEntity {
    @ApiModelProperty("用户编号")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("岗位id数组，逗号分隔")
    private String postIds;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("联系方式")
    private String mobile;

    @ApiModelProperty("用户性别 0:女 1:男")
    private Integer sex;

    @ApiModelProperty("用户头像地址")
    private String avatar;

    @ApiModelProperty("账号状态 0:停用 1:正常")
    private Integer status;

    @ApiModelProperty("所属部门名")
    private String deptName;
}
