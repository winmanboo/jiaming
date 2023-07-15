package com.deepcode.jiaming.admin.vo;

import com.deepcode.jiaming.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author winmanboo
 * @date 2023/7/15 09:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVo extends BaseEntity {
    @ApiModelProperty("角色编号")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色权限标识")
    private String code;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("角色状态 0:停用 1:启用")
    private Integer status;
}
