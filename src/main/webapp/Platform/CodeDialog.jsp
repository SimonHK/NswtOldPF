<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
      <td width="40%" height="10" align="right" ></td>
      <td height="10"></td>
    </tr>
    <tr>
      <td align="right" >�������</td>
      <td width="60%"><input name="CodeType"  type="text" class="input1" id="CodeType" size=20 verify="�������|NotNull"/></td>
    </tr>
    <tr id ="tr_CodeValue" style="display:">
      <td align="right" >����ֵ��</td>
      <td><input name="CodeValue"  type="text" class="input1" id="CodeValue" size=20 verify="����ֵ|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >�������ƣ�</td>
      <td><input name="CodeName"  type="text" class="input1" id="CodeName" size=20 verify="��������|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >��ע��</td>
      <td><input name="Memo" type="text"  class="input1" id="Memo" size=20/></td>
    </tr>
</table>
</form>
</body>
</html>