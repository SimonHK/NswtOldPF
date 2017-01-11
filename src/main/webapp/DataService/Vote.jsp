<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 220;
	diag.Title = "�½�����";
	diag.URL = "DataService/VoteDialog.jsp?ID=&RelaCatalogID="+$V("RelaCatalogID");
	diag.onLoad = function(){
		$DW.$("Title").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½�����";
	diag.Message = "�½����飬���õ�������⡢��ֹʱ��ȡ�";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
    dc.add("Prop4", $DW.$NV("Prop4"));
	Server.sendRequest("com.nswt.cms.dataservice.Vote.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
				}else{
					Tree.loadData("tree1");
				}
				DataGrid.loadData("dg1");
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
	Dialog.confirm("ȷ��Ҫɾ���õ�����",function(){
		var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.nswt.cms.dataservice.Vote.del",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						Tree.loadData("tree1",function(){if(currentTreeItem){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))}});
						DataGrid.loadData("dg1");
					}
				});
			});
	})	
}

function handStop(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��ֹ��ͶƱ��");
		return;
	}
	Dialog.confirm("ȷ��Ҫ��ֹ��ͶƱ��",function(){
		var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.nswt.cms.dataservice.Vote.handStop",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData("dg1");
					}
				});
			});
	})	
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵĵ��飡");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 220;
	diag.Title = "�����޸�";
	diag.URL = "DataService/VoteDialog.jsp?ID="+dr.get("ID")+ "&RelaCatalogID=" + dr.get("RelaCatalogID");
	diag.onLoad = function(){
		$DW.$("Title").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸ĵ���";
	diag.Message = "���õ�������⡢��ֹʱ��ȡ�";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
    dc.add("Prop4", $DW.$NV("Prop4")); 
	Server.sendRequest("com.nswt.cms.dataservice.Vote.edit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
				}
				DataGrid.loadData("dg1");
			}
		});
	});
}

function editItemDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 960;
	diag.Height = 450;
	diag.Title = "�����ʾ��б�";
	diag.URL = "DataService/VoteItem.jsp?ID="+ID;
	diag.onLoad = function(){
	};
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "�ر�";
}

function JSCode(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 120;
	diag.Title = "JS���ô���";
	diag.URL = "DataService/VoteJSCodeDialog.jsp?ID="+ID;
	diag.onLoad = function(){
		$DW.$("JSCode").focus();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "JS���ô���";
	diag.Message = "ֱ�Ӱ������JS����ճ����html��ҳ�м���";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "�ر�";
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�鿴������У�");
		return;
	} 
	var ID = arr[0];
	if(screen.width==800){
		var width = 800,height = 600,leftm = 0,topm = 0;
	}else if(screen.width>800){
	  	var width  = Math.floor( screen.width  * .78 );
  		var height = Math.floor( screen.height * .8 );
  		var leftm  = Math.floor( screen.width  * .1);
 		var topm   = Math.floor( screen.height * .1);
	}else{
		return;
	}
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  	var url="VotePreview.jsp?ID=" + ID;
  	window.open(url,"VotePreview");
}

getResult = function() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�鿴������У�");
		return;
	} else if(arr.lenght > 1) {
		Dialog.alert("��ѡ��Ҫ�鿴�����һ�У�");
		return;
	}
	result(arr[0]);
}

function result(ID){
	if(screen.width==800){
		var width = 800,height = 600,leftm = 0,topm = 0;
	}
	else if(screen.width>800){
	  	var width  = Math.floor( screen.width  * .78 );
  		var height = Math.floor( screen.height * .8 );
  		var leftm  = Math.floor( screen.width  * .1);
 		var topm   = Math.floor( screen.height * .1);
	}
	else{
		return;
	}
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  	var url= Server.ContextPath+"Services/VoteResult.jsp?ID=" + ID;
  	window.open(url,"VoteResult");
}

resultDetails = function() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�鿴������У�");
		return;
	} else if(arr.lenght > 1) {
		Dialog.alert("��ѡ��Ҫ�鿴�����һ�У�");
		return;
	}
	var ID = arr[0];
	var diag = new Dialog("Votelogs");
	diag.Width = 700;
	diag.Height = 450;
	diag.Title = "ͶƱ��ϸ";
	diag.URL = "DataService/VoteLogs.jsp?ID=" + ID;
	diag.ShowButtonRow = false;
	diag.show();
}

var currentTreeItem;
function onTreeClick(ele){
	currentTreeItem = ele;
	var cid =  ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("RelaCatalogID",cid);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","CatalogInnerCode",code);
	DataGrid.loadData("dg1");
}

function titleDetails() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�鿴������У�");
		return;
	} else if(arr.lenght > 1) {
		Dialog.alert("��ѡ��Ҫ�鿴�����һ�У�");
		return;
	}
	var ID = arr[0];
	var diag = new Dialog("Votelogs");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "��д������";
	diag.URL = "DataService/VoteTitle.jsp?ID=" + ID;
	diag.ShowButtonRow = false;
	diag.show();
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
            <td>
            <table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
																 style="height:450px;width:160px;"
																 method="com.nswt.cms.dataservice.Vote.treeDataBind" level="2"
																 lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
            </td>
            <td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon032a1.gif" />����ͶƱ�б�<input type="hidden" id="RelaCatalogID" name="RelaCatalogID" value="0"/></td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon032a2.gif" />�½�</z:tbutton> 
					<z:tbutton onClick="edit()"><img src="../Icons/icon032a4.gif" />�޸�</z:tbutton> 
					<z:tbutton onClick="del()"><img src="../Icons/icon032a3.gif" />ɾ��</z:tbutton>
					<z:tbutton onClick="handStop()"><img src="../Icons/icon032a5.gif" />�ֹ���ֹ</z:tbutton>
					<z:tbutton onClick="preview()"><img src="../Icons/icon032a15.gif" />Ԥ��</z:tbutton>
					<z:tbutton onClick="getResult()"><img src="../Icons/icon032a1.gif" />���</z:tbutton>
					<z:tbutton onClick="resultDetails()"><img src="../Icons/icon032a1.gif" />�����ϸ</z:tbutton>
					<z:tbutton onClick="titleDetails()"><img src="../Icons/icon032a1.gif" />��д����ϸ</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.dataservice.Vote.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo">���</td>
								<td width="3%" align="center" ztype="selector" field="id">&nbsp;</td>
								<td width="5%"><b>ID</b></td>
								<td width="23%"><b>��������</b></td>
								<td width="9%"><b>ͶƱ����</b></td>
								<td width="7%"><b>����IP</b></td>
								<td width="16%"><b>��ʼʱ��</b></td>
								<td width="16%"><b>��ֹʱ��</b></td>
							</tr>
							<tr onDblClick="edit();">
								<td align="center">&nbsp;</td>
								<td align="center">&nbsp;</td>
								<td>${ID}</td>
								<td><a href="#" onClick="editItemDialog('${ID}')">${Title}</a></td>
								<td>${Total}</td>
								<td>${IPLimitName}</td>
								<td>${StartTime}</td>
								<td>${EndTime}</td>
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
