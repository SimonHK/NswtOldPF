<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ƶ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Search","Search");
	DataGrid.setParam("dg1","_CatalogID",$V("CatalogID"));
	DataGrid.setParam("dg1","Name",$V("Name"));
	DataGrid.setParam("dg1","Info",$V("Info"));
	DataGrid.loadData("dg1");
}

function onVideoReturnBack(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		alert("����ѡ��Ҫ���ص���Ƶ��");
		return;
	}
	if(window.parent.Parent.onVideoReturnBack){
		window.parent.Parent.onVideoReturnBack(arr.join());
	}
	window.parent.Dialog.close();
}

</script>
</head>
<body oncontextmenu="return false;">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td>
		<table width="100%" cellpadding="2" cellspacing="0">
			<tr>
				<td width="20%" align="left"><label>������:<z:select
					id="CatalogID" listWidth="200" listHeight="300"
					listURL="Resource/VideoLibSelectList.jsp"> </z:select></label></td>
				<td width="6%">���ƣ�</td>
				<td width="16%"><input name="Name" id="Name" type="text"
					value=""></td>
				<td width="8%">������</td>
				<td width="17%"><input name="Info" id="Info" type="text"
					value=""></td>
				<td width="20%"><input type="button" name="Submit" value="��ѯ"
					onClick="search()"></td>
			</tr>
		</table>
		</td>

	</tr>
	<tr>
		<td style="padding:0px 5px;"><z:datagrid id="dg1"
												 method="com.nswt.cms.resource.VideoLib.dg1DataBindBrowse" size="12">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="5%" ztype="RowNo">���</td>
					<td width="5%" ztype="selector" field="ID">&nbsp;</td>
					<td width="22%"><b>��Ƶ����</b></td>
					<td width="33%"><b>��Ƶ����</b></td>
					<td width="8%"><b>��Ƶ��С</b></td>
					<td width="13%"><b>�ϴ��û�</b></td>
					<td width="14%"><b>�ϴ�ʱ��</b></td>
				</tr>
				<tr style1="background-color:#FFFFFF"
					style2="background-color:#F9FBFC">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>${Name}</td>
					<td>${Info}</td>
					<td>${Size}</td>
					<td>${AddUser}</td>
					<td>${AddTime}</td>
				</tr>
				<tr ztype="pagebar">
					<td colspan="8">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</body>
</html>
