package com.netease.yixin.publicaccount.datang.util;

import org.springframework.ui.ModelMap;

public class DataPack {

	private static final String CODE = "code";
	private static final String RESULT = "result";
	private static final String MSG = "msg";

	public static void packOk(ModelMap model, Object result) {
		model.put(CODE, Constant.CODE_SUCCESS);
		model.put(RESULT, result);
		model.put(MSG, "");
	}

	public static void packOk(ModelMap model) {
		model.put(CODE, Constant.CODE_SUCCESS);
		model.put(RESULT, true);
		model.put(MSG, "");
	}

	public static void packFail(ModelMap model, int code, String msg) {
		model.put(CODE, code);
		model.put(RESULT, false);
		model.put(MSG, msg);
	}

	public static void packFail(ModelMap model, int code) {
		model.put(CODE, code);
		model.put(RESULT, "false");
	}
	
	public static void packOk(ModelMap model, int code) {
		model.put(CODE, code);
		model.put(RESULT, true);
	}

}
