<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>FTP任务采集配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 300;
	diag.Title = "新建FTP采集任务";
	diag.URL = "DataChannel/FromFTPDialog.jsp";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建FTP采集任务";
	diag.Message = "新建FTP采集任务,可以对采集目录,采集文件类型等进行设置.";
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
		Dialog.alert("请先选择要修改的行！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 320;
	diag.Title = "修改FTP采集任务";
	diag.URL = "DataChannel/FromFTPDialog.jsp?ID="+arr[0];
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改FTP采集任务";
	diag.Message = "修改FTP采集任务,可以对采集目录,采集文件类型等进行设置.";
	diag.ShowButtonRow = true;
	window.EditFlag = false;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();	
	dc.add("IDs",arr.join());
	Dialog.confirm("确定要删除该任务吗？",function(){
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
		Dialog.alert("请先选择要清空采集数据的行！");
		return;
	}
	Dialog.confirm("采集数据是指从指定URL下载的文本文件和<br>图片文件,<font color=red>不包括已经转入栏目的文章</font>。<br><br>确定要清空该任务的采集数据吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.datachannel.FromWeb.delResult",dc,function(response){
			Dialog.alert(response.Message);
		});											  
	},null,350);
} 

function save(){
	Dialog.alert("不能保存!");
}

function execute(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择行！");
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",arr[0]);
	Dialog.confirm("确定要启动该任务吗？",function(){
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
		Dialog.alert("请先选择行！");
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",arr[0]);
	Dialog.confirm("确定要终止该任务吗？",function(){
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
				<td valign="middle" class="blockTd"><img src="../Icons/icon019a13.gif" width="20" height="20" />FTP采集任务列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon019a2.gif" width="20" height="20" />新建</z:tbutton> 
				<z:tbutton onClick="edit()"><img src="../Icons/icon019a4.gif" width="20" height="20" />修改</z:tbutton> 
				<z:tbutton onClick="del()"><img src="../Icons/icon019a3.gif" width="20" height="20" />删除</z:tbutton> 
				<z:tbutton onClick="execute()"><img src="../Icons/icon403a12.gif" width="20" height="20" />执行任务</z:tbutton> 
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.datachannel.FromFTP.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo"><b>序号</b></td>
							<td width="3%" ztype="selector" field="ID">&nbsp;</td>
							<td width="3%" ><b>ID</b></td>
							<td width="9%" ><b>任务名称</b></td>
							<td width="10%" ><b>FTP主机地址</b></td>
							<td width="5%" ><b>端口号</b></td>
							<td width="15%" ><b>采集目录</b></td>
							<td width="10%" ><b>所属栏目</b></td>
							<td width="3%" ><b>是否启用</b></td>
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
