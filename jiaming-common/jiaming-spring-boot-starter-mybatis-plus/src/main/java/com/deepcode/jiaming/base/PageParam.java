package com.deepcode.jiaming.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页参数
 */
public class PageParam implements Serializable {
  public static final String ASC = "ASC";

  public static final String DESC = "DESC";

  /**
   * 最大分页大小，如果分页大小大于500，则用500作为分页的大小。防止有人直接传入一个较大的数，导致服务器内存溢出宕机
   */
  public static final Integer MAX_PAGE_SIZE = 500;

  @NotNull(message = "页码不能为空")
  @ApiModelProperty(value = "当前页", required = true)
  private Integer page;

  @NotNull(message = "分页大小不能为空")
  @ApiModelProperty(value = "每页大小", required = true)
  private Integer size;

  public <T> Page<T> toPage() {
    int size_ = size > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : size;
    return new Page<>(page, size_);
  }

  public Integer getPage() {
    return page;
  }

  public Integer getSize() {
    return size;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
