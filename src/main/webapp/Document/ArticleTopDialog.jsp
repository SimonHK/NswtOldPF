<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<%	String IDs = request.getParameter("IDs");
	%>
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td height="10"><input id="IDs" type="hidden"
			value="<%=IDs%>" /></td>
	</tr>
	<tr>
		<td align="left">�ö���Ч���ޣ�<input id="TopDate" type="text" name="TopDate" verify="Date" ztype='date' style="width:80px;"/>
			<input id="TopTime" type="text" name="TopTime" value="23:59:59" verify="Time" ztype='time' style="width:80px;"/></td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td align="left"><b style="color:#F03">�ڴ�����֮ǰ���Ŵ����ö�״̬���������Զ�ȡ���ö��������������ö�����</b></td>
	</tr>
</table>
</form>
</body>
</html>