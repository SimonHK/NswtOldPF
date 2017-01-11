package com.nswt.bbs;

import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZDMemberPersonInfoSchema;
import com.nswt.schema.ZDMemberSchema;

public class UserPersonalInfo extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		String userName = params.getString("UserName");
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		ZDMemberPersonInfoSchema personInfo = new ZDMemberPersonInfoSchema();
		ZDMemberSchema member = new ZDMemberSchema();
		forumUser.setUserName(userName);
		forumUser.fill();
		personInfo.setUserName(userName);
		personInfo.fill();
		member.setUserName(userName);
		member.fill();
		group.setID(forumUser.getUserGroupID());
		group.fill();
		params.putAll(member.toMapx());
		params.putAll(forumUser.toMapx());
		Mapx map = new Mapx();
		map.put("0", "使用组头像");
		map.put("1", "使用自定义头像");
		params.put("UserImageOption", HtmlUtil.mapxToRadios("UserImageOption", map, forumUser.getUseSelfImage()));
		params.put("NickName", forumUser.getNickName());
		params.put("UserName", userName);
		params.put("RegTime", DateUtil.toDateTimeString(member.getRegTime()));
		params.put("LastLoginTime", DateUtil.toDateTimeString(forumUser.getLastLoginTime()));
		params.put("group", group.getName());
		params.put("HeadImage", forumUser.getHeadImage());
		params.put("Priv", ForumUtil.initPriv(SiteID));
		params.put("Birthday", personInfo.getBirthday());
		params.put("QQ", personInfo.getQQ());
		params.put("MSN", personInfo.getMSN());
		params.put("Tel", personInfo.getTel());
		params.put("Mobile", personInfo.getMobile());
		params.put("Address", personInfo.getAddress());
		params.put("ZipCode", personInfo.getZipCode());
		params.put("SiteID", SiteID);
		params.put("BBSName", ForumUtil.getBBSName(SiteID));

		return params;
	}

	public void checkPriv() {
		if (StringUtil.isEmpty(User.getUserName())) {
			Response.setStatus(0);
			return;
		}
		if (StringUtil.isNotEmpty(User.getUserName()) && User.getUserName().equals($V("UserName"))) {
			Response.setStatus(1);
			return;
		}

		ForumPriv priv = new ForumPriv($V("SiteID"));
		if (priv.hasPriv("AllowUserInfo")) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}

	}

}
