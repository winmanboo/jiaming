package com.deepcode.jiaming.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author winmanboo
 * @date 2023/5/20 21:27
 */
@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private String code;

    private String msg;

    private T data;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return build(ResultStatus.OK, data);
    }

    public static <T> Result<T> fail(String message) {
        return build(ResultStatus.FAIL.getCode(), message, null);
    }

    public static <T> Result<T> fail() {
        return build(ResultStatus.FAIL, null);
    }

    public static <T> Result<T> fail(T data) {
        return build(ResultStatus.FAIL, data);
    }

    public static <T> Result<T> fail(IResultStatus resultStatus) {
        return build(resultStatus, null);
    }

    public static <T> Result<T> valid(Boolean valid) {
        if (valid == null || !valid) return Result.fail();
        return Result.ok();
    }

    public static <T> Result<T> build(IResultStatus resultStatus, T data) {
        return build(resultStatus.getCode(), resultStatus.getMessage(), data);
    }

    public static <T> Result<T> build(String code, String msg, T data) {
        return new Result.ResultBuilder<T>()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code != null && code.equals(ResultStatus.OK.getCode());
    }
}
