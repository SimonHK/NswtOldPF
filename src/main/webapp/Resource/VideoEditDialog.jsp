<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
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

function onReturnBack(returnID){
	onCustomReturnBack(returnID);
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


</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.resource.Video.initEditDialog">
	<form id="form2">
	<table width="400px" align="center" cellpadding="2" cellspacing="0" id="edit">
		<tr>
			<td height="5" colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<img id="AbbrImage"
						name="AbbrImage" src="..${Alias}${Path}${ImageName}?${ModifyTime}">
			</td>
		</tr>
		<tr align="center">
			<td width="26%" height="20%" align="right">��Ƶ���ƣ�</td>
			<td height="75%" align="left">
			<div align="left"><input id="Name" name="Name" type="text"
				value="${Name}" class="input1" /> <input id="ID" type="hidden"
				value="${ID}" /><input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}"/></div>
			</td>
		</tr>
		<tr>
			<td align="right">��Ƶ������</td>
			<td width="74%" align="left"><textarea id="Info" name="Info"
				cols="40" rows="2" class="input3">${Info}</textarea></td>
		</tr>
		<tr>
			<td align="right">��Ƶ��ǩ��</td>
			<td width="74%" align="left"><input id="Tag" name="Tag"
				type="text" value="${Tag}" class="input1" /></td>
		</tr>
		<tr>
			<td align="right">���֣�</td>
			<td width="74%" align="left"><input id="Integral"
				name="Integral" type="text" value="${Integral}" class="input1" /></td>
		</tr>
		<tr align="center">
			<td align="right">�Ƿ�ԭ����</td>
			<td width="74%" align="left">${IsOriginal}</td>
		</tr>
		<tr align="center">
			<td align="right">����ץͼ��</td>
			<td width="74%" align="left"><input id="StartSecond"
				name="StartSecond" type="text" value="" class="input1" size="1" />��</td>
		</tr>
        ${CustomColumn}
		<tr>
			<td align="right">����޸��ˣ�</td>
			<td>${ModifyUser}</td>
		</tr>
		<tr>
			<td align="right">����޸�ʱ�䣺</td>
			<td>${ModifyTime}</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
