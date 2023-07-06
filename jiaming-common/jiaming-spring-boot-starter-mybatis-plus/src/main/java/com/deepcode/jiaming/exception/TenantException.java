package com.deepcode.jiaming.exception;

/**
 * 租户异常
 *
 * @author winmanboo
 * @date 2023/7/6 12:34
 */
public class TenantException extends RuntimeException {
    public TenantException() {
    }

    public TenantException(String message) {
        super(message);
    }

    public TenantException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantException(Throwable cause) {
        super(cause);
    }

    public TenantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
