<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>վ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	if(_DialogInstance){
		_DialogInstance.resize(800,360);
	}
	DataGrid.select($("dg1"),"0");
	_DialogInstance.OKButton.onclick = Parent.executeImportSite;
	Dialog.endWait();
});
</script>
</head>
<body scroll="no">
<input type="hidden" id="FileName" value="${FileName}">
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td valign="middle" class="blockTd"> <img
						src="../Icons/icon040a1.gif" width="20" height="20" />&nbsp;��ѡ���뵽�ĸ�վ�㣺 </td>
				</tr>
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.site.Site.importStep2DataBind" multiSelect="false" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="rowNo">���</td>
								<td width="4%" ztype="selector" field="id">&nbsp;</td>
								<td width="12%"><b>���뷽ʽ</b></td>
								<td width="23%"><b>Ӧ������</b></td>
								<td width="15%"><b>Ӧ�ô���</b></td>
								<td width="41%"><b>����</b></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${Type}</td>
								<td>${Name}</td>
								<td>${Alias}</td>
								<td>${URL}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="9" align="center">${PageBar}</td>
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
</z:init>