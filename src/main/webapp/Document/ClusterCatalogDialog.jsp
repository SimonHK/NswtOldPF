<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
	  <td height="35" valign="middle" class="tdgrey1">&nbsp;&nbsp;ѡ��Ҫ�ַ�������վȺ��Ŀ</td>
    </tr>
	<tr>
	  <td height="35" align="center" class="tdgrey2"><z:datagrid id="dg1" method="com.nswt.cms.document.ArticleDeployCatalog.articleDialogDataBind" page="false" size="4" autoFill="false">
        <table width="98%" cellpadding="2" cellspacing="0" class="dataTable">
          <tr ztype="head" class="dataTableHead">
            <td width="9%" ztype="Selector" field="MD5"></td>
            <td width="31%"><b>Ӧ������</b></td>
            <td width="20%"><b>��Ŀ����</b></td>
            <td width="40%"><b>��������ַ</b></td>
          </tr>
          <tr onclick="this" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
            <td></td>
            <td>${SiteName}</td>
            <td>${CatalogName}</td>
             <td>${ServerAddr}</td>
         </tr>
        </table>
	    </z:datagrid></td>
    </tr>
</table>
</form>
</body>
</html>
