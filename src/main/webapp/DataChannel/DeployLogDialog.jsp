<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
.tdgrey1 { background-color:#f9f9f9;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.datachannel.DeployLog.initDialog">
	<table width="100%" border="1" cellpadding="4" cellspacing="0" bordercolor="#dddddd" style="margin:10px auto;">
		<tr>
			<td align="right" valign="top" class="tdgrey1" width="100"><strong>���Ʒ�ʽ��</strong></td>
			<td class="tdgrey2">${methodDesc}</td>
		</tr>
		<tr>
			<td align="right" valign="top" class="tdgrey1"><strong>����Ŀ¼��</strong></td>
			<td class="tdgrey2">${source}</td>
		</tr>
		<tr>
			<td align="right" valign="top" class="tdgrey1" nowrap><strong>��������ַ��</strong></td>
			<td class="tdgrey2">${host}</td>
		</tr>
		<tr>
			<td align="right" valign="top" class="tdgrey1"><strong>Ŀ��Ŀ¼��</strong></td>
			<td class="tdgrey2">${target}</td>
		</tr>
		<tr>
			<td align="right" valign="top" class="tdgrey1" height=24><strong>ִ��ʱ�䣺</strong></td>
			<td class="tdgrey2" valign="top">${begintime}</td>
		</tr>
		<tr>
			<td align="right" valign="top" class="tdgrey1" height=120><strong>��Ϣ��</strong></td>
			<td class="tdgrey2" valign="top">${message}</td>
		</tr>
	</table>
</z:init>
</body>
</html>
