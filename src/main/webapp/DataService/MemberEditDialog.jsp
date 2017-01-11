<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">

function checkPWD(){
	var password=$V("NewPassword");
	var confirmPassword=$V("ConfirmPassword");
	if(password==confirmPassword){
		return true;
	} else{
		return false;
	}
}

</script>

</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.dataservice.Member.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td width="492" height="10"></td>
			<td width="612"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">��¼����</td>
			<td height="25"><b>${UserName}</b><input type="hidden" id="UserName" name="UserName" value="${UserName}"/></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">���룺</td>
			<td height="25">
            <input name="NewPassword" type="password" value="******" style="width:150px" class="inputText" id="NewPassword" verify="��������Ϊ��λ|Regex=\S{6,}&&NotNull" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">�ظ����룺</td>
			<td height="25">
            <input name="ConfirmPassword" type="password" value="******" style="width:150px" class="inputText" id="ConfirmPassword" verify="�������������ͬ|Script=checkPWD()" /></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">������</td>
			<td height="25">
            <input name="Name" type="text" value="${Name}" style="width:150px" class="inputText" id="Name" />
            </td>
		</tr>
        <tr>
			<td height="25" align="right" class="tdgrey1">�����ʼ���</td>
			<td height="25">
            <input name="Email" type="text" value="${Email}" style="width:150px" class="inputText" id="Email" verify="�����ʼ�|NotNull&&Email" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">��Ա���ͣ�</td>
			<td height="25">
            <z:select id="Type" name="Type" style="width:150px" listWidth="150"> ${Type}</z:select>
			</td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">��Ա����</td>
			<td height="25">
            <z:select id="MemberLevel" name="MemberLevel" style="width:150px" listWidth="150"> ${MemberLevel}</z:select>
			</td>
		</tr>
		<tr>
			<td height="25" align="right"><strong>�Ա�</strong></td>
			<td>${Gender}</td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">���֣�</td>
			<td height="25">
            <input name="Score" type="text" value="${Score}" style="width:150px" class="inputText" id="Score" verify="����|NotNull&&Number" /></td>
		</tr>
		<tr>
			<td height="25" align="right"><strong>���״̬��</strong></td>
			<td><z:select id="Status" name="Status" style="width:150px" listWidth="150"> ${Status}</z:select></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">�������⣺</td>
			<td height="25">
            <input name="PWQuestion" type="text" value="${PWQuestion}" style="width:150px" class="inputText" id="PWQuestion"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">����ش�</td>
			<td height="25">
            <input name="PWAnswer" type="text" value="${PWAnswer}" style="width:150px" class="inputText" id="PWAnswer" />
            </td>
		</tr>
        ${Columns}
	</table>
	</form>
</z:init>
</body>
</html>
