package com.deepcode.jiaming.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author winmanboo
 * @date 2023/7/15 17:24
 */
@Getter
@RequiredArgsConstructor
public enum MenuType {
    CATALOG(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private final int code;

    private final String value;
}
