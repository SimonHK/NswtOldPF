<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>�ĵ��б�</title>
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
		
		dc.add("PageIndex",DataGrid.getParam("dg1",Constant.PageIndex));
		dc.add("PageSize",DataGrid.getParam("dg1",Constant.Size));
		
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
	if($V("Type")=="ARCHIVE"){
		return;
	}
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

function topublish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��ҪתΪ�����������£�");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.document.ArticleList.topublish",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
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

function changeToPublish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��ҪתΪ�ѷ��������£�");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.nswt.cms.document.ArticleList.changeToPublish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			$("dg1").loadData();
		}
	});
}

function publishCatalog(){
	var diag = new Dialog("Diag1");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "����";
	diag.URL = "Site/CatalogGenerateDialog.jsp";
	diag.onLoad = function(){
		if(nodeType == "0"){
			$DW.$("tr_Flag").style.display="none";
		}
	};
	diag.OKEvent = publishCatalogSave;
	diag.show();
}

function publishCatalogSave(){
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();
	var dc = $DW.Form.getData("form2");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	dc.add("type",nodeType);
	dc.add("CatalogID",Tree.CurrentItem.getAttribute("cid"));
	Server.sendRequest("com.nswt.cms.site.Catalog.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var p = new Progress(response.get("TaskID"),"�������ɾ�̬�ļ�...");
			p.show();
		}
	});
}

function publishIndex(){
	var diag = new Dialog("Diag1");
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "����";
	diag.URL = "Site/CatalogGenerateDialog.jsp";
	diag.onLoad = function(){
		$DW.$("tr_Flag").style.display="none";
	};
	diag.OKEvent = publishIndexSave;
	diag.show();
}

function publishIndexSave(){
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();
  var dc = new DataCollection();
	Server.sendRequest("com.nswt.cms.site.CatalogSite.publishIndex",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var taskID = response.get("TaskID");
			var p = new Progress(taskID,"������ҳ...");
			p.show();
		}
	});
}

function move(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫת�Ƶ��ĵ���");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 390;
	diag.Title = "ѡ��ת����Ŀ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=0";
	diag.OKEvent = moveSave;
	diag.show();
}

function moveSave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫת�Ƶ��ĵ���");
		return;
	}
	
	var arrDest = $DW.$NV("CatalogID");
	if(arrDest == null || arrDest.length==0){
		Dialog.alert("����ѡ��ת�Ƶ�Ŀ����Ŀ��");
		return;
	}
	
	if ($V("CatalogID") == arrDest[0]) {
		Dialog.alert("����ѡ����ĵ��Ѿ��ڸ���Ŀ�£���ѡ��������Ŀ��");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("ArticleIDs",arr.join());
	dc.add("CatalogID",arrDest[0]);
	Dialog.wait("����ת���ĵ�...");
	Server.sendRequest("com.nswt.cms.document.ArticleList.move",dc,function(response){
		Dialog.closeEx();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("ת���ĵ��ɹ�");
			$D.close();
			DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
			DataGrid.setParam("dg1",Constant.PageIndex,0);
	  		DataGrid.loadData("dg1");
		}
	});
}


function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶ��ĵ���");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 420;
	diag.Title = "ѡ���Ƶ�����Ŀ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=1&Action=copy";
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶ��ĵ���");
		return;
	}
	
	var arrDest = $DW.$NV("CatalogID");
	if(arrDest == null || arrDest.length==0){
		Dialog.alert("����ѡ���Ƶ�Ŀ����Ŀ��");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("ArticleIDs",arr.join());
	dc.add("CatalogIDs",arrDest.join());
	dc.add("ReferType",$DW.$NV("ReferType"));
	Dialog.wait("���ڸ����ĵ�...");
	Server.sendRequest("com.nswt.cms.document.ArticleList.copy",dc,function(response){
		Dialog.closeEx();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�ɹ������ĵ�",function(){
				$D.close();
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      		DataGrid.loadData("dg1");
			});
		}
	});
}

