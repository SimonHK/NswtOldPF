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
	$("LogoFileName").src = $DW.Icon;
	$D.close();
}

function setAlias(){
	if($V("Alias") == ""){
	  $S("Alias",getSpell($V("Name"),true));
  }
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="����б�ҳģ��";
	diag.URL = "Site/TemplateExplorer.jsp?SiteID="+$V("ID")+"&Alias=default";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function browseFile(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="����ļ�";
	diag.URL = "Site/FileExplorer.jsp?SiteID="+$V("ID")+"&Alias=default";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<z:init method="com.nswt.cms.site.Site.initDialog">
	<form id="form2" method="post">
	<table width="740" cellpadding="2" cellspacing="0">
		<tr>
			<td width="135"></td>
			<td width="263" height="10"></td>
			<td width="328"></td>
		</tr>
		<tr>
			<td align="right">Ӧ��/ҵ������:</td>
			<td><input name="Name" type="text" class="input1" id="Name"
				onBlur="setAlias();" onChange="setAlias();" value="${Name}"
				size="40" verify="Ӧ������|NotNull" />
                <input name="ID" type="hidden"
				id="ID" value="${ID}" /></td>
			<td class="gray">Ӧ���ں�̨��ʾ������</td>
		</tr>
		<tr>
			<td align="right">��������:</td>
			<td><input name="Alias" type="text" class="input1" id="Alias"
				value="${Alias}" size="40"
				verify="Ӣ������|NotNull&&ֻ�����Сд��ĸ�����֡�Ӣ�ľ�š��»��߼�Ӣ��������|Regex=^[\w\.\_\-]+$" /></td>
			<td><span class="gray">���Ӧ���ļ���Ŀ¼���Ƽ�Maven��Ŀ��ΪartifactId������ֻʹ�����ֺ���ĸ</span></td>
		</tr>
		<tr>
			<td align="right">�ṹ������:</td>
			<td><input name="PackageName" type="text" class="input1"
					   id="PackageName" value="${AppVersion}" size="40" verify="������|NotNull"/></td>
			<td><span class="gray">�ṹ������</span></td>
		</tr>
		<tr>
			<td align="right">�汾��:</td>
			<td><input name="appversion" type="text" class="input1"
					   id="AppVersion" value="${AppVersion}" size="40" verify="�汾��|NotNull"/></td>
			<td><span class="gray">Ӧ�ð汾��</span></td>
		</tr>
		<tr>
			<td align="right">Ӧ������:</td>
			<td><input name="Info" type="text" class="input1" id="Info"
				value="${Info}" size="40" /></td>
			<td><span class="gray">Ӧ�õ�������ע��Ϣ</span></td>
		</tr>
		<tr>
			<td align="right">��ַ������</td>
			<td><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40" verify="URL|NotNull" /></td>
			<td><span class="gray">����߷���Ӧ��ʹ�õ�URL</span></td>
		</tr>
		<tr>
			<td align="right">����ģ�壺</td>
			<td><input name="AppTemplate" type="text" class="input1"
					   id="AppTemplate" value="${AppTemplate}" size="30" />
				<input
						type="button" onClick="browse('AppTemplate');"
						value="���..." /></td>
			<td><span class="gray">Ӧ�ù���ģ���б�</span></td>
		</tr>
		<tr>
			<td align="right">Դ���ַ��</td>
			<td><input name="scmurl" type="text" class="input1"
					   id="ScmUrl" value="${ScmUrl}" size="40" /></td>
			<td><span class="gray">SVN��GITԴ����汾�����ַ</span></td>
		</tr>
<%--		<tr>
			<td align="right">��ҳģ�壺</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" />
                <input
				type="button" onClick="browse('IndexTemplate');"
				value="���..." /></td>
			<td><span class="gray">Ӧ����ҳ��ʹ�õ�ģ���ļ�</span></td>
		</tr>--%>
		<tr>
			<td align="right">����ģ�壺</td>
			<td><input name="ListTemplate" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30" />
                <input
				type="button" onClick="browse('ListTemplate');"
				value="���..." /></td>
			<td><span class="gray">Ӧ����ҵ����ҳĬ��ʹ�õ�ģ��</span></td>
		</tr>
<%--		<tr>
			<td align="right">�ĵ�ģ�壺</td>
			<td><input name="DetailTemplate" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30" />
                <input
				type="button" onClick="browse('DetailTemplate');"
				value="���..." /></td>
			<td><span class="gray">Ӧ������֪ʶ�⼰����ϸҳ�鿴ʹ�õ�ģ���ļ�</span></td>
		</tr>
		<tr>
			<td align="right">�༭����ʽ��</td>
			<td><input id="EditorCss" name="EditorCss" type="text"
				class="input1" value="${EditorCss}" size="30" />
                <input
				type="button" onClick="browseFile('EditorCss');"
				value="���..." />              </td>
		  <td class="gray">ѡ�����ʽ�ļ����ᱻ���±༭�����룬ʹ���±༭ʱ��Ч�����ӽ���󷢲����е�Ч��</td>
		</tr>--%>
		<tr>
          <td height="35" align="right">����ҳ����չ����</td>
		  <td align="left"><input name="Prop1" type="text" class="input1"
						id="Prop1" value="${Prop1}" size="40"/></td>
		  <td align="left" class="gray">����ҳ�����չ��������shtml,jsp��Ĭ��shtml</td>
	  </tr>
	<%--	<tr>
		  <td height="35" align="right">��̬Ӧ��ͷ�����ã�</td>
		  <td><input type="text" class="input1" id="HeaderTemplate" value="${HeaderTemplate}" size="30" />
              <input name="button2"	type="button" onClick="browse('HeaderTemplate');" value="���..." />            </td>
		  <td class="gray">ָ����ģ���ļ���������ᱻ���õ���̬Ӧ�õ�&lt;head&gt;��&lt;/head&gt;֮��</td>
	  </tr>
		<tr>
		  <td height="35" align="right">��̬Ӧ�ö���ģ�壺</td>
		  <td><input name="TopTemplate" type="text" class="input1" id="TopTemplate" value="${TopTemplate}" size="30" />
              <input name="button" type="button" onClick="browse('TopTemplate');" value="���..." />            </td>
		  <td class="gray">ָ����ģ���ļ���������ᱻ���õ���̬Ӧ�õ�&lt;body&gt;֮��</td>
	  </tr>
		<tr>
		  <td height="35" align="right">��̬Ӧ�õײ�ģ�壺</td>
		  <td><input name="BottomTemplate" type="text" class="input1" id="BottomTemplate" value="${BottomTemplate}" size="30" />
              <input name="button2"	type="button" onClick="browse('BottomTemplate');" value="���..." />            </td>
		  <td class="gray">ָ����ģ���ļ���������ᱻ���õ���̬Ӧ�õ�&lt;/body&gt;֮��</td>
	  </tr>--%>
	</table>
	</form>
</z:init>
</body>
</html>
