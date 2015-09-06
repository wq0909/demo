package com.netease.yixin.publicaccount.datang.meta;

import com.netease.framework.dao.annotation.NColumn;
import com.netease.framework.dao.annotation.NPrimary;
import com.netease.framework.dao.annotation.NTable;

@NTable(alias = "DEV_DA_PACONFIG")
public class PAConfig implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7524471810189190757L;
	@NPrimary
	private long pid;
	private int status;
	private String account = ""; //账号名字 先WS的加二个字段吧
	private String passwd = ""; //精灵输出
	private String junAsk = "";
	private String junFeed = "";
	private String mmAsk = "";
	private String mmFeed = "";
	private String token = "";
	@NColumn(ignore = true)
	private String tokenVaild = "";
	@NColumn(ignore = true)
	private String sid;
	private String gameId = "dt2";
	private String aiGameId = "30";
	private long smid = 0;
	private long zmid = 0;
	

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getZmid() {
		return zmid;
	}

	public void setZmid(long zmid) {
		this.zmid = zmid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getJunAsk() {
		return junAsk;
	}

	public void setJunAsk(String junAsk) {
		this.junAsk = junAsk;
	}

	public String getJunFeed() {
		return junFeed;
	}

	public void setJunFeed(String junFeed) {
		this.junFeed = junFeed;
	}

	public String getMmAsk() {
		return mmAsk;
	}

	public void setMmAsk(String mmAsk) {
		this.mmAsk = mmAsk;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMmFeed() {
		return mmFeed;
	}

	public void setMmFeed(String mmFeed) {
		this.mmFeed = mmFeed;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public long getSmid() {
		return smid;
	}

	public void setSmid(long smid) {
		this.smid = smid;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenVaild() {
		return tokenVaild;
	}

	public void setTokenVaild(String tokenVaild) {
		this.tokenVaild = tokenVaild;
	}

	public String getAiGameId() {
		return aiGameId;
	}

	public void setAiGameId(String aiGameId) {
		this.aiGameId = aiGameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getGameId() {
		return gameId;
	}

}
