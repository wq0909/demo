package com.netease.yixin.dev.datang;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.yixin.sdk.meta.multipic.PicContent;
import com.netease.yixin.sdk.meta.multipic.PicData;
import com.netease.yixin.sdk.meta.multipic.PicImage;
import com.netease.yixin.sdk.meta.multipic.PicItem;

public class PicContentTest {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		PicImage image = new PicImage("111", "111", 272921,
				"http://nos.netease.com/yixinpublic/pr_0_0_1_1373509762_202");
		PicItem item = new PicItem("111", "111", "www.163.com", image);

		PicImage image2 = new PicImage("中2222", "中2222", 272921,
				"http://nos.netease.com/yixinpublic/pr_0_0_1_1373509762_202");
		PicItem item2 = new PicItem("中2222", "中2222", "www.163.com", image2);

		List<PicItem> items = new ArrayList<PicItem>();
		items.add(item);
		items.add(item2);
		
		PicData data = new PicData(items);
		PicContent pic = new PicContent(1, data);
		ObjectMapper obj = new ObjectMapper();
		obj.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
		StringWriter out2 = new StringWriter();
		obj.writeValue(out2, pic);
		String s = out2.toString();
		System.out.println(s);
	}

}
