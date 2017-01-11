<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%
  String siteID = request.getParameter("SiteID");
  if(siteID == null){
      siteID = Application.getCurrentSiteID()+"";
  }

	String alias = SiteUtil.getAlias(siteID);
	if(StringUtil.isEmpty(alias)){
	   alias = "default";
	}
	//String rootPath = Config.getContextRealPath()+Config.getValue("Statical.TemplateDir")+"/"+alias+"/template";
	String rootPath = "";
	if ("avicit".equals(alias)) {
		rootPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + alias + "/template/";
	}else{
		rootPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + alias + Config.getValue("Statical.WebRootDir") +  "/template/";
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ļ�������</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
body{overflow:auto;}
</style>
<script src="../Framework/Main.js"></script>
<script>
	function afterClick(ele){
		var fileName =  ele.getAttribute("filename");
	  var path = "/template/"+Explorer.currentPath+"/"+fileName;
	  if(!path.startWith("/")){
		  path = "/"+path;
	  }
	  
	  $S("FileType",ele.getAttribute("filetype"));
	  $S("Path",path.replace(/\/\//g,"/"));
	}
	
	function afterDbClick(ele){
		$S("FileType",ele.getAttribute("filetype"));
		if(ele.getAttribute("filetype")=="F"){
				var fileName =  ele.getAttribute("filename");
			  var path = "/template/"+Explorer.currentPath+"/"+fileName;
			  if(!path.startWith("/")){
				  path = "/"+path;
			  }
			  
			  $S("Path",path.replace(/\/\//g,"/"));
			  onOK();
		}
	}
	
	function onOK(){
		if($V("Path")==""){
			alert("��ѡ��ģ���ļ���");
			return;
		}
		
		if($V("FileType")=="D"){
			alert("����ѡ���ļ�����Ϊģ�壡");
			return;
		}
	  Parent.goBack($V("Path"));
	  Dialog.close();
	}
</script>
</head>
<base target="_self">
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
	<tr>
		<td><z:explorer id="e1" name="File.template" style="height:370px"
			baseDir="<%=rootPath%>" exclude="WEB-INF"
			column="checkbox,index,name,title,modifytime,size" page="true"
			size="500">
		</z:explorer></td>
	</tr>
</table>

<div style="display:none">
<table width="100%" border="0" cellpadding="3" cellspacing="4">
	<tr>
		<td>��ǰ·���� <input type="text" name="Path" id="Path" size="60"></td>
	</tr>
</table>
</div>
<input type="hidden" id="FileType">
</body>
</html>
