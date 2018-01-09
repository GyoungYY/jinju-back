CREATE TABLE `jj_collect` (
  `jinju_id` int(10) NOT NULL COMMENT '金句id',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `collect` int(1) NOT NULL COMMENT '收藏',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`jinju_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

