package com.netease.yixin.publicaccount.datang.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.netease.yixin.sdk.exception.YixinException;

public class AppsExceptionResolver implements HandlerExceptionResolver, Ordered {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelMap model = new ModelMap();
		// logger.warn("exception occured: ", ex);
		return forwardError(ex, model);
	}

	private ModelAndView forwardError(Exception ex, ModelMap model) {
		ex.printStackTrace();
		if (ex instanceof YixinException) {
			YixinException ye = (YixinException) ex;
			model.put("msg", ex.getMessage());
			model.put("code", ye.getCode());
			model.put("result", "");
		} else {
			model.put("msg", ex.getMessage());
			model.put("code", 400);
			model.put("result", "");
		}
		return new ModelAndView("springError", model);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
