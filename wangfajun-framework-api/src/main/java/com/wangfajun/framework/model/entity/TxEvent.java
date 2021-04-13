package com.wangfajun.framework.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分布式事务 本地消息 事件表
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "tx_event", autoResultMap = true)
public class TxEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 事件类型(1:支付事件;2:订单事件）
     */
    private Integer eventType;

    /**
     * 事件内容
     */
    private String content;

    /**
     * 事件状态(1:新事件;2:已发送;3:已接受;4:已处理)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

}
