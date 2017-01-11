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
var tabType="User";

Page.onLoad(function(){
	init();
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		dr.set("Tag",$V("Tag"));
		return true;
	}
});

function init(){
	var flag = false;
	if(!Cookie.get("Resource.LastAudioLib")||Cookie.get("Resource.LastAudioLib")=="0"){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		flag = true;
	}else{
		Tree.select("tree1","cid",Cookie.get("Resource.LastAudioLib"));
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
		if(Application.getPriv("audio",Application.CurrentSite,"audio_manage")){
			menu.setEvent(event);
			menu.addItem("�½�",add,"/Icons/icon018a2.gif");
		}
	}else{
		if(Application.getPriv("audio",ele.getAttribute("InnerCode"),"audio_manage")){
			menu.setEvent(event);
			menu.addItem("�½�",add,"/Icons/icon018a2.gif");
			menu.addItem("�޸�",change,"/Icons/icon018a4.gif");
			menu.addItem("ɾ��",delLib,"/Icons/icon018a3.gif");
			menu.addItem("����",sortLib,"/Icons/icon400a13.gif");
		}
	}
	menu.show();
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
	dc.add("CatalogType","6");
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
	diag.Height = 220;
	diag.Title = "�޸���Ƶ����";
	diag.URL = "Resource/AudioLibEditDialog.jsp?ID="+cid;
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
	 
	Server.sendRequest("com.nswt.cms.resource.AudioLib.AudioLibEdit",dc,function(response){
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
		if(Application.getPriv("audio",Application.CurrentSite,"audio_manage")){
			$("addLibButton").enable();
		} else {
			$("addLibButton").enable();
		}
		$("uploadButton").disable();
		$("repeatUploadButton").disable();
		$("editButton").disable();
		$("saveButton").disable();
		$("publishButton").disable();
		$("copyButton").disable();
		$("transferButton").disable();
		$("delButton").disable();
		return ;
	}
	Cookie.set("Resource.LastAudioLib",cid,"2100-01-01")
	$("uploadButton").enable();
	$("repeatUploadButton").enable();
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.loadData("dg1");
	Application.setAllPriv("audio",ele.getAttribute("InnerCode"));
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
		catalogName = "��Ƶ��";
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 240;
	diag.Title = "�½���Ƶ����";
	diag.URL = "Resource/AudioLibAddDialog.jsp?ParentID="+ ParentID;
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("ParentID"),ParentID,catalogName);
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
	Server.sendRequest("com.nswt.cms.resource.AudioLib.add",dc,function(response){
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
	diag.Width = 800;
	diag.Height = 320;
	diag.Title = "�ϴ���Ƶ";
	diag.URL = "Resource/AudioLibDialog.jsp?CatalogID="+CatalogID;
	diag.onLoad = function(){
		$DW.$("AudioBrowse").hide();
		
	};
	diag.OKEvent = OK;
	diag.show();
}

function OK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("AudioUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("AudioBrowse")){
	 	currentTab.contentWindow.onReturnBack();
	}
}

function onUploadCompleted(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","CatalogID",Tree.CurrentItem.getAttribute("cid"));
	DataGrid.loadData("dg1");
	if($D){
		setTimeout(function(){$D.close()},100);
	}
}

function RepeatUpload(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�����ϴ�����Ƶ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "�����ϴ�";
	diag.URL = "Resource/AudioRepeatUpload.jsp?ID="+arr[0];
	diag.OKEvent = RepeatOK;
	diag.show();
	diag.CancelButton.value="�ر�";
}

function RepeatOK(){
	$DW.RepeatUpload();
}

function save(){
	DataGrid.save("dg1","com.nswt.cms.resource.AudioLib.dg1Edit",function(){
		DataGrid.loadData('dg1');
	});
}

function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶ���Ƶ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 60;
	diag.Title = "������Ƶ";
	diag.URL = "Resource/AudioCopyDialog.jsp?CatalogID="+Tree.CurrentItem.getAttribute("cid");
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("CatalogID"),Tree.CurrentItem.getAttribute("cid"),Tree.CurrentItem.innerText);
	}
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶ���Ƶ��");
		return;
	}
	if(!$DW.$V("CatalogID")||$DW.$V("CatalogID")=="0"){
		Dialog.alert("��Ƶ����ת�Ƶ�վ���£�");
		return;
	}
	if($DW.$V("CatalogID")==Tree.CurrentItem.getAttribute("cid")){
		Dialog.alert("��Ƶ�Ѿ��������������ˣ�������ѡ����һ���������࣡");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("CatalogID",$DW.$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.resource.Audio.copy",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
			}
		});
	});
}

function transfer(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫת�Ƶ���Ƶ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 60;
	diag.Title = "ת����Ƶ";
	diag.URL = "Resource/AudioCopyDialog.jsp?CatalogID="+Tree.CurrentItem.getAttribute("cid");
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("CatalogID"),Tree.CurrentItem.getAttribute("cid"),Tree.CurrentItem.innerText);
	}
	diag.OKEvent = transferSave;
	diag.show();
}

