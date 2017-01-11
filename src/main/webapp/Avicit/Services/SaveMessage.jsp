
<%@page import="com.nswt.framework.data.DataTable"%><%@page import="com.nswt.schema.ZCBoardMessageSchema"%>
<%@page import="com.nswt.member.Member"%>
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.platform.pub.NoUtil"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="java.util.*"%>
<%
String UserName = request.getParameter("UserName");
String PassWord = request.getParameter("PassWord");
String QQ = request.getParameter("QQ");
String IP = request.getRemoteAddr();
String HiddenUserName = request.getParameter("HiddenUserName");
String Email = request.getParameter("Email");
String Title = request.getParameter("Title");
String Content = request.getParameter("MsgContent");
String BoardID = request.getParameter("BoardID");
if(!StringUtil.isEmpty(Content)||!StringUtil.isEmpty(BoardID)||new QueryBuilder("select count(*) from ZCMessageBoard where ID = ?",Long.parseLong(BoardID)).executeInt()>0){
	if(User.isLogin()){
		UserName = User.getUserName();
		if(StringUtil.isEmpty(Email)){
			Member member = new Member(UserName);	
			member.fill();
			Email = member.getEmail();
		}
	}else{
		if("on".equals(HiddenUserName)){
			UserName = "��������";
		}else{
			if(StringUtil.isNotEmpty(UserName)&&StringUtil.isNotEmpty(PassWord)){
				Member member = new Member(UserName);	
				if(member.isExists()&&member.fill()&&member.checkPassWord(PassWord)){
					User.setManager(false);
					User.setLogin(true);
					User.setUserName(member.getUserName());
					User.setRealName(member.getName());
					User.setType(member.getType());
					User.setMember(true);
					User.setValue("SiteID",member.getSiteID()+"");
					User.setValue("UserName", member.getUserName() + "");
					User.setValue("Type", member.getType());
				}else{
					out.print("<script type='text/javascript'>alert('�û��������ڻ��������');window.history.go(-1);</script>");
					return;	
				}
			}else{
				out.print("<script type='text/javascript'>alert('�û������������');window.history.go(-1);</script>");
				return;	
			}
		}
	}
	if(StringUtil.isEmpty(Title)){
		Title = "[������]";
	}
	DataTable dtd = new QueryBuilder("select * from ZCMessageBoard where ID=?",Long.parseLong(BoardID)).executeDataTable();
	String IsOpen = new QueryBuilder("select IsOpen from ZCMessageBoard where ID=?",Long.parseLong(BoardID)).executeString();
	ZCBoardMessageSchema message = new ZCBoardMessageSchema();
	message.setID(NoUtil.getMaxID("BoardMessageID"));
	message.setBoardID(BoardID);
	message.setTitle(StringUtil.htmlEncode(Title));
	message.setContent(StringUtil.htmlEncode(Content));
	if("Y".equals(IsOpen)){
		message.setPublishFlag("N");
	}else{
		message.setPublishFlag("Y");
	}
	message.setReplyFlag("N");
	message.setEMail(Email);
	message.setQQ(QQ);
	message.setIP(IP);
	message.setAddUser(UserName);
	message.setAddTime(new Date());
	if(message.insert()){
		out.println("<script type='text/javascript'>alert('���������Ѿ��ɹ��ύ');window.location='"+request.getHeader("REFERER")+"';</script>");
	}
}else{
	out.println("<script type='text/javascript'>alert('δ֪����');window.history.go(-1);</script>");
	return;
}

%>