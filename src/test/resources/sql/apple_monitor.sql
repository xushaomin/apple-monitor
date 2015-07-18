# Host: 120.25.126.11  (Version: 5.6.24-log)
# Date: 2015-07-17 11:51:28
# Generator: MySQL-Front 5.3  (Build 4.214)

/*!40101 SET NAMES utf8 */;

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
  `disorder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6840 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_app_config"
#

DROP TABLE IF EXISTS `t_app_config`;
CREATE TABLE `t_app_config` (
  `id` int(11) NOT NULL DEFAULT '0',
  `cluster_id` int(11) DEFAULT NULL COMMENT '应用集群ID',
  `app_config` text NOT NULL COMMENT 'JMX的XML配置',
  `state` smallint(3) NOT NULL DEFAULT '0' COMMENT '状态',
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
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "t_app_info"
#

DROP TABLE IF EXISTS `t_app_info`;
CREATE TABLE `t_app_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` int(11) NOT NULL DEFAULT '0' COMMENT '节点ID',
  `cluster_id` int(11) NOT NULL DEFAULT '0' COMMENT '分组ID',
  `app_name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `web_port` int(11) DEFAULT NULL COMMENT 'Web端口',
  `web_context` varchar(255) DEFAULT NULL COMMENT 'Web访问路径',
  `jmx_port` int(11) DEFAULT NULL COMMENT 'Jmx端口',
  `service_port` int(11) DEFAULT NULL COMMENT '服务端口',
  `install_path` varchar(255) DEFAULT NULL COMMENT '安装目录',
  `disorder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `state` smallint(3) DEFAULT NULL COMMENT '状态 0=停用 =1启用 =2删除',
  `conf_dataid` varchar(64) DEFAULT NULL COMMENT '配置项',
  `conf_group` varchar(64) DEFAULT NULL COMMENT '配置项',
  `conf_env` varchar(64) DEFAULT NULL COMMENT '配置项',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6842 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
