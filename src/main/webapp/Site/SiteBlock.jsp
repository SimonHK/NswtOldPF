<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ŀ</title>

<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	var type = $V("type");
	var ctype ="article";
	if(type=="1"){
		ctype ="article";
	}else if(type=="4"){
		ctype ="image";
	}else if(type=="5"){
		ctype ="video";
	}else if(type=="6"){
		ctype ="audio";
	}else if(type=="7"){
		ctype ="attach";
	}
	if($V("InnerCode")){
		$("BtnAdd").setAttribute("priv",ctype+"_manage");
		$("BtnEdit").setAttribute("priv",ctype+"_manage");
		$("BtnDel").setAttribute("priv",ctype+"_manage");
		$("BtnGenerate").setAttribute("priv",ctype+"_manage");
		Application.setAllPriv(ctype,$V("InnerCode"));
	}else{
		Application.setAllPriv("site",Application.CurrentSite);
	}
});

//�������ǰҳ��ʱ�������Ҽ��˵�
var iFrame =parent.parent;
Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}
});


function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 320;
	diag.Title = "�½�ҳ��Ƭ��";
	diag.URL = "Site/PageBlockDialog.jsp";
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = Form.getData($DW.Tab.getChildTab('Basic').contentWindow.$F("form2"));

	var blockType = $DW.Tab.getChildTab('Basic').contentWindow.$NV("Type");

  if($DW.Tab.getChildTab('Basic').contentWindow.Verify.hasError()){
			  return;
	}
	 
  if(blockType == 2){
		var titles = $DW.Tab.getChildTab('List').contentWindow.$NV("ItemTitle");
		var urls = $DW.Tab.getChildTab('List').contentWindow.$NV("ItemURL");
		var items = $DW.Tab.getChildTab('List').contentWindow.$("tableItems");
		if(items.rows.length==2){
			dc.add("ItemTitle",titles);
			dc.add("ItemURL",urls);
		}else{
			dc.add("ItemTitle",titles.join("^"));
			dc.add("ItemURL",urls.join("^"));
		}
	}else if(blockType == 3){
		var content = $DW.Tab.getChildTab('Content').contentWindow.FCKeditorAPI.GetInstance('Content').GetXHTML(false);
		if(content==null||content.trim().length==0){
			Dialog.alert("�������ݲ���Ϊ��!");
			return;
		}
		dc.add("Content",content);
	}
	
	var fileName = $DW.Tab.getChildTab('Basic').contentWindow.$V
	dc.add("Type",blockType);
	dc.add("CatalogID",$V("CatalogID"));

	Server.sendRequest("com.nswt.cms.site.PageBlock.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�½�ҳ��Ƭ�γɹ�",function(){
				$D.close();
			  DataGrid.setParam("dg1",Constant.PageIndex,0);
			  DataGrid.loadData("dg1");
			});

		}
	});
}

function copy(){
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 200;
	diag.Title = "����ҳ��Ƭ��";
	diag.URL = "Site/PageBlockCopyDialog.jsp?Type=3&CatalogType="+parent.$V("CatalogType");
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
	var arr = DataGrid.getSelectedValue("dg1");	
	var arrDest = $DW.$NV("CatalogID");
	var dc = new DataCollection();
	dc.add("ID",arr.join());
	dc.add("CatalogIDs",arrDest.join());
	Server.sendRequest("com.nswt.cms.site.PageBlock.copy",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�ɹ�����",function(){
				$D.close();
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	     		DataGrid.loadData("dg1");
			});
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	
	Dialog.confirm("ȷ��ɾ��ѡ�е�ҳ��Ƭ����",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			
			Server.sendRequest("com.nswt.cms.site.PageBlock.del",dc,function(response){
				if(response.Status==0){
					alert(response.Message);
				}else{
					Dialog.alert("ɾ���ɹ�",function(){
						DataGrid.setParam("dg1",Constant.PageIndex,0);
						DataGrid.loadData("dg1");
					});
				}
			});
	});

}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ��У�");
		return;
	}
	var ID = arr[0];
    editDialog(ID);
}

function editDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 320;
	diag.Title = "�޸�ҳ��Ƭ��";
	diag.URL = "Site/PageBlockDialog.jsp?ID="+ID;
	diag.OKEvent = editSave;
	diag.show();
	diag.show();
}

