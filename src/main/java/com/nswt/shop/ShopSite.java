package com.nswt.shop;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.framework.Page;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.Application;
import com.nswt.schema.ZCSiteSchema;

/**
 * 站点属性
 * 
 * @Author 黄雷 兰军
 * @Date 2007-8-10 2007-9-15
 * @Mail huanglei@nswt.com
 */

public class ShopSite extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();
		return site.toMapx();
	}

   //发布站点首页
	public void publishIndex() {
		Publisher p = new Publisher();
		if (p.publishIndex(Application.getCurrentSiteID())) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存数据发生错误!");
		}
	}

}
