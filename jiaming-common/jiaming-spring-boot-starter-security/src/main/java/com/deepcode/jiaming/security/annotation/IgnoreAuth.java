package com.deepcode.jiaming.security.annotation;

import java.lang.annotation.*;

/**
 * 用于忽略权限校验的接口方法或接口类上
 * <br/>
 * 如果放接口类上，那么该类下的所有接口将会忽略权限校验
 *
 * @author winmanboo
 * @date 2023/5/22 11:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IgnoreAuth {
}
