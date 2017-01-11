<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="z" %>
<%@page import="com.nswt.framework.Config"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ա����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function doLogout(){
	Dialog.confirm("��ȷ���˳���",function(){
		window.location = "Logout.jsp?SiteID="+<%=request.getParameter("SiteID")%>;													
	});
}

function save(){
	var dc = Form.getData("InfoForm");
	Server.sendRequest("com.nswt.member.MemberInfo.doSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location.reload();
			}						   
		});
	});
}

</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
<div id="nav" style="margin-bottom:12px">
	��ҳ  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=request.getParameter("SiteID")%>">��Ա����</a>  &raquo; ��������
</div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
      <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td>
      <z:init method="com.nswt.member.MemberInfo.init">
      <form id="InfoForm">
          <div class="forumbox"> <span class="fr">�� <span style=" font-family:'����'; color:#F60">*</span> ��ʾΪ������</span>
            <h4>�༭��������</h4>
            <ul class="tabs ">
              <li class="current"><a href="#action=profile&amp;typeid=2">��������</a></li>
              <li><a href="MemberInfoDetail.jsp?SiteID=<%=request.getParameter("SiteID")%>">��ϸ����</a></li>
              <li><a href="Logo.jsp?SiteID=<%=request.getParameter("SiteID")%>">�ϴ�ͷ��</a></li>
            </ul>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">������Ϣ</h5>
            <table width="80%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <tr>
                <td width="18%" align="right">�û�����</td>
                <td width="39%"><b>${UserName}</b><input type="hidden" id="UserName" name="UserName" value="${UserName}"/></td>
                <td width="43%" rowspan="5" align="left" valign="middle"><img id="LogoPic" src="${Logo}" width="100" height="100" style="border:#DFDFDF 1px solid"/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="Logo.jsp?SiteID=<%=request.getParameter("SiteID")%>">�ϴ�ͷ��</a></td>
              </tr>
               <tr>
                <td width="18%" align="right">��֤״̬��</td>
                <td width="39%"><b style="color:#999999">${StatusName}</b></td>
              </tr>
              <tr>
                <td width="18%" align="right">��Ա����</td>
                <td width="39%"><b>${MemberLevelName}</b></td>
              </tr>
              <tr>
                <td width="18%" align="right">ע��ʱ�䣺</td>
                <td width="39%"><b>${RegTime}</b></td>
                </tr>
              <tr>
                <td width="18%" align="right">�ϴε�¼��</td>
                <td width="39%"><b>${LastLoginTime}</b></td>
                </tr>
            </table>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">��չ��Ϣ</h5>
             <table width="99%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <tr>
                <td width="14%" align="right">
                <%if("Person".equals(User.getValue("Type"))){%>�ǳƣ�<%}else{%>��˾ȫ��<%}%>
                </td>
                <td width="86%"><input id="Name" name="Name" type="text" class="textInput" size="35" value="${Name}"/></td>
              </tr>
                <tr>
                <td align="right">E-mail��</td>
                <td><input id="Email" name="Email" type="text" class="textInput" size="35" value="${Email}"/></td>
              </tr>
              <tr>
                <td width="14%" align="right">�Ա�</td>
                <td width="86%">${Gender}</td>
              </tr>
              ${Columns}
            </table>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
            <div style=" padding-left:150px;">
              <button type="button" onClick="save()" name="tiajiao" value="true" class="submit">�ύ</button>
              &nbsp;
              <button type="reset" name="reset" value="false" class="button">�� ��</button>
            </div>
          </div>
        </form></z:init></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>