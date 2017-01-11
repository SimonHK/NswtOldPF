<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script>
function goBack(params){
	alert(params);
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;	
	diag.Height = 450;
	diag.Title ="����б�ҳģ��";
	diag.URL = "Site/TemplateExplorer.jsp";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function setAlias(){
	if($V("Alias") == ""){
	  	$S("Alias",getSpell($V("Name"),true));
  	}
}

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = ok;
	diag.show();
}

function ok(){
	if ($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")) {
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	} else if ($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")) {
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	$S("CoverImage",returnID);
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.site.NewspaperIssue.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
	});
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function browseImage(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = onImageSelected;
	diag.show();
}

function onImageSelected(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}
	else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}
function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.site.NewspaperIssue.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
	})
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<z:init method="com.nswt.cms.site.Newspaper.initDialog">
	<body class="dialogBody">
	<form id="form2"><input name="NewspaperID" type="hidden"
		id="NewspaperID" value="${ID}" /> <input name="Type" type="hidden"
		id="Type" value="8" /> <input name="SiteID" type="hidden" id="SiteID"
		value="${SiteID}" /> <input name="ChildCount" type="hidden"
		id="ChildCount" value="${ChildCount}" />
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td align="right">���ƣ�</td>
			<td align="left"><input name="Name" type="text" class="input1"
				id="Name" value="${Name}" size="30" onChange="setAlias();"
				onBlur="setAlias();" verify="����|NotNull" /></td>
		</tr>
		<tr>
			<td align="right">Ӣ�����ƣ�</td>
			<td align="left"><input name="Alias" type="text" class="input1"
				id="Alias" value="${Alias}" size="30" verify="Ӣ����|NotNull" /></td>
		</tr>
		<tr>
			<td align="right">��������</td>
			<td align="left">
			<z:select id="Period" style="width:100px;"> ${optionPeriod}</z:select>
			</td>
		</tr>
		<tr>
			<td align="right">����:</td>
			<td align="left"><input name="CoverImage" value="${CoverImage}"
				type="hidden" id="CoverImage" /> <img src="${PicSrc}" name="Pic"
				width="100" height="75" id="Pic"> <input type="button"
				name="submitbutton" value="���..." onClick="browseImage()"></td>
		</tr>
		<tr>
			<td align="right" width="31%">����ҳģ��:</td>
			<td align="left"><input name="CoverTemplate" type="text"
				class="input1" id="CoverTemplate" value="${CoverTemplate}" size="30">
			<input type="button" class="input2"
				onClick="browse('CoverTemplate');" value="���..." /></td>
		</tr>
		<tr>
			<td align="right">�Ƿ񿪷�:</td>
			<td align="left"><span class="tdgrey2"> <input
				type="radio" name="UseFlag" value="0" id="UseFlag0"> <label
				for="UseFlag0">ͣ��</label> <input type="radio" name="UseFlag"
				value="1" id="UseFlag1" checked> <label for="UseFlag1">����</label>
			</span></td>
		</tr>
	</table>
	</form>
	</body>
</z:init>
</html>
