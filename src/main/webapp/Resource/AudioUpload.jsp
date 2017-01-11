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
	qb = new QueryBuilder("select id,name from zccatalog where type=6 and id =?",Long.parseLong(CatalogID));
}else{
	qb = new QueryBuilder("select id,name from zccatalog where type=6 and siteid=?",Application.getCurrentSiteID());
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
		Dialog.alert("��û��ѡ�� ��Ƶ ���࣬��ѡ��");
		return;
	}
	if(getUploader("AudioFile").hasFile()&&!getUploader("AudioFile").hasError()){
		setCatalogID();
		var dc = Form.getData($F("form2"));
		getUploader("AudioFile").addPostParam(dc.toQueryString());
		getUploader("AudioFile").doUpload();
		$E.disable(window.parent.Parent.$D.CancelButton);
		$E.disable(window.parent.Parent.$D.OKButton);
	}else{
		Dialog.alert("δѡ���ļ����ļ�ѡ������");	
	}
}

/** ��ѡ����FileIDs,FileTypes,FilePaths,FileStatus */
function onUploadCompleted(FileIDs){
	if(customPicName==null||customPicName==""){
		window.parent.Parent.eval("onUploadCompleted()");
	}else{
		onCustomReturnBack(FileIDs)
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
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<z:init method="com.nswt.cms.resource.Audio.initUploadDialog">
    <form id="form2" >
    <input type="hidden" id="FileType" name="FileType" value="Audio"> 
    <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}">
	<table width="100%" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td>
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="100%" align="left"><label>��������:<z:select
						id="SelectCatalogID" listWidth="200" listHeight="300"
						listURL="Resource/AudioLibSelectList.jsp"> </z:select></label></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<fieldset><legend> <label><strong>��Ƶ�ļ��ϴ�:(Ŀǰֻ֧��${allowType}�ļ����ϴ�)</strong></label>
			</legend>
			<table width="90%" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td width="72%"><z:uploader id="AudioFile" width="500" action="../Editor/filemanager/upload/zuploader.jsp" onComplete="onUploadCompleted" uploadtype="Audio"/></td>
                    <td width="28%" align="right">��Ƶ��ǩ��<input name="FileTag" id="FileTag" type="text" style="width:100px;" value=""></td>
				</tr>
			</table>
			</fieldset>
			<%if(StringUtil.isNotEmpty(customColumn)){ %>
            <fieldset>
            <legend> <label><strong>��Ƶ�Զ�������:</strong></label></legend>
            <table width="720" align="center" cellpadding="2" cellspacing="0">
            <%=customColumn %>
            </table>
            </fieldset>
            <%} %>
			</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
