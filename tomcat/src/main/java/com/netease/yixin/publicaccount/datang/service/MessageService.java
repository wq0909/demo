package com.netease.yixin.publicaccount.datang.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netease.libs.http.HttpClientUtil;
import com.netease.yixin.publicaccount.datang.meta.ApiCode;
import com.netease.yixin.publicaccount.datang.util.JsonConverter;
import com.netease.yixin.sdk.exception.YixinException;
import com.netease.yixin.sdk.meta.MessageType;

@Service
public class MessageService {

	private static Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	HttpClientUtil httpClientUtil;
	@Autowired
	PAccountService paccountService;
	@Resource
	ConfigService configService;
	@Resource
	HttpService httpService;

	public String createUrl(String path, List<NameValuePair> pairs) {
		StringBuilder sb = new StringBuilder();
		sb.append(configService.getYixinUrl()).append(path).append("?");
		int size = pairs.size();
		for (int i = 0; i < size; i++) {
			NameValuePair pair = pairs.get(i);
			sb.append(pair.getName()).append("=").append(pair.getValue());
			if (i < size - 1)
				sb.append("&");
		}
		String url = sb.toString();
		return url;
	}

	private String createUrl(String url) {
		return configService.getYixinUrl() + url;
	}

	/**
	 * 发送素材消息
	 * 
	 * @param obj
	 * @param pid
	 * @return
	 */
	public boolean sendMid(JSONObject jo, long pid, String sid, long yid) {
		int type = jo.getIntValue("type");
		String md5 = null;
		String uri = null;
		Integer duration = null;
		Integer fileSize = null;
		String fileName = null;
		String content = null;
		if (type == MessageType.JSON_PUBLIC_MSG.intValue() || type == MessageType.ACTIVITY.intValue()
				|| type == MessageType.MULTEXT_PIC.intValue()) {
			content = jo.getString("jsonMessage");
			type = MessageType.JSON_PUBLIC_MSG.intValue();
			return sendP2PJson(pid, sid, yid, content);
		} else {
			content = jo.getString("content");
			if (jo.get("etag") != null)
				md5 = jo.getString("etag");
			if (jo.get("uri") != null)
				uri = jo.getString("uri");
			if (jo.get("duration") != null)
				duration = jo.getIntValue("duration");
			if (jo.get("fileSize") != null)
				fileSize = jo.getIntValue("fileSize");
			if (jo.get("fileName") != null)
				fileName = jo.getString("fileName");
			{
				logger.info("type:" + type);
				logger.info("md5:" + md5);
				logger.info("msg:" + content);
				logger.info("uri:" + uri);
				logger.info("duration:" + duration);
				logger.info("fileSize:" + fileSize);
				logger.info("fileName:" + fileName);
			}
			return sendP2P(pid, sid, yid, type, content, md5, fileName, fileSize, duration, uri);
		}
	}

