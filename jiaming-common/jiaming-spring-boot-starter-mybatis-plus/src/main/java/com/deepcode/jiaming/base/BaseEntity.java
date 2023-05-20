package com.deepcode.jiaming.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty("更新时间")
  private LocalDateTime updateTime;
}
