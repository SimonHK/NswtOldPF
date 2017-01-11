<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ʱ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ������־����",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.datachannel.DeployLog.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("ɾ���ɹ�");
				DataGrid.setParam("dg1",Constant.PageIndex,0);
        DataGrid.loadData("dg1");
			}
		});
	});

} 

function delAll(){
	DataGrid.selectAll("dg1");
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length == 0){
		Dialog.alert("û����־��¼��");
		return;
	}
	Dialog.confirm("ȷ��Ҫ�����־��",function(){
		var dc = new DataCollection();	
		Server.sendRequest("com.nswt.cms.datachannel.DeployLog.delAll",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("ɾ���ɹ�");
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	       DataGrid.loadData("dg1");
			}
		});
	});
} 

function refresh(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
    DataGrid.loadData("dg1");
}

function view(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�鿴���У�");
		return;
	}
	if(drs.length>1){
		Dialog.alert("ֻ���޸�1����Ϣ��");
		return;
	}
	dr = drs[0]; 
  	viewDialog(dr.get("ID"));
}

function viewDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "�鿴��Ϣ";
	diag.URL = "DataChannel/DeployLogDialog.jsp?ID="+ID;
	diag.onLoad = function(){
	};
	diag.OKEvent = function(){
		$D.close();
	};
	diag.show();
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="1">
	<tr>
		<td style="padding:0 0 6px;">
		<z:tbutton onClick="view()"><img src="../Icons/icon018a1.gif" />�鿴����</z:tbutton> 
		<z:tbutton onClick="delAll()"><img src="../Icons/icon018a3.gif" />���</z:tbutton> 
		<z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton> 
		<z:tbutton onClick="refresh()"><img src="../Icons/icon018a13.gif" />ˢ��</z:tbutton></td>
	</tr>
	<tr>
		<td>
		<z:datagrid id="dg1" method="com.nswt.cms.datachannel.DeployLog.dg1DataBind" size="14">
			<table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="5%" ztype="RowNo">&nbsp;</td>
					<td width="5%" ztype="selector" field="id">&nbsp;</td>
					<td width="9%"><b>���Ʒ�ʽ</b></td>
					<td width="17%"><b>����Ŀ¼</b></td>
					<td width="8%"><b></b>Ŀ��Ŀ¼</td>
					<td width="10%"><b></b>��������ַ</td>
					<td width="29%"><b>��Ϣ</b></td>
					<td width="17%"><b>ִ��ʱ��</b></td>
				</tr>
				<tr onDblClick="viewDialog(${ID})">
					<td align="center">&nbsp;</td>
					<td>&nbsp;</td>
					<td>${methodDesc}</td>
					<td>${Source}</td>
					<td>${Target}</td>
					<td>${Host}</td>
					<td>${Message}</td>
					<td>${BeginTime}</td>
				</tr>
				<tr ztype="pagebar">
					<td colspan="8">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</body>
</html>
