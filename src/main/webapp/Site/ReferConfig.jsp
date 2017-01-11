<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=gb2312" />
<title>��Ա�ȼ���Ŀ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "�½�������Դ";
	diag.URL = "Site/ReferConfigDialog.jsp?CodeName=";
	diag.onLoad = function(){
		$DW.$("CodeName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.nswt.cms.site.ReferConfig.add",dc,function(){
		var response = Server.getResponse();
		if(response.Status==1){
			DataGrid.loadData("dg1");
			$D.close();
		}
	});
}

function edit(ArticleRefer){
	var arr = DataGrid.getSelectedValue("dg1");
	if(ArticleRefer&&ArticleRefer!=""){
		arr = new Array();
		arr[0] = ArticleRefer;
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ�������Դ��");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "�޸�������Դ";
	diag.URL = "Site/ReferConfigDialog.jsp?CodeName="+encodeURI(encodeURI(arr[0]));
	diag.onLoad = function(){
		$DW.$("CodeName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function del(ArticleRefer){
	var arr = DataGrid.getSelectedValue("dg1");
	if(ArticleRefer&&ArticleRefer!=""){
		arr = new Array();
		arr[0] = ArticleRefer;
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ����������Դ��",function(){
		var dc = new DataCollection();	
		dc.add("CodeNames",arr.join());
		Server.sendRequest("com.nswt.cms.site.ReferConfig.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				DataGrid.loadData("dg1");
			}
		});
	},function(){
		return;
	});
}

function doSearch(){
	var name = "";
	if ($V("SearchContent") != "������������Դ") {
		name = $V("SearchContent").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchContent",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchContent'||ele.id == 'SearchContent'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchContent") == "������������Դ") {
		$S("SearchContent","");
	}
}
</script>
<body>
<table width="850" border="0" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 10px;">
                <div style="float: right; white-space: nowrap;">
				  <input name="SearchContent" type="text" id="SearchContent" value="������������Դ" onFocus="delKeyWord();" style="width:150px">
            	  <input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="doSearch()">
            	</div>
                <z:tbutton onClick="add()"><img src="../Icons/icon003a2.gif" />�½�</z:tbutton> 
                <z:tbutton onClick="edit()"><img src="../Icons/icon003a4.gif" />�޸�</z:tbutton> 
                <z:tbutton onClick="del()"><img src="../Icons/icon003a3.gif" />ɾ��</z:tbutton>
                </td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.cms.site.ReferConfig.dg1DataBind" size="13">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="7%" ztype="RowNo"></td>
							<td width="8%" ztype="selector" field="CodeName">&nbsp;</td>
							<td width="45%"><b>������Դ</b></td>
                            <td>����ĸ��д</td>
						</tr>
						<tr onDblClick="edit()" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td height="22" align="center">&nbsp;</td>
							<td>&nbsp;</td>
							<td width="45%">${CodeName}</td>
                            <td width="40%">${Prop4}</td>
						</tr>
						<tr ztype="pagebar">
							<td height="25" colspan="8">${PageBar}</td>
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