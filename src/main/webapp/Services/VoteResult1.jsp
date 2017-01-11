<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.nswt.cms.dataservice.VoteResult"%>
<%
request.setCharacterEncoding("GBK");
response.setContentType("text/html;charset=GBK");
VoteResult.vote(request,response);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>投票调查结果</title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Chart.js"></script>


</head>
<body>
<script>
window.close();
</script>
</body>
</html>
