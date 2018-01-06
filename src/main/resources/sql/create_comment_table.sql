CREATE TABLE `jj_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `jinju_id` int(10) NOT NULL COMMENT '金句id',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `up_vote_count` int(6) NOT NULL COMMENT '点赞总数',
  `down_vote_count` int(6) NOT NULL COMMENT '点踩总数',
  `comment_count` int(6) NOT NULL COMMENT '二级评论总数',
  `parent_id` int(1) DEFAULT NULL COMMENT '上级评论id（二级评论时使用，为0时表此为一级评论）',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;