package com.netease.yixin.publicaccount.datang.meta;

public class ApiCode {
	private String result;

	private int code;

	private String errmsg;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return "ok".equals(this.result);
	}

}
