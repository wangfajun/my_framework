-- 创建数据库
CREATE DATABASE IF NOT EXISTS tx_manager DEFAULT charset utf8 COLLATE utf8_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_logger
-- ----------------------------
DROP TABLE IF EXISTS `tx_manager`.`t_logger`;
CREATE TABLE `tx_manager`.`t_logger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(64) NOT NULL,
  `unit_id` varchar(32) NOT NULL,
  `tag` varchar(50) NOT NULL,
  `content` varchar(1024) NOT NULL,
  `create_time` varchar(30) NOT NULL,
  `app_name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logger
-- ----------------------------

-- ----------------------------
-- Table structure for t_tx_exception
-- ----------------------------
DROP TABLE IF EXISTS `tx_manager`.`t_tx_exception`;
CREATE TABLE `tx_manager`.`t_tx_exception` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(32) DEFAULT NULL,
  `unit_id` varchar(32) DEFAULT NULL,
  `mod_id` varchar(32) DEFAULT NULL,
  `transaction_state` tinyint(4) DEFAULT NULL,
  `registrar` tinyint(4) DEFAULT NULL COMMENT '-1 未知 0 Manager 通知事务失败， 1 client询问事务状态失败2 事务发起方关闭事务组失败',
  `ex_state` tinyint(4) DEFAULT NULL COMMENT '0 待处理 1已处理',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_tx_exception
-- ----------------------------
