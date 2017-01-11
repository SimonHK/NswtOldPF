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
	diag.Height = 300;
	diag.Title = "�½��ں�";
	diag.URL = "Site/NewspaperIssueDialog.jsp";
	
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½��ں�";
	diag.Message = "�����ڿ���š��ںš���ҳͼƬ��ģ���ļ���";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData($F("form2"));
  dc.add("NewspaperID",$V("NewspaperID"));
  if($DW.Verify.hasError()){
	    return;
	}

	Server.sendRequest("com.nswt.cms.site.NewspaperIssue.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("�½��ɹ�",function(){
				$D.close();
			  DataGrid.setParam("dg1",Constant.PageIndex,0);
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
			
			Server.sendRequest("com.nswt.cms.site.NewspaperIssue.del",dc,function(response){
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
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "�޸��ڿ�";
	diag.URL = "Site/NewspaperIssueDialog.jsp";
	
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
  dc.add("NewspaperID",$V("NewspaperID"));
  if($DW.Verify.hasError()){
	    return;
	}
	
	Server.sendRequest("com.nswt.cms.site.NewspaperIssue.edit",dc,function(response){
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

function generate(){
		var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫ���ɵ��У�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	
	Server.sendRequest("com.nswt.cms.site.NewspaperIssue.generate",dc,function(response){
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

</script>
</head>
<body>
<z:init method="com.nswt.cms.site.NewspaperIssue.init">
	<div style="padding:2px;">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td style="padding-bottom:4px"><z:tbutton onClick="add();">
				<img src="../Platform/Images/tab1_tri.gif" />�½�</z:tbutton> <z:tbutton
				onClick="edit();">
				<img src="../Platform/Images/tab1_tri.gif" />�༭</z:tbutton> <z:tbutton
				onClick="del();">
				<img src="../Platform/Images/tab1_tri.gif" />ɾ��</z:tbutton> <z:tbutton
				onClick="copy();">
				<img src="../Platform/Images/tab1_tri.gif" />����</z:tbutton> <z:tbutton
				onClick="generate();">
				<img src="../Platform/Images/tab1_tri.gif" />����</z:tbutton></td>
		</tr>
	</table>

	<input name="NewspaperID" type="hidden" id="NewspaperID"
		value="${NewspaperID}" /> <z:datagrid id="dg1"
											  method="com.nswt.cms.site.NewspaperIssue.dg1DataBind" size="14">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="5%" ztype="RowNo">���</td>
				<td width="3%" ztype="selector" field="id">&nbsp;</td>
				<td><strong>���</strong></td>
				<td><strong>�ں�</strong></td>
				<td><strong>��������</strong></td>
				<td><strong>״̬</strong></td>
				<td><strong>���ʱ��</strong></td>
				<td><strong>����</strong></td>
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
				<td><a
					href="Preview.jsp?File=${FileName}&Type=2&SiteID=${SiteID}"
					target="_blank">Ԥ��</a></td>
			</tr>
			<tr ztype="pagebar">
				<td colspan="8" align="center">${PageBar}</td>
			</tr>
		</table>
	</z:datagrid></div>
</z:init>
</body>
</html>
