package com.netease.yixin.dev.datang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.netease.libs.http.HttpClientUtil;

public class HttpPaConfigMainTests {

	private static String CRM_URL = "http://localhost:7070/";

	static HttpClientUtil client = new HttpClientUtil();

	public static void main(String[] args) throws IOException {
		testCreateCrmConfigAccount();
	}

	private static void testCreateCrmConfigAccount() {
		String h = CRM_URL + "paconfig/add";
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("passwd", "e10adc3949ba59abbe56e057f20f883e"));
		list.add(new BasicNameValuePair("account", "dt"));
		list.add(new BasicNameValuePair("junAsk", "dt2.chatbot.nie.163.com"));
		list.add(new BasicNameValuePair("junFeed", "chatbot.nie.163.com:8080"));
		list.add(new BasicNameValuePair("mmAsk", "123.125.48.32:1997"));
		list.add(new BasicNameValuePair("mmFeed", "123.58.175.25:8092"));
		list.add(new BasicNameValuePair("token", "123.125.48.32:1890"));
		list.add(new BasicNameValuePair("tokenVaild", "123.58.175.25:8092"));
		try {
			System.out.println(client.getData(h, list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
