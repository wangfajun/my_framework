package com.wangfajun.framework.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.order.mapper.TxEventMapper;
import com.wangfajun.framework.api.model.entity.TxEvent;
import com.wangfajun.framework.order.service.TxEventService;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本地消息表 事件服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class TxEventServiceImpl extends ServiceImpl<TxEventMapper, TxEvent> implements TxEventService {

}
