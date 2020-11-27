CREATE TABLE `t_dubbo_invoke_req_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `arg_json` varchar(2500) COLLATE utf8_bin DEFAULT NULL COMMENT 'dubbo请求参数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8mb4;


CREATE TABLE `t_user` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8mb4;

CREATE TABLE `t_register_config` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) DEFAULT NULL COMMENT 'host地址',
  `ip` varchar(60) DEFAULT NULL COMMENT '注册中心真实ip',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` smallint(2) DEFAULT NULL COMMENT '注册中心类型：1 zk,2 nacos',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
