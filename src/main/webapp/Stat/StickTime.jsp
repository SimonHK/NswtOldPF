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
	var path = window.location.pathname;
	path = path.substring(path.lastIndexOf("/")+1);
	Tree.select("tree1","target",path);
	
	var startDate = DateTime.toString(new Date(new Date()-30*24*60*60*1000));
	var endDate = DateTime.toString(new Date());
	
	chart = new Chart("Line2D","Chart1",680,250,"com.nswt.cms.stat.report.LoyaltyReport.getStickTimeChartData");
	chart.addParam("StartDate",startDate);
	chart.addParam("EndDate",endDate);
	chart.show("divChart");
	
	$S("StartDate",startDate);
	$S("EndDate",endDate);
});

function loadData(){
	chart.addParam("StartDate",$V("StartDate"));
	chart.addParam("EndDate",$V("EndDate"));
	chart.show("divChart");
	
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
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
				<div style="float:left;"><strong>ͣ��ʱ��:</strong> &nbsp;�� <input
					type="text" id="StartDate" style="width:90px; " ztype='date'>
				�� <input type="text" id="EndDate" style="width:90px; " verify="Date"
					ztype='date'> <input type="button" verify="Date"
					name="Submitbutton" id="Submitbutton" value="�鿴"
					onClick="loadData()"></div>
				</td>
			</tr>
			<tr>
				<td style="padding:0;">
				<div id="divChart" style="width:680px;height:250px"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
					method="com.nswt.cms.stat.report.LoyaltyReport.dgStickTimeDataBind"
					autoFill="false" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="8%" ztype="RowNO">���</td>
							<td width="42%">����</td>
							<td width="24%"><strong>�Ựͣ��ʱ��</strong></td>
							<td width="26%">ҳ��ͣ��ʱ��</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>${Date}</td>
							<td>${StickTotalTime}</td>
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
