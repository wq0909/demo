package com.netease.yixin.publicaccount.datang.util;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.netease.yixin.sdk.meta.MessageType;

public class JsonMsgUtil {

	static Logger logger = Logger.getLogger(JsonMsgUtil.class);

	public static void doSingleHandleMsg(JSONObject jo, long pid) throws Exception {
		int type = jo.getIntValue("type");
		String md5 = null;
		String uri = null;
		Integer duration = null;
		Integer fileSize = null;
		String fileName = null;

		String msg = null;
		if (type >= 101) {
			msg = jo.getString("jsonMessage");
			type = MessageType.JSON_PUBLIC_MSG.intValue();
		} else {
			msg = jo.getString("content");
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
				logger.info("msg:" + msg);
				logger.info("uri:" + uri);
				logger.info("duration:" + duration);
				logger.info("fileSize:" + fileSize);
				logger.info("fileName:" + fileName);
			}
		}
	}

}