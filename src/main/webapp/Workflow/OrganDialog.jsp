<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<table width="490" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
				<z:datagrid id="dg1" method="com.nswt.platform.Branch.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="sortBranch">
						<tr ztype="head" class="dataTableHead">
							<td width="7%" ztype="RowNo"></td>
							<td width="9%" ztype="selector" field="BranchInnerCode">&nbsp;</td>
							<td width="41%" ztype="tree" level="TreeLevel"><b>����</b></td>
							<td width="43%"><strong>��ע</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td>${Memo}</td>
						</tr>
					</table>
				</z:datagrid>
		</td>
	</tr>
</table>
</body>
</html>
