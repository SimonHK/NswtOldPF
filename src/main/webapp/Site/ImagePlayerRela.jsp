<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		dr.set("Info",$V("Info"));
		dr.set("LinkURL",$V("LinkURL"));
		dr.set("LinkText",$V("LinkText"));
		return true;
	}
});

function addImage(){
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "���ͼƬ";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=checkbox";
	diag.OKEvent = OK;	
	diag.show();
}

function editImage(id){
	$S("DoImageType",'edit');
	$S("DoImageID",id);
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "����ͼƬ";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=checkbox&showButton=0";
	diag.OKEvent = OK;	
	diag.show();
}

function OK(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	
	}else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
		
	}
}

function onUploadCompleted(returnID){
	var dc = new DataCollection();
	dc.add("ImagePlayerID",$V("ImagePlayerID"));
	dc.add("ImageIDs",returnID);
	var type=$V("DoImageType");
	var method="com.nswt.cms.site.ImagePlayerRela.relaImage";
	if(type=='edit'){
		dc.add("OldImageID",$V("DoImageID"));
		method="com.nswt.cms.site.ImagePlayerRela.editImage";
	}
	Server.sendRequest(method,dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function onReturnBack(returnID){
	onUploadCompleted(returnID);
}

function save(){
	DataGrid.save("dg1","com.nswt.cms.site.ImagePlayerRela.dg1Edit",function(){window.parent.location.reload();});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("��ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("ImagePlayerID",$V("ImagePlayerID"));
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.site.ImagePlayerRela.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}			
			});
		});
	});
}

function publishIndex(){
	var dc = new DataCollection();
	Server.sendRequest("com.nswt.cms.site.CatalogSite.publishIndex",dc,function(response){
		if(response.Status==0){
			Dialog.alert("����������ʧ��!");
		}else{
			Dialog.alert("�����������ɹ�!");
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
	dc.add("ImagePlayerID",$V("ImagePlayerID"));
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.cms.site.ImagePlayerRela.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
table.innerTable td{
	border-bottom:none;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<form enctype="multipart/form-data" id="form2" method="post"><input
	name="ImagePlayerID" type="hidden" id="ImagePlayerID"
	value="<%=request.getParameter("ImagePlayerID")%>" /> <input
	name="DoImageType" id="DoImageType" type="hidden" value='add' /> <input
	name="DoImageID" id="DoImageID" type="hidden" />
<table width="100%" border='0' cellpadding='0' cellspacing='0'>
	<tr>
		<td style="padding:4px 5px;"><z:tbutton onClick="addImage();"
			id="UploadButton"><img src="../Icons/icon039a2.gif" />���</z:tbutton> 
            <z:tbutton onClick="$('dg1').edit();" id="SaveButton"><img src="../Icons/icon039a4.gif" width="20" height="20" />�޸�</z:tbutton> 
			<z:tbutton onClick="save();"><img src="../Icons/icon039a16.gif" />����</z:tbutton> 
            <z:tbutton onClick="del();" id="DelButton"><img src="../Icons/icon039a3.gif" />ɾ��</z:tbutton> 
            <z:tbutton onClick="publishIndex();"><img src="../Icons/icon002a1.gif" />����</z:tbutton>
			</td>
	</tr>
	<tr>
		<td style="padding:0px 5px;">
        <z:datagrid id="dg1" method="com.nswt.cms.site.ImagePlayerRela.dg1DataBind" page="false">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable"
				afterdrag="afterRowDragEnd">
				<tr ztype="head" class="dataTableHead">
					<td width="6%" ztype="RowNo" drag="true"><img
						src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
					<td width="6%" ztype="selector" field="ID">&nbsp;</td>
					<td width="19%"><b>ͼƬ</b></td>
					<td><b>ͼƬ����</b><b>ͼƬ������Ϣ</b></td>
				</tr>
				<tr height="80">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td height="80" width="19%"><a
						href="javascript:editImage(${ID});"> <img align="center"
						src="..${Alias}${Path}s_${FileName}" title="�������ͼƬ"></a></td>
					<td>
					<table width="100%" cellspacing="0" class="innerTable">
						<tr>
							<td width="60"><b>����: </b></td>
							<td>${Name}</td>
						</tr>
						<tr>
							<td><b>˵��:</b></td>
							<td>${Info}</td>
						</tr>
						<tr>
							<td><b>���ӵ�ַ:</b></td>
							<td>${LinkURL}</td>
						</tr>
						<tr>
							<td><b>�����ı�:</b></td>
							<td>${LinkText}</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr ztype="edit" height="80">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td height="80"><img align="center"
						src="..${Alias}${Path}s_${FileName}" title="${LinkText}"></td>
					<td>
					<table width="100%" cellspacing="0" class="innerTable">
						<tr>
							<td width="60"><b>����:</b></td>
							<td><input type="text" class="input1" id="Name"
								value="${Name}" size="70"></td>
						</tr>
						<tr>
							<td><b>˵��:</b></td>
							<td><input type="text" class="input1" id="Info"
								value="${Info}" size="70"></td>
						</tr>
						<tr>
							<td><b>���ӵ�ַ:</b></td>
							<td><input name="text" type="text" class="input1"
								id="LinkURL" value="${LinkURL}" size="70"></td>
						</tr>
						<tr>
							<td><b>�����ı�:</b></td>
							<td><input type="text" class="input1" id="LinkText"
								value="${LinkText}" size="70"></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</form>
</body>
</html>
