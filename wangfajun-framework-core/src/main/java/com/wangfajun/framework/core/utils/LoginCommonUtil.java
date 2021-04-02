package com.wangfajun.framework.core.utils;

import com.wangfajun.framework.api.model.entity.Demo;
import com.wangfajun.framework.api.model.res.DemoLoginRes;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * demo管理公共工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginCommonUtil {

	public static DemoLoginRes getMembersLoginResDto(Demo newUserInfo) {
		DemoLoginRes dto = new DemoLoginRes();
		BeanUtils.copyProperties(newUserInfo, dto);

		// 创建jwt token
		String token = JwtTokenUtil.createJwt(newUserInfo);
		log.info("会员登录成功, token={}", token);

		// 将token放在响应参数里
		dto.setToken(token);
		return dto;
	}

}
