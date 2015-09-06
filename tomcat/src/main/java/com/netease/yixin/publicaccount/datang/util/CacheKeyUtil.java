package com.netease.yixin.publicaccount.datang.util;

public class CacheKeyUtil {

	public static String getKey(Class claz, Object... args) {
		StringBuilder sb = new StringBuilder();
		sb.append(claz.getSimpleName());
		sb.append("_");
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append("_");
			}
		}
		return sb.toString();
	}
}
