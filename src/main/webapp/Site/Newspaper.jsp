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
<script src="../Framework/Controls/Tabpage.js"></script>
<script>
var currentTab = "Basic";
var grantType ;
var currentTreeItem,lastTreeItem;
Page.onLoad(function(){
	if(Cookie.get("Newspaper.LastCatalog")=="0" || !Application.getPriv('article'
			,'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"DocList.LastCatalog"))%>','article_browse')){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		$("Template").hide();
		$("Block").hide();
		$("Field").hide();
		$("Priv").hide();
		$("Issue").hide();
		$("Page").hide();
	} else {
		var node = Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
		Tree.selectNode(node,true);
		Tree.scrollToNode(node);	
		$S("CatalogID",Cookie.get("DocList.LastCatalog"));
		$S("CatalogInnerCode",'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"DocList.LastCatalog"))%>');
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
	}
});

Page.onClick(function(){
	var div = $("_DivContextMenu");
	if(div){
		$E.hide(div);
	}
});

function onTabChange(tab){
	var catalogID = $V("CatalogID");
	var level = currentTreeItem.getAttribute("level");
	var pageUrl;
	if(catalogID == ""){
		pageUrl = Server.ContextPath+"Site/NewspaperSiteBasic.jsp";
		$("Template").hide();
		$("Block").hide();
		$("Field").hide();
		$("Priv").hide();
		$("Issue").hide();
		$("Page").hide();
		
		if(tab!="Basic"){
			tab = "Basic";
			Tab.onChildTabClick(tab);
		}
	} else {
		if (level==1) {
			$("Template").hide();
			$("Block").hide();
			$("Field").hide();
			$("Priv").hide();
			$("Issue").show();
			if (tab=="Issue") {
				pageUrl = Server.ContextPath+"Site/NewspaperIssue.jsp?NewspaperID="+catalogID;
			} else {
				tab ="Basic";
				Tab.onChildTabClick(tab);
				pageUrl = Server.ContextPath+"Site/NewspaperBasic.jsp?NewspaperID="+catalogID;
			}
			
		} else {
			$("Template").show();
			$("Block").show();
			$("Field").show();
			$("Priv").show();
			$("Issue").hide();
			
			if(tab=="Issue"){
				tab = "Basic";
				Tab.onChildTabClick(tab);
			}
			
			if("Grant"==tab){
				if(!grantType||"Site"==grantType){
					grantType = "Catalog";
				}
				pageUrl = Server.ContextPath+"Site/Catalog"+tab+grantType+".jsp?CatalogID="+$V("CatalogID")+"&IDType="+grantType;
			}if("Basic"==tab){
				pageUrl = Server.ContextPath+"Site/NewspaperPage.jsp?CatalogID="+$V("CatalogID");
			}else{
				pageUrl = Server.ContextPath+"Site/Catalog"+tab+".jsp?CatalogID="+$V("CatalogID");
				if(tab == "Field"){
			    	pageUrl =Server.ContextPath+"Site/CatalogColumn.jsp?CatalogID="+$V("CatalogID");;
				}
			}
		}
	}
	currentTab = tab;
	Tab.getChildTab(tab).src = pageUrl;
}

function changeGrantType(){
	grantType = Tab.getChildTab("Grant").contentWindow.$V("GrantType");
	if(grantType=="0"){
		grantType = "Site";
	}else if(grantType=="1"){
		grantType = "Catalog";
	}else if(grantType=="2"){
		grantType = "Template";	
	}else if(grantType=="3"){
		grantType = "Article";
	}
	onTabChange("Grant");
}

function onTreeClick(ele){
	lastTreeItem = currentTreeItem;
	currentTreeItem = ele;
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	if(!Tree.isRoot(ele)){
		Cookie.set("Newspaper.LastCatalog",cid,"2100-01-01");
		Cookie.set("Newspaper.LastCatalogCode",code,"2100-01-01");
	}
	$S("CatalogID",cid);
	$S("CatalogInnerCode",code);
	$S("Name",ele.innerText);
	onTabChange(currentTab);
}

function save(){
	if(DataGrid.EditingRow!=null){
		DataGrid.changeStatus(DataGrid.EditingRow);
	}
	DataGrid.save("dg1","com.nswt.cms.site.Catalog.dg1Edit",function(){window.location.reload();});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 480;
	diag.Title = "�½���Ŀ";
	var parentID;
	if($V("CatalogID")==""){
		parentID = 0;
	}else {
		parentID = $V("CatalogID");
	}

	diag.URL = "Site/CatalogDialog.jsp?Type=8&ParentID=" + parentID;
	diag.onLoad = function(){
		$DW.$("Name").focus();
		$DW.$S("ParentID",parentID);
		$DW.$S("Type",$V("CatalogType"));
		$DW.$S("ParentName",$V("Name"));
	};
	diag.OKEvent = addSave;
	diag.show();
}


