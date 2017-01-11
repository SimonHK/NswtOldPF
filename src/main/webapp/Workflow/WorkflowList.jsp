<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�������б�</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function save(){
	DataGrid.save("dg1","com.nswt.workflow.WorkflowPage.save",function(){DataGrid.loadData('dg1');});
}

function add(){
	var url = Server.ContextPath+"Workflow/WorkflowConfig.jsp";
	var width  = (screen.availWidth-10)+"px";
    var height = (screen.availHeight-50)+"px";
    var left  = 0;
    var top   = 0;
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,left=" + left+ ",top=" + top + ", width="+width+", height="+height;
    var w = window.open(url,"",args);
    if ( !w ){
		Dialog.alert( "���ֵ������ڱ���ֹ���������������ã��Ա�����ʹ�ñ�����!" ) ;
		return ;
	}
}

function editDialog(ID){
	var url = Server.ContextPath+"Workflow/WorkflowConfig.jsp?ID="+ID;
	var width  = (screen.availWidth-10)+"px";
    var height = (screen.availHeight-50)+"px";
    var left  = 0;
    var top   = 0;
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,left=" + left+ ",top=" + top + ", width="+width+", height="+height;
    var w = window.open(url,"",args);
    if ( !w ){
		Dialog.alert( "���ֵ������ڱ���ֹ���������������ã��Ա�����ʹ�ñ�����!" ) ;
		return ;
	}
}


function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Dialog.confirm("��ȷ��Ҫɾ��ѡ�еĹ�������",function(){
		Server.sendRequest("com.nswt.workflow.WorkflowPage.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}				
			});
		});
	})
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon400a9.gif" width="20" height="20" /> �������б�</td>
            </tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon404a8.gif" />�½�</z:tbutton>
				<z:tbutton onClick="DataGrid.edit(event,'dg1')"><img src="../Icons/icon403a10.gif" />�޸�</z:tbutton>
				<z:tbutton onClick="del()"><img src="../Icons/icon404a5.gif" />ɾ��</z:tbutton></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.workflow.WorkflowPage.dg1DataBind" size="15">
					<table cellpadding="2" cellspacing="0" class="dataTable" width="100%">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo">���</td>
							<td width="4%" ztype="selector" field="ID">&nbsp;</td>
							<td width="21%"><b>����������</b></td>
						    <td width="16%">����޸�ʱ��</td> 
						    <td width="54%">��ע</td> 
						</tr>
						<tr ondblclick="editDialog(${ID})" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td></td>
							<td>${Name}</td>
							<td>${ModifyTime}</td>
						    <td>${Memo}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="5" align="center">${PageBar}</td>
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
