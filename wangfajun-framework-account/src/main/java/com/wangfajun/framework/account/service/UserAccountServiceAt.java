package com.wangfajun.framework.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.account.entity.UserAccount;

/**
 * 账户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface UserAccountServiceAt extends IService<UserAccount> {

	void saveAt();
}
