package com.nswt.cms.dataservice;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.cms.pub.CMSCache;
import com.nswt.cms.site.BadWord;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.member.Login;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCommentSchema;

/**
 * 
 * @author nswt
 * @mail nswt@nswt.com
 * @date 2016-5-15
 */

public class CommentService extends Ajax {

	public static Mapx init(Mapx params) {
		params.put("ServicesContext", Config.getValue("ServicesContext"));
		params.put("CommentActionURL", Config.getValue("CommentActionURL"));
		params.put("CommentCountJS", Config.getValue("CommentCountJS"));
		params.put("CommentListJS", Config.getValue("CommentListJS"));
		params.put("CommentListPageJS", Config.getValue("CommentListPageJS"));
		return params;
	}

	public static void dg1DataBind(DataListAction dla) {
		String relaID = dla.getParam("RelaID");
		if (dla.getTotal() == 0) {
			dla.setTotal(new QueryBuilder("select count(*) from ZCComment where verifyflag='Y' and relaID = ?",Long.parseLong(relaID)));
		}
		DataTable dt = new QueryBuilder("select * from ZCComment where verifyflag='Y' and relaID = ? order by ID desc",Long.parseLong(relaID)).executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "AntiCount") == null) {
				dt.set(i, "AntiCount", 0);
			}
			if (dt.get(i, "SupporterCount") == null) {
				dt.set(i, "SupporterCount", 0);
			}
		}
		dla.bindData(dt);
	}

	public static DataTable listDataBind(Mapx params, DataRow parentDR) {
		String relaID = params.getString("RelaID");
		String count = params.getString("Count");
		if (StringUtil.isEmpty(count)) {
			count = 5 + "";
		}
		DataTable dt = new QueryBuilder("select * from ZCComment where verifyflag='Y' and relaID = ? order by ID desc",Long.parseLong(relaID)).executePagedDataTable(Integer.parseInt(count), 0);

		return dt;
	}

	public static void dealSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String relaID = request.getParameter("RelaID");
		String title = request.getParameter("Title");
		String content = request.getParameter("CmntContent");
		PrintWriter out = response.getWriter();
		
		if (StringUtil.lengthEx(content) > 400) {
			out.print("<script type='text/javascript'>alert('�������ݲ��ܳ���200���֣�');window.history.go(-1);</script>");
			return;
		}
		if (StringUtil.isNotEmpty(content)) {
			String catalogID = request.getParameter("CatalogID");
			String catalogType = request.getParameter("CatalogType");
			String siteID = request.getParameter("SiteID");
			String ip = request.getRemoteAddr();
			String anonymous = request.getParameter("CmntCheckbox");
			String parentID = request.getParameter("ParentID");
			String user = request.getParameter("CmntUserName");
			String password = request.getParameter("CmntPwd");
			if (User.isLogin()) {
				if ("on".equals(anonymous)) {
					user = "��������";
				} else {
					user = User.getUserName();
				}
			} else {
				QueryBuilder qb = null;
				qb = new QueryBuilder("select count(*) from ZDMember where UserName=? and Password=?");
				qb.add(user);
				qb.add(StringUtil.md5Hex(password).trim());
				if ("on".equals(anonymous)) {
					user = "��������";
				} else if (StringUtil.isEmpty(user) || StringUtil.isEmpty(password)) {
					out.print("<script type='text/javascript'>alert('�������û��������룡');window.history.go(-1);</script>");
					return;
				} else if (qb.executeString().equals("0")) {
					out.print("<script type='text/javascript'>alert('�û������������벻��ȷ��');window.history.go(-1);</script>");
					return;
				} else if (qb.executeInt() > 0) {
					Login login = new Login();
					login.loginComment(request, response, user, password);
				}
			}
			if (StringUtil.isEmpty(title)) {
				title = "��";
			}

			ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(catalogID);
			boolean verifyFlag = false;
			if (catalogConfig != null) {
				if ("Y".equals(catalogConfig.getCommentVerify())) {
					verifyFlag = true;
				}
			}
			String CommentAddUser = "";
			String CommentAddTime = "";
			String CommentAddUserIp = "";
			String CommentContent = "";
			if(StringUtil.isNotEmpty(parentID)) {
				DataTable dt = new QueryBuilder("select * from ZCComment where ID = ?", Long.parseLong(parentID)).executeDataTable();
				
				if (dt.getRowCount() > 0) {
					CommentAddUser = dt.getString(0, "AddUser");
					CommentAddTime = dt.getString(0, "AddTime");
					CommentAddUserIp = dt.getString(0, "AddUserIP");
					CommentContent = dt.getString(0, "Content");
				}
			}

			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(NoUtil.getMaxID("CommentID"));
			comment.setCatalogID(catalogID);
			comment.setCatalogType(catalogType);
			comment.setRelaID(relaID);
			comment.setSiteID(siteID);
			comment.setTitle(StringUtil.htmlEncode(BadWord.filterBadWord(StringUtil.subStringEx(title,90))));
			if (parentID != "" && parentID != null) {
				comment.setContent("<div class=\"huifu\">" + CommentAddUser + " " + CommentAddTime + " IP:"
						+ CommentAddUserIp + "<br>" + CommentContent + "</div><br>" + StringUtil.htmlEncode(StringUtil.subStringEx(content,900)));
			} else {
				comment.setContent(StringUtil.htmlEncode(StringUtil.subStringEx(content,900)));
			}
			comment.setAddUser(user);
			comment.setAddTime(new Date());
			comment.setAddUserIP(ip);
			if (verifyFlag) {
				comment.setVerifyFlag("X");
			} else {
				boolean hasBadWord = false;
				if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, BadWord.TREELEVEL_1))) {
					hasBadWord = true;
				}
				if (!hasBadWord) {
					if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, BadWord.TREELEVEL_2))) {
						hasBadWord = true;
					}
				}
				if (!hasBadWord) {
					if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, BadWord.TREELEVEL_3))) {
						hasBadWord = true;
					}
				}
				if (hasBadWord) {
					comment.setVerifyFlag("X");
				} else {
					comment.setVerifyFlag("Y");
				}
			}
			if (comment.insert()) {
				if (verifyFlag) {
					out.println("<script type='text/javascript'>alert('���������Ѿ��ύ,��ȴ�����Ա���');window.location='"
							+ request.getHeader("REFERER") + "';</script>");
				} else {
					out.println("<script type='text/javascript'>alert('�������۳ɹ�');window.location='"
							+ request.getHeader("REFERER") + "';</script>");
				}
			} else {
				out.println("<script type='text/javascript'>alert('��������ʧ��');window.location='"
						+ request.getHeader("REFERER") + "';</script>");
			}

		} else {
			out.print("<script type='text/javascript'>alert('�ύ�����ݲ��ܿ�');window.location='"
					+ request.getHeader("REFERER") + "';</script>");
		}
	}
}
