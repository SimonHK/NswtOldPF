<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">

function change(event){
 $("ListNameRule").value=event.value;
}


</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

</head>
	<body class="dialogBody">
	<form id="form2"><input type="hidden" id="ID" value="${ID}" />
	<table width="97%"  cellpadding="4" cellspacing="0" style="margin:10px auto;">
		<tr valign="top">
			<td  height="30" align="left">��ý���ļ��洢����</td>
			<td align="left"><input  name="ListNameRule" id="ListNameRule"
						type="text" class="input1" value="${ListNameRule}"
						size="55" /></td>
		</tr>
		<tr>
			<td colspan="2">
				<fieldset><legend>��ѡ��ϸҳ�������� </legend>
				<table width="100%" style="margin:3px auto">
				 <tr valign="top">
					<td align="left"><input on type="radio" name="rule" id="rule_1" value="/${catalogpath}/" checked="checked" onclick="change(this)"/></td>
					<td height="30" align="left">��Ƶ��·�����ɣ� /${catalogpath}/</td>
	            </tr>
				<tr>
					<td align="left"><input type="radio" name="rule" id="rule_2" value="/${year}/" onclick="change(this)"/></td>	
					<td height="30" align="left">��/����ID.shtml���ɣ� /${year}/</td>
				</tr>
				<tr>
					<td align="left"><input type="radio" name="rule" id="rule_3" value="/${year}/${month}/" onclick="change(this)"/></td>	
					<td height="30" align="left">��/��/����ID.shtml���ɣ� /${year}/${month}/</td>
				</tr>
				<tr>
					<td align="left"><input type="radio" name="rule" id="rule_3" value="/${year}/${month}/${day}/" onclick="change(this)"/></td>	
					<td height="30" align="left">��/��/��/����ID.shtml���ɣ� /${year}/${month}/${day}/</td>
				</tr>
	               
				</table>
				</fieldset>
			</td>
		</tr>
	</table>
	</form>
	</body>
</html>
