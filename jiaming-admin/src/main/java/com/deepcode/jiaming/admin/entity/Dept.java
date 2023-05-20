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
 * 部门表
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Dept对象", description = "部门表")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父部门id")
    private Long parentId;

    @ApiModelProperty("负责人")
    private Long leaderUserId;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门状态 0:停用 1:正常")
    private Integer status;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;

    @ApiModelProperty("租户id")
    private Long tenantId;
}
