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

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 430;
	diag.Title ="���ģ��";
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

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 760;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.onLoad = function(){
		$DW.$("SelectImage").hide();
	};
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
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.resource.AudioLib.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
		$("ImagePath").value = response.get("ImagePath");
	})
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<form id="form2"><z:init
	method="com.nswt.cms.resource.AudioLib.initDialog">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td height="6"></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">���ڵ㣺</td>
			<td colspan="2"><z:select id="ParentID" listWidth="200"
				listHeight="300" listURL="Resource/AudioLibSelectList.jsp"></z:select></td>
		</tr>
		<tr>
			<td align="right">��Ƶ�������ƣ�</td>
			<td colspan="2"><input name="Name" type="text" class="input1"
				id="Name" value="" size="30" verify="��Ƶ��������|NotNull" /></td>
		</tr>
		<tr>
			<td align="right">ר������ͼƬ��</td>
			<td align="center" width="206"><input name="ImagePath"
				value="${ImagePath}" type="hidden" id="ImagePath" size=8 /> <img
				src="${PicSrc}" name="Pic" width="120" height="90" id="Pic"></td>
			<td width="517" align="left" valign="middle"><input
				name="button" type="button" onClick="imageBrowse();" value="���" /></td>
		</tr>
        <tr>
			<td align="right" width="31%">��ҳģ��:</td>
			<td colspan="2"><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="" size="30"> <input
				type="button" class="input2" onClick="browse('IndexTemplate');"
				value="���..." /></td>
		</tr>
		<tr>
			<td align="right" width="452">�б�ҳģ��:</td>
			<td colspan="2"><input name="ListTemplate" type="text"
				class="input1" id="ListTemplate" value="" size="30"> <input
				type="button" class="input2" onClick="browse('ListTemplate');"
				value="���..." /></td>
		</tr>
		<tr>
			<td align="right">��ϸҳģ��:</td>
			<td colspan="2"><input name="DetailTemplate" type="text"
				class="input1" id="DetailTemplate" value="" size="30" /> <input
				type="button" class="input2" onClick="browse('DetailTemplate');"
				value="���..." /></td>
		</tr>
	</table>
</z:init></form>
</body>
</html>
