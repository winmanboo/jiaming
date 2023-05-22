package com.deepcode.jiaming.security.exception;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.ResultStatus;
import com.deepcode.jiaming.security.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ResponseUtil.out(response, objectMapper, Result.fail(ResultStatus.AUTHENTICATION_FAILED));
    }
}
