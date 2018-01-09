CREATE TABLE `jj_vote` (
  `jinju_id` int(10) NOT NULL COMMENT '金句id',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `type` int(1) NOT NULL COMMENT '类型 1：点赞，2：点踩',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`jinju_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

