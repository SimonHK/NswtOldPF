
<%@page import="com.nswt.schema.ZDUserSchema"%><%@page import="java.util.Date"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.extend.ExtendManager"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="com.nswt.member.Member"%>
<%@page import="com.nswt.framework.User"%>
<%
	String flag = "Y";

	String userName = request.getParameter("UserName");
	String passWord = request.getParameter("PassWord");
	ZDUserSchema user = new ZDUserSchema();
	user.setUserName(userName);
	if (user.fill()) {
		if (user.getPassword().equals(StringUtil.md5Hex(passWord))) {
			String VerifyCookie = StringUtil.md5Hex(user.getPassword().substring(0, 10) + (System.currentTimeMillis() + ""));
			User.setManager(true);
			User.setLogin(true);
			User.setUserName(userName);
			User.setRealName(user.getRealName());
			User.setType(user.getType());
			User.setMember(false);
			User.setValue("UserName", user.getUserName());
			User.setValue("Type", user.getType());
			//ExtendManager.executeAll("Member.Login", new Object[] { member });// ������չ
			if (StringUtil.isNotEmpty(user.getRealName())) {
				User.setValue("Name", user.getRealName());
			} else {
				User.setValue("Name", user.getUserName());
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append("<div id='loginMenu2'>");
			sb.append("<b>��ӭ��" + User.getRealName() + "</b>&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='goSystem();'>ϵͳ��̨</a>]&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='doSysLogout();'>�˳�</a>]</form>");
			sb.append("</div>");
			response.getWriter().write(flag + "&" + sb.toString());
		} else {
			flag = "N";
			response.getWriter().write(flag + "&��Ա���������������������");
		}
	} else {
		flag = "N";
		response.getWriter().write(flag + "&��Ա�������ڣ�����������");
	}
%>