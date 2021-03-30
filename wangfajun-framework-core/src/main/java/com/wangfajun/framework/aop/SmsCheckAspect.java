package com.wangfajun.framework.aop;

import com.wangfajun.framework.annotation.SmsCheck;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.exception.FrameworkErrorException;
import com.wangfajun.framework.utils.ContextHolderUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.http.HttpServletRequest;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Aspect
public class SmsCheckAspect {

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 以 controller 包下定义的所有请求为切入点
	 */
	@Pointcut("execution(public * *..controller..*.*(..))")
	public void check() {
	}

	/**
	 * 在切点之前织入
	 */
	@Before("check() && @annotation(smsCheck)")
	public void doBefore(JoinPoint joinPoint, SmsCheck smsCheck) {
		checkCode();
	}

	/**
	 * 手机验证码校验
	 *
	 */
	@SneakyThrows
	private void checkCode() {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String code = request.getParameter("code");
		if (StrUtil.isBlank(code)) {
			throw new FrameworkErrorException("验证码不能为空");
		}

		// 获取手机号码参数
		String mobile = request.getParameter("mobile");
		if (StrUtil.isBlank(mobile)) {
			throw new FrameworkErrorException("手机号不能为空");
		}

		// todo 测试时，放行,上线后，记得去掉
		if ("8888".equals(code)) {
			return;
		}

		// 组装redis的key
		String key = CommonConstants.MNS_SMS_VERIFY_CODE_KEY + CommonConstants.FRAMEWORK_CLIENT_TYPE + mobile;

		if (!redisTemplate.hasKey(key)) {
			throw new FrameworkErrorException("无效的验证码");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);
		if (codeObj == null) {
			throw new FrameworkErrorException("无效的验证码");
		}

		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new FrameworkErrorException("无效的验证码");
		}

		//redis中的验证码跟传进来的不一致
		if (!StrUtil.equals(saveCode, code)) {
			redisTemplate.delete(key);
			throw new FrameworkErrorException("无效的验证码");
		}

		redisTemplate.delete(key);
	}
}
