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
<form id="form2">
<table cellpadding="2" cellspacing="0">
    <tr>
      <td width="116" height="10" align="right" ></td>
      <td></td>
    </tr>
    <tr>
      <td align="right" >�û�����</td>
      <td width="194"><input name="UserName"  type="text" value="" class="input1" id="UserName" readonly=true verify="�û���|NotNull" /></td>
    </tr>
	<tr id ="tr_Password">
      <td align="right" >���룺</td>
      <td>
	  <input name="Password" type="password" class="input1" id="Password" verify="�û�����|NotNull" /></td>
    </tr>
    <tr id ="tr_ConfirmPassword">
      <td align="right" >�ظ����룺</td>
      <td>
	  <input name="ConfirmPassword" type="password" class="input1" id="ConfirmPassword" verify="�ظ�����|NotNull" /></td>
    </tr>
    <tr>
      <td height="10" colspan="2" align="center"  type="Data" field="type"></td>
    </tr>
</table>
</form>
</body>
</html>