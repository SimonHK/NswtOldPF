<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ա��Ϣ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 360;
	diag.Title = "������Ա";
	diag.URL = "DataService/MemberDialog.jsp";
	diag.onLoad = function(){
		$DW.$("UserName").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½���Ա��Ϣ";
	diag.Message = "�½���Ա�����������������ۿ۵�";
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
	  return;
     }
	var dc = $DW.Form.getData("form2");
	dc.add("Gender",$DW.$NV("Gender"));
	Server.sendRequest("com.nswt.cms.dataservice.Member.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
		});
	});
}

function edit(UserName){
    var arr = DataGrid.getSelectedValue("dg1");
	if(UserName&&UserName!=""){
		arr = new Array();
		arr[0] = UserName;
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�༭�Ļ�Ա��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 360;
	diag.Title = "��Ա��Ϣ�޸�";
	diag.URL = "DataService/MemberEditDialog.jsp?UserName="+arr[0];
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸Ļ�Ա��Ϣ";
	diag.Message = "�޸Ļ�Ա�����������������ۿ۵�";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("Gender",$DW.$NV("Gender"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.dataservice.Member.dg1Edit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ��ѡ�еĻ�Ա��",function(){
		var dc = new DataCollection();	
		dc.add("UserNames",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.Member.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
	});
} 

function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Status",$V("Status"));
	DataGrid.setParam("dg1","SearchUserName",$V("SearchUserName"));
	DataGrid.setParam("dg1","Search","search");
	DataGrid.loadData("dg1");
}

function doCheck(status){
	if(status==""){
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��˵Ļ�Ա��");
		return;
	}
	var dc = new DataCollection();	
	dc.add("UserNames",arr.join(","));
	dc.add("Status",status);
	Server.sendRequest("com.nswt.cms.dataservice.Member.doCheck",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			DataGrid.loadData('dg1');
		}
	});
}

function checkAddress(username){
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 320;
	diag.Title = "��Ա��ַ�б�";
	diag.URL = "DataService/MemberAddr.jsp?UserName="+username;
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />��Ա�б�</td>
				</tr>
				<tr>
					<td style="padding:8px 10px;">
                    <div style="float: right">״̬��
                    <z:select id="Status" name="Status" style="width:80px" listWidth="80" onChange="doSearch();">
					<select><option value=""></option>
						<option value="X">�ȴ����</option>
						<option value="Y">���ͨ��</option>
						<option value="N">��˲�ͨ��</option></select>
					</z:select>                    
                    ��¼����<input name="SearchUserName" type="text" id="SearchUserName" value="" style="width:90px"> 
                    <input type="button" name="Submit" value="��ѯ" onClick="doSearch()"></div>
                    <z:tbutton onClick="add()"><img src="../Icons/icon021a2.gif" />�½�</z:tbutton> 
                    <z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif" />�޸�</z:tbutton> 
                    <z:tbutton onClick="del()"><img src="../Icons/icon021a3.gif" />ɾ��</z:tbutton>
                    <z:tbutton onClick="doCheck('Y')"><img src="../Icons/icon404a4.gif" width="20" height="20" />���ͨ��</z:tbutton>
                    <z:tbutton onClick="doCheck('N')"><img src="../Icons/icon404a3.gif" width="20" height="20" />��ͨ��</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.dataservice.Member.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><strong>���</strong></td>
								<td width="3%" ztype="selector" field="UserName">&nbsp;</td>
								<td width="16%"><b>��¼��</b></td>
								<td width="18%"><b>����</b></td>
								<td width="13%"><b>��Ա����</b></td>
								<td width="12%"><b>��Ա����</b></td>
								<td width="8%"><b>�Ա�</b></td>
								<td width="8%"><b>����</b></td>
                                <td width="8%"><b>��ַ����</b></td>
								<td width="10%"><b>���״̬</b></td>
							</tr>
							<tr onDblClick="edit('${UserName}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${UserName}</td>
								<td>${Name}</td>
								<td>${TypeName}</td>
								<td>${MemberLevelName}</td>
								<td>${GenderName}</td>
								<td>${Score}</td>
                                <td><a href="#;" onClick="checkAddress('${UserName}');">��ַ����</a></td>
								<td>${StatusName}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
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
