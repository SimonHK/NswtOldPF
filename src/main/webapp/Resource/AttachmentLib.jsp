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
	init();
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 		return;
    	}
		dr.set("Name",$V("Name"));
		return true;
	}
});

function init(){
	var flag = false;
	if(!Cookie.get("Resource.LastAttachLib")||Cookie.get("Resource.LastAttachLib")=="0"){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		flag = true;
	}else{
		Tree.select("tree1","cid",Cookie.get("Resource.LastAttachLib"));
		if(Tree.CurrentItem!=null){
			onTreeClick();
		}else{
			flag = true;
		}
	}
	if(flag){
		var tree = $("tree1");
		var arr = tree.getElementsByTagName("p");
		for(var i=0;i<arr.length;i++){
			var p = arr[i];
			if(i==1){
				p.className = "cur";
				Tree.CurrentItem = p;
				p.onclick.apply(p);
				break;
			}
		}
	}
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	var cname = ele.getAttribute("cname");
	var param = cid + "," + cname;
	var menu = new Menu();
	menu.setParam(param);
	if(!cid||cid==null){
		if(Application.getPriv("attach",Application.CurrentSite,"attach_manage")){
			menu.setEvent(event);
			menu.addItem("�½�",add,"/Icons/icon018a2.gif");
			menu.show();
		}
	}else{
		if(Application.getPriv("attach",ele.getAttribute("InnerCode"),"attach_manage")){
			menu.setEvent(event);
			menu.addItem("�½�",add,"/Icons/icon018a2.gif");
			menu.addItem("�޸�",change,"/Icons/icon018a4.gif");
			menu.addItem("ɾ��",delLib,"/Icons/icon018a3.gif");
			menu.addItem("����",sortLib,"/Icons/icon400a13.gif");
			menu.show();
		}
	}
}

function sortLib(param){
	if(!param){
		return;
	}
	var catalogID = param.substring(0, param.indexOf(","));
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 100;
	diag.Title = "��Ŀ����";
	diag.URL = "./Site/CatalogSort.jsp?CatalogID="+catalogID;
	diag.onLoad = function(){
		$DW.$("Move").focus();
	};
	diag.OKEvent = sortLibSave;
	diag.show();
}

function sortLibSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  	return;
    }
	dc.add("CatalogType","7");
	Server.sendRequest("com.nswt.cms.site.Catalog.sortCatalog",dc,function(response){
		if(response.Status==1){
			Tree.loadData("tree1");
			$D.close();
		}
	});	
}

function change(param){
	if(!param){
		return ;
	}
	
	var cid = param.substring(0, param.indexOf(","));
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 130;
	diag.Title = "�޸ĸ�������";
	diag.URL = "Resource/AttachmentLibEditDialog.jsp?ID="+cid;
	diag.onLoad = function(){
	$DW.$("Name").focus();		
	};
	diag.OKEvent = changeSave;
	diag.show();
}

function changeSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.nswt.cms.resource.AttachmentLib.AttachmentLibEdit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				Tree.loadData("tree1");
				$D.close();
			}
		});
	});	
}

function onTreeClick(){
	var ele = Tree.CurrentItem;
	var cid = ele.getAttribute("cid");
	if(!cid){
	    if(Application.getPriv("video",Application.CurrentSite,"attach_manage")){
			$("addLibButton").enable();
		} else {
			$("addLibButton").disable();
		}
		$("uploadButton").disable();
		$("editButton").disable();
		$("publishButton").disable();
		$("copyButton").disable();
		$("transferButton").disable();
		$("delButton").disable();
		return ;
	}
	Cookie.set("Resource.LastAttachLib",cid,"2100-01-01")
	$("uploadButton").enable();
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.loadData("dg1");
	Application.setAllPriv("attach",ele.getAttribute("InnerCode"));
}

function add(param){
	var ParentID;
	var catalogName;
	if(!param){
		if(Tree.CurrentItem!=null){
			ParentID = Tree.CurrentItem.getAttribute("cid");
			catalogName = Tree.CurrentItem.innerText;
		}
	} else {
		ParentID = param.substring(0, param.indexOf(","));
		catalogName = param.substring(param.indexOf(",")+1);
	}
	if(!ParentID||ParentID=="null"||ParentID==null){
		ParentID = "0";
		catalogName = "������";
	}
	var diag = new Dialog("Diag1");
	diag.Width = 410;
	diag.Height = 150;
	diag.Title = "�½���������";
	diag.URL = "Resource/AttachmentLibAddDialog.jsp?ParentID="+ ParentID;
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("ParentID"),ParentID,catalogName);;
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.nswt.cms.resource.AttachmentLib.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				Tree.loadData("tree1");
				var win = $E.getTopLevelWindow();
				if(win.getAllPriv){
				   win.getAllPriv();//ˢ��jsȨ��
				}
				$D.close();
			}
		});
	});
}

