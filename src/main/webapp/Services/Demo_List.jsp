<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="z" %>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.data.DataTable"%>
<%@page import="com.nswt.framework.utility.*"%>

<%@page import="com.nswt.cms.pub.*"%>
<%@page import="com.nswt.cms.dataservice.*"%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Demo</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
long catalogID = 10743;//��ĿID
String strpage = request.getParameter("page");
strpage = StringUtil.isEmpty(strpage)?"1":strpage;

String size = request.getParameter("size");
size  = StringUtil.isEmpty(size)?"10":size; 

int pageSize = Integer.parseInt(size);
int pageIndex = Integer.parseInt(strpage) - 1;

DataTable articles = new QueryBuilder("select * from ZCArticle where CatalogID=? and Status=30 order by TopFlag,OrderFlag",catalogID).executePagedDataTable(pageSize,pageIndex); 
ColumnUtil.extendDocColumnData(articles, catalogID);//�����Զ����ֶ�

int total = new QueryBuilder("select count(*) from ZCArticle where CatalogID=? and Status=30 order by TopFlag,OrderFlag",catalogID).executeInt();
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<% for(int i = 0; i < articles.getRowCount(); i++) { %>
	<tr>
	    <td>  
		    <div class="Article">
				<div class="title"><b><a href="Demo_Detail.jsp?ArticleID=<%=articles.getString(i, "id")%>"><%=articles.getString(i, "Title") %></a></b></div>
				<div class="content"><%=articles.getString(i, "Content") %></div>
			</div>
		</td>
	  </tr>
	<% } %>
	
		<tr>
		<td nowrap><span style="font-size: 13px;">ҳ��:&nbsp;</span></td>
		<%
			int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
			int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
			int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
			if (pageIndex > 1) {
				out.println(" <td nowrap><a href='Demo_List.jsp?page="+ pageIndex+ "'>��һҳ</a></td>");
			}
			for (int i = start; i <= end; i++) {
				if (i == pageIndex + 1) {
					out.println("<td nowrap><span class=i>" + i	+ "</span></td>");
				} else {
					out.println("<td nowrap><a href='Demo_List.jsp?page="+i + "'>[" + i+ "]</a></td>");
				}
			}
			if (pageIndex < pageCount - 1) {
				out.println(" <td nowrap><a href='Demo_List.jsp?page="+ (pageIndex + 2)+ "'>��һҳ</a></td>");
			}
		%>
	</tr>
</table>
</body>
</html>