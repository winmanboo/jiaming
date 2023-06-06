package com.deepcode.jiaming.result;

import lombok.AllArgsConstructor;

/**
 * @author winmanboo
 * @date 2023/5/24 18:05
 */
@AllArgsConstructor
public enum UserResultStatus implements IResultStatus {
    A0001("A0001", "用户端错误"), // 一级宏观码
    A0010("A0010", "用户注册错误"), // 二级宏观码
    A0200("A0200", "用户登录错误"), // 二级宏观码
    A0201("A0201", "用户身份校验失败"),
    A0300("A0300", "访问权限异常"), // 二级宏观码
    A0301("A0301", "未认证"),
    A0302("A0302", "令牌未识别"),
    A0303("A0303", "令牌已过期"),
    A0304("A0304", "令牌无效"),
    A0305("A0305", "访问未授权"),
    A0400("A0400", "用户请求参数异常"), // 二级宏观码
    A0500("A0500", "用户请求服务器异常"), // 二级宏观码
    A0600("A0600", "用户资源异常"), // 二级宏观码
    A0700("A0700", "用户上传文件异常"), // 二级宏观码
    A0800("A0800", "用户当前版本异常"), // 二级宏观码
    A0900("A0900", "用户隐私未授权"), // 二级宏观码
    A1000("A1000", "用户设备异常"); // 二级宏观码

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
