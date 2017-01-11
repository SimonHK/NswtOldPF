<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onTypeChange(){
	if($NV("Type")=="Custom"){
		$("TableLink").hide();
		parent.$("Column").show();
	}else{
		$("TableLink").show();
		parent.$("Column").hide();
	}
}

function onServerTypeChange(){
	var st = $V("ServerType");
	if(st=="ORACLE"){
		$S("Port","1521");
	}
	if(st=="DB2"){
		$S("Port","50000");
	}
	if(st.startWith("MSSQL")){
		$S("Port","1433");
	}
	if(st=="MYSQL"){
		$S("Port","3306");
	}
}
var initFlag = true;
function loadTables(dr){//��ʼ��ʱdr��ֵ
	if(initFlag&&$V("ID")){
		initFlag = false;
		return;//�༭ʱ��һ��onchangeʱ��ִ��
	}
	if(!$V("DatabaseID")){
		$("OldCode").clear();
		return;
	}
	var dc = new DataCollection();
	dc.add("DatabaseID",$V("DatabaseID"));
	Dialog.wait("���ڳ��Ի�ȡ���ݿ������ݱ���Ϣ...");
	Server.sendRequest("com.nswt.cms.dataservice.OuterDatabase.getTables",dc,function(response){
		Dialog.endWait();
		if(!dr){
			Dialog.alert(response.Message);
		}
		if(dc.Status!=0){
			$("OldCode").clear();
			var arr = response.get("Tables");
			var t = $("OldCode");
			var options = [];
			for(var i=0;arr&&i<arr.length;i++){
				options.push([arr[i],arr[i]]);
			}
			t.addBatch(options);
			if(dr){
				$S(t,dr.get("OldCode"));
			}
		}
	});
}

function conn(){
	var diag = new Dialog("DiagConn");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "�½��ⲿ����";
	diag.URL = "DataService/OuterDatabase.jsp";
	diag.CancelEvent = function(){
		$("DatabaseID").loadData();
		$D.close();
	};
	diag.show();
	diag.OKButton.hide();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<z:init method="com.nswt.cms.dataservice.CustomTable.init">
<form id="F1">
<table width="500" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td width="23%" height="30" align="right" >�����ͣ�</td>
      <td width="77%"><input type="radio" name="Type" id="TypeCustom" value="Custom" checked onClick="onTypeChange()">
          <label for="TypeCustom">�Զ����</label>
          <input type="radio" name="Type" id="TypeLink"  value="Link" onClick="onTypeChange()">
          <label for="TypeLink">�ⲿ���ݿ���ر�
          <input name="ID" type="hidden" id="ID">
          </label></td>
    </tr>
    <tr>
      <td colspan="2" align="center"  height="5"></td>
    </tr>
</table>
<table width="500" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="30" align="right" >����룺</td>
      <td><input name="Code" type="text" class="inputText" id="Code" size=40 verify="NotNull"/></td>
    </tr>
    <tr>
      <td width="23%" height="30" align="right" >�����ƣ�</td>
      <td width="77%">
	  <input name="Name" type="text" class="inputText" id="Name" size=40 verify="NotNull"/>	  </td>
    </tr>
    <tr>
      <td height="30" align="right" >ѡ�</td>
      <td><input name="AllowView" type="checkbox" id="AllowView" value="Y" checked>
        <label for="AllowView">����ǰ̨�鿴</label>
		<input name="AllowModify" type="checkbox" id="AllowModify" value="Y" checked>
        <label for="AllowModify">����ǰ̨�ύ����</label></td>
    </tr>
    <tr>
      <td height="30" align="right" >��ע��</td>
      <td><input name="Memo" type="text" class="inputText" id="Memo" size=60/></td>
    </tr>
</table>
<table width="500" align="center" cellpadding="2" cellspacing="0" style="display:none" id="TableLink">
    <tr>
      <td height="30"  width="23%" align="right">�ⲿ���ݿ����ӣ�</td>
      <td width="77%">
	  <z:select id="DatabaseID" method="com.nswt.cms.dataservice.OuterDatabase.getDatabases" onChange="loadTables();" style="width:225px" verify="NotNull" condition="$NV('Type')=='Link'" defaultblank="true">
	  </z:select>
	  <z:tbutton onClick="conn()"><img src="../Icons/icon006a10.gif" width="20" height="20" />�½��ⲿ����</z:tbutton>
	  </td>
    </tr>
    <tr>
      <td height="30" align="right">ѡ���</td>
      <td><z:select id="OldCode" style="width:225px" verify="NotNull"  condition="$NV('Type')=='Link'"></z:select></td>
    </tr>
</table>
</form>
</z:init>
</body>
</html>
