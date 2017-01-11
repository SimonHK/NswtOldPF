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
function selectIcon(){
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "ѡ��ͼ��";
	diag.URL = "Platform/Icon.jsp";
	diag.onLoad = function(){
	};
	diag.show();
}

function afterSelectIcon(){
	$("Logo").src = $DW.Icon;
	$D.close();
}

function goBack(params){
	alert(params)
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="����б�ҳģ��";
	diag.URL = "Site/TemplateExplorer.jsp";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function setAlias(){
	if($V("Alias") == ""){
	  $S("Alias",getSpell($V("Name"),true));
  }
}

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 800;	
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = ok;
	diag.show();
}

function ok(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.site.Catalog.getPicSrc",dc,function(response){
		$("PicSrc").value = response.get("picSrc");
		$S("ImagePath",response.get("ImagePath"));
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function setRule(){
	var diag=new Dialog("diag3");
	diag.Width=500;
	diag.Height=230;
	diag.Title="������ϸҳ��������";
	diag.URL = "Site/CatalogDetailNameRuleDialog.jsp";
	diag.onLoad = function(){
		$DW.$S("DetailNameRule","/${catalogpath}/${document.id}.shtml");
	};
	diag.OKEvent=editDetailNameRule;
	diag.show();
}

function editDetailNameRule(){
	$S("DetailNameRule", $DW.$V("DetailNameRule"));
	$D.close();
}
function setListRule(){
   var diag=new Dialog("diag3");
   diag.Width=500;
   diag.Height=230;
   diag.Title="���ö�ý���ļ��洢����";
   diag.URL = "Site/CatalogListNameRuleDialog.jsp";
   diag.onLoad = function(){
     $DW.$S("ListNameRule","/${catalogpath}/");
	};
   diag.OKEvent=editListNameRule;
   diag.show();
}

function editListNameRule(){
   $S("ListNameRule", $DW.$V("ListNameRule"));
   $D.close();
}

function setURLRule(){
   var diag=new Dialog("diag3");
   diag.Width=500;
   diag.Height=230;
   diag.Title="�����ļ��洢����";
   diag.URL = "Site/CatalogURLNameRuleDialog.jsp";
   diag.onLoad = function(){
     $DW.$S("URL","/${catalogpath}/");
	};
   diag.OKEvent=editURLNameRule;
   diag.show();
}

function editURLNameRule(){
   $S("URL", $DW.$V("URL"));
   $D.close();
}

function changeKeywordFlag(){
	if($("KeywordFlag").checked){
		$("spanSetting").style.display="";
	}else{
		$("spanSetting").style.display="none";
	}
} 

function browseImage(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="���ͼƬ��";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = onImageSelected;
	diag.show();
}

function onImageSelected(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}
	else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}
function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.nswt.cms.site.CatalogExtend.getPicSrc",dc,function(response){
		$("CatalogImage").src = response.get("picSrc");
		$("ImagePath").value = response.get("ImagePath");
	})
}