Page.onLoad(function(){
	$("dg1").onContextMenu = function(tr,evt){
		evt = getEvent(evt);
		var menu = new Menu();
		menu.Width = 150;
		menu.setEvent(evt);
		menu.setParam([]);
		
		var arr = DataGrid.getSelectedValue("dg1");
		var flag = false;
		if(arr && arr.length>1){
			flag = true;
		}
	
		if(Tree.CurrentItem&&Application.getPriv("article",Tree.CurrentItem.getAttribute("innerCode")
				,"article_modify")&&$V("Type")!="ARCHIVE") {
			menu.addItem("�½�",add,"Icons/icon003a2.gif");
			menu.addItem("�༭",edit,"Icons/icon003a4.gif",flag);
			menu.addItem("ɾ��",del,"Icons/icon003a3.gif");
		
			menu.addItem("-");
			menu.addItem("����",publish,"Icons/icon003a13.gif");
			menu.addItem("����",sortOrder,"Icons/icon400a4.gif");
			
			var rows = $("dg1").getSelectedRows();
			var ds = $("dg1").DataSource;
			var topFlag = true;//��ʾ����ѡ������ö�״̬��һ��
			var first = ds.Rows[rows[0].rowIndex-1].get("TopFlag");
			for(var i=1;i<rows.length;i++){
				 if(ds.Rows[rows[i].rowIndex-1].get("TopFlag")!=first){
				 	topFlag = false;
					break;
				 }
			}
			if(topFlag){
				if(first=="0"){
					menu.addItem("�ö�",setTop);
				}else{
					menu.addItem("ȡ���ö�",setNotTop);
				}
			}else{
				menu.addItem("�ö�",setTop,null,true);
			}
			menu.addItem("-");
			menu.addItem("����",copy,"Icons/icon003a7.gif");
			menu.addItem("ת��",move,"Icons/icon003a7.gif");
			menu.addItem("-");
			menu.addItem("�汾",historyVersion,"Icons/icon026a12.gif",flag);
			menu.addItem("������ʷ",articleLog,"Icons/icon_column.gif",flag);
			var downFlag = 1;//1��ʾȫ���������ߣ�-1��ʾȫ������ȡ�����ߣ�0��ʾ���ӣ����ܽ��в���
			first = ds.Rows[rows[0].rowIndex-1].get("Status");
			for(var i=1;i<rows.length;i++){
				 if(ds.Rows[rows[i].rowIndex-1].get("Status")!=first){
				 	downFlag = 0;
					break;
				 }
			}
			if(downFlag!=0){
				if(first=="40"){
					menu.addItem("-");
					menu.addItem("����",up,"Icons/icon026a7.gif");
				}else if(first=="30"){
					menu.addItem("-");
					menu.addItem("����",down,"Icons/icon026a5.gif");
				}
			}
		}
		menu.addItem("Ԥ��",preview,"Icons/icon403a3.gif");
		if($V("Type")=="ARCHIVE"){
			menu.addItem("תΪ�ѷ���",changeToPublish,"Icons/icon003a13.gif");
		}
		menu.addItem("������Excel",function(){DataGrid.toExcel("dg1")},"Framework/Images/FileType/xls.gif");
		menu.show();
	}
});

function down(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ���ĵ���");
		return;
	}
	var dc = new DataCollection();
	dc.add("ArticleIDs",arr.join(","));
	Server.sendRequest("com.nswt.cms.document.ArticleList.down",dc,function(response){
		if(response.Status==1){
			Dialog.alert("�����ɹ�!",function(){
				DataGrid.loadData("dg1");
			});
		} else {
			Dialog.alert(response.Message);
		}																			
	});
}

function up(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ���ĵ���");
		return;
	}
	var dc = new DataCollection();
	dc.add("ArticleIDs",arr.join(","));
	Server.sendRequest("com.nswt.cms.document.ArticleList.up",dc,function(response){
		if(response.Status==1){
			Dialog.alert("�����ɹ�!",function(){
				DataGrid.loadData("dg1");
			});
		}																			
	});
}

function historyVersion(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ���ĵ���");
		return;
	}
	var id = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 380;
	diag.Title = "�汾";
	diag.URL = "Document/ArticleVersionDialog.jsp?ArticleID="+id;
	diag.onLoad = function(){
	};	
	diag.show();
	$E.hide(diag.OKButton);
	diag.CancelButton.value = "ȷ ��";
}

function articleLog(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ���ĵ���");
		return;
	}
	var id = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 350;
	diag.Title = "������ʷ";
	diag.URL = "Document/ArticleLogDialog.jsp?ArticleID="+id;
	diag.onLoad = function(){
	};
	diag.show();
	$E.hide(diag.OKButton);
	diag.CancelButton.value = "ȷ ��";
}

