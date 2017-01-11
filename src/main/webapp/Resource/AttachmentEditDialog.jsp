<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function goBack(params){
	alert(params)
}

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = ok;
	diag.show();
}

function ok(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}
	else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	if(customPicName==null||customPicName==""){
		var dc = new DataCollection();
		dc.add("PicID",returnID);
		Server.sendRequest("com.nswt.cms.resource.AttachmentLib.getPicSrc",dc,function(response){
			$("Pic").src = response.get("picSrc");
			$("ImagePath").value = response.get("ImagePath");
		})
	}else{
		onCustomReturnBack(returnID);
	}
}

/***ͼƬ�ϴ�**/
var customPicName; //�Զ���ͼƬ���ϴ�
function custom_img_upload(colsName){
	customPicName = colsName;
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag_custom");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "ͼƬ���/�ϴ�";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = OK;
	diag.show();
}

function OK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("ImageUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("ImageBrowse")){
	 	currentTab.contentWindow.onReturnBack();
	}
}

function onCustomReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	dc.add("Custom","1");
	dc.add("CatalogID", $V("CatalogID"));
	Server.sendRequest("com.nswt.cms.document.Article.getPicSrc",dc,function(response){
		$S(customPicName,response.get("s_picSrc"));
		$("Img"+customPicName).src = response.get("s_picSrc");
		$(customPicName).focus();
		customPicName = "";
	});
}

function onUploadCompleted(FileIDs){
	onCustomReturnBack(FileIDs);
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
	<body class="dialogBody">
	<z:init method="com.nswt.cms.resource.Attachment.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="31%" height="10"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">�������ƣ�</td>
			<td>
            <input name="Name" type="text" class="input1" id="Name" value="${Name}" verify="��������|NotNull" />
            <input type="hidden" id="ID" name="ID" value="${ID}"/>
            <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}"/>
            </td>
		</tr>
		<tr>
			<td align="right">����������</td>
			<td><input name="Info" type="text" class="input1" id="Info" value="${Info}" /></td>
		</tr>
        <tr>
			<td align="right">��С��</td>
			<td>${FileSize}<span style="padding-left:20px;">��ʽ��${Suffix}</span></td>
		</tr>
        <tr>
			<td align="right">�ϴ�ʱ�䣺</td>
			<td>${AddTime}</td>
		</tr>
        <tr>
			<td align="right">�ϴ��ߣ�</td>
			<td>${AddUser}</td>
		</tr>
        ${CustomColumn}
		<tr id="IndexImg">
			<td align="right">��������ͼƬ��</td>
			<td align="left" width="69%">
            <img src="${PicSrc}" name="Pic" width="120" height="90" id="Pic">
            <input name="button" type="button" onClick="imageBrowse();" value="���..."/>
            <input name="ImagePath" value="${ImagePath}" type="hidden" id="ImagePath"/>
            </td>
		</tr>
        <tr>
        	<td>&nbsp;</td>
            <td><a href="${AttachmentLink}" target="_blank" title="���ش���:${Prop1}">���ش˸���</a></td>
        </tr>
		<!--
		<tr>
			<td align="right" width="31%">�б�ҳģ��:</td>
			<td colspan="2"><input name="ListTemplate" type="text"
				class="input1" id="ListTemplate" value="${ListTemplate}" size="30">
			<input type="button" class="input2" onClick="browse('ListTemplate');"
				value="���..." /></td>
		</tr>
		<tr>
			<td align="right">ͼƬ��ϸҳģ��:</td>
			<td colspan="2"><input name="DetailTemplate" type="text"
				class="input1" id="DetailTemplate" value="${DetailTemplate}"
				size="30" /> <input type="button" class="input2"
				onClick="browse('DetailTemplate')" value="���..." /></td>
		</tr>
		  -->
	</table>
	</form>
	</z:init>
	</body>
</html>