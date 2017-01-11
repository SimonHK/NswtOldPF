<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=gb2312" />
<title>��Ա�ȼ���Ŀ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
					 
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "�½���Ա��չ�ֶ�";
	diag.URL = "DataService/MemberFieldDialog.jsp";
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
		noCheckArr.push($DW.$("Cols"));
		noCheckArr.push($DW.$("Rows"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.text==inputType){
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.selecter==inputType){
		noCheckArr.push($DW.$("Cols"));
		noCheckArr.push($DW.$("Rows"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.radio==inputType){
		noCheckArr.push($DW.$("Cols"));
		noCheckArr.push($DW.$("Rows"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.checkbox==inputType){
		noCheckArr.push($DW.$("Cols"));
		noCheckArr.push($DW.$("Rows"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.dateInput==inputType||$DW.timeInput==inputType){
		noCheckArr.push($DW.$("Cols"));
		noCheckArr.push($DW.$("Rows"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.htmlInput==inputType){
		noCheckArr.push($DW.$("Cols"));
		noCheckArr.push($DW.$("Rows"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
		var HTML = $DW.getHTML();
		if(HTML.trim()==""||HTML.trim()=="<br/>"){
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
	}else{
		dc.add("IsMandatory",$DW.$NV("IsMandatory"));
	}
	Server.sendRequest("com.nswt.cms.dataservice.MemberField.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});
}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "�޸Ļ�Ա��չ�ֶ�";
	diag.URL = "DataService/MemberFieldDialog.jsp?Code="+arr[0];
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ��������",function(){
		var dc = new DataCollection();	
		dc.add("Codes",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.MemberField.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				DataGrid.loadData("dg1");
			}
		});
	},function(){
		return;
	});
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head>
<body>
<table width="100%" border="0" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 10px;">
                <z:tbutton onClick="add()"><img src="../Icons/icon025a2.gif" />�½�</z:tbutton> 
                <z:tbutton onClick="edit()"><img src="../Icons/icon025a4.gif" />�޸�</z:tbutton> 
                <z:tbutton onClick="del()"><img src="../Icons/icon025a3.gif" />ɾ��</z:tbutton>
                </td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.cms.dataservice.MemberField.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo"><b>���</b></td>
							<td width="3%" ztype="selector" field="Code">&nbsp;</td>
							<td width="22%"><b>�ֶ�����</b></td>
							<td width="30%"><b>�ֶδ���</b></td>
                            <td width="8%"><b>������ʽ</b></td>
                            <td width="8%"><b>У������</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC"
                            onDblClick="edit();">
							<td height="22" align="center">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td>${Code}</td>
                            <td>${InputTypeName}</td>
                            <td>${VerifyTypeName}</td>
						</tr>
						<tr ztype="pagebar">
							<td height="25" colspan="8">${PageBar}</td>
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