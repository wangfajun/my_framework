package com.wangfajun.framework.api.model.req;

import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件内容
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@ApiModel(value="事件内容", description="事件内容")
public class EventContent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号")
	private String orderId;

	/**
	 * 用户编号
	 */
	@ApiModelProperty(value = "用户编号")
	private String userId;

	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额")
	private BigDecimal balance;

}
