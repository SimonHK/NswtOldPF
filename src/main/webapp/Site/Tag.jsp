<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Tag",$V("Tag"));
		dr.set("LinkURL",$V("LinkURL"));
		dr.set("TagText",$V("TagText"));		
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.nswt.cms.site.Tag.dg1Edit",function(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
		});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 150;
	diag.Title = "�½�Tag";
	diag.URL = "Site/TagDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Tag").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Tag.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
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
	Server.sendRequest("com.nswt.cms.site.Tag.del",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("ɾ��Tag�ɹ�");
			DataGrid.loadData('dg1');
		}
	});
});
}

function importWord(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "��������Tag";
	diag.URL = "Site/BadWordImport.jsp";
	diag.OKEvent = importSave;
	diag.show();
}

function importSave(){
	$DW.importWords();
}

function exportWord(){
	var arr = DataGrid.getSelectedValue("dg1");
	var ids;
	if(!arr||arr.length==0){
		ids = "";
	}else{
		ids = arr.join(",");	
	}
	window.location = "BadWordExport.jsp?ids="+ids;
}

function loadWordData(){
	DataGrid.loadData("dg1");	
}

function doSearch(){
	var TagkeyWord = "";
	if ($V("TagkeyWord") != "������Tag�ؼ���") {
		TagkeyWord = $V("TagkeyWord");
	}	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","TagWord",TagkeyWord);
	DataGrid.loadData("dg1");
}

function delKeyWord() {
	var keyWord = $V("TagkeyWord");
	if (keyWord == "������Tag�ؼ���") {
		$S("TagkeyWord","");
	}
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'TagkeyWord'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}
</script>
</head>
<body>
<z:init method="com.nswt.cms.site.Tag.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon011a1.gif" /> Tag�б�</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()">
						<img src="../Icons/icon011a2.gif" />�½�</z:tbutton> <z:tbutton
						onClick="save()">
						<img src="../Icons/icon011a16.gif" />����</z:tbutton> <z:tbutton
						onClick="del()">
						<img src="../Icons/icon011a3.gif" />ɾ��</z:tbutton><!--   <z:tbutton
						onClick="importWord()">
						
						<img src="../Icons/icon007a8.gif" />����</z:tbutton> <z:tbutton
						onClick="exportWord()">
						<img src="../Icons/icon007a7.gif" />����</z:tbutton>
						-->
					<div style="float: right; white-space: nowrap;"><input
						name="TagkeyWord" type="text" id="TagkeyWord" value="������Tag�ؼ���"
						onFocus="delKeyWord();" style="width:110px"> <input
						type="button" name="Submitbutton" value="��ѯ" onClick="doSearch()"
						id="Submitbutton"></div>
					</td>
				</tr>
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1"
								method="com.nswt.cms.site.Tag.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><strong>���</strong></td>
								<td width="5%" ztype="selector" field="id">&nbsp;</td>
								<td width="16%" sortfield="Tag"><b>Tag����</b></td>
								<td width="31%" class="wrap"><b>Tag����</b></td>
								<td width="12%" class="wrap"><b>���ô���</b></td>
								<td width="19%" class="wrap"><b>Tag����</b></td>
								<td width="12%"><b>�����</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="5%">&nbsp;</td>
								<td width="5%">&nbsp;</td>
								<td>${Tag}</td>
								<td>${LinkURL}</td>
								<td>${UsedCount}</td>
								<td>${TagText}</td>
								<td>${AddUser}</td>
							</tr>
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
								<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
								<td><input name="Tag" type="text" class="input1" id="Tag"
									value="${Tag}" size="20"></td>
								<td><input name="LinkURL" type="text" class="input1"
									id="LinkURL" value="${LinkURL}" size="30"></td>
								<td>${UsedCount}</td>
								<td><input name="TagText" type="text" class="input1"
									id="TagText" value="${TagText}" size="30"></td>
								<td>${AddUser}</td>
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
</z:init>
</body>
</html>