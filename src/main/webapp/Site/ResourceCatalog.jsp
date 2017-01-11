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
var currentTab = "Basic";
var currentTreeItem,lastTreeItem;

Page.onLoad(function(){
	var catalogType = $V("CatalogType");
	if(catalogType=="4"){
		if(Cookie.get("Resource.LastImageLib")=="0" && !Application.getPriv('image',
				'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastImageLib"))%>','image_browse')){
			Tree.CurrentItem = $("tree1").$T("p")[0];
		}else{
			Tree.select("tree1","cid",Cookie.get("Resource.LastImageLib"));
		}
	}else if(catalogType=="5"){
		if(Cookie.get("Resource.LastVideoLib")=="0" && !Application.getPriv('video',
				'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastVideoLib"))%>','video_browse')){
			Tree.CurrentItem = $("tree1").$T("p")[0];
		}else{
	   		Tree.select("tree1","cid",Cookie.get("Resource.LastVideoLib"));
	 	}
	}else if(catalogType=="6"){
		if(Cookie.get("Resource.LastAudioLib")=="0" && !Application.getPriv('audio',
				'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastAudioLib"))%>','audio_browse')){
			Tree.CurrentItem = $("tree1").$T("p")[0];
		}else{
			Tree.select("tree1","cid",Cookie.get("Resource.LastAudioLib"));
		}
	}else if(catalogType=="7"){
		if(Cookie.get("Resource.LastAttachLib")=="0" && !Application.getPriv('attach',
				'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastAttachLib"))%>','attach_browse')){
			Tree.CurrentItem = $("tree1").$T("p")[0];
		}else{
			Tree.select("tree1","cid",Cookie.get("Resource.LastAttachLib"));
		}
	}else{
		Tree.CurrentItem = $("tree1").$T("p")[0];
	}
	if(Tree.CurrentItem){
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
	currentTab = tab;
	var catalogID = $V("CatalogID");
	var url;
	if(catalogID == ""){
		url = Server.ContextPath+"Site/Site"+tab+".jsp?Type="+$V("CatalogType")+"&InnerCode="+$V("CatalogInnerCode");
	}else{
		url = Server.ContextPath+"Site/Catalog"+tab+".jsp?CatalogID="+$V("CatalogID")+"&Type="+$V("CatalogType")+"&InnerCode="+$V("CatalogInnerCode");
	}
	Tab.getChildTab(tab).src = url;
}

function onTreeClick(ele){
	lastTreeItem = currentTreeItem;
	currentTreeItem = ele;
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	var catalogType = $V("CatalogType");
	if(!Tree.isRoot(ele)){
		if(catalogType=="4"){
			Cookie.set("Resource.LastImageLib",cid,"2100-01-01");
		}else if(catalogType=="5"){
			Cookie.set("Resource.LastVideoLib",cid,"2100-01-01");
		}else if(catalogType=="6"){
			Cookie.set("Resource.LastAudioLib",cid,"2100-01-01")
		}else if(catalogType=="7"){
			Cookie.set("Resource.LastAttachLib",cid,"2100-01-01")
		}
	}
	$S("CatalogID",cid);
	$S("CatalogInnerCode",code);
	$S("Name",ele.innerText);
	onTabChange(currentTab);
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 760;
	diag.Height = 380;
	diag.Title = "�½���Ŀ";
	var parentID;
	if($V("CatalogID")==""){
		parentID = 0;
	}else {
		parentID = $V("CatalogID");
	}

	diag.URL = "Site/CatalogDialog.jsp?Type="+$V("CatalogType")+"&ParentID=" + parentID;
	diag.onLoad = function(){
		$DW.$("Name").focus();
		$DW.$S("ParentID",parentID);
		$DW.$S("Type",$V("CatalogType"));
		//$DW.$S("SiteID",$V("SiteID"));
		$DW.$S("ParentName",$V("Name"));
	};
	diag.OKEvent = addSave;
	diag.show();
}


function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("PublishFlag",$DW.$NV("PublishFlag"));
	dc.add("SingleFlag",$DW.$NV("SingleFlag"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Catalog.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			 Dialog.alert("�½���Ŀ�ɹ�",function(){
			 $D.close();
			 var win = $E.getTopLevelWindow();
			 if(win.getAllPriv){
			  	win.getAllPriv();//ˢ��jsȨ��
			 }
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
	diag.OKEvent = moveSave;
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
			//window.location.reload();
			Tree.loadData("tree1");
			onTabChange(currentTab);
		}
	});
}

function del(){
	var dc = new DataCollection();
	if($("Type")=="0"){
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


function publish(){
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
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.site.Catalog.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var taskID = response.get("TaskID");
			var p = new Progress(taskID,"���ڷ���...");
			p.show();
		}
	});
}

function publishIndex(){
	var dc = new DataCollection();
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
	diag.Height = 370;
	diag.Title = "����������Ŀ";
	var parentID;
	if($V("CatalogID")==""){
		parentID = 0;
	}else {
		parentID = $V("CatalogID");
	}
	
	var catalogType = $V("CatalogType");
	diag.URL = "Site/CatalogImport.jsp?Type="+catalogType+"&ParentID=" + parentID;
	diag.ShowButtonRow = false;
	diag.show();
}

function reloadTree(){
  Tree.loadData("tree1");
}

function  onTypeChange(){
	var catalogType = $V("CatalogType");
	Cookie.set("ResourceCatalog.CatalogType",catalogType,"2100-01-01");
	Tree.setParam("tree1","CatalogType",catalogType);
	Tree.loadData("tree1",function(){
		if(catalogType=="4"){
			if(Cookie.get("Resource.LastImageLib")=="0" && !Application.getPriv('image',
					'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastImageLib"))%>','image_browse')){
				Tree.CurrentItem = $("tree1").$T("p")[0];
			}else{
				Tree.select("tree1","cid",Cookie.get("Resource.LastImageLib"));
			}
		}else if(catalogType=="5"){
			if(Cookie.get("Resource.LastVideoLib")=="0" && !Application.getPriv('video',
					'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastVideoLib"))%>','video_browse')){
				Tree.CurrentItem = $("tree1").$T("p")[0];
			}else{
		   		Tree.select("tree1","cid",Cookie.get("Resource.LastVideoLib"));
		 	}
		}else if(catalogType=="6"){
			if(Cookie.get("Resource.LastAudioLib")=="0" && !Application.getPriv('audio',
					'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastAudioLib"))%>','audio_browse')){
				Tree.CurrentItem = $("tree1").$T("p")[0];
			}else{
				Tree.select("tree1","cid",Cookie.get("Resource.LastAudioLib"));
			}
		}else if(catalogType=="7"){
			if(Cookie.get("Resource.LastAttachLib")=="0" && !Application.getPriv('attach',
					'<%=CatalogUtil.getInnerCode(ServletUtil.getCookieValue(request,"Resource.LastAttachLib"))%>','attach_browse')){
				Tree.CurrentItem = $("tree1").$T("p")[0];
			}else{
				Tree.select("tree1","cid",Cookie.get("Resource.LastAttachLib"));
			}
		}else{
			Tree.CurrentItem = $("tree1").$T("p")[0];
		}
		if(Tree.CurrentItem){
			Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		}
	});
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	if(!cid){
		return ;
	}
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cid);
	menu.addItem("����",sortCatalog,"Icons/icon400a13.gif");
	menu.addItem("����",publish,"/Icons/icon003a13.gif");
	menu.addItem("Ԥ��",preview,"Icons/icon403a3.gif");
	menu.show();
}


