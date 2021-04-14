package com.wangfajun.framework.account.controller;

import com.wangfajun.framework.account.entity.UserAccount;
import com.wangfajun.framework.account.service.UserAccountServiceRocket;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Random;

/**
 * rocketMQ事务消息测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/14 13:58
 */
@RestController
@RequestMapping("/rocket")
public class AccountRocketMqController {

	@Autowired
	UserAccountServiceRocket userAccountServiceRocket;

	/**
	 * 创建用户
	 */
	@PostMapping("/createAccount")
	public String createAccount() throws MQClientException {
		// 构造用户信息
		UserAccount account = new UserAccount();
		account.setId(String.valueOf(new Random().nextInt(10000)));
		account.setUserName("laowang");
		account.setStatus(0);
		account.setCreateTime(new Date());

		userAccountServiceRocket.sendCreateAccountMsg(account);

		return "success";
	}

}
