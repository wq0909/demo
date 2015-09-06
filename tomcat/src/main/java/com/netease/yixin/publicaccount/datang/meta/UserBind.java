package com.netease.yixin.publicaccount.datang.meta;

import com.netease.framework.dao.annotation.NTable;

@NTable(alias = "DEV_DA_USERBIND")
public class UserBind implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4920301318386479973L;

	private long pid;

	private long yid;

	private String urs;

	private String ip;

	private int status;

	private long updateTime;

	public UserBind() {
	}

	public UserBind(long yid, long pid, String urs) {
		this.yid = yid;
		this.pid = pid;
		this.urs = urs;
	}

	public long getYid() {
		return yid;
	}

	public void setYid(long yid) {
		this.yid = yid;
	}

	public String getUrs() {
		return urs;
	}

	public void setUrs(String urs) {
		this.urs = urs;
	}

	public String getIp() {
		return ip;
	}

	public int getStatus() {
		return status;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

}
