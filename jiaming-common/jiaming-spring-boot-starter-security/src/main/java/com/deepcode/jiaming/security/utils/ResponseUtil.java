package com.deepcode.jiaming.security.utils;

import com.deepcode.jiaming.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author winmanboo
 * @date 2023/5/22 11:05
 */
@Slf4j
@UtilityClass
public class ResponseUtil {
    public static <T> void out(HttpServletResponse response, ObjectMapper objectMapper, Result<T> result) throws IOException {
        if (response.isCommitted()) {
            log.trace("响应已提交不能输出");
            return;
        }
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        objectMapper.writeValue(response.getWriter(), result);
    }
}
