/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : service-logistics

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-04-15 14:19:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for logistics_record
-- ----------------------------
DROP TABLE IF EXISTS `logistics_record`;
CREATE TABLE `logistics_record` (
  `id` varchar(255) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logistics_record
-- ----------------------------
