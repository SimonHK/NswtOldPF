<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�Զ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 380;
	diag.Height = 310;
	diag.Title = "�½��ֶ�";
	diag.URL = "DataService/CustomColumnItemDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = Save;
	diag.show();
}
function Save(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	if(($DW.$V("ShowMod")=="1" || $DW.$V("ShowMod")=="2")&&$DW.$V("ListOpt")!=""){
		Dialog.alert("�����ı�������ı�����Ҫ��д�б�ѡ��!");
		return;
	}
	dc.add("ClassCode",$V("ClassCode"));	
	Server.sendRequest("com.nswt.platform.CustomColumnItem.save",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				$D.close();
				window.location.reload();	
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}
function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 380;
	diag.Height = 310;
	diag.Title = "�޸��ֶ�";
	diag.URL = "DataService/CustomColumnItemDialog.jsp";
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
	};
	diag.OKEvent = Save;
	diag.show();
}
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	
	Dialog.alert("ȷ��Ҫɾ�����ֶ���",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.platform.CustomColumnItem.del",dc,function(response){
			if(response.Status==1){
				Dialog.alert("ɾ���ɹ�",function(){
					window.location="DataService/CustomColumnItem.jsp?ClassCode=" + $V("ClassCode");
				});
			}
			else{
				Dialog.alert(response.Message);
			}
		});
	})
}
function back(){
	window.location="CustomColumn.jsp";
}
</script>
</head>
<body>
<z:init method="com.nswt.platform.CustomColumnItem.init">
<input name="ClassCode" id="ClassCode" value="${ClassCode}"
		type="hidden" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon002a1.gif" /> ${ClassCode} �Զ����б�</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()">
						<img src="../Icons/icon024a2.gif" />����</z:tbutton> <z:tbutton onClick="del()">
						<img src="../Icons/icon024a3.gif" />ɾ��</z:tbutton> <z:tbutton
						onClick="back()">
						<img src="../Icons/icon400a8.gif" />����</z:tbutton></td>
				</tr>
				<tr>   <td style="padding:0px 5px;">
<z:datagrid id="dg1"
			method="com.nswt.platform.CustomColumnItem.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo">&nbsp;</td>
								<td width="4%" ztype="selector" field="id">&nbsp;</td>
								<td width="10%"><b>ҵ������</b></td>
								<td width="17%"><b>�ֶ�����</b></td>
								<td width="16%"><b>�ֶδ���</b></td>
								<td width="12%"><b>��������</b></td>
								<td width="12%"><b>�ֶγ���</b></td>
								<td width="10%"><b>��ʾ��ʽ</b></td>
								<td width="10%"><b>�Ƿ�ǿ�</b></td>
								<td width="5%" ztype="editDialog" function='editDialog'><b>�༭</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td  width="5">&nbsp;</td>
								<td width="20">&nbsp;</td>
								<td>${ClassCode}</td>
								<td>${Name}</td>
								<td>${Code}</td>
								<td>${Type}</td>
								<td>${Length}</td>
								<td>${ShowMod}</td>
								<td>${MandatoryFlag}</td>
								<td>&nbsp;</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="10" align="center">${PageBar}</td>
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
</z:init>
