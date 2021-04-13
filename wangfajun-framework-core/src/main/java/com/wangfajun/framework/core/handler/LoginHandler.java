package com.wangfajun.framework.core.handler;

import com.wangfajun.framework.model.entity.Demo;
import com.wangfajun.framework.model.res.DemoLoginRes;

/**
 * 登录处理器
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface LoginHandler {

	/**
	 * 登录
	 *
	 * @param mobile 登录请求参数
	 * @return
	 */
	DemoLoginRes handle(String mobile);

	/**
	 * 查询员工信息
	 *
	 * @param mobile 手机号
	 * @return
	 */
	Demo info(String mobile);

}
