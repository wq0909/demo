package com.netease.yixin.dev.datang;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import com.netease.yixin.sdk.meta.xml.request.ReqTextMessage;

public class FieldMailTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReqTextMessage t = new ReqTextMessage();
		Field f = ReflectionUtils.findField(ReqTextMessage.class, "MsgId");
		
		System.out.println(f);
	}

}
