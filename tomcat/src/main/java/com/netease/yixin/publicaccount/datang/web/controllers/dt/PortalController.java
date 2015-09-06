package com.netease.yixin.publicaccount.datang.web.controllers.dt;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.libs.http.HttpClientUtil;
import com.netease.yixin.publicaccount.dao.AppDao;
import com.netease.yixin.publicaccount.dao.PbAccountInfoDao;
import com.netease.yixin.publicaccount.dao.TxtMaterialDao;
import com.netease.yixin.publicaccount.datang.meta.GmHintMap;
import com.netease.yixin.publicaccount.datang.meta.PAConfig;
import com.netease.yixin.publicaccount.datang.service.CacheStatuService;
import com.netease.yixin.publicaccount.datang.service.ConfigService;
import com.netease.yixin.publicaccount.datang.service.HttpService;
import com.netease.yixin.publicaccount.datang.service.MarkAsyncService;
import com.netease.yixin.publicaccount.datang.service.MessageService;
import com.netease.yixin.publicaccount.datang.service.PAccountService;
import com.netease.yixin.publicaccount.datang.util.Constant;
import com.netease.yixin.publicaccount.datang.util.SimpleCipherUtil;
import com.netease.yixin.publicaccount.meta.App;
import com.netease.yixin.publicaccount.meta.PbAccountInfo;
import com.netease.yixin.publicaccount.meta.TxtMaterial;
import com.netease.yixin.publicaccount.service.MenuInvokeLimit;
import com.netease.yixin.publicaccount.util.Keys;
import com.netease.yixin.sdk.exception.YixinException;
import com.netease.yixin.sdk.meta.xml.ResponseMessage;
import com.netease.yixin.sdk.meta.xml.YixinMessage;
import com.netease.yixin.sdk.meta.xml.response.ResTextMessage;

@Controller
@RequestMapping(value = "/dt2")
public class PortalController {

	private static Logger logger = LoggerFactory.getLogger(PortalController.class);

	@Autowired(required = false)
	ReloadableResourceBundleMessageSource messageSource;
	@Autowired
	HttpClientUtil httpClientUtil;
	@Autowired
	HttpService httpService;
	@Resource
	MarkAsyncService markService;
	@Resource
	ConfigService configService;
	@Resource
	PAccountService paccountService;
	@Resource
	MessageService messageService;
	@Resource
	ObjectMapper objectMapper;
	@Resource
	CacheStatuService cacheStatuService;
	@Resource
	PbAccountInfoDao publicNickDao;
	@Resource
	AppDao appDao;
	@Resource
	TxtMaterialDao materialDao;

	public void preRender(ModelMap model) {
		model.put("url", configService.getSelfUrl());
	}

	private static final String FORMAT_MARK = "yid=%s,pid=%s,nps=%s,solved=%s,gm_mark=%s,";

	// ///////////////////////////////以上為HTTP服務//////////////////
	private ResTextMessage fillBasic(YixinMessage msg) {
		ResTextMessage tmsg = new ResTextMessage();
		tmsg.setMsgId(msg.getMsgId());
		postHandler(msg, tmsg);
		tmsg.setCreateTime(msg.getCreateTime());
		return tmsg;
	}

	private void postHandler(YixinMessage msg, ResponseMessage tmsg) {
		String fromId = SimpleCipherUtil.encode(msg.getFromUserName());
		String toId = SimpleCipherUtil.encode(msg.getToUserName());
		tmsg.setToUserName(fromId);
		tmsg.setFromUserName(toId);
	}

	private void fillBasic(YixinMessage msg, ResponseMessage resMsg) {
		resMsg.setMsgId(msg.getMsgId());
		postHandler(msg, resMsg);
		resMsg.setCreateTime(System.currentTimeMillis());
	}

	private static final String KEY = "result";