function changeSingleFlag(FlagType){
	if(FlagType=="Y"){
		$("IndexTemplateTr").style.display="none";
		$("ListTemplateTr").style.display="none";
	}else{
		$("IndexTemplateTr").style.display="";
		$("ListTemplateTr").style.display="";
	}
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<z:init method="com.nswt.cms.site.Catalog.initDialog">
<body class="dialogBody">
	<form id="form2">
	<input type="hidden" id="ID" value="${ID}" />
	<input type="hidden" id="ParentID" value="${ParentID}" /> 
	<input type="hidden" id="SiteID" value="${SiteID}" />
	<input type="hidden" id="Level" value="${Level}" /> 
	<input type="hidden" id="Type" value="${Type}"> 
	<input type="hidden" id="ChildCount" value="${ChildCount}" /> 
	<input type="hidden" id="IsLeaf" value="${IsLeaf}" />
	<table width="740" align="center" cellpadding="4" cellspacing="0">
		<tr valign="top">
			<td align=center>
			<fieldset><legend> �������� </legend>
			<table width=100%>
				<tr>
					<td width="19%" height="35" align="right">�ϼ���㣺</td>
					<td width="81%" align="left"><input disabled name="ParentName"
						type="text" class="input1" id="ParentName" value="${ParentName}"
						size="30" />
				    <span class="gray">�ϼ����Ϊ������½���ťʱѡ�е���Ŀ�����</span></td>
			    </tr>
				<tr>
					<td width="19%" height="35" align="right">��Ŀ���ƣ�</td>
					<td align="left"><input name="Name" type="text"
						class="input1" id="Name" value="${Name}" size="30"
						onChange="setAlias();" onBlur="setAlias();" verify="����|NotNull" />
				    <span class="gray">��Ŀ�ں�̨��ʾ������</span></td>
			    </tr>
				<tr>
					<td height="35" align="right">��ĿĿ¼����</td>
					<td align="left"><input name="Alias" type="text"
						class="input1" id="Alias" value="${Alias}" size="30"
						verify="��ĿӢ����|NotNull&&�����볤����2-20λ֮����ַ�|Regex=^[\w]{2,20}$" />
				    <span class="gray">�ϼ����Ŀ¼�´�ű���Ŀ��̬���ļ�����Ŀ¼����</span></td>
			    </tr>
				<tr>
					<td height="35" align="right">��ĿĿ¼URL��</td>
					<td align="left"><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40"/> <input name="SetRuleButton" id="SetRuleButton" type="button" value="����" onclick="setURLRule();">
				    <span class="gray"><br>
				    ��Ŀ�ļ���������վ��Ŀ¼��λ�ã�<br>
				    �����д����http://��https://��ͷ�����ӵ�ַ����Ŀ�����ӵ�ָ��λ��</span></td>
			    </tr>
				<tr>
				<td height="35" align="right">����ͼƬ��</td>
					<td align="left">
					<a href="#;" onClick="browseImage()"><font id="PlayerName" color="#FF0000"><img src="../Images/addpicture.jpg" name="CatalogImage" width="80" border=0 id="CatalogImage">
					<input type="hidden" ID="ImagePath" value="${ImagePath}"></font><br>��������ͼ</a>
			      </td>
			    </tr>
			</table>
			</fieldset>
			<fieldset>
			<legend>ģ�弰���� </legend>
			<table width=100%>
              <tr id="IndexTemplateTr">
                <td width="19%" height="35" align="right">��Ŀ��ҳģ�壺</td>
                <td width="81%" align="left"><input name="IndexTemplate" type="text"
						class="input1" id="IndexTemplate" value="${IndexTemplate}"
						size="30" />
                  <input type="button" name="Submit" value="���..."
						onClick="browse('IndexTemplate')">
                  ${edit}<br>
                  <span class="gray">��Ŀ��ҳʹ�õ�ģ���ļ������Բ���д����ģ��ᾲ̬������ĿĿ¼�µ�index.shtml�ļ�</span></td>
              </tr>
              <tr id="ListTemplateTr">
                <td height="35" align="right">��Ŀ�б�ҳģ�壺</td>
                <td align="left"><input name="ListTemplate" type="text"
						class="input1" id="ListTemplate" value="${ListTemplate}" size="30">
                    <input name="button" type="button" onClick="browse('ListTemplate');" value="���..." />
                ${edit1}<span class="gray"><br>
                ��Ŀ�б�ҳʹ�õ�ģ���ļ��������������Ŀ��ҳģȨ�����б�ҳ�ĵ�һҳ�ᾲ̬��Ϊindex.shtml��<br>
                ���û��������Ŀ��ҳģ�壬���б�ҳ��һҳ�ᾲ̬��Ϊlist.html</span></td>
              </tr>
              <tr>
                <td height="35" align="right">�ĵ���ϸҳģ�壺</td>
                <td align="left"><input name="DetailTemplate" type="text"
						class="input1" id="DetailTemplate" value="${DetailTemplate}"
						size="30" />
                    <input name="button" type="button" 
						onClick="browse('DetailTemplate');" value="���..." />
                ${edit2}<span class="gray">��Ŀ�µ��ĵ���̬��ʱʹ�õ�ģ���ļ�</span></td>
              </tr>
			  <%if(Current.getInt("InitParams.Type")>=4&&Current.getInt("InitParams.Type")<=7){%>
              <tr>
                <td height="35" align="right">��ý���ļ��洢����</td>
                <td align="left"><input name="ListNameRule" type="text" class="input1"
						id="ListNameRule" value="${ListNameRule}" size="40" />
                    <input name="SetRuleButton" id="SetRuleButton" type="button" value="����" onclick="setListRule();">
                <span class="gray">��Ŀ�µ��ļ��洢����</span> </td>
              </tr>
			  <%}%>
              <tr>
                <td height="35" align="right">��ϸҳ��������</td>
                <td align="left"><input name="DetailNameRule" type="text" class="input1"
						id="DetailNameRule" value="${DetailNameRule}" size="40" />
                    <input name="SetRuleButton" id="SetRuleButton" type="button" value="����" onclick="setRule();">
                <span class="gray">��Ŀ�µ��ĵ���ϸҳ�洢����</span> </td>
              </tr>
              <tr>
                <td height="35" align="right">Rssģ�壺</td>
                <td align="left"><input name="RssTemplate" type="text"
						class="input1" id="RssTemplate" value="${RssTemplate}" size="30" />
                    <input name="button" type="button" 
						onClick="browse('RssTemplate');" value="���..." />
                <span class="gray">����RSS�ļ�ʹ�õ�ģ���ļ�</span></td>
              </tr>
              <tr>
                <td height="35" align="right">ģ�����ã�</td>
                <td align="left"><z:select>
                  <select name="select" id="Extend" style="width:150px;">
                    <option value="1">������Ŀ</option>
                    <option value="2">��������Ŀ������ͬ����</option>
                  </select>
                </z:select>
                <span class="gray">����ģ������Ӱ��ķ�Χ</span></td>
              </tr>
            </table>
			</fieldset>
			<fieldset>
			<legend>������ </legend>
			<table width=100%>
              <tr>
                <td width="19%" height="35" align="right">��������</td>
                <td width="81%" align="left"><z:select id="Workflow" style="width:150px;"> ${Workflow} </z:select>                
                  <span class="gray">����Ŀ���ĵ����ʹ�õĹ����������û�����ã������ֱ�ӷ���</span></td>
              </tr>
              <tr>
                <td height="35" align="right">���������ã�</td>
                <td align="left"><z:select id="WorkFlowExtend" style="width:150px;">
                  <select name="select2" id="WorkFlowExtend" style="width:150px;">
                    <option value="1">������Ŀ</option>
                    <option value="2">��������Ŀ������ͬ����</option>
                  </select>
                  </z:select><span class="gray">���ι���������Ӱ��ķ�Χ</span></td>
              </tr>
            </table>
			</fieldset>
			<fieldset>
			<legend>�������� </legend>
			<table width="100%" style="margin:3px auto">
				<tr>
					<td width="19%" height="25" align="right">�б�ҳ��ʾ�ĵ�����</td>
					<td width="81%" align="left"><input name="ListPageSize" type="text" class="input1" style="width:150px;" verify="Int"
						id="ListPageSize" value="${ListPageSize}" size="20" />
						<span class="gray">�б�ҳ��ʾ��������¼�����ģ������ָ��pagesize���ԣ�����ģ��Ϊ׼</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">�б�ҳ����ҳ����</td>
					<td align="left"><input name="ListPage" type="text" class="input1" style="width:150px;" verify="Int"
						id="ListPage" value="${ListPage}" size="20" />
						<span class="gray">�б�ҳ�����ʾ����ҳ��С�ڵ���0�����ֱ�ʾ�����ƣ���ʵ��ҳ��������</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">Meta�ؼ��֣�</td>
					<td align="left"><input name="Meta_Keywords" type="text" class="input1"
						id="Meta_Keywords" value="${Meta_Keywords}" size="40"/>
				    <span class="gray">������ģ����ʹ��${Catalog.Meta_Keywords}���Ա���SEO</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">Meta������</td>
					<td align="left"><textarea id="Meta_Description" name="Meta_Description" 
						style="width:220px;height:60px">${Meta_Description}</textarea>
				    <span class="gray">������ģ����ʹ��${Catalog.Meta_Description}���Ա���SEO</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">�Ƿ񿪷���ʾ��</td>
					<td align="left">${PublishFlag}&nbsp;&nbsp;<span class="gray">�����������ʾ������Ŀ������������Ŀ�б�͵�����</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">�Ƿ�ҳ��Ŀ��</td>
					<td align="left"><input class="inputRadio" name="SingleFlag" id="SingleFlag_0" value="Y" type="radio" ${SingleFlagY} onClick="changeSingleFlag('Y')"><label for="SingleFlag_0">��</label><input class="inputRadio" name="SingleFlag" id="SingleFlag_1" value="N" ${SingleFlagN} type="radio" onClick="changeSingleFlag('N')"><label for="SingleFlag_1">��</label>&nbsp;&nbsp;<span class="gray">����ǵ�ҳ��Ŀ����Ŀ�б�͵����е���Ŀ����ָ����Ŀ�µĵ�һƪ����</span></td>
			    </tr>
                <tr>
					<td height="25" align="right">�Ƿ�����Ͷ�壺</td>
					<td align="left">${AllowContribute}<span class="gray">&nbsp;&nbsp;�Ƿ�����ǰ̨��Աͨ��Ͷ�幦���������</span></td>
			    </tr>
				<tr>
				  <td height=35 align="right">����������ã�</td>
				  <td align="left"><input type="hidden" id="hKeywordFlag" value="${KeywordFlag}">
                      <label for="KeywordFlag">
                      <input name="KeywordFlag" type="checkbox" id="KeywordFlag" value="Y" onclick="changeKeywordFlag()">
                        �����������ܹ��� </label>
                    <span id="spanSetting" style="display:none">�������ࣺ
                        <input name="KeywordSetting"  id="KeywordSetting"  value="${KeywordSetting}" size="10"/>
                    </span> <span class="gray"><br>
                    ��������������ܹ����������˹������࣬�������±༭�������ʹ�á�������¡�-��������ء�����</span></td>
				</tr>
			</table>
			</fieldset>			</td>
	    </tr>
	</table>
	</form>
	</body>
	</z:init>
</html>
