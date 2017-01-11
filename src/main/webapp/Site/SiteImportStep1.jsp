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
	if(getUploader("ExportFile").hasFile()&&!getUploader("ExportFile").hasError()){
		getUploader("ExportFile").doUpload();
		$E.disable(window.Parent.$D.CancelButton);
		$E.disable(window.Parent.$D.OKButton);
		return;
	}
}

function afterUpload(FileIDs,FileTypes,FilePaths,FileStatus){
	$E.enable(window.Parent.$D.CancelButton);
	$E.enable(window.Parent.$D.OKButton);
	window.location="SiteImportStep2.jsp?FileName="+FilePaths;
}
</script>
</head>
<body>
<z:init>
  <form id="form1">
    <table width="90%" height="80" align="center" cellpadding="2" cellspacing="0">
      <tr>
        <td height="55" align="left" valign="middle">	
		<p>选择要导入的文件：
		<z:uploader id="ExportFile" action="../Site/SiteImportSave.jsp" onComplete="afterUpload" allowtype="dat" fileCount="1"/>
		</p></td>
      </tr>
    </table>
  </form>
</z:init>
</body>
</html>
