<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<form id="form2"><z:init
	method="com.nswt.platform.UserLog.initDialog">
	
			<table width="450" align="center" cellpadding="2" cellspacing="0">
            	<tr>
					<td width="114"  align="right"></td>
					<td width="466" ></td>
				</tr>
				<tr>
					<td width="114"  align="right" >�û�����</td>
					<td width="466" >${UserName}</td>
				</tr>
				<tr>
					<td  align="right" >������</td>
					<td >${RealName}</td>
				</tr>
				<tr>
					<td  align="right" >ʱ�䣺</td>
					<td >${AddTime}</td>
				</tr>
				<tr>
					<td  align="right" >��Ϣ���ͣ�</td>
					<td >${LogType}</td>
				</tr>
				<tr>
					<td  align="right" >IP��</td>
					<td >${IP}</td>
				</tr>
				<tr>
					<td  align="right" >��Ϣ��</td>
					<td >${LogMessage}</td>
				</tr>
			</table>
</z:init></form>
</body>
</html>
