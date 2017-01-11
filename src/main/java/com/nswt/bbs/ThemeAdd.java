package com.nswt.bbs;

import java.util.Date;

import com.nswt.bbs.admin.ForumScore;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCForumAttachmentSchema;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCPostSchema;
import com.nswt.schema.ZCThemeSchema;
import com.nswt.schema.ZCForumMemberSchema;

public class ThemeAdd extends Ajax {

	public void add() {
		if (ForumUtil.isNotSendTheme($V("SiteID"), $V("ForumID"))) {
			Response.setLogInfo(0, "��û�з�����Ȩ��");
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ZCThemeSchema theme = new ZCThemeSchema();
		ZCForumMemberSchema user = new ZCForumMemberSchema();
		ZCForumSchema forum = new ZCForumSchema();
		ForumPriv priv = new ForumPriv($V("SiteID"));
		Transaction trans = new Transaction();

		theme.setValue(Request);
		theme.setID(NoUtil.getMaxID("ThemeID"));
		// theme.fill();
		forum.setID(theme.getForumID());
		forum.fill();
		theme.setSiteID($V("SiteID"));
		theme.setAddUser(User.getUserName());
		theme.setOrderFlag(0);
		theme.setViewCount(0);
		theme.setReplyCount(0);
		theme.setTopTheme("N");
		theme.setBest("N");
		theme.setType("0");
		theme.setAddTime(new Date());
		theme.setOrderTime(new Date());
		theme.setStatus("Y");
		theme.setVerifyFlag(forum.getVerify().equals("N") ? "Y" : "N");
		trans.add(theme, Transaction.INSERT);

		ZCPostSchema post = new ZCPostSchema();
		post.setValue(Request);
		post.setFirst("Y");
		post.setID(NoUtil.getMaxID("PostID"));
		post.setThemeID(theme.getID());
		post.setAddUser(User.getUserName());
		post.setAddTime(theme.getAddTime());
		post.setVerifyFlag(forum.getVerify().equals("N") ? "Y" : "N");
		post.setInvisible("Y");
		post.setLayer(1);
		post.setMessage(PostAdd.processMsg($V("Message")));
		trans.add(post, Transaction.INSERT);
		user.setUserName(theme.getAddUser());
		user.fill();

		if (priv.hasPriv("Verify")) {
			theme.setVerifyFlag("Y");
			post.setVerifyFlag("Y");
		}

		if (theme.getVerifyFlag().equals("Y")) {
			user.setForumScore(user.getForumScore() + forumScore.PublishTheme);
			ForumUtil.userGroupChange(user);
			user.setThemeCount(user.getThemeCount() + 1);
			forum.setThemeCount(forum.getThemeCount() + 1);
			forum.setLastThemeID(theme.getID());
			forum.setLastPost(theme.getSubject());
			forum.setLastPoster(theme.getAddUser());
		}
		if (post.getVerifyFlag().equals("Y")) {
			forum.setPostCount(forum.getPostCount() + 1);
		}
		trans.add(user, Transaction.UPDATE);
		trans.add(forum, Transaction.UPDATE);
		if ($V("file").length() > 0) {
			String[] Attachments = $V("file").split(",");
			String[] indexs = $V("indexs").split(",");
			for (int i = 0; i < Attachments.length; i++) {
				ZCForumAttachmentSchema attachment = new ZCForumAttachmentSchema();
				attachment.setID(NoUtil.getMaxID("ForumAttachmentID"));
				attachment.setPostID(post.getID());
				attachment.setSiteID($V("SiteID"));
				String suffix = $V("file" + indexs[i]).substring(($V("file" + indexs[i]).lastIndexOf(".") + 1));
				if (PubFun.isAllowExt(suffix, "Attach")) {
					attachment.setType("attach");
				} else if (PubFun.isAllowExt(suffix, "Image")) {
					attachment.setType("image");
				} else if (PubFun.isAllowExt(suffix, "Audio")) {
					attachment.setType("audio");
				} else if (PubFun.isAllowExt(suffix, "Video")) {
					attachment.setType("video");
				} else {
					Response.setLogInfo(0, "�������ϴ����ļ�����");
					return;
				}
				attachment.setSuffix(suffix);
				attachment.setName($V("file" + indexs[i]).substring($V("file" + indexs[i]).lastIndexOf("\\") + 1));
				String[] file = Attachments[i].split("#");
				attachment.setPath(file[0]);
				attachment.setAttSize(file[1]);
				attachment.setDownCount(0);
				attachment.setAddUser(User.getUserName());
				attachment.setAddTime(new Date());
				trans.add(attachment, Transaction.INSERT);
			}
		}
		if (trans.commit()) {
			if (theme.getVerifyFlag().equals("Y")) {
				CacheManager.set("Forum", "Forum", forum.getID(), forum);
				Response.setLogInfo(1, "�����ɹ�");
				Response.put("ThemeID", theme.getID() + "");
			} else {
				Response.setLogInfo(2, "��̳����Ա��������˻��ƣ���ȴ���ˣ�");
			}
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	public static Mapx initAddDialog(Mapx params) {
		String ForumID = params.getString("ForumID");
		String SiteID = params.getString("SiteID");
		params.put("Priv", ForumUtil.initPriv(ForumID, SiteID));
		params.put("BBSName", ForumUtil.getBBSName(SiteID));
		return params;
	}

	public static Mapx init(Mapx params) {
		String ForumID = params.getString("ForumID");
		String ThemeID = params.getString("ThemeID");
		String SiteID = params.getString("SiteID");
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID(ThemeID);
		theme.setForumID(ForumID);
		Mapx map = theme.toMapx();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ForumID);
		forum.fill();
		map.put("Name", forum.getName());
		map.put("AddUser", User.getUserName());
		map.put("SiteID", SiteID);
		map.put("Priv", ForumUtil.initPriv(ForumID, SiteID));
		map.put("BBSName", ForumUtil.getBBSName(SiteID));

		return map;
	}

}
