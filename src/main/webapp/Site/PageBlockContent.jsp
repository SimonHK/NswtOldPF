<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<z:init method="com.nswt.cms.site.PageBlock.init">
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<script src="../Framework/Main.js"></script>
	<script src="../Framework/Controls/Tabpage.js"></script>
	<script src="../Editor/fckeditor.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	</head>
<script>
function docListDialog(){
	var diag  = new Dialog("Diag3");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="�������";
	diag.URL = "Document/DocListDialog.jsp";
	diag.OKEvent = getDocList;
	diag.show();
}

function getDocList(){
	var dt = $DW.DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("��ѡ���ĵ���");
		return;
	}
	
	var urls ="";
	for(var i=drs.length;i>0;i--){
		var dr = drs[i-1];
		urls = urls + "<a href='"+dr.get("Link")+"'>"+dr.get("Title")+"</a><br>";
	}
	var editor = FCKeditorAPI.GetInstance('Content');
	editor.InsertHtml(urls || "" ) ;
	FCKeditorAPI.GetInstance("Content").Focus();
	$D.close();
}
	</script>
	<body>
	<div id="content">
	<form id="form2">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td><z:tbutton onClick="docListDialog()">
				<img src="../Icons/icon018a2.gif" />��������</z:tbutton></td></tr>
		<tr>
			<td><textarea name="textarea" id="_Content" style="display:none">
				  ${Content}
				  </textarea> <script type="text/javascript">
					var sBasePath = Server.ContextPath+"Editor/" ;
					var oFCKeditor = new FCKeditor( 'Content' ) ;
					oFCKeditor.BasePath	= sBasePath ;
					oFCKeditor.Width = 470 ;
					oFCKeditor.Height = 220 ;
					oFCKeditor.ToolbarSet = "Basic";
					oFCKeditor.Value = $V("_Content");
					oFCKeditor.Create() ;
				  </script></td>
		</tr>
	</table>


	</form>
	</div>
	<p>&nbsp;</p>
	</body>
</z:init>
</html>
