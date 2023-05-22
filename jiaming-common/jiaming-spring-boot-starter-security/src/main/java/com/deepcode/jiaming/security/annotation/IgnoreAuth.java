package com.deepcode.jiaming.security.annotation;

import java.lang.annotation.*;

/**
 * 用于忽略权限校验的接口方法
 *
 * @author winmanboo
 * @date 2023/5/22 11:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IgnoreAuth {
}
