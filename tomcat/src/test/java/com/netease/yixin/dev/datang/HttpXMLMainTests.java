package com.netease.yixin.dev.datang;

import java.io.IOException;

import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.libs.http.HttpClientUtil;
import com.netease.libs.http.meta.ApplicationType;
import com.netease.yixin.sdk.meta.xml.ResponseMessage;
import com.netease.yixin.sdk.meta.xml.request.ReqTextMessage;
import com.netease.yixin.sdk.meta.xml.response.Article;
import com.netease.yixin.sdk.meta.xml.response.Music;
import com.netease.yixin.sdk.meta.xml.response.ResMusicMessage;
import com.netease.yixin.sdk.meta.xml.response.ResNewsMessage;
import com.netease.yixin.sdk.utils.XmlUtil;
import com.thoughtworks.xstream.XStream;

public class HttpXMLMainTests {

	private static String url = "http://localhost/cgi-bin/token";
	// private static String host = "http://localhost:8080/subscribe.wx";
	private static String host = "http://192.168.1.108:8080/servlet/robot";
	static HttpClientUtil client = new HttpClientUtil();

	public static XStream xStream = new XStream();
	static {
		xStream.alias("xml", ResNewsMessage.class);
		xStream.alias("item", Article.class);
		xStream.alias("xml", ResMusicMessage.class);
		xStream.alias("Music", Music.class);
		xStream.alias("xml", ReqTextMessage.class);
	}

	public static void main(String[] args) throws IOException {
		testXML();
	}

	private static void testXML() throws IOException {
		String url = host + "?timestamp=1&nonce=1&echostr=859834&signature=f6806c5d063a4ce7c576de0b566ead0b90f5d6a9";
		ReqTextMessage msg = new ReqTextMessage();
		msg.setContent("hello");
		msg.setToUserName("1");
		msg.setFromUserName("12404");
		msg.setMsgId(1);
		String xml = xStream.toXML(msg);
		System.out.println(xml);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// System.out.println(a);
		StringEntity entify = new StringEntity(xml, "text/xml", "UTF-8");
		// StringEntity entify = new StringEntity(a, "application/json",
		// "UTF-8");
		try {
			long start = System.currentTimeMillis();
			String xmlText = "";
			xmlText = client.getData("http://www.163.com", ApplicationType.XML);
			System.out.println("postData" + (System.currentTimeMillis() - start));
			start = System.currentTimeMillis();
			xmlText = client.postData(url, entify, ApplicationType.XML);
			System.out.println("postData" + (System.currentTimeMillis() - start));
			System.out.println(xmlText);
			start = System.currentTimeMillis();
			ResponseMessage s = XmlUtil.fromXml(xmlText);
			System.out.println(s.getMsgId());
			System.out.println(System.currentTimeMillis() - start);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
