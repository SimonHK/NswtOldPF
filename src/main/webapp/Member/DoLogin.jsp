<%@page import="java.util.Date"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.extend.ExtendManager"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="com.nswt.member.Member"%>
<%@page import="com.nswt.framework.User"%>
<%
	String flag = "Y";

	String userName = request.getParameter("UserName");
	String passWord = request.getParameter("PassWord");
	String siteID = request.getParameter("SiteID");
	Member member = new Member(userName, siteID);
	if (member.isExistsCurrentSite()) {
		member.fill();
		if (member.checkPassWord(passWord)) {
			String VerifyCookie = StringUtil.md5Hex(member.getPassword().substring(0, 10)
					+ (System.currentTimeMillis() + ""));
			//Cookie.setCookie("VerifyCookie", VerifyCookie, CookieTime);
			//Cookie.setCookie("UserName", userName, CookieTime);
			User.setManager(false);
			User.setLogin(true);
			User.setUserName(userName);
			User.setRealName(member.getName());
			User.setType(member.getType());
			User.setMember(true);
			User.setValue("SiteID", member.getSiteID() + "");
			User.setValue("UserName", member.getUserName() + "");
			User.setValue("Type", member.getType());
			ExtendManager.executeAll("Member.Login", new Object[] { member });// ������չ
			if (StringUtil.isNotEmpty(member.getName())) {
				User.setValue("Name", member.getName());
			} else {
				User.setValue("Name", member.getUserName());
			}
			member.setLoginMD5(VerifyCookie);
			member.setMemberLevel(new QueryBuilder("select ID from ZDMemberLevel where Score <= "
					+ member.getScore() + " order by Score desc").executeOneValue()
					+ "");
			member.setLastLoginTime(new Date());
			member.update();
			
			StringBuffer sb = new StringBuffer();
			sb.append("<div id='loginMenu2'>");
			sb.append("<b>��ӭ��" + User.getRealName() + "</b>&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='goMemberCenter();'>��Ա����</a>]&nbsp;");
			//sb.append("[<a href='javascript:void(0);' onClick='goShopCart();'>���ﳵ</a>]&nbsp;");
			//sb.append("[<a href='javascript:void(0);' onClick='goFavorite();'>�ղؼ�</a>]&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='doLogout();'>�˳�</a>]</form>");
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