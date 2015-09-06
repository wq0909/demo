package com.netease.yixin.publicaccount.datang.meta;

import com.netease.framework.dao.annotation.NTable;

@NTable(alias = "DEV_DA_PALEFTMSG")
public class LeftMessage {

	private long id;
	private long pid;
	private String urs;
	private String content;
	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getCreateTime() {
		return createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getUrs() {
		return urs;
	}

	public void setUrs(String urs) {
		this.urs = urs;
	}

}
