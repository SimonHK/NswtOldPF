<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.cms.site.Keyword.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var selectedCID = "";//��ѡ���ȵ�ʷ����ID
Page.onLoad(function(){
	$("dg1").beforeEdit = function(tr,dr){
	    $S("LinkTarget",$V("hLinkTarget"));
	}
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Keyword",$V("Keyword"));
		dr.set("LinkURL",$V("LinkURL"));
		dr.set("LinkAlt",$V("LinkAlt"));
		dr.set("LinkTarget",$V("LinkTarget"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.nswt.cms.site.Keyword.dg1Edit",function(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
	});
}

function addType(){
	var diag = new Dialog("Diag1");
	diag.Width = 320;
	diag.Height = 70;
	diag.Title = "�½��ȵ�ʷ���";
	diag.URL = "Site/KeywordTypeDialog.jsp";
	diag.OKEvent = addTypeSave;
  	diag.show();
}

function addTypeSave(){
	var dc = new DataCollection();
	dc.add("TypeName",$DW.$V("TypeName"));
	
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.KeywordType.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�½�����ɹ�",function(){
			    $D.close();
				Tree.loadData("tree1",function(){
					Tree.select("tree1","cid",Cookie.get("DocList.LastCatalog"));
				});
			});
		}
	});
}

function editType(param) {
	var cmsg=param;
	var cid =cmsg[0];
	if(!cid){
		Dialog.alert("����ѡ��һ�����࣡");
		return ;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 320;
	diag.Height = 70;
	diag.Title = "�޸��ȵ�ʷ���";
	diag.URL = "Site/KeywordTypeDialog.jsp?id="+cid;
	diag.OKEvent = editTypeSave;
  	diag.show();
}

function editTypeSave(){
	var dc = new DataCollection();
	dc.add("ID",$DW.$V("cid"));
	dc.add("TypeName",$DW.$V("TypeName"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.KeywordType.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�޸ĳɹ�",function(){
			    $D.close();
				Tree.loadData("tree1",function(){
					//Tree.select("tree1","cid",Cookie.get("DocList.LastCatalog"));
				});
			});
		}
	});
}

function delType(param) {
	var cmsg = param;
	var cid = cmsg[0];
	var cname = cmsg[1];
	if(!cid) {
		Dialog.alert("����ѡ��һ�����࣡");
		return ;
	}
	Dialog.confirm("ȷ��ɾ�� \""+cname+"\"</font> ������<br><font style='color:#F00'>ȷ�Ͻ��Ƴ��÷����������ȵ��!</font>",function() {
		var dc = new DataCollection();
		dc.add("ID", cid);
		
		Server.sendRequest("com.nswt.cms.site.KeywordType.del",dc,function(response){
			if(response.Status == 0) {
				Dialog.alert(response.Message);
			} else {
				Dialog.alert("ɾ������ɹ�",function() {
					Tree.loadData("tree1",function() {
						DataGrid.setParam("dg1",Constant.PageIndex,0);
						DataGrid.loadData("dg1");
					});
				});
			}
		});
	})
}

function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	selectedCID = cid;
	DataGrid.setParam("dg1","id",cid);
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	var cname = ele.getAttribute("cname");
	var cmsg=[cid,cname];
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cmsg);
	menu.addItem("�½�",addType,"/Icons/icon018a2.gif");
	menu.addItem("�޸�",editType,"/Icons/icon018a4.gif");
	menu.addItem("ɾ��",delType,"/Icons/icon018a3.gif");
	menu.show();
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "�½��ȵ��";
	diag.URL = "Site/KeywordDialog.jsp?selectedCID="+selectedCID;
	diag.onLoad = function(){
		$DW.$("Keyword").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.$NV("keywordType");
	if(arr == null || arr == "") {
		Dialog.alert("��ѡ����������");
		return;
	}
	dc.add("selectdTypes",arr);
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Keyword.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function edit(){
  	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ���Ϣ��");
		return;
	}else if(drs.length>1){
		Dialog.alert("һ��ֻ���޸�һ����Ϣ��");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "�޸��ȵ��";
	diag.URL = "Site/KeywordDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.$("Keyword").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸��ȵ��";
	diag.Message = "�޸��ȵ��";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.$NV("keywordType");
	if(arr == null || arr == "") {
		Dialog.alert("��ѡ����������");
		return;
	}
	dc.add("selectdTypes",arr);
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Keyword.edit",dc,function(response){
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
	var message = "";
	if(selectedCID == "" || selectedCID == null) {
		message = "<font color='red'>ע���˲���������ɾ������ѡ�е��ȵ�ʣ��������з��ࣩ</font><br/>&nbsp;ȷ��Ҫ�����з������Ƴ���";
	} else {
		message = "��ȷ��Ҫ�Ӹ÷������Ƴ���";
	}
	Dialog.confirm(message,function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			dc.add("selectedCID",selectedCID);
			Server.sendRequest("com.nswt.cms.site.Keyword.del",dc,function(response){
				if(response.Status==0){
					Dialog.alert(response.Message);
				}else{
					Dialog.alert("ɾ���ؼ��ʳɹ�");
					DataGrid.setParam("dg1",Constant.PageIndex,0);
					DataGrid.loadData("dg1");
				}
			});
	});
}

