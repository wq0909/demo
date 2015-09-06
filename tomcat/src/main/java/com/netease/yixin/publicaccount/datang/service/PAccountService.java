package com.netease.yixin.publicaccount.datang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netease.libs.http.HttpClientUtil;
import com.netease.yixin.publicaccount.datang.dao.PAConfigDao;
import com.netease.yixin.publicaccount.datang.meta.PAConfig;
import com.netease.yixin.publicaccount.datang.meta.UserBind;
import com.netease.yixin.publicaccount.datang.util.SimpleCipherUtil;

@Service
public class PAccountService {

	@Resource
	private PAConfigDao pAConfigDao;
	
	@Resource
	private ConfigService configService;
	@Resource
	private CacheStatuService cacheStatuService;

	private static Logger logger = LoggerFactory.getLogger(PAccountService.class);
	
	private static HttpClientUtil httpClientUtil = new HttpClientUtil(128,"UTF-8");


	private static String PUBLICACCOUNT_URL = "http://10.120.104.124:8282/private/";

	// private static int VERIFY_TYPE = 2;

	public void initLogin() {
		List<PAConfig> list = pAConfigDao.getList();
		for (PAConfig config : list) {
			login(config);
		}
	}

	private boolean login(PAConfig config) {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("account", config.getAccount()));
		// params.add(new BasicNameValuePair("pass", config.getPasswd()));
		// params.add(new BasicNameValuePair("type", VERIFY_TYPE + ""));
		// String res;
		// try {
		// res = httpClientUtil.postData(configService.getYixinUrl() + "login",
		// params);
		// logger.info("public account login res", res);
		// SessionAccount account =
		// JsonConverter.mapToObject(SessionAccount.class, res);
		config.setPid(config.getPid());
		config.setSid("-1");
		cacheStatuService.setPublicAccount(config.getPid(), config);
		return true;
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// return false;
	}

	public PAConfig getAccount(long pid) {
		return cacheStatuService.getPublicAccount(pid);
	}

	/**
	 * 动态添加用户
	 * 
	 * @param config
	 * @return
	 */
	public PAConfig addAccount(PAConfig config) {
		if (login(config)) {
			PAConfig econfig = pAConfigDao.getById(config.getPid());
			if (econfig == null) {
				pAConfigDao.add(config);
			} else {
				pAConfigDao.updateConfig(config);
			}
			String h = PUBLICACCOUNT_URL + "createMenu";
			System.out.println(h + " " +config.getPid());
			List<NameValuePair> list = new ArrayList<NameValuePair>(4);
			list.add(new BasicNameValuePair("pid", config.getPid() + ""));
			list.add(new BasicNameValuePair("menu", config.getTokenVaild()));
			try {
				System.out.println(httpClientUtil.postData(h, list));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return config;
		}
		return null;
	}

	private static String st_format = "%s|%s";

	public String encode(long yid, long pid) {
		return SimpleCipherUtil.encode(String.format(st_format, yid, pid));
	}

	public UserBind decode(String st) {
		String dst = SimpleCipherUtil.decode(st);
		String[] idsstrs = StringUtils.split(dst, "|");
		UserBind fake = new UserBind(Long.valueOf(idsstrs[0]), Long.valueOf(idsstrs[1]), null);
		return fake;
	}
}
