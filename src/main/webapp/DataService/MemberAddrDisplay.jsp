<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.dataservice.MemberAddr.initDialog">
	<table width="100%" cellpadding="2" cellspacing="0">
    	<tr>
            <td width="13%" align="right">&nbsp;</td>
            <td width="37%">&nbsp;</td>
            <td width="13%" align="right">&nbsp;</td>
            <td width="37%">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">�ջ��ˣ�</td>
            <td>${RealName}</td>
            <td align="right">�̶��绰��</td>
            <td>${Tel}</td>
        </tr>
        <tr>
            <td align="right">ʡ�ݣ�</td>
            <td>${ProvinceName}&nbsp;${CityName}&nbsp;${DistrictName}</td>
            <td align="right">�ֻ���</td>
            <td>${Mobile}</td>
        </tr>
        <tr>
            <td align="right">��ַ��</td>
            <td>${Address}&nbsp;&nbsp;�ʱࣺ${ZipCode}</td>
            <td align="right">�����ʼ���</td>
            <td>${Email}</td>
        </tr>
    </table>
</z:init>
</body>
</html>
