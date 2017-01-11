<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��Ŀ</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	var type = $V("Type");
	var ctype ="article";
	if(type=="1"){
		ctype ="article";
	}else if(type=="4"){
		ctype ="image";
	}else if(type=="5"){
		ctype ="video";
	}else if(type=="6"){
		ctype ="audio";
	}else if(type=="7"){
		ctype ="attach";
	}
	if($V("InnerCode")){
		$("BtnSave").setAttribute("priv",ctype+"_manage");
		$("BtnAdd").setAttribute("priv",ctype+"_manage");
		$("BtnDel").setAttribute("priv",ctype+"_manage");
		$("BtnMove").setAttribute("priv",ctype+"_manage");
		$("BtnBatchAdd").setAttribute("priv",ctype+"_manage");
		$("BtnExportStructure").setAttribute("priv",ctype+"_manage");
		$("BtnClean").setAttribute("priv",ctype+"_manage");
		$("BtnPublish").setAttribute("priv",ctype+"_manage");
		Application.setAllPriv(ctype,$V("InnerCode"));
	}else{
		Application.setAllPriv("site",Application.CurrentSite);
	}
	if($V("hKeywordFlag") == "Y"){
		$("KeywordFlag").checked = true;
		$("spanSetting").style.display="";
	}
});

//�������ǰҳ��ʱ�������Ҽ��˵�
var iFrame =window.parent;
Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}	
});
var topFrame = window.parent;
function add(){
	if($NV('SingleFlag')=='Y'){
		Dialog.alert("��ҳ��Ŀ���ܴ����¼���Ŀ!");
		return;
	}
	topFrame.add();	
}

function publish(){
	topFrame.publish();	
}

function publishIndex(){
	topFrame.publishIndex();	
}

function del(){
	topFrame.del();
}

function refreshTree(){
	window.location.reload();
}

function move(){
	topFrame.move();
}

function edit(param){
	topFrame.edit(param);	
}

function batchAdd(){
	if($NV('SingleFlag')=='Y'){
		Dialog.alert("��ҳ��Ŀ���ܴ����¼���Ŀ!");
		return;
	}
	topFrame.batchAdd();
}

function batchDelete(){
	topFrame.batchDelete();
}

function copyConfig(){
	topFrame.copyConfig();
}
function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="����б�ҳģ��";
	diag.URL = "Site/TemplateExplorer.jsp";
	goBack = function(params){
		$S(ele,params);
		$(ele+"EditBtn").innerHTML = "<a href='javascript:void(0);' onclick=\"openEditor('"+params+"')\"><img src='../Platform/Images/icon_edit.gif' width='14' height='14'></a>";
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function save(){
	var dc = Form.getData("form1");
	dc.add("PublishFlag",$NV("PublishFlag"));
	dc.add("SingleFlag",$NV("SingleFlag"));
	dc.add("AllowContribute",$NV("AllowContribute"));
	dc.add("Extend",$V("Extend"));
	dc.add("WorkFlowExtend",$V("WorkFlowExtend"));
	dc.add("Workflow",$V("Workflow"));
	dc.add("ListPageSize",$V("ListPageSize"));
	if(!$("KeywordFlag").checked){
		dc.add("KeywordFlag","N");
	}
	
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.site.Catalog.save",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("����ɹ�",function(){
				topFrame.Tree.loadData("tree1",function(){
			    var node = topFrame.Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
					//���û��չ��������
					if(!node){
						var code = Cookie.get("DocList.LastCatalogCode");
						while(code&&code.length>6){
							code = code.substring(0,code.length-6);
							node = topFrame.Tree.getNode("tree1","innercode",code);
							if(node){
								topFrame.Tree.lazyLoad(node,function(){
									var img = topFrame.Tree.getLastBranchIcon(node);
									topFrame.Tree.changeIcon(img,node);
									node = topFrame.Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
									topFrame.Tree.selectNode(node,true);
									topFrame.Tree.scrollToNode(node);
								});
								break;
							}
						}
					}else{
						topFrame.Tree.selectNode(node,true);
						topFrame.Tree.scrollToNode(node);
					}
				});
			});
		}
	});
}
function openEditor(path){
	topFrame.openEditor(path);
}

function preview(){
	topFrame.preview();
}

