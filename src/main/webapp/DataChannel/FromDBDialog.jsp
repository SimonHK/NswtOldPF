<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body >
<z:init>
<z:tab>
	<z:childtab id ="Info" src="FromDBInfo.jsp?ID=${ID}" selected="true"><img src="../Icons/icon002a1.gif" width="20" height="20"/><b>������Ϣ</b></z:childtab>
	<z:childtab id ="Column" src="FromDBMapping.jsp?ID=${ID}"><img src="../Icons/icon018a7.gif" width="20" height="20"/><b>�ֶ�ӳ��</b></z:childtab>
</z:tab>
</z:init>
</body>
</html>
