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
	diag.Width = 700;	diag.Height = 450;
	diag.Title ="����б�ҳģ��";
diag.URL = "Site/TemplateExplorer.jsp?SiteID="+$V("SiteID");
	goBack = function(params){
		$S(ele,params);
	}
	diag.show();
}

function save(){
	var dc = Form.getData($F("form2"));
	Server.sendRequest("com.nswt.cms.site.Catalog.saveTemplate",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==0){
				$D.close();
				window.location.reload();
			}
		});
	});
}
</script>
</head>
<body>
<z:init method="com.nswt.cms.site.Catalog.init">
	<div style="padding:2px;">
	<table width="100%" cellpadding="4" cellspacing="0">
		<tr>
			<td><z:tbutton onClick="save();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />����</z:tbutton></td>
		</tr>
	</table>
	<form id="form2">
	<table width="100%" border="0" cellpadding="2" cellspacing="0"
		class="dataTable">
		<tr class="dataTableHead">
			<td width="20%"><b>����</b></td>
			<td height="10"><b>ֵ</b></td>
		</tr>
		<tr id="tr_SiteID" style="display:none">
			<td>վ��ID��</td>
			<td><input name="SiteID" type="hidden" id="SiteID"
				value="${SiteID}" /> <input name="ID" type="hidden" id="ID"
				value="${ID}" /></td>
		</tr>
		<tr>
			<td>��Ŀ���ƣ�</td>
			<td>${Name} <input type="hidden" id="Name" value="${Name}"></td>
		</tr>
		<tr style="display:${IsDisplay}">
			<td>��ҳģ��:</td>
			<td><input name="SiteID" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('IndexTemplate')"></td>
		</tr>
		<tr style="display:${IsDisplay}">
			<td>�б�ҳģ��:</td>
			<td><input name="SiteID" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('ListTemplate')"></td>
		</tr>

		<tr>
			<td>��ϸҳģ��:</td>
			<td><input name="SiteID" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('DetailTemplate')"></td>
		</tr>
		<tr>
			<td>Rssģ��:</td>
			<td><input name="SiteID" type="text" class="input1"
				id="RssTemplate" value="${RssTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('RssTemplate')"></td>
		</tr>
		<tr>
			<td>ģ������:</td>
			<td><z:select id="Extend" style="width:150px;"> ${Extend} </z:select>
			</td>
		</tr>
	</table>
	</form>
	</div>
</z:init>
</body>
</html>
