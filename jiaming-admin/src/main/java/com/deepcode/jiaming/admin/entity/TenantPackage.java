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
 * 租户套餐表
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_tenant_package")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TenantPackage对象", description = "租户套餐表")
public class TenantPackage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("租户套餐id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("套餐名")
    private String name;

    @ApiModelProperty("租户状态 0:停用 1:正常")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("关联的菜单编号")
    private String menuIds;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;
}
