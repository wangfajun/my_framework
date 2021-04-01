package com.wangfajun.framework.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付记录对象
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 14:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "pay_record", autoResultMap = true)
public class PayRecord {

	/**
	 * 支付单编号
	 */
	@TableId
	private String payId;

	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 支付状态(0:待支付，1：已支付)
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 支付金额
	 */
	private BigDecimal balance;

}
