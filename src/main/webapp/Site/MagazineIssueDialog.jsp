<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script src="../Editor/fckeditor.js"></script>
<script src="../Framework/Controls/StyleToolbar.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Page.onLoad(function(){
	isEditDialog();
});

function afterSelectIcon(){
	$("Logo").src = $DW.Icon;
	$D.close();
}

function goBack(params){
	alert(params)
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;	diag.Height = 450;
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
	diag.Width = 800;	
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
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.site.MagazineIssue.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
		$S("CoverImage", response.get("CoverImage"));
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

var editor;
function getContent(){
	editor = FCKeditorAPI.GetInstance('Content');
    return editor.GetXHTML(false);	
}

//�༭�Ի���ʱʹ�ںŲ��ɸ���
function isEditDialog(){
  if(!isNaN(window.location.search.substring(4))){
    $("Year").disable();
    $("PeriodNum").disable();
  }
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<z:init method="com.nswt.cms.site.MagazineIssue.initDialog">
	<body class="dialogBody">
	<form id="form2"><input type="hidden" id="ID">

	<table width="100%" border=0 cellPadding=2 cellSpacing=3>
		<TBODY>
			<tr>
				<td align=right height="5"></td>
				<td></td>
			</tr>
			<tr>
				<td align=right>���:</td>
				<td><input name="Year" id="Year" type="text" class="input1"
					size=20 verify="���|NotNull&&Int" /></td>
			</tr>
			<tr>
				<td align=right>�ں�:</td>
				<td><input name="PeriodNum" type="text" id="PeriodNum"
					class="input1" size=20 verify="�ں�|NotNull&&Int" /></td>
			</tr>
			<tr>
				<td align=right>��������:</td>
				<td><input name="PublishDate" type="text" id="PublishDate"
					class="input1" ztype="Date" /></td>
			</tr>
			<tr>
				<td align=right>����ͼƬ:</td>
				<td align=left><input name="CoverImage" value="${CoverImage}"
					type="hidden" id="CoverImage" /> <img src="${PicSrc}" name="Pic"
					id="Pic" height="90" width="120"> <input type="button" name="Submit" value="���..."
					onClick="imageBrowse()"></td>
			</tr>
			<tr>
				<td align=right>����ҳģ��:</td>
				<td><input name="CoverTemplate" type="text" class="input1"
					id="CoverTemplate" size="30"> <input type="button"
					class="input2" onClick="browse('CoverTemplate');" value="���..." /></td>
			</tr>
			<tr>
				<td align=right>���ڼ��:</td>
				<td>
            <div  id="Toolbar" style="height:26px; width:95%"></div>
            <textarea id="_Content" name="_Content" style=" display:none;">${Memo}</textarea>
            <script type="text/javascript">
				var sBasePath = <%=Config.getContextPath()%>+"Editor/" ;
				var oFCKeditor = new FCKeditor( 'Content' ) ;
				oFCKeditor.BasePath	= sBasePath ;
				oFCKeditor.ToolbarSet="Basic"
				oFCKeditor.Width  = '95%';
				oFCKeditor.Height = 90;
				oFCKeditor.Config['EditorAreaCSS'] = '${CssPath}';
				oFCKeditor.Config[ 'ToolbarLocation' ] = 'Out:Toolbar' ;
				oFCKeditor.Value = $V("_Content");
				oFCKeditor.Create();
			</script>            
            </td>
			</tr>
			<tr>
				<td align=right>������Ŀ:</td>
				<td>${LastCatalog}</td>
			</tr>
			<tr>
			</tr>
		</TBODY>
	</table>
	</form>
	</body>
</z:init>
</html>
