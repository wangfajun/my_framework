package com.wangfajun.framework.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.account.entity.UserAccount;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * rocketmq账户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface UserAccountServiceRocket extends IService<UserAccount> {

	void createAccount(UserAccount account, String transactionId);

	void sendCreateAccountMsg(UserAccount account) throws MQClientException;

}
