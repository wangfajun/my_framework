package com.wangfajun.framework.core.exception;

import com.wangfajun.framework.api.util.R;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.hutool.json.JSONUtil;

/**
 * oauth异常拦截
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws ServletException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		try {
			Throwable cause = authException.getCause();
			if(null == cause){
				response.getWriter().write(JSONUtil.toJsonStr(R.failed(String.valueOf(FrameWorkErrorCode.FRAMEWORK_AUTHORIZATION_TOKEN_EMPTY_ERR.getBizCode()),
						FrameWorkErrorCode.FRAMEWORK_AUTHORIZATION_TOKEN_EMPTY_ERR.getMsg())
				));
				return;
			}
			if (cause instanceof InvalidTokenException) {
				response.getWriter().write(JSONUtil.toJsonStr(R.failed(String.valueOf(FrameWorkErrorCode.FRAMEWORK_AUTHORIZATION_TOKEN_INVALID_ERR.getBizCode()),
						FrameWorkErrorCode.FRAMEWORK_AUTHORIZATION_TOKEN_INVALID_ERR.getMsg())
				));
				return;
			}
			if(authException instanceof InsufficientAuthenticationException){
				response.getWriter().write(JSONUtil.toJsonStr(R.failed(String.valueOf(FrameWorkErrorCode.FRAMEWORK_AUTHORIZATION_ERR.getBizCode()),
						FrameWorkErrorCode.FRAMEWORK_AUTHORIZATION_ERR.getMsg())
				));
				return;
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}