function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("PublishFlag",$DW.$NV("PublishFlag"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Catalog.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�½���Ŀ�ɹ�",function(){
				$D.close();
				var catalogType = $V("CatalogType");
	      		Tree.setParam("tree1","CatalogType",catalogType);
				Tree.loadData("tree1");
			});
		}
	});
}


function addIssue(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "�½��ں�";
	diag.URL = "Site/NewspaperIssueDialog.jsp";
	diag.OKEvent = addIssueSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½��ں�";
	diag.Message = "���ñ�ֽ�ꡢ�ںš���ҳͼƬ��ģ���ļ���";
	diag.show();
}

function addIssueSave(){
	var dc = $DW.Form.getData($F("form2"));
	dc.add("NewspaperID",$V("CatalogID"));
	if($DW.Verify.hasError()){
	    return;
	}

	Server.sendRequest("com.nswt.cms.site.NewspaperIssue.add",dc,function(response){
		if (response.Status==0) {
			Dialog.alert(response.Message);
		} else {
			Dialog.alert("�½��ɹ�",function(){
				$D.close();
				var catalogType = $V("CatalogType");
				Tree.setParam("tree1","CatalogType",catalogType);
				Tree.loadData("tree1");
			});
		}
	});
}


function addNewspaper(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "�½���ֽ";
	var parentID;
	if($V("CatalogID")==""){
		parentID = 0;
	}else {
		parentID = $V("CatalogID");
	}

	diag.URL = "Site/NewspaperDialog.jsp?ParentID=" + parentID;
	diag.OKEvent = addNewspaperSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½���ֽ";
	diag.Message = "���ñ�ֽ������Ϣ��";
	diag.show();
}

function addNewspaperSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Newspaper.add",dc,function(response){
		if (response.Status==0) {
			Dialog.alert(response.Message);
		} else {
			Dialog.alert("�½��ɹ�",function(){
				$D.close();
				var catalogType = $V("CatalogType");
				Tree.setParam("tree1","CatalogType",catalogType);
				Tree.loadData("tree1");
			});
		}
	});
}

function editNewspaper(NewspaperID){
  var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "�༭��ֽ";
	diag.URL = "Site/NewspaperDialog.jsp?NewspaperID=" + NewspaperID;
	
	diag.OKEvent = editNewspaperSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸ı�ֽ";
	diag.Message = "�޸ı�ֽ������Ϣ��";
	diag.show();
}

function editNewspaperSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Newspaper.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		} else {
			Dialog.alert("�޸ĳɹ�",function(){
				$D.close();
				var catalogType = $V("CatalogType");
	      		Tree.setParam("tree1","CatalogType",catalogType);
				Tree.loadData("tree1");
			});
		}
	});
}

function move(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 400;
	diag.Title = "ѡ��ת����Ŀ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=3&CatalogType="+$V("CatalogType");
	diag.onLoad = function(){
		try{
			 $DW.$("btnOK").onclick = moveSave;
	  }catch(ex){}
	};
	diag.show();
}

function moveSave(){
	var catalogID = $V("CatalogID");
	if(catalogID==""){
		Dialog.alert("����ѡ��Ҫת����Ŀ��");
		return;
	}
	
	var arrDest = $DW.$NV("CatalogID");
	if(arrDest == null || arrDest.length==0){
		Dialog.alert("����ѡ��ת�Ƶ�Ŀ��λ�ã�");
		return;
	}
	
	var newParentID = arrDest[0];
	if(newParentID==catalogID){
		Dialog.alert("���ܽ���Ŀת�Ƶ�����Ŀ�£�");
		return;
	}
	
	if(newParentID.indexOf("site")!=-1){
		newParentID = 0;
	}
	
	var dc = new DataCollection();
	dc.add("CatalogID",$V("CatalogID"));
	dc.add("ParentID",newParentID);
	dc.add("CatalogType",$V("CatalogType"));
	Server.sendRequest("com.nswt.cms.site.Catalog.move",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			$D.close();
			Tree.loadData("tree1");
			onTabChange(currentTab);
		}
	});
}

function del(){
	var dc = new DataCollection();
	if($V("Type")=="0"){
		Dialog.alert("ֻ��ɾ����Ŀ");
		return;
	}
	Dialog.confirm('���棺��ȷ��Ҫɾ��"'+$V("Name")+'"�����Ŀ��\n���������Ŀ�µ�����������¶�ɾ������',function(){
		dc.add("CatalogID",$V("CatalogID"));
		dc.add("CatalogType",$V("CatalogType"));
		Server.sendRequest("com.nswt.cms.site.Catalog.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("ɾ����Ŀ�ɹ�",function(){
					Tree.loadData("tree1");
					$S("CatalogID","");
					onTabChange("Basic");
				});
			}
		});
	});
}