function preview(){
	if($V("CatalogID")){
			window.open("../Site/Preview.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));
		}else{
			window.open("../Site/Preview.jsp?Type=0&SiteID="+Cookie.get("SiteID"));
	}
}


function sortCatalog(CatalogID){
	if(!CatalogID){
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 100;
	diag.Title = "��Ŀ����";
	diag.URL = "Site/CatalogSort.jsp?CatalogID=" + CatalogID;
	diag.onLoad = function(){
		$DW.$("Move").focus();
	};
	diag.OKEvent = sortCatalogSave;
	diag.show();
}

function sortCatalogSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  	return;
    }
	dc.add("CatalogType",$V("CatalogType"));
	Server.sendRequest("com.nswt.cms.site.Catalog.sortCatalog",dc,function(response){
		if(response.Status==1){
			reloadTree();
			$D.close();
		}
	});	
}

function openEditor(path){
	  var url = Server.ContextPath+"Framework/Controls/FileEditDialog.jsp?fileName="+path;
	  var width  = (screen.availWidth-10)+"px";
      var height = (screen.availHeight-50)+"px";
      var leftm  = 0;
      var topm   = 0;
 	  var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
      var w = window.open(url,"",args);
      if ( !w ){
				Dialog.alert( "���ֵ������ڱ���ֹ���������������ã��Ա�����ʹ�ñ�����!" ) ;
				return ;
	  }
}
</script>
</head>
<%
	String cookieCatalogType = ServletUtil.getCookieValue(request,"ResourceCatalog.CatalogType","4");
%>
<body oncontextmenu="return false;">
<input type="hidden" id="CatalogID">
<input type="hidden" id="CatalogInnerCode">
<input type="hidden" id="Name">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">

			<tr>
				<td valign="middle" class="blockTd">ý������ͣ�<z:select
					name="CatalogType" id="CatalogType" style="width:60px;"
					onChange="onTypeChange()">
					<span value="4"
						<%if("4".equals(cookieCatalogType))out.print("selected='true'");%>>ͼƬ</span>
					<span value="5"
						<%if("5".equals(cookieCatalogType))out.print("selected='true'");%>>��Ƶ</span>
					<span value="6"
						<%if("6".equals(cookieCatalogType))out.print("selected='true'");%>>��Ƶ</span>
					<span value="7"
						<%if("7".equals(cookieCatalogType))out.print("selected='true'");%>>����</span>
				</z:select></td>
			</tr>
			<tr>
				<td style="padding:0 6px;"><z:tree id="tree1"
												   style="height:425px;width:160px;"
												   method="com.nswt.cms.site.Catalog.treeResourceDataBind" level="2"
												   lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}'
						onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td><z:tab>
			<z:childtab id="Basic" onClick="onTabChange('Basic')"
				src="about:blank" selected="true">
				<img src="../Icons/icon002a1.gif" />
				<b>��Ŀ����</b>
			</z:childtab>
			<z:childtab id="Config" onClick="onTabChange('Config')"
				src="about:blank">
				<img src="../Icons/icon018a1.gif" />
				<b>��Ŀ����</b>
			</z:childtab>
			<z:childtab id="Extend" onClick="onTabChange('Extend')"
				src="about:blank">
				<img src="../Icons/icon018a1.gif" />
				<b>��չ����</b>
			</z:childtab>
			<z:childtab id="Block" onClick="onTabChange('Block')"
				src="about:blank">
				<img src="../Icons/icon003a1.gif" />
				<b>��������</b>
			</z:childtab>
			<z:childtab id="Column" onClick="onTabChange('Column')"
				src="about:blank">
				<img src="../Icons/icon002a4.gif" />
				<b>�ĵ��Զ����ֶ�</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>
