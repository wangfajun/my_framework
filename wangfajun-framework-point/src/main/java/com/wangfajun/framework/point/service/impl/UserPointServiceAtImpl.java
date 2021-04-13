package com.wangfajun.framework.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.point.entity.UserPoint;
import com.wangfajun.framework.point.mapper.UserPointMapper;
import com.wangfajun.framework.point.service.UserPointServiceAt;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AT模式 积分服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserPointServiceAtImpl extends ServiceImpl<UserPointMapper, UserPoint> implements UserPointServiceAt {

	@Override
	public void saveAt() {
		UserPoint payRecord = new UserPoint();
		payRecord.setId(String.valueOf(new Random().nextInt(10000)));
		payRecord.setUserId("1");
		payRecord.setPoints(1);
		payRecord.setCreateTime(new Date());
		payRecord.setStatus(0);
		this.save(payRecord);
	}

}
