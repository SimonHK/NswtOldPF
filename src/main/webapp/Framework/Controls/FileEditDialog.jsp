<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%
long siteID = Application.getCurrentSiteID();
String path = "";
path = Config.getContextRealPath()+Config.getValue("Statical.TargetDir")+"/"+SiteUtil.getAlias(siteID)+"/";
if(!path.endsWith("/")){
	path = path+"/";
}
String fileName = request.getParameter("fileName");
path = path+request.getParameter("fileName");
path = FileUtil.normalizePath(path);

String ext = path.substring(path.lastIndexOf(".")+1).toLowerCase();
if(!"html".equals(ext)&& !"htm".equals(ext) && !"shtml".equals(ext) && !"txt".equals(ext) && !"js".equals(ext)&& !"xml".equals(ext)&&!"css".equals(ext)&& !"jsp".equals(ext)&& !"java".equals(ext)&& !"json".equals(ext)){
  out.println("<script>alert('���ܱ༭�������ļ�');window.close();</script>");
  return;
}


//String FileContent = FileUtil.readText(path);
String FileContent = FileUtil.readTextAuto(path);
FileContent = StringUtil.replaceEx(FileContent, "&", "&amp;");

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ļ��༭---<%=request.getParameter("fileName")%></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="../../Tools/Swfobject.js"></script>
<script>
	var alias ='<%=request.getParameter("alias")%>';
	var path ='<%=path%>';
	function save(){
		var dc = new DataCollection();
		var content = $('SourceEditor').getText().replace(' ',' ');
		dc.add("Content",htmlEncode(content));
		dc.add("FileName",path);
		Server.sendRequest("com.nswt.cms.site.FileList.edit",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					$S('Source',content);
				}
			});
		});
	}
	
	Page.onLoad(function(){
		$("SourceEditor").height = (document.compatMode == "BackCompat"?document.body.clientHeight:document.documentElement.clientHeight)-30;
	});
	
	function closeX(){
		/*
		var oldContent = $V('Source').replace(/\r\n/gi,'\n').trim();
		if($('SourceEditor').getText().trim()!=oldContent){
			if(confirm("�����Ѿ��޸ģ���û�б��棬ȷ�Ϲر��𣿵����ȷ�ϡ��رգ������ȡ�������ء�")){
				window.close();
			}
		}else{
		*/
			window.close();
		//}
	}
</script>

<style>
body{ text-align:left; font-size:12px;color:#666666; margin:0px; background:#F6F9FD;}
</style>
</head>
<body scroll="no">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><z:tbutton onClick="save()">
			<img src="../../Icons/icon018a2.gif" />����</z:tbutton> <z:tbutton
			onClick="closeX()">
			<img src="../../Icons/icon403a11.gif" />�ر�</z:tbutton></td>
	</tr>
</table>
<div id="FlashDiv"></div>
<script type="text/javascript">
var so = new SWFObject("../../Framework/Controls/ScriptEditor.swf", "SourceEditor", "100%", "400", "9", "#ffffff");
so.addVariable("Language","html");
so.addVariable("AfterInit","setContent");
so.addParam("wmode", "Opaque");
so.write("FlashDiv");

function setContent(){
	$('SourceEditor').setText(htmlDecode($V('Source').replace(/\r\n/gi,'\n')));
}
</script>
<textarea style="display:none" id="Source"><%=StringUtil.htmlEncode(FileContent)%></textarea>
</body>
</html>
