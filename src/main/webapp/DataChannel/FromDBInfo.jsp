<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.datachannel.FromDB.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var initFlag = true;
function loadTables(tableName){//��ʼ��ʱdr��ֵ
	if(initFlag&&$V("ID")){
		initFlag = false;
		return;//�༭ʱ��һ��onchangeʱ��ִ��
	}
	if(!$V("DatabaseID")){
		$("TableName").clear();
		return;
	}
	var dc = new DataCollection();
	dc.add("DatabaseID",$V("DatabaseID"));
	Dialog.wait("���ڳ��Ի�ȡ���ݿ������ݱ���Ϣ...");
	Server.sendRequest("com.nswt.cms.dataservice.OuterDatabase.getTables",dc,function(response){
		Dialog.endWait();
		if(!tableName){
			Dialog.alert(response.Message);
		}
		if(dc.Status!=0){
			$("TableName").clear();
			var arr = response.get("Tables");
			var t = $("TableName");
			var options = [];
			for(var i=0;arr&&i<arr.length;i++){
				options.push([arr[i],arr[i]]);
			}
			t.addBatch(options);
			if(tableName){
				$S(t,tableName);
			}
			window.Init = false;
		}
	});
}

function onTableChange(){
	if(window.Init){
		window.Init = false;
		return;
	}
	var url  = Server.ContextPath+"DataChannel/FromDBMapping.jsp?DatabaseID="+$V("DatabaseID")+"&TableName="+$V("TableName");
	window.parent.Tab.getChildTab("Column").contentWindow.location = url;
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

Page.onLoad(function(){
	window.Init = true;//��ʼ��ʱ����Ҫ���¼���ӳ��
	var CatalogID = "${CatalogID}";
	var CatalogName = "${CatalogName}";
	Selector.setValueEx("CatalogID",CatalogID,CatalogName);
	$S("DatabaseID","${DatabaseID}");
	$S("PathReplacePartOld","${PathReplacePartOld}");
	$S("PathReplacePartNew","${PathReplacePartNew}");
	$S("NewRecordRule","${NewRecordRule}");
	$S("Memo","${Memo}");
	if("${Status}"){
		$NS("Status","${Status}");
	}
	loadTables("${TableName}");
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<form id="F1">
  <table width="760" align="center" cellpadding="2" cellspacing="0" id="TableLink">
    <tr>
      <td height="35" align="right" >�ɼ����ˣ�</td>
      <td height="35" class="tdgrey2"><z:select id="CatalogID" style="width:220px"  listWidth="300" listHeight="300" verify="NotNull" listURL="Site/CatalogSelectList.jsp"></z:select></td>
      </tr>
    <tr>
      <td width="21%" height="30" align="right" >�������ƣ�</td>
      <td width="79%">
	  <input name="Name" type="text" class="inputText" id="Name" style="width:220px" value="${Name}" verify="NotNull"/>
	  <input name="ID" type="hidden" id="ID" value="${ID}"></td>
      </tr>
    <tr>
      <td height="30"  width="21%" align="right">�ⲿ���ݿ����ӣ�</td>
      <td width="79%">
		  <z:select id="DatabaseID" onChange="loadTables();" method="com.nswt.cms.dataservice.OuterDatabase.getDatabases" style="width:220px" verify="NotNull" defaultblank="true" value="${DatabaseID}">		  </z:select>
			<z:tbutton onClick="conn()"><img src="../Icons/icon006a10.gif" width="20" height="20" />�½��ⲿ����</z:tbutton>		</td>
      </tr>
    <tr>
      <td height="30" align="right">ѡ�����ݱ�</td>
      <td><z:select id="TableName" style="width:220px;" verify="NotNull" onChange="onTableChange();"> </z:select></td>
      </tr>
    <tr>
      <td height="30" align="right" >����״̬��</td>
      <td><span class="tdgrey2">
        
          <input name="Status" type="radio" id="StatusY" value="Y" checked>
          <label for="StatusY">����</label>
          <input type="radio" name="Status" value="N" id="StatusN">
          <label for="StatusN">ͣ��</label>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ɼ��������״̬��<z:select id="ArticleStatus"> ${ArticleStatus}</z:select>
      </span></td>
    </tr>
    <tr>
      <td height="30" align="right" >�ļ�·���滻����</td>
      <td>������
        <input name="PathReplacePartOld" type="text" class="inputText" id="PathReplacePartOld" value="${PathReplacePartOld}" size=30/></td>
      </tr>
    <tr>
      <td height="30" align="right" >&nbsp;</td>
      <td>�滻Ϊ<span class="gray">
        <input name="PathReplacePartNew" type="text" class="inputText" id="PathReplacePartNew" value="${PathReplacePartNew}" size=30/>
      </span></td>
    </tr>
    <tr>
      <td height="30" align="right" >&nbsp;</td>
      <td class="gray">����ɼ��������ݺ���ͼƬ������������Ҫʹ�ò���ϵͳ������������ļ�ͬ�����ܣ���Զ�̷������ϵ��ļ��ɼ���ָ��Ŀ¼�£�Ȼ�������е�ͼƬ·���滻��ͬ�����ŵ�·����</td>
      </tr>
    <tr>
      <td height="30" align="right" >�ɼ�������</td>
      <td><input name="SQLCondition" type="text" class="inputText" id="SQLCondition" value="${SQLCondition}" size=40/>
        <span class="gray">        ��SQL��where�ؼ���֮��Ĳ�ѯ����</span></td>
    </tr>
    <tr>
      <td height="30" align="right" >��¼���¹���</td>
      <td><input name="NewRecordRule" type="text" class="inputText" id="NewRecordRule" value="${NewRecordRule}" size=40/></td>
      </tr>
    <tr>
      <td height="30" align="right" >&nbsp;</td>
      <td><p class="gray">��¼���¹�����Ϊ�˴ﵽÿ�βɼ�ֻ�ɼ��䶯�������ݶ����õĲ�ѯ������<strong>ID&gt;${Max(ID)}</strong>��ʾֻ�ɼ�Զ������Ϊ��ID�ֶ�ֵ�����Ѿ��ɼ�����������ID�ֶ����ֵ�ļ�¼������֮�������<strong>          Or
            </strong>��<strong> And </strong>����������<strong>AddTime&gt;${Max(AddTime)} or ModifyTime&gt;${Max(ModifyTime)} </strong></p>        </td>
      </tr>
    <tr>
      <td height="30" align="right" >��ע��</td>
      <td><input name="Memo" type="text" class="inputText" id="Memo" size=60/></td>
      </tr>
</table>
</form>
</body>
</html>
</z:init>
