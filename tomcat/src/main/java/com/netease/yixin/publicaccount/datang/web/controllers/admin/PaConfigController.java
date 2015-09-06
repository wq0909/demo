package com.netease.yixin.publicaccount.datang.web.controllers.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netease.yixin.publicaccount.datang.meta.PAConfig;
import com.netease.yixin.publicaccount.datang.service.PAccountService;
import com.netease.yixin.publicaccount.datang.util.SimpleCipherUtil;

@Controller
@RequestMapping(value = "/paconfig")
public class PaConfigController {

	@Autowired
	PAccountService pAccountService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long pid, ModelMap model) {
		PAConfig config = null;
		if (pid != null && pid > 0) {
			config = pAccountService.getAccount(pid);
		}
		if (config == null) {
			config = new PAConfig();
			config.setPid(pid);
		}
		model.put("config", config);
		return "admin/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(PAConfig config, ModelMap model) {
		pAccountService.addAccount(config);
		model.put("config", config);
		return "admin/ok";
	}

}
