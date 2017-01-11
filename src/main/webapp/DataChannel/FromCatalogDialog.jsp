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
function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 150;
	diag.Title = "�����Ŀ";
	diag.URL = "DataChannel/AddCatalogDialog.jsp?Type=Gather";
	diag.onLoad = function(){
		$DW.getCatalogInfo();
	};
	diag.OKEvent = addSave;
	diag.ShowButtonRow = true;
	diag.show();
}

function addSave(){
	var dt = $("dg1").DataSource;
	var addr = $DW.$V("ServerAddr");
	var site = $DW.$V("SiteID");
	var catalog = $DW.$V("CatalogID");
	if(addr=='localhost'&&site==$V("SiteID")&&catalog==$V("CatalogID")){
		Dialog.alert("���ܲɼ�����Ŀ����!");
		return;
	}
	dt.insertRow(dt.Rows.length,[addr,site,$DW.$("SiteID").getText(),catalog,$DW.$("CatalogID").getText(),$DW.$V("Password")]);
	$("dg1").setParam("Data",dt);
	$("dg1").loadData();
	$D.close();
}

function edit(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("����ѡ��һ�м�¼");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 150;
	diag.Title = "�޸���Ŀ";
	diag.URL = "DataChannel/AddCatalogDialog.jsp";
	diag.onLoad = function(){
		$DW.$S("ServerAddr",dt.Rows[0].get("ServerAddr"));
		$DW.$S("Password",dt.Rows[0].get("Password"));
		$DW.getSiteInfo(function(){
			$DW.$S("SiteID",dt.Rows[0].get("SiteID"));
			$DW.getCatalogInfo(function(){
				$DW.$S("CatalogID",dt.Rows[0].get("CatalogID"));
			});
		});
	};
	diag.OKEvent = editSave;
	diag.ShowButtonRow = true;
	diag.show();
}

function editSave(){
	var i = $("dg1").getSelectedRows()[0].rowIndex-1;
	var dt = $("dg1").DataSource;
	dt.deleteRow(i);
	dt.insertRow(i,[$DW.$V("ServerAddr"),$DW.$V("SiteID"),$DW.$("SiteID").getText(),$DW.$V("CatalogID"),$DW.$("CatalogID").getText(),$DW.$V("Password")]);	
	$("dg1").setParam("Data",dt);
	$("dg1").loadData();
	$D.close();
}

function del(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("����ѡ��һ�м�¼");
		return;
	}
	var i = $("dg1").getSelectedRows()[0].rowIndex-1;
	var dt = $("dg1").DataSource;
	dt.deleteRow(i);
	$("dg1").setParam("Data",dt);
	$("dg1").loadData();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.datachannel.FromCatalog.init">
<form id="form2">
<table width="780" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td width="151" height="10" class="tdgrey2">
	  <input type="hidden" id="ID" value="${ID}">
	  <input type="hidden" id="SiteID" value="${SiteID}">
	  </td>
		<td width="217" class="tdgrey2"></td>
	    <td width="136" class="tdgrey2"></td>
	    <td width="258" class="tdgrey2"></td>
	</tr>
	<tr>
	  <td height="35" align="right" class="tdgrey1">�ɼ��������ƣ�</td>
	  <td height="35" class="tdgrey2"><input name="Name" type="text" id="Name" verify="NotNull" value="${Name}" style="width:150px"></td>
	  <td height="35" align="right" class="tdgrey1">�ɼ����ˣ�</td>
	  <td height="35" class="tdgrey2"><z:select id="CatalogID" listWidth="300" listHeight="300" verify="NotNull" listURL="Site/CatalogSelectList.jsp"></z:select></td>
	</tr>
	<tr>
	  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">ͬ�������޸�/ɾ����</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${SyncArticleModify}</td>
		<td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">����ͬ��������״̬��</td>
		<td bordercolor="#eeeeee" class="tdgrey2"><z:select id="AfterInsertStatus">${AfterInsertStatus}</z:select></td>
	</tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">ͬ����Ŀ��ӣ�</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${SyncCatalogInsert}</td>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">�ٴ�ͬ��������״̬��</td>
	  <td bordercolor="#eeeeee" class="tdgrey2"><z:select id="AfterModifyStatus">${AfterModifyStatus}</z:select></td>
	</tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">ͬ����Ŀ�޸ģ�</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${SyncCatalogModify}</td>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">&nbsp;</td>
	</tr>
	<tr>
	  <td height="35" align="right" valign="middle" class="tdgrey1">Դ��Ŀ�б�</td>
	  <td height="35" class="tdgrey2">				
	    <z:tbutton onClick="add()"><img src="../Icons/icon007a2.gif" width="20" height="20" />���</z:tbutton> 
		<z:tbutton onClick="edit()"><img src="../Icons/icon007a4.gif" width="20" height="20" />�޸�</z:tbutton> 
		<z:tbutton onClick="del()"><img src="../Icons/icon007a3.gif" width="20" height="20" />ɾ��</z:tbutton>		</td>
      <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">����״̬��</td>
      <td height="35" class="tdgrey2"><input name="Status" type="radio" id="StatusY" value="Y" checked>
          <label for="StatusY">����</label>
          <input type="radio" name="Status" value="N" id="StatusN">
          <label for="StatusN">ͣ��</label>
          <script>Page.onLoad(function(){if("${Status}")$NS("Status","${Status}")});</script>
      </td>
	</tr>
	<tr>
	  <td height="35" colspan="4" align="center" class="tdgrey2"><z:datagrid id="dg1" multiSelect="false" method="com.nswt.datachannel.FromCatalog.dialogDataBind" page="false" size="8">
	    <table width="700" cellpadding="2" cellspacing="0" class="dataTable">
          <tr ztype="head" class="dataTableHead">
            <td width="7%" ztype="Selector" multi='false' field="_RowNo"></td>
            <td width="33%"><b>Ӧ������</b></td>
            <td width="20%"><b>��Ŀ����</b></td>
            <td width="40%"><b>��������ַ</b></td>
          </tr>
          <tr onDblClick='edit(this);' style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
            <td></td>
            <td>${SiteName}</td>
            <td>${CatalogName}</td>
            <td>${ServerAddr}</td>
          </tr>
        </table>
	  </z:datagrid></td>
	  </tr>
</table>
</form>
</z:init>
</body>
</html>
