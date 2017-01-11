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
		if(getUploader("TemplateFile").hasFile()&&!getUploader("TemplateFile").hasError()){
			var dc = Form.getData("form2");
			getUploader("TemplateFile").addPostParam(dc.toQueryString());
			getUploader("TemplateFile").doUpload();
			$E.disable($("upBtn"));
			$E.disable($("cancelBtn"));
			return;
		}
	}
	
	function afterUpload(FileIDs,FileTypes,FilePaths,FileStatus){
		$E.enable($("upBtn"));
		$E.enable($("cancelBtn"));
		var dc = Form.getData("form2");
		dc.add("FilePath",FilePaths);
		dc.add("IsZip",FileTypes);
		Server.sendRequest("com.nswt.cms.site.Template.dealUploadFile",dc,function(response){
		Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.parent.location.reload();
					Dialog.close();
				}
			});
		});
	}

</script>
</head>
<body>
<form id="form2">
<table width="80%" height="150" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td height="55" align="left">
		<p><br/>
        选择模板： 
        <z:uploader id="TemplateFile" action="../Site/TemplateImportSave.jsp" onComplete="afterUpload" allowtype="zip,html,htm" fileCount="1"/>
		<br>
		支持ZIP、HTML、HTM格式文件<input type="hidden" id="SiteID" name="SiteID" value="<%=request.getParameter("SiteID")%>" /></p>
		</td>
	</tr>
	<tr>
		<td align="center">
		<div style="text-align:left;">
		<p><font color="#FF6633">请注意，如遇同名文件，源文件将被覆盖。</font></p>
		</div>
		</td>
	</tr>
	<tr>
		<td align="center">
        <input id="upBtn" name="upBtn" type="button" class="inputButton" value=" 确 定 " onClick="doUpload();" /> &nbsp;
		<input id="cancelBtn" name="cancelBtn" type="button" class="inputButton" onClick="Dialog.close();" value=" 取 消 " />
        </td>
	</tr>
</table>
</form>
</body>
</html>
