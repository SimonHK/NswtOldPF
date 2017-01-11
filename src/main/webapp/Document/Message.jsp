<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag =new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "����Ϣ";
	diag.URL = "Document/MessageDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ToUser").focus();
	};
	diag.OKEvent = addSave
	diag.show();
}

function reply(){
    var dt = DataGrid.getSelectedData("dg1");
	var dr = dt.Rows;
	if(!dt||dr.length==0){
		Dialog.alert("����ѡ��Ҫ�ظ�����Ϣ��");
		return;
	}
	if(dr.length>1){
		Dialog.alert("ֻ�ܻظ�1����Ϣ��");
		return;
	}

	var diag =new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "�ظ���Ϣ";
	diag.URL = "Document/MessageReplyDialog.jsp?ID="+dr[0].get("ID");
	diag.OKEvent = replySave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
		return;
    }
	var dc = Form.getData($DW.$F("formMsg"));
	Server.sendRequest("com.nswt.cms.document.Message.add",dc,function(response){
		if(response.Status==1){
			DataGrid.setParam("dg1",Constant.PageIndex,0);
			DataGrid.loadData("dg1");
			$D.close();
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function replySave(){
	if($DW.Verify.hasError()){
		return;
    }
	var dc = Form.getData($DW.$F("formMsg"));
	Server.sendRequest("com.nswt.cms.document.Message.reply",dc,function(response){
		if(response.Status==1){
			DataGrid.setParam("dg1",Constant.PageIndex,0);
			DataGrid.loadData("dg1");
			Dialog.alert(response.Message);
			$D.close();
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function messageHistory(){
	var diag =new Dialog("diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "�ѷ���Ϣ";
	diag.URL = "Document/MessageHistory.jsp";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
}

function messageDetail(fromUser){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("��ѡ����Ҫ�鿴����Ϣ��");
		return;
	}
	var diag =new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "�鿴��Ϣ";
	diag.URL = "Document/MessageDetailDialog.jsp?ID="+arr[0];
	diag.OKEvent = reply;
	diag.show();
	if(fromUser=="SYSTEM"){
		diag.OKButton.hide();
	}else{
		diag.OKButton.value = "�ظ�";
	}
	DataGrid.loadData("dg1");
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
		Server.sendRequest("com.nswt.cms.document.Message.del",dc,function(response){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function setReadFlag(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.document.Message.setReadFlag",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			DataGrid.loadData("dg1");
		}
	});
}

setInterval(function(){DataGrid.loadData("dg1");},10000);
</script>
</head>
<z:init method="com.nswt.cms.document.Message.init">
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon028a1.gif" width="20" height="20" /> ����Ϣ�б�</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<z:tbutton onClick="add()"><img src="../Icons/icon028a2.gif" width="20" height="20" />�½�</z:tbutton> 
					<z:tbutton onClick="reply()"><img src="../Icons/icon028a4.gif" width="20" height="20" />�ظ�</z:tbutton> 
					<z:tbutton onClick="del()"><img src="../Icons/icon028a3.gif" width="20" height="20" />ɾ��</z:tbutton> 
					<z:tbutton onClick="setReadFlag()"><img src="../Icons/icon034a4.gif" width="20" height="20" />���Ϊ����</z:tbutton>
					<z:tbutton onClick="messageHistory()"><img src="../Icons/icon028a7.gif" width="20" height="20" />�ѷ���Ϣ</z:tbutton>
					</td>
				</tr>
				<tr>
					<td
						style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
					<z:datagrid id="dg1"
								method="com.nswt.cms.document.Message.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="3%"  ztype="RowNo">&nbsp;</td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="3%">&nbsp;</td>
								<td width="57%" sortField="subject">����</td>
								<td width="17%" sortField="fromuser"><strong>������</strong></td>
								<td width="17%" sortField="addtime" direction="DESC"><strong>����ʱ��</strong></td>
							</tr>
							<tr onDblClick="messageDetail('${FromUser}');">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${ReadFlagIcon}</td>
								<td>${Subject}</td>
								<td>${FromUser}</td>
								<td>${addTime}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="7" align="center">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</body>
</z:init>
</html>
