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
	//如果选择了导入文件按上传文件导入
	if(getUploader("WordsFile").hasFile()&&!getUploader("WordsFile").hasError()){
		getUploader("WordsFile").doUpload();
		return;
	}
	//如果没有导入文件,且填写了栏目,按填写栏目导入
	if(txt==''||txt==$("KeyWords").getAttribute("oldValue")){
		Dialog.alert("请选择上传的txt文件<br/>或填写关键词(更改示例内容)");
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
		<td width="80%" height="30" align="left">选择文件： <z:uploader id="WordsFile" action="../Site/KeyWordImportSave.jsp" onComplete="afterUpload" width="290" allowtype="txt,xls" fileMaxSize="20971520" fileCount="1"/>&nbsp;(支持txt、xls格式)
		</td>
	</tr>
</table>
</form>
<form id="form3">
<table width="100%" align="center" cellpadding="1" cellspacing="0"	>
	<tr>
		<td width="11%" height="10" align="left"></td>
		<td valign="top"><br>
		<p><span class="style1">要求：</span><br>
		<span style="margin-left:3.6em;">1.上传文件必须为文本文件；</span><br>
		<span style="margin-left:3.6em;">2.文件要求每行一个关键词条目；</span><br>
		<span style="margin-left:3.6em;">3.请使用英文标点，参数之间用英文"<font
			color="#FF0000">,</font>"或者<font color="#FF0000">空格</font>隔开。</span><br />
		<span style="margin-left:3.6em;">4.打开方式由数字 1,2,3代替，<font
			color="#FF6600">1--原窗口；2--新窗口；3--父窗口。</font></span><br />
		<span style="margin-left:3.6em;">5.所属分类为热点词汇库中分类名，多个分类用"<font
			color="#FF0000">/</font>"隔开；</span><br>
		<span style="margin-left:3.6em;">6.您可以选择上传.txt/.xls文件,或直接更改以下示例内容,然后点击"确定"提交。</span>
		<p><span class="style1">示例：</span>
		<textarea cols="60"
			style="height:150px; width:420px; vertical-align:top;" id="KeyWords"
			name="KeyWords">
新宇联安科技信息(北京)有限公司,http://www.nswt.com/,NSWT,2,名企风范
新宇联安科技信息(北京)有限公司,http://www.nswt.com/,博雅,2,科技/娱乐/NBA
合作加盟,http://www.nswt.com/,加盟方式,2,金融经济
新闻,,,3,
</textarea>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">

	init();
	<!--设定textarea的初始值 -->
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
	//格式化,去掉左边空格,右边空格,空字符(换行符除外)
	function format(str){
		str=trim(str);
		str=str.replace(/[\t\r\f]/g,'');
		str=str.replace(/[ \t\r\f]*\n[ \t\r\f]*\n+/g,'\n');
		return str;
	}
</script>
</body>
</html>
