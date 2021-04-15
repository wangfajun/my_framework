-- 创建数据库
CREATE DATABASE IF NOT EXISTS service_core DEFAULT charset utf8 COLLATE utf8_general_ci;

-- demo
DROP TABLE IF EXISTS `service_core`.`fm_demo`;
create table `service_core`.`fm_demo`(
	`user_id` int(11) NOT NULL,
	`user_name` varchar(50),
	`mobile` varchar(50),
	`user_img_url` varchar(255),
	`id_card` varchar(18),
	`status` int2 default 0,
	`create_time` datetime,
	`create_user` varchar(50),
	`update_time` datetime,
	`update_user` varchar(50),
	primary key (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;