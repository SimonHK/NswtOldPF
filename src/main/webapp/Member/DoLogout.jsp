<%@page import="com.nswt.framework.User"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String SiteID = User.getValue("SiteID") + "";
	User.setUserName("");
	User.setLogin(false);
	User.setMember(false);
	User.destory();
	String flag = "Y";
	
	StringBuffer sb = new StringBuffer();
	sb.append("<form name='form'>");
	sb.append("�û�����<input type='text' id='name' size='10'/>");
	sb.append("���룺<input type='password' id='code' size='10'/>&nbsp;");
	sb.append("<input type='button' class='btn' value='��¼' onClick='doLogin()'/>&nbsp;");
	sb.append("<input type='button' value='ע��' onClick='doRegister()' class='btn' />&nbsp;");
	//sb.append("[<a href='javascript:void(0); onClick='goShopCart();'>���ﳵ</a>]&nbsp;");
	
	response.getWriter().write(flag + "&" + sb.toString());
%>