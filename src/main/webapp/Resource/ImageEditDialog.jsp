<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style>
.ErrorMsg {
background:#FFF2E9   scroll 5px;
border:1px solid #FF6600;
color:#000000;
padding:5px 5px 5px 25px;
}
</style>
<script type="text/javascript">
function RepeatUpload(){
	if($V("RepeatFile")){
		msg();
		$("divMsg").className="ErrorMsg";
		$F("form2").submit();
	}else{
		Dialog.alert("�������ѡ���ļ�!");
		return;
	}
}
var counter=1;
function msg(){
		  var txt = "�����ϴ������У����Ժ�...��ʱ";
			setInterval(function(){$("divMsg").innerHTML="<font color=red>"+txt+counter+"��</font>";counter++}, 1000);
}

function onUploadCompleted( returnValue,returnID,errorMessage){
	if(customPicName==null||customPicName==""){
		switch ( returnValue )
		{
			case 0 :	// No errors
				break ;
			case 1 :	// 
				Dialog.alert(errorMessage) ;
				$("divMsg").hide();
				$E.enable(window.parent.Parent.$D.CancelButton);
	    		$E.enable(window.parent.Parent.$D.OKButton);
				return ;
			case 202 :
				Dialog.alert( '��Ч���ļ����ͣ������ļ��ϴ�ʧ��:'+errorMessage ) ;
				return ;
			case 203 :
				Dialog.alert( "��û��Ȩ���ϴ����ļ����������������" ) ;
				return ;
			default :
				Dialog.alert( '�ϴ�ʧ�ܣ��������: ' + returnValue ) ;
				return ;
		}
		window.Parent.DataList.loadData("dg1");
		window.location = window.location;
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
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.resource.Image.initEditDialog">
<div style="display:none">
	<iframe name="formTarget" src="javascript:void(0)"></iframe></div>
<form id="form2" enctype="multipart/form-data"
		action="../Editor/filemanager/upload/simpleuploader.jsp" method="post"
		target="formTarget">
<input id="IDs" type="hidden" value="${IDs}"/>
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td height="120" colspan="2" align="center" valign="middle"><br>
			<img src="${Alias}${Path}s_${FileName}?${ModifyTime}"></td>
		</tr>
		<tr>
			<td height="10" align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td height="20" colspan="2" align="center" valign="middle"><br>
			����Ҫͬʱ�޸� <b style="color:#FF6600;">${IDCount}</b> ��ͼƬ����Ϣ</td>
		</tr>
		<tr>
			<td align="right">�����ϴ���</td>
			<td><input id="Repeat" name="Repeat" type="hidden" value="1" />
			<input id="RepeatID" name="RepeatID" type="hidden" value="${ID}" /> <input
				type="hidden" id="FileType" name="FileType" value="Image"> <input
				name='RepeatFile' id='RepeatFile' type='file' value='' size='30'>
			<input type="button" class="input2" onClick="RepeatUpload();"
				value="�ϴ�" />
                <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}"/>
			<div id="divMsg"></div>
			</td>
		</tr>
		<tr>
			<td align="right">ͼƬ���ƣ�</td>
			<td>
			<div align="left"><input id="Name" name="Name" type="text"
				value="${Name}" class="input1" size="30" /></div>
			</td>
		</tr>

		<tr style="display:none ">
			<td align="right">ͼƬԭ����</td>
			<td><input id="OldName" name="OldName" type="text"
				class="input1" value="${OldName}" /></td>
		</tr>
		<tr style="display:none ">
			<td align="right">ͼƬ�����ı���</td>
			<td><input id="LinkText" name="LinkText" type="text"
				class="input1" value="${LinkText}" /></td>
		</tr>
		<tr style="display:none ">
			<td align="right">ͼƬ����:</td>
			<td><input id="LinkURL" name="LinkURL" type="text"
				class="input1" value="${LinkURL}" /></td>
		</tr>
		<tr>
			<td align="right">ͼƬ������</td>
			<td><textarea id="Info" name="Info" cols="50" rows="2"
				class="input3" value="">${Info}</textarea></td>
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
