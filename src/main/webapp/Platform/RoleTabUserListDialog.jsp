<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title> </title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSearch(){
	var name = "";
	if ($V("SearchUserName") != "�������û�������ʵ����") {
		name = $V("SearchUserName").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchUserName",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchUserName'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchUserName") == "�������û�������ʵ����") {
		$S("SearchUserName","");
	}
}
</script>
</head>
<body>
<table width="660" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td>
      <div style="float: right; white-space: nowrap;">
	  <input name="SearchUserName" type="text" id="SearchUserName" value="�������û�������ʵ����" onFocus="delKeyWord();" style="width:150px">
      	  <input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="doSearch()">
      	</div></td>
    </tr>
    <tr>
		<td style="padding-top:4px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
		<z:datagrid id="dg1" method="com.nswt.platform.RoleTabBasic.bindUserList" size="10">
		     <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
		   	        <td width="7%" ztype="RowNo">���</td>
			        <td width="7%" ztype="selector" field="UserName">&nbsp;</td>
		            <td width="23%" ><b>�û���</b></td>
					<td width="25%" ><b>��ʵ����</b></td>
					<td width="16%" ><b>�û�״̬</b></td>
					<td width="22%" ><b>�����ʼ�</b></td>
			    </tr>
			    <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
		          <td >&nbsp;</td>
			      <td>&nbsp;</td>
				  <td>${UserName}</td>
				  <td>${RealName}</td>
				  <td>${StatusName}</td>
				  <td>${Email}</td>
			    </tr>
				<tr ztype="pagebar">
				  <td colspan="6" align="center">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid>
		</td>
	</tr>
    
</table>
</body>
</html>
