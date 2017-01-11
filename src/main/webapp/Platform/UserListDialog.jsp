<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Array.prototype.unique = function(){  
 var  a = {};
 for(var i=0; i <this.length; i++){  
    if(typeof a[this[i]] == "undefined")  
      a[this[i]] = 1;  
 }  
 this.length = 0;  
 for(var i in a)  
    this[this.length] = i;  
 return this;  
}  

function add(){
	var diag  = new Dialog("Diag3");
	diag.Width = 750;
	diag.Height = 360;
	diag.Title ="浏览用户";
	diag.URL = "Platform/UserSelectDialog.jsp";
	diag.OKEvent = getUserList;
	diag.show();
}

function getUserList(){
	var UserNames = $DW.getSelectUser();
	if(!UserNames||UserNames==null||UserNames==""){
		return;
	}
	
	var allUsers;
	var allRealNames;
    var oldUsers = $V("UserNames");
	var oldRealNames = $V("RealNames");
    if(oldUsers != ""){
	    oldUsers = oldUsers.split(",");	
	    allUsers = oldUsers.concat(UserNames[0]).unique();
    }else{
	  allUsers = UserNames[0].split(",");
    }
	if(oldRealNames != ""){
	    oldRealNames = oldRealNames.split(",");	
	    allRealNames = oldRealNames.concat(UserNames[1]).unique();
    }else{
	  allRealNames = UserNames[1].split(",");
    }
	$S("UserNames",allUsers.join(","));
	$S("RealNames",allRealNames.join(","));
	DataGrid.setParam("dg1","SelectedUser",$V("UserNames"));
    DataGrid.loadData("dg1");
	$D.close();
}

function deleteRow(UserName,RealName){
	var UserNames = $V("UserNames");
	UserNames = UserNames.split(",");	
	UserNames.remove(UserName);
	$S("UserNames",UserNames.join(","));
	updateRealName(UserNames.join(","));
	DataGrid.setParam("dg1","SelectedUser",$V("UserNames"));
  	DataGrid.loadData("dg1");
}

function del(){
  	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		return;
	}
	
	var UserNames = $V("UserNames");
	UserNames = UserNames.split(",");	
	var RealNames = $V("RealNames");
	RealNames = RealNames.split(",");
	for(var i = 0;i<arr.length;i++){
		UserNames.remove(arr[i]);
	}
	$S("UserNames",UserNames.join(","));
	updateRealName(UserNames.join(","));
	DataGrid.setParam("dg1","SelectedUser",$V("UserNames"));
  	DataGrid.loadData("dg1");
}

function updateRealName(UserNames){
	var dc = new DataCollection();
	dc.add("UserNames",UserNames);
	Server.sendRequest("com.nswt.platform.UserSelect.getRealNames",dc,function(response){
		$S("RealNames",response.Message);
	});
}

function getSelectUser(){
	var arr = [];
	arr.push($V("UserNames"));
	arr.push($V("RealNames"));
	return arr;
}

</script>
</head>
<body>
<z:init method="com.nswt.platform.UserSelect.init">
<input type="hidden" id="UserNames" name="UserNames" value="${UserNames}"/>
<input type="hidden" id="RealNames" name="RealNames" value="${RealNames}"/>
<table width="100%" border="0" cellspacing="4" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon018a1.gif" />已选择用户列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />添加</z:tbutton> 
				<z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.platform.UserSelect.selectedUserDataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo"></td>
							<td width="6%" ztype="selector" field="UserName">&nbsp;</td>
							<td width="25%"><b>用户名</b></td>
                            <td width="27%"><b>真实姓名</b></td>
                            <td width="27%"><b>所属机构</b></td>
							<td width="10%"><b>操作</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td height="15">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${UserName}</td>
                            <td>${RealName}</td>
                            <td>${BranchName}</td>
							<td><a href="#;" onClick="deleteRow('${UserName}','${RealName}')">删除</a></td>
						</tr>
					</table>
					</z:datagrid>
                </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</z:init>
</body>
</html>
