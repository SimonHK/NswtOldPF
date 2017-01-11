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
			if(options[i].innerText == "��������"){
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
				arr[i].add("��������","Content");
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
					<td align="right" width="100">�������ƣ�</td>
					<td><input name="Name" type="text" value="${Name}" id="Name"
						verify="NotNull" size="20" /></td>
				</tr>
				<tr>
					<td height="24" align="right" width="100">�ɼ����ˣ�</td>
					<td><z:select id="CatalogID" listWidth="300" verify="NotNull"
						condition="$NV('Type')=='D'" listHeight="300"
						listURL="Site/CatalogSelectList.jsp"></z:select></td>
				</tr>
				<tr id="tr_Password2">
					<td align="right" width="100">�ɼ��ļ����ͣ�</td>
					<td><input name="GatherType" type="text" value="${GatherType}"
						id="GatherType" verify="NotNull" size="20" /> <a href="#"
						class="tip" onMouseOut="Tip.close(this)"
						onMouseOver="Tip.show(this,'����ָ�����ļ����Ͳɼ��ļ�,����ͬʱָ���������.��:txt,rmvb,....���ֲɼ��ɼ�����֮��ʹ��Ӣ�ĵĶ��Ÿ���');"><img
						src="../Framework/Images/icon_tip.gif" width="13" height="16"></a>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">FTP������ַ��</td>
					<td><input type="hidden" id="ID" value="${ID}" /> <input
						name="FtpHost" type="text" value="${FtpHost}" id="FtpHost"
						verify="NotNull" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">FTP�����˿ںţ�</td>
					<td><input name="FtpPort" type="text" value="${FtpPort}"
						id="FtpPort" verify="NotNull&&Int" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">�ɼ�Ŀ¼��</td>
					<td><input name="GatherDrectory" value="${GatherDrectory}"
						type="text" id="GatherDrectory" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">�û�����</td>
					<td><input name="FtpUserName" type="text"
						value="${FtpUserName}" id="FtpUserName"
						verify="�û������20λ������Ӣ����ĸ�����֣����֣���ǡ�_������.������@��|Regex=\S{1,20}&&NotNull"
						size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">���룺</td>
					<td><input name="FtpUserPassword" type="password"
						value="${FtpUserPassword}" id="FtpUserPassword"
						verify="����Ϊ4-20λ|Regex=\S{4,20}&&NotNull" size="20" /></td>
				</tr>
				<tr>
					<td align="right" width="100">�Ƿ����ã�</td>
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
					<label>��תURL��</label> </td>
				</tr>
				<tr>
					<td height="25"><input name="RedirectUrl" type="text"
						disabled="true" id="RedirectUrl" style="width:340px"
						value="���ڴ���д��ת�ϲ�����..."></td>
				</tr>
				<tr>
					<td height="25">
					<p><input type="radio" name="ArticleType" value="Y"
						id="ContentRadio" onclick="onContentUniteClick();"> <label
						for="ContentUniteFlag">�������ݣ�</label></p>
					</td>
				</tr>
				<tr>
					<td><textarea name="Content" id="Content"
						disabled="true" style="width:340px;height:100px">${Content}</textarea></td>
				</tr>
				<tr>
					<td class="gray">����תURL�ϲ���������ݺϲ�ģ���У�����ʹ��<strong>
					$</strong><strong>{Name}</strong> �ķ�ʽ���ô�FTP�ϲɼ������ļ���,ͬ���Ŀ���ʹ��<strong>$</strong><strong>{Suffix}</strong>�õ��ļ��ĺ�׺��Ҳ����ʹ��<strong>$</strong><strong>{Path}</strong>�õ��ļ�����·��.���Ὣ<strong><strong>$</strong><strong>{Name}</strong>&nbsp;</strong>,&nbsp;<strong>$</strong><strong>{Suffix}</strong>&nbsp;</strong>��&nbsp;<strong>$</strong><strong>{Path}</strong>��Щ�����滻���ֶε�ʵ��ֵ��</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
	</body>
</z:init>
</html>
