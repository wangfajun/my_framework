package com.wangfajun.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.exception.FrameWorkErrorCode;
import com.wangfajun.framework.handler.LoginHandler;
import com.wangfajun.framework.model.entity.Demo;
import com.wangfajun.framework.model.res.DemoLoginRes;
import com.wangfajun.framework.service.DemoService;
import com.wangfajun.framework.mapper.DemoMapper;
import com.wangfajun.framework.utils.LoginTokenControlUtil;
import com.wangfajun.framework.exception.FrameworkErrorException;
import org.springframework.stereotype.Service;
import java.util.Map;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

	private Map<String, LoginHandler> clientLoginHandlerMap;

	private LoginTokenControlUtil loginTokenControlUtil;

	/**
	 * 登录
	 *
	 * @param mobile 手机号
	 * @return
	 */
	@Override
	public DemoLoginRes login(String mobile) {
		LoginHandler loginHandler = clientLoginHandlerMap.get(CommonConstants.MOBILE_CLIENT_TYPE_LOGIN);
		if (ObjectUtil.isEmpty(loginHandler)) {
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_LOGIN_TYPE_NOT_EXISTS_ERR);
		}
		return loginHandler.handle(mobile);
	}

	/**
	 * 退出
	 * @return
	 */
	@Override
	public void logout() {
		loginTokenControlUtil.logout();
	}

}
