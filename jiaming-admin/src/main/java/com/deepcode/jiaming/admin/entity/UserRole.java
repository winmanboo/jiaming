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
 * 用户与角色关联表
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserRole对象", description = "用户与角色关联表")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户与角色关联id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;

    @ApiModelProperty("租户id")
    private Long tenantId;
}
