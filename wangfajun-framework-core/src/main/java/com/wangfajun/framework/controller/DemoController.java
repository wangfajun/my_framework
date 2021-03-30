package com.wangfajun.framework.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wangfajun.framework.model.res.DemoLoginRes;
import com.wangfajun.framework.service.DemoService;
import com.wangfajun.framework.annotation.Inner;
import com.wangfajun.framework.annotation.SmsCheck;
import com.wangfajun.framework.annotation.TokenCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * demo
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@RestController
@RequestMapping
@Api(value = "/demo", tags = "demo管理")
public class DemoController {

	@Autowired
	private DemoService userInfoService;

	@Inner
	@ApiOperation(value = "登录", notes = "登录")
	@PostMapping("/login")
	@SmsCheck
	public R<DemoLoginRes> login(String mobile) {
		return R.ok(userInfoService.login(mobile));
	}

	/**
	 * 退出
	 * @return
	 */
	@TokenCheck
	@ApiOperation(value = "退出", notes = "退出")
	@PostMapping("/logout" )
	public R<Boolean> logout() {
		userInfoService.logout();
		return R.ok(Boolean.TRUE);
	}

}
