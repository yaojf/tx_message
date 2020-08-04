CREATE TABLE `t_tx_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `message_type` tinyint(4) NOT NULL COMMENT '消息类型 1:MQ',
  `message_content` text COMMENT '消息内容 JSON',
  `retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '当前重试次数',
  `max_retry_count` int(11) NOT NULL DEFAULT '3' COMMENT '最大重试次数',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息状态 0:未发送 1:发送成功 2:发送失败',
  `app_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名',
  `app_env` varchar(32) NOT NULL DEFAULT '' COMMENT '环境',
  `expand` varchar(1024) DEFAULT NULL COMMENT '扩展信息 JSON',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `edit_time` datetime NOT NULL COMMENT '修改时间',
  `editor` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
  `creator` varchar(32) NOT NULL COMMENT '创建人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
);