drop table if exists order_record;
CREATE TABLE order_record(
    orderId INT unsigned NOT NULL,
    status TINYINT(1) DEFAULT 0 COMMENT '0:待支付;1:已支付',
    balance decimal(10,2) COMMENT '金额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (orderId)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单流水表';