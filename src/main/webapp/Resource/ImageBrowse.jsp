<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html style=" overflow:hidden;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onImageOver(ele){
	ele.style.backgroundColor='#fffabf';
}
function onImageOut(ele){
	ele.style.backgroundColor='';
}
function doSearch(){
	DataList.setParam("dg1",Constant.PageIndex,0);
	DataList.setParam("dg1","Search","Search");
	DataList.setParam("dg1","_CatalogID",$V("CatalogID"));
	DataList.setParam("dg1","Name",$V("Name"));
	DataList.setParam("dg1","Info",$V("Info"));
	DataList.loadData("dg1");
}

function onReturnBack(){
	var arr = $NV("ImageID");
	if(!arr||arr.length==0){
		alert("����ѡ������Ҫ��ͼƬ��");
		return;
	}
	if(window.parent.Parent.onReturnBack){
		window.parent.Parent.onReturnBack(arr.join());
	}
	window.parent.Dialog.close();
}

function clickEdit(id){
	edit(id);
};

function clickDelete(id){
	del(id);
}

function del(imageID){
	if(!imageID){
		var arr = $NV("ImageID");
		if(!arr||arr.length==0){
			Dialog.alert("����ѡ��Ҫɾ����ͼƬ��");
			return;
		}
		imageID = arr.join();
	}
	
	Dialog.confirm("��ЩͼƬ�����������й�������ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",imageID);
		Server.sendRequest("com.nswt.cms.resource.Image.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.setParam("dg1","CatalogID",treeItem.getAttribute("cid"));
					DataList.setParam("dg1","Name",$V("Name"));
					DataList.loadData("dg1");
				}
			});
		});
	});
}

function edit(imageID){
	if(!imageID){
		var arr = $NV("ImageID");
		if(!arr||arr.length==0){
			Dialog.alert("����ѡ��Ҫ�༭��ͼƬ��");
			return;
		}
		imageID = arr[0];
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 450;
	diag.Title = "�༭ͼƬ";
	diag.URL = "Resource/ImageEditDialog.jsp?ID="+imageID;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.nswt.cms.resource.Image.edit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataList.setParam("dg1","Name",$V("Name"));
				DataList.loadData("dg1");
			}
		});
	});
}

function clickImage(ele){
	var box = $("ImageID_box"+$(ele).$A("ImageID"));
	if(box.checked){
			if(box.type=="checkbox"){
				box.checked = false;
			}else{
				box.checked = true;	
			}
	}else{
			box.checked = true;
	}
}
</script>
</head>
<body>
<div>
<table width="99%" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table cellpadding="3" cellspacing="0" id="tbSearch">
			<tr>
				<td width="0" align="left">��������: <z:select id="CatalogID"
					style="width:80px" listWidth="200" listHeight="300"
					listURL="Resource/ImageLibSelectList.jsp"> </z:select></td>
				<td width="0">���ƣ�</td>
				<td width="0"><input name="Name" id="Name" type="text" value=""></td>
				<td width="0">������</td>
				<td width="0"><input name="Info" id="Info" type="text" value=""></td>
				<td width="0"><z:tbutton id="addButton" onClick="doSearch()">
					<img src="../Icons/icon030a15.gif" />��ѯ</z:tbutton></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<ul class="img-wrapper"
			style="height:340px; overflow:hidden; display:block;">
			<z:datalist id="dg1"
						method="com.nswt.cms.resource.ImageLib.dg1DataListBrowse" size="10"
						page="true">
				<li style="height:160px">
				<dl>
					<dt><a href='#;' hidefocus><em><img imageid='${id}'
						src='${alias}${path}s_${filename}?${ModifyTime}' title='${name}'
						onClick='clickImage(this)'></em></a></dt>
					<dd><em><label><input id='ImageID_box${id}'
						name='ImageID' type='${selecttype}' value='${id}'>${name}</label></em></dd>
				</dl>
				</li>
			</z:datalist>
		</ul>
		</td>
	</tr>
	<tr>
		<td><z:pagebar target="dg1" /></td>
	</tr>
</table>
</div>
</body>
</html>
