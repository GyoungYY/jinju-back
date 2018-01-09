CREATE TABLE `jinju` (
  `jinju_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '金句ID',
  `user_id` int(10) NOT NULL COMMENT '发表用户ID',
  `content` varchar(1000) NOT NULL COMMENT '金句内容',
  `type` int(5) NOT NULL COMMENT '类型',
  `up_vote_count` int(6) NOT NULL COMMENT '点赞总数',
  `down_vote_count` int(6) NOT NULL COMMENT '点踩总数',
  `collect_count` int(6) NOT NULL COMMENT '收藏总数',
  `comment_count` int(6) NOT NULL COMMENT '评论总数',
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '区分删除 1：删除 0：未删除',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`jinju_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `jinju_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `jj_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

