package com.deepcode.jiaming.admin.dto;

import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author winmanboo
 * @date 2023/7/18 12:03
 */
@Data
public class DeptDTO {
    @ApiModelProperty("部门 id")
    @NotNull(message = "部门 id 不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("上级部门")
    @NotNull(message = "上级部门不能为空", groups = AddGroup.class)
    private Long parentId;

    @ApiModelProperty("部门名称")
    @NotEmpty(message = "部门名称不能为空", groups = AddGroup.class)
    private String name;

    @ApiModelProperty("负责人")
    private Long leaderUserId;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门状态")
    @NotNull(message = "部门状态不能为空", groups = AddGroup.class)
    private Integer status;
}
