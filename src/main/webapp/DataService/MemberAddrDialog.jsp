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
<script src="../Framework/District.js"></script>
<script type="text/javascript">

function changeDefault(){
	if($V("IsDefault")=="Y"){
		$S("IsDefault","N");
	}else{
		$S("IsDefault","Y");
	}
}

</script>
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.dataservice.MemberAddr.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td width="492" height="10"></td>
			<td width="612"></td>
		</tr>
		<tr>
			<td height="25" align="right">�ջ��ˣ�</td>
			<td height="25">
            <input name="RealName" type="text" value="${RealName}" id="RealName" verify="NotNull" />
            <input name="UserName" id="UserName" type="hidden" value="${UserName}" />
            <input name="ID" id="ID" type="hidden" value="${ID}" />
            <input name="Country" type="hidden" value="000000" id="Country"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">ʡ�ݣ�</td>
			<td height="25">
            <z:select id="Province" onChange="changeProvince();" listHeight="300" value="${Province}" verify="NotNull"> </z:select>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">���У�</td>
			<td height="25">
            <z:select id="City" onChange="changeCity();" listHeight="300" value="${City}" verify="NotNull"> </z:select>
            </td>
		</tr>
        <tr>
			<td height="25" align="right">������</td>
			<td height="25">
            <z:select id="District" listHeight="300" value="${District}" verify="NotNull"> </z:select>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">��ϸ��ַ��</td>
			<td height="25">
            <input name="Address" type="text" value="${Address}" id="Address"/>
			</td>
		</tr>
		<tr>
			<td height="25" align="right">�ʱࣺ</td>
			<td height="25">
            <input name="ZipCode" type="text" value="${ZipCode}" id="ZipCode"/>
			</td>
		</tr>
		<tr>
			<td height="25" align="right">�̶��绰��</td>
			<td height="25">
            <input name="Tel" type="text" value="${Tel}" id="Tel"/></td>
		</tr>
        <tr>
			<td height="25" align="right">�ֻ���</td>
			<td height="25">
            <input name="Mobile" type="text" value="${Mobile}" id="Mobile"/></td>
		</tr>
		<tr>
			<td height="25" align="right">�����ʼ���</td>
			<td height="25">
            <input name="Email" type="text" value="${Email}" id="Email"/></td>
		</tr>
		<tr>
			<td height="25" align="right">Ĭ�ϵ�ַ��</td>
			<td height="25">
            <input type="checkbox" id="checkDefault" name="checkDefault" onClick="changeDefault();" ${checkDefault} />
            <input id="IsDefault" name="IsDefault" type="hidden" value="${IsDefault}"/>
            </td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
