package com.wangfajun.framework.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.account.entity.UserAccount;
import com.wangfajun.framework.account.mapper.UserAccountMapper;
import com.wangfajun.framework.account.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * 账户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public void saveObj() {
		UserAccount account = new UserAccount();
		account.setId(String.valueOf(new Random().nextInt(10000)));
		account.setUserName("laowang");
		account.setStatus(0);
		account.setCreateTime(new Date());

		this.save(account);

		restTemplate.postForEntity("http://service-point/seataAt/save",null,String.class);
	}
}
