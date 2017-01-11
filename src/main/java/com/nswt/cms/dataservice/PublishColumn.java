package com.nswt.cms.dataservice;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;

public class PublishColumn extends Page {

	/**
	 * ��ʼ����ѯ����
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initSearch(Mapx params) {
		String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("today", dateStr);
		return map;
	}

}
