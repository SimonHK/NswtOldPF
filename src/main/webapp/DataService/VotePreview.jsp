<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>ͶƱ����Ԥ��</title>
<script src="../Tools/vote.js"  type="text/javascript"></script>
<link href="../Tools/vote.css" type="text/css" rel="stylesheet" />
<%
	String ID = request.getParameter("ID");
	ZCVoteSchema vote = new ZCVoteSchema();
	vote.setID(ID);
	vote.fill();
%>
</head>
<body style=" text-align:center">
<div id='vote_<%=ID%>' class='votecontainer' style='text-align:left'>
<h2>��ӭ�������:<%=vote.getTitle() %></h2>
<form id='voteForm_<%=ID%>' name='voteForm_<%=ID%>'
	action='../Services/VoteResult.jsp' method='post' target='_blank'><input
	type='hidden' id='ID' name='ID' value='<%=ID%>'> <input
	type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>

<dl>
	<z:simplelist method="com.nswt.cms.dataservice.Vote.getVoteSubjects">
		<dt id="${ID}">${_RowNo}.${Subject}</dt>
		<z:simplelist method="com.nswt.cms.dataservice.Vote.getVoteItems">
			<dd>
				${html}
			</dd>
		</z:simplelist>
	</z:simplelist>
</dl>
<%if(!"N".equalsIgnoreCase(vote.getVerifyFlag())){%>
<dl>
<dd><img src='<%=Config.getContextPath()%>AuthCode.jsp' alt='���ˢ����֤��' height='14' align='absmiddle' style='cursor:pointer;' onClick="this.src='<%=Config.getContextPath()%>AuthCode.jsp?Height=14&Width=50&'+new Date().getTime()" />&nbsp; <input	name='VerifyCode' type='text' style='width:60px' id='VerifyCode' class='inputText' onfocus='this.select();'/></dd>
</dl>
<%}%>
<dl>
<%
if ("N".equals(vote.getProp4())) {// �Ƿ���ʵ�鿴��ť
%>
<dd><input type='submit' value='�ύ'
		onclick='return checkVote(<%=ID%>);' ></dd>
<%
} else {
%>
<dd><input type='submit' value='�ύ'
		onclick='return checkVote(<%=ID%>);' >&nbsp;&nbsp<input
		type='button' value='�鿴' onclick='javascript:window.open("../Services/VoteResult.jsp?ID=<%=ID%>")'></dd>
<%} %>
</dl>
</form>
	
</div>

</body>
</html>
