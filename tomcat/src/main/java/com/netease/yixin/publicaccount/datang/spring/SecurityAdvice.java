package com.netease.yixin.publicaccount.datang.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netease.libs.utils.DataUtils;
import com.netease.libs.utils.SecurityUtils;

//@Component("amfROAdvice")
//@Aspect
//@Order(1)
public class SecurityAdvice {

	private static Logger logger = Logger.getLogger(SecurityAdvice.class);

	@Pointcut("execution(public org.springframework.web.servlet.ModelAndView cn.edu.zstu.nvs.web.controller.*.*(..))")
	protected void amfHandler() {
	}

	@Around("amfHandler()")
	public Object doProceed(final ProceedingJoinPoint pjp) {
		boolean debug = logger.isDebugEnabled();
		long start = 0;
		if (debug) {
			start = System.currentTimeMillis();
			StringBuilder sb = new StringBuilder();
			sb.append("amf call begin... Handler: ");
			sb.append(pjp.getSignature().toLongString());
			sb.append(" with args: [");
			sb.append(StringUtils.join(pjp.getArgs(), ","));
			sb.append("]");
			sb.append(" and accountName: ");
			sb.append(SecurityUtils.getLoginAccount());
			sb.append(" and hostId: ");
			if (SecurityUtils.isLogined())
				sb.append(SecurityUtils.getLoginAccount());
			else
				sb.append(" is null");
			logger.debug(sb.toString());
		}
		String errorMsg = null;
		Object result;
		try {

			result = (Object) pjp.proceed();
			// XYUtils.assertNotNull(result,
			// "amf result is null, but it mustn't be happen!");
			return result;
		} catch (Exception e) {
			errorMsg = e.getMessage();
			logger.error("amf call error!", e);
			return errorMsg;
		} catch (Throwable e) {
			logger.error("amf call error!", e);
			errorMsg = DataUtils.getStackTrace(e);
			return errorMsg;
		} finally {
			// �黹token
			// SecurityUtils.clearThreadLocal();
		}
	}

	@Before("amfHandler() && (@target(cn.edu.zstu.nvs.spring.interceptor.annotations.SecurityAmf) || @annotation(cn.edu.zstu.nvs.spring.interceptor.annotations.SecurityAmf))")
	public void doCheckSecurity(final JoinPoint pjp) {
		boolean debug = logger.isDebugEnabled();
		SecurityAmf securityAmfByTarget = pjp.getTarget().getClass().getAnnotation(SecurityAmf.class);
		SecurityAmf securityAmfByMethod = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(
				SecurityAmf.class);
		SecurityAmf securityAmf = (securityAmfByMethod != null) ? securityAmfByMethod : securityAmfByTarget;
		if (debug) {
			logger.debug("check amf rpc security!");
		}
		int needRights = securityAmf.value();
		// int glqx = SecurityUtils.getAccount().getGlqx();
		// if (!BackgroundManageUtils.checkPower(glqx, needRights)) {
		// throw new ForbiddenRuntimeException("�˺�û��Ȩ��!");
		// }
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Documented
	public @interface SecurityAmf {

		/**
		 * 
		 * @return
		 */
		public boolean accuntOnly() default false;

		/**
		 * 
		 * @return
		 */
		public int authorities = -1;

		public int value();
	}

}