<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>�Զ����</title>
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
	diag.Height = 320;
	diag.Title = "�½��Զ����ֶ�";
	diag.URL = "Site/CatalogColumnDialog.jsp";
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
	if($DW.input==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.text==inputType){
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.select==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.radio==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.checkbox==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.dateInput==inputType||$DW.timeInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.imageInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.htmlInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
		var HTML = $DW.$V("HTML");
		if(HTML==""||HTML=="<br/>"){
			Dialog.alert("HTML���ݲ���Ϊ��");
			return;
		}else{
			dc.add("HTML",HTML);
		}
	}
	if($DW.Verify.hasError(noCheckArr,"form2")){
		return;
	}
	if(!$DW.$NV("IsMandatory")){
		dc.add("IsMandatory","N");
	}
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.site.CatalogColumn.add",dc,function(response){
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
  editDialog(dr.get("ID"));
}

function editDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 320;
	diag.Title = "�༭�Զ����ֶ�";
	diag.URL = "Site/CatalogColumnEditDialog.jsp?ColumnID="+ID;
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
	if($DW.input==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.text==inputType){
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.select==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.radio==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.checkbox==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.dateInput==inputType||$DW.timeInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.imageInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.htmlInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
		var HTML = $DW.$V("HTML");
		if(HTML==""||HTML=="<br/>"){
			Dialog.alert("HTML���ݲ���Ϊ��");
			return;
		}else{
			dc.add("HTML",HTML);
		}
	}
	if($DW.Verify.hasError(noCheckArr,"form2")){
		return;
	}
	if(!$DW.$NV("IsMandatory")){
		dc.add("IsMandatory","N");
	}
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.site.CatalogColumn.save",dc,function(response){
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
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 120;
	diag.Title = "ɾ���Զ����ֶ�";
	diag.URL = "Site/CatalogColumnDelDialog.jsp?ColumnID="+arr[0];
	diag.ShowMessageRow = true;
	diag.Message = "ɾ���Զ����ֶΣ�ɾ��ʱ��ѡ���Ƿ�ͬʱɾ��������Ŀ���Զ����ֶ�";
	diag.OKEvent = delSave;
	diag.show();
}

function delSave(){
	var dc = $DW.Form.getData("form2");
	var arr = DataGrid.getSelectedValue("dg1");
	dc.add("CatalogID",$V("CatalogID"));
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.site.CatalogColumn.del",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
				$D.close();
			}
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
			<input name="CatalogID" id="CatalogID" value="${CatalogID}"type="hidden" />
			<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" />
			<input name="Type" type="hidden" id="Type" value="${Type}" />
			<z:tbutton id="BtnAdd" priv="article_manage"
				onClick="add()">
				<img src="../Icons/icon024a2.gif" />�½�</z:tbutton> <z:tbutton id="BtnEdit"
				priv="article_manage" onClick="edit()">
				<img src="../Icons/icon024a4.gif" />�༭</z:tbutton> <z:tbutton id="BtnDel"
				priv="article_manage" onClick="del()">
				<img src="../Icons/icon024a3.gif" />ɾ��</z:tbutton></td>
		</tr>
	</table>
	<z:datagrid id="dg1"
				method="com.nswt.cms.site.CatalogColumn.dg1DataBind" page="false">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="4%" ztype="RowNo">&nbsp;</td>
				<td width="5%" ztype="selector" field="id">&nbsp;</td>
				<td width="15%"><b>�ֶ�ID</b></td>
				<td width="15%"><b>����</b></td>
				<td width="14%"><b>����</b></td>
				<td width="15%"><b>������ʽ</b></td>
				<td width="12%"><b>У������</b></td>
				<td width="10%" align="center" ztype="Checkbox" field="IsMandatory"
					checkedvalue="Y"><b>�Ƿ����</b></td>
				<td width="10%"><b>�������</b></td>
			</tr>
			<tr onDblClick="editDialog(${ID})">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${ID}</td>
				<td>${Name}</td>
				<td>${Code}</td>
				<td>${InputTypeName}</td>
				<td>${VerifyTypeName}</td>
				<td align="center"></td>
				<td>${MaxLength}</td>
				<td>${OrderFlag}</td>
			</tr>
		</table>
	</z:datagrid></div>

</z:init>
</body>
</html>
