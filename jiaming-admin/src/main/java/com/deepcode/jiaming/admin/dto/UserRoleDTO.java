package com.deepcode.jiaming.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author winmanboo
 * @date 2023/7/15 13:17
 */
@Data
public class UserRoleDTO {
    @ApiModelProperty("用户 id")
    @NotNull(message = "用户 id 不能为空")
    private Long userId;

    @ApiModelProperty("角色 id")
    @NotEmpty(message = "角色 id 列表不能为空")
    private List<Long> roleIds;
}
