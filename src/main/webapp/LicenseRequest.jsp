<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import="com.nswt.framework.orm.*"%>
<%@page import="com.nswt.framework.controls.*"%>
<%@page import="com.nswt.framework.license.*"%>
<%@page import="com.nswt.schema.*"%>
<%@page import="com.nswt.platform.*"%>
<%
//if(session.getAttribute(com.nswtzas.Constant.UserSessionAttrName)!=null){
//	Login.ssoLogin(request,response,((UserData)session.getAttribute(com.nswtzas.Constant.UserSessionAttrName)).getUserName());
//}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ȡ���֤</title>
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<style>
body {
	background-color:#F6FAFF
}
h4 {
	color:#226699
}
</style>
<script src="Framework/Main.js"></script>
<script>
function getRequest(){
	var customer = $V("Customer");
	if(!customer){
		Dialog.alert("������д����Ȩ��λ/����");
		return;
	}
	var dc = new DataCollection();
	dc.add("Customer",$V("Customer"));
	Server.sendRequest("com.nswt.platform.License.getRequest",dc,function(response){
		Dialog.alert("��������ɹ�");
		$("Request").value = response.get("Request");
	});
}

function saveLicense(){
	var customer = $V("Response");
	if(!customer){
		Dialog.alert("������д���֤");
		return;
	}
	var dc = new DataCollection();
	dc.add("License",$V("Response"));
	Server.sendRequest("com.nswt.platform.License.saveLicense",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.location = "Login.jsp";
				}
			});
	});
}
</script>
</head>
<body>
<form id="form1" method="post">
  <table width="100%" height="100%">
    <tr>
      <td align="center" valign="middle"><div style="margin:0 auto; background-color:#fff; padding:1px; border:1px solid #d2dbe5; width:660px;">
          <table width="100%" border="0" cellspacing="0" cellpadding="3">
            <tr>
              <td colspan="2" height="30" align="center" style="background-color:#E6EEF6" ><h4>��ȡ���֤</h4></td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
              <td width="18%" align="right" style="height:30">����Ȩ��λ/���ˣ�</td>
              <td width="82%" align="left"><input name="Customer"
							type="text" id="Customer" size="50"
							value="<%=LicenseInfo.getName()%>">
                <input type="button"
							name="Submit" value="��������" onClick="getRequest()"></td>
            </tr>
            <tr>
              <td align="right" style="height:100px">���֤����</td>
              <td align="left"><textarea name="Request" wrap="virtual"
							id="Request" style="width:500px;height:100px"></textarea></td>
            </tr>
            <tr>
              <td align="right">���֤��</td>
              <td align="left"><textarea name="Response" id="Response"
							style="width:500px;height:100px"></textarea></td>
            </tr>
            <tr>
              <td colspan="2" align="center"><input
							type="button" name="Submit2" onClick="saveLicense()"
							value="�������֤"></td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
          </table>
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>
