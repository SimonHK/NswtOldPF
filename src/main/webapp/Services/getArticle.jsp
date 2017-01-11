<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.nswt.framework.data.DataTable"%>
<%@page import="com.nswt.project.avicit.GetArticle"%><%@page import="com.nswt.cms.pub.PubFun"%>
<%@page import="com.nswt.schema.ZCArticleSchema"%>
<%@page import="com.nswt.platform.Application"%>
<%@page import="com.nswt.cms.pub.SiteUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>½ðº½ÊýÂë</title>
</head>
<style type="text/css">
a {
	text-decoration:none;
}
.info {
	width:627px;
}
ul, li {
	list-style:none outside;
	margin:0;
	padding:0;
}
ul.listinfo {
	padding-left:15px;
	clear:both;
}
ul.listinfo li {
	height:22px;
	line-height:22px;
	font-size:12px;
	color:#2b477f;
}
ul.listinfo li a {
	color:#2b477f;
}
.bom {
	border:1px #99bbe8 solid;
}
.frm {
	float:right;
	margin-right:16px;
}
.title {
	height:31px;
	line-height:30px;
	background:url(images/title.gif) no-repeat left top;
	padding-left:20px;
	color:#10789b;
	font-size:13px;
	font-weight:bold;
}
.title .frm img{ margin-top:6px; +margin-top:0px !important;  _margin-top:6px;  cursor:pointer; margin-left:5px;}
</style>
<body>
<div class="info">
  <div class="bom">
    <ul class="listinfo">
    <%
    	String catalogID = request.getParameter("catalog");
    	String userName = request.getParameter("u");
    	String password = request.getParameter("p");
    	String count = request.getParameter("count");
    	DataTable dt = GetArticle.getArticle(catalogID, userName, password, count);
    	for (int i=0; dt!=null && i<dt.getRowCount(); i++) {
    		ZCArticleSchema article=new ZCArticleSchema();
    		article.setID(dt.getString(i,"ID"));
    		article.fill();
    %>
      <li><span class="frm"><%=dt.getString(i, "PublishDate")%></span><a href="<%=SiteUtil.getURL(Application.getCurrentSiteID())%>/<%=PubFun.getArticleURL(article)%>"><%=dt.getString(i,"Title") %></a></li>
      <%} %>
    </ul>
  </div>
</div>
</body>
</html>