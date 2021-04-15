-- 创建数据库
CREATE DATABASE IF NOT EXISTS service_point DEFAULT charset utf8 COLLATE utf8_general_ci;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `service_point`.`undo_log`;
CREATE TABLE `service_point`.`undo_log` (
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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for user_point
-- ----------------------------
DROP TABLE IF EXISTS `service_point`.`user_point`;
CREATE TABLE `service_point`.`user_point` (
  `id` varchar(11) NOT NULL COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `status` tinyint(4) DEFAULT NULL COMMENT '积分状态(0:可用，1：禁用)',
  `points` int(11) DEFAULT '0' COMMENT '积分数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_point
-- ----------------------------
INSERT INTO `service_point`.`user_point` VALUES ('4906', '1', '0', '1', '2021-04-13 14:08:30');
