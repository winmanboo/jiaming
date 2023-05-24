package com.deepcode.jiaming.uaa.handler;

import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.result.UserResultStatus;
import com.deepcode.jiaming.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author winmanboo
 * @date 2023/5/24 19:06
 */
@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ResponseUtil.out(response, Result.fail(UserResultStatus.A0305));
    }
}
