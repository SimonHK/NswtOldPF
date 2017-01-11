<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%
	//�����DocList.LastCatalog���ض���CatalogBasic.jsp
	CatalogSite.onRequestBegin(request, response);
%>
<z:init method="com.nswt.cms.site.CatalogSite.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>��Ŀ</title>
<script src="../Framework/Main.js"></script>
<script> 
//�������ǰҳ��ʱ�������Ҽ��˵�
var iFrame =parent.parent;

Page.onLoad(function(){
		Application.setAllPriv("site",Application.CurrentSite);
});

Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}
});

var topFrame = window.parent;
function add(){
	topFrame.add();	
}

function publish(){
	topFrame.publish();	
}

function publishIndex(){
	if(!$V("IndexTemplate")){
		Dialog.alert("��ҳģ�岻��Ϊ��");
		return;
	}
	topFrame.publishIndex();	
}

function del(){
	topFrame.del();
}

function batchAdd(){
	topFrame.batchAdd();
}

function batchDelete(){
	topFrame.batchDelete();
}

function addMagazine(){
}

function openEditor(path){
	topFrame.openEditor(path);
}

function preview(){
	topFrame.preview();
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="ѡ����ҳģ��";
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

function changeIndexTemplate(){
	var indexTemplate=$V('IndexTemplate');
	if(!indexTemplate){
		Dialog.alert("��ѡ��ģ��!");
		return;
	}
	var dc=new DataCollection();
	dc.add("IndexTemplate",indexTemplate);
	Server.sendRequest("com.nswt.cms.site.CatalogSite.changeTemplate",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			$('editLink').style.display="block";
			$('editLink').onclick=function(){openEditor(indexTemplate);}
		}
	});
}

function preview(){
	topFrame.preview();
}

function exportStructure(){
	var diag = new Dialog("Diag4");
	diag.Width = 500;
	diag.Height = 350;
	diag.Title ="������Ŀ�ṹ";
	diag.URL = "Site/CatalogExportStructure.jsp?Type=${Type}&ID=";
	diag.ShowMessageRow = true;
	diag.Message = "�������ı��ɸ��ƺ���ʹ�á��������롱���ܵ��������վ�����Ŀ�¡�";
	diag.show();
	diag.OKButton.hide();
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
	<div style="padding:2px">
	<table width="100%" cellpadding='4' cellspacing='0'>
		<tr>
			<td><z:tbutton id="BtnAdd" priv="site_manage"
				onClick="add();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />�½���Ŀ</z:tbutton> <z:tbutton
				id="BtnBatchAdd" priv="site_manage" onClick="batchAdd();">
				<img src="../Icons/icon002a9.gif" width="20" height="20" />�����½�</z:tbutton>
				<z:tbutton id="BtnBatchAdd" priv="site_manage" onClick="batchDelete();">
					<img src="../Icons/icon002a3.gif" width="20" height="20" />����ɾ��</z:tbutton> 
				<z:tbutton id="BtnExportStructure" priv="article_manage" onClick="exportStructure();">
					<img src="../Icons/icon002a11.gif" width="20" height="20" />������Ŀ�ṹ</z:tbutton>
				<z:tbutton
				id="BtnPublish" priv="site_manage" onClick="publish();">
				<img src="../Icons/icon002a1.gif" />����ȫվ</z:tbutton> <z:tbutton
				id="BtnPublishIndex" priv="site_manage" onClick="publishIndex();">
				<img src="../Icons/icon002a1.gif" />������ҳ</z:tbutton> <z:tbutton id="BtnPublish"
				priv="site_browse" onClick="preview();">
				<img src="../Icons/icon403a3.gif" />Ԥ��</z:tbutton></td>
		</tr>
	</table>
	<form id="form2">
	<table width="600" border="1" cellpadding="3" cellspacing="0"
		bordercolor="#eeeeee">
		<tr>
		  <td>վ��ID��</td>
		  <td>${ID}</td>
	    </tr>
		<tr>
			<td>Ӧ�����ƣ�</td>
		  <td>${Name} &nbsp;
		    <input
				type="hidden" id="ID" value="${ID}"> <input type="hidden"
				id="Name" value="${Name}">
	      <input name="BtnPreview" type="button" id="BtnPreview" value="Ԥ��" onclick="preview();"></td>
		</tr>
		<tr>
			<td>վ��Ŀ¼����</td>
			<td>${Alias}</td>
		</tr>
		<tr>
			<td>վ��URL��</td>
			<td>${URL}</td>
		</tr>
		<!--tr>
	  <td>&nbsp;</td>
      <td  align="right" >վ��Logo��</td>
      <td><img src="./${LogoFileName}" align="absmiddle"/>&nbsp;</td>
    </tr-->
		<tr>
			<td>��ҳģ�壺</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��ģ��"
				onClick="browse('IndexTemplate')"> <input type="button"
				name="Submit" value="����" onClick="changeIndexTemplate()">&nbsp;&nbsp;${edit}			</td>
		</tr>
		<tr>
			<td>�༭����ʽ��</td>
			<td>${EditorCss}&nbsp;</td>
		</tr>
		<tr>
			<td>������</td>
			<td>${Info}&nbsp;</td>
		</tr>
		<!--tr>
	  <td>&nbsp;</td>
      <td  align="right" >Ƶ������</td>
      <td>${ChannelCount}</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td  align="right" >�ڿ�����</td>
      <td>${MagzineCount}</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td  align="right" >ר������</td>
      <td>${SpecialCount}</td>
    </tr>
    <tr!-->
	</table>
	</form>
	</div>
</body>
</html>
</z:init>
