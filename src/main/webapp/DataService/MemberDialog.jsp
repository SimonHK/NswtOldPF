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
//�������ĵ�¼���Ƿ�����
function checkName(){
	var UserName=$V("UserName");
	var valid=/^[a-zA-Z0-9_]{4,}$/;
	if(!valid.test(UserName)){
		$("nameCheck").innerHTML="";
		return;
	}
	var dc=new DataCollection();
	dc.add("UserName",UserName);
	Server.sendRequest("com.nswt.cms.dataservice.Member.checkName",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			$("nameCheck").innerHTML="<font color='red'>���ڵĵ�¼��,�����</font>";
			$S("UserName","");
		}
	});
}

function checkPWD(){
	var password=$V("Password");
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
<z:init method="com.nswt.cms.dataservice.Member.initAddDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td width="492" height="10"></td>
			<td width="612"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">��¼����</td>
			<td height="25">
            <input name="UserName" type="text" value="" style="width:150px" class="inputText" id="UserName" onBlur="checkName()" verify="��¼������Ϊ4λ��ֻ�ܰ�����ĸ�����ֺ��»���|Regex=[a-zA-Z0-9_]{4,}&&NotNull" />
			<span id="nameCheck"></span></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">���룺</td>
			<td height="25">
            <input name="Password" type="password" value="" style="width:150px" class="inputText" id="Password" verify="��������Ϊ��λ|Regex=\S{5,}&&NotNull" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">�ظ����룺</td>
			<td height="25">
            <input name="ConfirmPassword" type="password" value="" style="width:150px" class="inputText" id="ConfirmPassword" verify="�������������ͬ|Script=checkPWD()" /></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">������</td>
			<td height="25">
            <input name="Name" type="text" value="" style="width:150px" class="inputText" id="Name" />
            </td>
		</tr>
        <tr>
			<td height="25" align="right" class="tdgrey1">�����ʼ���</td>
			<td height="25">
            <input name="Email" type="text" value="" style="width:150px" class="inputText" id="Email" verify="�����ʼ�|NotNull&&Email" />
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
            <input name="Score" type="text" value="" style="width:150px" class="inputText" id="Score" verify="����|NotNull&&Number" /></td>
		</tr>
		<tr>
			<td height="25" align="right"><strong>���״̬��</strong></td>
			<td><z:select id="Status" name="Status" style="width:150px" listWidth="150"> ${Status}</z:select></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">�������⣺</td>
			<td height="25">
            <input name="PWQuestion" type="text" value="" style="width:150px" class="inputText" id="PWQuestion"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">����ش�</td>
			<td height="25">
            <input name="PWAnswer" type="text" value="" style="width:150px" class="inputText" id="PWAnswer"/>
            </td>
		</tr>
        ${Columns}
	</table>
	</form>
</z:init>
</body>
</html>
