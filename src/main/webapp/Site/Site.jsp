<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>应用</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var leftFrame ;
Page.onLoad(function(){
    /*
	// 权限控制
	leftFrame = parent.leftFrame;
	//if(!leftFrame.isBranchAdmin){
		$("addButton").className="divbtnDisabled";
		$("addButton").onclick="";
		$("delButton").className="divbtnDisabled";
		$("delButton").onclick="";
	}
	*/

});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 760;
	diag.Height = 420;
	diag.Title = "新建"+this.document.title;
	diag.URL = "Site/SiteDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建应用";
	diag.Message = "新建一个应用，并设置应用的名称、地址、默认模板文件等";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("MessageBoardFlag",$DW.$NV("MessageBoardFlag"));
	dc.add("AutoIndexFlag",$DW.$NV("AutoIndexFlag"));
	dc.add("AutoStatFlag",$DW.$NV("AutoStatFlag"));
	dc.add("BBSEnableFlag",$DW.$NV("BBSEnableFlag"));
	dc.add("ShopEnableFlag",$DW.$NV("ShopEnableFlag"));
	dc.add("CommentAuditFlag",$DW.$NV("CommentAuditFlag"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Site.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				window.parent.location.reload();
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("您确认要删除应用吗，删除后部分数据和文件将无法恢复？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Dialog.wait("正在清除应用数据");
		Server.sendRequest("com.nswt.cms.site.Site.del",dc,function(response){
			Dialog.endWait();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.parent.location.reload();
				}
			});
		});
	});
}


function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的应用！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0]; 
  	editDialog(dr.get("ID"));
}

function editDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 760;
	diag.Height = 420;
	diag.Title = "应用修改";
	diag.URL = "Site/SiteDialog.jsp?ID="+ID;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改应用";
	diag.Message = "设置应用名称、地址、默认模板文件等";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("MessageBoardFlag",$DW.$NV("MessageBoardFlag"));
	dc.add("AutoIndexFlag",$DW.$NV("AutoIndexFlag"));
	dc.add("AutoStatFlag",$DW.$NV("AutoStatFlag"));
	dc.add("BBSEnableFlag",$DW.$NV("BBSEnableFlag"));
	dc.add("ShopEnableFlag",$DW.$NV("ShopEnableFlag"));
	dc.add("CommentAuditFlag",$DW.$NV("CommentAuditFlag"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Site.save",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}

function exportSite(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要导出的应用！");
		return;
	}
	dr = drs[0]; 
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 120;
	diag.Title = "导出应用";
	diag.URL = "Site/SiteExportDialog.jsp";
	diag.OKEvent = function(){
		window.location = Server.ContextPath+"Site/SiteExport.jsp?ID="+dr.get("ID")+"&ExportMediaFile="+$DW.$V("ExportMediaFile");
		$D.close();
	}
	diag.onLoad = function(){
		$DW.$("Name").innerHTML = dr.get("Name");
	}
	diag.show();
}

function importSite(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 100;
	diag.Title = "导入应用";
	diag.URL = "Site/SiteImportStep1.jsp";
	diag.OKEvent = function(){
		$DW.doUpload();
	}
	diag.show();
}

function executeImportSite(){
	var dc = new DataCollection();
	var dr = $DW.$("dg1").getSelectedData().Rows[0];
	dc.add("ID",dr.get("ID"));
	if(dr.get("ID")=="0"){
		dc.add("Name",$DW.$V("Name"));
		dc.add("Alias",$DW.$V("Alias"));
		dc.add("URL",$DW.$V("URL"));
	}else{
		dc.add("Name",dr.get("Name"));
		dc.add("Alias",dr.get("Alias"));
		dc.add("URL",dr.get("URL"));
	}
	dc.add("FileName",$DW.$V("FileName"));
	Server.sendRequest("com.nswt.cms.site.Site.checkImportSite",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			$D.close();
			var taskID = response.get("TaskID");
			var p = new Progress(taskID,"正在导入应用数据...",700,150);
			p.show(function(){
				window.parent.location.reload();
			});
		}
	});
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	var order = $("dg1").DataSource.get(rowIndex-1,"OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = ds.get(i-1,"OrderFlag");
		dc.add("Type","Before");
	}else{
		dc.add("Type","After");
		target = $("dg1").DataSource.get(1,"OrderFlag");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.cms.site.Site.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}
</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF9900}
-->
</style>
</head>
<body scroll="no">
<z:init method="com.nswt.cms.site.Site.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon040a1.gif" width="20" height="20" /> 应用列表</td>
				</tr>
				<tr>
				  <td style="padding:0 8px 4px;">
					<z:tbutton onClick="add()"><img src="../Icons/icon040a2.gif" width="20" height="20" />新建应用</z:tbutton>
					<z:tbutton onClick="edit()"><img src="../Icons/icon040a4.gif" width="20" height="20" />编辑应用</z:tbutton>
					<%--<z:tbutton onClick="exportSite()"> <img src="../Icons/icon040a7.gif" width="20" height="20" />应用导出</z:tbutton>
                 	<z:tbutton onClick="importSite()"> <img src="../Icons/icon040a9.gif" width="20" height="20" />应用导入</z:tbutton>--%>
					<z:tbutton onClick="del()"><img src="../Icons/icon040a3.gif" width="20" height="20" />删除</z:tbutton>
					<%--<span class="notice" style="float:right;padding-top:3px;padding-bottom:3px;border-width:1px;margin-bottom:0px">应用群配置请使用&nbsp;<a href="../DataChannel/FromCatalog.jsp" style='color:#529214;font-weight:normal;text-decoration: underline'>应用群采集</a>&nbsp;与&nbsp;<a href="../DataChannel/DeployCatalog.jsp" style='color:#529214;font-weight:normal;text-decoration: underline'>应用群分发</a>&nbsp;</span>					</td>--%>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.site.Site.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" 
								afterdrag="afterRowDragEnd">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo" drag="true"><img
									src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="4%" ztype="selector" field="id">&nbsp;</td>
								<td width="7%"><b>应用ID</b></td>
								<td width="18%"><b>应用名称</b></td>
								<td width="15%"><b>artifactId</b></td>
								<td width="10%"><b>版本号</b></td>
								<td width="30%"><b>域名地址</b></td>
								<td width="12%"><b>应用描述</b></td>
							</tr>
							<tr onDblClick="editDialog(${ID})">
								<td width="4%">&nbsp;</td>
								<td width="4%">&nbsp;</td>
								<td>${ID}</td>
								<td>${Name}</td>
								<td>${Alias}</td>
								<td></td>
								<td>${URL}</td>
								<td>${Info}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="7" align="center">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</z:init>
</body>
</html>
