package com.nswt.cms.stat.impl;

import com.nswt.cms.stat.AbstractStat;
import com.nswt.cms.stat.Visit;
import com.nswt.cms.stat.VisitCount;

/**
 * 日期 : 2010-6-23 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class DistrictStat extends AbstractStat {
	private static final String Type = "District";

	public String getStatType() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.cms.stat.AbstractStat#deal(com.nswt.cms.stat.Visit)
	 */
	public void deal(Visit v) {
		VisitCount.getInstance().add(v.SiteID, Type, "PV", v.District);
		if (v.UVFlag) {
			VisitCount.getInstance().add(v.SiteID, Type, "UV", v.District);
			if (v.IPFlag) {
				VisitCount.getInstance().add(v.SiteID, Type, "IP", v.District);
			}

		}
	}
}
