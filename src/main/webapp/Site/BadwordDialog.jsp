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
<body class="dialogBody">
<form id="form2">
<table width="100%" height="123" cellpadding="3" cellspacing="0">
	<tr>
		<td width="12" colspan="3" height="10"></td>
	</tr>
	<tr>
		<td width="12"></td>
		<td align="right">���дʣ�</td>
		<td><input name="BadWord" type="text" value="" class="input1"
			id="BadWord" verify="���д�|NotNull" size=25 /> <input name="ID"
			type="hidden" id="ID" /></td>
	</tr>
	<tr>
		<td></td>
		<td align="right">���м���</td>
		<td><z:select name="Level" id="Level">
			<span value="1" selected>һ��</span>
			<span value="2">�Ƚ�����</span>
			<span value="3">�ر�����</span>
		</z:select></td>
	</tr>
	<tr>
		<td></td>
		<td height="26" align="right">�滻�ʣ�</td>
		<td height="26"><input name="ReplaceWord" type="text"
			class="input1" id="ReplaceWord" value="" size="25" /></td>
	</tr>

	<tr>
		<td height="5" align="right"></td>
		<td height="5"></td>
	</tr>
</table>
</form>
</body>
</html>
