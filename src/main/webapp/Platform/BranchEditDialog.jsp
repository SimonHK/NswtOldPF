<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
var usertype = "";
function selectUser(type) {
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 460;
	diag.Title = "ѡ���û�";
	var selecteduser = $V(type) + "|" + $V(type+"Name");
	usertype = type;
	diag.URL = "Platform/UserListDialog.jsp?Type=radio&SelectedUser="+selecteduser;
	diag.onLoad = function(){
		$DW.updateRealName($DW.$V("UserNames"));
	};
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect() {
	var user = $DW.getSelectUser();
	$S(usertype, user[0]);
	$S(usertype+"Name", user[1]);
	$D.close();
}

function clearUser(type) {
	$S(type, "");
	$S(type+"Name", "");
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.platform.Branch.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="114" height="10"></td>
			<td width="376"><input id="BranchInnerCode" name="BranchInnerCode" type="hidden" value="${BranchInnerCode}"/></td>
		</tr>
		<tr>
			<td align="right">�� ����</td>
			<td height="30">${ParentName}</td>
		</tr>
		<tr>
			<td align="right">�������ƣ�</td>
			<td height="30"><input id="Name" name="Name" type="text"
				value="${Name}" class="input1" verify="��������|NotNull" size=36 /></td>
		</tr>
		<tr>
			<td align="right">�������룺</td>
			<td height="30"><input id="BranchCode" name="BranchCode" type="text"
				value="${BranchCode}" class="input1" size=36/></td>
		</tr>
		<tr style="display:none">
			<td align="right">�������ܣ�</td>
			<td height="30"><input type="hidden" name="Manager"id="Manager" value="${Manager}">
      		<textarea name="ManagerName" style="height:30px; width:200px;"
					id="ManagerName" readonly/>${ManagerName}</textarea>
			<a href="javascript:void(0);" onClick="selectUser('Manager');">���</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:void(0);" onClick="clearUser('Manager');">���</a></td>
		</tr>
		<tr style="display:none">
			<td align="right">�ϼ������쵼��</td>
			<td height="30"><input type="hidden" name="Leader1"id="Leader1" value="${Leader1}">
      		<textarea name="Leader1Name" style="height:30px; width:200px;"
					id="Leader1Name" readonly/>${Leader1Name}</textarea>
			<a href="javascript:void(0);" onClick="selectUser('Leader1');">���</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:void(0);" onClick="clearUser('Leader1');">���</a></td>
		</tr>
		<tr style="display:none">
			<td align="right">�ϼ��ֹ��쵼��</td>
			<td height="30"><input type="hidden" name="Leader2"id="Leader2" value="${Leader2}">
      		<textarea name="Leader2Name" style="height:30px; width:200px;"
					id="Leader2Name" readonly/>${Leader2Name}</textarea>
			<a href="javascript:void(0);" onClick="selectUser('Leader2');">���</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:void(0);" onClick="clearUser('Leader2');">���</a></td>
		</tr>
		<tr>
			<td align="right">�� ����</td>
			<td height="30"><input id="Phone" name="Phone" type="text"
				value="${Phone}" class="input1" size=36 /></td>
		</tr>
		<tr>
			<td align="right">�� �棺</td>
			<td height="30"><input id="Fax" name="Fax" type="text"
				value="${Fax}" class="input1" size=36 /></td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>