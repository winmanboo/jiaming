package com.deepcode.jiaming.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * @author winmanboo
 * @date 2023/6/9 22:07
 */
@UtilityClass
public class BooleanNumUtil {
    /**
     * num 为 1 表示 true
     *
     * @param num 待检查的数值
     * @return num 是否为 1
     */
    public static boolean isTrue(Integer num) {
        if (num == null) return false;

        return Objects.equals(num, 1);
    }

    /**
     * num 是否为 0
     *
     * @param num 待检查的数值
     * @return num 是否为 0
     */
    public static boolean isFalse(Integer num) {
        return !isTrue(num);
    }
}
