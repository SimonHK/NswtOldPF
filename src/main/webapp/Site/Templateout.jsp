<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%String path = request.getParameter("path");%>
<title>����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="3" cellspacing="0">
	<tr>
		<td width="986" align="center">
		<p align="center">&nbsp;</p>
		<p>�ļ�ѹ����ϣ������أ�</p>
		<div style="text-align:center;">
		<p>&nbsp;</p>
		<p>���ص�ַ:<a href="<%=path%>">�������</a></p>
		</div>
	</tr>
	<tr>
		<td width="62" height="5" align="right"></td>
		<td width="8" height="5"></td>
	</tr>
</table>
</form>
</body>
</html>
