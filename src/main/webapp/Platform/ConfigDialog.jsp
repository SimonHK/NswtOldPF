<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
	<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td width="89" height="10" ></td>
		<td ></td>
	</tr>
	<tr>
		<td align="right" >��������룺</td>
		<td width="249" >
		<input name="Type" type="text" value="" id="Type" verify="���������|NotNull" size="30"/></td>
	</tr>
	<tr>
		<td align="right" >���������ƣ�</td>
		<td >
		<input name="Name" type="text" value="" id="Name" verify="����������|NotNull" size="30"/></td>
	</tr>
	<tr>
		<td align="right" >������ֵ��</td>
		<td ><textarea value="" id="Value" name="Value" cols="36" rows="4" class="input3" verify="������ֵ|NotNull&length<66" ></textarea></td>
	</tr>
</table>
	</form>
</body>
</html>