function clean(){
	topFrame.clean();
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

function exportStructure(){
	var diag = new Dialog("Diag4");
	diag.Width = 500;
	diag.Height = 350;
	diag.Title ="������Ŀ�ṹ";
	diag.URL = "Site/CatalogExportStructure.jsp?Type="+$V("Type")+"&ID="+$V("ID");
	diag.ShowMessageRow = true;
	diag.Message = "�������ı��ɸ��ƺ���ʹ�á��������롱���ܵ��������վ�����Ŀ�¡�";
	diag.show();
	diag.OKButton.hide();
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
</head>
<body>
<z:init method="com.nswt.cms.site.Catalog.init">
	<div style="padding:2px;">
	<table width="100%" cellpadding='0' cellspacing='0'
		style="margin-bottom:4px;">
		<tr>
			<td><z:tbutton id="BtnSave" priv="article_manage"
				onClick="save('${ID}');">
				<img src="../Icons/icon002a4.gif" width="20" height="20" />����</z:tbutton> <z:tbutton
				id="BtnAdd" priv="article_manage" onClick="add();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />�½�����Ŀ</z:tbutton> <z:tbutton
				id="BtnDel" priv="article_manage" onClick="del();">
				<img src="../Icons/icon002a3.gif" width="20" height="20" />ɾ��</z:tbutton> <z:tbutton
				id="BtnMove" priv="article_manage" onClick="move();">
				<img src="../Icons/icon002a7.gif" width="20" height="20" />�ƶ�</z:tbutton> <z:tbutton
				id="BtnBatchAdd" priv="article_manage" onClick="batchAdd();">
				<img src="../Icons/icon002a8.gif" width="20" height="20" />�����½�</z:tbutton>
				<z:tbutton
				id="BtnExportStructure" priv="article_manage" onClick="exportStructure();">
				<img src="../Icons/icon002a11.gif" width="20" height="20" />������Ŀ�ṹ</z:tbutton>
				<z:tbutton
				id="BtnClean" priv="article_manage" onClick="clean();">
				<img src="../Icons/icon002a9.gif" width="20" height="20" />���</z:tbutton> <z:tbutton
				id="BtnPublish" priv="article_manage" onClick="publish();">
				<img src="../Icons/icon002a1.gif" />����</z:tbutton> </td>
		</tr>
	</table>
	<form id="form1">
	<table width="600" border="1" cellpadding="1" cellspacing="0"
		bordercolor="#eeeeee">
		
		<tr id="tr_ID">
			<td width="22%" height="25">ID��</td>
		  <td width="78%">${ID}
			<input name="ID" type="hidden" id="ID" value="${ID}" />
			<input name="SiteID" type="hidden" id="SiteID" value="${SiteID}" />
			<input name="Type" type="hidden" id="Type" value="${Type}" />
			<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" /> 
			&nbsp;<span class="gray">(�ڲ����룺${InnerCode})</span> </td>
		</tr>
		<tr>
			<td>��Ŀ���ƣ�</td>
		  <td><input name="Name" type="text" class="input1" id="Name"
				value="${Name}" size="30" verify="NotNull" />
		    <input name="BtnPreview" type="button" id="BtnPreview" value="Ԥ��" onclick="preview();">		 </td>
		</tr>
		<tr>
			<td>��ĿĿ¼����</td>
			<td><input name="Alias" type="text" class="input1" id="Alias"
				value="${Alias}" size="30"
				verify="��ĿӢ����|NotNull&&�����볤����2-20λ֮����ַ�|Regex=^[\w]{2,20}$"/></td>
		</tr>
		<tr>
			<td>��ĿĿ¼URL��</td>
			<td><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40"/>
				 <input name="SetRuleButton" id="SetRuleButton" type="button" value="����" onclick="setURLRule();">
				<a href="#" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'�ļ����λ�ã�����http://��https://��ͷ����Ŀ�����ӵ�ָ��λ��');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a>				</td>
		</tr>
		<tr>
          <td height="35" >����ͼƬ��</td>
		  <td align="left"><a href="#;" onClick="browseImage()"><font id="PlayerName" color="#FF0000"><img src="${ImageFullPath}" name="CatalogImage" width="80" border=0 id="CatalogImage">
		          <input type="hidden" ID="ImagePath" value="${ImagePath}">
		    </font><br>
	      ��������ͼ</a> </td>
	    </tr>
		<tr id="IndexTemplateTr" style="display:${IsDisplay}">
			<td>��Ŀ��ҳģ�壺</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('IndexTemplate')" /> <span id="IndexTemplateEditBtn">${edit}</span></td>
		</tr>
		<tr id="ListTemplateTr" style="display:${IsDisplay}">
			<td>�б�ҳģ�壺</td>
			<td><input name="ListTemplate" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30"/> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('ListTemplate')" /> <span id="ListTemplateEditBtn">${edit1}</span></td>
		</tr>

		<tr>
			<td>��ϸҳģ�壺</td>
			<td><input name="DetailTemplate" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30"/> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('DetailTemplate')" /> <span id="DetailTemplateEditBtn">${edit2}</span></td>
		</tr>
		<%if(Current.getInt("InitParams.Type")>=4&&Current.getInt("InitParams.Type")<=7){%>
              <tr>
                <td height="35">��ý���ļ��洢����</td>
                <td align="left"><input name="ListNameRule" type="text" class="input1"
						id="ListNameRule" value="${ListNameRule}" size="40" />
                    <input name="SetRuleButton" id="SetRuleButton" type="button" value="����" onclick="setListRule();">
                <span class="gray">��Ŀ�µ��ļ��洢����</span> </td>
              </tr>
			  <%}%>		<tr>
			<td>��ϸҳ��������</td>
			<td><input name="DetailNameRule" type="text" class="input1"
				id="DetailNameRule" value="${DetailNameRule}" size="40" />
				<input name="SetRuleButton" id="SetRuleButton" type="button" value="����" onclick="setRule();">				</td>
			
		</tr>
		<tr>
			<td>Rssģ�壺</td>
			<td><input name="SiteID" type="text" class="input1"
				id="RssTemplate" value="${RssTemplate}" size="30" /> <input
				type="button" name="Submit" value="ѡ��.."
				onClick="browse('RssTemplate')"> <span id="RssTemplateEditBtn">${edit3}</span></td>
		</tr>
		<tr>
			<td>ģ�����ã�</td>
			<td><z:select>
				<select id="Extend" style="width:150px;">
					<option value="1">������Ŀ</option>
					<option value="2">��������Ŀ������ͬ����</option>
				</select>
			</z:select></td>
		</tr>
		<tr>
			<td>��������</td>
			<td><z:select id="Workflow" style="width:150px;"> ${Workflow} </z:select>
				<a href="#" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'ѡ��������˹��������ɵ���ϵͳ����->�����������塱�˵��������á�');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a></td>
		</tr>
		<tr>
			<td>���������ã�</td>
			<td><z:select id="WorkFlowExtend" style="width:150px;">
				<select id="WorkFlowExtend" style="width:150px;">
					<option value="1">������Ŀ</option>
					<option value="2">��������Ŀ������ͬ����</option>
				</select>
			</z:select>  </td>
		</tr>
		<tr style="display:none">
			<td>�����ֶΣ�</td>
			<td><input name="OrderColumn" type="text" class="input1"
				id="OrderColumn" value="${OrderColumn}" size="30" /></td>
		</tr>
		<tr>
			<td>�б�ҳ��ʾ�ĵ�����</td>
			<td><input name="ListPageSize" type="text" class="input1" style="width:150px;" verify="Int"
				id="ListPageSize" value="${ListPageSize}" size="30" />
				<a href="javascript:void(0);" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'�б�ҳ��ʾ��������¼�����ģ������ָ��pagesize���ԣ�����ģ��Ϊ׼');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a></td>
		</tr>
		<tr>
			<td>�б�ҳ����ҳ����</td>
			<td><input name="ListPage" type="text" class="input1" style="width:150px;" verify="Int"
				id="ListPage" value="${ListPage}" size="30" />
				<a href="javascript:void(0);" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'�б�ҳ�����ʾ����ҳ��С�ڵ���0�����ֱ�ʾ�����ƣ���ʵ��ҳ��������');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a></td>
		</tr>
		<tr>
			<td height=25>����������ã�</td>
			<td>
				<input type="hidden" id="hKeywordFlag" value="${KeywordFlag}">
			  <label for="KeywordFlag">
				<input name="KeywordFlag" type="checkbox" id="KeywordFlag" value="Y">
			  �Զ���ȡ�ؼ���</label>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Զ��������ࣺ
              <input name="KeywordSetting"  id="KeywordSetting"  class="inputText" value="${KeywordSetting}" size="20"/></td>
		</tr>
		<tr>
		  <td height=25>&nbsp;</td>
		  <td class="gray">�����������Զ��Ÿ����Ķ���ʡ�ϵͳ�����·���ʱ����Ƿ����ֹ�����������£����û�У������ݹؼ��ּ���������¡����û�������Զ��������࣬��ֻ�ڱ���Ŀ�²���������£�����������Զ��������࣬����������Զ����������뱾��Ŀ������һ������ͬ����Ŀ�в���������¡�</td>
	    </tr>
		<tr>
			<td height=25>Meta�ؼ��֣�</td>
			<td><input name="Meta_Keywords" type="text" class="input1"
				id="Meta_Keywords" value="${Meta_Keywords}" size="30"/></td>
		</tr>
		<tr>
			<td height=25>Meta������</td>
			<td><textarea id="Meta_Description" name="Meta_Description" 
				style="width:250px;height:60px">${Meta_Description}</textarea></td>
		</tr>
		<tr>
			<td height=20>�Ƿ񿪷���ʾ��</td>
			<td>${PublishFlag}</td>
		</tr>
		<tr>
			<td height=20>�Ƿ�ҳ��Ŀ��</td>
			<td><input class="inputRadio" name="SingleFlag" id="SingleFlag_0" value="Y" type="radio" ${SingleFlagY} onClick="changeSingleFlag('Y')"><label for="SingleFlag_0">��</label><input class="inputRadio" name="SingleFlag" id="SingleFlag_1" value="N" ${SingleFlagN} type="radio" onClick="changeSingleFlag('N')"><label for="SingleFlag_1">��</label></td>
		</tr>
        <tr>
			<td height=20>�Ƿ�����Ͷ�壺</td>
			<td>${AllowContribute}</td>
		</tr>
	</table>
	</form>
	</div>
</z:init>
</body>
</html>