function sortOrder(){
	if(DataGrid.getParam("dg1",Constant.SortString)){
		Dialog.confirm("Ĭ�������²ſ��ܵ���˳���Ƿ�Ҫ�л���Ĭ������",function(){
			DataGrid.setParam("dg1",Constant.SortString,"");
			DataGrid.loadData("dg1",function(){
				Dialog.alert("�л��ɹ�!");
			});
		});
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ���ĵ���");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 310;
	diag.Title = "�����ĵ�˳��";
	diag.URL = "Document/ArticleSortDialog.jsp?CatalogID="+DataGrid.getParam("dg1","CatalogID");
	diag.onLoad = function(){
	};
	diag.OKEvent =  sortOrderSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�����ĵ�˳��";
	diag.Message = "�����ĵ�˳��ʹ�ĵ������ڵ�ǰ�Ի�����ѡ�е��ĵ�֮ǰ��";
	diag.show();
}

function sortOrderSave(){
	var dt = $DW.DataGrid.getSelectedData("dg1");
	if(!dt||dt.Rows.length<1){
		Dialog.alert("����ѡ����!");
		return;
	}
	var dc = new DataCollection();
	dc.add("TopFlag","false");
	dc.add("Target",dt.get(0,"OrderFlag"));
	if(dt.get(0,"TopFlag")=="1"){
		dc.add("TopFlag","true");
	}
	var arr = [];
	dt = DataGrid.getSelectedData("dg1");
	for(var i=0;i<dt.Rows.length;i++){
		arr.push(dt.get(i,"OrderFlag"));
	}
	dc.add("Orders",arr.join(","));
	dc.add("Type","Before");
	dc.add("CatalogID",$V("CatalogID"));
	Dialog.wait("���ڵ����ĵ�˳��...");
	Server.sendRequest("com.nswt.cms.document.ArticleList.sortArticle",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			Dialog.alert("�����ɹ�!",function(){
				$D.close();
				DataGrid.loadData("dg1");
			});
		}
	});
}

function setTop(){	
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		Dialog.alert("����ѡ���ĵ�!");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "�����ö�";
	diag.URL = "Document/ArticleTopDialog.jsp?IDs=" + arr.join(",");
	diag.OKEvent = setTopSave;
	diag.show();
}

function setTopSave(){	
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.nswt.cms.document.ArticleList.setTop",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}

function setNotTop(id){	
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		Dialog.alert("����ѡ���ĵ�!");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	Server.sendRequest("com.nswt.cms.document.ArticleList.setNotTop",dc,function(response){
		if(response.Status==1){
			Dialog.alert("�����ɹ�!",function(){
				DataGrid.loadData("dg1");
			});
		}
	});
}

function changeType(){
   	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.setParam("dg1","Type",$V("Type"));
	onlyPreview();
	DataGrid.loadData("dg1");
}

function onlyPreview(){
	if($V("Type")=="ARCHIVE"){
		$("BtnAdd").disable();
		$("BtnEdit").disable();
		$("BtnToPublish").disable();
		$("BtnPublish").disable();
		$("BtnCopy").disable();
		$("BtnMove").disable();
		$("BtnOrder").disable();
		$("BtnDel").disable();
	}else{
		initButtons(Tree.CurrentItem);
	}
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		if($V("CatalogID")&&$V("CatalogID")!="0"){
			window.open("../Site/Preview.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));
		}else{
			window.open("../Site/Preview.jsp?Type=0&SiteID="+Cookie.get("SiteID"));
		}
	}else{
		window.open("Preview.jsp?ArticleID="+arr[0]);
	}	
}


function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogID",cid);
	try{
		if(!Tree.isRoot(ele)){
			Cookie.set("DocList.LastCatalog",cid,"2100-01-01");
			Cookie.set("DocList.LastCatalogCode",code,"2100-01-01");
		}else{
			Cookie.set("DocList.LastCatalog","0","2100-01-01");
		}
		DataGrid.setParam("dg1","CatalogID",cid);
		DataGrid.setParam("dg1","StartDate","");//��Ŀ�л�ʱ�����ʱ��͹ؼ���ѡ��
		DataGrid.setParam("dg1","EndDate","");
		DataGrid.setParam("dg1","Keyword","");
		DataGrid.setParam("dg1", Constant.PageIndex, 0);	
		$S("StartDate","");
		$S("EndDate","");
		$S("Keyword","");
		DataGrid.loadData("dg1");
		if(!initButtons(ele)){
			return;
		}
		Application.setAllPriv("article",code);
		if($V("Type")=="ARCHIVE"){
			$("BtnAdd").disable();
			$("BtnEdit").disable();
			$("BtnToPublish").disable();
			$("BtnPublish").disable();
			$("BtnCopy").disable();
			$("BtnMove").disable();
			$("BtnOrder").disable();
			$("BtnDel").disable();
		}
	}catch(ex){alert(ex.message);}
}

