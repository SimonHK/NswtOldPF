<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<%
	String catalogID = request.getParameter("RelaCatalogID");
	String value = "0";
	String text = "APPӦ��";
	if (StringUtil.isNotEmpty(catalogID)&&!catalogID.equalsIgnoreCase("null")&&!catalogID.equals("0")) {
		value = catalogID;
		text = CatalogUtil.getName(catalogID);
	}
%>

<script>
Page.onLoad(function(){
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
});
</script>
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.dataservice.MessageBoard.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
    	<tr><td colspan="2" height="20"></td></tr>
		<tr>
			<td width="25%" height="25" align="right" >���԰�����</td>
			<td width="75%" height="25">
            <input name="Name" type="text" value="${Name}" class="inputText" id="Name" size="45" verify="���԰���|NotNull"/>
            <input type="hidden" id="ID" name="ID" value="${ID}"/>
            </td>
		</tr>
        <tr>
			<td height="25" align="right" >���԰�������</td>
			<td height="25"><textarea id="Description" name="Description" style="width:250px; height:100px;" verify="���԰�����|NotNull">${Description}</textarea></td>
		</tr>
        <tr>
			<td height="25" align="right" >������Ŀ��</td>
			<td height="25"><z:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></z:select></td>
		</tr>
        <tr>
			<td height="25" align="right" >��������ˣ�</td>
			<td height="25">${RadioIsOpen}</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
