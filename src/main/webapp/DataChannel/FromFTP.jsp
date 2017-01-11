<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>FTP����ɼ�����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 300;
	diag.Title = "�½�FTP�ɼ�����";
	diag.URL = "DataChannel/FromFTPDialog.jsp";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½�FTP�ɼ�����";
	diag.Message = "�½�FTP�ɼ�����,���ԶԲɼ�Ŀ¼,�ɼ��ļ����͵Ƚ�������.";
	diag.ShowButtonRow = true;
	window.EditFlag = false;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	dc.add("Status",$DW.$NV("Status"));
	Server.sendRequest("com.nswt.datachannel.FromFTP.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message);
			$D.close();
			DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ��У�");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 320;
	diag.Title = "�޸�FTP�ɼ�����";
	diag.URL = "DataChannel/FromFTPDialog.jsp?ID="+arr[0];
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸�FTP�ɼ�����";
	diag.Message = "�޸�FTP�ɼ�����,���ԶԲɼ�Ŀ¼,�ɼ��ļ����͵Ƚ�������.";
	diag.ShowButtonRow = true;
	window.EditFlag = false;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	var dc = new DataCollection();	
	dc.add("IDs",arr.join());
	Dialog.confirm("ȷ��Ҫɾ����������",function(){
		Server.sendRequest("com.nswt.datachannel.FromFTP.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
	});
} 

function delResult(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��ղɼ����ݵ��У�");
		return;
	}
	Dialog.confirm("�ɼ�������ָ��ָ��URL���ص��ı��ļ���<br>ͼƬ�ļ�,<font color=red>�������Ѿ�ת����Ŀ������</font>��<br><br>ȷ��Ҫ��ո�����Ĳɼ�������",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.datachannel.FromWeb.delResult",dc,function(response){
			Dialog.alert(response.Message);
		});											  
	},null,350);
} 

function save(){
	Dialog.alert("���ܱ���!");
}

function execute(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���У�");
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",arr[0]);
	Dialog.confirm("ȷ��Ҫ������������",function(){
		Server.sendRequest("com.nswt.datachannel.FromFTP.execute",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
	});
}

function cancel(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���У�");
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",arr[0]);
	Dialog.confirm("ȷ��Ҫ��ֹ��������",function(){
		Server.sendRequest("com.nswt.datachannel.FromFTP.cancel",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
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
				<td valign="middle" class="blockTd"><img src="../Icons/icon019a13.gif" width="20" height="20" />FTP�ɼ������б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon019a2.gif" width="20" height="20" />�½�</z:tbutton> 
				<z:tbutton onClick="edit()"><img src="../Icons/icon019a4.gif" width="20" height="20" />�޸�</z:tbutton> 
				<z:tbutton onClick="del()"><img src="../Icons/icon019a3.gif" width="20" height="20" />ɾ��</z:tbutton> 
				<z:tbutton onClick="execute()"><img src="../Icons/icon403a12.gif" width="20" height="20" />ִ������</z:tbutton> 
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.datachannel.FromFTP.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo"><b>���</b></td>
							<td width="3%" ztype="selector" field="ID">&nbsp;</td>
							<td width="3%" ><b>ID</b></td>
							<td width="9%" ><b>��������</b></td>
							<td width="10%" ><b>FTP������ַ</b></td>
							<td width="5%" ><b>�˿ں�</b></td>
							<td width="15%" ><b>�ɼ�Ŀ¼</b></td>
							<td width="10%" ><b>������Ŀ</b></td>
							<td width="3%" ><b>�Ƿ�����</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF" onDblClick="edit()" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${ID}</td>
							<td>${Name}</td>
							<td>${FTPHost}</td>
							<td>${FTPPort}</td>
							<td>${GatherDrectory}</td>
							<td>${CataLogName}</td>
							<td>${StatusName}</td>
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
