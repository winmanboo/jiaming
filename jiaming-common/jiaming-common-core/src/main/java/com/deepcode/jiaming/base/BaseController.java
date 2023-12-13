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

    protected Result<Void> ok() {
        return Result.ok();
    }

    protected Result<Void> fail() {
        return Result.fail();
    }

    protected Result<Void> fail(String msg) {
        return Result.fail(msg);
    }
}
