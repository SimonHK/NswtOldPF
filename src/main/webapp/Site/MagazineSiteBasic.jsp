<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>��Ŀ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script> 
//�������ǰҳ��ʱ�������Ҽ��˵�
var iFrame =parent.parent;
Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}
});

var topFrame = window.parent;
function add(){
	topFrame.add();	
}

function publish(){
	topFrame.publish();	
}

function publishIndex(){
	topFrame.publishIndex();	
}

function del(){
	topFrame.del();
}

function batchAdd(){
	topFrame.batchAdd();
}

function addMag(){
	topFrame.addMag();
}



</script>
</head>
<body>
<z:init method="com.nswt.cms.site.CatalogSite.init">
	<div style="padding:2px">
	<table width="100%" cellpadding='0' cellspacing='0'>
		<tr>
			<td><z:tbutton onClick="addMag();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />�½��ڿ�</z:tbutton> <z:tbutton
				onClick="batchAdd();">
				<img src="../Icons/icon002a9.gif" width="20" height="20" />�����½�</z:tbutton></td>
		</tr>
	</table>
	<form id="form2">
	<table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
		<tr class="dataTableHead">
			<td width="31"></td>
			<td width="164" height="10"><b>����</b></td>
			<td><b>ֵ</b></td>
		</tr>
		<tr id="tr_ID" style="display:none">
			<td>&nbsp;</td>
			<td>ID��</td>
			<td>${ID} <input type="hidden" id="ID" value="${ID}"><input
				type="hidden" id="Name" value="${Name}"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>Ӧ�����ƣ�</td>
			<td>${Name} &nbsp;&nbsp;<a
				href="Preview.jsp?Type=0&SiteID=${ID}" target="_blank">Ԥ��</a></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>Ӣ�����ƣ�</td>
			<td>${Alias}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>վ��URL��</td>
			<td>${URL}</td>
		</tr>
	   	<tr>
			<td>&nbsp;</td>
			<td>�ڿ�������</td>
			<td>${Count}</td>
		</tr>
	
	</table>
	</form>
	</z:init>
	</div>
</body>
</html>

