<%@ page contentType="text/html;charset=gbk"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import=" com.nswt.cms.pub.SiteUtil"%>
<%@ taglib uri="controls" prefix="z"%>
<%
	String siteID = request.getParameter("SiteID");
	boolean commentFlag = SiteUtil.getCommentAuditFlag(siteID);
	String WherePart = "";
	if (commentFlag) {
		WherePart = " and verifyflag='Y'"; //ÆÀÂÛÐèÒªÉóºË
	}
	String relaID = request.getParameter("RelaID");
	String sql = "select count(*) from zccomment where RelaID=?" + WherePart;
	int count = new QueryBuilder(sql,relaID).executeInt();
	out.println("if(navigator.userAgent.toLowerCase().indexOf(\"gecko\") != -1)document.getElementById(\"CmntCount\").textContent=\"" + count + "\";else document.getElementById(\"CmntCount\").innerText=\"" + count + "\";");
%>