	@RequestMapping(value = "/yixin.wx")
	public void mm(HttpServletRequest request, ModelMap model) {
		YixinMessage yxMsg = prepareModel(request, model);
		long yid = getYid(yxMsg);
		long pid = getPid(yxMsg);
		String content = yxMsg.getContent();
		String[] cs = StringUtils.split(content, ".");
		ResTextMessage tmsg = fillBasic(yxMsg);
		if (cs != null && cs.length > 0) {
			tmsg.setContent(process(cs));
		} else {
			tmsg = fillBasic(yxMsg);
			tmsg.setContent("说点什么吧");
		}
		model.addAttribute(KEY, tmsg);
	}

	private String process(String[] cs) {
		String action = cs[0];
		if (action.equals("t")) {
			String nick = cs[1];

			PbAccountInfo pbAccount = publicNickDao.getByNick(nick);
			if (pbAccount == null) {
				return "不存在公共号";
			}
			App app = appDao.getById(pbAccount.getPid());
			if (app == null) {
				return "APP不存在公共号";
			}
			String key = MenuInvokeLimit.TOKEN + "_" + app.getSecret() + "_"
					+ DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).getTime();
			logger.info(" key " + key);
			boolean result = cacheStatuService.getCacheTemplate().delete(key);
			if (result) {
				return "删除成功";
			} else {
				return "删除失败";
			}
		}
		if (action.equals("m")) {
			String nick = cs[1];
			String id = cs[2];
			PbAccountInfo pbAccount = publicNickDao.getByNick(nick);
			if (pbAccount == null) {
				return "不存在公共号";
			} else {
				if (cs.length > 3) {
					String version = cs[3];
					String key = String.format(Keys.MATERIAL_ID_VERSION, id, version,pbAccount.getPid());
					logger.info(" key " + key);
					cacheStatuService.getCacheTemplate().delete(key);
				}
				TxtMaterial t = materialDao.getById(Long.valueOf(id), pbAccount.getPid());
				if (t == null) {
					return "不存在素材";
				}
				materialDao.deleteById(t);
			}
		}

		return "操作成功";
	}

	private long getPid(YixinMessage yxMsg) {
		return Long.valueOf(yxMsg.getToUserName());
	}

	private long getYid(YixinMessage yxMsg) {
		return Long.valueOf(yxMsg.getFromUserName());
	}

	private YixinMessage prepareModel(HttpServletRequest request, ModelMap model) {
		preRender(model);
		YixinMessage yxMsg = (YixinMessage) request.getAttribute("yxMsg");

		if (yxMsg == null) {
			throw new YixinException("解析内容为空");
			// ReqTextMessage txtMsg = new ReqTextMessage();
			// filleror(txtMsg);
			// return txtMsg;
		}

		String fromId = SimpleCipherUtil.decode(yxMsg.getFromUserName());
		String toId = SimpleCipherUtil.decode(yxMsg.getToUserName());
		yxMsg.setFromUserName(fromId);
		yxMsg.setToUserName(toId);
		return yxMsg;
	}

	/**
	 * 处理图片
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/image.wx")
	public void image(HttpServletRequest request, ModelMap model) {
		YixinMessage yxMsg = prepareModel(request, model);
		long yid = getYid(yxMsg);
		long pid = getPid(yxMsg);
		int status = cacheStatuService.getAskStatus(pid, yid);
		if (status != Constant.TYPE_MM) {
			unknowAi(model, yxMsg, pid);
		}
	}

	@RequestMapping(value = "/location.wx")
	public void location(HttpServletRequest request, ModelMap model) {
		YixinMessage yxMsg = prepareModel(request, model);
		long yid = getYid(yxMsg);
		long pid = getPid(yxMsg);
		int status = cacheStatuService.getAskStatus(pid, yid);
		if (status != Constant.TYPE_MM) {
			unknowAi(model, yxMsg, pid);
		}
	}

	public void unknowAi(ModelMap model, YixinMessage yxMsg, long pid) {
		ResTextMessage tmsg = fillBasic(yxMsg);
		tmsg.setContent("不知道你在说什么");
		model.addAttribute(KEY, tmsg);
	}

	/**
	 * 处理不认识的所有请求
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/hello.wx")
	public void hello(HttpServletRequest request, ModelMap model) {
		System.out.println("hello...");
	}

}
