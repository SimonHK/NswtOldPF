<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.platform.UserList.initEditDialog">
<form id="form2">
<table width="570" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td >
   <fieldset>
    <legend><label>������Ϣ</label></legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="39%" height="30" align="right" >�û�����</td>
          <td width="61%"><input name="UserName"  type="text" value="${UserName}" id="UserName" verify="�û���|NotNull" readonly="true"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >��ʵ������</td>
          <td><input name="RealName"  type="text" value="${RealName}" id="RealName"  /></td>
        </tr>
        <tr id ="tr_Password2">
          <td height="30" align="right" >���룺</td>
          <td><input name="NewPassword" type="password" id="NewPassword" verify="����Ϊ4-20λ|Regex=\S{4,20}&&NotNull" value="${Password}" /></td>
        </tr>
        <tr id ="tr_ConfirmPassword2">
          <td height="30" align="right" >�ظ����룺</td>
          <td><input name="NewConfirmPassword" type="password" id="NewConfirmPassword" verify="�ظ�����Ϊ4-20λ|Regex=\S{4,20}&&NotNull" value="${Password}"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >����������</td>
          <td ><z:select id="BranchInnerCode"> ${BranchInnerCode} </z:select></td>
        </tr>
        <tr id ="tr_IsBranchAdmin" style="display:none">
          <td height="30" align="right" >��������Ա��</td>
          <td >${IsBranchAdmin}</td>
        </tr>
        <tr>
          <td height="30" align="right" >�Ƿ�ͣ�ã�</td>
          <td >${Status}</td>
        </tr>
        <tr>
          <td height="30" align="right" >�����ʼ���</td>
          <td><input name="Email"  type="text"  id="Email" value="${Email}" verify="�����ʼ�|Email"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >��ϵ�绰��</td>
          <td><input name="Tel"  type="text" value="${Tel}"  id="Tel" verify="��ϵ�绰|Number" size="12" />
            <br>01012345678(ʾ��)</td>
        </tr>
        <tr>
          <td height="30" align="right" >�ֻ����룺</td>
          <td><input name="Mobile"  type="text" value="${Mobile}"  id="Mobile" verify="�ֻ�����|Number&&Length=11" size="12"/>
            <br>13912345678(ʾ��)</td>
        </tr>
        <tr>
          <td height="30" align="right" >��ע��</td>
          <td><input name="Memo" type="text"  id="Memo" value="${Memo}"/></td>
        </tr>
      </table></fieldset></td>
      <td  width="240"  valign="top">
	  <fieldset>
		<legend><label>������ɫ</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr>
			<td>
	    <z:tree id="tree1" style="width:230px;height:300px" method="com.nswt.platform.UserList.initEditRoleTree">
	      <p cid='${RoleCode}' >
	        <input type="checkbox" name="RoleCode" value='${RoleCode}' ${checked}>
	        ${RoleCode}(${RoleName})</p>
	      </z:tree>
		</td>
		 </tr>
      </table></fieldset>
	  </td>
    </tr>
	</table>
</form>
</z:init>
</body>
</html>