<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<z:init>
	<z:tab>
    	<z:childtab id="SelectImage" selected="true" src="SelectImage.jsp?CatalogID=${CatalogID}&Search=${Search}&SelectType=${SelectType}&RelaImageIDs=${RelaImageIDs}&ImageIDs=${ImageIDs}">
			<img src="../Icons/icon030a12.gif" />
			<b>����ͼƬ</b>
		</z:childtab>
		<z:childtab id="ImageBrowse" src="ImageBrowse.jsp?_CatalogID=${CatalogID}&Search=${Search}&SelectType=${SelectType}">
			<img src="../Icons/icon030a11.gif" />
			<b>ͼƬ���</b>
		</z:childtab>
		<z:childtab id="ImageUpload" src="ImageUploadCover.jsp">
			<img src="../Icons/icon030a13.gif" />
			<b>�ϴ�ͼƬ</b>
		</z:childtab>
	</z:tab>
</z:init>
</body>
</html>
