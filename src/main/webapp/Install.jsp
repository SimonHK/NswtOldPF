<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.platform.*"%>
<%@page import="com.nswt.framework.license.*"%>
<%
if(Config.isInstalled){
	return;
}
if(StringUtil.isNotEmpty(request.getParameter("SQL"))){
	com.nswt.platform.pub.Install.generateSQL(request,response);
	return;
}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>��ʼ��<%=Config.getAppCode()%></title>
<link href="Include/Default.css" rel="stylesheet" type="text/css" />
<script src="Framework/Main.js"></script>
<script>
function onServerTypeChange(){
	var st = $V("ServerType");
	$("DB2Info").hide();
	$("trOracleMisc").hide();
	if(st=="ORACLE"){
		$S("Port","1521");
		$("trOracleMisc").show();
	}
	if(st=="DB2"){
		$S("Port","50000");
		$("DB2Info").show();
	}
	if(st.startWith("MSSQL")){
		$S("Port","1433");
	}
	if(st=="MYSQL"){
		$S("Port","3306");
	}
	if(st=="SYBASE"){
		$S("Port","5000");
	}
}

function execute(){
	if(Verify.hasError()){
		return;
	}
	Dialog.alert("���Ե�......");
	var dc = Form.getData("F1");
	Server.sendRequest("com.nswt.platform.pub.Install.execute",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			taskID = response.get("TaskID");
			var p = new Progress(taskID,"����ִ��ϵͳ��ʼ��...",500,150);
			p.show(function(){
				window.location = Server.ContextPath+"Login.jsp";
			});			
			p.Dialog.OKButton.hide();
			p.Dialog.CancelButton.hide();
			p.Dialog.CancelButton.onclick = function(){}
		}else	if(response.Status==2){
			Dialog.alert(response.Message,function(){
				window.location = Server.ContextPath+"Login.jsp";
			},600,100);
		}else{
			Dialog.alert(response.Message,null,600,100);
		}
	});
}

function onAutoCreateClick(){
	if(!$("AutoCreate").checked){
		$("trSQL").show();
	}else{
		$("trSQL").hide();
	}
}

function onJNDIPoolClick(){
	if($("isJNDIPool").checked){
		$("trJNDI").show();
		$("trAddress").hide();
		$("trPort").hide();
		$("trDBName").hide();
		$("trPassword").hide();
		$("trUserName").hide();
		
	}else{
		$("trJNDI").hide();
		$("trSQL").hide();
		$("trAddress").show();
		$("trPort").show();
		$("trDBName").show();
		$("trPassword").show();
		$("trUserName").show();
	}
}

function onImportDataClick(){
	if($NV("ImportData")=="0"){
		$("AutoCreate").checked = false;
		$("AutoCreate").disable();
	}else{
		$("AutoCreate").checked = true;
		$("AutoCreate").enable();
	}
}

