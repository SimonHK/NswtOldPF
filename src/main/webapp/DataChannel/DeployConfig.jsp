<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ʱ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 320;
	diag.Title = "�½�����";
	diag.URL = "DataChannel/DeployConfigDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}

  dc.add("Method",$DW.$NV('Method'));
  dc.add("UseFlag",$DW.$NV("UseFlag"));
	Server.sendRequest("com.nswt.cms.datachannel.DeployConfig.add",dc,function(response){
		if(response.Status==1){
			$D.close();
			DataGrid.setParam("dg1",Constant.PageIndex,0);
      DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ����ã�");
		return;
	}
	if(drs.length>1){
		Dialog.alert("ֻ���޸�1����Ϣ��");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}


function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 320;
	diag.Title = "�޸�����";
	diag.URL = "DataChannel/DeployConfigDialog.jsp";
	diag.onLoad = function(){
    $DW.$S("ID",dr.get("ID"));
		$DW.$S("SourceDir",dr.get("SourceDir"));
		$DW.$S("TargetDir",dr.get("TargetDir"));
		$DW.$S("Host",dr.get("Host"));
		$DW.$S("Port",dr.get("Port"));
		$DW.$S("UserName",dr.get("UserName"));
		$DW.$S("Password",dr.get("Password"));
    $DW.$NS("Method",dr.get("Method"));
		$DW.$NS("UseFlag",dr.get("UseFlag"));
		$DW.$("SourceDir").focus();
		
		$DW.changeMethod();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}

  dc.add("Method",$DW.$NV('Method'));
  dc.add("UseFlag",$DW.$NV("UseFlag"));
	Server.sendRequest("com.nswt.cms.datachannel.DeployConfig.edit",dc,function(response){
		if(response.Status==1){
			Dialog.alert("�޸ĳɹ�",function(){
				 DataGrid.loadData("dg1");
         $D.close();
			});
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ������������",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.datachannel.DeployConfig.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("ɾ���ɹ�");
        DataGrid.loadData("dg1");
			}
		});
	});
} 

function deploy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�ַ����У�");
		return;
	}
	Dialog.confirm("�ַ�����ɨ��Ŀ¼��ȷ��Ҫ����ִ��ѡ�е�������",function(){
			var dc = new DataCollection();	
			dc.add("IDs",arr.join());
			Server.sendRequest("com.nswt.cms.datachannel.DeployConfig.deploy",dc,function(response){
				if(response.Status==0){
					Dialog.alert(response.Message);
				}else{
					Dialog.alert("�Ѿ���������ӵ������У���鿴ִ����־��");
	        DataGrid.loadData("dg1");
				}
			});
	});
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="1">
	<tr>
		<td style="padding:0 0 6px;">
		<z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />�½�</z:tbutton> 
		<z:tbutton onClick="edit()"><img src="../Icons/icon018a4.gif" />�༭</z:tbutton> 
		<z:tbutton onClick="deploy()"><img src="../Icons/icon018a4.gif" />�����ַ�</z:tbutton> 
		<z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton></td>
	</tr>
	<tr>
		<td>
		<z:datagrid id="dg1" method="com.nswt.cms.datachannel.DeployConfig.dg1DataBind" size="14">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="6%" ztype="RowNo">&nbsp;</td>
					<td width="6%" ztype="selector" field="id">&nbsp;</td>
					<td width="13%"><b>���Ʒ�ʽ</b></td>
					<td width="26%"><b>����Ŀ¼</b></td>
					<td width="18%"><b></b>��������ַ</td>
					<td width="19%"><b></b>Ŀ��Ŀ¼</td>
					<td width="12%"><b>����״̬</b></td>
				</tr>
				<tr onDblClick="edit()">
					<td align="center">&nbsp;</td>
					<td>&nbsp;</td>
					<td>${MethodName}</td>
					<td>${SourceDir}</td>
					<td>${Host}</td>
					<td>${TargetDir}</td>
					<td>${UseFlagName}</td>
				</tr>
				<tr ztype="pagebar">
					<td colspan="7">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</body>
</html>
