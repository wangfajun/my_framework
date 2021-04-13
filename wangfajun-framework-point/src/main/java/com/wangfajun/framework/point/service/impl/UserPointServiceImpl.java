package com.wangfajun.framework.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.point.entity.UserPoint;
import com.wangfajun.framework.point.mapper.UserPointMapper;
import com.wangfajun.framework.point.service.UserPointService;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 积分服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserPointServiceImpl extends ServiceImpl<UserPointMapper, UserPoint> implements UserPointService {

}
