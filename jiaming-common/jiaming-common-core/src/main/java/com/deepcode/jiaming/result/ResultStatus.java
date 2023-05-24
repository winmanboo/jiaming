package com.deepcode.jiaming.result;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultStatus implements IResultStatus {
  OK("00000", "操作成功"),
  FAIL("B0001", "操作失败");

  private final String code;

  private final String msg;

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return msg;
  }
}
