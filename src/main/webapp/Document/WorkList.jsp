<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ĵ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var catalogID = $V("CatalogID");
	if(catalogID==""){
		Dialog.alert("��ѡ����Ŀ��");
		return;
	}
	var width  = (screen.availWidth-10)+"px";
  	var height = (screen.availHeight-50)+"px";
  	var leftm  = 0;
  	var topm   = 0;
  
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  	var url="Article.jsp?CatalogID=" + catalogID;
  	var w = window.open(url,"",args);
  	if ( !w ){
		Dialog.alert( "���ֵ������ڱ���ֹ���������������ã��Ա�����ʹ�ñ�����!" ) ;
		return ;
	}
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	
	Dialog.confirm("ȷ��ɾ��ѡ�е��ĵ���",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.nswt.cms.document.ArticleList.del",dc,function(response){
				if(response.Status==0){
					Dialog.alert(response.Message);
				}else{
					Dialog.alert("�ɹ�ɾ���ĵ�");
					DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
					DataGrid.setParam("dg1",Constant.PageIndex,0);
		      DataGrid.loadData("dg1");
				}
			});
	});
}

function edit(id){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ�༭�����£�");
		return;
	}
	var id = arr[0];
	var width  = (screen.availWidth-10)+"px";
	var height = (screen.availHeight-50)+"px";
	var leftm  = 0;
	var topm   = 0;
	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var url="Article.jsp?ArticleID=" + id;
	var w = window.open(url,"",args);
	if(!w){
		Dialog.alert( "���ֵ������ڱ���ֹ���������������ã��Ա�����ʹ�ñ�����!" ) ;
		return ;
	}
}

function onTreeClick(ele){
	var cid=  ele.getAttribute("cid");
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.loadData("dg1");
	$S("CatalogID",cid);
}

function delKeyWord() {
	if ($V("Keyword") == "���������") {
		$S("Keyword","");
	}
}

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	if ($V("Keyword") != "���������" && $V("Keyword") && $V("Keyword").trim()) {
		DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	} else {
		DataGrid.setParam("dg1","Keyword","");
	}
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'Keyword'||ele.id == 'Submitbutton'){
			search();
		}
	}
}

function doAction(EntryID,ActionID,ArticleID){
	var dc = new DataCollection();
	dc.add("EntryID",EntryID);
	dc.add("ActionID",ActionID);
	dc.add("ArticleID",ArticleID);
	Server.sendRequest("com.nswt.cms.document.Article.doAction",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
   				DataGrid.loadData("dg1");
			}
		});
	});
}

function changeType(){
   	DataGrid.setParam("dg1",Constant.PageIndex,0);
	if ($V("Keyword") != "���������" && $V("Keyword") && $V("Keyword").trim()) {
		DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	} else {
		DataGrid.setParam("dg1","Keyword","");
	}
	DataGrid.setParam("dg1","Type",$V("Type"));
	DataGrid.loadData("dg1");
}

Page.onLoad(function(){
	$("article_applystep").disable();
	if($("BtnPublish")){
		$("BtnPublish").disable();
	}
	if($("BtnForceEnd")){
		$("BtnForceEnd").disable();
	}
});

function onRowClick(ele){
	var index = ele.rowIndex-1;
	if("Unread"==$("dg1").DataSource.Rows[index].get("State")){
		$("article_applystep").enable();
	}else{
		$("article_applystep").disable();
	}
	if($("BtnPublish")){
		if("20"==$("dg1").DataSource.Rows[index].get("Status")||"30"==$("dg1").DataSource.Rows[index].get("Status")){
			$("BtnPublish").enable();
		}else{
			$("BtnPublish").disable();
		}
	}
	if($("BtnForceEnd")){
		if("10"==$("dg1").DataSource.Rows[index].get("Status")){
			$("BtnForceEnd").enable();
		}else{
			$("BtnForceEnd").disable();
		}	}
}

function applyStep(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("����ѡ���¼!");
		return;
	}
	for(var i=dt.Rows.length-1;i>=0;i--){
		if("Unread"!=dt.Rows[i].get("State")){
			dt.deleteRow(i);
		}
	}
	if(dt.Rows.length==0){
		Dialog.alert("ѡ�еļ�¼��û�д��ڴ�����״̬��!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Data",dt);
	Server.sendRequest("com.nswt.cms.document.WorkList.applyStep",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
   				DataGrid.loadData("dg1");
			}
		});
	});
}

