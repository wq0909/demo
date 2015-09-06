package com.netease.yixin.dev.datang;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.core.io.ClassPathResource;

import com.netease.libs.http.HttpClientUtil;
import com.netease.libs.security.SimpleCipherDES;

public class RobotTest {

	private static String urlformat = "http://%s/cgi-bin/%s";
	static HttpClientUtil client = new HttpClientUtil(1, "GBK", "GBK");

	public static void main(String[] args) {
		aiAsk();
	}

	public static void main2(String[] args) {
		// some dirty test code for this Encrypt Wrapper Class
		String token = "12851)(*&#0)";
		System.out.println("token=" + token);
		byte[] bt = token.getBytes();
	}

	private static void aiAsk() {
		String url = String.format(urlformat, "dt2.chatbot.nie.163.com", "bot.cgi");
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		try {
			list.add(new BasicNameValuePair("ques", new String("修改密码")));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list.add(new BasicNameValuePair("user", "qatest5@163.com"));
		try {
			String res = client.postData(url, list);
			System.out.println(res);
			/*String evUrl = String.format(urlformat, "chatbot.nie.163.com:8080", "save_evaluate.py");
			list = new ArrayList<NameValuePair>(4);
			list.add(new BasicNameValuePair("gameid", "22"));
			list.add(new BasicNameValuePair("quesiton", "修改密码"));
			list.add(new BasicNameValuePair("answer", res));
			list.add(new BasicNameValuePair("evaluate", 1 + ""));
			list.add(new BasicNameValuePair("remarks", "qatest5@163.com"));
			res = client.getData(evUrl, list);
			System.out.println(res);*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
