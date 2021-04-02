package com.wangfajun.framework.core.convert;

import com.wangfajun.framework.api.constant.CommonConstants;
import com.wangfajun.framework.api.model.entity.Demo;

import org.springframework.stereotype.Component;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * demo转换
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Component
public class DemoConverter {

	public Demo buildUserInfo(String mobile){
		Demo demo = new Demo();
		// 保存会员信息
		demo = new Demo();
		demo.setUserName(mobile);
		demo.setMobile(mobile);
		demo.setCreateTime(new Date());
		demo.setUserImgUrl(CommonConstants.FRAMEWORK_DEFAULT_IMG_URL);
		return demo;
	}

}
