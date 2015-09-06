package com.netease.yixin.publicaccount.datang.service;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 三分钟刷新消息服务
 * 
 * @author Administrator
 * 
 */
@Service(value = "wordService")
public class WordService {

	@Resource
	PAccountService paccountService;
	@Autowired
	HttpService httpService;
	@Autowired
	CacheStatuService cacheStatuService;
	@Autowired
	ConfigService configService;

	private final static int TAG_STATUS_ONLINE = 1;

	private static Logger logger = LoggerFactory.getLogger(WordService.class);

	public void flush() {
		if (configService.getOnline() == TAG_STATUS_ONLINE) {
			logger.info("flush message", new Date());
		}
	}

}
