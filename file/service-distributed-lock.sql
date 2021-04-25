-- 创建数据库
CREATE DATABASE IF NOT EXISTS service_distributed_lock DEFAULT charset utf8 COLLATE utf8_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_record
-- ----------------------------
DROP TABLE IF EXISTS `service_distributed_lock`.`order_record`;
CREATE TABLE `service_distributed_lock`.`order_record` (
  `order_id` varchar(50) NOT NULL COMMENT '订单编号',
  `status` tinyint(1) DEFAULT '0' COMMENT '0:待支付;1:已支付',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单流水表';

-- ----------------------------
-- Table structure for tbl_order_lock
-- ----------------------------
DROP TABLE IF EXISTS `service_distributed_lock`.`order_lock`;
CREATE TABLE `service_distributed_lock`.`order_lock` (
  `order_id` int(8) NOT NULL,
  `driver_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
