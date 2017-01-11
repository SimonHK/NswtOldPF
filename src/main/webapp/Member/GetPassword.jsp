<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.framework.Config"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="com.nswt.member.Member"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ա��������</title>
<link href="default.css" rel="stylesheet" type="text/css">
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function resetPassword(){
	if($V("NewPassWord").replace(/(^\s*)|(\s*$)/g,"")==""||$V("ConfirmPassword").replace(/(^\s*)|(\s*$)/g,"")==""){
		Dialog.alert("����д������Ϣ");
		return;
	}
	var dc = Form.getData("passForm");
	Server.sendRequest("com.nswt.member.MemberInfo.resetPassword",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location = "Login.jsp?SiteID="+response.get("SiteID");
			}						   
		});
	});
}

</script>
</head>
<%
	String userName = request.getParameter("User");
	String pwq = request.getParameter("pwq");
	boolean flag = false;
	if(StringUtil.isNotEmpty(userName)&&StringUtil.isNotEmpty(pwq)){
		Member member = new Member(userName);
		if(member.fill()){
			if(StringUtil.isNotEmpty(member.getLoginMD5())&&member.getLoginMD5().equalsIgnoreCase(pwq)){
				flag = true;
			}
		}
	}
%>
<body>
<%@ include file="../Include/Top.jsp" %>
<div style="width:950px; margin:auto;">
  <table width="100%" border="0" cellspacing="15" cellpadding="0">
    <tr valign="top">
      <td>
      <div style="border:1px solid #ddd; padding:30px 20px;zoom:1;">
      <div style="border-bottom:1px solid #ddd; padding-bottom:4px; margin-bottom:10px;"><h4>��������</h4></div>
      <table width="100%" border="0" cellspacing="50" cellpadding="0">
		<%if(flag){%>
        <tr valign="top">
          <td width="55%">
          <form id="passForm" style="margin:20px 15px;">
          <table width="100%" border="0" cellspacing="9" cellpadding="0">
            <tr>
                <td align="right">������:<input type="hidden" id="UserName" name="UserName" value="<%=userName %>"></td>
                <td><input type="password" class="name" name="NewPassWord" id="NewPassWord" value=""/></td>
            </tr>
            <tr>
                <td align="right">ȷ��������:</td>
                <td><input type="password" class="name" name="ConfirmPassword" id="ConfirmPassword" value=""/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="button" class="btn" onclick="resetPassword();" value=" �� �� "/></td>
            </tr>
            </table>
            </form>
            </td>
        </tr>
        <%}else{%>
        <tr valign="middle">
          <td width="55%">
          <table width="100%" border="0" cellspacing="9" cellpadding="0" height="100%">
            <tr>
                <td align="center">��Ч�����ӣ������ѹ���</td>
            </tr>
            </table>
            </td>
        </tr>
        <%}%>
      </table></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>
