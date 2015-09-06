package com.netease.yixin.publicaccount.datang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.libs.http.HttpClientUtil;
import com.netease.yixin.publicaccount.datang.meta.PAConfig;
import com.netease.yixin.publicaccount.datang.util.JsonConverter;
import com.netease.yixin.publicaccount.datang.util.JsonMsgUtil;
import com.netease.yixin.sdk.meta.JsonResponse;

@Service
public class HttpService {

	@Autowired
	HttpClientUtil httpClientUtil;
	@Autowired
	MarkAsyncService markService;
	@Resource
	PAccountService paccountService;
	@Resource
	ConfigService configService;
	@Resource
	MessageService messageService;

	private static Logger logger = LoggerFactory.getLogger(HttpService.class);

	public void aiRemark(long pid, String urs, String quest, String md5, String resolved) {
		List<NameValuePair> list;
		PAConfig config = paccountService.getAccount(pid);
		if (config == null) {
			logger.error(" cannot find pid url " + pid, urs);
		}
		String url = config.getJunFeed();
		list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("gameid", config.getAiGameId()));
		list.add(new BasicNameValuePair("question", quest));
		list.add(new BasicNameValuePair("answer", md5));
		list.add(new BasicNameValuePair("md5", md5));
		list.add(new BasicNameValuePair("evaluate", resolved));
		list.add(new BasicNameValuePair("remarks", "yixin|" + urs));
		markService.submitMark(url, list);
 	}

	public void mmRemark(long pid, String urs, String aid, String nps, String solved, String gm_mark) {
		PAConfig config = paccountService.getAccount(pid);
		if (config == null) {
			logger.error(" cannot find pid url " + pid, urs);
		}
		String url = config.getMmFeed();
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("pid", config.getGameId()));
		list.add(new BasicNameValuePair("act", "evaluate"));
		list.add(new BasicNameValuePair("aid", aid));
		list.add(new BasicNameValuePair("username", urs));
		list.add(new BasicNameValuePair("nps", nps));
		list.add(new BasicNameValuePair("solved", solved));
		if (gm_mark.equals("5")) {
			list.add(new BasicNameValuePair("feeling", "5"));
			list.add(new BasicNameValuePair("react_speed", "5"));
			list.add(new BasicNameValuePair("gm_service_quality", "5"));
			list.add(new BasicNameValuePair("gm_communication_skill", "5"));
		}
		list.add(new BasicNameValuePair("gm_mark", gm_mark));
		markService.submitMark(url, list);
	}

	public String askAI(long pid, String urs, String content) {
		PAConfig config = paccountService.getAccount(pid);
		if (config == null) {
			logger.error(" cannot find pid url " + pid, urs);
			return null;
		}
		String url = config.getJunAsk();
		List<NameValuePair> list = new ArrayList<NameValuePair>(2);
		list.add(new BasicNameValuePair("ques", content));
		list.add(new BasicNameValuePair("user", "yixin|" + urs));
		list.add(new BasicNameValuePair("output", config.getPasswd()));
		try {
			String res = this.httpClientUtil.postData(url, list);
			logger.error(config.getPasswd() + url + " " + res);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(" aiAsk bind error", e);
		}
		return "";
	}

	public String getToken(long pid, String urs) {
		PAConfig config = paccountService.getAccount(pid);
		if (config == null) {
			logger.error(" cannot find pid url " + pid, urs);
			return null;
		}
		String url = config.getToken();
		List<NameValuePair> list = new ArrayList<NameValuePair>(3);
		list.add(new BasicNameValuePair("pid", config.getGameId()));
		list.add(new BasicNameValuePair("type", "2"));
		list.add(new BasicNameValuePair("uid", urs));
		try {
			String res = httpClientUtil.postData(url, list);
			String[] result = StringUtils.split(res, ":");
			if (result[0].equals("0")) {
				return result[1];
			} else {
				logger.error("error get token" + urs);
				return "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提单
	 * 
	 * @param url
	 * @param urs
	 * @param content
	 * @return
	 */
	public String askMM(long pid, long yid, String urs, String content) {
		PAConfig config = paccountService.getAccount(pid);
		if (config == null) {
			logger.error(" cannot find pid url " + pid, content);
			return null;
		}
		String url = config.getMmAsk();
		List<NameValuePair> list = new ArrayList<NameValuePair>(4);
		list.add(new BasicNameValuePair("pid", config.getGameId()));
		list.add(new BasicNameValuePair("msg", content));
		list.add(new BasicNameValuePair("yx", yid + ""));
		list.add(new BasicNameValuePair("urs", urs));
		try {
			String res = httpClientUtil.postData(url, list);
			if (StringUtils.isNotBlank(res)) {
				if (res.indexOf("true") == -1) {
					// 有点难看啊,先WS吧
					logger.error(" return res " + res);
					res = httpClientUtil.postData(url, list);
					if (!(StringUtils.isNotBlank(res) && res.indexOf("true") != -1)) {
						messageService.sendP2PTxt(config.getPid(), config.getSid(), yid, "消息留言失败");
					}
				}
			} else {
				logger.error(" return null res " + pid, content);
			}
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public JSONObject getNoTitleMat(long pid, long mid) {
		String url = String.format(configService.getPublicUrl() + "%s", "getNoTitleMat?mid=" + mid + "&pid=" + pid);
		return getCommonMid(url);
	}

	public JSONObject getCommonMid(String url) {
		try {
			String res = this.httpClientUtil.getData(url);
			JsonResponse jres = JSON.parseObject(res, JsonResponse.class);
			if (jres != null && jres.getCode() == 200) {
				Object j = jres.getResult();
				if (j instanceof JSONObject) {
					return (JSONObject) j;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(" getSelf bind error", e);
		}
		return null;
	}

	public JSONObject getMid(long pid, long mid) {
		String url = String.format(configService.getPublicUrl() + "%s", "getMatforDt?mid=" + mid + "&pid=" + pid);
		return getCommonMid(url);
	}
}
