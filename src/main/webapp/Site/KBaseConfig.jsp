<%--
  Created by IntelliJ IDEA.
  User: SimonKing
  Date: 16/11/1
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>发布统计</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
</head>
<body >
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <z:tab>
                <z:childtab id="ConfigCatalogShow" src="CatalogShowConfig.jsp" selected="true"><img src="../Icons/icon023a11.gif" /><b>库结构设置</b></z:childtab>
                <z:childtab id="ConfigRefer" src="ReferConfig.jsp" ><img src="../Icons/icon003a16.gif" /><b>资料来源管理</b></z:childtab>
            </z:tab>
        </td>
    </tr>
</table>
</body>
</html>
