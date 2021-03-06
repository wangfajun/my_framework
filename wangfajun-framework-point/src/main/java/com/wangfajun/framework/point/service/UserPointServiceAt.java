package com.wangfajun.framework.point.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.point.entity.UserPoint;

/**
 * AT模式 积分服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface UserPointServiceAt extends IService<UserPoint> {

	void saveAt();

}
