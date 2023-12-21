package com.deepcode.jiaming.base;

import com.deepcode.jiaming.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author winmanboo
 * @date 2023/7/20 22:10
 */
public abstract class BaseController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected <T> Result<T> ok(T data) {
        return Result.ok(data);
    }

    protected <T> Result<T> ok() {
        return Result.ok();
    }

    protected <T> Result<T> fail() {
        return Result.fail();
    }

    protected <T> Result<T> fail(String msg) {
        return Result.fail(msg);
    }
}
