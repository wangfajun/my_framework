package com.wangfajun.framework.core.handler.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.api.aes.FrameWorkAESCipher;
import com.wangfajun.framework.api.constant.CommonConstants;
import com.wangfajun.framework.core.convert.DemoConverter;
import com.wangfajun.framework.api.enums.DemoStatusEnum;
import com.wangfajun.framework.core.exception.FrameWorkErrorCode;
import com.wangfajun.framework.core.exception.FrameworkErrorException;
import com.wangfajun.framework.core.handler.LoginHandler;
import com.wangfajun.framework.core.mapper.DemoMapper;
import com.wangfajun.framework.api.model.entity.Demo;
import com.wangfajun.framework.api.model.res.DemoLoginRes;
import com.wangfajun.framework.core.utils.LoginTokenControlUtil;
import com.wangfajun.framework.core.utils.RedissonLockUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * 手机号登录处理器
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Component("demoLogin")
public class DemoLoginHandler implements LoginHandler {

	@Autowired
	private DemoMapper userInfoMapper;

	@Autowired
	private RedissonLockUtils redissonLockUtils;

	@Autowired
	private LoginTokenControlUtil loginTokenControlUtil;

	@Autowired
	private FrameWorkAESCipher frameWorkAESCipher;

	@Autowired
	private DemoConverter userInfoConverter;

	/**
	 * 登录
	 * @param mobile 手机号登录请求参数
	 * @return
	 */
	@Override
	@Transactional
	public DemoLoginRes handle(String mobile) {
		String key = CommonConstants.FRAMEWORK_DISTRIBUTED_LOGIN_KEY + mobile;
		try {
			if(redissonLockUtils.tryLock(key, CommonConstants.WAIT_TIME, CommonConstants.LEASE_TIME, TimeUnit.MILLISECONDS)){
				// 根据手机号，获取会员信息
				Demo userInfo = info(mobile);
				if (userInfo == null) {
					userInfo = userInfoConverter.buildUserInfo(mobile);
					int count = userInfoMapper.insert(userInfo);
					if(count <= 0){
						throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_MOBILE_LOGIN_FAIL);
					}
				}
				// 设置jwt令牌到返回参数,设置token到redis
				return loginTokenControlUtil.loginSuccess(userInfo);
			}else{
				throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_DUPLICATE_REQ_ERR);
			}
		}catch (Exception e){
			if(e instanceof FrameworkErrorException){
				FrameworkErrorException ex = (FrameworkErrorException)e;
				throw new FrameworkErrorException(ex.getBizCode(),ex.getMsg());
			}else {
				throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_LOGIN_ERR);
			}
		}finally {
			//释放锁
			redissonLockUtils.unlock(key);
		}
	}

	/**
	 * mobile 获取用户信息
	 * @param mobile 手机号（不带区号）
	 * @return Member 会员信息
	 */
	@Override
	public Demo info(String mobile) {
		Demo userInfo = userInfoMapper.selectOne(Wrappers.<Demo>query().lambda()
				.eq(Demo::getMobile, frameWorkAESCipher.encrypt(mobile))
		);
		if (ObjectUtils.isEmpty(userInfo)) {
			log.info("手机号:{}未注册",mobile);
			return null;
		}else{
			// 会员账号被冻结
			if(userInfo.getStatus().equals(DemoStatusEnum.DEMO_FORBIDDEN.getStatus())){
				throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_STATUS_INVALID_ERR);
			}
		}
		return userInfo;
	}

}
