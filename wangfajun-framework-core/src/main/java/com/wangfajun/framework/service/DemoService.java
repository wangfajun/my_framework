package com.wangfajun.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.model.res.DemoLoginRes;
import com.wangfajun.framework.model.entity.Demo;

/**
 * Demo服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface DemoService extends IService<Demo> {

	/**
	 * 登录
	 *
	 * @param mobile 登录请求参数
	 * @return
	 */
	DemoLoginRes login(String mobile);

	/**
	 * 退出
	 */
	void logout();

}
