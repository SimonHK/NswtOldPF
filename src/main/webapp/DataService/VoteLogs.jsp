<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">
showDetails = function(ID) {
	var diag = new Dialog("VoteResultDetails");
	diag.Width = 600;
	diag.Height = 400;
	diag.URL = "DataService/VoteResultDetails.jsp?ID=" + ID;
	diag.ShowButtonRow = false;
	diag.show();
}
</script>
</head>
<body>
<input type='hidden' id='ID' name='ID' value='<%=request.getParameter("ID")%>'>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon032a1.gif" />ͶƱ��ϸ�б�˫���鿴��</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.nswt.cms.dataservice.Vote.dg2DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="8%" ztype="RowNo">���</td>
								<td width="8%" align="center" ztype="selector" field="id">&nbsp;</td>
								<td width="15%">IP</td>
								<td width="15%">ͶƱ��</td>
								<td width="20%">ͶƱʱ��</td>
								<td width="34%">��д�����ݣ��Զ��Ÿ�����</td>
							</tr>
							<tr onDblClick="showDetails('${ID}');">
								<td align="center">&nbsp;</td>
								<td align="center">&nbsp;</td>
								<td>${IP}</td>
								<td>${addUser}</td>
								<td>${addTime}</td>
								<td title="${OtherContents}">
									<a href="javascript:void(0)" onClick="showDetails('${ID}');">
										${OtherContents}
									</a>
								</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="12" align="center">${PageBar}</td>
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