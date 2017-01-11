<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>���ӷ���</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.nswt.cms.site.LinkGroup.save",function(){DataGrid.loadData('dg1');});
}

function add(){
	var diag = new Dialog("DiagLinkAdd");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "�½�"+this.document.title;
	diag.URL = "Site/LinkGroupDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("Type",$DW.$NV("Type"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.LinkGroup.add",dc,function(response){
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
	Dialog.confirm("��ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.site.LinkGroup.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function link(ID,type,name){
	if(!ID){
		var dt = $("dg1").getSelectedData();
		if(!dt||dt.Rows.length==0){
			Dialog.alert("����ѡ���¼!");
			return;
		}
		ID = dt.Rows[0].get("ID");
		type = dt.Rows[0].get("type");
		name = dt.Rows[0].get("name");
	}
	var diag = new Dialog("DiagLinkEdit");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "���ӷ��� "+name;
	diag.URL = "Site/Link.jsp?LinkGroupID="+ID+"&Type="+type;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
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
					src="../Icons/icon028a1.gif" />�������ӷ����б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
					<z:tbutton onClick="add();"><img src="../Icons/icon028a2.gif" />�½����ӷ���</z:tbutton> 
					<z:tbutton onClick="link();"><img src="../Icons/icon028a10.gif"/>���ӹ���</z:tbutton> 
					<z:tbutton onClick="$('dg1').edit();"><img src="../Icons/icon028a4.gif" />�޸�</z:tbutton> 
					<z:tbutton onClick="save();"><img src="../Icons/icon028a4.gif" />����</z:tbutton>
					<z:tbutton onClick="del();"><img src="../Icons/icon028a3.gif" />ɾ��</z:tbutton></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
					method="com.nswt.cms.site.LinkGroup.dg1DataBind">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo"><strong>���</strong></td>
							<td width="5%" ztype="selector" field="id">&nbsp;</td>
							<td width="50%"><strong>���ӷ�������</strong></td>
							<td width="30%"><strong>����</strong></td>
						</tr>
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>
							<td><a href="javascript:void(0)"
								onClick="link('${ID}','${Type}','${Name}');">${Name}</a></td>
							<td>${TypeName}</td>
						</tr>
						<tr ztype="edit">
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>
							<td><input type="text" class="input1" id="Name"
								value="${Name}" maxlength="20" style="width:200px"></td>
							<td>${TypeName}</td>
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
