<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script>
function openArticle(ArticleID,BackupNo){
	if(screen.width==800){
		var width = 800,height = 600,leftm = 0,topm = 0;
	}else if(screen.width>800){
	  	var width  = Math.floor( screen.width  * .9 );
  		var height = Math.floor( screen.height * .98 );
  		var leftm  = Math.floor( screen.width  * .02);
 		  var topm   = Math.floor( screen.height * .02);
	}else{
		return;
	}
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  var url = "ArticleView.jsp?ArticleID=" + ArticleID+"&BackupNo="+BackupNo;
  window.open(url,"",args);
}

function closeX(){
   Dialog.close();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	
	if(!confirm("ȷ��ɾ��ѡ�е��ĵ���")){
		return;
	}
	var dc = new DataCollection();
	dc.add("BackupNo",arr.join());
	Server.sendRequest("com.nswt.cms.document.ArticleHistory.del",dc,function(response){
		if(response.Status==0){
			alert(response.Message);
		}else{
			alert("�ɹ�ɾ���ĵ��汾");
			DataGrid.setParam("dg1",Constant.PageIndex,0);
      DataGrid.loadData("dg1");
		}
	});
}
function restore(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		alert("����ѡ��Ҫ�ָ��İ汾��");
		return;
	}
	
	if(!confirm("ȷ��ʹ��ѡ�е��ĵ����лָ���")){
		return;
	}
	var dc = new DataCollection();
	dc.add("BackupNo",arr[0]);
	dc.add("ArticleID",$V("ArticleID"));
	Server.sendRequest("com.nswt.cms.document.ArticleHistory.restore",dc,function(response){
		if(response.Status==0){
			alert(response.Message);
		}else{
			alert("�ɹ��ָ��ĵ�");
			DataGrid.setParam("dg1",Constant.PageIndex,0);
      DataGrid.loadData("dg1");
			if(isIE){
			  window.Parent.reloadArticle();
			}else{
			  window.parent.opener.reloadArticle();
			}
			
		}
	});
}

function add(){
	var dc = new DataCollection();
	dc.add("ArticleID",$V("ArticleID"));
	Server.sendRequest("com.nswt.cms.document.ArticleHistory.addVersion",dc,function(response){
		if(response.Status==0){
			alert(response.Message);
		}else{
			window.location=window.location;
		}
	});
}

function compare(){
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="65%" style="padding:4px;">
		<div style="float: left"><z:tbutton onClick="add()">
			<img src="../Icons/icon018a9.gif" />����Ϊ�°汾</z:tbutton> <z:tbutton
			onClick="restore()">
			<img src="../Icons/icon018a16.gif" />�ָ�</z:tbutton> <z:tbutton onClick="del()">
			<img src="../Icons/icon018a5.gif" />ɾ��</z:tbutton></div>
		</td>
	</tr>
	<tr>
		<td style="padding:4px;"><input type="hidden" name="AritcleID"
			id="ArticleID" value="<%=request.getParameter("ArticleID")%>">
		<z:datagrid id="dg1"
			method="com.nswt.cms.document.ArticleHistory.historyDataBind"
			size="12">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="4%" height="30">&nbsp;</td>
					<td width="4%" ztype="selector" field="BackupNo">&nbsp;</td>
					<td width="20%"><strong>�汾��</strong></td>
					<td width="50%"><b>����</b></td>
					<td width="30%"><strong>����ʱ��</strong></td>
				</tr>
				<tr onDblClick="openArticle(${ID},'${BackupNo}')"
					style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>${BackupNo}</td>
					<td><a href="#" onClick="openArticle(${ID},'${BackupNo}')">${Title}</a></td>
					<td>${BackupTime}</td>
				</tr>
				<tr ztype="SimplePageBar">
					<td colspan="5" align="left">
					<div align="center">${PageBar}</div>
					</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</body>
</html>
