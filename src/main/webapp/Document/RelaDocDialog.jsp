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

function autoRela(){
	if(Parent.$V("Keyword")==""){
		Dialog.alert("δ��д�ؼ��֣����ܽ������ܹ���!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Keyword",Parent.$V("Keyword"));
	dc.add("CatalogID",Parent.$V("CatalogID"));
	dc.add("ArticleID",Parent.$V("ArticleID"));
	Dialog.wait("���ڽ�������ƥ�䣬��ȴ�...");
	Server.sendRequest("com.nswt.cms.document.Article.getAutoRelaIDs",dc,function(response){
		Dialog.endWait();
		if(response.Status=="0"){
			Dialog.alert(response.Message);
		}else{			
			var ids = response.get("IDs");
			if(ids){
				var oldIDs = $V("ArticleIDs");
				if(oldIDs){					
					var arr = ids.split(",");
					for(var i=arr.length-1;i>=0;i--){
						if((","+oldIDs+",").indexOf(","+arr[i]+",")>=0){
							arr.splice(i,1);
						}
					}
					ids = arr.join()+","+$V("ArticleIDs");
					if(ids.startsWith(",")){
						ids = ids.substring(1);
					}
				}
			}else{
				ids = $V("ArticleIDs");
			}
			$S("ArticleIDs",ids);
			DataGrid.setParam("dg1","RelativeArticle",ids);
		  DataGrid.loadData("dg1");
		}
	});
}

function autoRelaSave(){
	if($DW.$("dg1").getSelectedValue()!=null){
		$S("ArticleIDs",$DW.$("dg1").getSelectedValue());
		$("dg1").setParam("RelativeArticle",$DW.$("dg1").getSelectedValue());
		$("dg1").loadData();
	}
	$D.close();
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	var arr = [];
	var dt = $("dg1").DataSource;
	for(var i=0;i<dt.getRowCount();i++){
		arr.push(dt.Rows[i].get("ID"));
	}
	$S("ArticleIDs",arr.join());
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
					src="../Icons/icon018a1.gif" />����ĵ��б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />���</z:tbutton> 
				<z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton>
				<z:tbutton onClick="autoRela()"><img src="../Icons/icon021a13.gif" width="20" height="20" />�������</z:tbutton>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.nswt.cms.document.Article.relativeDg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="8%" ztype="selector" field="id">&nbsp;</td>
							<td width="76%" drag="true"><b>����</b></td>
							<td width="8%"><b>����</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td height="15">&nbsp;</td>
							<td>&nbsp;</td>
							<td title="���ߣ�${author} ����ʱ�䣺${addtime}">${Title}</td>
							<td><a href="#;" onClick="deleteRow(${ID})">ɾ��</a></td>
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
