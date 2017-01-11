<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�û�</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 380;
	diag.Title = "�½�"+this.document.title;
	diag.URL = "Platform/UserAddDialog.jsp";
	diag.onLoad = function(){
		$DW.$NS("IsBranchAdmin","N");
		$DW.$NS("Status","N");
		$DW.$S("Password","");
		$DW.$("UserName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	if ($DW.$V("ConfirmPassword") != $DW.$V("Password")){
	   Dialog.alert("�����������벻��ͬ������������...");
	   return ;
	}
	
	Server.sendRequest("com.nswt.platform.UserList.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				Dialog.alert("����ӵ��û�������ͬ����OAϵͳ");
				$D.close();
				DataGrid.loadData('dg1');
			}
		});	
	});
}

function save(){
	DataGrid.save("dg1","com.nswt.platform.UserList.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����û���");
		return;
	}
	Dialog.confirm("��ȷ��Ҫɾ����Щ�û���</br><b style='color:#F00'>"+arr.join(',</br>')+"</b>",function(){
		var dc = new DataCollection();
		dc.add("UserNames",arr.join());
		Server.sendRequest("com.nswt.platform.UserList.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					Dialog.alert("��ɾ�����û�������ͬ����OAϵͳ");
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function stopUser(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫͣ�õ��û���");
		return;
	}
	Dialog.confirm("��ȷ��Ҫͣ����Щ�û���</br><b style='color:#F00'>"+arr.join(',</br>')+"</b>",function(){
		var dc = new DataCollection();
		dc.add("UserNames",arr.join());
		Server.sendRequest("com.nswt.platform.UserList.stopUser",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					Dialog.alert("���޸ĵ��û�������ͬ����OAϵͳ");
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function setPriv(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�༭���û���");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "�����û�"+arr[0]+"Ȩ��";
	diag.URL = "Platform/UserTab.jsp?UserName="+arr[0];
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�"; 
}

function doSearch(){
	var name = "";
	if ($V("SearchUserName") != "�������û�������ʵ����") {
		name = $V("SearchUserName").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchUserName",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchUserName'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchUserName") == "�������û�������ʵ����") {
		$S("SearchUserName","");
	}
}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�༭���û���");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 380;
	diag.Title = "�༭�û�"+arr[0];
	diag.URL = "Platform/UserEditDialog.jsp?UserName="+arr[0];
	diag.onLoad = function(){
		$DW.$("RealName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
    if ($DW.$V("NewConfirmPassword") != $DW.$V("NewPassword")){
	   Dialog.alert("�����������벻��ͬ������������",function(){
	       $DW.$S("NewPassword","");
		   $DW.$S("NewConfirmPassword","");
		   return;
	   });
	   return;
	}
	Server.sendRequest("com.nswt.platform.UserList.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				Dialog.alert("���޸ĵ��û�������ͬ����OAϵͳ");
				$D.close();
				DataGrid.loadData("dg1");
			}
		})
	});
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
						src="../Icons/icon021a1.gif" /> �û��б�</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon021a2.gif"/>�½�</z:tbutton>
				<z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif"/>�༭</z:tbutton>
				<z:tbutton onClick="stopUser()"><img src="../Icons/icon021a8.gif"/>ͣ��</z:tbutton>
				<z:tbutton onClick="del()"><img src="../Icons/icon021a3.gif"/>ɾ��</z:tbutton>
				<z:tbutton onClick="setPriv()"><img src="../Icons/icon021a9.gif"/>�޸�Ȩ��</z:tbutton>				
				<div style="float: right; white-space: nowrap;">
				  <input name="SearchUserName" type="text" id="SearchUserName" value="�������û�������ʵ����" onFocus="delKeyWord();" style="width:150px">
            	  <input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="doSearch()">
            	</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.platform.UserList.dg1DataBind" size="15">
				     <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
				   	        <td width="4%" ztype="RowNo">���</td>
					        <td width="4%" ztype="selector" field="UserName">&nbsp;</td>
				            <td width="12%" ><b>�û���</b></td>
							<td width="10%" ><b>��ʵ����</b></td>
							<td width="8%" ><b>�û�״̬</b></td>
							<td width="18%" ><b>��������</b></td>
                            <td width="42%" ><b>������ɫ</b></td>
					    </tr>
					    <tr onDblClick="edit(this)" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
				          <td >&nbsp;</td>
					      <td>&nbsp;</td>
						  <td title="${UserName}">${UserName}</td>
						  <td title="${RealName}">${RealName}</td>
						  <td title="${StatusName}">${StatusName}</td>
						  <td title="${BranchInnercodeName}">${BranchInnercodeName}</td>
                          <td title="${RoleNames}">${RoleNames}</td>
					    </tr>
						<tr ztype="pagebar">
						  <td colspan="10" align="center">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid>
				</td>
			</tr>
		</table> 
      	</td>
      </tr>
    </table>
</body>
</html>
