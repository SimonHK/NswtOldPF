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

function config(){
/*
	var catalogID = $V("CatalogID");
	if(catalogID==""){
		Dialog.alert("��ѡ����Ŀ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 850;
	diag.Height = 480;
	diag.Title = "����";
	diag.URL = "Document/NewspaperPageConfig.jsp?CatalogID=" + catalogID;
	//diag.OKEvent = moveSave;
	diag.show();
*/
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
  var url="NewspaperPageConfig.jsp?CatalogID=" + catalogID;
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

function openArticle(ArticleID){
	var width  = (screen.availWidth-10)+"px";
  var height = (screen.availHeight-50)+"px";
  var leftm  = 0;
  var topm   = 0;
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  var url="Article.jsp?ArticleID=" + ArticleID;
  var w = window.open(url,"",args);
  if ( !w ){
		Dialog.alert( "���ֵ������ڱ���ֹ���������������ã��Ա�����ʹ�ñ�����!" ) ;
		return ;
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
			Dialog.alert("�ɹ������ĵ�");
			DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
			DataGrid.setParam("dg1",Constant.PageIndex,0);
      DataGrid.loadData("dg1");
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
	diag.Height = 400;
	diag.Title = "ѡ��ת����Ŀ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=1";
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
	
	var dc = new DataCollection();
	dc.add("ArticleIDs",arr.join());
	dc.add("CatalogIDs",arrDest.join());
	Server.sendRequest("com.nswt.cms.document.ArticleList.move",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�ɹ�ת���ĵ�");
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
	diag.Height = 400;
	diag.Title = "ѡ���Ƶ�����Ŀ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=1";
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
	Server.sendRequest("com.nswt.cms.document.ArticleList.copy",dc,function(response){
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

function showMenu (event,name){
	var evt = getEvent(event);
	var menu = new Menu();
	menu.setEvent(evt);
	var param = [];
	param.push(name);
	menu.setParam(param);
	menu.addItem("�½�",window.parent.ArticleTopFrame.add,"Icons/icon018a2.gif");
	menu.addItem("��",openA,"Icons/icon029a2.gif");
	menu.addItem("ɾ��",del,"Icons/icon018a3.gif");
	menu.addItem("-");
	menu.addItem("ǩ��",publish,"Icons/icon018a3.gif");
	menu.addItem("Ԥ��",preview,"Icons/icon018a3.gif");
	menu.addItem("-");
	menu.addItem("����",lock,"Icons/icon018a4.gif");
	menu.addItem("����",unLock,"Icons/icon018a4.gif");
	
	menu.addItem("-");
	menu.addItem("��ע",note,"Icons/icon018a3.gif");
	menu.addItem("��ʷ�汾",historyVersion,"Icons/icon018a3.gif");
	menu.show();
	
}

function edit(dr){
	var articleID = dr.get("ID");
	openArticle(articleID);
}

function save(){
}


function openA(param){
	openArticle(param.childNodes[0]);
}
function paste(param){
	alert(param.childNodes[0]);
	alert("ճ��"+param);
}

function lock(param){
	alert("����"+param);
}
function unLock(param){
	alert("����"+param);
}

function note(param){
	alert("��ע"+param);
}

function historyVersion(param){
	alert("��ʷ�汾"+param);
}
function refreshX(){
	window.location.reload();
}

function preview(){
}

function onTreeClick(ele){
	var cid=  ele.getAttribute("cid");
	//DataGrid.setParam("dg1",Constant.PageIndex,0);
	//DataGrid.setParam("dg1","IssueID",$V("IssueID"));
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.loadData("dg1");
	$S("CatalogID",cid);
	dealBtn("Article",ele.getAttribute("innerCode"));
}

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	//DataGrid.setParam("dg1","IssueID",$V("IssueID"));
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.loadData("dg1");
}

function changeIssue(){
	if($V("CatalogID")==""){
		return
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","IssueID",$V("IssueID"));
	DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.loadData("dg1");
}

function dealBtn(IDType,ID){
	window.top.Privilege.setBtn(IDType,ID,"article_inserted",$("article_inserted"));
	window.top.Privilege.setBtn(IDType,ID,"article_updated",$("article_updated"));
	window.top.Privilege.setBtn(IDType,ID,"article_deleted",$("article_deleted"));
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
					style="height:450px;width:160px;"
					method="com.nswt.cms.site.Newspaper.docTreeDataBind">
					<p cid='${ID}' innercode='${InnerCode}'
						onClick="onTreeClick(this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td style="padding:8px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton id="article_inserted"
					onClick="config()">
					<img src="../Icons/icon018a2.gif" />����</z:tbutton> <z:tbutton
					id="article_inserted" onClick="add()">
					<img src="../Icons/icon018a2.gif" />�½�</z:tbutton> <z:tbutton
					id="article_updated" onClick="save()">
					<img src="../Icons/icon018a16.gif" />����</z:tbutton> <z:tbutton
					id="article_deploy" onClick="publish()">
					<img src="../Icons/icon018a4.gif" />����</z:tbutton> <z:tbutton onClick="copy()">
					<img src="../Icons/icon018a8.gif" />����</z:tbutton> <z:tbutton onClick="move()">
					<img src="../Icons/icon018a7.gif" />ת��</z:tbutton> <z:tbutton
					id="article_deleted" onClick="del()">
					<img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton></div>
				<div style="float: right; white-space: nowrap;"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
							method="com.nswt.cms.document.ArticleList.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="4%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" ztype="selector" field="id">&nbsp;</td>
							<td width="35%" sortfield="title"><b>����</b></td>
							<td width="10%"><strong>����</strong></td>
							<td width="18%" sortfield="addtime"><strong>����ʱ��</strong></td>
							<td width="8%"><strong>״̬</strong></td>
							<td width="6%"><strong>ͷ��</strong></td>
							<td width="6%" ztype="editDialog" function="edit"><strong>�༭</strong></td>
							<td width="9%"><strong>Ԥ��</strong></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Title}</td>
							<td>${Author}</td>
							<td>${PublishDate}</td>
							<td>${Status}</td>
							<td>${TopFlag}</td>
							<td></td>
							<td><a href="./Preview.jsp?ArticleID=${ID}" target="_blank">Ԥ��</a></td>
						</tr>

						<tr ztype="pagebar">
							<td align="left">&nbsp;</td>
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
