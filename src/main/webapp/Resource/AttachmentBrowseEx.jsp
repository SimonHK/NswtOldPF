<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Search","Search");
	DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
	DataGrid.setParam("dg1","Name",$V("Name"));
	DataGrid.setParam("dg1","Info",$V("Info"));
	DataGrid.loadData("dg1");
}

function onFileReturnBack(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���صĸ�����");
		return;
	}
	var dc = new DataCollection();
	dc.add("AttachID",arr[0]);
	Server.sendRequest("com.nswt.cms.resource.Attachment.getAttachSrc",dc,function(response){
		path = response.get("AttachPath");
		if(window.parent.onReturnBack){
			window.parent.onReturnBack(path);
		}
		window.parent.Dialog.close();
	});
}
</script>
</head>
<body oncontextmenu="return false;">
<div style="height:100%;overflow:auto;">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td>
		<table width="100%" cellpadding="2" cellspacing="0" id="tbSearch">
			<tr>
				<td width="18%" align="left"><label>��������:<z:select
					id="CatalogID" style="width:80px" listWidth="200" listHeight="300"
					listURL="Resource/AttachmentLibSelectList.jsp"> </z:select></label></td>
				<td width="7%">���ƣ�</td>
				<td width="17%"><input name="Name" id="Name" type="text"
					value=""></td>
				<td width="8%">������</td>
				<td width="19%"><input name="Info" id="Info" type="text"
					value=""></td>
				<td width="20%">
                <z:tbutton id="searchButton" onClick="doSearch()"><img src="../Icons/icon003a8.gif" />��ѯ</z:tbutton>
                <z:tbutton id="returnButton" onClick="onFileReturnBack()"><img src="../Icons/icon003a8.gif" />ȷ��</z:tbutton>
                </td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding:0px 5px;"><z:datagrid id="dg1"
			method="com.nswt.cms.resource.AttachmentLib.dg1DataBindBrowse"
			size="10">
			<table class="dataTable" cellspacing="0" cellpadding="2" width="100%">
				<tr ztype="head" class="dataTableHead">
					<td width="4%" ztype="RowNo"></td>
					<td width="5%" ztype="selector" field="ID">&nbsp;</td>
					<td width="20%"><b>��������</b></td>
					<td width="30%"><b>��������</b></td>
					<td width="7%"><b>������С</b></td>
					<td width="8%"><b>������ʽ</b></td>
					<td width="9%"><b>�ϴ��û�</b></td>
					<td width="17%"><b>�ϴ�ʱ��</b></td>
				</tr>
				<tr style1="background-color:#FFFFFF; cursor:default;"
					style2="background-color:#F9FBFC; cursor:default;">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><font color="#0099FF">${Name}</font></td>
					<td>${Info}</td>
					<td>${FileSize}</td>
					<td>${SuffixImage}</td>
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
</div>
</body>
</html>
