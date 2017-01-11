<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">

function selectIcon(){
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "选择图标";
	diag.URL = "Platform/Icon.jsp";
	diag.onLoad = function(){
	};
	diag.show();
}

function afterSelectIcon(){
	$("LogoFileName").src = $DW.Icon;
	$D.close();
}

function setAlias(){
	if($V("Alias") == ""){
	  $S("Alias",getSpell($V("Name"),true));
  }
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览列表页模板";
	diag.URL = "Site/TemplateExplorer.jsp?SiteID="+$V("ID")+"&Alias=default";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function browseFile(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览文件";
	diag.URL = "Site/FileExplorer.jsp?SiteID="+$V("ID")+"&Alias=default";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.site.Site.initDialog">
	<form id="form2" method="post">
	<table width="740" cellpadding="2" cellspacing="0">
		<tr>
			<td width="135"></td>
			<td width="263" height="10"></td>
			<td width="328"></td>
		</tr>
		<tr>
			<td align="right">应用/业务名称:</td>
			<td><input name="Name" type="text" class="input1" id="Name"
				onBlur="setAlias();" onChange="setAlias();" value="${Name}"
				size="40" verify="应用名称|NotNull" />
                <input name="ID" type="hidden"
				id="ID" value="${ID}" /></td>
			<td class="gray">应用在后台显示的名称</td>
		</tr>
		<tr>
			<td align="right">工程名称:</td>
			<td><input name="Alias" type="text" class="input1" id="Alias"
				value="${Alias}" size="40"
				verify="英文名称|NotNull&&只允许大小写字母、数字、英文句号、下划线及英文破折线|Regex=^[\w\.\_\-]+$" /></td>
			<td><span class="gray">存放应用文件的目录名称及Maven项目中为artifactId，建议只使用数字和字母</span></td>
		</tr>
		<tr>
			<td align="right">结构包名称:</td>
			<td><input name="PackageName" type="text" class="input1"
					   id="PackageName" value="${AppVersion}" size="40" verify="包名称|NotNull"/></td>
			<td><span class="gray">结构包名称</span></td>
		</tr>
		<tr>
			<td align="right">版本号:</td>
			<td><input name="appversion" type="text" class="input1"
					   id="AppVersion" value="${AppVersion}" size="40" verify="版本号|NotNull"/></td>
			<td><span class="gray">应用版本号</span></td>
		</tr>
		<tr>
			<td align="right">应用描述:</td>
			<td><input name="Info" type="text" class="input1" id="Info"
				value="${Info}" size="40" /></td>
			<td><span class="gray">应用的其他备注信息</span></td>
		</tr>
		<tr>
			<td align="right">地址域名：</td>
			<td><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40" verify="URL|NotNull" /></td>
			<td><span class="gray">浏览者访问应用使用的URL</span></td>
		</tr>
		<tr>
			<td align="right">构建模板：</td>
			<td><input name="AppTemplate" type="text" class="input1"
					   id="AppTemplate" value="${AppTemplate}" size="30" />
				<input
						type="button" onClick="browse('AppTemplate');"
						value="浏览..." /></td>
			<td><span class="gray">应用构建模板列表</span></td>
		</tr>
		<tr>
			<td align="right">源码地址：</td>
			<td><input name="scmurl" type="text" class="input1"
					   id="ScmUrl" value="${ScmUrl}" size="40" /></td>
			<td><span class="gray">SVN或GIT源代码版本管理地址</span></td>
		</tr>
<%--		<tr>
			<td align="right">首页模板：</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" />
                <input
				type="button" onClick="browse('IndexTemplate');"
				value="浏览..." /></td>
			<td><span class="gray">应用首页面使用的模板文件</span></td>
		</tr>--%>
		<tr>
			<td align="right">功能模板：</td>
			<td><input name="ListTemplate" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30" />
                <input
				type="button" onClick="browse('ListTemplate');"
				value="浏览..." /></td>
			<td><span class="gray">应用下业务功能页默认使用的模板</span></td>
		</tr>
<%--		<tr>
			<td align="right">文档模板：</td>
			<td><input name="DetailTemplate" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30" />
                <input
				type="button" onClick="browse('DetailTemplate');"
				value="浏览..." /></td>
			<td><span class="gray">应用下文知识库及档详细页查看使用的模板文件</span></td>
		</tr>
		<tr>
			<td align="right">编辑器样式：</td>
			<td><input id="EditorCss" name="EditorCss" type="text"
				class="input1" value="${EditorCss}" size="30" />
                <input
				type="button" onClick="browseFile('EditorCss');"
				value="浏览..." />              </td>
		  <td class="gray">选择的样式文件将会被文章编辑器引入，使文章编辑时的效果更接近最后发布运行的效果</td>
		</tr>--%>
		<tr>
          <td height="35" align="right">生成页面扩展名：</td>
		  <td align="left"><input name="Prop1" type="text" class="input1"
						id="Prop1" value="${Prop1}" size="40"/></td>
		  <td align="left" class="gray">生成页面的扩展名，允许shtml,jsp，默认shtml</td>
	  </tr>
	<%--	<tr>
		  <td height="35" align="right">动态应用头部引用：</td>
		  <td><input type="text" class="input1" id="HeaderTemplate" value="${HeaderTemplate}" size="30" />
              <input name="button2"	type="button" onClick="browse('HeaderTemplate');" value="浏览..." />            </td>
		  <td class="gray">指定的模板文件的输出将会被放置到动态应用的&lt;head&gt;与&lt;/head&gt;之间</td>
	  </tr>
		<tr>
		  <td height="35" align="right">动态应用顶部模板：</td>
		  <td><input name="TopTemplate" type="text" class="input1" id="TopTemplate" value="${TopTemplate}" size="30" />
              <input name="button" type="button" onClick="browse('TopTemplate');" value="浏览..." />            </td>
		  <td class="gray">指定的模板文件的输出将会被放置到动态应用的&lt;body&gt;之下</td>
	  </tr>
		<tr>
		  <td height="35" align="right">动态应用底部模板：</td>
		  <td><input name="BottomTemplate" type="text" class="input1" id="BottomTemplate" value="${BottomTemplate}" size="30" />
              <input name="button2"	type="button" onClick="browse('BottomTemplate');" value="浏览..." />            </td>
		  <td class="gray">指定的模板文件的输出将会被放置到动态应用的&lt;/body&gt;之上</td>
	  </tr>--%>
	</table>
	</form>
</z:init>
</body>
</html>