function delNewspaper(){
	var dc = new DataCollection();
	Dialog.confirm('���棺��ȷ��Ҫɾ��"'+$V("Name")+'"����ڿ���\n��������ڿ��µ�����������¶�ɾ������',function(){
		dc.add("CatalogID",$V("CatalogID"));
		dc.add("CatalogType",$V("CatalogType"));
		Server.sendRequest("com.nswt.cms.site.Newspaper.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("ɾ���ɹ�",function(){
					Tree.loadData("tree1");
					$S("CatalogID","");
					onTabChange("Basic");
				});
			}
		});
	});
}

function edit(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 480;
	diag.Title = "�޸���Ŀ";
	diag.URL = "Site/CatalogDialog.jsp?CatalogID=" + ID;
	diag.onLoad = function(){
		$DW.$("Name").focus();
		$DW.$("ParentName").parentNode.parentNode.style.display = "none";
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("PublishFlag",$DW.$NV("PublishFlag"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Catalog.save",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("����ɹ�",function(){
				$D.close();
				Tree.loadData("tree1");
				onTabChange("Basic");
			});
		}
	});
}

function publish(){
	var diag = new Dialog("Diag1");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "���ɾ�̬ҳ��";
	diag.URL = "Site/CatalogGenerateDialog.jsp";
	diag.onLoad = function(){
		if(nodeType == "0"){
			$DW.$("tr_Flag").style.display="none";
			
		}
	};
	diag.OKEvent = publishSave;
	diag.show();
}

function publishSave(){
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();

	var dc = $DW.Form.getData("form2");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	dc.add("type",nodeType);
	dc.add("CatalogType",$V("CatalogType"));
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.site.Catalog.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			Dialog.alert("����ҳ��ɹ�");
			$D.close();
		}
	});
}

function publishIndex(){
	var dc = new DataCollection();
	dc.add("CatalogType",$V("CatalogType"));
	Server.sendRequest("com.nswt.cms.site.CatalogSite.publishIndex",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("����ҳ��ɹ�");
		}
	});
}

function batchAdd(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 400;
	diag.Title = "����������Ŀ";
	var parentID;
	if($V("CatalogID")==""){
		parentID = 0;
	}else {
		parentID = $V("CatalogID");
	}

	diag.URL = "Site/CatalogImportDialog.jsp?ParentID=" + parentID;
	diag.show();
}

function reloadTree(){
	Tree.loadData("tree1");
}

function  onTypeChange(){
	var catalogType = $V("CatalogType");
	Tree.setParam("tree1","CatalogType",catalogType);
	Tree.loadData("tree1");
	Tab.onChildTabClick("Basic");
	Tab.getChildTab("Basic").src = Server.ContextPath+"Site/SiteBasic.jsp";
}
</script>
</head>

<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="CatalogInnerCode">
<input type="hidden" id="Name">
<input type="hidden" id="CatalogType" value="8">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
																 style="height:440px"
																 method="com.nswt.cms.site.Newspaper.treeDataBind" level="2"
																 lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}' 
					onClick="onTreeClick(this);" level="${level}">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td><z:tab>
			<z:childtab id="Basic" selected="true" onClick="onTabChange('Basic')"
				src="NewspaperSiteBasic.jsp">
				<img src="../Icons/icon002a1.gif" />
				<b>������Ϣ</b>
			</z:childtab>
			<z:childtab id="Template" onClick="onTabChange('Template')"
				src="about:blank">
				<img src="../Icons/icon003a12.gif" />
				<b>ģ������</b>
			</z:childtab>
			<z:childtab id="Block" onClick="onTabChange('Block')"
				src="about:blank">
				<img src="../Icons/icon003a1.gif" />
				<b>ҳ��Ƭ��</b>
			</z:childtab>
			<z:childtab id="Field" onClick="onTabChange('Field')"
				src="SiteField.jsp">
				<img src="../Icons/icon002a4.gif" />
				<b>�Զ����ֶ�</b>
			</z:childtab>
			<z:childtab id="Priv" onClick="onTabChange('Priv')" src="about:blank">
				<img src="../Icons/icon010a8.gif" />
				<b>Ȩ�޹���</b>
			</z:childtab>
			<z:childtab id="Issue" onClick="onTabChange('Issue')"
				src="about:blank">
				<img src="../Icons/icon010a8.gif" />
				<b>�ںŹ���</b>
			</z:childtab>
			<z:childtab id="Page" onClick="onTabChange('Page')" src="about:blank">
				<img src="../Icons/icon010a8.gif" />
				<b>�������</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>
