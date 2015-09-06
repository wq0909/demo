package com.netease.yixin.publicaccount.datang.util;

public class KeyUtils {

	private static final String FORMAT_TOKEN = "token_%s_%s_";
	private static final String FORMAT_MM = "mm_%s_%s_";
	private static final String FORMAT_CONTENT = "ask_%s_%s_";
	private static final String FORMAT_USERS = "users_";
	private static final String FORMAT_PACONFIG = "paconfig_%s_";
	private static final String FORMAT_URS_PID = "usspid_%s_%s_";
	private static final String FORMAT_MID_PID = "mid_%s_%s_";
	private static final String FORMAT_MARK_PID = "mark_%s_%s_";

	public static String formatToken(long pid, long yid) {
		return String.format(FORMAT_TOKEN, pid, yid);
	}

	public static String formatMM(long pid, long yid) {
		return String.format(FORMAT_MM, pid, yid);
	}

	public static String formatAsk(long pid, long yid) {
		return String.format(FORMAT_CONTENT, pid, yid);
	}

	public static String formatTxtMsgKey(String urs, long pid) {
		return String.format(FORMAT_URS_PID, urs, pid);
	}
	
	public static String formatJsonMsgKey(String urs, long pid) {
		return String.format(FORMAT_MID_PID, urs, pid);
	}
	
	public static String formatRateMsgKey(String urs, long pid) {
		return String.format(FORMAT_MARK_PID, urs, pid);
	}

	public static String formatList() {
		return FORMAT_USERS;
	}

	public static String formatPaConfig(long pid) {
		return String.format(FORMAT_PACONFIG, pid);
	}

}
