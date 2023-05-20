package com.deepcode.jiaming.exception;

import com.deepcode.jiaming.result.IResultStatus;
import com.deepcode.jiaming.result.ResultStatus;
import lombok.Getter;

/**
 * @author winmanboo
 * @date 2023/5/20 21:31
 */
@Getter
public class JiamingException extends RuntimeException {
    private final String code;

    public JiamingException(IResultStatus resultStatus) {
        super(resultStatus.getMessage());
        this.code = resultStatus.getCode();
    }

    public JiamingException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public JiamingException(String msg) {
        super(msg);
        this.code = ResultStatus.FAIL.getCode();
    }
}
