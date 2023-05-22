package com.deepcode.jiaming.security.exception;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.ResultStatus;
import com.deepcode.jiaming.security.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        ResponseUtil.out(response, objectMapper, Result.fail(ResultStatus.ACCESS_FAILED));
    }
}
