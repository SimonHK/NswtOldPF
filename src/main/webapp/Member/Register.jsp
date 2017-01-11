<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.framework.Config"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Աע��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<%
String SiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(SiteID)||SiteID==null||SiteID=="null"){
	SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime Desc").executeOneValue()+"";
}

%>
<script type="text/javascript">

function TipMsg(){
	this.tips = [];
	this.addTips = function(ele,Str){
		var flag = false;
		for(var i=0;i<this.tips.length;i++){
			if(this.tips[i].substring(0,this.tips[i].indexOf(":"))==ele.id){
				flag = true;
			}
		}
		if(!flag){
			this.tips[this.tips.length] = ele.id+":"+ele.innerHTML;
		}
		ele.innerHTML = Str;
	}
	this.restore = function(ele){
		var flag = false;
		var index;
		for(var i=0;i<this.tips.length;i++){
			if(this.tips[i].substring(0,this.tips[i].indexOf(":"))==ele.id){
				index = i;
				flag = true;
			}
		}
		if(flag){
			var Str = this.tips[index].substring(this.tips[index].indexOf(":")+1);
			ele.innerHTML = Str;
		}
	}
}

var tipMsg = new TipMsg();
var checkUserFlag = true;

function checkUser(){
	var dc = new DataCollection();
	var userName = $V("UserName");
	if(userName.trim()==""){
		tipMsg.addTips($("UserNameNote"),"<font color='red'>�û�������Ϊ��!�û�������4-16���ַ�(����4��16)�� ����Ӣ��Сд�����֡��»��ߡ�</font>");
		return false;
	}
	var ulength = userName.trim().length;
	var rlength = userName.trim().replace(/[\W]/g,'').length;
	if(ulength>16||ulength<4){
		tipMsg.addTips($("UserNameNote"),"<font color='red'>���ȴ����û�������4-16���ַ�(����4��16)�� ����Ӣ��Сд�����֡��»��ߡ�</font>");
		return false;
	}
	if(rlength!=ulength){
		tipMsg.addTips($("UserNameNote"),"<font color='red'>����ʹ�����ĺ�������ţ�����Ӣ��Сд�����֡��»��ߡ�</font>");
		return false;
	}
	dc.add("UserName",userName);
	Server.sendRequest("com.nswt.member.Register.checkUser",dc,function(response){
		if(response.Status!=1){
			tipMsg.addTips($("UserNameNote"),"<font color='red'>"+response.Message+"</font>");
			checkUserFlag = false;
		}else{
		    tipMsg.addTips($("UserNameNote"),"<font color='green'>"+response.Message+"</font>");
			checkUserFlag = true;
		}
	});
	return true;
}	

function checkPassWord(){
	var passWord = $V("PassWord");
	if(passWord.trim()==""){
		tipMsg.addTips($("PassWordNote"),"<font color='red'>�û����벻��Ϊ�գ�������4-16λ���ֺ���ĸ�����</font>");
		return false;
	}else if(passWord.trim().length>16||passWord.trim().length<4){
		tipMsg.addTips($("PassWordNote"),"<font color='red'>�û����볤�Ȳ���ȷ��������4-16λ���ֺ���ĸ�����</font>");
		return false;
	}else{
		tipMsg.restore($("PassWordNote"));
	}
	return true
}

function checkConfirmPassword(){
	var passWord = $V("PassWord");
	var confirmPassword = $V("ConfirmPassword");
	if(passWord.trim()!=confirmPassword.trim()){
		tipMsg.addTips($("ConfirmPasswordNote"),"<font color='red'>������������벻һ�£�����������</font>");
		return false;
	}else{
		tipMsg.restore($("ConfirmPasswordNote"));
	}
	return true
}

function checkEmail(){
	var email = $V("Email");
	var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(email&&email.match(pattern)==null){
		tipMsg.addTips($("EmailNote"),"<font color='red'>��������ĸ�ʽ����ȷ����������ȷ�ĵ��������ַ</font>");
		return false;
	}else if(email.trim()==""){
		tipMsg.addTips($("EmailNote"),"<font color='red'>�������䲻��Ϊ�գ���������ȷ�ĵ��������ַ</font>");
		return false;	
	}else{
		tipMsg.restore($("EmailNote"));
		return true
	}
}

