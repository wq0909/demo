package com.netease.yixin.publicaccount.datang.util;

public class ResultBase {

	public static final int UNKOWN = -1;

	public static final int SUCCESS = 0;
	public static final int NO_ERROR = 0;
	public static final int SUCCESS_ITEM_CREATE = 100;
	public static final int SUCCESS_ITEM_EXIST = 101;

	public static final int ERROR_NO_AUTH = 200;

	public static final int ERROR_BASE = 300;
	public static final int ERROR_PARAMETER_LEAK = 300;
	public static final int ERROR_NICK_EXIST = 301;
	public static final int ERROR_STUDENT_NUM = 302;
	public static final int ERROR_PASSWORD = 303;
	public static final int ERROR_URS_ACCOUNT = 304;
	public static final int ERROR_URS_BIND = 305;
	public static final int ERROR_NO_REQUEST_BODY = 306;
	public static final int ERROR_URS_NOEXIST = 307;
	public static final int ERROR_URS_PASSWORD = 308;
	public static final int ERROR_URS_LIMIT = 309;
	public static final int ERROR_STUDENT_INFO = 310;

	public static final int ERROR_NOT_FOUND = 400;
	public static final int ERROR_MAP_NOT_FOUND = 401;
	public static final int ERROR_NUM_NOT_FOUND = 402;
	public static final int ERROR_INFO_NOT_FOUND = 403;
	public static final int ERROR_DATABASE_CON = 500;
	public static final int ERROR_INTER_ERROR = 600;

	private int result = UNKOWN;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
