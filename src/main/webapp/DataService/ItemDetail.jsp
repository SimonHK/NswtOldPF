<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>ͶƱ����д������ϸ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
</head>
<body>
<z:init method="com.nswt.cms.dataservice.Vote.initDetailItem">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr ztype="head" class="dataTableHead">
			<td >ͶƱIP:${IP}</td>
			<td >ͶƱ��:${addUser}</td>
			<td >ͶƱʱ��:${addTime}</td>
		</tr>
		<tr >
			<td colspan="3">��д����:</td>
		</tr>
	</table>
	<div style="word-wrap:break-word; width:95.5%; margin:0 2%;">${Content}</div>
</z:init>
</body>
</html>