function upload(){
	var CatalogID = 0;
	if(Tree.CurrentItem!=null){
		CatalogID = Tree.CurrentItem.getAttribute("cid")	
	}
	var diag = new Dialog("Diag1");
	diag.Width = 530;
	diag.Height = 450;
	diag.Title = "�ϴ�����";
	diag.URL = "Resource/AttachmentLibDialog.jsp?CatalogID="+CatalogID;
	diag.onLoad = function(){
		$DW.$("AttachmentBrowse").hide();
	};
    diag.OKEvent=OK;
	diag.show();
}

function OK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("AttachmentUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("AttachmentBrowse")){
	 	currentTab.contentWindow.onFileReturnBack();
	}
}

function save(){
	DataGrid.save("dg1","com.nswt.cms.resource.AttachmentLib.dg1Edit",function(){
		DataGrid.setParam("dg1","Name",$V("SearchName"));
		DataGrid.loadData('dg1');
	});
}

function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶĸ�����");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 60;
	diag.Title = "���Ƹ���";
	diag.URL = "Resource/AttachmentCopyDialog.jsp?CatalogID="+Tree.CurrentItem.getAttribute("cid");
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("CatalogID"),Tree.CurrentItem.getAttribute("cid"),Tree.CurrentItem.innerText);
	}
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶĸ�����");
		return;
	}
	if(!$DW.$V("CatalogID")||$DW.$V("CatalogID")=="0"){
		Dialog.alert("�������ܸ��Ƶ�վ���£�");
		return;
	}
	if($DW.$V("CatalogID")==Tree.CurrentItem.getAttribute("cid")){
		Dialog.alert("�����Ѿ��������������ˣ�������ѡ����һ���������࣡");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("CatalogID",$DW.$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.resource.Attachment.copy",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
			}
		});
	});
}

function RepeatUpload(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�����ϴ��ĸ�����");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "�����ϴ�";
	diag.URL = "Resource/AttachRepeatUpload.jsp?ID="+arr[0];
	diag.OKEvent = RepeatOK;
	diag.show();
	diag.CancelButton.value="�ر�";
}

function RepeatOK(){
	$DW.RepeatUpload();
}

function transfer(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫת�Ƶĸ�����");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 60;
	diag.Title = "ת�Ƹ���";
	diag.URL = "Resource/AttachmentCopyDialog.jsp?CatalogID="+Tree.CurrentItem.getAttribute("cid");
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("CatalogID"),Tree.CurrentItem.getAttribute("cid"),Tree.CurrentItem.innerText);
	}
	diag.OKEvent = transferSave;
	diag.show();
}

function transferSave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫת�Ƶĸ�����");
		return;
	}
	if(!$DW.$V("CatalogID")||$DW.$V("CatalogID")=="0"){
		Dialog.alert("��������ת�Ƶ�վ���£�");
		return;
	}
	if($DW.$V("CatalogID")==Tree.CurrentItem.getAttribute("cid")){
		Dialog.alert("�����Ѿ��������������ˣ�������ѡ����һ���������࣡");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("CatalogID",$DW.$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.resource.Attachment.transfer",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				if ($V("SearchName") != "�����븽������") {
					DataGrid.setParam("dg1","Name",$V("SearchName"));
				} else {
					DataGrid.setParam("dg1","Name","");
				}
				DataGrid.loadData("dg1");
			}
		});
	});
}

function delLib(param){
	if(!param){
		return ;
	}
	var catalogID = param.substring(0, param.indexOf(","));
	Dialog.confirm("�÷����µĸ���������ɾ������ȷ��Ҫɾ���˸���������",function(){
		var dc = new DataCollection();
		dc.add("catalogID",catalogID);
		Server.sendRequest("com.nswt.cms.resource.AttachmentLib.delLib",dc,function(response){
		Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.location.reload();
				}
			})
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ���ĸ�����");
		return;
	}
	Dialog.confirm("��Щ�������������»���ͼƬ�й�������ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.resource.Attachment.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					if ($V("SearchName") != "�����븽������") {
						DataGrid.setParam("dg1","Name",$V("SearchName"));
					} else {
						DataGrid.setParam("dg1","Name","");
					}
					DataGrid.loadData("dg1");
				}
			});
		})
	});
}

function onFileUploadCompleted(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","CatalogID",Tree.CurrentItem.getAttribute("cid"));
	if ($V("SearchName") != "�����븽������") {
		DataGrid.setParam("dg1","Name",$V("SearchName"));
	} else {
		DataGrid.setParam("dg1","Name","");
	}
	DataGrid.loadData("dg1");
	if($D){
		setTimeout(function(){$D.close()},100);
	}
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchName'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	var keyWord = $V("SearchName");
	if (keyWord == "�����븽������") {
		$S("SearchName","");
	}
}

function doSearch(){
	var name = "";
	if ($V("SearchName") != "�����븽������") {
		name = $V("SearchName");
	}
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchName",name);
	DataList.setParam("dg1","StartDate",$V("StartDate"));
	DataList.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function publish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ�����ĸ�����");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.resource.AttachmentLib.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			var p = new Progress(response.get("TaskID"),"���ڷ���...");
			p.show();
		}
	});
}

