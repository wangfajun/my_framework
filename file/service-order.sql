/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : service-order

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-04-15 14:20:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_record
-- ----------------------------
DROP TABLE IF EXISTS `order_record`;
CREATE TABLE `order_record` (
  `order_id` varchar(50) NOT NULL COMMENT '订单编号',
  `status` tinyint(1) DEFAULT '0' COMMENT '0:待支付;1:已支付',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单流水表';

-- ----------------------------
-- Records of order_record
-- ----------------------------

-- ----------------------------
-- Table structure for tx_event
-- ----------------------------
DROP TABLE IF EXISTS `tx_event`;
CREATE TABLE `tx_event` (
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
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
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
