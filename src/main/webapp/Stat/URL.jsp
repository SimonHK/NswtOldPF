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
	var startDate = DateTime.toString(new Date(new Date()-30*24*60*60*1000));
	var endDate = DateTime.toString(new Date());
	
	$S("StartDate",startDate);
	$S("EndDate",endDate);
});

function loadData(){
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
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
				<div style="float:left;"><strong>URL���������:</strong> &nbsp; �� <input
					name="text" type="text" id="StartDate" style="width:90px; "
					ztype='date'> �� <input name="text2" type="text"
					id="EndDate" style="width:90px; " verify="Date" ztype='date'>
				<input type="button" verify="Date" name="Submitbutton"
					id="Submitbutton" value="�鿴" onClick="loadData()"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
					method="com.nswt.cms.stat.report.URLReport.dgTopDataBind"
					autoFill="false" size="50">
					<table width="100%" align="center" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="6%" ztype="RowNo">���</td>
							<td width="52%">URL</td>
							<td width="14%">�����</td>
							<td width="14%">�ٷֱ�</td>
							<td width="14%">����</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><a href="${Item}"
								target="_blank">${Item}</a></td>
							<td>${Top}</td>
							<td>${Rate}</td>
							<td>${Trend}</td>
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
