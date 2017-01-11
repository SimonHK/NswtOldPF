<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>图片</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	Tab.getChildTab("ImageBrowse").contentWindow.$("tbSearch").hide();
});
</script>
</head>
<body>
<z:init>
	<z:tab>
		<z:childtab id="ImageBrowse"
			src="ImageBrowse.jsp?_CatalogID=${CatalogID}&Search=${Search}&Type=carsModel"
			selected="true">
			<img src="../Icons/icon030a11.gif" />
			<b>图片浏览</b>
		</z:childtab>
		<z:childtab id="ImageUpload" src="ImageUpload.jsp">
			<img src="../Icons/icon030a13.gif" />
			<b>图片上传</b>
		</z:childtab>
	</z:tab>
</z:init>
</body>
</html>