function transferSave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫת�Ƶ���Ƶ��");
		return;
	}
	if(!$DW.$V("CatalogID")||$DW.$V("CatalogID")=="0"){
		Dialog.alert("��Ƶ����ת�Ƶ�վ���£�");
		return;
	}
	if($DW.$V("CatalogID")==Tree.CurrentItem.getAttribute("cid")){
		Dialog.alert("��Ƶ�Ѿ��������������ˣ�������ѡ����һ���������࣡");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("CatalogID",$DW.$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.resource.Audio.transfer",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
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
	Dialog.confirm("�÷����µ���Ƶ������ɾ������ȷ��Ҫɾ������Ƶ������",function(){
		var dc = new DataCollection();
		dc.add("catalogID",catalogID);
		Server.sendRequest("com.nswt.cms.resource.AudioLib.delLib",dc,function(response){
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
		Dialog.alert("����ѡ��Ҫɾ������Ƶ��");
		return;
	}
	Dialog.confirm("��Щ��Ƶ���������»�����Ƶ�������й�������ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.resource.Audio.del",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
				}
			});
		});
	})
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
	if (keyWord == "��������Ƶ����") {
		$S("SearchName","");
	}
}

function doSearch(){
	var name = "";
	if ($V("SearchName") != "��������Ƶ����") {
		name = $V("SearchName");
	}
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Name",name);
	DataList.setParam("dg1","StartDate",$V("StartDate"));
	DataList.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function publish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ��������Ƶ��");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.resource.AudioLib.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			var p = new Progress(response.get("TaskID"),"���ڷ���...");
			p.show();
		}
	});
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
		cid = Cookie.get("Resource.LastAudioLib");
	}
	dc.add("CatalogID",cid);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.cms.resource.AudioLib.sortColumn",dc,function(response){
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
	diag.Title = "�鿴/�༭��Ƶ��Ϣ";
	diag.URL = "Resource/AudioEditDialog.jsp?ID="+arr[0];
	diag.onLoad = function(){
		$DW.$("Name").focus();		
	};
	diag.OKEvent = DetailSave;
	diag.show();
}

function DetailSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.nswt.cms.resource.Audio.dialogEdit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
			}
		});
	});	
}
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="1" class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd">
					<z:tree id="tree1" style="height:450px;width:160px;" method="com.nswt.cms.resource.AudioLib.treeDataBind" level="2" lazy="true">
					<p cid='${ID}' cname='${Name}' InnerCode='${InnerCode}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton id="addLibButton" priv="audio_manage" onClick="add()">
					<img src="../Icons/icon051a2.gif" />�½�����</z:tbutton> <z:tbutton id="uploadButton" priv="audio_modify" onClick="upload()">
					<img src="../Icons/icon051a13.gif" />�ϴ�</z:tbutton> <z:tbutton id="repeatUploadButton" priv="audio_modify" onClick="RepeatUpload()">
					<img src="../Icons/icon051a13.gif" />�����ϴ�</z:tbutton><z:tbutton id="editButton" priv="audio_modify" onClick="DetailEdit()">
					<img src="../Icons/icon051a4.gif" width="20" height="20" />�༭</z:tbutton>
					<z:tbutton id="saveButton" priv="audio_modify" onClick="save()">
					<img src="../Icons/icon051a16.gif" width="20" height="20" />����</z:tbutton> 
					<z:tbutton id="publishButton" priv="audio_modify" onClick="publish()">
					<img src="../Icons/icon051a5.gif" />����</z:tbutton> <z:tbutton id="copyButton" priv="audio_modify" onClick="copy()">
					<img src="../Icons/icon051a8.gif" />����</z:tbutton> <z:tbutton id="transferButton" priv="audio_modify" onClick="transfer()">
					<img src="../Icons/icon051a7.gif" />ת��</z:tbutton> <z:tbutton id="delButton" priv="audio_modify" onClick="del()">
					<img src="../Icons/icon051a3.gif" />ɾ��</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:4px 10px;">�г��� �� <input type="text" id="StartDate" style="width:100px; " ztype='date'> �� <input type="text" id="EndDate" style="width:100px; " ztype='date'> &nbsp;�ؼ���: <input name="SearchName" type="text" id="SearchName" onFocus="delKeyWord();" style="width:110px"> <input type="button" name="Submitbutton" value="��ѯ" onClick="doSearch()" id="Submitbutton"></td>
			</tr>
			<tr>
				<td style="padding:0px 5px;"><z:datagrid id="dg1" method="com.nswt.cms.resource.AudioLib.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="5%" ztype="selector" field="id">&nbsp;</td>
							<td width="30%"><b>��Ƶ����</b></td>
							<td width="8%" align="right"><b>��С</b></td>
							<td width="12%"><b>��ǩ</b></td>
							<td width="7%"><b>��ʽ</b></td>
							<td width="8%"><b>������</b></td>
							<td width="14%"><b>�ϴ�ʱ��</b></td>
							<td width="6%"><b>����</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td align="right">${FileSize}</td>
							<td>${Tag}</td>
							<td>${Suffix}</td>
							<td>${AddUser}</td>
							<td title="${AddTime}">${AddTimeName}</td>
							<td><embed name="movie" type="application/x-shockwave-flash" src="../Tools/musicplayer.swf" width="17" height="17" wmode="transparent" quality="high" flashvars="song_url=..${Alias}${Path}${FileName}"></embed></td>
						</tr>
						<tr ztype="edit">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><input name="text" type="text" class="input1" id="Name" value="${Name}"></td>
							<td align="right">${FileSize}</td>
							<td><input name="text" type="text" style="80px" class="input1" id="Tag" value="${Tag}"></td>
							<td>${Suffix}</td>
							<td>${AddUser}</td>
							<td>${AddTimeName}</td>
                            <td><a href="javascript:DetailEdit()">�鿴/�༭</a></td>
							<td>&nbsp;</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="11" align="center">${PageBar}</td>
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
