package com.netease.yixin.publicaccount.datang.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.netease.framework.dao.DomainDaoSupport;
import com.netease.framework.dao.cache.NRedisConnectionImpl;
import com.netease.framework.dao.templet.cache.ICacheTemplet;
import com.netease.libs.utils.DateFormatUtil;
import com.netease.yixin.publicaccount.datang.dao.PAConfigDao;
import com.netease.yixin.publicaccount.datang.meta.PAConfig;
import com.netease.yixin.publicaccount.datang.meta.UserContent;
import com.netease.yixin.publicaccount.datang.util.KeyUtils;
import com.netease.yixin.sdk.exception.YixinException;

@Service
public class CacheStatuService {

	private static final int MAX_LENGTH = 100;
	private static final int MAX_DAY = 3;
	private static final String TAG_ENTER_LINE = "\n";
	@Resource
	protected DomainDaoSupport domainDaoSupport;
	@Autowired
	HttpService httpService;

	private static final String USER = "%s:%s:%s";

	public NRedisConnectionImpl getRedis() {
		return (NRedisConnectionImpl) domainDaoSupport.getCacheTemplet().getConnectionManager().getConnection();
	}

	public ICacheTemplet getCacheTemplate() {
		return domainDaoSupport.getCacheTemplet();
	}

	public void setAskTarget(long pid, long yid, int status) {
		getCacheTemplate().set(KeyUtils.formatMM(pid, yid), status);
	}

	public int getAskStatus(long pid, long yid) {
		Integer rtn = getCacheTemplate().getObject(KeyUtils.formatMM(pid, yid));
		return rtn == null ? 0 : rtn;
	}

	public void setAskContent(long pid, long yid, String urs, Object askContent) {
		setAskContent(pid, yid, urs, askContent.toString());
		getRedis().sadd(KeyUtils.formatList(), String.format(USER, pid, yid, urs));
	}

	private void setAskContent(long pid, long yid, String urs, String askContent) {
		UserContent content = getCacheTemplate().getObject(KeyUtils.formatAsk(pid, yid));
		String timeContent = DateFormatUtil.formatTimeLong(System.currentTimeMillis()) + " " + askContent
				+ TAG_ENTER_LINE;
		if (content == null) {
			content = new UserContent(yid, pid, urs, TAG_ENTER_LINE + timeContent);
		} else {
			content.append(timeContent);
		}
		getCacheTemplate().set(KeyUtils.formatAsk(pid, yid), content);
	}

	public void addLeftMessage(String urs, long pid, String left) {
		checkLength(urs, pid);
		getRedis().expire(KeyUtils.formatTxtMsgKey(urs, pid), (int) (DateUtils.MILLIS_PER_DAY * MAX_DAY));
		getCacheTemplate().addToList(KeyUtils.formatTxtMsgKey(urs, pid), left);
	}

	private void checkLength(String urs, long pid) {
		int length = getCacheTemplate().getListLength(KeyUtils.formatTxtMsgKey(urs, pid));
		if (length >= MAX_LENGTH) {
			throw new YixinException("缓存内容大于" + MAX_LENGTH);
		}
	}

	public List<String> getLeftTxtMsg(String urs, long pid) {
		int length = getCacheTemplate().getListLength(KeyUtils.formatTxtMsgKey(urs, pid));
		return getCacheTemplate().getStringListRange(KeyUtils.formatTxtMsgKey(urs, pid), 0, length);
	}

	public void addLeftMidMessage(String urs, long pid, long mid) {
		checkLength(urs, pid);
		getRedis().expire(KeyUtils.formatJsonMsgKey(urs, pid), (int) (DateUtils.MILLIS_PER_DAY * MAX_DAY));
		getCacheTemplate().addToList(KeyUtils.formatJsonMsgKey(urs, pid), mid + "");
	}

	public List<String> getLeftJsonMsg(String urs, long pid) {
		int length = getCacheTemplate().getListLength(KeyUtils.formatJsonMsgKey(urs, pid));
		return getCacheTemplate().getStringListRange(KeyUtils.formatJsonMsgKey(urs, pid), 0, length);
	}

	public void addLeftMMarkMessage(String urs, long pid, String qid) {
		checkLength(urs, pid);
		getRedis().expire(KeyUtils.formatRateMsgKey(urs, pid), (int) (DateUtils.MILLIS_PER_DAY * MAX_DAY));
		getCacheTemplate().addToList(KeyUtils.formatRateMsgKey(urs, pid), qid);
	}

	public List<String> getLeftRateMsg(String urs, long pid) {
		int length = getCacheTemplate().getListLength(KeyUtils.formatRateMsgKey(urs, pid));
		return getCacheTemplate().getStringListRange(KeyUtils.formatRateMsgKey(urs, pid), 0, length);
	}

	public void clearAll(String urs, long pid) {
		getCacheTemplate().delete(KeyUtils.formatTxtMsgKey(urs, pid));
		getCacheTemplate().delete(KeyUtils.formatJsonMsgKey(urs, pid));
		getCacheTemplate().delete(KeyUtils.formatRateMsgKey(urs, pid));
	}

	public void setPublicAccount(long pid, PAConfig config) {
		getCacheTemplate().set(KeyUtils.formatPaConfig(pid), config);
	}

	public PAConfig getPublicAccount(long pid) {
		return getCacheTemplate().getObject(KeyUtils.formatPaConfig(pid));
	}

	private static final int EXPIRE = (int) (DateUtils.MILLIS_PER_DAY - DateUtils.MILLIS_PER_MINUTE * 10);

	public String getMaterialByHttp(long pid, long mid) {
		String key = String.format("publicmid_%s_%s_", pid, mid);
		String cacheRes = getCacheTemplate().getString(key);
		if (StringUtils.isBlank(cacheRes)) {
			JSONObject jo = httpService.getNoTitleMat(pid, mid);
			if (jo != null) {
				String newRes = jo.getString("jsonMessage");
				if (!StringUtils.isBlank(newRes)) {
					getCacheTemplate().set(key, newRes, (int) DateUtils.MILLIS_PER_MINUTE / 1000 * 10);
					cacheRes = newRes;
				}
			}
		}
		return cacheRes;
	}

	public void flush() {
		Set<String> users = getRedis().smembers(KeyUtils.formatList());
		for (String urs : users) {
			String[] details = StringUtils.split(urs, ":");
			long pid = Long.valueOf(details[0]);
			long yid = Long.valueOf(details[1]);
			UserContent uc = getCacheTemplate().getObject(KeyUtils.formatAsk(pid, yid));
			if (uc != null && StringUtils.isNotBlank(uc.getContent())) {
				getCacheTemplate().delete(KeyUtils.formatAsk(pid, yid));
				httpService.askMM(pid, yid, details[2], uc.getContent());
			} else {
				getRedis().srem(KeyUtils.formatList(), urs);
			}

		}
	}
}
