<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��λ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 260;
	diag.Title = "�½���λ";
	diag.URL = "DataService/AdvertiseLayoutDialog.jsp?RelaCatalogID="+$V("RelaCatalogID");
	diag.onLoad = function(){
		$DW.$("PositionName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.dataservice.AdvertiseLayout.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert("������λ�ɹ�",function(){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
				}else{
					Tree.loadData("tree1");
				}
				DataGrid.loadData("dg1");	
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ���İ�λ��");
		return;
	}
	
	Dialog.confirm("ȷ��Ҫɾ����λ��",function(){
		var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.nswt.cms.dataservice.AdvertiseLayout.del",dc,function(response){
				if(response.Status==1){
					Dialog.alert(response.Message,function(){
					   if(currentTreeItem){
						   Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
					   }
					   DataGrid.loadData("dg1");
					});
				}
				else{
					Dialog.alert(response.Message);
				}
			});
	})
}

function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���Ƶİ�λ��");
		return;
	}
	if(arr.length>1){
		Dialog.alert("���Ʋ���ֻ�����һ����λ��");
		return;
	}
	var dc = new DataCollection();
	dc.add("ID",arr[0]);
	Server.sendRequest("com.nswt.cms.dataservice.AdvertiseLayout.copy",dc,function(response){
		if(response.Status==1){
			Dialog.alert("���ư�λ�ɹ�",function(){
				DataGrid.loadData("dg1");
			});
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function editDialog(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�༭�İ�λ��");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 260;
	diag.Title = "�༭��λ������Ϣ";
	diag.URL = "DataService/AdvertiseLayoutDialog.jsp?ID="+dr.get("ID")+ "&RelaCatalogID=" + dr.get("RelaCatalogID");
	diag.onLoad = function(){
    	$DW.$S("ID",dr.get("ID"));
		$DW.$("PositionName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.dataservice.AdvertiseLayout.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert("�޸İ�λ�ɹ�",function(){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
				}
				DataGrid.loadData("dg1");
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��ҪԤ���Ĺ���λ��");
		return;
	}
	window.open("AdvertisePreview.jsp?ID="+arr[0])
}

function doSearch(){
	var name = "";
	if($V("SearchContent").trim()!= "������Ҫ��ѯ�İ�λ����"){
		name = $V("SearchContent");
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchContent",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchContent'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	var keyWord = $V("SearchContent");
	if (keyWord == "������Ҫ��ѯ�İ�λ����") {
		$S("SearchContent","");
	}
}

function editAdContent(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ�����λ��");
		return;
	}
	var diag = new Dialog("diag1");
	diag.Width = 520;
	diag.Height = 460;
	diag.Title = "�༭�������";
	diag.URL = "DataService/AdvertiseDialog.jsp?PosID="+arr[0]+"&addType=layout";
	diag.OKEvent = editAdSave;
	diag.show();
}

function editAdSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = Form.getData($DW.$F("adform"));
	if($DW.$V("AdType")=="image"){
		if($DW.checkImgPath()){
			dc.add("imgADLinkUrl",$DW.$NV("imgADLinkUrl").join("^"));
			dc.add("imgADAlt",$DW.$NV("imgADAlt").join("^"));
			dc.add("ImgPath",$DW.$NV("ImgPath").join("^"));
		}else{
			Dialog.alert("��ΪͼƬ���ѡ��ͼƬ");
			return;
		}
	}else if($DW.$V("AdType")=="flash"){
		dc.add("SwfFilePath",$DW.$NV("SwfFilePath").join("^"));
	}else if($DW.$V("AdType")=="text"){
		dc.add("textContent",$DW.$NV("textContent").join("^"));
		dc.add("textLinkUrl",$DW.$NV("textLinkUrl").join("^"));
		dc.add("TextColor",$DW.$NV("TextColor").join("^"));
	}
	Server.sendRequest("com.nswt.cms.dataservice.Advertise.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg1");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function showAdList(ID,PositionName){
	var arr;
	if(ID==""||ID==null){
		arr = DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			Dialog.alert("����ѡ�����λ��");
			return;
		}
		ID = arr[0];
	}
	var diag = new Dialog("Diag1");
	diag.Width = 720;
	diag.Height = 380;
	diag.Title = "����б�";
	diag.URL = "DataService/AdvertiseListDialog.jsp?PosID="+ID+"&PosName="+PositionName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
}

function loadAdData(){
	DataGrid.loadData("dg1");	
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
																 method="com.nswt.cms.dataservice.AdvertiseLayout.treeDataBind" level="2"
																 lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
    </td>
    <td>
	<table width="100%" border="0" cellpadding="4" cellspacing="0" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon010a1.gif"/> ����λ�б�
        <input type="hidden" id="RelaCatalogID" name="RelaCatalogID" value="0"/></td>
      </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add();"><img src="../Icons/icon010a2.gif" />�½�</z:tbutton> 
        <z:tbutton onClick="editDialog();"><img src="../Icons/icon010a16.gif" />�޸�</z:tbutton>
        <z:tbutton onClick="del();"><img src="../Icons/icon010a3.gif" />ɾ��</z:tbutton> 
        <z:tbutton onClick="copy();"><img src="../Icons/icon010a12.gif" />����</z:tbutton>
        <z:tbutton onClick="showAdList();"><img src="../Icons/icon010a12.gif" />����б�</z:tbutton>
        <z:tbutton onClick="editAdContent();"><img src="../Icons/icon010a4.gif" />�޸Ĺ������</z:tbutton>
        <z:tbutton onClick="preview();"><img src="../Icons/icon010a15.gif" />Ԥ��</z:tbutton>
		<div style="display:none;float: right; white-space: nowrap;">
		<input name="SearchContent" type="text" id="SearchContent" value="������Ҫ��ѯ�İ�λ����" onFocus="delKeyWord();" style="width:150px">
		<input type="button" name="Submitbutton" id="Submitbutton" value="����" onClick="doSearch()">
		</div>
        </td>
      </tr>
      <tr>
        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
		<z:datagrid id="dg1" method="com.nswt.cms.dataservice.AdvertiseLayout.dg1DataBind" size="15">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="5%" ztype="RowNo">���</td>
				<td width="3%" ztype="selector" field="id">&nbsp;</td>
				<td width="17%"><b>��λ����</b></td>
				<td width="15%"><b>JS·��</b></td>
                <td width="10%"><b>������Ŀ</b></td>
                <td width="10%"><b>��λ����</b></td>
                <td width="10%"><b>�������</b></td>
				<td width="10%"><b>�ߴ�</b></td>
                <td width="19%"><b>��λ����</b></td>
			</tr>
			<tr onDblClick="showAdList(${ID},'${PositionName}')" style1="background-color:#FFFFFF"
				style2="background-color:#F9FBFC">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${PositionName}</td>
				<td>${JSName}</td>
                <td>${CatalogName}</td>
                <td>${PositionTypeName}</td>
                <td>${ADTypeName}</td>
				<td>${PositionSizeWidth}*${PositionSizeHeight}</td>
                <td title="${Description}">${Description}</td>
			</tr>
			<tr ztype="pagebar">
				<td colspan="9" align="center">${PageBar}</td>
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