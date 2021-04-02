/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : service-pay

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-04-01 16:34:18
*/

SET FOREIGN_KEY_CHECKS=0;

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
