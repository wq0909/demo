package com.netease.yixin.publicaccount.datang.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.yixin.sdk.meta.MsgSubType;
import com.netease.yixin.sdk.meta.cmd.CmdButton;
import com.netease.yixin.sdk.meta.cmd.CmdContent;
import com.netease.yixin.sdk.meta.cmd.CmdData;

public class CmdFakeUtil {
	private final static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
	}

	public static String fillContent(MsgSubType type, Object content) {
		return fillContent(type, content, null);
	}

	public static String fillContent(MsgSubType type, Object content, List<ImmutablePair<String, String>> buttonPairs) {
		CmdData d = new CmdData(content);
		// TODO 生成之后就不再变
		CmdContent c = new CmdContent(type.intValue(), d);
		if (CollectionUtils.isNotEmpty(buttonPairs)) {
			List<CmdButton> buttons = new ArrayList<CmdButton>();
			for (ImmutablePair<String, String> pair : buttonPairs) {
				buttons.add(new CmdButton(pair.getLeft(), pair.getRight()));
			}
			d.setButtons(buttons);
		}
		return postFill(c);
	}

	private static String postFill(CmdContent cmd) {
		try {
			return objectMapper.writeValueAsString(cmd);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
