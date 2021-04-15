package com.wangfajun.framework.logistics.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 库存表
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "stock", autoResultMap = true)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物流编号 主键
     */
    private String id;

	/**
	 * 用户状态(0:可用，1：禁用)
	 */
	private Integer status;

	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 扣减数量
	 */
	private Integer nums;

	/**
     * 创建时间
     */
    private Date createTime;

}
