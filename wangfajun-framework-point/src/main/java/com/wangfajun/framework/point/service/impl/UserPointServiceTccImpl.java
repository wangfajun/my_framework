package com.wangfajun.framework.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.point.entity.UserPoint;
import com.wangfajun.framework.point.mapper.UserPointMapper;
import com.wangfajun.framework.point.service.UserPointServiceTcc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;

/**
 * TCC积分服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
public class UserPointServiceTccImpl extends ServiceImpl<UserPointMapper, UserPoint> implements UserPointServiceTcc {

	@Override
	@Transactional
	public String pointTry(BusinessActionContext businessActionContext) {
		System.out.println("point try");
		return "success";
	}

	@Override
	@Transactional
	public boolean pointCommit(BusinessActionContext businessActionContext) {
		System.out.println("point commit");
		return true;
	}

	@Override
	@Transactional
	public boolean pointRollback(BusinessActionContext businessActionContext) {
		System.out.println("point rollback");
		return true;
	}

}
