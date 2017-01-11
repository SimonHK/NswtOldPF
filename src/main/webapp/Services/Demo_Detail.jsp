<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=GBK"%> 
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import="com.nswt.framework.cache.*"%>
<%@page import="com.nswt.framework.orm.*"%>
<%@page import="com.nswt.framework.controls.*"%>
<%@page import="com.nswt.schema.*"%>
<%@page import="com.nswt.platform.*"%>
<%@page import="com.nswt.cms.site.*"%>
<%@page import="com.nswt.cms.document.*"%>
<%@page import="com.nswt.cms.dataservice.*"%>
<%@page import="com.nswt.cms.pub.*"%>
<%@page import="com.nswt.platform.pub.*"%>
<%
long id = Long.parseLong(request.getParameter("ArticleID"));
String type = request.getParameter("Type");
DataTable dt = null;
if("B".equals(type)){
	dt = new QueryBuilder("select * from BZCArticle where ID=? order by BackupNo desc",id).executeDataTable();
}else{
	dt = new QueryBuilder("select * from ZCArticle where ID=?",id).executeDataTable();
}
if(dt.getRowCount()==0){
	out.println("û���ҵ�idΪ"+id+"������");
	return;
}
DataRow article  = dt.getDataRow(0);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%=article.get("Title")%></title>
<style>
div{ line-height:1.5; font-size: 14px;}
div div{ padding:5px; background-color:#f9f9f9;border:#e6e6e6 1px solid;}
div div div{ padding:5px 10px; background-color:#f5f5f5; border:none;}
</style>
</head>
<body style="text-align:center; background:#fff">
<div style="margin:20px auto; width:70%; text-align:left;">
<div style="margin-bottom:10px;">��ǰλ�ã�<%=PubFun.getCurrentPage(article.getLong("CatalogID"),0,article.getString("Name")," > ","")%>
</div>
<div>
<div style=" float:right; font-size:12px;">���ߣ�<%=article.get("Author")%></div>
<div style="text-align:center; margin:0;"><b><%=article.get("Title")%></b></div>
<% if("4".equals(article.getString("Type"))){%>
<div style="padding:20px; margin:10px 0 0 0;">��ת���ӣ�<a href="<%=article.get("RedirectURL")%>"><%=article.get("RedirectURL")%></a></div>
<% }else{%>
<div style="padding:20px; margin:10px 0 0 0;"><%=article.get("Content")%></div>
<% }%>
</div>
</div>
</body>
</html>

