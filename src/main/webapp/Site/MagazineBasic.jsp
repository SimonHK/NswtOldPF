<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>��Ŀ</title>
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
function addIssue(){
	topFrame.addIssue();	
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

function delMag(){
	topFrame.delMag();
}

function editMag(){
	topFrame.editMag($V("ID"));
}



</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<z:init method="com.nswt.cms.site.Magazine.init">
	<div style="padding:2px">
	<table width="100%" cellpadding='0' cellspacing='0'>
		<tr>
			<td><z:tbutton onClick="addIssue();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />�½��ں�</z:tbutton> <z:tbutton
				onClick="publish();">
				<img src="../Icons/icon002a1.gif" />���������ڿ�</z:tbutton> <z:tbutton
				onClick="editMag();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />�༭</z:tbutton> <z:tbutton
				onClick="delMag();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />ɾ��</z:tbutton></td>
		</tr>
	</table>
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
		<tr class="dataTableHead">
			<td width="31"></td>
			<td width="100" height="10"><b>����</b></td>
			<td><b>ֵ</b></td>
		</tr>
		<tr id="tr_ID" >
			<td>&nbsp;</td>
			<td>ID��</td>
			<td>${ID} <input type="hidden" id="ID" value="${ID}"><input
				type="hidden" id="Name" value="${Name}"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>�ڿ����ƣ�</td>
			<td>${Name} &nbsp;&nbsp;<a
				href="Preview.jsp?Type=1&CatalogID=${ID}" target="_blank">Ԥ��</a></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>�ڿ�Ŀ¼����</td>
			<td>${Alias}</td>
		</tr>
		<tr>
			<!--td>&nbsp;</td>
      <td  align="right" >����ͼƬ��</td>
      <td><img src="./${CoverImage}" align="absmiddle"/>&nbsp;</td>
    </tr-->
		<tr>
			<td>&nbsp;</td>
			<td>����ģ�壺</td>
			<td>${CoverTemplate}&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>��ǰ�ںţ�</td>
			<td>${CurrentYear}���${CurrentPeriodNum}��</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>��������</td>
			<td>${Total}&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>�Ƿ񿪷ţ�</td>
			<td>${OpenFlag}&nbsp;</td>
		</tr>
	</table>
	</form>
	</div>
</z:init>
</body>
</html>
