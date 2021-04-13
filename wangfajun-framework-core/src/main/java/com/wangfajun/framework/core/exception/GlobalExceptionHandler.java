package com.wangfajun.framework.core.exception;

import com.wangfajun.framework.util.R;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * 全局异常
	 */
	@ExceptionHandler(Exception.class)
	public R exceptionHandler(Exception e) {
		log.error("Exception.message:{}", e);
		return R.failed(FrameWorkErrorCode.FRAMEWORK_SYS_ERR.getBizCode(), FrameWorkErrorCode.FRAMEWORK_SYS_ERR.getMsg());
	}

	/**
	 * 业务异常
	 */
	@ExceptionHandler(FrameworkErrorException.class)
	public R exceptionHandler(FrameworkErrorException e) {
		log.error("MnsErrorException.message:{}", e);
		return R.failed(e.getBizCode(), e.getMessage());
	}

	/**
	 * 参数校验异常处理
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public R exceptionHandler(MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException.message:{}", e);
		BindingResult result = e.getBindingResult();
		StringBuilder errorMsg = new StringBuilder();
		if (result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			fieldErrors.forEach(error -> {
				errorMsg.append(error.getDefaultMessage()).append("!");
			});
		}
		return R.failed(errorMsg.toString());
	}

}
