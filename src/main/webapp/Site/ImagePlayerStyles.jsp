<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ��������ʽ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "���"+this.document.title;
	diag.URL = "Site/ImagePlayerStyleDialog.jsp?ImagePlayerStyleID=";
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	$DW.saveStyle();	
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("��ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.site.ImagePlayerStyles.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.setParam("dg1",Constant.PageIndex,0);
					DataGrid.loadData("dg1");
				}
			});
		});
	});
}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�༭����ʽ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "�޸�"+this.document.title;
	diag.URL = "Site/ImagePlayerStyleDialog.jsp?ImagePlayerStyleID="+arr[0];
	diag.OKEvent = addSave;
	diag.show();
}

</script>
</head>
<body scroll="no">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
        <td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon039a11.gif" /> ��������ʽ�б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
                <z:tbutton onClick="add()"><img src="../Icons/icon039a2.gif" />���</z:tbutton> 
                <z:tbutton onClick="edit()"><img src="../Icons/icon039a4.gif" />�༭</z:tbutton> 
                <z:tbutton onClick="del()"><img src="../Icons/icon039a3.gif" />ɾ��</z:tbutton></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.cms.site.ImagePlayerStyles.dg1DataBind" size="5">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo">���</td>
							<td width="3%" ztype="selector" field="id">&nbsp;</td>
							<td width="18%"><strong>��ʽ����</strong></td>
							<td width="38%"><strong>����ͼ</strong></td>
                            <td width="29%"><strong>��ע</strong></td>
                            <td width="9%"><strong>Ĭ��</strong></td>
						</tr>
						<tr onDblClick="edit();">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td><img src="${CmsPath}${ImagePath}" height="80" onload="if(this.width>280)this.width='280'"/></td>
                            <td>${Memo}</td>
                            <td>${IsDefaultName}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="6" align="center">${PageBar}</td>
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
