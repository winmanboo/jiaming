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
 * 系统角色
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Role对象", description = "系统角色")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色权限标识")
    private String code;

    @ApiModelProperty("角色状态 0:停用 1:启用")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;

    @ApiModelProperty("租户编号")
    private Long tenantId;
}
