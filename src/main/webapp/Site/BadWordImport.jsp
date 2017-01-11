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
<script type="text/javascript">
function importWords(){
	var txt=format($V("BadWords"));
	//���ѡ���˵����ļ����ϴ��ļ�����
	if(getUploader("WordsFile").hasFile()&&!getUploader("WordsFile").hasError()){
		getUploader("WordsFile").doUpload();
		return;
	}
	//���û�е����ļ�,����д����Ŀ,����д��Ŀ����
	if(txt==''||txt==$("BadWords").getAttribute("oldValue")){
		Dialog.alert("��ѡ���ϴ���txt�ļ�<br/>����д���д�(����ʾ������)");
		return;
	}
	doImport();
}

function doImport(){
	var BadWords = $V("BadWords");
	var dc = new DataCollection();
	dc.add("BadWords",BadWords);
	Server.sendRequest("com.nswt.cms.site.BadWord.importWords",dc,function(response){
	Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.loadWordData();
				Dialog.close();
			}
		});
	});
}

function afterUpload(FileIDs,FileTypes,FilePaths,FileStatus){
	var dc = new DataCollection();
	dc.add("FilePath",FilePaths);
	Server.sendRequest("com.nswt.cms.site.BadWord.importWords",dc,function(response){
	Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.loadWordData();
				Dialog.close();
			}
		});
	});
}
</script>
<style type="text/css">
<!--
.style1 {color: #445566; font-weight:bold;}
-->
</style>
</head>
<body class="dialogBody">
    <form id="form2">
<table width="100%" align="center" cellpadding="1" cellspacing="0"	>
	<tr>
		<td width="11%" height="30" align="left">&nbsp;</td>
		<td width="89%" height="30" align="left">&nbsp;</td>
	</tr>
	<tr>
		<td width="11%" height="30" align="left">&nbsp;&nbsp;</td>
		<td width="89%" height="30" align="left">ѡ���ļ��� <z:uploader id="WordsFile" action="../Site/BadWordImportSave.jsp" onComplete="afterUpload" width="290" allowtype="txt" fileMaxSize="20971520" fileCount="1"/>
		</td>
	</tr>
</table>
	</form>
    <form id="form3">
<table width="100%" align="center" cellpadding="1" cellspacing="0"	> 
	<tr>
		<td width="11%" align="center"></td>
		<td width="89%" valign="top"><br>
		<p><span class="style1">Ҫ��</span><br>
		<span style="margin-left:3.6em;">1.�ϴ��ļ�����Ϊ�ı��ļ���</span><br>
		<span style="margin-left:3.6em;">2.�ļ�Ҫ��ÿ��һ�����д���Ŀ��</span><br>
		<span style="margin-left:3.6em;">3.��ʹ��Ӣ�ı�㣬����֮����Ӣ��"<font
			color="#FF0000">,</font>"����<font color="#FF0000">�ո�</font>������</span><br />
		<span style="margin-left:3.6em;">4.���м��������� 1,2,3���棬<font
			color="#FF6600">1--һ�㣻2--�Ƚ����У�3--�ر����С�</font></span><br />
		<span style="margin-left:3.6em;">5.������ѡ���ϴ�.txt�ļ�,���������ʾ����ֱ�Ӹ�������,Ȼ����"ȷ��"�ύ��</span>
		<p><span class="style1">ʾ����</span>
		<textarea cols="50"
			style="height:150px; vertical-align:top;" id="BadWords"
			name="BadWords">
�ҿ�,�ҵ���,3
TMD,***,1
��,̫��,2
</textarea>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">

	init();
	<!--�趨textarea�ĳ�ʼֵ -->
	function init(){
		var obj=document.getElementById("BadWords");
		var str=obj.value;
		obj.setAttribute('oldValue',format(str));
	}
	
	function trim(str){
		str=str.replace(/^\s*/g,'');
		str=str.replace(/\s*$/g,'');
		return str;
	}
	//��ʽ��,ȥ����߿ո�,�ұ߿ո�,���ַ�(���з�����)
	function format(str){
		str=trim(str);
		str=str.replace(/[\t\r\f]/g,'');
		str=str.replace(/[ \t\r\f]*\n[ \t\r\f]*\n+/g,'\n');
		return str;
	}
</script>
</body>
</html>
