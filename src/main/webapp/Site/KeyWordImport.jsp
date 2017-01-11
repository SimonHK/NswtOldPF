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
	var txt=format($V("KeyWords"));
	//���ѡ���˵����ļ����ϴ��ļ�����
	if(getUploader("WordsFile").hasFile()&&!getUploader("WordsFile").hasError()){
		getUploader("WordsFile").doUpload();
		return;
	}
	//���û�е����ļ�,����д����Ŀ,����д��Ŀ����
	if(txt==''||txt==$("KeyWords").getAttribute("oldValue")){
		Dialog.alert("��ѡ���ϴ���txt�ļ�<br/>����д�ؼ���(����ʾ������)");
		return;
	}
	doImport();
}

function doImport(){
	var KeyWords = $V("KeyWords");
	var dc = new DataCollection();
	dc.add("KeyWords",KeyWords);
	Server.sendRequest("com.nswt.cms.site.Keyword.importWords",dc,function(response){
	Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.loadWordData();
				window.Parent.loadTreeData();
				Dialog.close();
			}
		});
	});
}

function afterUpload(FileIDs,FileTypes,FilePaths,FileStatus){
	var dc = new DataCollection();
	dc.add("FilePath",FilePaths);
	Server.sendRequest("com.nswt.cms.site.Keyword.importWords",dc,function(response){
	Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.loadWordData();
				window.Parent.loadTreeData();
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
		<td width="11%" height="10" align="left">&nbsp;</td>
		<td width="89%" height="10" align="left">&nbsp;</td>
	</tr>
	<tr>
		<td width="11%" height="30" align="left">&nbsp;&nbsp;</td>
		<td width="80%" height="30" align="left">ѡ���ļ��� <z:uploader id="WordsFile" action="../Site/KeyWordImportSave.jsp" onComplete="afterUpload" width="290" allowtype="txt,xls" fileMaxSize="20971520" fileCount="1"/>&nbsp;(֧��txt��xls��ʽ)
		</td>
	</tr>
</table>
</form>
<form id="form3">
<table width="100%" align="center" cellpadding="1" cellspacing="0"	>
	<tr>
		<td width="11%" height="10" align="left"></td>
		<td valign="top"><br>
		<p><span class="style1">Ҫ��</span><br>
		<span style="margin-left:3.6em;">1.�ϴ��ļ�����Ϊ�ı��ļ���</span><br>
		<span style="margin-left:3.6em;">2.�ļ�Ҫ��ÿ��һ���ؼ�����Ŀ��</span><br>
		<span style="margin-left:3.6em;">3.��ʹ��Ӣ�ı�㣬����֮����Ӣ��"<font
			color="#FF0000">,</font>"����<font color="#FF0000">�ո�</font>������</span><br />
		<span style="margin-left:3.6em;">4.�򿪷�ʽ������ 1,2,3���棬<font
			color="#FF6600">1--ԭ���ڣ�2--�´��ڣ�3--�����ڡ�</font></span><br />
		<span style="margin-left:3.6em;">5.��������Ϊ�ȵ�ʻ���з����������������"<font
			color="#FF0000">/</font>"������</span><br>
		<span style="margin-left:3.6em;">6.������ѡ���ϴ�.txt/.xls�ļ�,��ֱ�Ӹ�������ʾ������,Ȼ����"ȷ��"�ύ��</span>
		<p><span class="style1">ʾ����</span>
		<textarea cols="60"
			style="height:150px; width:420px; vertical-align:top;" id="KeyWords"
			name="KeyWords">
���������Ƽ���Ϣ(����)���޹�˾,http://www.nswt.com/,NSWT,2,����緶
���������Ƽ���Ϣ(����)���޹�˾,http://www.nswt.com/,����,2,�Ƽ�/����/NBA
��������,http://www.nswt.com/,���˷�ʽ,2,���ھ���
����,,,3,
</textarea>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">

	init();
	<!--�趨textarea�ĳ�ʼֵ -->
	function init(){
		var obj=document.getElementById("KeyWords");
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
