package com.netease.yixin.publicaccount.datang.meta;

public class UserContent implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7023256936528134820L;
	public long yid;
	public long pid;
	private StringBuilder sb;
	private String urs;

	public UserContent(long yid, long pid, String urs, String content) {
		this.yid = yid;
		this.pid = pid;
		this.urs = urs;
		this.sb = new StringBuilder().append(content);
	}

	public void append(String content) {
		this.sb.append(content);
	}

	public long getYid() {
		return yid;
	}

	public void setYid(long yid) {
		this.yid = yid;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public StringBuilder getSb() {
		return sb;
	}

	public String getContent() {
		return sb.toString();
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}

	public String getUrs() {
		return urs;
	}

	public void setUrs(String urs) {
		this.urs = urs;
	}

}
