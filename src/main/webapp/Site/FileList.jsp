<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%
	String rootPath = Config.getContextRealPath()+Config.getValue("Statical.TemplateDir")+"/"+Application.getCurrentSiteAlias();
%>
<title>�ļ�������</title>

<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
body{ _overflow:hidden;}
</style>
<script src="../Framework/Main.js"></script>
<script>

var explorerType = "";
function add(){
   addFile();	
}

function addDir(){
	addDirectory();
}

function up(){
   Explorer.goParent();	
}

function editFile(){
	if(Explorer.lastRowClickEle){
		var param = [Explorer.lastRowClickEle.getAttribute("filename")];
	 	edit(param,"");

	}else{
		alert("�뵥��ѡ���ļ�");
	}
}

function renameFile(){
	 if(Explorer.lastRowClickEle){
	 	rename(Explorer.lastRowClickEle.getAttribute("filename"));
	 }else{
		alert("�뵥��ѡ���ļ�");
	 }
}

function delFile(){
	if(Explorer.lastRowClickEle){
		del(Explorer.lastRowClickEle.getAttribute("filename"));
	}else{
		alert("�뵥��ѡ���ļ�");
	}
}

function comparePackage(){
	compPackage();
}

function importFile(){
	var currentPath = Explorer.baseDir+"/"+Explorer.currentPath;
	var diag = new Dialog("Diag1");
	diag.Width = 320;
	diag.Height = 160;
	diag.Title = "�ϴ��ļ�";
	diag.URL =  "Site/FileImportDialog.jsp?SiteID="+$V("SiteID")+"&Path="+currentPath;
	diag.ShowButtonRow = false;
	diag.show();
}


function exportFiles(){
	exportFile();
}

</script>
</head>
<body>
<input type="hidden" id="SiteID"
	value="<%=Application.getCurrentSiteID()%>">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon018a1.gif" /> �ļ��б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 0;"><z:tbutton onClick="add()">
					<img src="../Icons/icon018a2.gif" />�½��ļ�</z:tbutton> <z:tbutton
					onClick="addDir()">
					<img src="../Icons/icon018a2.gif" />�½�Ŀ¼</z:tbutton> <z:tbutton
					onClick="editFile()">
					<img src="../Icons/icon018a2.gif" />�༭</z:tbutton> <z:tbutton
					onClick="renameFile()">
					<img src="../Icons/icon018a2.gif" />������</z:tbutton><z:tbutton
						onClick="comparePackage()">
					<img src="../Icons/icon028a1.gif" />������</z:tbutton> <z:tbutton
					onClick="delFile()">
					<img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton> <z:tbutton onClick="up()">
					<img src="../Icons/icon400a4.gif" />�ϼ�</z:tbutton> <z:tbutton
					onClick="importFile()">
					<img src="../Icons/icon026a7.gif" />�ϴ�</z:tbutton> <z:tbutton
					onClick="exportFiles()">
					<img src="../Icons/icon026a13.gif" />ȫ������</z:tbutton> <z:tbutton
					onClick="deploy()">
					<img src="../Icons/icon018a2.gif" />�ַ�</z:tbutton></td>
			</tr>
			<tr>
				<td><z:explorer id="e1" style="height:400px;"
					name="File.template" baseDir="<%=rootPath%>" exclude="WEB-INF"
					column="checkbox,index,name,title,modifytime,size" page="true"
					size="500">
				</z:explorer></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
