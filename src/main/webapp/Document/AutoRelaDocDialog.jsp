<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Array.prototype.unique = function(){  
 var  a = {};
 for(var i=0; i <this.length; i++){  
    if(typeof a[this[i]] == "undefined")  
      a[this[i]] = 1;  
 }  
 this.length = 0;  
 for(var i in a)  
    this[this.length] = i;  
 return this;  
}  

function add(){
	var diag  = new Dialog("Diag3");
	diag.Width = 830;
	diag.Height = 460;
	diag.Title ="�������";
	diag.URL = "Document/DocListDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = getDocList;
	diag.show();
}

function getDocList(){
	var dt = $DW.DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("��ѡ���ĵ���");
		return;
	}
	var articleIDs =[];
	for(var i=0;i<drs.length;i++){
		var dr = drs[i];
		articleIDs.push(dr.get("ID"));
	}
	
	var allIDs;
  var oldArticleIDs = $V("ArticleIDs");
  if(oldArticleIDs != ""){
	  oldArticleIDs = oldArticleIDs.split(",");	
	  allIDs = oldArticleIDs.concat(articleIDs).unique();
  }else{
  	allIDs = articleIDs;
  }
	
	allIDs.remove($V("ArticleID"));
	
	$S("ArticleIDs",allIDs.join(","));

	DataGrid.setParam("dg1","RelativeArticle",$V("ArticleIDs"));
  DataGrid.loadData("dg1");
	$D.close();
}

function deleteRow(ID){
	var articleIDs = $V("ArticleIDs");
	articleIDs = articleIDs.split(",");	
	articleIDs.remove(ID);
	$S("ArticleIDs",articleIDs.join(","));

	DataGrid.setParam("dg1","RelativeArticle",$V("ArticleIDs"));
  DataGrid.loadData("dg1");
}

function del(){
  var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�������£�");
		return;
	}
	
	var articleIDs = $V("ArticleIDs");
	articleIDs = articleIDs.split(",");	
	for(var i = 0;i<arr.length;i++){
		articleIDs.remove(arr[i]);
	}
  
	$S("ArticleIDs",articleIDs.join(","));
	DataGrid.setParam("dg1","RelativeArticle",$V("ArticleIDs"));
  DataGrid.loadData("dg1");
}

function keywordRela(){
	
}
</script>
</head>
<body>
<input type="hidden" id="ArticleIDs"
	value="<%=request.getParameter("RelativeArticle")%>">
<input type="hidden" id="ArticleID"
	value="<%=request.getParameter("ArticleID")%>">
<table width="100%" border="0" cellspacing="4" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon018a1.gif" />ϵͳ�����Ƽ��ĺͱ��ĵ���ص��ĵ��б�</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.document.Article.autoRelativeDataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="10%" height="30" ztype="RowNo">���</td>
							<td width="10%" ztype="selector" field="id">&nbsp;</td>
							<td width="80%"><b>����</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td height="15">&nbsp;</td>
							<td>&nbsp;</td>
							<td title="���ߣ�${author} ����ʱ�䣺${addtime}">${Title}</td>
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
