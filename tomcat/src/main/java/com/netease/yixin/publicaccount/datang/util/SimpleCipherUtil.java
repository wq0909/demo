package com.netease.yixin.publicaccount.datang.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

import com.netease.libs.security.SimpleCipherDES;

public class SimpleCipherUtil {

	// static InputStream is =
	// SimpleCipherUtil.class.getClass().getResourceAsStream("/auth.key");
	static SimpleCipherDES des = new SimpleCipherDES();
	static {
		try {
			des.setKey(new ClassPathResource("auth.key").getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String encode(String st) {
		return des.simpleStringEncrypt(st);
	}

	public static String decode(String st) {
		return des.simpleStringDecrypt(st);
	}

}
