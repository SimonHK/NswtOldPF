<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.platform.UserLog.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�û���־</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	var name = "";
	name = $V("SearchUser").trim();
	var ip = "";
	ip = $V("IP").trim();
	var logMessage = "";
	logMessage = $V("LogMessage").trim();
	var startDate = "";
	startDate = $V("StartDate").trim();
	var endDate = "";
	endDate = $V("EndDate").trim();
	var logType = "";
	logType = $V("LogType").trim();
	var subType = "";
	subType = $V("SubType").trim();
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchUser",name);
	DataGrid.setParam("dg1","IP",ip);
	DataGrid.setParam("dg1","LogType",logType);
	DataGrid.setParam("dg1","SubType",subType);
	DataGrid.setParam("dg1","LogMessage",logMessage);
	DataGrid.setParam("dg1","StartDate",startDate);
	DataGrid.setParam("dg1","EndDate",endDate);
	DataGrid.loadData("dg1");
}

function changeLogType(){
	$("SubType").setParam("LogType",$V("LogType"));
	$("SubType").loadData();
}

function del(){
	var dc = new DataCollection();
	dc.add("month",$V("month"));
	Server.sendRequest("com.nswt.platform.UserLog.delByTime",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});	
}

function browse(logID,userName){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "��ϸ��Ϣ";
	diag.URL = "Platform/UserLogDialog.jsp?LogID="+logID+"&UserName="+userName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
}
</script>
</head>
<body>
<z:init method="com.nswt.platform.UserLog.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />�û���־				
				</td>
			</tr>
			<tr><td  style="padding-top:0px">
				��&nbsp;<input value="" type="text" id="StartDate" size="14" name="StartDate" ztype="Date"  class="inputText" verify="NotNull">
				��&nbsp;<input value="${Time}" type="text" id="EndDate" size="14" name="EndDate" ztype="Date"  class="inputText" verify="NotNull" >				
				IP��<input type="text" name="IP" id="IP" value="" style="width:65px">
				���ͣ�<z:select id="LogType" style="width:70px" onChange="changeLogType()">${LogType}</z:select>
				�����ͣ�<z:select id="SubType" style="width:70px" method="com.nswt.platform.UserLog.getSubType" lazy="true"></z:select>
			</td>
			</tr>
			<tr>
			  <td style="padding-top:0px">�û�����<input type="text" name="SearchUser" id="SearchUser"  value="" style="width:60px">��Ϣ���ݣ�
				<input type="text" name="LogMessage" id="LogMessage" value="" style="width:80px">
				<input type="button" name="Submit" value="��ѯ" onClick="doSearch()">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<z:select>
				<select name="select" id="month">
                  <option value="3">3����֮ǰ </option>
                  <option value="6">6����֮ǰ </option>
                  <option value="12">12����֮ǰ </option>
                  <option value="24">24����֮ǰ </option>
                </select>
				</z:select>
				<input name="button" type="button" onclick="del()" value="ɾ��"></td>
			</tr>
			<tr><td style="padding:0px 5px;">
				<z:datagrid id="dg1" method="com.nswt.platform.UserLog.dg1DataBind" page="true" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" align="center" ztype="selector" field="LogID">&nbsp;</td>
							<td width="8%"><b>�û���</b></td>
							<td width="8%"><b>����</b></td>
							<td width="16%"><strong>ʱ��</strong></td>
							<td width="10%"><strong>��Ϣ����</strong></td>
							<td width="39%"><strong>��Ϣ</strong></td>
							<td width="16%"><strong>IP</strong></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td align="center">&nbsp;</td>
							<td>${UserName}</td>
							<td>${RealName}</td>
							<td>${AddTime}</td>
							<td>${LogTypeName}</td>
							<td title="${LogMessage}"><a href="#" onclick="browse('${LogID}','${UserName}');">${LogMessage}</a></td>
							<td>${IP}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="8">${PageBar}</td>
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
</z:init>
