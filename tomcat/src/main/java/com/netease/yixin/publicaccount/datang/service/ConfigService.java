package com.netease.yixin.publicaccount.datang.service;

import org.springframework.stereotype.Service;

import com.netease.libs.spring.BasePropertyPlaceholderConfigurer.AnnotationProperty;

@Service
public class ConfigService {

	@AnnotationProperty(value = "yixinUrl")
	private String yixinUrl;

	@AnnotationProperty(value = "selfUrl")
	private String selfUrl;

	@AnnotationProperty(value = "publicUrl")
	private String publicUrl;

	@AnnotationProperty(value = "online")
	private int online = 0;

	@AnnotationProperty(value = "pid")
	private long pid = 4002;

	public String getYixinUrl() {
		return yixinUrl;
	}

	public void setYixinUrl(String yixinUrl) {
		this.yixinUrl = yixinUrl;
	}

	public String getSelfUrl() {
		return selfUrl;
	}

	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	public long getPid(long spid) {
		if (spid == 4002) {
			return pid;
		} else {
			return spid;
		}
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public void setSelfUrl(String selfUrl) {
		this.selfUrl = selfUrl;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

}
