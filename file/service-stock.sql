-- 创建数据库
CREATE DATABASE IF NOT EXISTS service_stock DEFAULT charset utf8 COLLATE utf8_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `service_stock`.`stock`;
CREATE TABLE `service_stock`.`stock` (
  `id` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `nums` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `service_stock`.`stock` VALUES ('1', '0', '1', '2021-04-15 14:11:37');

-- ----------------------------
-- Table structure for transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `service_stock`.`transaction_log`;
CREATE TABLE `service_stock`.`transaction_log` (
  `id` varchar(255) NOT NULL,
  `business` varchar(255) DEFAULT NULL,
  `foreign_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transaction_log
-- ----------------------------
