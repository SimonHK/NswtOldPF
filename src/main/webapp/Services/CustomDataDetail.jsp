<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import="com.nswt.cms.dataservice.*"%>
<%
request.setCharacterEncoding("GBK");
response.setContentType("text/html;charset=GBK");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�Զ���������ϸ��Ϣ</title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Chart.js"></script>
<style type="text/css">
#voteresult{
	width:850px;
	margin:0 auto;
	margin-top:10px;
	background:#E1F4FF;
	padding:10px;}
	#voteresult h2{
	font-size:14px;
	font-weight:bold;
	line-height:30px;
	padding:0;
	margin:0;
	padding-left:5px;
}
.voteresultb{
	padding:10px;
	border:1px solid #58CDFA;
	background:#FFFFFF;
}
.voteresultb table{
	width:100%;
	font-size:12px;
	border-collapse:collapse;
}
.voteresultb td {
	padding:4px 8px;
	text-indent:0;
	background-color:#FFFFFF;
	color:#363636;
}
.voteresultb td, .voteresultb th{
	border-collapse:collapse;
	border-color:#D5D9D8;
	border-style:solid;
	border-width:1px;
}
</style>
</head>
<%
if(StringUtil.isEmpty(request.getParameter("ID"))){
	out.println("ID����Ϊ��");
	return;
}
QueryBuilder qb = new QueryBuilder("where ID=?",request.getParameter("ID"));
DataTable dt = CustomTableUtil.executeDataTable(request.getParameter("TableCode"),qb);
%>
<body style=" text-align:center">
<div id="voteresult" style=" text-align:left">
  <h2>�����б�</h2>
  <div class="voteresultb">
 	<%if(dt.getRowCount()==0){%>
		δ�ҵ��������������ݡ�
	<%}%>
    <table width="100%" cellspacing="2">
		<tr>
		<td>����</td>
		<td><%=dt.getString(0,"Name")%></td>
		</tr>
		<tr>
		<td>֤������</td>
		<td><%=dt.getString(0,"CertNo")%></td>
		</tr>
		<tr>
		<td>�ܳɼ�</td>
		<td><%=dt.getString(0,"TotalScore")%></td>
		</tr>
		<tr>
		<td>���ʱ��</td>
		<td><%=dt.getString(0,"AddDate")%></td>
		</tr>
    </table>
  </div>
</div>
</body>
</html>
