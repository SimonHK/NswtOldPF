<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�Զ���������ϸ��Ϣ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<%
String ID = request.getParameter("_TableID");
if(StringUtil.isEmpty(ID)){
	out.println("ID����Ϊ��");
	return;
}
%>
<body style=" text-align:center">
<table style="width:100%;height:100%;text-align:center">
  <tr><td valign="middle">
    <%=Form.getManageFormContent(ID)%>
  </td></tr>
  <tr><td>&nbsp;</td></tr>
</table>
</body>
</html>
