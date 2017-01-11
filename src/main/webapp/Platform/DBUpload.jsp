<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>数据导入</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function doUpload(){
	if(getUploader("DBFile").hasFile()&&!getUploader("DBFile").hasError()){
		getUploader("DBFile").doUpload();
		$E.disable(window.Parent.$D.CancelButton);
		$E.disable(window.Parent.$D.OKButton);
		return;
	}
}

function afterUpload(FileIDs,FileTypes,FilePaths,FileStatus){
	var dc = new DataCollection();
	dc.add("DBFile",FilePaths);
	Server.sendRequest("com.nswt.platform.SysInfo.uploadDB",dc,function(response){
		if(response.Status==1){
			window.location="DBUpload.jsp?TaskID="+response.Message;
		}
	});
}

</script>
</head>
<body class="dialogBody">
<form id="f1">
<table width="100%" cellpadding="2" cellspacing="0">
    <tr>
      <td height="30" colspan="2" align="center">
	    <span>
	      <%
		  if(StringUtil.isNotEmpty(request.getParameter("TaskID"))){
		  %>
			<script>
				var p = new Progress(<%=request.getParameter("TaskID")%>,"正在导入数据...",500,150);
				p.show(function(){
					$D.close();
					window.Parent.Dialog.alert("导入成功!");
					window.Parent.$D.close();
				});			
				p.Dialog.OKButton.hide();
				p.Dialog.CancelButton.hide();
				p.Dialog.CancelButton.onclick = function(){}
			</script>
		  <%}%>
	  &nbsp;</span></td>
    </tr>
    <tr>
      <td width="31%" align="right" valign="middle">己导出的数据库文件：</td>
      <td width="69%" height="30"><z:uploader id="DBFile" action="../Platform/DBUploadSave.jsp" onComplete="afterUpload" width="300" allowtype="dat" fileCount="1"/></td>
    </tr>
</table>
</form>
</body>
</html>