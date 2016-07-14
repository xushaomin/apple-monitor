DROP TABLE IF EXISTS `t_alert_contact`;
CREATE TABLE `t_alert_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `mobile` varchar(128) DEFAULT NULL COMMENT '手机',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_alert_group"
#

DROP TABLE IF EXISTS `t_alert_group`;
CREATE TABLE `t_alert_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_alert_group_contact"
#

DROP TABLE IF EXISTS `t_alert_group_contact`;
CREATE TABLE `t_alert_group_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT '分组ID',
  `contact_id` int(11) DEFAULT NULL COMMENT '联系人ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_app_cluster"
#

DROP TABLE IF EXISTS `t_app_cluster`;
CREATE TABLE `t_app_cluster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cluster_name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `cluster_desc` varchar(255) DEFAULT NULL COMMENT '应用描述',
  `is_cluster` smallint(3) DEFAULT NULL COMMENT '是否集群 =1是 =0否',
  `app_num` int(11) DEFAULT NULL COMMENT '集群应用的数量',
  `group_id` int(11) DEFAULT NULL,
  `disorder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_app_config"
#

DROP TABLE IF EXISTS `t_app_config`;
CREATE TABLE `t_app_config` (
  `id` int(11) NOT NULL DEFAULT '0',
  `cluster_id` int(11) DEFAULT NULL COMMENT '应用集群ID',
  `app_config` varchar(2550) NOT NULL DEFAULT '' COMMENT 'JMX的XML配置',
  `state` smallint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `is_alert` tinyint(1) DEFAULT '0' COMMENT '是否报警 =1是 =0否',
  `alert_group_id` int(11) DEFAULT NULL COMMENT '报警分组ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_app_downtime"
#

DROP TABLE IF EXISTS `t_app_downtime`;
CREATE TABLE `t_app_downtime` (
  `id` int(11) NOT NULL DEFAULT '0',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `recording_start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `recording_end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "t_app_downtime_history"
#

DROP TABLE IF EXISTS `t_app_downtime_history`;
CREATE TABLE `t_app_downtime_history` (
  `id` int(11) NOT NULL DEFAULT '0',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "t_app_group"
#

DROP TABLE IF EXISTS `t_app_group`;
CREATE TABLE `t_app_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_app_info"
#

DROP TABLE IF EXISTS `t_app_info`;
CREATE TABLE `t_app_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` int(11) NOT NULL DEFAULT '0' COMMENT '节点ID',
  `cluster_id` int(11) NOT NULL DEFAULT '0' COMMENT '分组ID',
  `app_name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `app_version` varchar(255) DEFAULT NULL COMMENT '应用版本',
  `web_port` int(11) DEFAULT NULL COMMENT 'Web端口',
  `web_context` varchar(255) DEFAULT NULL COMMENT 'Web访问路径',
  `jmx_port` int(11) DEFAULT NULL COMMENT 'Jmx端口',
  `service_port` int(11) DEFAULT NULL COMMENT '服务端口',
  `install_path` varchar(255) DEFAULT NULL COMMENT '安装目录',
  `log_level` varchar(255) DEFAULT 'WARN' COMMENT '日志级别',
  `start_param` varchar(512) DEFAULT NULL COMMENT '启动参数',
  `mem_max` varchar(255) DEFAULT NULL COMMENT '分配最大内存',
  `disorder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `conf_dataid` varchar(64) DEFAULT NULL COMMENT '配置项',
  `conf_group` varchar(64) DEFAULT NULL COMMENT '配置项',
  `conf_env` varchar(64) DEFAULT NULL COMMENT '配置项',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=302 DEFAULT CHARSET=utf8;

#
# Structure for table "t_node_info"
#

DROP TABLE IF EXISTS `t_node_info`;
CREATE TABLE `t_node_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(64) DEFAULT NULL COMMENT '服务器IP',
  `host` varchar(64) DEFAULT NULL COMMENT '服务器HOST',
  `model` varchar(64) DEFAULT NULL COMMENT '服务器型号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `disorder` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Structure for table "t_third_plus"
#

DROP TABLE IF EXISTS `t_third_plus`;
CREATE TABLE `t_third_plus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '类型 =1短信 =2推送 =3邮件 =4微信',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `third_key` varchar(128) DEFAULT NULL COMMENT '认证帐号',
  `third_secret` varchar(128) DEFAULT NULL COMMENT '认证密码',
  `third_extend` varchar(255) DEFAULT NULL COMMENT '认证扩展',
  `third_class` varchar(256) DEFAULT NULL COMMENT '第三方扩展执行类',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
