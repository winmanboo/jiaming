package com.deepcode.jiaming.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.deepcode.jiaming.base.BaseEntity;
import com.google.common.io.BaseEncoding;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 岗位表
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Data
@TableName("sys_post")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Post对象", description = "岗位表")
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("岗位id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("岗位标识")
    private String code;

    @ApiModelProperty("岗位名称")
    private String name;

    @ApiModelProperty("状态 0:停用 1:正常")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("是否删除 0:未删除 1:删除")
    private Integer deleted;

    @ApiModelProperty("租户id")
    private Long tenantId;
}
