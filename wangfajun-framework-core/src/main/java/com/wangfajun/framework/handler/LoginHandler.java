package com.wangfajun.framework.handler;

import com.wangfajun.framework.model.res.UserLoginRes;
import com.wangfajun.framework.model.entity.UserInfo;

public interface LoginHandler {

	/**
	 * 登录
	 *
	 * @param mobile 登录请求参数
	 * @return
	 */
	UserLoginRes handle(String mobile);

	/**
	 * 查询员工信息
	 *
	 * @param mobile 手机号
	 * @return
	 */
	UserInfo info(String mobile);

}
