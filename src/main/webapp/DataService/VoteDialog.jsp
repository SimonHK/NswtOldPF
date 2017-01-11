<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<z:init method="com.nswt.cms.dataservice.Vote.initDialog">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
	Selector.setValueEx("VoteCatalogID",'${VoteCatalogID}','${VoteCatalogName}');
	changeVoteType();
	if($V("ID")!=""){
		$("VoteType_0").disabled = true;
		$("VoteType_1").disabled = true;
	}
	var Prop4 = "${Prop4}";
	if(!Prop4){
		Prop4 = "AfterVote";
	}
	$NS("Prop4",Prop4);
});

function changeVoteType(){
	if($NV("VoteType")=="0"){
		$("tr_RelaCatalogID").show();
		$("tr_VoteCatalogID").hide();
	}else{
		$("tr_RelaCatalogID").hide();
		$("tr_VoteCatalogID").show();
	}
}
</script>
</head>
<body class="dialogBody">
<form id="form2">
<table width="500" border="0" align="center" cellpadding="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
	<tr>
      <td width="22%" align="right" ></td>
      <td width="78%"></td>
    </tr>
    <tr>
      <td align="right" >�������ͣ�</td>
      <td>
		  <input type='radio' name='VoteType' id='VoteType_0' value='0' onclick="changeVoteType()" ${VoteType_0}><label for='VoteType_0'>��ͨ����</label>
		  <input type='radio' name='VoteType' id='VoteType_1' value='1' onclick="changeVoteType()" ${VoteType_1}><label for='VoteType_1'>��Ŀ����</label>
	  </td>
    </tr>
    <tr>
      <td align="right" >�������⣺</td>
      <td>
		  <input name="Title" id="Title" value="${Title}" type="text" size="50" verify="��������|NotNull" />
		  <input type="hidden" name="ID" id="ID" value="${ID}" />
	  </td>
    </tr>
	<tr>
      <td align="right" >�Ƿ�����IP��</td>
      <td>${IPLimit}</td>
    </tr>
    <tr>
      <td align="right" >�Ƿ�����鿴��</td>
      <td><input type="radio" name="Prop4" value="Yes" id="Prop4_0">
      <label for="Prop4_0">����ֱ�Ӳ鿴</label>
      <input type="radio" name="Prop4" value="AfterVote" id="Prop4_1">
      <label for="Prop4_1">����ͶƱ��鿴</label>
      <input type="radio" name="Prop4" value="Not" id="Prop4_2">
      <label for="Prop4_2">������鿴</label></td>
    </tr>
    <tr>
      <td align="right" >��֤���־��</td>
      <td>${VerifyFlag}</td>
    </tr>
    <tr style="display:none">
      <td align="right" >�����ʾ��</td>
      <td><input name="Width" id="Width" value="${Width}" type="text" size="1" verify="��ʾ���|NotNull&&Int" />����</td>
    </tr>
    <tr>
	  <td align="right">��ʼʱ�䣺</td>
	  <td>
	  <input name="StartDate" id="StartDate" value="${StartDate}" type="text" size="14" ztype="Date"/>
	  <input name="StartTime" id="StartTime" value="${StartTime}" type="text" size="10" ztype="Time"/><font color="red">Ϊ����������ʼ</font>
	  </td>
	</tr>
	<tr>
	  <td align="right">��ֹʱ�䣺</td>
	  <td >
	  <input name="EndDate" id="EndDate" value="${EndDate}" type="text" size="14" ztype="Date"/> 
	  <input name="EndTime" id="EndTime" value="${EndTime}" type="text" size="10" ztype="Time"/><font color="red">Ϊ������Ҫ�ֶ���ֹ</font>
	  </td>
	</tr>
    <tr id="tr_RelaCatalogID">
        <td height="25" align="right" >������Ŀ��</td>
        <td height="25"><z:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></z:select></td>
    </tr>
    <tr id="tr_VoteCatalogID" style="display:none">
        <td height="25" align="right" >������Ŀ��</td>
        <td height="25"><z:select id="VoteCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp" verify="NotNull" condition="$('VoteType_1').checked==true"></z:select></td>
    </tr>
</table>
</form>
</body>
</z:init>
</html>
