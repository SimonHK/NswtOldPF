<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.framework.Config"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ƹ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<script type="text/javascript">

function save(){
    if(Verify.hasError()){
		return;		     
    }
    if(!$V("Picture")){
    	Dialog.alert("���ϴ�ͼƬ���ٱ��棡",function(){
    		var url = window.location+'';
			window.location = url.substring(0,url.lastIndexOf('#'))+'#'+'PicturePath';
    	});
    	return;
    }
	var dc = Form.getData($F("myform"));
	Server.sendRequest("com.nswt.cms.dataservice.Apply.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location = "http://www.nswt.com";
			}
		});
	});
}

function doUpload(){
	if(!$V("PictureFile")){
		Dialog.alert("�������ͼƬ");
		return;
	}
	$F("myform").method="POST";
	$F("myform").action="./UploadSave.jsp";
	$F("myform").submit();
}

function afterSave(returnStr,path,picturePath){
	if(returnStr){
		$("PicturePath").src = picturePath;
		$S("Picture",path);
	}else{
		alert("ͼƬ�ϴ�ʧ�ܣ��������ϴ���");
	}
}
</script>

</head>
<body>
<%@ include file="../Include/Head.jsp" %>
<z:init method="com.nswt.cms.dataservice.Apply.init">
<div style="display:none"><iframe name="formTarget"  src="javascript:void(0)"></iframe></div>
<div class="wrap">
 
  <form id="myform" target="formTarget" enctype="multipart/form-data">
	<div class="forumbox">
	  <span class="fr">�� <span style=" font-family:'����'; color:#F60">*</span> ��ʾΪ������</span><h4>��Ƹ</h4>
           	  <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">������Ϣ</h5>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="forumTable">
  		<tr>
  		<td width="100" align="right">ӦƸ��λ��</td>
			<td width="330"><input
				name="WillPosition" type="text" class="text" id="WillPosition"
				 value="${Position}" verify="NotNull" /></td>
			
		</tr>
  <tr>
			<td width="100" align="right">������</td>
			<td width="330"><input name="Name"
				type="text" class="text" id="Name" verify="NotNull" /></td>
			
			
			<td width="300" colspan="3" rowspan="10" align="center" valign="top" class="edit_content_text"
				style="text-align: center;vertical-align: middle;"><div style="background-color:#f9f9f9; border:1px solid #ddd; padding:20px;"><img
				src="../Images/nophoto.jpg" width="150" height="150" id="PicturePath"
				style="border-width:0px;border: 1px solid #999; margin-top: 2px; margin-bottom: 3px;" /><br />
			<span class="STYLE8" style="color: #FF3300">���ϴ�������Ƭ</span><br>
			<input name="PictureFile" type="file" id="PictureFile" tabIndex="-1"/><input
				type="button" value="�ϴ�" onClick="doUpload();" tabIndex="-1"/> <br>
		  <input type="hidden" id="Picture" name="Picture" value=""/></div></td>
		</tr>

		<tr>
			<td width="100" align="right">�Ա�</td>
			<td width="330"> ${Gender}</td>
		</tr>
		<tr>
			<td width="100" align="right">�������ڣ�</td>
			<td width="330"><input
				name="BirthDate" type="text" class="text" id="BirthDate"
				style="width:80px;" value="" ztype="Date"
				verify="NotNull&&Date"/></td>
		</tr>
		<tr>
			<td width="100" align="right">���֤�ţ�</td>
			<td width="330"><input
				name="CertNumber" type="text" class="text" id="CertNumber"
				style="width:130px;" value=""
				verify="���֤���벻��ȷ!|NotNull" maxlength=18/></td>
		</tr>
		<tr>
			<td width="100" align="right">���壺</td>
			<td width="330"><z:select
				id="Ethnicity" name="Ethnicity" style="width:80px;" className="text"
				verify="NotNull"> ${Ethnicity} </z:select></td>
		</tr>
		<tr>
			<td width="100" align="right">���᣺</td>
			<td width="330"><z:select 
				id="NativePlace" name="NativePlace" className="text" 
				style="width:120px;" verify="NotNull">${NativePlace}</z:select></td>
		</tr>
		<tr>
			<td width="100" align="right">������ò��</td>
			<td width="330"><z:select
				id="Political" name="Political" style="width:180px;" className="text"
				verify="NotNull"> ${Political} </z:select></td>
		</tr>
		<tr>
			<td width="100" align="right">���ѧ����</td>
			<td width="330"><z:select
				id="EduLevel" name="EduLevel" style="width:180px;" className="text" verify="NotNull">${EduLevel}</z:select>			</td>
		</tr>
		<tr>
			<td width="100" align="right">�ֻ���</td>
			<td width="330"><input name="Mobile"
				type="text" class="text" id="Mobile" style="width:80px;" value=""
				maxlength=11 verify="�ֻ����벻��ȷ,ӦΪ11λ����|Regex=1\d{10}&&NotNull" /></td>
		</tr>
		<tr>
			<td width="100" align="right">�̶��绰��</td>
			<td width="330"><input name="Phone"
				type="text" class="text" id="Phone" style="width:85px;" value="" maxlength=12
				/> <span style="color:#F00">����010-62121234</span></td>
		</tr>
		<tr>
			<td width="100" align="right">��ϵ��ַ��</td>
			<td width="330"><input name="Address"
				type="text" class="text" id="Address" style="width:300px;" value=""
				maxlength="25" verify="NotNull" /></td>
		</tr>
		<tr>
			<td width="100" align="right">�������䣺</td>
			<td width="330"><input name="Email" type="text"
				class="text" id="Email" style="width:200px;" value="" maxlength="150"
				verify="NotNull&&Email" /></td>
			<td width="100" align="left" colspan="3">�ʱࣺ<span class="edit_content_text">
			  <input name="Postcode" type="text"
				class="text" id="Postcode" style="width:45px;" value="" maxlength=6
				verify="�ʱ಻��ȷ,ӦΪ6λ����|Regex=\d{6}&&NotNull" />
			</span></td>
		</tr>
		<tr>
			<td width="100" align="right">��ҵԺУ��</td>
			<td width="330"><input
				name="University" type="text" class="text" id="University"
				style="width:150px;" verify="NotNull"/></td>
			<td width="100" align="left" colspan="3">רҵ��<span class="edit_content_text">
			  <input
				name="Speacility" type="text" class="text" id="Speacility"
				value="" maxlength="25" verify="NotNull" />
			</span></td>
		</tr>
		<tr>
			<td width="100" align="right">�������ڵأ�</td>
			<td class="edit_content_text" colspan="3"><input
				name="RegisteredPlace" type="text" class="text" id="RegisteredPlace"
				style="width:300px;" value=""/></td>
		</tr>
		<tr>
			<td width="100" align="right">�ʸ���֤��</td>
			<td class="edit_content_text" colspan="5"><input
				name="Authentification" type="text" class="text"
				id="Authentification" style="width:300px;" verify="Length<200"
				value="" /></td>
		</tr>
		<tr>
			<td width="100" align="right">���˼�飺</td>
			<td colspan="5" class="edit_content_text"><textarea
				name="PersonIntro" rows="10" cols="20" id="PersonIntro"
				class="textarea" style="height:180px;width:500px;"
				verify="NotNull&&Length<500"></textarea> <span
				style="color: #ff6600">(1000����)</span></td>
		</tr>
		<tr>
			<td width="100" align="right" style="height: 55px;">
			�������</td>
			<td class="edit_content_text" colspan="5" style="height: 55px"><span
				class="edit_content_text" style="height: 55px"> <textarea
				name="Honour" rows="4" cols="20" id="Honour" class="textarea"
				style="height:180px;width:500px;" verify="Length<500"></textarea>
			</span><span style="color: #ff6600">(1000����)</span></td>
		</tr>
		<tr>
			<td width="100" align="right" style="height: 55px;">
			����������</td>
			<td class="edit_content_text" colspan="5" style="height: 55px">
			<textarea name="SHLPracticeExperience" rows="4" cols="20"
				id="SHLPracticeExperience" class="textarea"
				style="height:180px;width:500px;" verify="Length<500"></textarea>
			<span style="color: #ff6600">(1500����)</span></td>
		</tr>
		<tr>
			<td  width="100"  align="middle">&nbsp;</td>
			<td colspan="5" ><input name="buttonsubmit"
				type="button" value="�� ��" onClick="save();"></td>
		</tr>
</table>

</form>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</z:init>
</body>
</html>