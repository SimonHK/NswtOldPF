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
var chart;
Page.onLoad(function(){
	var startDate = DateTime.toString(new Date(new Date()-30*24*60*60*1000));
	var endDate = DateTime.toString(new Date());
	
	chart = new Chart("Pie3D","Chart1",680,300,"com.nswt.cms.stat.report.DistrictReport.getChartData");
	chart.addParam("StartDate",startDate);
	chart.addParam("EndDate",endDate);
	chart.addParam("Code","<%=request.getParameter("Code")%>");
	chart.addParam("Type","P");
	chart.show("divChart");
	
	$S("StartDate",startDate);
	$S("EndDate",endDate);
});


function loadData(){
	chart.addParam("StartDate",$V("StartDate"));
	chart.addParam("EndDate",$V("EndDate"));
	chart.addParam("Code","<%=request.getParameter("Code")%>");
	chart.addParam("Type",$V("Type"));
	chart.show("divChart");
	
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.setParam("dg1","Code","<%=request.getParameter("Code")%>");
	DataGrid.setParam("dg1","Type",$V("Type"));
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
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
				<div style="float:left;"><strong>�����ֲ�:</strong> &nbsp; <z:select
					id="Type">
					<span value="C">ȫ��ֲ�</span>
					<span value="P" selected="true">���ڷֲ�</span>
				</z:select> &nbsp;�� <input name="text" type="text" id="StartDate"
					style="width:90px; " ztype='date'> �� <input name="text2"
					type="text" id="EndDate" style="width:90px; " verify="Date"
					ztype='date'> <input type="button" verify="Date"
					name="Submitbutton" id="Submitbutton" value="�鿴"
					onClick="loadData()"></div>
				</td>
			</tr>
			<tr>
				<td style="padding:0;">
				<div id="divChart" style="width:680px;height:300px"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
					method="com.nswt.cms.stat.report.DistrictReport.dg1DataBind"
					autoFill="false" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="22%">����</td>
							<td width="15%"><strong>PV</strong></td>
							<td width="16%"><strong>PV�ٷֱ�</strong></td>
							<td width="15%">UV</td>
							<td width="15%">IP</td>
							<td width="17%">PV����</td>
						</tr>
						<tr>
							<td>${Item}</td>
							<td>${PV}</td>
							<td>${Rate}</td>
							<td>${UV}</td>
							<td>${IP}</td>
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
