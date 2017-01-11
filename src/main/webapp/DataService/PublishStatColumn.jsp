<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=gb2312" />
<title>��Ŀ����ͳ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statColumn(){
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function statColumnAll(){
	DataGrid.setParam("dg2","startDate","");
	DataGrid.setParam("dg2","endDate","");
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function exportColumn(){
	DataGrid.toExcel("dg2",0);
}
</script>
</head>
<body>
	<input type="hidden" id="ID" value="${ID}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<z:init method="com.nswt.cms.dataservice.PublishColumn.initSearch">
						<span style="float: left;width:300px">
						ͳ��ʱ�䣺��
						<input value="${today}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						��<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						
						<z:tbutton onClick="statColumn()"> <img src="../Icons/icon031a1.gif" />ͳ��</z:tbutton>
						<z:tbutton onClick="statColumnAll()"> <img src="../Icons/icon031a15.gif" />ȫ��ͳ��</z:tbutton>
						<z:tbutton onClick="exportColumn()"> <img src="../Icons/icon031a7.gif" />����EXCEL</z:tbutton>
						
						</z:init>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.nswt.cms.dataservice.PublishStaff.dg11DataBind" page="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><b>���</b></td>
								<td width="7%">��ĿID</td>
								<td width="33%"  ztype="tree" level="treelevel" >��Ŀ����</td>
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
								<td >${ID}</td>
								<td >${CatalogName}</td>
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
</body>

</html>
