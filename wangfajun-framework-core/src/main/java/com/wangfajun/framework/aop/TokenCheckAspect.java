package com.wangfajun.framework.aop;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wangfajun.framework.annotation.TokenCheck;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.exception.FrameWorkErrorCode;
import com.wangfajun.framework.exception.FrameworkErrorException;
import com.wangfajun.framework.model.res.JwtUserInfoRes;
import com.wangfajun.framework.utils.JwtTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * jwt token拦截器
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Configuration
@Aspect
public class TokenCheckAspect {

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
	@Before("check() && @annotation(tokenCheck)")
	public void doBefore(JoinPoint joinPoint, TokenCheck tokenCheck) {
		// 开始打印请求日志
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 获取请求头信息authorization信息
		final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
		log.info("## authHeader= {}", authHeader);

		if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_NO_LOGIN_ERR);
		}

		// 获取token：以jwt 前缀+空格开头
		final String token = authHeader.substring(JwtTokenUtil.TOKEN_PREFIX_LENTH);

		// 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
		JwtTokenUtil.parseJwt(token, CommonConstants.FRAMEWORK_BASE64_SECRET);

		// 根据token,解析出会员信息
		JwtUserInfoRes memberResDto = JwtTokenUtil.getUserInfo(token);

		//访问时从token中取出memberId 和 redis中存储的对应memberId的token比对，不一致则不给访问。
		String userId = memberResDto.getUserId();
		Object tokenObj = redisTemplate.opsForValue().get(CommonConstants.FRAMEWORK_LOGIN_TOKEN + userId);

		//为空，说明登出过或用户超过5小时没在界面操作，得重新登录
		if (ObjectUtil.isEmpty(tokenObj)) {
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_TOKEN_EXPIRE_ERR);
		}else{
			// 当前请求头中的token跟redis中的不一致，说明用户重新登录过
			if(!token.equals(tokenObj.toString())){
				throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_LOGIN_ANOTHER_AREA_ERR);
			}
		}

		// 会员在界面有操作，则令牌失效时间往后延5小时
		redisTemplate.expire(CommonConstants.FRAMEWORK_LOGIN_TOKEN + userId,
				CommonConstants.FRAMEWORK_REDIS_TOKEN_EXPIRE_SECOND,
				TimeUnit.SECONDS);

	}

}
