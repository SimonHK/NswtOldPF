<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function setCodeOrder(value){
	$S("CodeOrder",value);
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.platform.District.initDialog">
<form id="form2">
<input name="codePK" type="hidden" id="codePK" value="${Code}">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
      <td height="10" align="center" ></td>
      <td></td>
    </tr>
    <tr>
      <td align="right" >������ţ�</td>
      <td><input name="Code" type="text" id="Code" size=20 value="${Code}" verify="�������|NotNull" onBlur="setCodeOrder(this.value);"/><font color=red> * </font></td>
    </tr>
    <tr>
      <td align="right">�������ƣ�</td>
      <td><input name="Name" type="text" id="Name" size=20 value="${Name}"/><font color=red> * </font></td>
    </tr>
    <tr>
      <td align="right" >����˳��</td>
      <td><input name="CodeOrder" type="text" id="CodeOrder" size=20 value="${CodeOrder}"/><font color=red> * </font></td>
    </tr>
    <tr>
      <td align="right" >����</td>
      <td><input name="TreeLevel" type="text" id="TreeLevel" size=20 value="${TreeLevel}"/><font color=red> * </font></td>
    </tr>
    <tr>
      <td align="right" >�������ͣ�</td>
      <td><input name="Type" type="text" id="Type" size=20 value="${Type}"/><font color=red> * </font></td>
    </tr>
</table>
</form>
</z:init>
</body>
</html>