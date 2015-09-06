package com.netease.yixin.publicaccount.datang.listener;

import org.springframework.web.context.WebApplicationContext;

import com.netease.libs.spring.listener.NContextLoaderListener;
import com.netease.yixin.publicaccount.datang.service.PAccountService;

public class CrmRobotContextLoaderListener extends NContextLoaderListener {

	@Override
	public void initedInvoke(WebApplicationContext ctx) {
		PAccountService pas = ctx.getBean(PAccountService.class);
		pas.initLogin();
	}

}
