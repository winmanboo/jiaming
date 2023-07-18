package com.deepcode.jiaming.admin.vo;

import com.deepcode.jiaming.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门信息
 *
 * @author winmanboo
 * @date 2023/7/12 17:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptVo extends BaseEntity {
    @ApiModelProperty("部门id")
    private Long id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父部门id")
    private Long parentId;

    @ApiModelProperty("负责人 id")
    private String leaderUserId;

    @ApiModelProperty("负责人")
    private String leader;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("下级部门信息")
    private List<DeptVo> children;
}