Page.onLoad(function (){
	if(window.top.location != window.self.location){
		window.top.location = window.self.location;
	}
	onJNDIPoolClick();
	onAutoCreateClick();
	onServerTypeChange();
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
<!--
body{ background-color:#F6FAFF;_position:relative; color:#222;}
h4{ color:#226699}
a.zInputBtn {_position:relative;}
.red {color: #FF0000}
</style>
</head>
<body>
<form id="F1">
  <table width="100%" height="100%" border="0">
    <tr>
      <td valign="middle"><div style="margin:0 auto; background-color:#fff; padding:1px; border:1px solid #bbccdd; width:760px;">
          <table width="100%" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="40" align="center"><table width="100%" cellspacing="0" cellpadding="10" class="cellspacing" border="0" style="background: rgb(234, 236, 233) url(Platform/Images/form_titleBg.gif) no-repeat scroll right top;">
                  <tbody>
                    <tr>
                      <td width="25" align="right"><img src="Framework/Images/window.gif" name="_MessageIcon_Diag1" width="32" height="32" id="_MessageIcon_Diag1"></td>
                      <td align="left" style="line-height: 16px;"><h4>��ʼ��<%=Config.getAppCode()%></h4>
                        <div>��Ϊ<%=Config.getAppCode()%>�������ݿ��ʼ�����������ݿ��ʼ����ɺ��Զ�����ȫ�ļ���������ȫվ��</div></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td><table width="100%" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td colspan="4" height="10"></td>
                  </tr>
				  <%if(Runtime.getRuntime().maxMemory()<127*1024*1024){%>
                  <tr>
                    <td width="15%" height="30" align="right" ><em>�ر���ʾ��</em></td>
                    <td colspan="3"><span class="red">��ǰJVM����ڴ�����С���������-Xmx������128M����,�Ա����������һ��ʱ����ڴ������ֹͣ��Ӧ��״����</span></td>
                  </tr>
				  <%}%>
                  <tr>
                    <td width="15%" height="30" align="right" ><em>���ݿ����ͣ�</em></td>
                    <td width="35%"><div style="_position: relative;">
                        <z:select id="ServerType" onChange="onServerTypeChange();"> <span value="ORACLE">Oracle</span> <span value="DB2">DB2</span> <span value="MSSQL">SQL
                            Server 2005</span> <span value="MYSQL">Mysql</span> <span value="SYBASE">Sybase ASE 15</span> </z:select>
                      </div></td>
                    <td width="45%"><span id="DB2Info" style="display:none">ע�⣺<span class="red">DB2��Ĭ�ϱ�ռ�ҳ��СҪ����ڵ���16K����������ȷ������ṹ��</span></span></td>
                    <td width="5%">&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="30" align="right">&nbsp;</td>
                    <td><label>
                      <input name="ImportData" type="radio" id="ImportData1" onClick="onImportDataClick()" value="1" checked>
                      ��ʼ�����ݿ�</label>
                      <label>
                      <input name="ImportData" type="radio" id="ImportData2" onClick="onImportDataClick()" value="0">
                      ����������</label></td>
                    <td class="gray"><strong>��ʼ�����ݿ�</strong>���ʼ�����б��е����ݣ�<strong>����������</strong>��ᱣ�����ݿ���ԭ������</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4"><hr></td>
                  </tr>
                  <tr>
                    <td height="30" align="right"><em>��ṹ��</em></td>
                    <td><label>
                      <input name="AutoCreate" type="checkbox" id="AutoCreate" onClick="onAutoCreateClick()" value="1" checked>
                      �Զ��������ݿ��ṹ</label></td>
                    <td class="gray"><strong>�Զ��������ݿ��ṹ</strong>���Զ�������ṹ��������ݿ���ԭ������ͬ���ı������ɾ��ͬ����</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trSQL">
                    <td height="50" align="right">&nbsp;</td>
                    <td><p>�����ض�Ӧ���ݿ��SQL�ļ����ֶ�ִ�С�</p>
                      <p><a href="Install.jsp?SQL=1&Type=DB2"><strong>DB2</strong></a>�� <a href="Install.jsp?SQL=1&Type=ORACLE"><strong>Oracle</strong></a>�� <a href="Install.jsp?SQL=1&Type=MSSQL"><strong>SQLServer</strong></a>�� <a href="Install.jsp?SQL=1&Type=MYSQL"><strong>Mysql</strong></a>�� <a href="Install.jsp?SQL=1&Type=SYBASE"><strong>Sybase ASE 15</strong></a></p></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4"><hr></td>
                  </tr>
                  <tr>
                    <td height="30" align="right"><em>���ӳأ�</em></td>
                    <td><label>
                      <input name="isJNDIPool" type="checkbox" id="isJNDIPool" onClick="onJNDIPoolClick()" value="1">
                      ʹ��JNDI���ӳ�</label></td>
                    <td class="gray"><strong>ʹ��JNDI���ӳ�</strong>����м�������������</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trJNDI">
                    <td height="30" align="right"></td>
                    <td>JNDI���ƣ�
                      <input name="JNDIName" type="text" id="JNDIName" value="jdbc/nswtp" size=20  /></td>
                    <td class="gray">��ע��JNDI�����Ƿ���ǰ׺&quot;<strong>jdbc/</strong>&quot;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4"><hr></td>
                  </tr>
                  <tr style="display:none" id="trAddress">
                    <td height="30" align="right"><em>��������ַ��</em></td>
                    <td><input name="Address" type="text" id="Address" value="" size=20  verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">���ݿ������������IP��ַ</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trPort">
                    <td height="30" align="right"><em>�������˿ڣ�</em></td>
                    <td><input name="Port" type="text" id="Port" value="" size=20 verify="NotNull&&Int" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">�������ݿ�ʹ�õĶ˿�</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trUserName">
                    <td height="30" align="right"><em>�û�����</em></td>
                    <td><input name="UserName" type="text" id="UserName" value="" size=20 verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">�������ݿ�ʹ�õ��û���</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trPassword">
                    <td height="30" align="right"><em>���룺</em></td>
                    <td><input name="Password" type="password" id="Password" value="" size=20 verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">�������ݿ�ʹ�õ�����</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trDBName">
                    <td height="30" align="right"><em>���ݿ����ƣ�</em></td>
                    <td><input name="DBName" type="text" id="DBName" value="" size=20 verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">Oracle�´˴���ʵ������Mysql��SQLServer�������ݿⲻ���ڣ�����Զ��������ݿ�</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr id="trOracleMisc" style="display:none">
                    <td height="40" align="right"><em>Oracle����ѡ�</em></td>
                    <td><input type="checkbox" name="isLatin1Charset" value="1" id="isLatin1Charset">
                    <label for="isLatin1Charset">�����ַ���ΪUS7ASCII</label></td>
                    <td><span class="gray">���Oracle�ַ���ΪUS7ASCII����빴ѡ��ѡ�������ܻ�������롣</span></td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="40" align="right">&nbsp;</td>
                    <td><div style="_position:relative;">
                        <input type="button" name="Submit" value="" class="btnOK" style="border:0 none; background:url(Framework/Images/btn_OK.gif) no-repeat; width:70px; height:23px;" onClick="execute()">
                      </div></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>
