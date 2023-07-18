package com.deepcode.jiaming.admin.vo;

import com.deepcode.jiaming.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author winmanboo
 * @date 2023/7/14 17:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostVo extends BaseEntity {
    @ApiModelProperty("岗位id")
    private Long id;

    @ApiModelProperty("岗位标识")
    private String code;

    @ApiModelProperty("岗位名称")
    private String name;

    @ApiModelProperty("状态 0:停用 1:正常")
    private Integer status;
}
