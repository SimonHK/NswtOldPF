<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<%
	String catalogID = request.getParameter("RelaCatalogID");
	String value = "0";
	String text = "APPӦ��";
	if (StringUtil.isNotEmpty(catalogID)&&!catalogID.equalsIgnoreCase("null")&&!catalogID.equals("0")) {
		value = catalogID;
		text = CatalogUtil.getName(catalogID);
	}
%>
<script>
Page.onLoad(function(){
	if($V("hPositionType")!=""){
		$S("PositionType",$V("hPositionType"));
	}
	if($V("Align")=="Y"){
		$("AlignBox").checked = true;
		$("PaddingLeft").disabled = true;
		$("PaddingTop").disabled = true;
	}else{
		$("AlignBox").checked = false;
	}
	if($V("Scroll")=="Y"){
		$("ScrollBox").checked = true;
	}else{
		$("ScrollBox").checked = false;
	}
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
});

function  ChangePositionType(){
	var PositionType = $V("PositionType");
	if(PositionType=="code"){
		$("trSize").hide();
	}else{
		$("trSize").show();
	}
	if(PositionType=="text"){
		$("trPosition").hide();
		$("trSize").hide();
	}
	if(PositionType=="couplet"||PositionType=="float"||PositionType=="fixure"){
		$("trPosition").show();
	}else{
		$("trPosition").hide();
	}
	if(PositionType=="fixure"){
		$("AlignSpan").style.display="";
		$("ScrollSpan").style.display="none";
	}else if(PositionType=="couplet"){
		$("ScrollSpan").style.display="";
		$("AlignBox").checked = false;
		$("PaddingLeft").disabled = false;
		$("PaddingTop").disabled = false;
		$S("Align","N");
		$("AlignSpan").style.display="none";
	}else{
		$("AlignSpan").style.display="none";
		$("ScrollSpan").style.display="none";
	}
	if($V("ID")!=""){
		if(PositionType!=$V("hPositionType")){
			$("alertTr").style.display="";
		}else{
			$("alertTr").style.display="none";
		}
	}
}

function changeAlign(){
	var Align = $V("Align");	
	var AlignBox = $("AlignBox");
	if(Align=="Y"){
		AlignBox.checked = false;
		$("PaddingLeft").disabled = false;
		$("PaddingTop").disabled = false;
		$S("Align","N");
	}else{
		AlignBox.checked = true;
		$("PaddingLeft").disabled = true;
		$("PaddingTop").disabled = true;
		$S("Align","Y");
	}
}

function changeScroll(){
	var Scroll = $V("Scroll");	
	var ScrollBox = $("ScrollBox");
	if(Scroll=="Y"){
		ScrollBox.checked = false;
		$S("Scroll","N");
	}else{
		ScrollBox.checked = true;
		$S("Scroll","Y");
	}
}
</script>
</head>
<body>
<z:init method="com.nswt.cms.dataservice.AdvertiseLayout.DialogInit">
<form id="form2">
<table width="100%" border="0" cellpadding="4" cellspacing="" bordercolor="#DEDEDC" style="border-collapse:collapse;">
	<tr>
		<td width="25%" height="12"></td>
		<td colspan="2">
        <input type="hidden" id="ID" name="ID" value="${ID}"/>
        <input type="hidden" id="hPositionType" name="hPositionType" value="${PositionType}"/>
        </td>
	</tr>
    <tr>
      <td  align="right" valign="top">��λ���ƣ�</td>
      <td width="30%"><input name="PositionName" type="text" id="PositionName" value="${PositionName}" size="20" verify="����λ����|NotNull"/></td>
      <td width="45%">������Ŀ:
      <z:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></z:select></td>
    </tr>
    <tr>
      <td  align="right" valign="top">��λ���ͣ�</td>
      <td colspan="2"><z:select name="PositionType" id="PositionType" onChange="ChangePositionType();">
        <span value="banner" selected>���κ��</span>
        <span value="fixure">�̶�λ��</span>
        <span value="float">Ư���ƶ�</span>
        <span value="couplet">�������</span>
        <span value="imagechange">ͼƬ�ֻ����</span>
        <span value="imagelist">ͼƬ�б���</span>
        <span value="text">���ֹ��</span>
        <span value="code">�������</span>
      </z:select>
      <span id="ScrollSpan" style="padding-left:30px;display:none;"><label for="ScrollBox"><input type="checkbox" id="ScrollBox" onClick="changeScroll();" checked/>��������</label></span>
      <span id="AlignSpan" style="padding-left:30px;display:none;"><label for="AlignBox"><input type="checkbox" id="AlignBox" onClick="changeAlign();" checked/>ȫ������</label></span><input type="hidden" id="Scroll" name="Scroll" value="${Scroll}"/><input id="Align" name="Align" type="hidden" value="${Align}"/>
      </td>
    </tr>
    <tr id="trPosition" style="display:none;">
    	<td align="right"  valign="top">��λλ�ã�</td>
        <td valign="top" colspan="2">
        ��߾ࣺ<input name='PaddingLeft' id='PaddingLeft' type='text' size='5' value='${PaddingLeft}' verify="��߾�|Number&&NotNull" condition="$V('PositionType')!='banner'&&$V('PositionType')!='imagechange'&&$V('PositionType')!='code'&&$('PaddingLeft').disabled!=true&&$V('PositionType')!='text'"> px&nbsp;&nbsp;
        �ϱ߾ࣺ<input name='PaddingTop' id='PaddingTop' type='text' size='5' value='${PaddingTop}' verify="�ϱ߾�|Number&&NotNull" condition="$V('PositionType')!='banner'&&$V('PositionType')!='imagechange'&&$V('PositionType')!='code'&&$('PaddingTop').disabled!=true&&$V('PositionType')!='text'" /> px
        </td>
    </tr>
    <tr id="trSize">
      <td align="right" valign="top">��λ�ߴ磺</td>
      <td colspan="2">&nbsp;&nbsp;&nbsp;��ȣ�<input name="PositionWidth" type="text" id="PositionWidth"  value="${PositionWidth}" size="5" verify="��λ���|Number&&NotNull" condition="$V('PositionType')!='code'&&$V('PositionType')!='text'"/> px&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      �߶ȣ�<input name="PositionHeight" type="text" id="PositionHeight" value="${PositionHeight}" size="5" verify="��λ�߶�|Number&&NotNull" condition="$V('PositionType')!='code'&&$V('PositionType')!='text'" /> px</td>
    </tr>
    <tr>
      <td  align="right" valign="top">��λ������</td>
      <td colspan="2"><textarea id="Description" name="Description" cols="52" rows="4">${Description}</textarea></td>
    </tr>
    <tr id="alertTr" style="display:none;">
      <td>&nbsp;</td>
      <td align="left" colspan="2"><font color="#FF0000">ע�⣺�����λ���ͽ����°�λ�����еĹ���������</font></td>
    </tr>
</table>
</form>
</z:init>
</body>
</html>