<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>投票项详细信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script >
function checkResult(VoteID,SubjectID,ItemID){
	var diag = new Dialog("DiagVT");
	diag.Width = 700;
	diag.Height = 350;
	diag.Title = "查看投票项详情";
	diag.URL = "DataService/VoteItemDetail.jsp?VoteID="+VoteID+"&SubjectID="+SubjectID+"&ItemID="+ItemID;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
</script>
</head>
<body>
	<table width="100%" cellpadding="2" cellspacing="0"
		class="dataTable" >
		<tr ztype="head" class="dataTableHead">
			<td width="50%">名称</td>
			<td width="20%"><b>结果</b></td>
		</tr>
		<z:simplelist method="com.nswt.cms.dataservice.Vote.listSubject">
		<tr>
			<td>${_RowNo}.${Subject}</td>
			<td></td>
		</tr>
		<z:simplelist method="com.nswt.cms.dataservice.Vote.listItem">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;${_RowNo}.${Item}</td>
				<td>${Result}</td>
			</tr>
			</z:simplelist>
		</z:simplelist>
	</table>

</body>
</html>
