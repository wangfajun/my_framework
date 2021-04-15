-- 创建数据库
CREATE DATABASE IF NOT EXISTS service_logistics DEFAULT charset utf8 COLLATE utf8_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for logistics_record
-- ----------------------------
DROP TABLE IF EXISTS `service_logistics`.`logistics_record`;
CREATE TABLE `service_logistics`.`logistics_record` (
  `id` varchar(255) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logistics_record
-- ----------------------------
