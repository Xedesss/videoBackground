-- 创建数据库
CREATE DATABASE IF NOT EXISTS `health_video` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `health_video`;

-- Banner轮播图表
CREATE TABLE `banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `image_url` varchar(255) NOT NULL COMMENT '图片URL',
  `type` varchar(20) NOT NULL DEFAULT 'video' COMMENT '点击类型：video-视频详情，webview-网页',
  `url` varchar(255) NOT NULL COMMENT '跳转链接',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序，数值越小越靠前',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 视频分类表
CREATE TABLE `video_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '分类图标',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序，数值越小越靠前',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频分类表';

-- 视频表
CREATE TABLE `video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '讲师ID',
  `course_name` varchar(100) NOT NULL COMMENT '课程名称',
  `title` varchar(100) NOT NULL COMMENT '视频标题',
  `cover_url` varchar(255) NOT NULL COMMENT '封面图URL',
  `video_url` varchar(255) NOT NULL COMMENT '视频URL',
  `status` varchar(20) NOT NULL DEFAULT '未开始' COMMENT '状态：未开始、进行中、已结束',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `entry_id` varchar(50) DEFAULT NULL COMMENT '课程ID或期数',
  `view_count` int(11) NOT NULL DEFAULT 0 COMMENT '观看次数',
  `duration` int(11) NOT NULL DEFAULT 0 COMMENT '视频时长(秒)',
  `description` text DEFAULT NULL COMMENT '视频描述',
  `has_questions` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否有问题：0-否，1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`),
  KEY `idx_teacher` (`teacher_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频表';

-- 视频问题表
CREATE TABLE `video_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `video_id` bigint(20) NOT NULL COMMENT '视频ID',
  `title` varchar(255) NOT NULL COMMENT '问题标题',
  `type` varchar(20) NOT NULL DEFAULT 'single' COMMENT '类型：single-单选，multiple-多选',
  `correct_answer` varchar(50) NOT NULL COMMENT '正确答案',
  `explanation` text DEFAULT NULL COMMENT '解释',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_video` (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频问题表';

-- 问题选项表
CREATE TABLE `question_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `question_id` bigint(20) NOT NULL COMMENT '问题ID',
  `option_id` varchar(10) NOT NULL COMMENT '选项ID：如A、B、C、D',
  `content` varchar(255) NOT NULL COMMENT '选项内容',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_question` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题选项表';

-- 讲师表
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '讲师姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `title` varchar(100) DEFAULT NULL COMMENT '职称',
  `description` text DEFAULT NULL COMMENT '讲师介绍',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='讲师表';

-- 用户表
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `nickname` varchar(50) NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `points` int(11) NOT NULL DEFAULT 0 COMMENT '积分',
  `level` int(11) NOT NULL DEFAULT 1 COMMENT '用户等级',
  `watch_count` int(11) NOT NULL DEFAULT 0 COMMENT '观看视频数',
  `is_vip` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是VIP：0-否，1-是',
  `vip_level` int(11) DEFAULT 0 COMMENT 'VIP等级',
  `vip_name` varchar(50) DEFAULT NULL COMMENT 'VIP名称',
  `vip_expire_date` date DEFAULT NULL COMMENT 'VIP过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 用户等级表
CREATE TABLE `user_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `level` int(11) NOT NULL COMMENT '等级',
  `name` varchar(50) NOT NULL COMMENT '等级名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '等级图标',
  `need_points` int(11) NOT NULL DEFAULT 0 COMMENT '所需积分',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户等级表';

-- 观看记录表
CREATE TABLE `watch_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `video_id` bigint(20) NOT NULL COMMENT '视频ID',
  `progress` int(11) NOT NULL DEFAULT 0 COMMENT '观看进度(百分比)',
  `last_position` int(11) NOT NULL DEFAULT 0 COMMENT '上次观看位置(秒)',
  `watch_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '观看时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_video` (`user_id`, `video_id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_video` (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='观看记录表';

-- 视频答题记录表
CREATE TABLE `video_answer_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `video_id` bigint(20) NOT NULL COMMENT '视频ID',
  `question_id` bigint(20) NOT NULL COMMENT '问题ID',
  `user_answer` varchar(50) NOT NULL COMMENT '用户答案',
  `is_correct` tinyint(1) NOT NULL COMMENT '是否正确：0-错误，1-正确',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_video_question` (`user_id`, `video_id`, `question_id`),
  KEY `idx_user_video` (`user_id`, `video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频答题记录表';

-- 积分记录表
CREATE TABLE `points_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `points` int(11) NOT NULL COMMENT '积分',
  `type` varchar(20) NOT NULL COMMENT '类型：income-收入，expense-支出',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

-- 积分兑换项目表
CREATE TABLE `points_exchange_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `points` int(11) NOT NULL COMMENT '所需积分',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `stock` int(11) NOT NULL DEFAULT 0 COMMENT '库存',
  `exchange_count` int(11) NOT NULL DEFAULT 0 COMMENT '兑换次数',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分兑换项目表';

-- 积分兑换记录表
CREATE TABLE `points_exchange_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_id` bigint(20) NOT NULL COMMENT '兑换项ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `title` varchar(100) NOT NULL COMMENT '商品标题',
  `points` int(11) NOT NULL COMMENT '消耗积分',
  `count` int(11) NOT NULL DEFAULT 1 COMMENT '兑换数量',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态：0-待处理，1-已完成，2-已取消',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user` (`user_id`),
  KEY `idx_item` (`item_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分兑换记录表';

-- 初始化用户等级数据
INSERT INTO `user_level` (`level`, `name`, `icon`, `need_points`) VALUES
(1, '初学者', '/images/level/1.png', 0),
(2, '养生新手', '/images/level/2.png', 100),
(3, '养生达人', '/images/level/3.png', 500),
(4, '养生专家', '/images/level/4.png', 1000),
(5, '养生大师', '/images/level/5.png', 2000);

-- 初始化视频分类数据
INSERT INTO `video_category` (`name`, `icon`, `sort`) VALUES
('藏密珍宝', '/images/category/1.png', 1),
('八段锦', '/images/category/2.png', 2),
('太极养生', '/images/category/3.png', 3); 