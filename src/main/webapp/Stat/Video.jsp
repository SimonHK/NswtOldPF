<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ۺϱ���</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Chart.js"></script>
<script>
Page.onLoad(function(){
	var path = window.location.pathname;
	path = path.substring(path.lastIndexOf("/")+1);
	Tree.select("tree1","target",path);
});

function loadData(){
	DataGrid.setParam("dg1","ID",$V("CatalogID"));
	DataGrid.loadData("dg1");
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="160"><%@include file="StatTypes.jsp"%>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td style="padding:4px 10px;">
				<div style="float:left;"><strong>��Ƶ���������:</strong> &nbsp; <z:select
					id="CatalogID" style="width:200px" listWidth="300" listHeight="350"
					listURL="Resource/VideoLibSelectList.jsp">
				</z:select> <input type="button" verify="Date" name="Submitbutton"
					id="Submitbutton" value="�鿴" onClick="loadData()"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
					method="com.nswt.cms.stat.report.TopReport.dgVideoDataBind"
					autoFill="false" size="50">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="21%">������Ŀ</td>
							<td width="43%">����</td>
							<td width="12%">������</td>
							<td width="11%">�����</td>
							<td width="13%">ҳ��ͣ��ʱ��</td>
						</tr>
						<tr>
							<td>${CatalogInnerCodeName}</td>
							<td>${Name}</td>
							<td>${AddUser}</td>
							<td>${HitCount}</td>
							<td>${StickTime}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
