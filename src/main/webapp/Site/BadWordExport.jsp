<%@page import="java.io.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%
String ids = request.getParameter("ids");
String sql = "";
if(StringUtil.isNotEmpty(ids)){
	sql = "select Word,ReplaceWord,TreeLevel from ZCBadWord where id in ("+ids+")";
}else{
	sql = "select Word,ReplaceWord,TreeLevel from ZCBadWord";
}
DataTable dt = new QueryBuilder(sql).executeDataTable();
String now = DateUtil.getCurrentDate();
try {
	OutputStream os = response.getOutputStream();
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename="+ new String(("Ãô¸Ð´Êµ¼³ö"+now+".txt").getBytes("GBK"),"ISO-8859-1"));
	//DataTableUtil.dataTableToTxt(dt,os,new String[0]);
	os.flush();
	os.close();
} catch (IOException e) {
	e.printStackTrace();
}	
%>
