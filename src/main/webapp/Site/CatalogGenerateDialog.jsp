<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script>
	var counter=1;
	function msg(){
		  var txt = "����ҳ���ʱ";
			setInterval(function(){document.getElementById("spanMessage").innerHTML=txt+counter;counter++}, 1000);
	}
	</script>
</head>

<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td width="112" height="10"></td>
		<td></td>
	</tr>
	<tr id="tr_ID">
		<td align="right">��ʾ��</td>
		<td width="833">�������ɵľ�̬ҳ�潫����ԭ��ҳ�棬ȷ���������о�̬ҳ����</td>
	</tr>
	<tr id="tr_Flag">
		<td align="right"></td>
		<td width="833">		
		<label for="DetailFlag"><input type="checkbox" Id="DetailFlag" value="Y" checked>
		������ϸҳ</label>
		<label for="ChildFlag"><input type="checkbox" Id="ChildFlag" value="Y" checked>
		��������Ŀ</label>
		</td>
	</tr>
	<tr>
		<td height="40" colspan="2" align="center">
		<div id="Message" style="text-align:center; display:none "><img
			src="../Framework/Images/loadingGreen15px.gif" width="15" height="15"><span
			id="spanMessage">����ҳ���ʱ0</span></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>
