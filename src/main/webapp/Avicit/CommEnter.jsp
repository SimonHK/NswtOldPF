<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>会员信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 250;
	diag.Title = "新增企业";
	diag.URL = "Avicit/CommEnterDialog.jsp";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建企业信息";
	diag.Message = "新建企业,添加企业各种信息";
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
	  return;
     }
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.nswt.project.avicit.CommEnter.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
		});
	});
}

function edit(ID){
    var arr = DataGrid.getSelectedValue("dg1");
	if(ID&&ID!=""){
		arr = new Array();
		arr[0] = ID;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要修改的企业！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 250;
	diag.Title = "企业信息修改";
	diag.URL = "Avicit/CommEnterDialog.jsp?ID="+arr[0];
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改企业信息";
	diag.Message = "修改企业信息";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.project.avicit.CommEnter.add",dc,function(){
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
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除选中企业吗？",function(){
		var dc = new DataCollection();	
		dc.add("ID",arr.join());
		Server.sendRequest("com.nswt.project.avicit.CommEnter.del",dc,function(){
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

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr><td>
                    <z:tbutton onClick="add()"><img src="../Icons/icon038a2.gif" />新建</z:tbutton> 
                    <z:tbutton onClick="edit()"><img src="../Icons/icon038a4.gif" />修改</z:tbutton> 
                    <z:tbutton onClick="del()"><img src="../Icons/icon038a3.gif" />删除</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.project.avicit.CommEnter.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="ID"></td>
								<td width="16%"><b>企业名称</b></td>
								<td width="18%"><b>省市</b></td>
								<td width="13%"><b>法人</b></td>
								<td width="12%"><b>承诺时间</b></td>
								<td width="8%"><b>承诺书</b></td>
								
							</tr>
							<tr onDblClick="edit('${ID}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td>&nbsp;</td>
								<td width="4%">&nbsp;</td>
								<td>${FullName}</td>
								<td>${PlaceName}</td>
								<td>${LegalPerson}</td>
								<td>${CommDate}</td>
								<td><a href="${SiteUrl}${CommWordUrl}">${CommWord}</a></td>
								
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="7">${PageBar}</td>
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