function checkName(){
	var Name = $V("Name");
	if(RealName.trim()==""){
		tipMsg.addTips($("NameNote"),"<font color='red'>��ʵ��������Ϊ��</font>");
		return false;	
	}else{
		tipMsg.restore($("NameNote"));
		return true
	}
}

function checkVerifyCode(){
	var verifyCode = $V("VerifyCode");
	if(verifyCode.trim()==""){
		tipMsg.addTips($("VerifyCodeNote"),"<font color='red'>��֤�벻��Ϊ��</font>");
		return false;	
	}else{
		return true;
	}
}

function doReg(){
	if(checkUser()&&checkPassWord()&&checkConfirmPassword()&&checkEmail()&&checkVerifyCode()&&checkUserFlag){
		if($("Agree").checked==true){
			if($V("SiteID")!=""&&$V("SiteID")!="null"){
				var dc = Form.getData("regform");
				Server.sendRequest("com.nswt.member.Register.register",dc,function(response){
					if(response.Status!=1){
						Dialog.alert(response.Message);
					}else{
						window.location = "MemberInfo.jsp?SiteID=<%=request.getParameter("SiteID")%>";
					}
				});
			}else{
				Dialog.alert("�����Ҳ���վ��");
				return;	
			}
		}else{
			Dialog.alert("δͬ�����Э�飬����ע��");
			return;
		}
	}else{
		return;
	}
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		doReg();
	}
}

