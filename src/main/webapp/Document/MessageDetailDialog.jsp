<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body>
<z:init method="com.nswt.cms.document.Message.initDetailDialog">
	<form id="formMsg">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td align="center" valign="top">
			<table width="96%" border="1" cellpadding="4" cellspacing="0" bordercolor="#e3e3e3"
				style="margin:10px auto">
				<tr>
					<td width="80" height="28" align="right" valign="top" class="tdgrey1"><input
						type="hidden" id="ID" name="ID" value="${ID}"> <input
						type="hidden" id="Type" name="Type" value="${Type}">
				  ${UserType}���ˣ�</td>
					<td align="left" class="tdgrey2">${FromUser}${ToUser}</td>
				</tr>
				<tr>
					<td height="28" align="right" valign="top" class="tdgrey1">����ʱ�䣺</td>
					<td align="left" class="tdgrey2">${AddTime}</td>
				</tr>
				<tr>
					<td height="28" align="right" valign="top" class="tdgrey1">���⣺</td>
					<td align="left" class="tdgrey2">${Subject}</td>
				</tr>
				<tr>
					<td height="28" align="right" valign="middle" class="tdgrey1">���ݣ�</td>
					<td align="left" valign="top" class="tdgrey2">${Content}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