function initButtons(ele){
	if(Tree.hasChild(ele)||Tree.isRoot(ele)){//�п��ܸ��ڵ���û���ӽڵ�
		if(Tree.isRoot(ele)){
			Misc.setButtonText("BtnPublish","����վ����ҳ");
			$("BtnPublish").onclick = publishIndex;
			$("BtnToPublish").disable();
			$("BtnAdd").disable();
			$("BtnEdit").disable();
			$("BtnPreview").enable();
			$("BtnMove").disable();
			$("BtnCopy").disable();
			$("BtnDel").disable();
			$("BtnOrder").disable();
			return false;
		}else{
			Misc.setButtonText("BtnPublish","����");
			$("BtnPublish").onclick = publish;
			$("BtnToPublish").enable();
			$("BtnAdd").enable();
			$("BtnEdit").enable();
			$("BtnPreview").enable();
			$("BtnMove").enable();
			$("BtnCopy").enable();
			$("BtnDel").enable();
			$("BtnOrder").enable();	
			return true;
		}
	}else{
		Misc.setButtonText("BtnPublish","����");
		$("BtnPublish").onclick = publish;
		if($V("Type")=="ARCHIVE"){
			$("BtnAdd").disable();
			$("BtnEdit").disable();
			$("BtnToPublish").disable();
			$("BtnPublish").disable();
			$("BtnCopy").disable();
			$("BtnMove").disable();
			$("BtnOrder").disable();
			$("BtnDel").disable();
		}else{
			$("BtnPublish").enable();
			$("BtnToPublish").enable();
			$("BtnAdd").enable();
			$("BtnEdit").enable();
			$("BtnPreview").enable();
			$("BtnMove").enable();
			$("BtnCopy").enable();
			$("BtnDel").enable();
			$("BtnOrder").enable();		
		}
		return true;
	}
}

Page.onReady(init);

