-- 创建数据库
CREATE DATABASE IF NOT EXISTS service_pay DEFAULT charset utf8 COLLATE utf8_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pay_record
-- ----------------------------
DROP TABLE IF EXISTS `service_pay`.`pay_record`;
CREATE TABLE `service_pay`.`pay_record` (
  `pay_id` varchar(50) NOT NULL COMMENT '支付编号',
  `status` tinyint(4) DEFAULT NULL COMMENT '支付状态(0:待支付，1：已支付)',
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pay_record
-- ----------------------------

-- ----------------------------
-- Table structure for tx_event
-- ----------------------------
DROP TABLE IF EXISTS `service_pay`.`tx_event`;
CREATE TABLE `service_pay`.`tx_event` (
  `id` int(11) NOT NULL COMMENT '主键(事件id)',
  `event_type` int(11) DEFAULT NULL COMMENT '事件类型(1:支付事件;2:订单事件）',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `status` int(11) DEFAULT NULL COMMENT '事件状态(1:新事件;2:已发送;3:已接受;4:已处理)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tx_event
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `service_pay`.`undo_log`;
CREATE TABLE `service_pay`.`undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