function changeNameTitle(type){
	if(type==1){
		$("NameTitle").innerHTML = "��ʵ������";
	}else{
		$("NameTitle").innerHTML = "��˾���ƣ�";
	}
}
</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<z:init method="com.nswt.member.Register.init">
  <form id="regform" name="regform">
    <div class="wrap">
      <div id="menu" class="forumbox"> <span class="fl"> <a href="#;">ע��</a> <a href="Login.jsp?SiteID=<%=SiteID%>">��¼</a> </span> <span class="fr"><a href="#;">��ǩ</a> | <a href="#;">����</a> </span> </div>
      <div id="nav" style="margin-bottom:15px;"><a href="#">��ҳ</a> &raquo; ע��</div>
      <div class="forumbox"> <span class="fr">�� <span style=" font-family:'����'; color:#F60">*</span> ��ʾΪ������</span>
        <h4>ע��</h4>
        <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">������Ϣ</h5>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
          <tr>
            <td width="100" align="right">�˺����ͣ�</td>
            <td width="200">&nbsp;&nbsp;
              <label for="Type2">
                <input type="radio" name="Type" id="Type2" value="Person" checked onClick="changeNameTitle(1);">
                ����</label>
              <label for="Type3">
                <input type="radio" name="Type" id="Type3" value="Company" onClick="changeNameTitle(2);">
                ��ҵ</label></td>
            <td><input type="hidden" id="SiteID" name="SiteID" value="<%=SiteID%>"/></td>
          </tr>
          <tr>
            <td width="100" align="right">�û�����</td>
            <td width="200"><span style=" font-family:'����'; color:#F60">*</span>
              <input id="UserName" name="UserName" type="text" class="textInput" onBlur="checkUser();"/></td>
            <td><div id="UserNameNote" class="gray">4-16���ַ�(����4��16)��2-8�����֣� ����Ӣ��Сд�����֡��»��ߣ�����ĸ��ͷ��</div></td>
          </tr>
          <tr>
            <td align="right">���룺</td>
            <td><span style=" font-family:'����'; color:#F60">*</span>
              <input id="PassWord" name="PassWord" type="password" class="textInput" onBlur="checkPassWord();" /></td>
            <td><div id="PassWordNote" class="gray">��ĸ�д�Сд֮�֡�4��16λ������4��16��������Ӣ�ġ����֡�</div></td>
          </tr>
          <tr>
            <td align="right">ȷ�����룺</td>
            <td><span style=" font-family:'����'; color:#F60">*</span>
              <input id="ConfirmPassword" name="ConfirmPassword" type="password" class="textInput" onBlur="checkConfirmPassword();"/></td>
            <td><div id="ConfirmPasswordNote" class="gray">������ȷ��һ�����롣</div></td>
          </tr>
          <tr>
            <td align="right">E-mail��</td>
            <td><span style=" font-family:'����'; color:#F60">*</span>
              <input id="Email" name="Email" type="text" class="textInput" onBlur="checkEmail();"/></td>
            <td><div id="EmailNote" class="gray">�����������һ��������Ҫ;����������ϸ��д��</div></td>
          </tr>
          <tr>
            <td align="right">��֤�룺</td>
            <td><span style=" font-family:'����'; color:#F60">*</span>
              <input id="VerifyCode" name="VerifyCode" type="text" class="textInput" size="5" onBlur="checkVerifyCode();"/>
              <img src="../AuthCode.jsp" alt="���ˢ����֤��" name="yzm" height="20" align="middle" id="yzm" class="yanheng" style="cursor:pointer;" onClick="yzm.src='../AuthCode.jsp?'+new Date().getTime()" /></td>
            <td><div id="VerifyCodeNote" class="gray">Ϊ�˷�ֹ�����Զ�ע����Ϊ��������ͼƬ�ϵ���֤�롣</div></td>
          </tr>
        </table>
        <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">��չ��Ϣ</h5>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
          <tr>
            <td width="100" align="right" id="NameTitle">��ʵ������</td>
            <td width="200"><input id="Name" name="Name" type="text" class="textInput"/></td>
            <td><div id="NameNote" class="gray">3��������ĸ��2�����Ϻ��֡�</div></td>
          </tr>
          <tr>
            <td width="100" align="right">��ȫ���⣺</td>
            <td width="200"><select id="PWQuestion" name="PWQuestion" style="width:130px;">
              <option value="" selected>û��ȫ����</option>
              <option value="������������ʲô��">������������ʲô��</option>
              <option value="�����Сѧ��ʲô��">�����Сѧ��ʲô��</option>
              <option value="��ĸ��׽�ʲô���֣�">��ĸ��׽�ʲô���֣�</option>
              <option value="���ĸ�׽�ʲô���֣�">���ĸ�׽�ʲô���֣�</option>
              <option value="����ϲ����һ�׸�����">����ϲ����һ�׸�����</option>
            </select></td>
            <td><div class="gray">�����������һ�����</div></td>
          </tr>
          <tr>
            <td width="100" align="right">����𰸣�</td>
            <td width="200"><input type="text" id="PWAnswer" name="PWAnswer" value=""/></td>
            <td><div class="gray">���밲ȫ����Ĵ�</div></td>
          </tr>
          <tr>
            <td width="100" align="right">�Ա�</td>
            <td width="200"><label for="Gender1">
              <input type="radio" name="Gender" id="Gender1" value="M" checked>
              ��</label>
              <label for="Gender2">
                <input type="radio" name="Gender" id="Gender2" value="F">
                Ů</label></td>
            <td><div class="gray">&nbsp;</div></td>
          </tr>
          <tr>
            <td align="right">�ֻ���</td>
            <td><input id="Mobile" name="Mobile" type="text" class="textInput" /></td>
            <td><div class="gray">��ʽ�� +8613412345678 �� 13412345678��</div></td>
          </tr>
          <tr>
            <td align="right">��ַ��</td>
            <td><input id="Address" name="Address" type="text" class="textInput" style="width:180px;"/></td>
            <td><div class="gray">�밴��ͨ�ʼ��ռ��˸�ʽ��д��</div></td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
          <tr>
            <td width="100" align="right">�ʱࣺ</td>
            <td width="200"><input id="ZipCode" name="ZipCode" type="text" class="textInput" /></td>
            <td><div class="gray">&nbsp;</div></td>
          </tr>
          ${Columns}
        </table>
        <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
        <div style="text-align:center;">��<a href="#;">����Ķ����������</a>������ <span style=" font-family:'����'; color:#F60">*</span>
          <input type="checkbox" name="Agree" id="Agree" checked="checked" />
          &nbsp;���ѿ�����ͬ�⡶�������<br />
          <br />
          <button type="button" onClick="doReg();" name="tiajiao" value="true" class="btn">�ύ</button>
          &nbsp;
          <button type="reset" name="reset" value="false" class="btn">�� ��</button>
        </div>
      </div>
    </div>
  </form>
  <%@ include file="../Include/Bottom.jsp" %>
</z:init>
</body>
</html>
