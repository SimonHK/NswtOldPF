<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.schema.ZCArticleSchema"%>
<%@page import="com.nswt.framework.Config"%>
<%
long aritcleID = Long.parseLong(request.getParameter("ArticleID"));
ZCArticleSchema article = new ZCArticleSchema();
article.setID(aritcleID);
if(!article.fill()){
	out.println("û���ҵ�idΪ"+aritcleID+"������");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>����Ԥ��--<%=article.getTitle()%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<%@ include file="Verify.jsp"%>
<div style="width:950px; margin:auto;">
  <table width="100%" border="0" cellspacing="15" cellpadding="0">
    <tr valign="top">
      <td>
      <div style="border:1px solid #ddd; padding:30px 20px;zoom:1;">
      <h4 style="text-align:center"><b><%=article.getTitle()%></b></h4><hr><div style="margin:20px; line-height:20px;"><%=article.getContent()%></div>
     </div></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>

</body>
</html>
