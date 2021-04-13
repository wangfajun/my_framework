package com.wangfajun.framework.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.account.entity.UserAccount;
import com.wangfajun.framework.account.mapper.UserAccountMapper;
import com.wangfajun.framework.account.service.UserAccountServiceTcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;

/**
 * TCC账户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
public class UserAccountServiceTccImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountServiceTcc {

	@Autowired
	RestTemplate restTemplate;

	@Override
	@Transactional
	public String userAccountTry(BusinessActionContext businessActionContext) {
		System.out.println("account try");
		restTemplate.postForEntity("http://service-point/seata/tcc",null,String.class);
		return null;
	}

	@Override
	@Transactional
	public boolean userAccountCommit(BusinessActionContext businessActionContext) {
		System.out.println("account commit");
		return true;
	}

	@Override
	@Transactional
	public boolean userAccountRollback(BusinessActionContext businessActionContext) {
		System.out.println("account rollback");
		return true;
	}

}
