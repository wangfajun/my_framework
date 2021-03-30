package com.wangfajun.framework.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wangfajun.framework.model.res.UserLoginRes;
import com.wangfajun.framework.service.UserInfoService;
import com.wangfajun.framework.annotation.Inner;
import com.wangfajun.framework.annotation.SmsCheck;
import com.wangfajun.framework.annotation.TokenCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author wangfajun
 */
@RestController
@RequestMapping
@Api(value = "/user", tags = "xxx管理")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@Inner
	@ApiOperation(value = "登录", notes = "登录")
	@PostMapping("/login")
	@SmsCheck
	public R<UserLoginRes> login(String mobile) {
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
