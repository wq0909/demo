package com.netease.yixin.publicaccount.datang.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netease.libs.spring.BasePropertyPlaceholderConfigurer.AnnotationProperty;
import com.netease.libs.utils.HostUtil;
import com.netease.yixin.publicaccount.datang.web.controllers.IndexController;

@Service
public class WordService {

	private static final Logger log = LoggerFactory.getLogger(WordService.class);

	@PostConstruct
	public void init() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					String d = HostUtil.getHostname() + new Date();
					System.out.println(d);
					log.error(d);
					try {
						Thread.sleep(IndexController.TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	@AnnotationProperty("ddb.url")
	private String db;

	public void flush() {
		System.out.println("flush");
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
}
