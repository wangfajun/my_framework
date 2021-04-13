package com.wangfajun.framework.account.controller;

import com.wangfajun.framework.account.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * seata AT测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
//@RequestMapping("/seataAt")
public class AccountSeataAtController {

	@Autowired
	UserAccountService userAccountService;

	/**
	 * seata AT模式 分布式事务测试
	 */
	@GetMapping("/one")
	@GlobalTransactional(rollbackFor = Exception.class)
	public String save(){

		userAccountService.saveObj();

		return "success";
	}

}