function download(attid){
	if(!attid||attid==""){
		return;
	}else{
		window.open(Server.ContextPath + "Services/AttachDownLoad.jsp?id="+attid,"_blank");	
	}
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	
	var order = $("dg1").DataSource.get(rowIndex-1,"OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = ds.get(i-1,"OrderFlag");
		dc.add("Type","After");		
	}else{
		dc.add("Type","Before");
		target = $("dg1").DataSource.get(1,"OrderFlag");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	var cid = Tree.CurrentItem.getAttribute("cid");
	if (!cid) {
		cid = Cookie.get("Resource.LastAttachLib");
	}
	dc.add("CatalogID",cid);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.cms.resource.AttachmentLib.sortColumn",dc,function(response){
		if(response.Status==1){
			DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);	
		}
	});
}

function DetailEdit(){
    var arr=DataGrid.getSelectedValue("dg1");
  	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 180;
	diag.Title = "�鿴/�༭������Ϣ";
	diag.URL = "Resource/AttachmentEditDialog.jsp?ID="+arr[0];
	diag.onLoad = function(){
		$DW.$("Name").focus();		
	};
	diag.OKEvent = DetailEditSave;
	diag.show();
}

function DetailEditSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.nswt.cms.resource.Attachment.dialogEdit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});	
}


</script>
</head>
<body onContextMenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1" style="height:450px;width:160px;" method="com.nswt.cms.resource.AttachmentLib.treeDataBind" lazy="true" expand="false">
					<p cid='${ID}' cname='${Name}' InnerCode='${InnerCode}' onClick="onTreeClick(this);" onContextMenu="showMenu(event,this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton id="addLibButton" priv="attach_manage" onClick="add()">
					<img src="../Icons/icon003a2.gif" />�½�����</z:tbutton> <z:tbutton id="uploadButton" priv="attach_modify" onClick="upload()">
					<img src="../Icons/icon003a8.gif" />�ϴ�</z:tbutton> <z:tbutton id="uploadButton" priv="attach_modify" onClick="RepeatUpload()">
					<img src="../Icons/icon003a8.gif" />�����ϴ�</z:tbutton> <z:tbutton id="editButton" priv="attach_modify" onClick="save()">
					<img src="../Icons/icon003a4.gif" />����</z:tbutton> <z:tbutton id="publishButton" priv="attach_modify" onClick="publish()">
					<img src="../Icons/icon003a4.gif" />����</z:tbutton> <z:tbutton id="copyButton" priv="attach_modify" onClick="copy()">
					<img src="../Icons/icon003a17.gif" />����</z:tbutton> <z:tbutton id="transferButton" priv="attach_modify" onClick="transfer()">
					<img src="../Icons/icon003a7.gif" />ת��</z:tbutton> <z:tbutton id="delButton" priv="attach_modify" onClick="del()">
					<img src="../Icons/icon003a3.gif" />ɾ��</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:4px 10px;">�г��� �� <input type="text" id="StartDate" style="width:100px; " ztype='date'> �� <input type="text" id="EndDate" style="width:100px; " ztype='date'> &nbsp;�ؼ���: <input name="SearchName" type="text" id="SearchName" onFocus="delKeyWord();" style="width:110px"> <input type="button" name="Submitbutton" value="��ѯ" onClick="doSearch()" id="Submitbutton"></td>
			</tr>
			<tr>
				<td style="padding:0px 5px;">
				<z:datagrid id="dg1" method="com.nswt.cms.resource.AttachmentLib.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" ztype="selector" field="id">&nbsp;</td>
							<td width="18%"><b>��������</b></td>
							<td width="6%" align="center"><b>��С</b></td>
							<td width="5%"><b>��ʽ</b></td>
							<td width="11%"><b>������</b></td>
							<td width="10%"><b>�ϴ�ʱ��</b></td>
							<td width="15%"><b>����</b></td>
							<td width="5%"><b>����</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}${LockImage}</td>
							<td align="center">${FileSize}</td>
							<td>${SuffixImage}</td>
							<td>${AddUser}</td>
							<td title="${AddTime}">${AddTimeName}</td>
							<td><a href="javascript:DetailEdit()">�鿴/�༭</a></td>
							<td><a href="${AttachmentLink}" target="_blank"><img src="../Framework/Images/icon_down.gif" width="15" height="15" style="cursor:pointer;" title="���ش���:${Prop1}"/></a></td>
						</tr>
						<tr ztype="edit">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><input name="text" type="text" class="input1" id="Name" size="30" value="${Name}"></td>
							<td align="center">${FileSize}</td>
							<td>${SuffixImage}</td>
							<td>${AddUser}</td>
							<td title="${AddTime}">${AddTimeName}</td>
							<td><a href="javascript:DetailEdit()">�鿴/�༭</a></td>
							<td><img src="../Framework/Images/icon_down.gif" width="15" height="15" /></td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="center">${PageBar}</td>
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
