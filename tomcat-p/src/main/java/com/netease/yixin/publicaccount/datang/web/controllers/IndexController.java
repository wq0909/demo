package com.netease.yixin.publicaccount.datang.web.controllers;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.netease.yixin.publicaccount.datang.service.WordService;

@Controller
public class IndexController {

	public static long TIME = 3000;

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private WordService wordService;

	@RequestMapping(value = "/mvc", method = RequestMethod.GET)
	public ModelAndView add(Long pid, Long time, ModelMap model) {
		Map<String, String> env = System.getenv();
		for (String k : env.keySet()) {
			log.info(k + " " + env.get(k));
		}
		System.out.println("=========================");
		Properties p = System.getProperties();
		for (Object k : p.keySet()) {
			log.info(k + " " + p.get(k));
		}
		if (time != null)
			TIME = time;
		String db = wordService.getDb();
		String conf = System.getenv().get("conf");
		if (StringUtils.isBlank(conf))
			conf = System.getenv().get("CONF");
		model.put("db", db);
		model.put("env", conf);
		System.out.println(db + " " + conf);
		return new ModelAndView("bind", model);
	}
}
