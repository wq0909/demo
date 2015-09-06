package com.netease.yixin.dev.datang;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.netease.libs.http.HttpClientUtil;
import com.netease.libs.utils.DateFormatUtil;

public class MMTest {

	private static String urlformat = "http://%s/cgi-bin/csa/%s";
	static HttpClientUtil client = new HttpClientUtil(1, "GBK");

	public static void main(String[] args) {
		System.out.println(DateFormatUtil.formatTimeLong(System.currentTimeMillis()));;
		//mmAsk();
	}

	public static void mmAsk() {
		String url = String.format(urlformat, "123.125.48.32:1997", "csa_server_submit.py");
		url = "http://im-chat.gameyw.netease.com:8091/fcgi-bin/csa/csa_server_submit.py";
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("pid", "dt2"));
		list.add(new BasicNameValuePair("yx", "2156"));
		// list.add(new BasicNameValuePair("truename", "2156"));
		list.add(new BasicNameValuePair("msg", "xxxxxx"));
		list.add(new BasicNameValuePair("urs", "zyobi@163.com"));
		try {
			String res = client.getData(url, list);
			System.out.println(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void evaluation() {
		String url = String.format(urlformat, "123.58.175.25:8092", "interface_answer_evaluation.py");
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("pid", "xyq"));
		list.add(new BasicNameValuePair("act", "evaluate"));
		list.add(new BasicNameValuePair("aid", "1"));
		list.add(new BasicNameValuePair("username", "qatest5@163.com"));

		list.add(new BasicNameValuePair("nps", "1"));
		list.add(new BasicNameValuePair("solved", "1"));
		list.add(new BasicNameValuePair("feeling", "1"));
		list.add(new BasicNameValuePair("react_speed", "1"));

		list.add(new BasicNameValuePair("gm_service_quality", "1"));
		list.add(new BasicNameValuePair("gm_comminication_skill", "1"));
		list.add(new BasicNameValuePair("gm_mark", "1"));

		try {
			String res = client.getData(url, list);
			System.out.println(res);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void token() {
		String url = String.format(urlformat, "123.125.48.32:1890", "csa_server_token.py");
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("pid", "dt2"));
		list.add(new BasicNameValuePair("type", "2"));
		list.add(new BasicNameValuePair("uid", "qatest5@163.com"));


		try {
			String res = client.getData(url, list);
			System.out.println(res);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void vaildtoken() {
		String url = String.format(urlformat, "123.58.175.25:8092", "guide_csa.py");
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("act", "do_login_with_token"));
		list.add(new BasicNameValuePair("token", "evaluate"));
		list.add(new BasicNameValuePair("refer", "http://www.163.com"));

		try {
			String res = client.getData(url, list);
			System.out.println(res);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
