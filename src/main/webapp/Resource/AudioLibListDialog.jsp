<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�б�</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body onContextMenu="return false;">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td colspan="2" align="center">
		<z:tree id="tree1"
			style="height:395px"
			method="com.nswt.cms.resource.AudioLib.treeDataBind">
			<p cid='${ID}'><label><input type="checkbox" name="ID"
				value='${ID}'><span id="span_${ID}">${Name}</span></label></p>
		</z:tree>
		</td>
	</tr>
</table>
</body>
</html>