function init(){
	if(Cookie.get("DocList.LastCatalog")=="0" || !Application.getPriv('article'
			,Cookie.get("DocList.LastCatalogCode"),'article_browse')){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		Misc.setButtonText("BtnPublish","����վ����ҳ");
		$("BtnPublish").onclick = publishIndex;
		$("BtnAdd").disable();
		$("BtnEdit").disable();
		$("BtnPreview").enable();
		$("BtnMove").disable();
		$("BtnCopy").disable();
		$("BtnDel").disable();
		$("BtnOrder").disable();
	}else{
		var node = Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
		Tree.selectNode(node,true);
		Tree.scrollToNode(node);	
		$S("CatalogID",Cookie.get("DocList.LastCatalog"));
		initButtons(Tree.CurrentItem);
		Application.setAllPriv("article",Tree.CurrentItem.getAttribute("innerCode"));
	}
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

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	if(DataGrid.getParam("dg1",Constant.SortString)){
		Dialog.confirm("Ĭ�������²ſ��ܵ���˳���Ƿ�Ҫ���л���Ĭ������",function(){
			DataGrid.setParam("dg1",Constant.SortString,"");
			DataGrid.loadData("dg1",function(){
				Dialog.alert("�л��ɹ�!");
			});
		});
		return;
	}
	var order = sourceDr.get("OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	dc.add("TopFlag","false");
	var i = rowIndex-1;
	if(i!=0){
		target = targetDr.get("OrderFlag");
		dc.add("Type","After");
		if(rowIndex<ds.getRowCount()){
			var topFlag = targetDr.get("TopFlag");
			if(topFlag=="1"){
				dc.add("TopFlag","true");
			}
		}		
	}else{
		dc.add("Type","Before");
		target = $("dg1").DataSource.get(1,"OrderFlag");
		var topFlag = ds.get(1,"TopFlag");
		if(topFlag=="1"){
			dc.add("TopFlag","true");
		}
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	dc.add("CatalogID",$V("CatalogID"));
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.cms.document.ArticleList.sortArticle",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});	
}

function afterTreeDragEnd(evt){
	var item = this;
	var catalogID = item.$A("cid");
	var row = DragManager.DragSource.parentNode;
	var dc = new DataCollection();
	dc.add("ArticleIDs",$("dg1").DataSource.get(row.rowIndex-1,"ID"));
	dc.add("CatalogID",catalogID);
	Dialog.wait("���Ե�...");
	Server.sendRequest("com.nswt.cms.document.ArticleList.move",dc,function(response){
		Dialog.endWait();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				item.onclick.apply(item);
			}
		});
	});
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	if(!cid){
		return ;
	}
	Tree.selectNode(ele,true);
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cid);
	menu.addItem("Ԥ����Ŀ",function(){window.open("../Site/Preview.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));},"Icons/icon403a3.gif");
	if(Application.getPriv("article",ele.getAttribute("innercode"),"article_modify")) {
		menu.addItem("������Ŀ",publishCatalog,"/Icons/icon003a13.gif");
		menu.addItem("������Excel",function(){DataGrid.toExcel("dg1")},"Framework/Images/FileType/xls.gif");
	}
	menu.show();
}

</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
																 style="height:440px;width:160px;"
																 method="com.nswt.cms.document.ArticleList.treeDataBind" level="2"
																 lazy="true" resizeable="true">
					<p cid='${ID}' innercode='${InnerCode}'
						onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
						oncontextmenu="showMenu(event,this);stopEvent(event);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton id="BtnAdd"
					priv="article_modify" onClick="add()">
					<img src="../Icons/icon003a2.gif" width="20" height="20" />�½�</z:tbutton> <z:tbutton
					id="BtnEdit" priv="article_modify"
					onClick="DataGrid.edit(event,'dg1')">
					<img src="../Icons/icon003a4.gif" width="20" height="20" />�༭</z:tbutton> <z:tbutton
					id="BtnToPublish" priv="article_modify" onClick="topublish()">
					<img src="../Icons/icon003a6.gif" width="20" height="20" />������</z:tbutton> <z:tbutton
					id="BtnPublish" priv="article_modify" onClick="publish()">
					<img src="../Icons/icon003a13.gif" width="20" height="20" />����</z:tbutton> <z:tbutton
					id="BtnPreview" priv="article_browse" onClick="preview()">
					<img src="../Icons/icon403a3.gif" width="20" height="20" />Ԥ��</z:tbutton> <z:tbutton
					id="BtnCopy" priv="article_modify" onClick="copy()">
					<img src="../Icons/icon003a12.gif" width="20" height="20" />����</z:tbutton> <z:tbutton
					id="BtnMove" priv="article_modify" onClick="move()">
					<img src="../Icons/icon003a7.gif" width="20" height="20" />ת��</z:tbutton> <z:tbutton
					id="BtnOrder" priv="article_modify" onClick="sortOrder()">
					<img src="../Icons/icon400a4.gif" width="20" height="20" />����</z:tbutton> <z:tbutton
					id="BtnDel" priv="article_modify" onClick="del()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />ɾ��</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">�г�: <z:select id="Type"
					onChange="changeType()" value="ALL" style="width:90px;">
					<select>
					<option value="ALL" selected="true">�����ĵ�</option>
					<option value="ADD">�Ҵ������ĵ�</option>
					<option value="WORKFLOW">��ת�е��ĵ�</option>
					<option value="TOPUBLISH">���������ĵ�</option>
					<option value="PUBLISHED">�ѷ������ĵ�</option>
					<option value="OFFLINE">�����ߵ��ĵ�</option>
					<option value="ARCHIVE">�ѹ鵵���ĵ�</option>
                    <option value="Editer">���б༭Ͷ��</option>
                    <option value="Member">���л�ԱͶ��</option>
					</select>
		  </z:select> &nbsp;�� <input type="text" id="StartDate" style="width:90px; "
					ztype='date'> �� <input type="text" id="EndDate"
					style="width:90px; " ztype='date'> &nbsp;�ؼ���: <input
					name="Keyword" type="text" id="Keyword"> <input
					type="button" name="Submitbutton" id="Submitbutton" value="��ѯ"
					onClick="search()"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg1" method="com.nswt.cms.document.ArticleList.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="5%"><strong>����</strong></td>
							<td width="48%" sortfield="title" direction=""><b>����</b></td>
							<td width="7%"><strong>������</strong></td>
							<td width="17%"><strong>״̬</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>����ʱ��</strong></td>
						</tr>
						<tr onDblClick="edit(${ID});">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td algin="center">${Icon}</td>
							<td style="${TitleStyle}"><span title="${Title}">${Title}</span></td>
							<td>${AddUser}</td>
							<td>${StatusName}</td>
							<td title="${PublishDate}">${PublishDate}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
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
