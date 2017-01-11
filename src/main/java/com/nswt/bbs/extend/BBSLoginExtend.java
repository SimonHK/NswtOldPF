package com.nswt.bbs.extend;

import com.nswt.bbs.ForumUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.member.Member;
import com.nswt.framework.extend.IExtendAction;

/**
 * ���� : 2010-2-4 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class BBSLoginExtend implements IExtendAction {
	public String getTarget() {
		return "Member.Login";
	}

	public void execute(Object[] args) {
		Member member = (Member) args[0];
		String siteID = String.valueOf(member.getSiteID());
		if (SiteUtil.isBBSEnable(siteID)) {
			ForumUtil.allowVisit(siteID);
		}
	}

	public String getName() {
		return "BBS��Ա��¼�߼���չ";
	}
}
