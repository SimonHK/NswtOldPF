package com.nswt.bbs;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.CookieImpl;
import com.nswt.framework.User;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCForumSet;

public class Forum extends Ajax {
	/**
	 * 
	 * @param params
	 * @param parentDR
	 *            ����simplelistѭ����������DataRow
	 * @return
	 */
	public static DataTable getList1(Mapx params, DataRow parentDR) {
		String SiteID = params.getString("SiteID");
		SiteID = ForumUtil.getCurrentBBSSiteID(SiteID);
		String ForumID = params.getString("ForumID");
		ZCForumSet set = ForumCache.getForumSet(SiteID, ForumID);
		for (int i = 0; i < set.size(); i++) {
			String forumadmin = set.get(i).getForumAdmin();
			if (StringUtil.isEmpty(forumadmin) || forumadmin.charAt(0) == ',') {
				set.get(i).setForumAdmin("&nbsp;");
			} else {
				int index = forumadmin.lastIndexOf(",");
				if (index == -1) {
					index = forumadmin.length();
				}
				set.get(i).setForumAdmin("����������" + forumadmin.substring(0, index));
			}
		}
		return set.toDataTable();
	}

	public static DataTable getList2(Mapx params, DataRow parentDR) {
		ZCForumSet set = ForumCache.getChildForumSet(parentDR.getString("SiteID"), parentDR.getString("ID"));
		DataTable dt = set.toDataTable();
		dt.insertColumn("LastPostInfo");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String forumadmin = dt.getString(i, "ForumAdmin");
			if (StringUtil.isEmpty(forumadmin) || forumadmin.charAt(0) == ',') {
				dt.set(i, "ForumAdmin", "���ް���");
			} else {
				int index = forumadmin.lastIndexOf(",");
				if (index == -1) {
					index = forumadmin.length();
				}
				dt.set(i, "ForumAdmin", forumadmin.substring(0, index));
			}
			if (StringUtil.isNotEmpty(dt.getString(i, "Info"))) {
				dt.set(i, "Info", "<br>" + dt.getString(i, "Info").replaceAll("\\n", "<br>"));
			}
			if (StringUtil.isNotEmpty(dt.getString(i, "LastPost"))) {
				StringBuffer sb = new StringBuffer();
				sb.append("<a href='Post.jsp?ForumID=" + dt.getString(i, "ID") + "&ThemeID="
						+ dt.getString(i, "LastThemeID") + "&SiteID=" + dt.getString(i, "SiteID") + "'" + " title='"
						+ dt.getString(i, "LastPost") + "'>" + StringUtil.subStringEx(dt.getString(i, "LastPost"), 18)
						+ "</a>");
				sb.append("<br>by <a href='javascript:void(0)'  onclick=\"userinfo('" + dt.getString(i, "LastPoster")
						+ "')\">" + dt.getString(i, "LastPoster") + "</a></cite>");
				dt.set(i, "LastPostInfo", sb.toString());
			}
		}
		return dt;
	}

	/**
	 * ������Ƿ��������߱��趨����
	 * 
	 */
	public void check() {
		String ForumID = $V("ID");
		if (ForumUtil.getForumLock(ForumID)) {// ��鱻����
			ForumUtil.isUnLockGroup(ForumID);
			if (StringUtil.isEmpty((String) User.getValue("UnLockGroup")) || !User.getValue("UnLockGroup").equals("Y")) {
				Response.setLogInfo(2, "�ð���ѱ�����");
				return;
			}
		}
		if (ForumUtil.getForumPassword(ForumID)) {// ����趨������
			if (User.getValue("pass_" + ForumID) != null && User.getValue("pass_" + ForumID).equals("Y")) {
				Response.setStatus(1);
				return;
			}
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}

	}

	public void checkPassword() {
		ZCForumSchema forum = ForumCache.getForum($V("ID"));
		if ($V("Password").equals(forum.getPassword())) {
			User.setValue((String) ("pass_" + forum.getID()), "Y");
			Response.setStatus(1);
		} else {
			Response.setLogInfo(0, "�������");
		}
	}

	public static Mapx initPassword(Mapx params) {
		params.put("ID", params.getString("ID"));
		return params;
	}

	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		SiteID = ForumUtil.getCurrentBBSSiteID(SiteID);
		if (StringUtil.isEmpty(User.getUserName())) {
			params.put("UserName", "�ο�");
			params.put("button", "<a href='../Member/Register.jsp?SiteID=" + SiteID
					+ "'>ע��</a>&nbsp;&nbsp;<a href='../Member/Login.jsp?SiteID=" + SiteID + "'>��¼</a>");
		} else {
			params.put("UserName", User.getUserName());
		}

		params.put("SiteID", SiteID);
		params.put("Priv", ForumUtil.initPriv(SiteID));
		params.put("BBSName", ForumUtil.getBBSName(SiteID));
		return params;
	}

	public static void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieImpl Cookie = new CookieImpl(request);
		Cookie.setCookie("VerifyCookie", "", 0);
		Cookie.setCookie("UserName", "", 0);
		Cookie.writeToResponse(request, response);
		User.destory();
	}

	public static void init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ForumID = request.getParameter("ForumID");
		String SiteID = request.getParameter("SiteID");

		PrintWriter out = response.getWriter();

		// �ӻ����в鿴��̳�Ŀ���״̬
		if (StringUtil.isEmpty(SiteID)) {
			SiteID = ForumUtil.getCurrentBBSSiteID(SiteID);
		}
		if (ForumUtil.getForumStatus(SiteID)) {
			out.println("<script>window.location='" + Config.getContextPath() + "/BBS/CloseBBS.jsp?SiteID=" + SiteID
					+ "';</script>");
		}
		// ��Session�в鿴�û���û�з�����̳��Ȩ��
		if (StringUtil.isEmpty((String) User.getValue("AllowMemberVisit"))) {
			ForumUtil.allowVisit(SiteID);
		}
		if ("N".equals(User.getValue("AllowMemberVisit"))) {
			out.println("<script>alert('�����ڵ��û����ֹ������̳'); window.location='" + Config.getContextPath()
					+ "/BBS/Index.jsp?SiteID=" + SiteID + "';</script>");
		}
		// �ӻ����в鿴����������������״̬
		if (StringUtil.isNotEmpty(ForumID)) {
			if (StringUtil.isEmpty((String) User.getValue("UnLockGroup"))) {
				ForumUtil.isUnLockGroup(ForumID);
			}
			if ((ForumUtil.getForumLock(ForumID)) || !ForumUtil.getForumDisplay(ForumID)) {
				if (StringUtil.isEmpty((String) User.getValue("UnLockGroup"))
						|| !User.getValue("UnLockGroup").equals("Y")) {
					out.println("<script>alert('�ð���ѱ�����������'); window.location='" + Config.getContextPath()
							+ "/BBS/Index.jsp?SiteID=" + SiteID + "';</script>");
				}
			}
			// ��session�в鿴�Ƿ��Ѿ�ͨ��������֤

			if (ForumUtil.getForumPassword(ForumID)) {
				if (StringUtil.isEmpty((String) User.getValue("pass_" + ForumID))) {
					ForumUtil.isUnPasswordGroup(ForumID);
				}
				if (StringUtil.isEmpty((String) User.getValue("pass_" + ForumID))
						|| !((String) User.getValue("pass_" + ForumID)).equals("Y")) {
					out.println("<script>alert('�ð�����趨���룬������ֱ�ӽ���'); window.location='" + Config.getContextPath()
							+ "/BBS/Index.jsp?SiteID=" + SiteID + "';</script>");
				}
			}
		}
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0);
	}

}
