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
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
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
	$S("CoverImage",returnID);
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.site.MagazineIssue.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
	});
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<form id="form2"><input type="hidden" id="ID">
<table width="100%" border=0 cellpadding=2 cellspacing=0>
	<tbody>
		<tr>
			<td align=right height="5"></td>
			<td></td>
		</tr>
		<tr>
			<td align=right>��:</td>
			<td><input name="Year" id="Year" type="text" class="input1"
				size=20 verify="��|NotNull&&Int" /></td>
		</tr>
		<tr>
			<td align=right>�ں�:</td>
			<td><input name="PeriodNum" type="text" id="PeriodNum"
				class="input1" size=20 verify="�ں�|NotNull" /></td>
		</tr>
		<tr>
			<td align=right>��������:</td>
			<td><input name="PublishDate" type="text" id="PublishDate"
				class="input1" ztype="Date" verify="��������|NotNull" /></td>
		</tr>
		<tr>
			<td align=right>����ͼƬ:</td>
			<td><input name="CoverImage" value="${CoverImage}"
				type="hidden" id="CoverImage" /> <img src="${PicSrc}" name="Pic"
				width="100" height="75" id="Pic"> <input type="button"
				name="Submit" value="���..." onClick="imageBrowse()"></td>
		</tr>
		<tr>
			<td align=right>����ҳģ��:</td>
			<td><input name="CoverTemplate" type="text" class="input1"
				id="CoverTemplate" size="30"> <input type="button"
				class="input2" onClick="browse('ListTemplate');" value="���..." /></td>
		</tr>
		<tr>
			<td align=right>���ڼ��:</td>
			<td><textarea name=Memo cols=50 rows=5 id="Memo"></textarea></td>
		</tr>
		<tr>
		</tr>
	</tbody>
</table>
</form>
</body>
</html>
