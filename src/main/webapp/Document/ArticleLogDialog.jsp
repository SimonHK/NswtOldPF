<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSearch(){
	var name = $V("UserName").trim();
	var startDate=$V("startDate").trim();
	var endDate=$V("endDate").trim();
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","UserName",name);
	DataGrid.setParam("dg1","startDate",startDate);
	DataGrid.setParam("dg1","endDate",endDate);
	DataGrid.setParam("dg1","ArticleID",$V("ArticleID"));
	DataGrid.loadData("dg1");
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<input type="hidden" name="AritcleID" id="ArticleID" value="<%=request.getParameter("ArticleID")%>">
<div style="padding:5px;white-space: nowrap;">
��ʼʱ�䣺<input ztype="date" type="text" size="15" value="" id="startDate" />&nbsp;&nbsp;
����ʱ�䣺<input ztype="date" type="text" size="15" value="" id="endDate" />&nbsp;&nbsp;
������Ա��<input name="UserName" type="text" id="UserName" value=""  style="width:100px">
<input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="doSearch()">
</div>
<div style="padding:5px;"><z:datagrid id="dg1" method="com.nswt.cms.document.ArticleNote.logDataBind">
	<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
		<tr ztype="head" class="dataTableHead">
			<td width="6%" ztype="RowNo"><strong>���</strong></td>
			<td width="55%""><b>������ϸ</b></td>
			<td width="19%"><strong>������</strong></td>
			<td width="22%"><strong>����ʱ��</strong></td>
		</tr>
		<tr style1="background-color:#FFFFFF"
			style2="background-color:#F9FBFC">
			<td>&nbsp;</td>
			<td>${ActionDetail}</td>
			<td>${addUser}</td>
			<td>${addTime}</td>
		</tr>
	</table>
</z:datagrid></div>
</body>
</html>
