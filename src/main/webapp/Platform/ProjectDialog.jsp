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
      <td align="right" >��Ŀ���ƣ�</td>
      <td width="60%"><input name="Name"  type="text" class="input1" id="Name" size=20 verify="��Ŀ����|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >��Ŀ��ʼʱ�䣺</td>
      <td><input name="BeginTime" ztype="date" type="text" class="input1" id="BeginTime" size=20 verify="��Ŀ��ʼʱ��|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >��Ŀ����ʱ�䣺</td>
      <td><input name="EndTime" ztype="date" type="text" class="input1" id="EndTime" size=20 verify="��Ŀ����ʱ��|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >��ע��</td>
      <td><input name="Prop1" type="text"  class="input1" id="Prop1" size=20/></td>
    </tr>
</table>
</form>
</body>
</html>