	public boolean sendP2PTxt(long pid, String sid, long targetId, String content) {
		if (targetId <= 0)
			return false;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		fillBasic(pid, sid, params);
		params.add(new BasicNameValuePair("uid", String.valueOf(targetId)));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("type", String.valueOf(MessageType.TEXT.intValue())));
		return postData(createUrl("sendP2pMsg"), params, ApiCode.class).isSuccess();
	}

	public boolean sendGroupTxt(long pid, String sid, String content) throws IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		fillBasic(pid, sid, params);
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("type", String.valueOf(MessageType.TEXT.intValue())));
		return postData(createUrl("sendMsg"), params, ApiCode.class).isSuccess();
	}

	public boolean sendP2P(long pid, String sid, Long targetUserId, int type, String content, String md5,
			String fileName, int fileSize, int duration, String uri) {
		if (targetUserId == null || targetUserId <= 0)
			return false;
		return send(createUrl("sendP2pMsg"), pid, sid, targetUserId, type, content, md5, fileName, fileSize, duration,
				uri).isSuccess();
	}

	private ApiCode send(String postUrl, long pid, String sid, Long targetUserId, int type, String content, String md5,
			String fileName, int fileSize, int duration, String uri) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		fillBasic(pid, sid, params);
		if (targetUserId != null && targetUserId > 0)
			params.add(new BasicNameValuePair("uid", String.valueOf(targetUserId)));
		params.add(new BasicNameValuePair("type", String.valueOf(type)));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("md5", md5));
		params.add(new BasicNameValuePair("fileName", fileName));
		params.add(new BasicNameValuePair("fileSize", String.valueOf(fileSize)));
		params.add(new BasicNameValuePair("duration", String.valueOf(duration)));
		params.add(new BasicNameValuePair("uri", uri));
		return postData(postUrl, params, ApiCode.class);
	}

	private void fillBasic(long pid, String sid, List<NameValuePair> params) {
		params.add(new BasicNameValuePair("pid", String.valueOf(pid)));
		params.add(new BasicNameValuePair("sid", String.valueOf(sid)));
	}

	public ApiCode sendGroup(long pid, String sid, int type, String content, String md5, String fileName, int fileSize,
			int duration, String uri) {
		return send(createUrl("sendMsg"), pid, sid, null, type, content, md5, fileName, fileSize, duration, uri);
	}

	private ApiCode postData(String createUrl, List<NameValuePair> params, Class<ApiCode> class1) {
		try {
			String res = httpClientUtil.postData(createUrl("sendP2pMsg"), params);
			return JsonConverter.mapToObject(ApiCode.class, res);
		} catch (IOException e) {
			e.printStackTrace();
			throw new YixinException("ERROR " + e.getMessage());
		}
	}

	private ApiCode postEntity(String url, String json) {
		try {
			StringEntity entify = new StringEntity(json, "text/xml", "UTF-8");
			return postData(url, entify, ApiCode.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new YixinException("ERROR " + e.getMessage());
		}
	}

	private <T> T postData(String url, final StringEntity entity, Class<T> tClass) throws YixinException {
		try {
			String res = httpClientUtil.postData(url, entity);
			return convertToObject(tClass, res);
		} catch (IOException e) {
			throw new YixinException("网络连接错误" + e.getMessage());
		}
	}

	private <T> T convertToObject(Class<T> tClass, String res) throws IOException, JsonParseException,
			JsonMappingException {
		if (StringUtils.isNotBlank(res)) {
			return JsonConverter.mapToObject(tClass, res);
		} else {
			throw new YixinException("数据转换错误");
		}
	}

	/**
	 * 发送JSON的格式数据到用户
	 * 
	 * @param account
	 * @param targetUserId
	 * @param json
	 * @return
	 */
	public boolean sendP2PJson(long pid, String sid, Long targetUserId, String json) {
		if (targetUserId == null || targetUserId <= 0)
			return false;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		fillBasic(pid, sid, params);
		params.add(new BasicNameValuePair("uid", String.valueOf(targetUserId)));
		params.add(new BasicNameValuePair("type", String.valueOf(MessageType.JSON_PUBLIC_MSG.intValue())));
		return postEntity(createUrl("sendP2pMsg", params), json).isSuccess();
	}

	/**
	 * 群发JSON的多图文数据
	 * 
	 * @param account
	 * @param json
	 * @return
	 */
	public boolean sendGroupJson(long pid, String sid, String json) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		fillBasic(pid, sid, params);
		params.add(new BasicNameValuePair("type", String.valueOf(MessageType.JSON_PUBLIC_MSG.intValue())));
		return postEntity(createUrl("sendMsg", params), json).isSuccess();
	}

	public boolean sendMid(Long mid, Long pid, String sid, long yid) {
		JSONObject js = httpService.getMid(pid, mid);
		if (js == null) {
			return false;
		} else {
			return sendMid(js, pid, sid, yid);
		}
	}

}