function articleLog(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ�༭�����£�");
		return;
	}
	var id = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 680;
	diag.Height = 350;
	diag.Title = "������ʷ";
	diag.URL = "Document/ArticleLogDialog.jsp?ArticleID="+id;
	diag.onLoad = function(){
	};
	diag.show();
	$E.hide(diag.OKButton);
	diag.CancelButton.value = "ȷ ��";
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		Dialog.alert("����ѡ��ҪԤ�������£�");
		return;
	}else{
		window.open("Preview.jsp?ArticleID="+arr[0]);
	}	
}

function publish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ���������£�");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.document.ArticleList.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			var p = new Progress(response.get("TaskID"),"���ڷ���...");
			p.show(function(){
				$("dg1").loadData();
			});
		}
	});
}

function workflowConfig(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("����ѡ��Ҫ�鿴���̵�����!");
		return;
	}
	var url = Server.ContextPath+"Workflow/WorkflowDetail.jsp?ID="+dt.get(0,"workflowconfigid");
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

function forceEnd(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("����ѡ���¼!");
		return;
	}
	for(var i=dt.Rows.length-1;i>=0;i--){
		if("10"!=dt.Rows[i].get("Status")){
			dt.deleteRow(i);
		}
	}
	if(dt.Rows.length==0){
		Dialog.alert("ѡ�еļ�¼��û�д�����ת��״̬��!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Data",dt);
	Server.sendRequest("com.nswt.cms.document.WorkList.forceEnd",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
   				DataGrid.loadData("dg1");
			}
		});
	});
}
</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td></td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="4" class="blockTable">
			<tr>
				<td colspan="2" valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />�ĵ�����б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton priv="true" onClick="DataGrid.edit(event,'dg1')"><img src="../Icons/icon018a4.gif" />��</z:tbutton> 
				<z:tbutton id="article_applystep" onClick="applyStep()"><img src="../Icons/icon018a16.gif" width="20" height="20" />���봦��</z:tbutton> 
				<z:tbutton id="article_manage" onClick="del()"><img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton>
				<z:tbutton id="article_manage" onClick="articleLog()"><img src="../Icons/icon_column.gif" width="20" height="20" />������ʷ</z:tbutton>
				<%if (!UserList.ADMINISTRATOR.equals(User.getUserName())) { %>
				<z:tbutton id="BtnPublish" priv="article_modify" onClick="publish()"><img src="../Icons/icon003a13.gif" width="20" height="20" />����</z:tbutton>
				<z:tbutton id="BtnPreview" priv="article_browse" onClick="preview()"><img src="../Icons/icon403a3.gif" width="20" height="20" />Ԥ��</z:tbutton>
				<%}else{%>
				<z:tbutton id="BtnForceEnd" priv="article_modify" onClick="forceEnd()"><img src="../Icons/icon400a12.gif" width="20" height="20" />ǿ�н�������</z:tbutton>
				<%}%>
				<z:tbutton id="BtnWorkflowConfig" onClick="workflowConfig()"><img src="../Icons/icon042a1.gif" width="20" height="20" />�鿴����</z:tbutton>
					<z:select id="Type" onChange="changeType()" style="width:110px;">
					<%if(UserList.ADMINISTRATOR.equals(User.getUserName())){ %>
					<span value="ALL">������ת�е��ĵ�</span>
					<%} %>
					<span value="TOME">���Ҵ�����ĵ�</span>
					<span value="HANDLED">�Ҵ�������ĵ�</span>
				</z:select><input name="Keyword" type="text" id="Keyword" value="���������" onFocus="delKeyWord();" style="width:100px"> <input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="search()"></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"><z:datagrid id="dg1" method="com.nswt.cms.document.WorkList.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" height="30" ztype="RowNo"></td>
							<td width="2%" ztype="selector" field="id">&nbsp;</td>
							<td width="10%"><b>������Ŀ</b></td>
							<td width="28%"><b>����</b></td>
							<td width="8%"><strong>������</strong></td>
							<td width="10%"><strong>���ʱ��</strong></td>
							<td width="8%"><strong>�ĵ�״̬</strong></td>
							<td width="8%"><strong>�ϴζ���</strong></td>
							<td width="7%"><strong>��ǰ����</strong></td>
							<td width="8%"><strong>��־</strong></td>
                            <td width="8%"><strong>������</strong></td>
						</tr>
						<tr onDblClick="edit(${ID});" onclick="onRowClick(this)">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${CatalogIDName}</td>
							<td>${Title}</td>
							<td>${AddUser}</td>
							<td>${AddTime}</td>
							<td>${StatusName}</td>
							<td>${ActionName}</td>
							<td>${StepName}</td>
							<td>${StateName}</td>
                            <td>${Owner}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="left">${PageBar}</td>
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
