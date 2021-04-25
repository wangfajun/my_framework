package com.wangfajun.framework.distributedlock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单记录表
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "order_record", autoResultMap = true)
public class OrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
	@TableId
    private String orderId;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

	/**
	 * 订单金额
	 */
	private BigDecimal balance;

}
