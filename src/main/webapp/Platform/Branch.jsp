<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��֯����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		dr.set("Memo",$V("Memo"));
		return true;
	}
});

function addBranch(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var ParentInnerCode="";
	if(drs&&drs.length>0){
		ParentInnerCode = drs[0].get("BranchInnerCode");
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "�½���֯����";
	diag.URL = "Platform/BranchDialog.jsp?ParentInnerCode="+ParentInnerCode;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�����֯����";
	diag.Message = "���û������ơ��ϼ�������";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.platform.Branch.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�༭����֯������");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "�޸Ļ���";
	diag.URL = "Platform/BranchEditDialog.jsp?BranchInnerCode="+dr.get("BranchInnerCode");
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.platform.Branch.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Dialog.confirm("ע�⣺���Ǽ���ɾ�����¼���֯������һ��ɾ������ȷ��Ҫɾ����",function(){
		Server.sendRequest("com.nswt.platform.Branch.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}				
			});
		});
	});
}

function sortBranch(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	
	var ds = $("dg1").DataSource;
	if (ds.get(rowIndex-1,"BranchInnerCode").length == 4) {
		alert("��ѡ������ܻ������ܻ�������Ҫ����");
		DataGrid.loadData("dg1");
		return;
	}
	
	if (rowIndex-1 == 0) {
		alert("�κ��ӻ��������������ܻ���ǰ��");
		DataGrid.loadData("dg1");
		return;
	}
	
	var type = "";
	var orderBranch = "";
	var nextBranch = "";
	if (ds.get(rowIndex-1,"ParentInnerCode") == ds.get(rowIndex,"ParentInnerCode")) {
		type = "Before";
		orderBranch = ds.get(rowIndex-1,"BranchInnerCode");
		nextBranch = ds.get(rowIndex,"BranchInnerCode");
	} else if (ds.get(rowIndex-1,"ParentInnerCode") == ds.get(rowIndex-2,"ParentInnerCode")) {
		type = "After";
		orderBranch = ds.get(rowIndex-1,"BranchInnerCode");
		nextBranch = ds.get(rowIndex-2,"BranchInnerCode");
	} else {
		alert("����ͬһ�����µ��ӻ�����������");
		DataGrid.loadData("dg1");
		return;
	}
	var dc = new DataCollection();
	dc.add("OrderType",type);
	dc.add("OrderBranch",orderBranch);
	dc.add("NextBranch",nextBranch);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.platform.Branch.sortBranch",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function up(){
	window.history.back(-1);
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon042a1.gif" width="20" height="20" />��֯������</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;"><z:tbutton onClick="addBranch()">
						<img src="../Icons/icon042a2.gif" width="20" height="20"/>�½�����</z:tbutton>
					<z:tbutton onClick="edit()">
						<img src="../Icons/icon042a4.gif" width="20" height="20"/>�༭</z:tbutton>
					<z:tbutton onClick="del()">
						<img src="../Icons/icon042a3.gif" width="20" height="20"/>ɾ��</z:tbutton>
					<z:tbutton onClick="up()">
						<img src="../Icons/icon042a9.gif" width="20" height="20"/>����</z:tbutton>	
						</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.platform.Branch.dg1DataBind"
					page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="sortBranch">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" ztype="selector" field="BranchInnerCode">&nbsp;</td>
							<td width="25%" ztype="tree" level="TreeLevel"><b>����</b></td>>
							<td width="10%"><b>����</b></td>
							<td width="20%"><strong>��������</strong></td>
							<td width="18%"><strong>�绰</strong></td>
							<td width="18%"><strong>����</strong></td>
						</tr>
						<tr onDblClick="edit();">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><a href="Branch.jsp?ParentInnerCode=${BranchInnerCode}">${Name}</a></td>
							<td>${BranchCode}</td>
							<td>${ManagerName}</td>
							<td>${Phone}</td>
							<td>${Fax}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
