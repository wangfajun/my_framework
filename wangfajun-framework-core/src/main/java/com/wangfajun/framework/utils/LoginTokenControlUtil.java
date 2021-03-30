package com.wangfajun.framework.utils;

import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.model.entity.UserInfo;
import com.wangfajun.framework.model.res.JwtUserInfoRes;
import com.wangfajun.framework.model.res.UserLoginRes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 用户登录令牌版本管理工具
 *
 * @author wangfajun
 */
@Component
@AllArgsConstructor
public class LoginTokenControlUtil {

    private RedisUtil redisUtil;

    /**
     * 登录成功，返回token，设置token到redis
     * @param userInfo 会员信息
     * @return MemberLoginResDto 准点登录成功返回对象
     */
    public UserLoginRes loginSuccess(UserInfo userInfo) {
    	// 设置jwt令牌到返回参数里，给前端用，后续操作，请求头里都要带上此token
        UserLoginRes dto = LoginCommonUtil.getMembersLoginResDto(userInfo);

        // 记录token到redis,失效时间5小时，如果用户有操作，网关中将时间往后延
        redisUtil.set(CommonConstants.FRAMEWORK_LOGIN_TOKEN + userInfo.getUserId(),
				dto.getToken(),
				CommonConstants.FRAMEWORK_REDIS_TOKEN_EXPIRE_SECOND);

        return dto;
    }

    /**
     * 会员登出时，redis中把会员登录token的删除,网关拦截器ValidateJwtTokenFilter会校验token是否还存在
     */
    public void logout(){
		// 根据请求头中的token,获取会员信息
        JwtUserInfoRes dto = JwtTokenUtil.getUserInfo();
        // 会员登出，删除redis中的token
        redisUtil.del(CommonConstants.FRAMEWORK_LOGIN_TOKEN + dto.getUserId());
    }

}
