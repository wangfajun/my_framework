package com.wangfajun.framework.convert;

import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.model.entity.UserInfo;
import org.springframework.stereotype.Component;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户转换
 *
 * @author wangfajun
 */
@Slf4j
@Component
public class UserInfoConverter {

	public UserInfo buildUserInfo(String mobile){
		UserInfo userInfo = new UserInfo();
		// 保存会员信息
		userInfo = new UserInfo();
		userInfo.setUserName(mobile);
		userInfo.setMobile(mobile);
		userInfo.setCreateTime(new Date());
		userInfo.setUserImgUrl(CommonConstants.FRAMEWORK_DEFAULT_IMG_URL);
		return userInfo;
	}
}
