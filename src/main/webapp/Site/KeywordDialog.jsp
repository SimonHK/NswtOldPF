<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<z:init method="com.nswt.cms.site.Keyword.initDialog">
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
      <table width="100%" border="0" cellpadding="0" cellspacing="0" height="200">
        <tr>
          <td width="39%" height="30" align="right" >�ؼ��֣�</td>
          <td width="61%"><input name="Keyword" type="text" value="${Keyword}" style="width:100px" class="input1" id="Keyword" verify="�ؼ���|NotNull" size=15 /> <input name="ID" type="hidden" id="ID" /></td>
        </tr>
        <tr>
          <td height="30" align="right" >���ӵ�ַ��</td>
          <td><input name="LinkURL" type="text" value="${LinkUrl}" class="input1" id="LinkURL" size=36 /></td>
        </tr>
        <tr id ="tr_Password2">
          <td height="30" align="right" >������ʾ��</td>
          <td><input name="LinkAlt" type="text" value="${LinkAlt}" class="input1" id="LinkAlt" size="36" /></td>
        </tr>
        <tr id ="tr_ConfirmPassword2">
          <td height="30" align="right" >�򿪷�ʽ��</td>
          <td><z:select name="LinkTarget" id="LinkTarget" value="${LinkTarget}"><span value="_self">ԭ����</span> <span value="_blank">�´���</span> <span value="_parent">������</span></z:select></td>
        </tr>
      </table></fieldset></td>
      <td  width="240"  valign="top">
	  <fieldset>
		<legend><label>��������</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0" height="200">
			<tr>
			<td>
	    <z:tree id="tree1" style="width:230px;height:200px" method="com.nswt.cms.site.KeywordType.loadTypeTree">
	      <p cid='${ID}' >
	        <input type="checkbox" name="keywordType" value='${ID}' ${checked}>
	        ${TypeName}</p>
	      </z:tree>
	      <input type="hidden" id="ID" value="${ID}" />
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