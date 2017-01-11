<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ƹ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","searchGender",$V("searchGender"));
	DataGrid.setParam("dg1","searchEduLevel",$V("searchEduLevel"));
	DataGrid.loadData("dg1");
}

function editDialog(ID){
	var diag = new Dialog("Diag2");
	diag.Width = 700;
	diag.Height = 450;
	diag.Title = "��Ƹ��Ϣ����";
	diag.URL = "DataService/ApplyAdminDialog.jsp?ID="+ID;
	diag.onLoad = function(){
	};

	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
}

 
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ��ѡ�еĻ�Ա��",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.ApplyAdmin.del",dc,function(){
			var response = Server.getResponse();
			Dialog.alert(response.Message);
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
} 
</script>
</head>
<body>
<z:init method="com.nswt.cms.dataservice.ApplyAdmin.initSearch">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon018a6.gif" />��Ƹ�б�</td>
			</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="del()">
					<img src="../Icons/icon021a3.gif"/>ɾ��</z:tbutton>
				<div style="float: right; white-space: nowrap;">
                  	  <div ztype='select' id="searchGender" style="width:50px;">${Gender}</div>&nbsp;	
	                  <div ztype='select' id="searchEduLevel" style="width:100px;">${EduLevel}</div>&nbsp;
            	  <input type="button" name="buttonSubmit" value="��ѯ" onClick="doSearch()">
            	</div>	
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
							method="com.nswt.cms.dataservice.ApplyAdmin.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo"><strong>���</strong></td>
							<td width="5%" ztype="selector" field="ID">&nbsp;</td>
							<td width="10%"><b>ӦƸ��λ</b></td>
							<td width="10%"><b>����</b></td>
							<td width="5%"><b>�Ա�</b></td>
							<td width="15%"><b>ѧ��</b></td>
							<td width="15%"><b>��ϵ�绰</b></td>
							<td width="25%"><b>��������</b></td>
							<td width="20%"><b>��ҵѧУ</b></td>
							
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC" onDblClick="editDialog('${ID}')">
							<td width="5%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${WillPosition}</td>
							<td>${Name}</td>
							<td>${GenderName}</td>
							<td>${EduLevelName}</td>
							<td>${Mobile}</td>
							<td>${Email}</td>
							<td>${University}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="8">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</z:init>
</body>
</html>

