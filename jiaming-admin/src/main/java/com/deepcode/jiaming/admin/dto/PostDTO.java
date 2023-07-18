package com.deepcode.jiaming.admin.dto;

import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author winmanboo
 * @date 2023/7/18 17:23
 */
@Data
public class PostDTO {
    @ApiModelProperty("岗位 id")
    @NotNull(message = "岗位 id 不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("岗位名称")
    @NotEmpty(message = "岗位名称不能为空", groups = AddGroup.class)
    private String name;

    @ApiModelProperty("岗位状态")
    @NotNull(message = "岗位状态不能为空", groups = AddGroup.class)
    private Integer status;

    @ApiModelProperty("岗位编号")
    @NotEmpty(message = "岗位编号", groups = AddGroup.class)
    private String code;
}
