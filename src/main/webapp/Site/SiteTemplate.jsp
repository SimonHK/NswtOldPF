<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ŀ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	
});

function browse(ele){
	var diag  = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 440;
	diag.Title ="����б�ҳģ��";
	diag.URL = "Site/TemplateExplorer.jsp?SiteID="+$V("ID");
	goBack = function(params){
		$S(ele,params);
	}
	diag.show();
}

function save(){
	var dc = Form.getData($F("form2"));
	Server.sendRequest("com.nswt.cms.site.Site.saveTemplate",dc,function(response){
		if(response.Status==0){
			alert(response.Message);
		}else{
			alert("�޸�ģ��ɹ�");
			//Tree.loadData("tree1");
			window.location.reload();
		}
	});
}
</script>
</head>
<body>
<z:init method="com.nswt.cms.site.Site.init">
	<div style="padding:2px;">
	<table width="100%" cellpadding="4" cellspacing="0">
		<tr>
			<td><z:tbutton onClick="save();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />����</z:tbutton></td>
		</tr>
	</table>
	<form id="form2">
	<table width="100%" class="dataTable">
		<tr class="dataTableHead">
			<td width="20%"><b>����</b></td>
			<td height="10"><b>ֵ</b></td>
		</tr>
		<tr id="tr_SiteID" style="display:none">
			<td>վ��ID��</td>
			<td><input name="ID" type="hidden" id="ID" value="${ID}" /></td>
		</tr>
		<tr>
			<td>��ҳģ��:</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('IndexTemplate')"></td>
		</tr>
		<tr>
			<td>�б�ҳĬ��ģ��:</td>
			<td><input name="ListTemplate" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('ListTemplate')"></td>
		</tr>
		<tr>
			<td>��ϸҳĬ��ģ��:</td>
			<td><input name="DetailTemplate" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('DetailTemplate')"></td>
		</tr>
		<tr>
			<td>Rssģ��:</td>
			<td><input name="RsslTemplate" type="text" class="input1"
				id="RsslTemplate" value="${RsslTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('RsslTemplate')"></td>
		</tr>
	</table>
	</form>
	</div>
</z:init>
</body>
</html>
