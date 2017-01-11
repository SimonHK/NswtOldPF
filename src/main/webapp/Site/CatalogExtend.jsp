<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��չ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
Page.onLoad(function(){
	var type = $V("Type");
	var ctype ="article";
	if(type=="1"){
		ctype ="article";
	}else if(type=="4"){
		ctype ="image";
	}else if(type=="5"){
		ctype ="video";
	}else if(type=="6"){
		ctype ="audio";
	}else if(type=="7"){
		ctype ="attach";
	}
	$("BtnAdd").setAttribute("priv",ctype+"_manage");
	$("BtnEdit").setAttribute("priv",ctype+"_manage");
	$("BtnDel").setAttribute("priv",ctype+"_manage");
	Application.setAllPriv(ctype,$V("InnerCode"));
});

function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "�½���չ����";
	diag.URL = "Site/CatalogExtendDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	var noCheckArr = [];
	var inputType = $DW.$V("InputType");
	if($DW.htmlinput==inputType){
		var content = $DW.getContent();
		if(content==""||content=="<br/>"){
			alert("�������ݲ���Ϊ��");
			return;
		}else{
			dc.add("Content",content);
		}
	}else{
		if($DW.input==inputType){
			noCheckArr.push($DW.$("ImagePath"));
		}else if($DW.imageInput==inputType){
			noCheckArr.push($DW.$("TextValue"));
		}
		if($DW.Verify.hasError(noCheckArr,"form2")){
			return;
		}
	}
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.site.CatalogExtend.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ��У�");
		return;
	}
	if(drs.length>1){
		Dialog.alert("ֻ���޸�1����Ϣ��");
		return;
	}
	dr = drs[0];
  editDialog(dr.get("ColumnID"));
}

function editDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "�༭��չ����";
	diag.URL = "Site/CatalogExtendEditDialog.jsp?ColumnID="+ID+"&CatalogID="+$V("CatalogID");
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	var noCheckArr = [];
	var inputType = $DW.$V("InputType");
	var content = $DW.getContent();
	if($DW.htmlinput==inputType){
		if(content==""||content=="<br/>"){
			alert("�������ݲ���Ϊ��");
			return;
		}else{
			dc.add("Content",content);
		}
	}else{
		if($DW.input==inputType){
			noCheckArr.push($DW.$("ImagePath"));
		}else if($DW.imageInput==inputType){
			noCheckArr.push($DW.$("TextValue"));
		}
		if($DW.Verify.hasError(noCheckArr,"form2")){
			return;
		}
	}
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.site.CatalogExtend.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
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
		dc.add("CatalogID",$V("CatalogID"));
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.site.CatalogExtend.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}
			});
		});
	});
}

</script>
</head>
<body>
<z:init>
	<div style="padding:2px;">
	<table width="100%" cellpadding="0" cellspacing="0"
		style="margin-bottom:4px;">
		<tr>
			<td>
			<input name="CatalogID" id="CatalogID" value="${CatalogID}" type="hidden" />
			<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" />
			<input name="Type" type="hidden" id="Type" value="${Type}" />
			<z:tbutton id="BtnAdd" priv="article_manage"
				onClick="add()">
				<img src="../Icons/icon018a2.gif" />�½�</z:tbutton> <z:tbutton id="BtnEdit"
				priv="article_manage" onClick="edit()">
				<img src="../Icons/icon018a4.gif" />�༭</z:tbutton> <z:tbutton id="BtnDel"
				priv="article_manage" onClick="del()">
				<img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton></td>
		</tr>
	</table>
	<z:datagrid id="dg1"
				method="com.nswt.cms.site.CatalogExtend.dg1DataBind" page="false"
				autoFill="true">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="6%" ztype="RowNo">&nbsp;</td>
				<td width="5%" ztype="selector" field="ColumnID">&nbsp;</td>
				<td width="13%"><b>��������</b></td>
				<td width="12%"><b>����</b></td>
				<td width="14%"><b>����</b></td>
				<td width="50%"><b>��������</b></td>
			</tr>
			<tr style1="background-color:#FFFFFF"
				style2="background-color:#F9FBFC"
				onDblClick="editDialog(${ColumnID})">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${Name}</td>
				<td>${Code}</td>
				<td>${InputTypeName}</td>
				<td>${TextValue}</td>
			</tr>
		</table>
	</z:datagrid></div>
</z:init>
</body>
</html>
