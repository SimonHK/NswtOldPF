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
	<table width="378" cellpadding="2" cellspacing="0">
		<tr>
			<td height="10">&nbsp;</td>
			<td></td>
		</tr>
		<tr>
			<td align="right">ID��</td>
			<td height="30"><input id="ID" name="ID" type="text"
				 class="input1" verify="ID|NotNull" size=30 /></td>
		</tr>
		<tr>
			<td align="right">���ƣ�</td>
			<td height="30"><input id="Name" name="Name" type="text"
				 class="input1" verify="����|NotNull" size=30 /></td>
		</tr>
					<tr>
				<td align="right">�������ͣ�</td>
				<td >
				<z:select id="DataType" style="width:150px;">
										<span value="string" selected>�ı���</span>
										<span value="datetime" >����</span>
				</z:select>
				</td>
			</tr>
	</table>
	</form>
</body>
</html>
