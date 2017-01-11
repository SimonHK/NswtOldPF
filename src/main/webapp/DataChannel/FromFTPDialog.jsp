<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.nswt.datachannel.FromFTP"%>
<html>
<z:init method="com.nswt.datachannel.FromFTP.init">
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script src="../Framework/Main.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<script type="text/javascript">
function onTypeClick(){
	if($NV("Type")=="D"){
		$("trCatalog").show();
		$("trCatalogOptions").show();
		parent.$("Template").show();
	}else{
		$("trCatalogOptions").hide();
		$("trCatalog").hide();
		parent.$("Template").hide();
	}
}

function onRedirectURLUniteClick(){
	if($V("RedirectURLRadio")=="Y"){
		$("RedirectUrl").enable();
		$S("Content","");
		$("Content").disable();
	}else{
		$("RedirectUrl").disable();
		
	}
}

function onContentUniteClick(){
	var arr = $N("Mapping");
	if($V("ContentRadio")=="Y"){
		$("Content").enable();
		$S("RedirectUrl","http://");
		$("RedirectUrl").disable();
		if(!arr||arr.length==0){
			return;
		}
		var options = arr[0].options;
		var index = 0;
		for(var i=0;i<options.length;i++){
			if(options[i].innerText == "文章内容"){
				index = i;
				break;
			}
		}
		if(index!=0){
			contentRemoveFlag = true;
			for(var i=0;i<arr.length;i++){
				if($V(arr[i])=="Content"){
					arr[i].selectedIndex = 0;
				}
				arr[i].remove(index);
			}
		}
	}else{
		$("ContentUniteRule").disable();
		if(contentRemoveFlag){
			contentRemoveFlag = false;
			for(var i=0;arr&&i<arr.length;i++){
				arr[i].add("文章内容","Content");
			}
		}
	}
}

Page.onLoad(function(){

	Selector.setValueEx("CatalogID",'${CatalogID}','${CatalogName}');
	$S("ContentRadio","${ContentRadio}");
	$S("Content","${Content}");
	$S("RedirectURLRadio","${RedirectURLRadio}");
	$S("RedirectUrl","${RedirectUrl}");
	onRedirectURLUniteClick();
	onContentUniteClick();
});

</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="123" cellpadding="3" cellspacing="0">
		<tr>
			<td colspan="2" height="10"></td>
		</tr>
		<tr>
			<td width="750" valign="top"
				style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style=" background:none;">
				<tr>
					<td align="right" width="100">任务名称：</td>
					<td><input name="Name" type="text" value="${Name}" id="Name"
						verify="NotNull" size="20" /></td>
				</tr>
				<tr>
					<td height="24" align="right" width="100">采集到此：</td>
					<td><z:select id="CatalogID" listWidth="300" verify="NotNull"
						condition="$NV('Type')=='D'" listHeight="300"
						listURL="Site/CatalogSelectList.jsp"></z:select></td>
				</tr>
				<tr id="tr_Password2">
					<td align="right" width="100">采集文件类型：</td>
					<td><input name="GatherType" type="text" value="${GatherType}"
						id="GatherType" verify="NotNull" size="20" /> <a href="#"
						class="tip" onMouseOut="Tip.close(this)"
						onMouseOver="Tip.show(this,'按照指定的文件类型采集文件,可以同时指定多个类型.如:txt,rmvb,....多种采集采集类型之间使用英文的逗号隔开');"><img
						src="../Framework/Images/icon_tip.gif" width="13" height="16"></a>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">FTP主机地址：</td>
					<td><input type="hidden" id="ID" value="${ID}" /> <input
						name="FtpHost" type="text" value="${FtpHost}" id="FtpHost"
						verify="NotNull" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">FTP主机端口号：</td>
					<td><input name="FtpPort" type="text" value="${FtpPort}"
						id="FtpPort" verify="NotNull&&Int" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">采集目录：</td>
					<td><input name="GatherDrectory" value="${GatherDrectory}"
						type="text" id="GatherDrectory" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">用户名：</td>
					<td><input name="FtpUserName" type="text"
						value="${FtpUserName}" id="FtpUserName"
						verify="用户名最多20位，仅限英文字母，数字，汉字，半角“_”、“.”、“@”|Regex=\S{1,20}&&NotNull"
						size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">密码：</td>
					<td><input name="FtpUserPassword" type="password"
						value="${FtpUserPassword}" id="FtpUserPassword"
						verify="密码为4-20位|Regex=\S{4,20}&&NotNull" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">是否启用：</td>
					<td>${radioStatus}</td>
				</tr>
			</table>
			</td>
			<td valign="top"
				style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<table width="100%" border="0">
				<tr>
					<td height="25"><input type="radio" name="ArticleType"
						value="Y" id="RedirectURLRadio" onclick="onRedirectURLUniteClick();">
					<label>跳转URL：</label> </td>
				</tr>
				<tr>
					<td height="25"><input name="RedirectUrl" type="text"
						disabled="true" id="RedirectUrl" style="width:340px"
						value="请在此填写跳转合并规则..."></td>
				</tr>
				<tr>
					<td height="25">
					<p><input type="radio" name="ArticleType" value="Y"
						id="ContentRadio" onclick="onContentUniteClick();"> <label
						for="ContentUniteFlag">文章内容：</label></p>
					</td>
				</tr>
				<tr>
					<td><textarea name="Content" id="Content"
						disabled="true" style="width:340px;height:100px">${Content}</textarea></td>
				</tr>
				<tr>
					<td class="gray">在跳转URL合并规则和内容合并模板中，可以使用<strong>
					$</strong><strong>{Name}</strong> 的方式引用从FTP上采集到的文件名,同样的可以使用<strong>$</strong><strong>{Suffix}</strong>得到文件的后缀，也可以使用<strong>$</strong><strong>{Path}</strong>得到文件所在路径.最后会将<strong><strong>$</strong><strong>{Name}</strong>&nbsp;</strong>,&nbsp;<strong>$</strong><strong>{Suffix}</strong>&nbsp;</strong>和&nbsp;<strong>$</strong><strong>{Path}</strong>这些配置替换成字段的实际值。</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
	</body>
</z:init>
</html>
