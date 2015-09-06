package com.netease.yixin.publicaccount.datang.meta;

import java.util.HashMap;

public class GmHintMap {

/*	public static HashMap<Long, String> bindMap = new HashMap<Long, String>();

	static {
		bindMap.put((long)6062000, "大唐无双2");
		bindMap.put((long)8742537, "梦幻西游2");
		bindMap.put((long)8502708, "新大话西游2");
		bindMap.put((long)8502705, "倩女幽魂2");
		bindMap.put((long)8503195, "天下3");
		bindMap.put((long)8505702, "武魂");
		bindMap.put((long)8500191, "藏地传奇");
		bindMap.put((long)8502700, "龙剑");
	}*/

	public static HashMap<String, String> bindHintMap = new HashMap<String, String>();
	static {
		bindHintMap.put("sub.title", "欢迎您关注《%s》易信官方公众帐号。<br/> 在这里你可获得：<br/> ★24小时贴身客服<br/> ★最新资讯的活动 ^_^<br/>");
		bindHintMap.put("bindok.title", "您好，您已成功绑定%s游戏帐号，赶紧去体验吧^_^");
		bindHintMap.put("ai.title", "Hi，我是%s小精灵！请大侠输入想要咨询的内容，小精灵会认真解答的哟！");
		bindHintMap.put("mm.title", "Hi，我是%s！请在下方输入您的问题，客服将在24小时内回复您！");
		bindHintMap.put("nobind.title", "★24小时贴身客服<br/>★最新的%s资讯<br/>绑定游戏帐号即可享受完善的服务^_^<br/>");
	}

}
