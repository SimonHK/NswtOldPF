<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script>
</script>
</head>
<body class="dialogBody">
<form id="form2">
<table width="346" height="100" border="0" cellpadding="4" cellspacing="" bordercolor="#DEDEDC" style="border-collapse:collapse;">
 	<tr>
      <td width="21%" height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td width="21%" height="31"  align="right" valign="top">�ֶ����ƣ�</td>
      <td width="79%" valign="top"><input name="Name" id="Name" type="text" size="20" verify="�ֶ�����|NotNull" />
      </td>
    </tr>
    <tr>
      <td height="31" align="right" valign="top">�ֶδ��룺</td>
      <td valign="top"><input name="Code" id="Code" type="text" size="12"  verify="�ֶ�����|NotNull" />
      </td>
    </tr>
	<tr>
      <td height="29"  align="right" valign="top">�ֶ����ͣ�</td>
      <td valign="top" ><z:select name="Type" id="Type">
        <span value="1">int-����</span>
        <span value="2">long-������</span>
        <span value="3" selected>string-�ַ���</span>
        <span value="4">float-����</span>
        <span value="5">double-˫�ֽ�</span>
		<span value="6">date-����</span>
		<span value="7">text-�ı�</span>
      </z:select>
      </td>
    </tr>
	<tr>
      <td align="right" valign="top">���ȣ�</td>
      <td valign="top"><input name="Length" id="Length" type="text" size="12" /> 
      </td>
    </tr>
	<tr>
      <td height="29"  align="right" valign="top">������ʽ��</td>
      <td valign="top" ><z:select name="ShowMod" id="ShowMod">
        <span value="1" selected>�����ı�</span>
        <span value="2">�����ı�</span>
        <span value="3">�����б��</span>
        <span value="4">��ѡ��</span>
        <span value="5">��ѡ��</span>
        <span value="6">ý���ͼƬ��</span>
        <span value="7">������</span>
      </z:select>
      </td>
    </tr>
	<tr>
      <td   align="right" valign="top">Ĭ��ֵ��</td>
      <td valign="top" ><input name="DefaultValue" id="DefaultValue" type="text" size="20" /></td>
    </tr>
	<tr>
      <td height="83"  align="right" valign="top">�б�ѡ�<br>
        <br>
          <font color="red">(ÿ��Ϊһ<br>
      ���б���)</font></td>
      <td valign="top" ><textarea id="ListOpt" name="ListOpt" cols="43" rows="5"></textarea></td>
    </tr>
	<tr>
      <td height="33"  align="right" valign="top">�Ƿ�ǿգ�</td>
      <td valign="top" ><label for="MandatoryFlag"><input name="MandatoryFlag" type="checkbox" id="MandatoryFlag" checked />��Ϊ��</label></td>
    </tr>
</table>
<input name="ID" id="ID" type="hidden"/>
</form>
</body>
</html>