<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%
	String CatalogID = request.getParameter("CatalogID");
	QueryBuilder qb = null;
	if(CatalogID!=null&&!"".equals(CatalogID)&&!"null".equals(CatalogID)){
		qb = new QueryBuilder("select id,name from zccatalog where type=7 and id =?",Long.parseLong(CatalogID));
	}else{
		qb = new QueryBuilder("select id,name from zccatalog where type=7 and siteid=? order by id",Application.getCurrentSiteID());
	}
	DataTable dt = qb.executePagedDataTable(1,0);
	String value = "";
	String text = "";
	String customColumn = "";
	if (dt != null && dt.getRowCount() > 0) {
		value = dt.getString(0,0);
		text = dt.getString(0,1);
		customColumn = ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, dt.getString(0,"ID"));
	}
%>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	Selector.setValueEx("SelectCatalogID",'<%=value%>','<%=text%>');
});

function setCatalogID(){
	$S("CatalogID",$V("SelectCatalogID"));
}

function upload(){
	var sid = $V("SelectCatalogID");
	if(sid==null){
		Dialog.alert("��û��ѡ�� ���� ���࣬��ѡ��");
		return;
	}
	if(getUploader("AttachFile").hasFile()&&!getUploader("AttachFile").hasError()){
		setCatalogID();
		var dc = Form.getData("form2");
		getUploader("AttachFile").addPostParam(dc.toQueryString());
		getUploader("AttachFile").doUpload();
		$E.disable(window.parent.Parent.$D.CancelButton);
		$E.disable(window.parent.Parent.$D.OKButton);
	}else{
		Dialog.alert("δѡ���ļ����ļ�ѡ������");	
	}
}

function doPreview(sender){   
    var flag = 0;
    for(var i=0; i<exts.length; i++) {
	  	if(sender.value.toLowerCase().trim().endsWith("." + exts[i].toLowerCase())) {
			flag = 1;
	  	}
	}
    if(flag == 0) {
	    Dialog.alert('��֧�ִ˸�ʽ��');   
        return false;   
	}
}


/** ��ѡ����FileIDs,FileTypes,FilePaths,FileStatus */
function onUploadCompleted(FileIDs){
	if(customPicName==null||customPicName==""){
		window.parent.Parent.onFileUploadCompleted(FileIDs);
	}else{
		onCustomReturnBack(FileIDs);
	}
}

function browse(){
	var diag = new Dialog("Diag2");
	diag.Width = 300;
	diag.Height = 400;
	diag.Title = "���������б�";
	diag.URL = "Resource/AttachmentLibListDialog.jsp";
	diag.OKEvent = browseSave;
	diag.show();
}


function browseSave(){
	var arr = $DW.$NV("ID");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ�и������࣡");
		return;
	}
	var OtherLibTable = $("OtherLibTable");
	var sb = [];
	var catalogid=$V("SelectCatalogID");
	for(var i=0;i<arr.length;i++){
		if(arr[i]==catalogid){
			continue;
		}
		sb.push("<tr><td><input type='hidden' name='OtherID' value='"+arr[i]+"'>"+$DW.$("span_"+arr[i]).innerHTML+"</td><td><img src='../Framework/Images/icon_cancel.gif' title='ɾ��' onclick='deleteOtherID(this);' /></td></tr>");	
	}
	OtherLibTable.outerHTML="<table width='100%' border='1' cellspacing='0' id='OtherLibTable' bordercolor='#eeeeee'>"+sb.join('')+"</table>";
	$D.close();
}

function deleteOtherID(ele){
	ele.parentElement.parentElement.parentElement.deleteRow(ele.parentElement.parentElement.rowIndex);
	return false;
}


function imageBrowse(){
	customPicName = "";
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.onLoad = function(){
		$DW.$("SelectImage").hide();
	};
	diag.OKEvent = OK;
	diag.show();
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

function showImage(){
  $("tr").show();
  $("link1").hide();
  $("link2").show();
}

function hideImage(){
 $("tr").hide();
 $("link1").show();
 $("link2").hide();
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

</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<z:init method="com.nswt.cms.resource.Attachment.init">
<body>
<form id="form2" >
<input type="hidden" id="FileType" name="FileType" value="Attach">
<input type="hidden" id="CatalogID" name="CatalogID"
	value="${CatalogID}">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
			<td  valign="top">
			<fieldset><legend> <strong>����:</strong></legend>
			<table id="Videotable0" style="display:" width="100%" cellpadding="2"
				cellspacing="0">
				<tr>
					<td width="16%" align="right">�ϴ�˵����</td>
					<td width="84%" colspan="2">�����С ${fileMaxSizeHuman} <br>֧�ָ�ʽ ${allowType}���ļ�</td>
				</tr>
				<tr>
					<td align="right">���������</td>
					<td colspan="2">
                    <z:uploader id="AttachFile" action="../Editor/filemanager/upload/zuploader.jsp" onComplete="onUploadCompleted" allowtype="${allowType}" fileMaxSize="${fileMaxSize}" fileCount="1"/>
						<a id="link1" style="display:" href="javascript:showImage()">��ʾ����ͼ</a>
						<a id="link2" style="display:none" href="javascript:hideImage()">��������ͼ</a>
						</td>
				</tr>
				<tr style="display:none" id="tr">
					<td align="right">��������ͼ��</td>
					<td align="left" width="37%" valign="middle">
                    <input name="ImagePath" value="${ImagePath}" type="hidden" id="ImagePath" size=8 /> 
                    <img src="${PicSrc}" name="Pic" width="120" height="90" id="Pic">
                    <div style="padding-left:30px;">
                    <input name="button" type="button" onClick="imageBrowse();" value="���..." />
                    </div></td>
				</tr>
				<tr>
					<td align="right">�������ƣ�</td>
					<td><input id="FileName" name="FileName" type="text"
						class="input1" value="" /></td>
				</tr>
                <tr>
					<td align="right">����������</td>
					<td><input id="Info" name="Info" type="text" class="input1" value="" /></td>
				</tr>
                <%=customColumn %>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr >
			<td  valign="top">
			<fieldset><legend> <label><strong>��������</strong></label></legend>
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="58%" align="left">
					<table width="100%" cellpadding="2" cellspacing="0">
						<tr>
							<td width="21%">����������:</td>
							<td colspan="2"><z:select id="SelectCatalogID"
								listWidth="200" listHeight="300"
								listURL="Resource/AttachmentLibSelectList.jsp"> </z:select></td>
						</tr>
						<tr>
							<td valign="top">������������:</td>
							<td width="21%">
							<div style="overflow:auto;height:70px;width:122px;">
							<table width="100%" border="1" cellspacing="0" id="OtherLibTable" bordercolor="#eeeeee">
								<tr>
									<td width="55%">&nbsp;</td>
									<td width="45%">&nbsp;</td>
								</tr>
								<tr>
									<td width="55%">&nbsp;</td>
									<td width="45%">&nbsp;</td>
								</tr>
								<tr>
									<td width="55%">&nbsp;</td>
									<td width="45%">&nbsp;</td>
								</tr>
							</table>
							</div>
							</td>
							<td width="58%" valign="top"><input type="button"
								class="input2" onClick="browse();" value="ѡ��" /></td>
						</tr>
					</table>
					<div id="divMsg" align="center"></div>
					</td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
</table>
</form>
<div id="divMsg" align="center"></div>
</body>
</z:init>
</html>
