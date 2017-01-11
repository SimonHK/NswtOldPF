<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.cms.dataservice.PublishStaff.initInputorColumn">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=gb2312" />
<title>��Ŀ����ͳ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statInputorColumn(){
	DataGrid.setParam("dg12","ColumnInputor",$V("ColumnInputor"));
	DataGrid.setParam("dg12","CatalogInnerCode",$V("CatalogInnerCode"));
	DataGrid.setParam("dg12","startDate",$V("startDate"));
	DataGrid.setParam("dg12","endDate",$V("endDate"));
	DataGrid.setParam("dg12",Constant.PageIndex,0);
	DataGrid.loadData("dg12");
}

function statInputorColumnAll(){
	DataGrid.setParam("dg12","ColumnInputor",$V("ColumnInputor"));
	DataGrid.setParam("dg12","CatalogInnerCode",$V("CatalogInnerCode"));
	DataGrid.setParam("dg12","startDate","");
	DataGrid.setParam("dg12","endDate","");
	DataGrid.setParam("dg12",Constant.PageIndex,0);
	DataGrid.loadData("dg12");
}

function exportInputorColumn(){
	DataGrid.toExcel("dg12",0);
}

</script>
</head>
<body>
	<input type="hidden" id="ID" value="${ID}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				
				<tr>
					<td style="padding:4px 8px;" class="blockTd"><b>${RealName}&nbsp;&nbsp;������Ŀ&nbsp;&nbsp;"${CatalogName}"&nbsp;&nbsp;����ͳ��</b></td>
				</tr>
				<tr>
					<td style="padding:4px 8px;">
						<span style="float: left;width:300px">
						ͳ��ʱ�䣺��
						<input value="${today}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						��<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						
						<z:tbutton onClick="statInputorColumn()"> <img src="../Icons/icon031a1.gif" />ͳ��</z:tbutton>
						<z:tbutton onClick="statInputorColumnAll()"> <img src="../Icons/icon031a15.gif" />ȫ��ͳ��</z:tbutton>
						<z:tbutton onClick="exportInputorColumn()"> <img src="../Icons/icon031a7.gif" />����EXCEL</z:tbutton>
						
						<input type="hidden" name="ColumnInputor" id="ColumnInputor" value="${ColumnInputor}" />
						<input type="hidden" name="CatalogID" id="CatalogID" value="${CatalogID}" />
						<input type="hidden" name="CatalogInnerCode" id="CatalogInnerCode" value="${CatalogInnerCode}" />
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg12" method="com.nswt.cms.dataservice.PublishStaff.dg12DataBind" size="14">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><b>���</b></td>
								<td width="9%">��Ŀ</td>
								<td width="7%">���</td>
								<td width="31%">����</td>
								<td width="9%">״̬</td>
								<td width="18%">�޸�ʱ��</td>
								<td width="21%">����ʱ��</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td width="9%">${CatalogName}</td>
								<td>${ID}</td>
								<td><a href="../Document/Preview.jsp?ArticleID=${ID}" target="_blank" title="${Title}">${Title}</a></td>
								<td>${StatusName}</td>
								<td>${LastModifyTime}</td>
								<td>${PublishDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="6">${PageBar}</td>
								<td></td>
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
</z:init>