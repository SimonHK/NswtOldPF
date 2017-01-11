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
<script src="../Framework/Spell.js"></script>
<script src="../Editor/fckeditor.js"></script>
<script src="../Framework/Controls/StyleToolbar.js"></script>
<script>
Page.onLoad(function(){
	
});

var editor;
function getContent(){
	editor = FCKeditorAPI.GetInstance('Content');
    return editor.GetXHTML(false);	
}

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
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "�½��ں�";
	diag.URL = "Site/MagazineIssueDialog.jsp?MagazineID="+$V("MagazineID");
	
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½��ں�";
	diag.Message = "�����ڿ���š��ںš���ҳͼƬ��ģ���ļ���";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData($F("form2"));
  dc.add("MagazineID",$V("MagazineID"));
  if($DW.Verify.hasError()){
	    return;
	}

	Server.sendRequest("com.nswt.cms.site.MagazineIssue.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�½��ɹ�",function(){
				$D.close();
			  DataGrid.setParam("dg1",Constant.PageIndex,0);
			  window.parent.reloadTree();
			  DataGrid.loadData("dg1");
			});

		}
	});
}

function copy(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "�����ڿ�";
	diag.URL = "Site/PageBlockCopyDialog.jsp";
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	
	Dialog.confirm("ȷ��ɾ��ѡ�е��ں���",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			
			Server.sendRequest("com.nswt.cms.site.MagazineIssue.del",dc,function(response){
				if(response.Status==0){
					alert(response.Message);
				}else{
					Dialog.alert("ɾ���ɹ�",function(){
						DataGrid.setParam("dg1",Constant.PageIndex,0);
						window.parent.Tree.loadData("tree1");
					    DataGrid.loadData("dg1");
					});
				}
			});
	});

}

function edit(){
  var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵĿ��ţ�");
		return;
	}
	if(drs.length>1){
		Dialog.alert("ֻ���޸�1����Ϣ��");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ��У�");
		return;
	}
	var ID = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "�޸��ڿ�";
	diag.URL = "Site/MagazineIssueDialog.jsp?ID="+ID;
	
	diag.onLoad = function(){
		$DW.Form.setValue(dr);
		$DW.$S("PublishDate",dr.get("PubDate"));
	};
	
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸��ں�";
	diag.Message = "�����ڿ���š��ںš���ҳͼƬ��ģ���ļ���";
	diag.show();
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData($F("form2"));
  dc.add("MagazineID",$V("MagazineID"));
  editor = $DW.FCKeditorAPI.GetInstance('Content');
  var content = editor.GetXHTML(false);
    dc.add("Memo",content);
  
  if($DW.Verify.hasError()){
	    return;
	}
	
	Server.sendRequest("com.nswt.cms.site.MagazineIssue.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�޸ĳɹ�",function(){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				DataGrid.loadData("dg1");
			});
		}
	});
}
/**
function generate(){
		var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ���ɵ��У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	
	Server.sendRequest("com.nswtcms.site.MagazineIssue.generate",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�����ڿ��ɹ�",function(){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				DataGrid.loadData("dg1");
			});
		}
	});
}
*/

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
    var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ�������ںţ�");
		return;
	}
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();

	var dc = $DW.Form.getData("form2");
    dc.add("IDs",arr.join());	

	Server.sendRequest("com.nswt.cms.site.Magazine.publish",dc,function(response){
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


</script>

</head>
<body>
<z:init method="com.nswt.cms.site.MagazineIssue.init">

	<div style="padding:2px;">
	<table width="100%" cellpadding="4" cellspacing="0">
		<tr>
			<td><z:tbutton onClick="add();">
				<img src="../Icons/icon010a2.gif" />�½�</z:tbutton> <z:tbutton
				onClick="edit();">
				<img src="../Icons/icon010a4.gif" />�༭</z:tbutton> <z:tbutton
				onClick="del();">
				<img src="../Icons/icon010a3.gif" />ɾ��</z:tbutton> <!--<z:tbutton
				onClick="copy();">
				<img src="../Platform/Images/tab1_tri.gif" />����</z:tbutton>--></td>
		</tr>
	</table>

	    <input name="MagazineID" type="hidden" id="MagazineID"
		value="${MagazineID}" />
		<input name="CatalogID" type="hidden" id="CatalogID"
		value="${CatalogID}" /> <z:datagrid id="dg1"
											method="com.nswt.cms.site.MagazineIssue.dg1DataBind" size="15">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="5%" ztype="RowNo">���</td>
				<td width="3%" ztype="selector" field="id">&nbsp;</td>
				<td><strong>���</strong></td>
				<td><strong>�ں�</strong></td>
				<td><strong>��������</strong></td>
				<td><strong>״̬</strong></td>
				<td><strong>���ʱ��</strong></td>
			</tr>
			<tr style1="background-color:#FFFFFF"
				style2="background-color:#F9FBFC">
				<td width="5">&nbsp;</td>
				<td width="36">&nbsp;</td>
				<td>${Year}</td>
				<td>${PeriodNum}</td>
				<td>${pubDate}</td>
				<td>${Status}</td>
				<td>${AddTime}</td>
			</tr>
			<tr ztype="pagebar">
				<td colspan="7" align="center">${PageBar}</td>
			</tr>
		</table>
	</z:datagrid></div>
</z:init>
</body>
</html>
