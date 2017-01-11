<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�������</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script language="javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("CodeType",$V("CodeType"));
		dr.set("CodeName",$V("CodeName"));
		dr.set("Memo",$V("Memo"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.nswt.platform.Code.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "�½��������";
	diag.URL = "Platform/CodeDialog.jsp";
	diag.onLoad = function(){
		$DW.$("CodeType").focus();
		$DW.$("tr_CodeValue").style.display="none";
		$DW.$("CodeValue").setAttribute("verify","");
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("ParentCode","System");
	dc.add("CodeValue","System");
	Server.sendRequest("com.nswt.platform.Code.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
					$D.close();
					DataGrid.loadData('dg1');
			}	
		});
	});
}

function del(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.Values.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("DT",dt);
	Dialog.confirm("��ȷ��Ҫɾ����",function(){
		Server.sendRequest("com.nswt.platform.Code.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchCodeType'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchCodeType") == "�����������������") {
		$S("SearchCodeType","");
	}
}

function doSearch(){
	var searchName = "";
	if($V("SearchCodeType") != "�����������������"){
		searchName = $V("SearchCodeType").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchCodeType",searchName);
	DataGrid.loadData("dg1");
}

function codeList(codeType,codeName){
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 380;
	diag.Title = "����";
	diag.URL = "Platform/CodeList.jsp?CodeType="+codeType+"&CodeName="+codeName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon023a6.gif" /> �����б�</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()">
						<img src="../Icons/icon018a2.gif" />�½�</z:tbutton> <z:tbutton
						onClick="save()">
						<img src="../Icons/icon018a4.gif" />����</z:tbutton> <z:tbutton onClick="del()">
						<img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton>
						<div style="float: right; white-space: nowrap;"><input type="text" name="SearchCodeType"
						id="SearchCodeType" value="�����������������" onFocus="delKeyWord();" style="width:150px"> 
						<input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="doSearch()"></div></td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.platform.Code.dg1BindCode"
						size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo">���</td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="18%"><b>�������</b></td>
								<td width="24%"><b>��������</b></td>
								<td width="28%">��ע</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><span class="fc_bl3"><a href="javascript:void(0);" onClick="codeList('${CodeType}','${CodeName}');">${CodeType}</a></span></td>
								<td>${CodeName}</td>
								<td>${Memo}</td>
							</tr>
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td><input name="CodeType" type="CodeType" id="CodeType"
									value="${CodeType}" size="20"></td>
								<td><input name="CodeName" type="text" id="CodeName"
									value="${CodeName}" size="20"></td>
								<td><input name="Memo" type="text" id="Memo"
									value="${Memo}" size="20"></td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="5">${PageBar}</td>
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