function editSave(){
	var dc = Form.getData($DW.Tab.getChildTab('Basic').contentWindow.$F("form2"));

	var blockType = $DW.Tab.getChildTab('Basic').contentWindow.$NV("Type");

  if($DW.Tab.getChildTab('Basic').contentWindow.Verify.hasError()){
			  return;
	}
	 
  if(blockType == 2){
		var titles = $DW.Tab.getChildTab('List').contentWindow.$NV("ItemTitle");
		var urls = $DW.Tab.getChildTab('List').contentWindow.$NV("ItemURL");
		var items = $DW.Tab.getChildTab('List').contentWindow.$("tableItems");
		if(items.rows.length==2){
			dc.add("ItemTitle",titles);
			dc.add("ItemURL",urls);
		}else{
			dc.add("ItemTitle",titles.join("^"));
			dc.add("ItemURL",urls.join("^"));
		}
	}else if(blockType == 3){
		var content = $DW.Tab.getChildTab('Content').contentWindow.FCKeditorAPI.GetInstance('Content').GetXHTML(false);
		if(content==null||content.trim().length==0){
			Dialog.alert("�������ݲ���Ϊ��!");
			return;
		}
		dc.add("Content",content);
	}
	dc.add("Type",blockType);
	dc.add("CatalogID",$V("CatalogID"));
	
	Server.sendRequest("com.nswt.cms.site.PageBlock.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�޸�ҳ��Ƭ�γɹ�",function(){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				DataGrid.loadData("dg1");
			});
		}
	});
}

function generate(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ���ɵ��У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	
	Server.sendRequest("com.nswt.cms.site.PageBlock.generate",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("����ҳ��Ƭ�γɹ�");
		}
	});
}

</script>
</head>
<body>
<z:init method="com.nswt.cms.site.PageBlock.init">
	<div style="padding:2px;">
	<table width="100%" cellpadding="0" cellspacing="0"
		style="margin-bottom:4px;">
		<tr>
			<td><z:tbutton id="BtnAdd" priv="site_manage"
				onClick="add();">
				<img src="../Icons/icon018a2.gif" />�½�</z:tbutton> <z:tbutton id="BtnEdit"
				priv="site_manage" onClick="edit();">
				<img src="../Icons/icon018a4.gif" />�༭</z:tbutton> <z:tbutton id="BtnDel"
				priv="site_manage" onClick="del();">
				<img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton> <z:tbutton id="BtnGenerate"
				priv="site_manage" onClick="generate();">
				<img src="../Icons/icon018a6.gif" />����</z:tbutton></td>
		</tr>
	</table>

	<input type="hidden" id="type" value="${type}" name="type"> 
	<input name="CatalogID" type="hidden" id="CatalogID" value="${CatalogID}" />
	<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" />
	<input name="SiteID" type="hidden" id="SiteID" value="${SiteID}" />
	<z:datagrid id="dg1" method="com.nswt.cms.site.PageBlock.dg1DataBind" size="14">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="5%" ztype="RowNo">���</td>
				<td width="4%" ztype="selector" field="id">&nbsp;</td>
				<td width="12%"><strong>������Ŀ</strong></td>
				<td width="11%"><strong>Ƭ������</strong></td>
				<td width="12%"><strong>Ƭ�δ���</strong></td>
				<td width="23%"><strong>ģ���ļ�</strong></td>
				<td width="28%"><strong>�����ļ�</strong></td>
				<td width="5%"><strong>����</strong></td>
			</tr>
			<tr onDblClick="editDialog(${ID})">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><span title="${CatalogPath}" style="cursor:default;">${CatalogIDName}</span></td>
				<td>${Name}</td>
				<td>${Code}</td>
				<td>${Template}</td>
				<td>${FileName}</td>
				<td><a
					href="Preview.jsp?File=${FileName}&Type=2&SiteID=${SiteID}"
					target="_blank">Ԥ��</a></td>
			</tr>
			<tr ztype="pagebar">
				<td valign="middle" colspan="8">${PageBar}</td>
			</tr>
		</table>
	</z:datagrid></div>
</z:init>
</body>
</html>
