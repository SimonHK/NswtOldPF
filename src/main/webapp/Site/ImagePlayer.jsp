<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ������</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 420;
	diag.Title = "�½�"+this.document.title;
	diag.URL = "Site/ImagePlayerDialog.jsp?ImagePlayerID=&RelaCatalog="+$V("CatalogInnerCode");
	diag.ShowButtonRow = false;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("��ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.site.ImagePlayer.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
					if(currentTreeItem){
						Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
					}
				}
			});
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�༭�Ĳ�������");
		return;
	}
	if(drs.length>1){
		Dialog.alert("ֻ���޸�1����Ϣ��");
		return;
	}
	dr = drs[0];
	
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 420;
	diag.Title = this.document.title;
	var url = "Site/ImagePlayerDialog.jsp?ImagePlayerID="+dr.get("ID")+"&ImageSource="+dr.get("ImageSource") + "&RelaCatalog=" + dr.get("RelaCatalogInnerCode");
	diag.URL = url;
	diag.ShowButtonRow = false;
	diag.show();
}

var currentTreeItem;
function onTreeClick(ele){
	currentTreeItem = ele;
	var cid =  ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogInnerCode",code);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","CatalogInnerCode",code);
	DataGrid.loadData("dg1");
}

</script>
</head>
<body scroll="no">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
        <table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
																 style="height:450px;width:160px;"
																 method="com.nswt.cms.site.ImagePlayer.treeDataBind" level="2"
																 lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
        </td>
        <td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon039a1.gif" /> �������б�
                <input type="hidden" id="CatalogInnerCode" name="CatalogInnerCode" value="" />
                </td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;"><z:tbutton onClick="add()">
					<img src="../Icons/icon039a2.gif" />�½�</z:tbutton> <z:tbutton onClick="edit()">
					<img src="../Icons/icon039a4.gif" />�༭</z:tbutton> <z:tbutton onClick="del()">
					<img src="../Icons/icon039a3.gif" />ɾ��</z:tbutton></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
							method="com.nswt.cms.site.ImagePlayer.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo">���</td>
							<td width="5%" ztype="selector" field="id">&nbsp;</td>
							<td width="25%"><strong>����</strong></td>
							<td width="25%"><strong>����</strong></td>
							<td width="15%"><strong>�߶�</strong></td>
							<td width="15%"><strong>���</strong></td>
                            <td width="10%"><strong>������Ŀ</strong></td>
						</tr>
						<tr onDblClick="edit();">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td>${Code}</td>
							<td>${Height}px</td>
							<td>${Width}px</td>
                            <td>${CatalogName}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="7" align="center">${PageBar}</td>
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
