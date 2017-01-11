<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var Type = "<%=request.getParameter("Type")%>";
function getSiteInfo(func){
	var url = $V("ServerAddr");
	var dc = new DataCollection();
	dc.add("ServerAddr",url);
	dc.add("Type",Type);
	Dialog.wait("��ȴ�...");
	Server.sendRequest("com.nswt.datachannel.CatalogInfoService.getRemoteSiteInfo",dc,function(response){
		Dialog.endWait();
		var dt = response.get("SiteTable");
		$("SiteID").clear();
		if(dt){
			for(var i=0;i<dt.getRowCount();i++){
		   		$("SiteID").add(dt.Rows[i].get("Name"),dt.Rows[i].get("ID"));
			}
			$("SiteID").selectedIndex = 0;
			if(func){
				func();
			}
		}else{
			Dialog.alert("��ȡ����ʧ��!");	
		}
	});
}
function getCatalogInfo(func){
	if(!$V("SiteID")){
		return;
	}
	var url = $V("ServerAddr");
	var dc = new DataCollection();
	dc.add("ServerAddr",url);
	dc.add("Type",Type);
	dc.add("SiteID",$V("SiteID"));
	Dialog.wait("��ȴ�...");
	Server.sendRequest("com.nswt.datachannel.CatalogInfoService.getRemoteCatalogInfo",dc,function(response){
		Dialog.endWait();
		var dt = response.get("CatalogTable");		
		$("CatalogID").clear();
		if(dt){
			for(var i=0;i<dt.getRowCount();i++){
			   $("CatalogID").add(dt.Rows[i].get("Name"),dt.Rows[i].get("ID"));
			}
			$("CatalogID").selectedIndex = 0;
			if(func){
				func();
			}
		}else{
			Dialog.alert("��ȡ����ʧ��!");	
		}
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<form id="form2">
<table width="500" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td width="112" height="10" class="tdgrey2">
		<input type="hidden" id="Type" value="USER">
	  <input type="hidden" id="ID" value="">		</td>
		<td width="378" class="tdgrey2"></td>
    </tr>
	<tr>
	  <td height="35" align="right" class="tdgrey1">��������ַ��</td>
	  <td height="35" class="tdgrey2"><input name="ServerAddr" type="text" verify="NotNull" id="ServerAddr" value="localhost" style="width:250px"></td>
    </tr>
	<tr>
	  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">ѡ��վ�㣺</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">
	  <z:select id="SiteID" verify="NotNull" listHeight="300" onChange="getCatalogInfo()" style="width:250px" method="com.nswt.datachannel.CatalogInfoService.getLocalSites"> </z:select>
	  <input type="button" name="Submit" value="��ȡվ����Ϣ" onClick="getSiteInfo()"></td>
	</tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">ѡ����Ŀ��</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">
	  <z:select id="CatalogID" verify="NotNull" listHeight="300" style="width:250px"> </z:select> 
	  <input type="button" name="Submit2" value="��ȡ��Ŀ��Ϣ" onClick="getCatalogInfo()"></td>
    </tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">��Կ��</td>
	  <td bordercolor="#eeeeee" class="tdgrey2"><input name="Password" type="text" id="Password" style="width:250px"></td>
    </tr>
</table>
</form>
</body>
</html>
