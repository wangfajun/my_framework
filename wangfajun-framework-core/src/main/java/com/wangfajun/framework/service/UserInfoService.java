package com.wangfajun.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.model.res.UserLoginRes;
import com.wangfajun.framework.model.entity.UserInfo;

/**
 * 会员前端服务
 *
 * @author august
 */
public interface UserInfoService extends IService<UserInfo> {

	/**
	 * 登录
	 *
	 * @param mobile 登录请求参数
	 * @return
	 */
	UserLoginRes login(String mobile);

	/**
	 * 退出
	 */
	void logout();

}
