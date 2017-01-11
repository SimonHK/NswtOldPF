<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script>
function closeX(){
   window.close();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	
	Dialog.confirm("ȷ��ɾ��ѡ�е���ע��",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.document.ArticleNote.del",dc,function(response){
			if(response.Status==0){
				alert(response.Message);
			}else{
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      DataGrid.loadData("dg1");
			}
		});
	});
}

function add(){
	var diag  = new Dialog("Diag");
	diag.Width = 420;
	diag.Height = 180;
	diag.Title ="������ע";
	diag.URL = "Document/ArticleNoteAddDialog.jsp";
	diag.onLoad = function(){
	};
	
  diag.OKEvent = addSave;
	diag.show();
}


function addSave(){
	var dc = new DataCollection();
	var content = $DW.$V("Content");
	if(content == ""){
		Dialog.alert("��ע���ݲ���Ϊ��");
		$DW.$("Content").focus();
		return;
	} else if (content.length>400) {
		Dialog.alert("��ע���ݲ��ܳ���400���֣�");
		$DW.$("Content").focus();
		return;
	}
	
	dc.add("Content",content);
	dc.add("ArticleID",$V("ArticleID"));
	Server.sendRequest("com.nswt.cms.document.ArticleNote.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�ɹ������ע",function(){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      		DataGrid.loadData("dg1");
			});
		}
	});
}

function noteDetail(){
	var dt = DataGrid.getSelectedData("dg1");
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("��ѡ����Ҫ�鿴����ע��");
		return;
	}
	Dialog.alert(dt.Rows[0].get("ActionDetail"));
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" class="cellspacing"
	cellpadding="0">
	<tr>
		<td>
		<div><z:tbutton onClick="add()">
			<img src="../Icons/icon018a16.gif" />���</z:tbutton> <z:tbutton onClick="del()">
			<img src="../Icons/icon018a5.gif" />ɾ��</z:tbutton></div>
</td></tr><tr><td>
		<input type="hidden" name="AritcleID" id="ArticleID"
			value="<%=request.getParameter("ArticleID")%>"> <z:datagrid
			id="dg1" method="com.nswt.cms.document.ArticleNote.noteDataBind"
			size="10">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="5%" ztype="selector" field="id">&nbsp;</td>
					<td width="58%""><b>��ע</b></td>
					<td width="14%"><strong>��ע��</strong></td>
					<td width="23%"><strong>��עʱ��</strong></td>
				</tr>
				<tr onDblClick="noteDetail();">
					<td>&nbsp;</td>
					<td>${ActionDetail}</td>
					<td>${addUser}</td>
					<td>${addTime}</td>
				</tr>

				<tr ztype="pagebar">
					<td colspan="4" align="left">
					<div align="center">${PageBar}</div>
					</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</body>
</html>
