package com.deepcode.jiaming.exception;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author winmanboo
 * @date 2023/5/20 21:31
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_PATTERN = "「全局异常拦截」，原因：{}";

    @ExceptionHandler(value = Exception.class)
    public Result<Void> handleException(Throwable throwable) {
        log.error(ERROR_PATTERN, throwable.getMessage());
        throwable.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(value = JiamingException.class)
    public Result<Void> handleServiceException(JiamingException mallException) {
        log.error(ERROR_PATTERN, mallException.getMessage());
        mallException.printStackTrace();
        return Result.build(mallException.getCode(), mallException.getMessage(), null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();

        bindingResult.getFieldErrors().forEach(item -> {
            sb.append("{").append(item.getDefaultMessage()).append("}");
            sb.append(", ");
        });

        log.error(ERROR_PATTERN, sb);

        return Result.fail(ResultStatus.ARGUMENT_NOT_VALID);
    }
}
