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
<script>
	function doUpload(){
		if(getUploader("File").hasFile()&&!getUploader("File").hasError()){
			var dc = Form.getData("form2");
			getUploader("File").addPostParam(dc.toQueryString());
			getUploader("File").doUpload();
			$E.disable($("upBtn"));
			$E.disable($("cancelBtn"));
			return;
		}
	}
	
	function afterUpload(FileIDs,FileTypes,FilePaths,FileStatus){
		$E.enable($("upBtn"));
		$E.enable($("cancelBtn"));
		Dialog.alert("上传完成",function(){
				window.parent.location.reload();
				Dialog.close();
		});
	}
</script>
</head>
<body>
<form id="form2">
<table width="80%" height="150" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td height="55" align="left">
        <br/>
		<p>选择文件： 
        <z:uploader id="File" action="../Site/FileImportSave.jsp" onComplete="afterUpload" allowtype="zip,html,htm,css,js,jpg,gif" fileCount="1"/>
        </p>
		</td>
	</tr>
    	<tr>
		<td align="center">
		<div style="text-align:left;">
		<p><font color="#FF6633">支持html、htm、css、js、jpg、gif、zip等格式</font></p>
		</div>
		</td>
	</tr>
	<tr>
		<td align="center">
        <input type="hidden" id="SiteID" name="SiteID" value="<%=request.getParameter("SiteID")%>" />
        <input type="hidden" id="Path" name="Path" value="<%=request.getParameter("Path")%>" />
        <input id="upBtn" name="upBtn" type="button" class="inputButton" value=" 确 定 " onClick="doUpload();" /> &nbsp;
		<input id="cancelBtn" name="cancelBtn" type="button" class="inputButton" onClick="Dialog.close();" value=" 取 消 " />
        </td>
	</tr>
</table>
</form>
</body>
</html>