function importWord(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "��������ؼ���";
	diag.URL = "Site/KeyWordImport.jsp";
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
	window.location = "KeyWordExport.jsp?ids="+ids+"&selectedCID="+selectedCID;
}

function loadWordData(){
	DataGrid.loadData("dg1");	
}

function loadTreeData(){
	Tree.loadData("tree1",function(){Tree.select("tree1","cid",selectedCID)});	
}

function doSearch(){
	var Word = "";
	if ($V("Word") != "������ؼ���") {
		Word = $V("Word");
	}	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Word",Word);
	DataGrid.loadData("dg1");
}

function delKeyWord() {
	var keyWord = $V("Word");
	if (keyWord == "������ؼ���") {
		$S("Word","");
	}
}

function addKeyWord() {
	var keyWord = $V("Word");
	if (keyWord == "") {
		$S("Word","������ؼ���");
	}
}

function afterTreeDragEnd(evt){
	var item = this;
	var typeID = item.$A("cid");
	var row = DragManager.DragSource.parentNode;
	var dc = new DataCollection();
	dc.add("KeywordIDs",$("dg1").DataSource.get(row.rowIndex-1,"ID"));
	dc.add("TypeID",typeID);
	Server.sendRequest("com.nswt.cms.site.Keyword.move",dc,function(response){
		Dialog.alert(response.Message,function(){
			DataGrid.setParam("dg1",Constant.PageIndex,0);
			DataGrid.loadData("dg1");
		});
	});
}

</script>
	</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td width="140">
			<table width="140" oncontextmenu="return false;" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
																	 style="height:443px;width:130px;"
																	 method="com.nswt.cms.site.Keyword.treeDataBind" level="2"
																	 lazy="true">
						<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}'
						   cname='${TypeName}' onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
						   oncontextmenu="showMenu(event,this);">${TypeName}</p>
					</z:tree></td>
				</tr>
			</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd">
						<img src="../Icons/icon048a1.gif" /> �ȵ���б�
					</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
						<z:tbutton onClick="addType()"><img src="../Icons/icon048a2.gif" />�½�����</z:tbutton>
						<z:tbutton onClick="add()"><img src="../Icons/icon048a2.gif" />�½�</z:tbutton>
						<z:tbutton onClick="save()"><img src="../Icons/icon048a16.gif" />����</z:tbutton>
						<z:tbutton onClick="edit()"><img src="../Icons/icon048a16.gif" />�༭</z:tbutton>
						<z:tbutton onClick="del()"><img src="../Icons/icon048a3.gif" />ɾ��</z:tbutton>
						<z:tbutton onClick="importWord()"><img src="../Icons/icon048a8.gif" />����</z:tbutton>
						<z:tbutton onClick="exportWord()"><img src="../Icons/icon048a7.gif" />����</z:tbutton>
					<div style="float: right; white-space: nowrap;"><input
						name="Word" type="text" id="Word" value="������ؼ���"
						onFocus="delKeyWord();" onblur="addKeyWord();" style="width:110px"> <input
						type="button" name="Submitbutton" value="��ѯ" onClick="doSearch()"
						id="Submitbutton"></div>
					</td>
				</tr>
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.site.Keyword.dg1DataBind" size="12">
						<table width="100%" cellpadding="2" cellspacing="0"
							   class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="4%" ztype="selector" field="id">&nbsp;</td>
								<td width="15%">�ȵ��</td>
								<td width="21%">���ӵ�ַ</td>
								<td width="22%">��ʾ</td>
								<td width="14%">�򿪷�ʽ</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td width="3%">&nbsp;</td>
								<td>${Keyword}</td>
								<td>${LinkURL}</td>
								<td>${LinkAlt}</td>
								<td>
								<div ztype="select" name="LinkTarget${ID}" id="LinkTarget${ID}"
									value="${LinkTarget}" style="width:80px;display:none"
									disabled="true"><span value="_self" selected>ԭ����</span> <span
									value="_blank">�´���</span> <span value="_parent">������</span></div>
								</td>
							</tr>
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td width="4%" bgcolor="#E1F3FF">&nbsp;</td>
								<td width="3%" bgcolor="#E1F3FF">&nbsp;</td>
								<td><input name="Keyword" type="text" class="input1"
									id="Keyword" value="${Keyword}" size="15"></td>
								<td><input name="LinkURL" type="text" class="input1"
									id="LinkURL" value="${LinkURL}" size="30"></td>
								<td><input name="LinkAlt" type="text" class="input1"
									id="LinkAlt" value="${LinkAlt}" size="30"></td>
								<td><input type="hidden" id="hLinkTarget"
									value="${LinkTarget}">
								<div ztype="select" name="LinkTarget" id="LinkTarget"
									style="width:80px;"><span value="_self" >ԭ����</span>
								<span value="_blank">�´���</span> <span value="_parent">������</span>
								</div>
								</td>
								<td>${AddTime}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="8" align="center">${PageBar}</td>
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
</z:init>
