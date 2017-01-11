<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
Page.onLoad(function(){
});
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 380;
	diag.Height = 380;
	diag.Title = "�½��ֶ�";
	diag.URL = "../Platform/CustomColumnItemDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
		$DW.$("button").onclick = Save;
	};
	diag.show();
}
function Save(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	if(($DW.$V("ShowMod")=="1" || $DW.$V("ShowMod")=="2")&&$DW.$V("ListOpt")!=""){
		alert("�����ı�������ı�����Ҫ��д�б�ѡ��!");
		return;
	}
	dc.add("ClassCode",$V("ClassCode"));	
	Server.sendRequest("com.nswt.platform.CustomColumnItem.save",dc,function(response){
		if(response.Status==1){
			alert(response.Message);
			$D.close();
			window.location.reload();			
		}
		else{
			alert(response.Message);
		}
	});
}
function showEditDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 380;
	diag.Height = 380;
	diag.Title = "�޸��ֶ�";
	diag.URL = "../Platform/CustomColumnItemDialog.jsp";
	diag.onLoad = function(){
		$DW.$S("ID",dr.get("ID"));
		$DW.$S("Name",dr.get("Name"));
		$DW.$S("Code",dr.get("Code"));
		$DW.$S("Type",dr.get("Type_Code"));
		$DW.$S("Length",dr.get("Length"));
		$DW.$S("ShowMod",dr.get("ShowMod_Code"));
		$DW.$S("DefaultValue",dr.get("DefaultValue"));
		$DW.$S("ListOpt",dr.get("ListOpt"));
		if(dr.get("MandatoryFlag_Code")=="0"){
			$DW.$("MandatoryFlag").checked="";
		}
		$DW.$("Name").focus();
		$DW.$("button").onclick = Save;
	};
	diag.show();
}
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	if(!window.confirm("ȷ��Ҫɾ�����ֶ���")){
		return;
	}
	else{
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.platform.CustomColumnItem.del",dc,function(response){
			if(response.Status==1){
				alert("ɾ���ɹ�");
				window.location="CustomItem.jsp?ClassCode=Sys_CMS&CatalogID="+$V("CatalogID");
			}
			else{
				alert(response.Message);
			}
		});
	}
}
function CopyTo(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		alert("����ѡ��Ҫ���Ƶ��У�");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 260;
	diag.Height = 180;
	diag.Title = "���Ƶ�������Ŀ";
	diag.URL = "CustomItemCopyToDialog.jsp?IDs="+arr.join()+"&CatalogID="+$V("CatalogID");
	diag.onLoad = function(){
		$DW.$("button").onclick = CopyToSave;
	};
	diag.show();
}
function CopyToSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("IDs",$DW.$V("IDs"));
	Server.sendRequest("com.nswt.platform.CustomColumnItem.CopyToSave",dc,function(response){
		if(response.Status==1){
			alert(response.Message);
			$D.close();
		}
		else{
			alert(response.Message);
		}
	});
}
</script>
</head>
<body scroll="no">
<z:init method="com.nswt.platform.CustomColumnItem.init">
<div style="padding:2px;">
	<table width="100%" cellpadding="4">
		<tr>
			<td>
				<z:tbutton onClick="add();"><img src="../Platform/Images/tab1_tri.gif"/>�½�</z:tbutton>
				<z:tbutton onClick="del();"><img src="../Platform/Images/tab1_tri.gif"/>ɾ��</z:tbutton>
				<z:tbutton onClick="CopyTo();"><img src="../Platform/Images/tab1_tri.gif"/>���Ƶ�������Ŀ</z:tbutton>
			</td>
		</tr>
	</table>
<div id="maincontent">
<z:datagrid id="dg1" method="com.nswt.platform.CustomColumnItem.dg1DataBind" size="20" editAction="showEditDialog">
  <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
    <tr ztype="head" class="dataTableHead">
      <td  width="5">&nbsp;</td>
      <td width="20" ztype="selector" field="id">&nbsp;</td>
      <td width="160"><b>�ֶ�����</b></td>
      <td width="100"><b>�ֶδ���</b></td>
      <td width="80"><b>��������</b></td>
      <td width="60"><b>�ֶγ���</b></td>
      <td width="60"><b>��ʾ��ʽ</b></td>
      <td width="60"><b>�Ƿ�ǿ�</b></td>
    </tr>
    <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
      <td  width="5">&nbsp;</td>
      <td width="20">&nbsp; </td>
      <td>${Name}</td>
      <td>${Code}</td>
      <td>${Type}</td>
      <td>${Length}</td>
      <td>${ShowMod}</td>
      <td><b>${MandatoryFlag}</b></td>
    </tr>
	<tr ztype="pagebar">
		<td colspan="8" align="center">${PageBar}</td>
	</tr>
  </table>
</z:datagrid>
</div>
<input name="ClassCode" id="ClassCode" value="${ClassCode}" type="hidden"/>
<input name="CatalogID" id="CatalogID" value="${CatalogID}" type="hidden"/>
</div>
</z:init>
</body>
</html>
