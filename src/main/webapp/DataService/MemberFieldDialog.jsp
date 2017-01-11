<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Editor/fckeditor.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script type="text/javascript">

Page.onLoad(function(){
	if($V("hCode")!=""){
		$("Code").disabled = true;
	}
	changeInputType();
});

var input = "input";
var text = "textarea";
var selecter = "select";
var radio = "radio";
var checkbox = "checkbox";
var dateInput = "date";
var htmlInput = "html";
var timeInput = "time";

function changeInputType(){
	var inputType = $V("InputType");
	if(input==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").show();
		$("tr_MaxLength").show();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(text==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").show();
		$("tr_MaxLength").show();
		$("tr_Cols_Rows").show();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(selecter==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").show();
		$("tr_HTML").hide();
	}else if(radio==inputType){
		$("tr_IsMandatory").hide();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").show();
		$("tr_HTML").hide();
	}else if(checkbox==inputType){
		$("tr_IsMandatory").hide();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").show();
		$("tr_HTML").hide();
	}else if(dateInput==inputType||timeInput==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").hide();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(htmlInput==inputType){
		$("tr_IsMandatory").hide();
		$("tr_DefaultValue").hide();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").show();
	}
}

function setSpell(){
	if($V("Code") == ""){
	  $S("Code",getSpell($V("Name")));
  }
}

var editor;
function getHTML(){
	editor = FCKeditorAPI.GetInstance('HTML');
    return editor.GetXHTML(false);	
}

</script>
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.dataservice.MemberField.initDialog">
	<form id="form2">
	<table width="100%" height="100" border="0" align="center" cellpadding="4" cellspacing="" bordercolor="#DEDEDC" style="border-collapse:collapse;">
		<tr>
			<td width="34%" height="10"></td>
			<td width="66%"><input type="hidden" id="hCode" name="hCode" value="${Code}" style="width:140px;"/></td>
		</tr>
		<tr>
			<td height="29" align="right">������ʽ��</td>
			<td><z:select id="InputType" onChange="changeInputType()" style="width:120px;"> ${InputType}</z:select></td>
		</tr>
		<tr>
			<td align="right">�ֶ����ƣ�</td>
			<td><input name="Name" id="Name" type="text" verify="�ֶ�����|NotNull" value="${Name}" onBlur="setSpell();" style="width:140px;"/></td>
		</tr>
		<tr>
			<td align="right">�ֶδ��룺</td>
			<td><input name="Code" id="Code" type="text" verify="�ֶ�����|NotNull" value="${Code}" onBlur="setSpell();" style="width:140px;"/></td>
		</tr>
		<tr id="tr_IsMandatory">
			<td align="right">�Ƿ���</td>
			<td>${IsMandatory}</td>
		</tr>
		<tr id="tr_DefaultValue">
			<td align="right">Ĭ��ֵ��</td>
			<td><input name="DefaultValue" id="DefaultValue" type="text" value="${DefaultValue}" style="width:140px;"/></td>
		</tr>
		<tr id="tr_VerifyType">
			<td height="29" align="right">У�����ͣ�</td>
			<td><z:select id="VerifyType" style="width:120px;"> ${VerifyType}</z:select></td>
		</tr>
		<tr id="tr_MaxLength">
			<td align="right">����ַ�����</td>
			<td><input name="MaxLength" id="MaxLength" type="text" verify="����ַ���|NotNull&&Int" value="${MaxLength}" style="width:140px;"/></td>
		</tr>
		<tr id="tr_Cols_Rows">
			<td align="right">���ߣ�</td>
			<td>��ȣ�<input name="Cols" id="Cols" type="text" size="2" verify="���|NotNull&&Int" value="${Cols}" /> 
            �߶ȣ�<input name="Rows" id="Rows" type="text" size="2" verify="�߶�|NotNull&&Int" value="${Rows}" /></td>
		</tr>
		<tr id="tr_ListOption" style="display:none">
			<td height="83" align="right">�б�ѡ�<br>
			<br>
			<font color="red">(ÿ��Ϊһ<br>
			���б���)</font></td>
			<td><textarea id="ListOption" name="ListOption" cols="43" rows="5" verify="�б�ѡ��|NotNull">${ListOption}</textarea></td>
		</tr>
        <tr id="tr_HTML" style="display:none">
			<td height="83" align="right">HTML���ݣ�</td>
			<td>
            <div  id="Toolbar" style="height:26px; width:95%"></div>
            <textarea id="_HTML" name="_HTML" style=" display:none;">${HTML}</textarea>
            <script type="text/javascript">
				var sBasePath = "../Editor/" ;
				var oFCKeditor = new FCKeditor( 'HTML' ) ;
				oFCKeditor.BasePath	= sBasePath ;
				oFCKeditor.ToolbarSet="Basic"
				oFCKeditor.Width  = '95%';
				oFCKeditor.Height = 120;
				oFCKeditor.Config['EditorAreaCSS'] = '${CssPath}';
				oFCKeditor.Config[ 'ToolbarLocation' ] = 'Out:Toolbar' ;
				oFCKeditor.Value = $V("_HTML");
				oFCKeditor.Create();
			</script>   
            </td>
		</tr>
	</table>
	</form>
    </z:init>
</body>
</html>
