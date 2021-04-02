package com.wangfajun.framework.point.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.api.model.req.EventContent;
import com.wangfajun.framework.point.entity.UserPoint;

/**
 * 积分服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface UserPointService extends IService<UserPoint> {

	/**
	 * 添加积分
	 *
	 * @param eventContent
	 * @return
	 */
	boolean addPoint(EventContent eventContent);

}
