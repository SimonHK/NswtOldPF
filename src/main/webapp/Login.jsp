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
	body
	{
		font: 12px 'Lucida Sans Unicode', 'Trebuchet MS', Arial, Helvetica;
		margin: 0;
		background-color: #d9dee2;
		background-image: -webkit-gradient(linear, left top, left bottom, from(#ebeef2), to(#d9dee2));
		background-image: -webkit-linear-gradient(top, #ebeef2, #d9dee2);
		background-image: -moz-linear-gradient(top, #ebeef2, #d9dee2);
		background-image: -ms-linear-gradient(top, #ebeef2, #d9dee2);
		background-image: -o-linear-gradient(top, #ebeef2, #d9dee2);
		background-image: linear-gradient(top, #ebeef2, #d9dee2);
	}

	#form1
	{
		background-color: #fff;
		background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#eee));
		background-image: -webkit-linear-gradient(top, #fff, #eee);
		background-image: -moz-linear-gradient(top, #fff, #eee);
		background-image: -ms-linear-gradient(top, #fff, #eee);
		background-image: -o-linear-gradient(top, #fff, #eee);
		background-image: linear-gradient(top, #fff, #eee);
		height: 240px;
		width: 400px;
		margin: -150px 0 0 -230px;
		padding: 30px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 0;
		-moz-border-radius: 3px;
		-webkit-border-radius: 3px;
		border-radius: 3px;
		-webkit-box-shadow:
				0 0 2px rgba(0, 0, 0, 0.2),
				0 1px 1px rgba(0, 0, 0, .2),
				0 3px 0 #fff,
				0 4px 0 rgba(0, 0, 0, .2),
				0 6px 0 #fff,
				0 7px 0 rgba(0, 0, 0, .2);
		-moz-box-shadow:
				0 0 2px rgba(0, 0, 0, 0.2),
				1px 1px   0 rgba(0,   0,   0,   .1),
				3px 3px   0 rgba(255, 255, 255, 1),
				4px 4px   0 rgba(0,   0,   0,   .1),
				6px 6px   0 rgba(255, 255, 255, 1),
				7px 7px   0 rgba(0,   0,   0,   .1);
		box-shadow:
				0 0 2px rgba(0, 0, 0, 0.2),
				0 1px 1px rgba(0, 0, 0, .2),
				0 3px 0 #fff,
				0 4px 0 rgba(0, 0, 0, .2),
				0 6px 0 #fff,
				0 7px 0 rgba(0, 0, 0, .2);
	}

	#form1:before
	{
		content: '';
		position: absolute;
		z-index: -1;
		border: 1px dashed #ccc;
		top: 5px;
		bottom: 5px;
		left: 5px;
		right: 5px;
		-moz-box-shadow: 0 0 0 1px #fff;
		-webkit-box-shadow: 0 0 0 1px #fff;
		box-shadow: 0 0 0 1px #fff;
	}

	/*--------------------*/

	h1
	{
		text-shadow: 0 1px 0 rgba(255, 255, 255, .7), 0px 2px 0 rgba(0, 0, 0, .5);
		text-transform: uppercase;
		text-align: center;
		color: #666;
		margin: 0 0 30px 0;
		letter-spacing: 4px;
		font: normal 26px/1 Verdana, Helvetica;
		position: relative;
	}

	h1:after, h1:before
	{
		background-color: #777;
		content: "";
		height: 1px;
		position: absolute;
		top: 15px;
		width: 120px;
	}

	h1:after
	{
		background-image: -webkit-gradient(linear, left top, right top, from(#777), to(#fff));
		background-image: -webkit-linear-gradient(left, #777, #fff);
		background-image: -moz-linear-gradient(left, #777, #fff);
		background-image: -ms-linear-gradient(left, #777, #fff);
		background-image: -o-linear-gradient(left, #777, #fff);
		background-image: linear-gradient(left, #777, #fff);
		right: 0;
	}

	h1:before
	{
		background-image: -webkit-gradient(linear, right top, left top, from(#777), to(#fff));
		background-image: -webkit-linear-gradient(right, #777, #fff);
		background-image: -moz-linear-gradient(right, #777, #fff);
		background-image: -ms-linear-gradient(right, #777, #fff);
		background-image: -o-linear-gradient(right, #777, #fff);
		background-image: linear-gradient(right, #777, #fff);
		left: 0;
	}

	/*--------------------*/

	fieldset
	{
		border: 0;
		padding: 0;
		margin: 0;
	}

	/*--------------------*/

	#inputs input
	{
		background: #f1f1f1 url(/dist/uploads/2011/09/login-sprite.png) no-repeat;
		padding: 15px 15px 15px 30px;
		margin: 0 0 10px 0;
		width: 353px; /* 353 + 2 + 45 = 400 */
		border: 1px solid #ccc;
		-moz-border-radius: 5px;
		-webkit-border-radius: 5px;
		border-radius: 5px;
		-moz-box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
		-webkit-box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
		box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
	}

	#username
	{
		background-position: 5px -2px !important;
	}

	#password
	{
		background-position: 5px -52px !important;
	}

	#inputs input:focus
	{
		background-color: #fff;
		border-color: #e8c291;
		outline: none;
		-moz-box-shadow: 0 0 0 1px #e8c291 inset;
		-webkit-box-shadow: 0 0 0 1px #e8c291 inset;
		box-shadow: 0 0 0 1px #e8c291 inset;
	}

	/*--------------------*/
	#actions
	{
		margin: 25px 0 0 0;
	}

	#submit
	{
		background-color: #ffb94b;
		background-image: -webkit-gradient(linear, left top, left bottom, from(#fddb6f), to(#ffb94b));
		background-image: -webkit-linear-gradient(top, #fddb6f, #ffb94b);
		background-image: -moz-linear-gradient(top, #fddb6f, #ffb94b);
		background-image: -ms-linear-gradient(top, #fddb6f, #ffb94b);
		background-image: -o-linear-gradient(top, #fddb6f, #ffb94b);
		background-image: linear-gradient(top, #fddb6f, #ffb94b);

		-moz-border-radius: 3px;
		-webkit-border-radius: 3px;
		border-radius: 3px;

		text-shadow: 0 1px 0 rgba(255,255,255,0.5);

		-moz-box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3) inset;
		-webkit-box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3) inset;
		box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3) inset;

		border-width: 1px;
		border-style: solid;
		border-color: #d69e31 #e3a037 #d5982d #e3a037;

		float: left;
		height: 35px;
		padding: 0;
		width: 120px;
		cursor: pointer;
		font: bold 15px Arial, Helvetica;
		color: #8f5a0a;
	}

	#submit:hover,#submit:focus
	{
		background-color: #fddb6f;
		background-image: -webkit-gradient(linear, left top, left bottom, from(#ffb94b), to(#fddb6f));
		background-image: -webkit-linear-gradient(top, #ffb94b, #fddb6f);
		background-image: -moz-linear-gradient(top, #ffb94b, #fddb6f);
		background-image: -ms-linear-gradient(top, #ffb94b, #fddb6f);
		background-image: -o-linear-gradient(top, #ffb94b, #fddb6f);
		background-image: linear-gradient(top, #ffb94b, #fddb6f);
	}

	#submit:active
	{
		outline: none;

		-moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
		-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
		box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	}

	#submit::-moz-focus-inner
	{
		border: none;
	}

	#actions a
	{
		color: #3151A2;
		float: right;
		line-height: 35px;
		margin-left: 10px;
	}

	/*--------------------*/

	#back
	{
		display: block;
		text-align: center;
		position: relative;
		top: 60px;
		color: #999;
	}
	/**by hongkai end*/
</style>
<script src="Framework/Main.js"></script>
<script>
function login(){
	if($V("UserName").trim()==""||$V("Password").trim()==""){
		alert("�������û���������");
		return;
	}
	var dc = Form.getData("form1");
	Server.sendRequest("com.nswt.platform.Login.submit",dc,function(response){
		if(response&&response.Status==0){
			alert(response.Message);
			if($("spanVerifyCode").innerText.trim()==""){
				var sb = [];
				sb.push("&nbsp;��֤�룺");
				sb.push("<img src=\"AuthCode.jsp?Height=18&Width=60\" alt=\"���ˢ����֤��\" height=\"18\" ");
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
<form id="form1" method="post">
<h1>Ӧ����ƽ̨</h1>
<fieldset id="inputs">
	<input id="UserName" type="text" placeholder="�û���" autofocus required onfocus="this.select();">
	<input id="Password" type="password" placeholder="�û�����" required onfocus="this.select();">
	<span id='spanVerifyCode'></span>
</fieldset>
<fieldset id="actions">
	<img src="Platform/Images/loginbutton.jpg" name="LoginImg" align="absmiddle" id="LoginImg" style="cursor:pointer" onClick="login();" />
	<%--<input type="submit" id="submit" value="�� ¼" onclick="return login();">--%>
<a href="">��������?</a><a href="">ע��</a><a href="Member/Login.jsp">�ҵĿռ�</a>
</fieldset>
<a href="/slick-login-form-with-html5-css3" id="back">Back to article...</a>
</form>
</body>
</html>
