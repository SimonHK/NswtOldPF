<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script>
	function check(){
		if($V("TemplateFile")==""){
			alert("��ѡ�����ģ���ļ���");
			return false;
		}
		return true;
	}
	
	function closeDialog(){
	   Dialog.close();
	}
</script>
</head>
<body class="dialogBody">
<p>&nbsp;<iframe src="javascript:void(0);" name="targetFrame"
	width="0" height="0" frameborder="0"></iframe></p>
<form id="form2" enctype="multipart/form-data" target="targetFrame"
	action="TemplateImportSave.jsp?SiteID=<%=request.getParameter("SiteID")%>"
	method="POST" onSubmit="return check();">
<table width="100%" align="center" cellpadding="2" cellspacing="3">
	<tr>
		<td height="55" colspan="2" align="left">ѡ��ģ�壺 <input
			name="TemplateFile" type="file" id="TemplateFile" class="input1">
		<br>
		<p>֧��ZIP��HTML��HTM��JSP��PHP��ASP��ʽ�ļ�</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<div style="text-align:left;"><br>
		<p>����ļ�������<br>
		<label for="Overwrite"> <input name="DealType" id="Overwrite"
			type="radio" value="0"> ����</label> <label for="Rename"> <input
			name="DealType" id="Rename" type="radio" value="1" checked>
		������ </label> <label for="Note"> <input name="DealType" id="Note"
			type="radio" value="2"> ��ʾ</label></p>
		</div>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center"></td>
	</tr>
	<tr>
		<td colspan="2" align="center"></td>
	</tr>

	<tr>
		<td colspan="2" align="center"><input name="button"
			type="Submit" class="inputButton" id="button" value=" ȷ �� " /> &nbsp;
		<input name="button2" type="button" class="inputButton"
			onClick="Dialog.close();" value=" ȡ �� " /></td>
	</tr>
</table>
</form>
</body>
</html>
