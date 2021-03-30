package com.wangfajun.framework.aop;

import com.baomidou.mybatisplus.extension.api.R;
import com.wangfajun.framework.utils.JacksonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * controller请求响应参数打印
 */
@Slf4j
@Configuration
@Aspect
public class WebLogAop {

	private static final String GET = "get";

	/**
	 * 以 controller 包下定义的所有请求为切入点
	 */
	@Pointcut("execution(public * *..controller..*.*(..))")
	public void webLog() {
	}

	/**
	 * 在切点之前织入
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		// 开始打印请求日志
		ServletRequestAttributes attributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 打印请求相关参数
		log.info("### <<<------------------------- Log Start ------------------------->>>");
		// 打印请求 url
		log.info("### URL            : {}", request.getRequestURL().toString());
		// 打印 Http method
		log.info("### HTTP Method    : {}", request.getMethod());
		// 打印调用 controller 的全路径以及执行方法
		log.info(
				"### Class Method   : {}.{}",
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());
		// 打印请求的 IP
		log.info("### IP             : {}", request.getRemoteAddr());
		// 打印请求入参
		if (!GET.equalsIgnoreCase(request.getMethod())) {
			log.info("### Request Args   : {}", JacksonUtils.marshallToString(joinPoint.getArgs()));
		}
	}

	/**
	 * 在切点之后织入
	 */
	@After("webLog()")
	public void doAfter() {
		log.info("### <<<------------------------- Log End ------------------------->>>");
	}

	/**
	 * 环绕
	 */
	@Around("webLog()")
	@SneakyThrows
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
		Object result = proceedingJoinPoint.proceed();
		// 打印出参
		if (result instanceof R) {
			log.info("# Response Args  : {}", JacksonUtils.marshallToString(result));
		}
		return result;
	}
}
