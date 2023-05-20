package com.deepcode.jiaming.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.deepcode.jiaming.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User对象", description = "系统用户")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("系统用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("备注")
    private String remark;

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

    @ApiModelProperty("登录ip")
    private String loginIp;

    @ApiModelProperty("登录时间")
    private LocalDateTime loginDate;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;

    @ApiModelProperty("租户id，如果为0则为平台用户")
    private Long tenantId;
}
