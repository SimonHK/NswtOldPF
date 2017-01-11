<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function uploadFile(){
	var diag = new Dialog("Diag2");
	diag.Width = 760;
	diag.Height = 400;
	diag.Title = "文件浏览/上传";
	diag.URL = "Resource/AttachmentLibDialog.jsp?Search=Search&SelectType=radio&CatalogID=34";
	diag.onLoad = function(){
		$DW.$("AttachmentBrowse").hide();
	};
	diag.OKEvent = FileOK;	
	diag.show();
}

function FileOK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("AttachmentUpload")){
 		currentTab.contentWindow.upload();
	} else if (currentTab==$DW.Tab.getChildTab("AttachmentBrowse")) {
	 	currentTab.contentWindow.onFileReturnBack();
	}
}

function onFileUploadCompleted(returnID){
	onFileReturnBack(returnID);
}

function onFileReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("FileID",returnID);
	Server.sendRequest("com.nswt.project.avicit.CommEnter.getFilePath",dc,function(response){
		$S("CommWord", response.get("CommWord"));
		$S("CommWordUrl", response.get("CommWordUrl"));
		$D.close();
	});
}
</script>
</head>
<body class="dialogBody">
<z:init method="com.nswt.project.avicit.CommEnterPrise.init">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td width="492" height="10"></td>
			<td width="612"></td>
		</tr>
		<tr>
			<td height="25" align="right">企业全名：</td>
			<td>
			<input name="FullName" type="text" value="${FullName}" id="FullName"/>
			<input name="ID" type="hidden" value="${ID}" id="ID"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">企业简称：</td>
			<td>
			<input name="ShortName" type="text" value="${ShortName}" id="ShortName"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">企业网址：</td>
			<td>
			<input name="EnterpriseUrl" type="text" value="${EnterpriseUrl}" id="EnterpriseUrl"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">法人代表：</td>
			<td>
			<input name="LegalPerson" type="text" value="${LegalPerson}" id="LegalPerson"/>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">省市：</td>
			<td height="25">
            <z:select id="Place" listHeight="300" verify="NotNull">${Place}</z:select>
            </td>
		</tr>
		<tr>
			<td height="25" align="right">所属行业：</td>
			<td height="25">
            <z:select id="EnterpriseType"  verify="NotNull">${EnterpriseType}</z:select>
            </td>
		</tr>
		
		<tr>
			<td height="25" align="right">承诺书：</td>
			<td>
			<input type="text" name="CommWord" ID="CommWord" value="${CommWord}" readonly><a href="#;" onClick="uploadFile()">上传承诺书</a>
			<input type="hidden" name="CommWordUrl" ID="CommWordUrl" value="${CommWordUrl}">
            </td>
		</tr>
		<tr>
			<td height="30" align="right" class="tdgrey1">承诺时间：</td>
			<td class="tdgrey1">
				<span class="tdgrey2"> 
				<input name="CommDate" type="text" class="inputText" id="CommDate"
					ztype="Date" size=14 verify="NotNull" value="${CommDate}" /> 
				</span>
			</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
