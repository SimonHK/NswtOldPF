package com.nswt.cms.stat.impl;

import com.nswt.cms.stat.AbstractStat;
import com.nswt.cms.stat.Visit;
import com.nswt.cms.stat.VisitCount;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public class ClientStat extends AbstractStat {
	private static final String[] avgSubTypes = new String[] { "StickTime" };

	private static final String Type = "Client";

	public String getStatType() {
		return Type;
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		if ("Unload".equals(v.Event)) {
			return;
		} else {
			if (StringUtil.isEmpty(v.FlashVersion)) {
				v.FlashVersion = "其他";
			} else {
				v.FlashVersion = v.FlashVersion.replaceAll("(\\%20)+", " ");
				if (v.FlashVersion.indexOf(" ") > 0) {
					v.FlashVersion = v.FlashVersion.substring(0, v.FlashVersion.indexOf(" "));
				}
			}
			String cd = v.ColorDepth;
			if (StringUtil.isEmpty(cd)) {
				cd = "其他";
			} else {
				cd = cd.replaceAll("\\D", "");
				if (cd.equals("8") || cd.equals("16") || cd.equals("24") || cd.equals("32")) {
					cd = cd + "-bit";
				} else {
					cd = "其他";
				}
			}
			v.ColorDepth = cd;
			VisitCount.getInstance().add(v.SiteID, Type, "ColorDepth", v.ColorDepth);
			VisitCount.getInstance().add(v.SiteID, Type, "OS", v.OS);
			VisitCount.getInstance().add(v.SiteID, Type, "Browser", v.Browser);
			VisitCount.getInstance().add(v.SiteID, Type, "Language", v.Language);
			VisitCount.getInstance().add(v.SiteID, Type, "FlashVersion", v.FlashVersion);
			VisitCount.getInstance().add(v.SiteID, Type, "JavaEnabled", String.valueOf(v.JavaEnabled));
			VisitCount.getInstance().add(v.SiteID, Type, "CookieEnabled", String.valueOf(v.CookieEnabled));
			VisitCount.getInstance().add(v.SiteID, Type, "Screen", v.Screen);
		}
	}
}
