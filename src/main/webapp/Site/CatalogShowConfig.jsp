<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=gb2312" />
<title>��ṹ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSave(){
	if(Verify.hasError()){
	  return;
    }
	var dc = Form.getData("form2");
	Server.sendRequest("com.nswt.cms.site.CatalogShowConfig.save",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
	});	
}
</script>
</head>
<body>
<z:init method="com.nswt.cms.site.CatalogShowConfig.init">
<table width="800" border="0" cellpadding="0">
	<tr valign="top">
		<td>
        <form id="form2">
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                <fieldset><legend>�����ı���</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">Ĭ�ϼ��ز㼶��</td>
                        <td width="30%"><input type="text" id="ArticleCatalogShowLevel" name="ArticleCatalogShowLevel" value="${ArticleCatalogShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">�ӽڵ��ӳټ��ط�ʽ��</td>
                        <td width="35%">
                        <z:select id="ArticleCatalogLoadType" value="${ArticleCatalogLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">����ȫ���ӽڵ�</option><option value="NextLevel">��������һ�㼶</option>
                        </select></z:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>ͼƬ����Ŀ��</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">Ĭ�ϼ��ز㼶��</td>
                        <td width="30%"><input type="text" id="ImageLibShowLevel" name="ImageLibShowLevel" value="${ImageLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">�ӽڵ��ӳټ��ط�ʽ��</td>
                        <td width="35%">
                        <z:select id="ImageLibLoadType" value="${ImageLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">����ȫ���ӽڵ�</option><option value="NextLevel">��������һ�㼶</option>
                        </select></z:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>��Ƶ��ṹ��</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">Ĭ�ϼ��ز㼶��</td>
                        <td width="30%"><input type="text" id="VideoLibShowLevel" name="VideoLibShowLevel" value="${VideoLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">�ӽڵ��ӳټ��ط�ʽ��</td>
                        <td width="35%">
                        <z:select id="VideoLibLoadType" value="${VideoLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">����ȫ���ӽڵ�</option><option value="NextLevel">��������һ�㼶</option>
                        </select></z:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>������ṹ��</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">Ĭ�ϼ��ز㼶��</td>
                        <td width="30%"><input type="text" id="AttachLibShowLevel" name="AttachLibShowLevel" value="${AttachLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">�ӽڵ��ӳټ��ط�ʽ��</td>
                        <td width="35%">
                        <z:select id="AttachLibLoadType" value="${AttachLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">����ȫ���ӽڵ�</option><option value="NextLevel">��������һ�㼶</option>
                        </select></z:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>��Ƶ��ṹ��</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">Ĭ�ϼ��ز㼶��</td>
                        <td width="30%"><input type="text" id="AudioLibShowLevel" name="AudioLibShowLevel" value="${AudioLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">�ӽڵ��ӳټ��ط�ʽ��</td>
                        <td width="35%">
                        <z:select id="AudioLibLoadType" value="${AudioLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">����ȫ���ӽڵ�</option><option value="NextLevel">��������һ�㼶</option>
                        </select></z:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <div style="width:100%; text-align:center; padding-top:10px;"><input type="button" value=" �� �� " onClick="doSave();"/></div>
                </td>
			</tr>
		</table>
        </form>
		</td>
	</tr>
</table>
</z:init>
</body>
</html>