<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script>
function getUserList(){
	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 400;
	diag.Title = "�û��б�";
	diag.URL = "Document/MessageUserList.jsp";
	diag.OKEvent = addUser;
	diag.show();
}

function addUser(){
	var arr = $DW.DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���û���");
		return;
	}
	var users = new Array();
	if($V("ToUser")!=""){
		users = $V("ToUser").split(",");
		for(var i=0;i<users.length;i++){
			for(var j=0;j<arr.length;j++){
				if(users[i]==arr[j]){
					arr.splice(j,1);
				}
			}
		}
	}
	users = users.concat(arr);
	$S("ToUser",users.join());
	$D.close();
}

function getRoleList(){
	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 400;
	diag.Title = "��ɫ�б�";
	diag.URL = "Document/MessageRoleList.jsp";
	diag.OKEvent = addRole;
	diag.show();
}

function addRole(){
	var arr = $DW.DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ�н�ɫ��");
		return;
	}
	var roles = new Array();
	if($V("ToRole")!=""){
		roles = $V("ToRole").split(",");
		for(var i=0;i<roles.length;i++){
			for(var j=0;j<arr.length;j++){
				if(roles[i]==arr[j]){
					arr.splice(j,1);
				}
			}
		}
	}
	roles = roles.concat(arr);
	$S("ToRole",roles.join());
    $D.close();
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body class="dialogBody">
<form id="formMsg">
<table width="100%" border="0" cellpadding="4" cellspacing=""
	bordercolor="#DEDEDC" style="border-collapse:collapse;">
	<tr>
		<td width="72" height="12" class="tdgrey2"></td>
		<td class="tdgrey2"></td>
	</tr>
	<tr>
		<td align="right" valign="top" class="tdgrey1">�����ˣ�</td>
		<td width="325" class="tdgrey2"><input name="ToUser" id="ToUser"
			type="text" size="30" verify="������|NotNull" condition="!$V('ToRole')"
			element="chooseUser" /> <input type="button" id="chooseUser"
			name="chooseUser" value="ѡ��.." onClick="getUserList()"></td>
	</tr>
	<tr>
		<td align="right" valign="top" class="tdgrey1">���ܽ�ɫ��</td>
		<td width="325" class="tdgrey2"><input name="ToRole" id="ToRole"
			type="text" size="30" verify="������|NotNull" condition="!$V('ToUser')"
			element="chooseRole" /> <input type="button" id="chooseRole"
			name="chooseRole" value="ѡ��.." onClick="getRoleList()"></td>
	</tr>
	<tr>
		<td align="right" valign="top" class="tdgrey1">���⣺</td>
		<td width="325" class="tdgrey2"><input name="Subject"
			id="Subject" type="text" size="49" verify="��Ϣ����|NotNull" /></td>
	</tr>
	<tr>
		<td align="right" valign="top" class="tdgrey1">���ݣ�</td>
		<td class="tdgrey2"><textarea id="Content" name="Content"
			cols="50" rows="5" verify="��Ϣ����|NotNull" /></textarea></td>
	</tr>
	<tr></tr>
</table>
</form>
</body>
</html>
