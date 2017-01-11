<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function changeLoginStatus(){
	if($("LoginStatusButton").innerHTML.indexOf("��ʱ��ֹ")>=0){
		Dialog.confirm("��ʱ��ֹ��¼�󣬳�<%=UserList.ADMINISTRATOR%>֮��������û������ܵ�¼���Ƿ�ȷ�ϣ�",function(){
			changeLoginStatusServer();
		});
	}else{
		changeLoginStatusServer();
	}
}

function changeLoginStatusServer(){
	Server.sendRequest("com.nswt.platform.SysInfo.changeLoginStatus",null,function(response){
		Dialog.alert("���óɹ�");
		$("LoginStatusButton").$T("b")[0].innerHTML = response.get("LoginStatus");
	});
}

function forceExit(){
	Dialog.confirm("����ǰ�û�֮��������û�������ǿ��ע�����Ƿ�ȷ�ϣ�",function(){
		Server.sendRequest("com.nswt.platform.SysInfo.forceExit",null,function(response){
			if(response.Status==1){
				Dialog.alert("�����ɹ�!");
			}else{
				Dialog.alert("����ʧ��!");
			}
		});
	});
}

function exportDB(){
	window.location = "DBDownload.jsp";
}

function importDB(){
	var diag = new Dialog("DBUpload");
	diag.Title = "�������ݿ�";
	diag.URL = "Platform/DBUpload.jsp";
	diag.Width = 500;
	diag.Height = 100;
	diag.OKEvent = function(){
		$DW.doUpload();
	}
	diag.show();
}

function importUser(){
	Server.sendRequest("com.nswt.platform.SysInfo.inportUser",null,function(response){
		if(response.Status==1){
			Dialog.alert("�����ɹ�!");
		}else{
			Dialog.alert("����ʧ��!");
		}
	});
}

function synchronizationUser(){
	Server.sendRequest("com.nswt.platform.SysInfo.synchronizationUser",null,function(response){
		if(response.Status==1){
			Dialog.alert("�����ɹ�!");
		}else{
			Dialog.alert("����ʧ��!");
		}
	});
}

Page.onLoad(function(){
//ʹ��demoվ
	//var username="<%=User.getUserName()%>";
	//if(username!=="admin"){
	//	$("LoginStatusButton").disable();
	//	$("b1").disable();
	//	$("b2").disable();
	//	$("b3").disable();
	//}
});
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />ϵͳ��Ϣ</td>
      </tr>
      <tr>
      <td style="padding:0 8px 4px;">	  
	    <z:tbutton onClick="changeLoginStatus()" id="LoginStatusButton">
		<img src="../Icons/icon021a8.gif" width="20" height="20"/><%=SysInfo.getLoginStatus()%></z:tbutton>
        <z:tbutton id="b1" onClick="forceExit()"><img src="../Icons/icon021a5.gif" width="20" height="20"/>ǿ��ע�����лỰ</z:tbutton>
        <z:tbutton id="b2" onClick="exportDB()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>�������ݿ�</z:tbutton>
        <z:tbutton id="b3" onClick="importDB()"><img src="../Icons/icon005a6.gif" width="20" height="20"/>�������ݿ�</z:tbutton>
        <z:tbutton id="b4" onClick="importUser()"><img src="../Icons/icon005a6.gif" width="20" height="20"/>�����û�</z:tbutton>
        <z:tbutton id="b5" onClick="synchronizationUser()"><img src="../Icons/icon005a6.gif" width="20" height="20"/>ͬ���û�</z:tbutton>
		</td>
      </tr>
      <tr>
        <td style="padding:0"><table width="100%" border="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
          <tr>
            <td width="55%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="36%" height="30" align="right" type="Tree"><b>ϵͳ��Ϣ��&nbsp;</b></td>
                <td width="64%" type="Data" field="count"><b>ֵ</b></td>
              </tr>
              <z:datalist id="list1" method="com.nswt.platform.SysInfo.list1DataBind">
                <tr style1="backgroundcolor:#FEFEFE" style2="backgroundcolor:#F9FBFC">
                  <td align="right">${Name}��</td>
                  <td>${Value}</td>
                </tr>
              </z:datalist>
            </table></td>
            <td width="45%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="38%" height="30" align="right" type="Tree"><b>���ݿ�������&nbsp;</b></td>
                <td width="62%" type="Data" field="count"><b>ֵ</b></td>
              </tr>
              <z:datalist id="list2" method="com.nswt.platform.SysInfo.list2DataBind">
                <tr style1="backgroundcolor:#FEFEFE" style2="backgroundcolor:#F9FBFC">
                  <td align="right">${Name}��</td>
                  <td>${Value}</td>
                </tr>
              </z:datalist>
            </table>
              <%--<p>&nbsp;</p>--%>
              <%--<table width="100%" cellpadding="0" cellspacing="0" class="dataTable">--%>
                <%--<tr class="dataTableHead">--%>
                  <%--<td width="38%" height="30" align="right" type="Tree"><b>��Ȩ��Ϣ��&nbsp;</b></td>--%>
                  <%--<td width="62%" type="Data" field="count"><b>ֵ</b></td>--%>
                <%--</tr>--%>
                <%--<z:datalist id="list3" method="com.nswt.platform.SysInfo.list3DataBind">--%>
                  <%--<tr style1="backgroundcolor:#FEFEFE" style2="backgroundcolor:#F9FBFC">--%>
                    <%--<td align="right">${Name}��</td>--%>
                    <%--<td>${Value}</td>--%>
                  <%--</tr>--%>
                <%--</z:datalist>--%>
              <%--</table>--%>
              </td>
          </tr>
          
        </table>
          <br>
          <p>&nbsp;</p>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>