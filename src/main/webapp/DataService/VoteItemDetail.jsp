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

<script type="text/javascript">

function subjectDetail(SubjectID,LogID){
	var diag = new Dialog("DiagSD");
	diag.Width = 500;
	diag.Height = 220;
	diag.Title = "填写详细内容";
	diag.URL = "DataService/SubjectDetail.jsp?SubjectID="+SubjectID+ "&LogID=" + LogID;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
function ItemDetail(ItemID,LogID){
	var diag = new Dialog("DiagSD");
	diag.Width = 500;
	diag.Height = 220;
	diag.Title = "填写详细内容";
	diag.URL = "DataService/ItemDetail.jsp?ItemID="+ItemID+"&LogID=" + LogID;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
function exportAll(){
	DataGrid.allToExcel("dg2",1);
}
function exportCurrentPage(){
	DataGrid.allToExcel("dg2",0);
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr>
			<td style="padding:0 8px 4px;">
				<z:tbutton onClick="exportAll()">
				<img src="../Icons/icon032a7.gif" />导出全部</z:tbutton>
				<z:tbutton onClick="exportCurrentPage()">
				<img src="../Icons/icon032a7.gif" />导出当前页</z:tbutton>
			</td>
		</tr>
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.nswt.cms.dataservice.Vote.dg3DataBind" size="10">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo">序号</td>
								<td width="5%" align="center" ztype="selector" field="id">&nbsp;</td>
								<td width="15%">IP</td>
								<td width="15%">投票人</td>
								<td width="26%">投票时间</td>
								<td width="34%">填写内容</td>
							</tr>
							<tr >
								<td align="center">&nbsp;</td>
								<td align="center">&nbsp;</td>
								<td>${IP}</td>
								<td>${addUser}</td>
								<td>${addTime}</td>
								<td >${OtherContents}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="12" align="center">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid>		
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</body>
</html>