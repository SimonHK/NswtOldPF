package com.nswt.cms.site;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCImagePlayerSchema;

/**
 * Í¼Æ¬²¥·ÅÆ÷Ô¤ÀÀ
 * @Author »ÆÀ×
 * @Date 2007-8-10
 * @Mail huanglei@nswt.com
 */
public class ImagePlayerPreview extends Page {
	
	public static Mapx init(Mapx params) {
		String s = (String)params.get("ImagePlayerID");		
		if(StringUtil.isEmpty(s)){
			return null;
		}
		long ImagePlayerID = Long.parseLong(params.get("ImagePlayerID").toString());
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setID(ImagePlayerID);
		imagePlayer.fill();
		params.put("PlayerPreview", PubFun.getImagePlayerHTML(imagePlayer));
		return params;
	}
	
}
