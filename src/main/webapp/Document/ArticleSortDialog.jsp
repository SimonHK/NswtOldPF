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
<table width="100%" border="0" cellspacing="6" class="cellspacing"
	cellpadding="0">
	<tr>
		<td><z:datagrid id="dg1"
						method="com.nswt.cms.document.ArticleList.dg1DataBind" size="10"
						multiSelect="false">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="5%" height="30" ztype="RowNo"></td>
					<td width="4%" height="30" ztype="Selector" field="ID"></td>
					<td width="56%" sortfield="orderflag" direction=""><b>����</b></td>
					<td width="7%"><strong>������</strong></td>
					<td width="6%"><strong>�ö�</strong></td>
					<td width="7%"><strong>״̬</strong></td>
					<td width="15%" sortfield="publishdate" direction=""><strong>����ʱ��</strong></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>${Title}</td>
					<td>${AddUser}</td>
					<td>${TopFlag}</td>
					<td>${StatusName}</td>
					<td>${PublishDate}</td>
				</tr>
				<tr ztype="pagebar">
					<td colspan="9" align="left">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</body>
</html>
