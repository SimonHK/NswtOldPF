<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>投票项填写内容明细</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
</head>
<body>
<z:init method="com.nswt.cms.dataservice.Vote.initDetailItem">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr ztype="head" class="dataTableHead">
			<td >投票IP:${IP}</td>
			<td >投票人:${addUser}</td>
			<td >投票时间:${addTime}</td>
		</tr>
		<tr >
			<td colspan="3">填写内容:</td>
		</tr>
	</table>
	<div style="word-wrap:break-word; width:95.5%; margin:0 2%;">${Content}</div>
</z:init>
</body>
</html>