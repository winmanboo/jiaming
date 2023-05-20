package com.deepcode.jiaming.result;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultStatus implements IResultStatus {
  OK("00000", "操作成功"),
  FAIL("B0001", "操作失败"),
  EXCEPTION("B0002", "服务器出点了点小差"),
  ARGUMENT_NOT_VALID("B0003", "请求参数格式有误"),
  LOGIN_EXCEPTION("A0200", "用户登陆异常"),
  AUTHENTICATION_FAILED("A0220", "用户身份校验失败"),
  LOGIN_EXPIRE("A0230", "登陆已过期"),
  ACCESS_FAILED("A0300", "访问权限异常");

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
