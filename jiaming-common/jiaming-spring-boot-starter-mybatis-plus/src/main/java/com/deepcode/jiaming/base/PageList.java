package com.deepcode.jiaming.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {
  @ApiModelProperty("总页数")
  private Long pages;
  
  @ApiModelProperty("总条数")
  private Long total;
  
  @ApiModelProperty("结果集")
  private List<T> list;

  public static <T> PageList<T> turnTo(IPage<T> page) {
    PageList<T> pageList = new PageList<>();
    pageList.setPages(page.getPages());
    pageList.setTotal(page.getTotal());
    pageList.setList(page.getRecords());
    return pageList;
  }
}
