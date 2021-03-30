package com.wangfajun.framework.utils;

import com.wangfajun.framework.model.entity.UserInfo;
import com.wangfajun.framework.model.res.UserLoginRes;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员管理公共工具类
 * @author wangfajun
 * @version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginCommonUtil {

	public static UserLoginRes getMembersLoginResDto(UserInfo newUserInfo) {
		UserLoginRes dto = new UserLoginRes();
		BeanUtils.copyProperties(newUserInfo, dto);

		// 创建jwt token
		String token = JwtTokenUtil.createJwt(newUserInfo);
		log.info("会员登录成功, token={}", token);

		// 将token放在响应参数里
		dto.setToken(token);
		return dto;
	}

}
