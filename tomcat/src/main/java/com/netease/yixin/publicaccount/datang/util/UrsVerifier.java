package com.netease.yixin.publicaccount.datang.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netease.libs.utils.Md5Util;

public class UrsVerifier {
	private static Logger logger = LoggerFactory.getLogger(UrsVerifier.class);

	public static void main(String[] args) {
		try {
			System.out.println(check("qatest5@163.com", Md5Util.encode("qa1234"), "127.0.0.01"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String formatUrs(String urs) {
		urs = StringUtils.trim(urs);
		if (urs.indexOf("@") == -1) {
			urs += "@163.com";
		}
		return urs;
	}

	public static short check(String uid, String pass, String clientIp) throws Exception {
		short result = getVerifyResult(uid, pass, clientIp);
		return result;
	}

	public static String format(String name) {
		if (!StringUtils.isEmpty(name)) {
			return "";
		} else if (name.indexOf("@") == -1) {
			return name + "@163.com";
		} else {
			return name;
		}
	}

	private static short getVerifyResult(String uid, String pass, String clientIp) throws Exception {
		URL url = new URL("http://reg.163.com/services/userlogin?username=" + uid + "&password=" + pass + "&userip="
				+ clientIp + "&type=0&product=elearn&needmainaccount=true");

		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			logger.debug("url: " + url);

			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);

			BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String resp = is.readLine();
			is.close();

			if (resp == null) {
				logger.debug("urs verify, resp is null");
				return ResultBase.UNKOWN;
			}

			resp = resp.trim();

			logger.info(" ---------------- verify result: " + resp + " uid: " + uid);

			// TODO 用户不存在的提示
			if (resp.equals("200")) {
				return ResultBase.SUCCESS;
			} else {
				// 用户名不存在
				if (resp.equals("420")) {
					return ResultBase.ERROR_URS_NOEXIST;
				}
				// 密码错误
				if (resp.equals("460")) {
					return ResultBase.ERROR_URS_ACCOUNT;
				}
				// urs验证的其他错评
				short res_urs = Short.parseShort(resp);
				return res_urs;
			}
		} catch (Exception ex) {
			logger.info(" URS verifypass error!", ex);
			return ResultBase.UNKOWN;
		}
	}

	public static String getUrsInfo(String uid) throws Exception {
		URL url = new URL("http://reg.163.com/services/userinfo/getothersinfo?username=" + uid + "&product=elearn");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(1000);
		conn.setReadTimeout(1000);
		BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String resCode = is.readLine();
		if (null == resCode) {
			is.close();
			return null;
		}
		if ((resCode.trim()).equals("201")) {
			String info = null;
			String line = null;
			while (null != (line = is.readLine())) {
				if (line.equals("")) {
					info = is.readLine();
					is.close();
					return info;
				}
			}
		} else {
			String s_error = "get urs info error, uid:" + uid + "code is:" + resCode;
			logger.info(s_error);
		}
		is.close();
		return null;
	}
}
