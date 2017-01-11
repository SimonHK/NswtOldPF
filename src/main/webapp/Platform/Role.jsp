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
var tabMap = {
	Basic:"RoleTabBasic.jsp?1=1",
	Menu:"RoleTabMenu.jsp?2=2",
	Site:"RoleTabSite.jsp?PrivType=site",
	Article:"RoleTabCatalog.jsp?4=4"
};

var TabID = "Basic";//ȫ�ֵ�
var OldSiteID = "";
var OldPrivType = "";

function onRoleTabClick(tabid){
	if(tabid){
		TabID = tabid;
	}
	var cid = Tree.CurrentItem.getAttribute("cid");
	Tab.onChildTabClick(TabID);
	var URL = Tab.getCurrentTab().src;
	var newURL = tabMap[TabID]+"&RoleCode="+cid+"&OldSiteID="+OldSiteID+"&OldPrivType="+OldPrivType;
	if(URL.indexOf(newURL)<0){
		Tab.getCurrentTab().src = newURL;
	}
	return;
}

function onTreeClick(){
	var cid = Tree.CurrentItem.getAttribute("cid");
	Cookie.set("Role.LastRoleCode",cid,"2100-01-01");
	if(!cid || cid == null) {
		$("Site").hide();
		$("Menu").hide();
		$("Article").hide();
		onRoleTabClick("Basic");
	} else {
		$("Site").show();
		$("Menu").show();
		$("Article").show();
	}
	Tab.getCurrentTab().src = tabMap[TabID]+"&RoleCode="+cid+"&OldSiteID="+OldSiteID+"&OldPrivType="+OldPrivType;
}

Page.onLoad(function(){
	if(!Cookie.get("Role.LastRoleCode")||Cookie.get("Role.LastRoleCode")==""){
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
	}else{
		Tree.select("tree1","cid",Cookie.get("Role.LastRoleCode"));
	}
});

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	if(!cid){
		return ;
	}
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cid);
	menu.addItem("�½���ɫ",add,"/Icons/icon018a2.gif");
	menu.addItem("�޸Ľ�ɫ",showEditDialog,"/Icons/icon018a2.gif");
	menu.addItem("ɾ����ɫ",del,"/Icons/icon018a2.gif");
	//menu.addItem("-");
	// Type 0 ��ʾ�ڵ�Ϊվ�㣬1��ʾ�ڵ�Ϊ��Ŀ
   	//menu.addItem("����",attribute);
	menu.show();
}
function add(param){
	var diag = new Dialog("Diag1");
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "�½���ɫ";
	diag.URL = "Platform/RoleAddDialog.jsp";
	diag.onLoad = function(){
		$DW.$("RoleCode").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.nswt.platform.RoleTabBasic.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				window.parent.parent.location.reload();
			}
		});
	});
}

function del(param){
	var RoleCode = param;
	if(!RoleCode){
		Dialog.alert("����ѡ��һ����ɫ��");
		return ;
	}
	Dialog.confirm("ȷ��ɾ�� <b style='color:#F00'>"+RoleCode+"</b> ��ɫ��",function(){
		var dc = new DataCollection();
		dc.add("RoleCode",param);
		Server.sendRequest("com.nswt.platform.RoleTabBasic.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					Cookie.set("Role.LastRoleCode","");
					window.parent.parent.location.reload();
				}
			});
		});
	});
}

function showEditDialog(param){
	var RoleCode = param;
	if(!RoleCode){
		Dialog.alert("����ѡ��һ����ɫ��");
		return ;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 80;
	diag.Title = "�޸Ľ�ɫ";
	diag.URL = "Platform/RoleEditDialog.jsp?RoleCode="+param;
	diag.onLoad = function(){
		$DW.$("RoleName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.nswt.platform.RoleTabBasic.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				window.parent.location.reload();
			}
		});
	});
}
function attribute(param){
	var RoleCode = param;
	var diag = new Dialog("Diag1");
	diag.Width = 260;
	diag.Height = 130;
	diag.Title = "����";
	diag.URL = "Platform/RoleAttribute.jsp?RoleCode="+param;
	diag.ShowButtonRow = false;
	diag.show();
}
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1" style="height:430px;width:160px;" method="com.nswt.platform.Role.treeDataBind">
					<p cid='${RoleCode}' cname='${RoleName}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">&nbsp;${RoleCode}(${RoleName})</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td><z:tab>
			<z:childtab id="Basic" src="RoleTabBasic.jsp" selected="true" afterClick="onRoleTabClick('Basic')">
				<img src='../Icons/icon018a1.gif' />
				<b>������Ϣ</b>
			</z:childtab>
			<z:childtab id="Site" src="" afterClick="onRoleTabClick('Site')">
				<img src="../Icons/icon040a1.gif" />
				<b>վ��Ȩ��</b>
			</z:childtab>
			<z:childtab id="Menu" src="" afterClick="onRoleTabClick('Menu')">
				<img src="../Icons/icon018a1.gif" />
				<b>�˵�Ȩ��</b>
			</z:childtab>
			<z:childtab id="Article" src="" afterClick="onRoleTabClick('Article')">
				<img src="../Icons/icon003a1.gif" />
				<b>�ĵ�Ȩ��</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>
