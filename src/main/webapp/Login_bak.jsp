<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=GBK"%> 
<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.platform.*"%>
<%@page import="com.nswt.framework.license.*"%>
<%
//if(session.getAttribute(com.nswtzas.Constant.UserSessionAttrName)!=null){
//	Login.ssoLogin(request,response,((UserData)session.getAttribute(com.nswtzas.Constant.UserSessionAttrName)).getUserName());
//}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=Config.getAppCode()%><%=Config.getAppName()%></title>
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<style>
.input1{ border:1px solid #84A1BD; width:100px; height:20px; line-height:23px;}
.input2{ border:1px solid #84A1BD; width:68px; height:20px; line-height:23px;}
.button1{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(images/bt001.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.button2{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(images/bt002.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.STYLE3 {
	color: #FF0000;
	font-weight: bold;
}
</style>
<script src="Framework/Main.js"></script>
<script>
function login(){
	if($V("UserName").trim()==""||$V("Password").trim()==""){
		alert("请输入用户名和密码");
		return;
	}
	var dc = Form.getData("form1");
	Server.sendRequest("com.nswt.platform.Login.submit",dc,function(response){
		if(response&&response.Status==0){
			alert(response.Message);
			if($("spanVerifyCode").innerText.trim()==""){
				var sb = [];
				sb.push("&nbsp;验证码：");
				sb.push("<img src=\"AuthCode.jsp?Height=18&Width=60\" alt=\"点击刷新验证码\" height=\"18\" ");
				sb.push("align=\"absmiddle\" style=\"cursor:pointer;\" ");
				sb.push("onClick=\"this.src='AuthCode.jsp?Height=18&Width=60&'+new Date().getTime()\" />&nbsp; <input ");
				sb.push("name=\"VerifyCode\" type=\"text\" style=\"width:60px\" id=\"VerifyCode\" ");
				sb.push("class=\"inputText\" onfocus=\"this.select();\"/>");
				$("spanVerifyCode").innerHTML = sb.join('');
			}
			$("UserName").style.width = "80px";
			$("Password").style.width = "80px";
		}
	});
}
document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		login();
	}
}
Page.onLoad(function(){
	if(window.top.location != window.self.location){
		window.top.location = window.self.location;
	}else{
		$("UserName").focusEx();
		$S("UserName","admin");
		$S("Password","admin");
	}
});
</script>
</head>
<body>
<form id="form1" method="post" style=" display:block;height:100%;">
<table width="100%" height="100%">
	<tr>
		<td align="center" valign="middle">
		<table
			style=" height:283px; width:620px; background:url(Platform/Images/loginbg.jpg) no-repeat 0px 0px;">
			<tr>
				<td>
				<div style="height:213px; width:620px;"></div>
				<div style="height:70px; width:620px;margin:0px auto 0px auto;">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top:8px;">
					<tr>
						<td align="center">用户名：
					    <input name="UserName" type="text" style="width:120px"
							id="UserName" class="inputText" onfocus="this.select();"/>
					    &nbsp;密码：
					    <input name="Password" type="password" style="width:120px"
							id="Password" class="inputText" onfocus="this.select();"/>
					    <span id='spanVerifyCode'></span>	
						&nbsp;<img src="Platform/Images/loginbutton.jpg" name="LoginImg" align="absmiddle" id="LoginImg" style="cursor:pointer"
							onClick="login();" /></td>
					</tr>
					<tr>
						<td height="23" align="center" valign="bottom">Copyright
						&copy; 2007-2010 www.nswt.com Inc. All rights reserved. 新宇联安软件 版权所有</td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		<br>
		</td>
	</tr>
</table>
</form>
</body>
</html>
