<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ������</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

var ImagePlayerID = "<%=request.getParameter("ImagePlayerID")%>";
var imagesource = "<%=request.getParameter("ImageSource")%>";
var catalogInnerCode = "<%=request.getParameter("RelaCatalog")%>";
var disPlayCount = "<%=request.getParameter("DisPlayCount")%>";
Page.onLoad(function(){
	if (imagesource == "catalog_first") {
		tabClick("ImagePlayerPreview");
		$("ImagePlayerRela").hide();
	} else {
		if(ImagePlayerID==""||ImagePlayerID=="null"){
			tabClick("ImagePlayerBasic");
		}else{
			tabClick("ImagePlayerRela");		
		}
	}
});

function tabClick(tabid){
	Tab.onChildTabClick(tabid);
	var url = tabid+".jsp?ImagePlayerID="+ImagePlayerID;
	if (imagesource) {
		url += "&ImageSource=" + imagesource;
	}
	
	if (catalogInnerCode) {
		url += "&RelaCatalog=" + catalogInnerCode;
	}
	if(disPlayCount){
		url += "&DisPlayCount=" + disPlayCount;
	}
	Tab.getCurrentTab().src = url;
	return;
}

</script>
</head>
<body>
<z:init>
	<z:tab>
		<z:childtab src="ImagePlayerBasic.jsp?ImagePlayerID=${ImagePlayerID}&ImageSource=${ImageSource}&RelaCatalog=${RelaCatalog}&DisplayCount=${DisplayCount}" id="ImagePlayerBasic" selected="true">
			<img src="../Icons/icon039a1.gif" />
			<b>������Ϣ</b>
		</z:childtab>
		<z:childtab id="ImagePlayerRela" src="" afterClick="tabClick('ImagePlayerRela')">
			<img src="../Icons/icon039a13.gif" />
			<b>����ͼƬ</b>
		</z:childtab>
		<z:childtab id="ImagePlayerPreview" src="" afterClick="tabClick('ImagePlayerPreview')">
			<img src="../Icons/icon039a12.gif" />
			<b> Ԥ&nbsp;&nbsp;&nbsp;&nbsp;�� </b>
		</z:childtab>
	</z:tab>
</z:init>
</body>
</html>
