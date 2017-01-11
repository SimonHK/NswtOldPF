package com.nswt.bbs.extend;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.extend.IExtendAction;

/**
 * 日期 : 2010-2-4 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class BBSMenuExtend implements IExtendAction {
	public void execute(Object[] args) {
		StringBuffer sb = (StringBuffer) args[0];
		String SiteID = (String) args[1];
		if (!SiteUtil.isBBSEnable(SiteID)) {
			return;
		}
		sb.append("<ul class='sidemenu'>");
		sb.append("<li id='Menu_MT'><a href='" + Config.getContextPath() + "BBS/MyThemes.jsp?cur=Menu_MT&SiteID="
				+ SiteID + "'>我发表的帖子</a></li>");
		sb.append("<li id='Menu_MS'><a href='" + Config.getContextPath() + "BBS/MyScore.jsp?cur=Menu_MS&SiteID="
				+ SiteID + "'>我的论坛积分</a></li>");
		sb.append("</ul>");
		sb.append("<hr class='shadowline'/>");
	}

	public String getTarget() {
		return "Member.Menu";
	}

	public String getName() {
		return "BBS会员中心菜单扩展";
	}

}
