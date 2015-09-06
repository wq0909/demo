package com.netease.yixin.publicaccount.datang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.netease.libs.http.HttpClientUtil;
import com.netease.yixin.publicaccount.datang.meta.PAConfig;
import com.netease.yixin.publicaccount.datang.util.CmdFakeUtil;
import com.netease.yixin.sdk.meta.MsgSubType;

@Service
public class MarkAsyncService {
	@Autowired
	HttpClientUtil httpClientUtil;
	@Resource
	MessageService messageService;
	@Resource
	CacheStatuService cacheStatuService;
	@Resource
	PAccountService paccountService;
	@Resource
	ConfigService config;

	private static Logger logger = LoggerFactory.getLogger(MarkAsyncService.class);

	@Async
	public void submitMark(String url, List<NameValuePair> params) {
		try {
			String res = httpClientUtil.getData(url, params);
			logger.error("mark " + url + " " + res + " " + (res.startsWith("1") ? "成功" : "失败"));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("submit mark error", e);
		}
	}

	@Async
	public void asyncHint(long pid, long yid, String content) {
		PAConfig config = paccountService.getAccount(pid);
		messageService.sendP2PTxt(config.getPid(), config.getSid(), yid, content);
	}

	@Async
	public void syncMessage(String urs, long pid, long yid) {
		List<String> msgs = cacheStatuService.getLeftTxtMsg(urs, pid);
		PAConfig paconfig = paccountService.getAccount(pid);
		for (String msg : msgs) {
			messageService.sendP2PTxt(paconfig.getPid(), paconfig.getSid(), yid, msg);
		}
		List<String> mids = cacheStatuService.getLeftJsonMsg(urs, pid);
		for (String mid : mids) {
			try {
				messageService.sendMid(Long.valueOf(mid), pid, paconfig.getSid(), yid);
			} catch (Exception ex) {
				logger.error(" get mid error " + mid + " " + yid);
			}
		}
		List<String> rates = cacheStatuService.getLeftRateMsg(urs, pid);
		for (String qid : rates) {
			List<ImmutablePair<String, String>> pairs = new ArrayList<ImmutablePair<String, String>>();
			/*pairs.add(new ImmutablePair<String, String>("提交", UrlFillUtil.getMMMarkUrl(config.getSelfUrl(),
					paconfig.getPid(), yid, qid)));*/
			String content = CmdFakeUtil.fillContent(MsgSubType.EVENT_MMMARK, "", pairs);
			messageService.sendP2PJson(paconfig.getPid(), paconfig.getSid(), yid, content);
		}
		cacheStatuService.clearAll(urs, pid);
	}
}
