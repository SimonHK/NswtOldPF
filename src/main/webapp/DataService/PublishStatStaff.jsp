<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.cms.dataservice.PublishStaff.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=gb2312" />
<title>��Ա����ͳ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statStaff(){
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function exportStaff(){
	DataGrid.toExcel("dg1",1);
}

function statInputor(username){
	document.body.style.overflow="hidden";
	document.getElementById("InputorStat").src="PublishStatInputor.jsp?Username="+username;
	document.getElementById("InputorStat").style.display="block";
	document.getElementById("StaffStat").style.display="none";
}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:300px">
						ͳ��ʱ�䣺��
						<input value="${today}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						��<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />ͳ��</z:tbutton>
						<z:tbutton onClick="statAll()"> <img src="../Icons/icon031a15.gif" />ȫ��ͳ��</z:tbutton>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />����EXCEL</z:tbutton>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.dataservice.PublishStaff.dg1DataBind">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><b>���</b></td>
								<td width="17%">����</td>
								<td width="20%">¼���û�</td>
								<td width="8%" ><b>������</b></td>
								<td width="8%" ><b>��ת��</b></td>
								<td width="8%" ><b>��������</b></td>
								<td width="8%" ><b>�ѷ�����</b></td>
								<td width="8%" ><b>��������</b></td>
								<td width="8%" ><b>�ѹ鵵��</b></td>
								<td width="8%" ><b>��������</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${BranchName}</td>
								<td><a href="#" onClick="statInputor('${UserName}')">${UserName}(${Inputor})</a></td>
								<td>${DraftCount}</td>
								<td>${WorkflowCount}</td>
								<td>${ToPublishCount}</td>
								<td>${PublishCount}</td>
								<td>${OfflineCount}</td>
								<td>${ArchiveCount}</td>
								<td>${ArticleCount}</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>
</html>
</z:init>