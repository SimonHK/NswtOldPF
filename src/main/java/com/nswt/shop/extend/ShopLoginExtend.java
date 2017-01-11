package com.nswt.shop.extend;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.extend.IExtendAction;
import com.nswt.member.Member;

/**
 * ���� : 2010-2-4 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class ShopLoginExtend implements IExtendAction {
	public String getTarget() {
		return "Member.Login";
	}

	public void execute(Object[] args) {
		Member member = (Member) args[0];
		String siteID = String.valueOf(member.getSiteID());
		if (SiteUtil.isShopEnable(siteID)) {
			
		}
	}

	public String getName() {
		return "Shop��Ա��¼�߼���չ";
	}
}
