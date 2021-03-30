package com.wangfajun.framework.handler.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.aes.FrameWorkAESCipher;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.convert.UserInfoConverter;
import com.wangfajun.framework.enums.UserInfoStatusEnum;
import com.wangfajun.framework.exception.FrameWorkErrorCode;
import com.wangfajun.framework.exception.FrameworkErrorException;
import com.wangfajun.framework.model.res.UserLoginRes;
import com.wangfajun.framework.handler.LoginHandler;
import com.wangfajun.framework.mapper.UserInfoMapper;
import com.wangfajun.framework.model.entity.UserInfo;
import com.wangfajun.framework.utils.LoginTokenControlUtil;
import com.wangfajun.framework.utils.RedissonLockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * 手机号登录处理器
 * @author wangfajun
 */
@Slf4j
@Component("mobileLogin")
public class UserLoginHandler implements LoginHandler {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private RedissonLockUtils redissonLockUtils;

	@Autowired
	private LoginTokenControlUtil loginTokenControlUtil;

	@Autowired
	private FrameWorkAESCipher frameWorkAESCipher;

	@Autowired
	private UserInfoConverter userInfoConverter;

	/**
	 * 登录
	 * @param mobile 手机号登录请求参数
	 * @return
	 */
	@Override
	@Transactional
	public UserLoginRes handle(String mobile) {
		String key = CommonConstants.FRAMEWORK_DISTRIBUTED_LOGIN_KEY + mobile;
		try {
			if(redissonLockUtils.tryLock(key, CommonConstants.WAIT_TIME, CommonConstants.LEASE_TIME, TimeUnit.MILLISECONDS)){
				// 根据手机号，获取会员信息
				UserInfo userInfo = info(mobile);
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
	public UserInfo info(String mobile) {
		UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>query().lambda()
				.eq(UserInfo::getMobile, frameWorkAESCipher.encrypt(mobile))
		);
		if (ObjectUtils.isEmpty(userInfo)) {
			log.info("手机号:{}未注册",mobile);
			return null;
		}else{
			// 会员账号被冻结
			if(userInfo.getStatus().equals(UserInfoStatusEnum.USER_FORBIDDEN.getStatus())){
				throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_STATUS_INVALID_ERR);
			}
		}
		return userInfo;
	}

}
