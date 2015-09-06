CREATE TABLE `DEV_DA_USERBIND` ( 
	`yid` bigint(20) NOT NULL COMMENT '用户帐号id', 
	`pid` bigint(20) NOT NULL COMMENT '公众帐号id', 
	`urs` varchar(128) DEFAULT '' COMMENT 'urs', 
	`ip` varchar(64) DEFAULT '' COMMENT 'ip',
	`updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间', 
	`status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',  
	PRIMARY KEY (`yid`,`pid`), 
	KEY `IDX_DEV_DA_USERBIND_URS` (`pid`,`urs`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公众大塘游戏应用' /* BF=pid, POLICY=PUBLICACCOUNT */; 


﻿CREATE TABLE `DEV_DA_PACONFIG` (
	`pid` bigint(20) NOT NULL COMMENT '公众帐号id', 
	`account` varchar(128) NOT NULL DEFAULT '' COMMENT 'urs', 
	`passwd` varchar(128) NOT NULL DEFAULT '' COMMENT 'ip',
	`junAsk` varchar(128) NOT NULL DEFAULT '' COMMENT '精灵URL', 
	`junFeed` varchar(128) NOT NULL DEFAULT '' COMMENT '精灵反馈URL',
	`mmAsk` varchar(128) NOT NULL DEFAULT '' COMMENT '人工URL', 
	`mmFeed` varchar(128) NOT NULL DEFAULT '' COMMENT '人工反馈URL', 
	`token` varchar(128) NOT NULL DEFAULT '' COMMENT 'TOKENURL',
	`tokenVaild` varchar(128) NOT NULL DEFAULT '' COMMENT 'TOKEN反馈URL',
        `gameId` varchar(128) NOT NULL DEFAULT '' COMMENT '游戏ID', 
	`aiGameId` varchar(128) NOT NULL DEFAULT '' COMMENT '精灵游戏ID',
        `smid` bigint(20) NOT NULL COMMENT '自助服务ID', 
        `zmid` bigint(20) NOT NULL COMMENT '帐号服务ID', 
	`status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',  
	PRIMARY KEY (`pid`), 
	KEY `IDX_DEV_DA_PACONFIG_URS` (`account`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏应用帐号' /* BF=pid, POLICY=PUBLICACCOUNT */; 
