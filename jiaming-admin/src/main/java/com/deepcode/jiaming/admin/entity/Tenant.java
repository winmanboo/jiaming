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
 * 租户表
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_tenant")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Tenant对象", description = "租户表")
public class Tenant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("租户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租户名")
    private String name;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("联系人")
    private String userName;

    @ApiModelProperty("联系方式")
    private String mobile;

    @ApiModelProperty("租户状态 0:停用 1:正常")
    private Integer status;

    @ApiModelProperty("绑定的域名")
    private String domain;

    @ApiModelProperty("租户套餐id")
    private Long packageId;

    @ApiModelProperty("过期时间")
    private LocalDateTime expireTime;

    @ApiModelProperty("账号配额")
    private Integer accountCount;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;
}
