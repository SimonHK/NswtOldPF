<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>添加图片播放器样式</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function doUpload(){
	if(getUploader("ZipFile").hasFile()&&!getUploader("ZipFile").hasError()){
		var dc = Form.getData("form2");
		getUploader("ZipFile").addPostParam(dc.toQueryString());
		getUploader("ZipFile").doUpload();
		$E.disable(window.Parent.$D.CancelButton);
		$E.disable(window.Parent.$D.OKButton);
		$E.disable($("upBtn"));
	}else{
		Dialog.alert("未选择文件或文件选择有误");	
	}	
}

function saveStyle(){
	if(Verify.hasError()){
		return;
	}
	if($V("ImagePlayerStyleID")==""){
		Dialog.alert("请先上传样式相关文件，然后保存");
		return;
	}
	var dc = Form.getData("form2");
	dc.add("IsDefault",$V("IsDefault"));
	Server.sendRequest("com.nswt.cms.site.ImagePlayerStyles.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.DataGrid.loadData('dg1');
				window.Parent.$D.close();
			}
		});
	});
}

function onUploadCompleted(FileIDs,FileTypes,FilePaths,FileStatus){
	$S("ImagePlayerStyleID",FileIDs);
	$S("FilePath",FilePaths);
	$E.enable(window.Parent.$D.CancelButton);
	$E.enable(window.Parent.$D.OKButton);
}

function changeDefault(){
	if($("Default").checked==true){
		$S("IsDefault","Y");
	}else{
		$S("IsDefault","N");
	}
}

Page.onLoad(function(){
	if($V("IsDefault")=="Y"){
		$("Default").checked=true;
	}
});
</script>
</head>
<body>
<div style="display:none;"><iframe src="javascript:void(0);" name="targetFrame" width="0" height="0" frameborder="0"></iframe></div>
<z:init method="com.nswt.cms.site.ImagePlayerStyles.init">
<form id="form2">
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    	 <tr>
    		<td>&nbsp;</td>
            <td><input type="hidden" id="IsDefault" name="IsDefault" value="${IsDefault}" />
            <input name="ImagePlayerStyleID" type="hidden" id="ImagePlayerStyleID" value="${ImagePlayerStyleID}" />
            <input name="FilePath" type="hidden" id="FilePath" value="${FilePath}" /></td>
        </tr>
    	<tr>
    		<td width="26%" align="right">播放器样式名称：</td>
            <td width="74%"><input name="Name" type="text" value="${Name}" style="width:200px" class="input1" id="Name" verify="播放器样式名称|NotNull"/>
            <label for="Default"><input type="checkbox" id="Default" name="Default" onClick="changeDefault();"/>默认样式</label>
            </td>
        </tr>
        <tr>
    		<td align="right">样式相关文件：</td>
            <td><z:uploader id="ZipFile" action="${AppPath}Site/ImagePlayerStyleSave.jsp" onComplete="onUploadCompleted" width="250" allowtype="zip" fileMaxSize="${fileMaxSize}" fileCount="1"/>&nbsp;<span style="vertical-align:top"><input type="button" id="upBtn" name="upBtn" value="上传" onClick="doUpload()"/></span></td>
        </tr>
        <tr>
    		<td align="right">备注：</td>
            <td><textarea id="Memo" name="Memo" cols="50">${Memo}</textarea></td>
        </tr>
	</table>
	</form>
</z:init>
</body>
</html>