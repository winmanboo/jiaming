package com.deepcode.jiaming.uaa.point;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.UserResultStatus;
import com.deepcode.jiaming.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author winmanboo
 * @date 2023/5/24 19:05
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ResponseUtil.out(response, Result.fail(UserResultStatus.A0201));
    }
}
