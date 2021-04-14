package com.wangfajun.framework.account.controller;

import com.wangfajun.framework.account.entity.UserAccount;
import com.wangfajun.framework.account.service.UserAccountServiceAt;
import com.wangfajun.framework.account.service.UserAccountServiceTcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Random;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * seata AT、TCC测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/seata")
public class AccountSeataController {

	@Autowired
	UserAccountServiceAt userAccountService;

	@Autowired
	UserAccountServiceTcc userAccountServiceTcc;

	/**
	 * seata AT模式 分布式事务测试
	 */
	@PostMapping("/at")
	@GlobalTransactional(rollbackFor = Exception.class)
	public String save(){
		userAccountService.saveAt();
		return "success";
	}

	/**
	 * seata TCC模式 分布式事务测试
	 */
	@PostMapping("/tcc")
	@GlobalTransactional(rollbackFor = Exception.class)
	public String oneTcc(){
		userAccountServiceTcc.userAccountTry(null);

		UserAccount account = new UserAccount();
		account.setId(String.valueOf(new Random().nextInt(10000)));
		account.setUserName("laowang");
		account.setStatus(0);
		account.setCreateTime(new Date());

		return "success";
	}

}
