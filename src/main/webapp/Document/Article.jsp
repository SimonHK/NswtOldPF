<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.cms.document.Article.init">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>���±༭��${Title}</title>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script src="../Framework/Main.js"></script>
	<script src="../Framework/Controls/StyleToolbar.js"></script>
	<script src="../Editor/fckeditor.js"></script>
	<style type="text/css">
<!--
html,body {
	overflow: auto !important;
	overflow: hidden;
	margin: 0;
	padding: 0;
}

body {
	text-align: left;
	font-size: 12px;
	color: #666666;
	margin: 0px;
	background: #F6F9FD;
}

.windowname {
	color: #000;
	padding: 4px 10px;
}

.themleft {
	background: url(Images/themtitleft.gif) no-repeat left top;
}

.themright {
	background: url(Images/themtitright.gif) no-repeat right top;
	cursor: pointer;
}

.thembg {
	background: #FFFFFF url(Images/themtitbg.gif) repeat-x left top;
	border-bottom: #b8c9d6 1px solid;
	border-right: #b8c9d6 1px solid;
}

#id_AdvanceTd { /*
background:#FFFFFF url(Images/thembg.gif) repeat-y center top;
*/
	
}

textarea,form,select,input,button,option,iframe {
	font-size: 12px;
	background-color: transparent;
}

#otherbtn a,#otherbtn a:visited {
	display: block;
	width: 85px;
	height: 22px;
	line-height: 22px;
	padding: 0;
	text-align: left;
	text-decoration: none;
	background: url(Images/btbg.gif) no-repeat center center;
}

#otherbtn img,#otherbtn img {
	margin: 0 10px 0 5px;
}

#otherbtn a:hover {
	background: url(Images/btbgo.gif) no-repeat center center;
}

.tbSwitch {
	height: 26px;
	position: absolute;
	background: url("Images/paggingbar.gif");
}

#wrapper {
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}

#pageBarDiv {
	position: fixed;
	_position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
}
.redCount {color: #FF0000}
-->
    </style>
	<script>
var CatalogID = '${CatalogID}';
var InnerCode = '${InnerCode}';
var ChannelID = '';
var TempArticleID = '';

var pages = ${Pages}; //ҳ��
var currentPage = 1;
var pageTitles; //ҳ���������
var contents; //ҳ����������
var PAGE_SPLIT ="<!--_NSWT_PAGE_BREAK_-->";
var TITLE_SPLIT ="|";
var editorMode = 0;

var isDirty = false; //�Ƿ��Ѿ��޸�

var editor;

var INFO_ATTACH_PIC = "(ͼ)";
//ĳЩOnclick�¼��Ĳ������ͣ��������޸ĵȲ������ж���
var ClickMethod="";
//У�������Ƿ�Ϊ��
var checkContentNull = true;

function onAttachPic(_oSelectElement){
	var sValue = $("DocTitle").value;
	if(_oSelectElement.checked){			
		if(sValue.indexOf(INFO_ATTACH_PIC) < 0){
			sValue += INFO_ATTACH_PIC;
		}
	}else{
		sValue = sValue.replace( INFO_ATTACH_PIC , "");
	}

	$("DocTitle").value = sValue;
}

function displayAdvance(_sAdvanceId, _elContolImg, _contract, _outspread){
	var oAdvanceTd = $(_sAdvanceId);
	if(oAdvanceTd == null){
		return;
	}
	var bDisplayed = (oAdvanceTd.style.display != "none");
	isIE?(oAdvanceTd.style.display = (bDisplayed?"none":"inline")):(oAdvanceTd.style.display = (bDisplayed?"none":"table-cell"))
	$(_elContolImg).src = (bDisplayed?_contract:_outspread);
	$(_elContolImg).title	= (bDisplayed?"չ��":"����");

}

function afterSelectUser(entryId,actionId){
	if(!$("NextStepUser")){
		var input = document.createElement("input");
		input.type = "hidden";
		input.id = "NextStepUser";
		$("form1").appendChild(input);
	}
	if($DW.getSelectUser()&&$DW.getSelectUser()[0]){
			$("NextStepUser").value = $DW.getSelectUser()[0];
	}	
	$D.close();
	save(entryId,actionId,false);
}

function checkDate(date,time){
	if(!date||!time){
	   return true;
	}
  // ��ȡ�����ַ���
  var arys = date.split("-");
  var aryt=time.split(":");
  // �õ�ϵͳʱ�䲢�ֽ�
    var now = new Date() ;
 	var year = now.getFullYear() ;
 	var month = now.getMonth() + 1 ;
 	var day = now.getDate();
 	var hour = now.getHours();
 	var min = now.getMinutes();
 	var second = now.getSeconds();
  // �ж�
  if(eval(arys[0]>year)){
        return true ;
    } else if(eval(arys[0]==year) && eval(arys[1]>month)) {
        return true ;
    } else if(eval(arys[0]==year) && eval(arys[1]==month) && eval(arys[2]>day) ) {
        return true ;
    } else if(eval(arys[0]==year) && eval(arys[1]==month) && eval(arys[2]==day)&&eval(aryt[0]>hour)){
        return true;
    } else if(eval(arys[0]==year) && eval(arys[1]==month) && eval(arys[2]==day)&&eval(aryt[0]==hour)&&eval(aryt[1]>min)){
        return true;
    } else if(eval(arys[0]==year) && eval(arys[1]==month) && eval(arys[2]==day)&&eval(aryt[0]==hour)&&eval(aryt[1]==min)&&eval(aryt[2]>second)){
        return true;
    } 
      return false;
}

function addNoteBeforeSave(args){//��������Article.BeforeSave��չ����
	var diag  = new Dialog("Diag");
	diag.Width = 420;
	diag.Height = 180;
	diag.Title ="������ע";
	diag.URL = "Document/ArticleNoteAddDialog.jsp";
	diag.onLoad = function(){
	};
	
  diag.OKEvent = function(){
  	$S("NoteContent",$DW.$V("Content"))
	$D.close();
  	save.apply(null,args);
   };
	diag.show();
}

function checkArchiveDate(date1,date2){
	if(!date1||!date2){
       return true;
	}
		
  	// ��ȡ�����ַ���
  	var arys1 = date1.split("-");
  	var arys2 = date2.split("-");

  	// �ж�
  	if(eval(arys1[0]>arys2[0])){
        return true ;
    } else if(eval(arys1[0]==arys2[0]) && eval(arys1[1]>arys2[1])) {
        return true ;
    } else if(eval(arys1[0]==arys2[0]) && eval(arys1[1]==arys2[1]) 
    	    && eval(arys1[2]>arys2[2])) {
        return true ;
    } else if(eval(arys1[0]==arys2[0]) && eval(arys1[1]==arys2[1]) 
    	    && eval(arys1[2]==arys2[2])){
        return true ;
	}
       return false;
   
}

function checkArchiveTime(time1,time2){
	if(time1||time2){
        return true;
	}
	var arys1=time1.split(":");
	var arys2=time2.split(":");

	if(eval(arys1[0]>arys2[0])){
        return true ;
    } else if(eval(arys1[0]==arys2[0]) && eval(arys1[1]>arys2[1])) {
        return true ;
    } else if(eval(arys1[0]==arys2[0]) && eval(arys1[1]==arys2[1]) 
    	    && eval(arys1[2]>arys2[2])) {
        return true ;
    }
       return false;
}


function save(entryId,actionId,selectUserFlag){
	var dc = Form.getData("form1");
	dc.add("entryId",entryId);
	dc.add("actionId",actionId);
	if(ClickMethod){
		dc.add("ClickMethod",ClickMethod);
	}
	ClickMethod="";
	if(Verify.hasError()){
		return;
	}
  
	if ($V("ArchiveDate") && !checkDate($V("ArchiveDate"),$V("ArchiveTime"))) {
  	 	Dialog.alert("�鵵ʱ�䲻��С�ڵ�ǰʱ�䣡");
  	 	return;
  	}

  	var date1 = $V("ArchiveDate");
  	var date2 = $V("PublishDate");
  	var time1 = $V("ArchiveTime");
  	var time2 = $V("PublishTime");

  	if (!checkArchiveDate(date1,date2)) {
	  	 Dialog.alert("�鵵���ڲ���С���������ڣ�");
	  	 return;
	}

	if (date1&&date2&&eval(date1==date2)&&!checkArchiveTime(time1,time2)){
         Dialog.alert("�鵵ʱ�䲻��С������ʱ�䣡");
         return ;
	}
	
	editor = FCKeditorAPI.GetInstance('Content');
  contents[currentPage-1]=editor.GetXHTML(false);
  
  //У���������ݷǿ�
  var content = contents.join();
  content = content.replace(/<br \/>/g,"");
  content = content.replace(/<p>\&nbsp;<\/p>/g,"");
  if(content.trim()=="" && checkContentNull){
  	Dialog.alert("�������ݲ���Ϊ��");
  	return;
  }
  
  content = contents.join(PAGE_SPLIT);
	dc.add("Content",content);
	
	pageTitles[currentPage-1]=$V("PageTitle");
	dc.add("PageTitles",pageTitles.join(TITLE_SPLIT));

	dc.add("Type",$V("NewsType"));
	dc.add("Attribute",$NV("Attribute"));
	
	if(!$("TopFlag").checked){
		dc.add("TopFlag",0);
	}
	if(!$("TemplateFlag").checked){
		dc.add("TemplateFlag",0);
	}
	if(!$("CommentFlag").checked){
		dc.add("CommentFlag",0);
	}
	
	dc.add("ClusterTarget",$V("ClusterTarget"));
	
  if(selectUserFlag){//��ѡ����һ�����û�
		var diag  = new Dialog("DiagSelectUser");
		diag.Width = 600;
		diag.Height = 400;
		diag.Title ="ѡ����һ��������";
		diag.URL = "Platform/UserListDialog.jsp?Type=checkbox";
		diag.OKEvent = function(){
			afterSelectUser(entryId,actionId);
		};
		diag.show();
		return;
	}
	
	//������չ���ڴ���չ�п��Ը�����ĿУ���ֶ�����ֵ�����߸��ݹ������ڵ���ǿ��Ҫ��д��ע
	<z:extend target="Article.BeforeSave" />
	
 	$S("NoteContent","");//�´ζ���֮ǰ��Ҫ�����ע
	
	Server.sendRequest("com.nswt.cms.document.Article.checkBadWord",dc,function(response){
		if(response.Status == 1){
			var isBadWordExist = window.confirm("�����������д�," + response.get("BadMsg") + "�Ƿ�ǿ�Ʊ������ţ�");
			if (isBadWordExist) {
				saveArticle("save",dc,response);
			}
		} else {
        saveArticle("save",dc,response);
		}
	});
}

function saveArticle(saveMethod,dc,response){
	Dialog.wait("���ڴ���...");
	Server.sendRequest("com.nswt.cms.document.Article."+saveMethod,dc,function(response){
	  Dialog.closeEx();
		if(response.Status==1){
			var st = response.get("SaveTime");
			$("SaveTime").innerHTML = st;
			$S("Method","UPDATE"); //�����״̬����Ϊ����
			isDirty = false;
			
			Dialog.alert("�����ɹ�");
			changeSaveOrPublishAlertInfo();

			var buttonDiv = response.get("buttonDiv");
			if(buttonDiv){
				$("buttonDiv").innerHTML=buttonDiv;
			}else{
				$("buttonDiv").innerHTML="";
			}
			opener.DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
	   	opener.DataGrid.loadData("dg1");
	   	if("Y"==response.get("ContentChanged")){
	   		  var savedContent = response.get("Content");
	   		  contents = savedContent.split(PAGE_SPLIT);
	   			FCKeditorAPI.GetInstance('Content').SetHTML(contents[currentPage-1],true);
	   	}
			$S("Keyword",response.get("Keyword"));
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function changeSaveOrPublishAlertInfo(){
	var pw = $E.getTopLevelWindow();
	var diag = pw.Dialog.getInstance("_DialogAlert"+(pw.Dialog.AlertNo-1));
	if(diag){
		diag.CancelButton.value = "�����༭";
		diag.addButton("Close","�رձ༭��",closeX);
		diag.addButton("New","�½�����",create);
	}
}

function autoSave(){
	var content = editor.GetXHTML(false);
	isDirty = (content==contents[currentPage-1]) ? isDirty:true;
	
	var pageTitle = $V("PageTitle");
	isDirty = (pageTitle==pageTitles[currentPage-1]) ? isDirty:true;
	
	if(!isDirty){
		return;
	}
	if($V("Title")==""){
		return;
	}
	
	var dc = Form.getData($F("form1"));
  contents[currentPage-1] = editor.GetXHTML(false);
	var content = contents.join(PAGE_SPLIT);;
	dc.add("Content",content);
	
	pageTitles[currentPage-1]=$V("PageTitle");
	dc.add("PageTitles",pageTitles.join(TITLE_SPLIT));
	
	dc.add("Type",$V("NewsType"));

	if(!$("TopFlag").checked){
		dc.add("TopFlag",0);
	}
	if(!$("TemplateFlag").checked){
		dc.add("TemplateFlag",0);
	}
	if(!$("CommentFlag").checked){
		dc.add("CommentFlag",0);
	}
	
	Server.sendRequest("com.nswt.cms.document.Article.checkBadWord",dc,function(response){
		if(response.Status == 1){
			var isBadWordExist = window.confirm("�����������д�," + response.get("BadMsg") + "�Ƿ񱣴�ݸ壿");
			if (isBadWordExist) {
				Server.sendRequest("com.nswt.cms.document.Article.saveVersion",dc,function(response){
					if(response.Status==1){
						var st = response.get("SaveTime");
						$("SaveTime").innerHTML = st;
					}
				});
			}
		} else {
			Server.sendRequest("com.nswt.cms.document.Article.saveVersion",dc,function(response){
				if(response.Status==1){
					var st = response.get("SaveTime");
					$("SaveTime").innerHTML = st;
				}
			});
		}
	});
}

function showTemplate(){
	if($("TemplateFlag").checked){
		$E.show("DivTemplate");
	} else {
		$E.hide("DivTemplate");
		$S("Template","");
	}
}


function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title ="���ģ��";
	diag.URL = "Site/TemplateExplorer.jsp?SiteID="+$V("SiteID");
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function changeDocType(){
    var ele = $("NewsType");
	if(ele.value=="4"){
		$("DivRedirect").style.display="";
		$("DivContent").style.display="none";
		checkContentNull = false;
	} else {
		$("DivRedirect").style.display="none";
		$("DivContent").style.display="";
		checkContentNull = true;
	}
}

function goback(){
	window.location = "ArticleList.jsp?CatalogID="+CatalogID;
}

//�������
function setReferName(ele){
   $S("ReferName",$(ele).value);
}

//���Ϊ
function saveAsDialog(){
  var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 370;
	diag.Title = "���Ϊ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=0";
	diag.onLoad = function(){
	};
	diag.OKEvent = doSaveAs;
	diag.show();
}

function doSaveAs(){
	var arrDest = $DW.$NV("CatalogID");
  	if(arrDest == null || arrDest.length==0){
		Dialog.alert("����ѡ��Ŀ��");
		return;
	}else{
		var otherCatalogID = arrDest[0];
		var catalogName = $DW.$("span_"+otherCatalogID).innerHTML;
		$S("CatalogID",otherCatalogID);
		$("CatalogName").innerHTML = catalogName;
		$D.close();
	}
}

//����
function copyDialog(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 410;
	diag.Title = "ѡ���Ƶ�����Ŀ";
	diag.URL = "Site/CatalogListDialog.jsp?Type=1&Action=copy&IDs="+$V("ReferTarget")+"&ReferType="+$V("ReferType");
	diag.onLoad = function(){
		$DW.$("_SiteSelector").disable();
	};
	diag.OKEvent = addCopy;
	diag.show();
}

function addCopy(){
  var arrDest = $DW.$NV("CatalogID");
  if(arrDest == null || arrDest.length==0){
		arrDest = [];//����ѡ��
	}
	
	//ȥ������ĿID
	var arr= [];
	for(var i = 0;i<arrDest.length;i++){
		if($V("CatalogID") != arrDest[i]){
			var otherCatalogID = arrDest[i];
			arr.push(otherCatalogID);
		}
	}
	$("spanReferCount").innerHTML = arr.length; 
	$S("ReferType",$DW.$NV("ReferType"));
	$S("ReferTarget",arr.join(","));
	$D.close();
}

function copyClusterDialog(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 340;
	diag.Title = "ѡ��Ҫ�ַ�������վȺ��Ŀ";
	diag.URL = "Document/ClusterCatalogDialog.jsp?CatalogInnerCode="+$V("InnerCode")+"&ID="+$V("ArticleID");
	diag.onLoad = function(){
		var clusterTarget = $V("ClusterTarget");
		if(clusterTarget){
			var arr = clusterTarget.split(",");
			for(var i=0;i<arr.length;i++){
				$DW.$("dg1").select(arr[i]);
			}
		}
	};
	diag.OKEvent = copyClusterSave;
	diag.ShowButtonRow = true;
	diag.show();
}

function copyClusterSave(){
	if($DW.Verify.hasError()){
		return;
	}
	$S("ClusterTarget",$DW.$("dg1").getSelectedValue());
	$("spanClusterCount").innerHTML = $DW.$("dg1").getSelectedValue().length; 
	$D.close();
}

function version(){
	var diag = new Dialog("Diag1");
	diag.Width = 680;
	diag.Height = 380;
	diag.Title = "�汾";
	diag.URL = "Document/ArticleVersionDialog.jsp?ArticleID="+$V("ArticleID");
	diag.onLoad = function(){
	};
	diag.show();
	$E.hide(diag.OKButton);
	diag.CancelButton.value = "ȷ ��";
}

function articleLog(){
	var diag = new Dialog("Diag1");
	diag.Width = 680;
	diag.Height = 350;
	diag.Title = "������ʷ";
	diag.URL = "Document/ArticleLogDialog.jsp?ArticleID="+$V("ArticleID");
	diag.onLoad = function(){
	};
	diag.show();
	$E.hide(diag.OKButton);
	diag.CancelButton.value = "ȷ ��";
}

function closeX(){
	window.close();
}

function note(){
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 350;
	diag.Title = "��ע";
	diag.URL = "Document/ArticleNoteDialog.jsp?ArticleID="+$V("ArticleID");
	diag.onLoad = function(){
	};
	diag.ShowButtonRow = false;
	diag.show();
}

function addRelaArticle(){
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 440;
	diag.Title = "�������";
	diag.URL = "Document/RelaDocDialog.jsp?RelativeArticle="+$V("RelativeArticle")+"&ArticleID="+$V("ArticleID");
	diag.onLoad = function(){
	};
	diag.OKEvent = addRelaArticleSave;
	diag.show();
}

function addRelaArticleSave(){
	$S("RelativeArticle",$DW.$V("ArticleIDs"));
	$D.close();
}

function addRecArticle(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 340;
	diag.Title = "�Ƽ�����";
	diag.URL = "Document/RecommendDocDialog.jsp?RecommendArticle="+$V("RecommendArticle")+"&ArticleID="+$V("ArticleID");
	diag.onLoad = function(){
		
	};
	diag.OKEvent = addRecArticleSave;
	diag.show();
}

function addRecArticleSave(){
	$S("RecommendArticle",$DW.$V("ArticleIDs"));
	$D.close();
}

function topublish(){
	if(isDirty){
		Dialog.alert("���ȱ���");	
	}else{
		var dc = new DataCollection();
		dc.add("ArticleID",$V("ArticleID"));
		Server.sendRequest("com.nswt.cms.document.Article.topublish",dc,function(response){
			if(response.Status==1){
				Dialog.alert("תΪ�����������ɹ�");
				opener.DataGrid.loadData("dg1");
			}else{
				Dialog.alert(response.Message);
			}
		});
	}
}

//��������
function publish(workflow){
	if(workflow){
	  var dc = new DataCollection();
		dc.add("ArticleID",$V("ArticleID"));
		Server.sendRequest("com.nswt.cms.document.Article.publish",dc,function(response){
			if(response.Status==1){
				Dialog.alert("���·����ɹ�");
			}else{
				Dialog.alert(response.Message);
			}
		});
	}else{
		 var dc = Form.getData("form1");
		 if(Verify.hasError()){
		 	 return;
	   }
	  
 	   if($V("ArchiveDate") && !checkDate($V("ArchiveDate"),$V("ArchiveTime"))) {
  	   Dialog.alert("�鵵ʱ�䲻��С�ڵ�ǰʱ�䣡");
  	   return;
     }
	   var date1 = $V("ArchiveDate");
	   var date2 = $V("PublishDate");
	   var time1 = $V("ArchiveTime");
	   var time2 = $V("PublishTime");

	   if (!checkArchiveDate(date1,date2)) {
		  	 Dialog.alert("�鵵���ڲ���С���������ڣ�");
		  	 return;
		 }

		 if (date1&&date2&&eval(date1==date2)&&!checkArchiveTime(time1,time2)){
	         Dialog.alert("�鵵ʱ�䲻��С������ʱ�䣡");
	         return ;
		 }
		
		editor = FCKeditorAPI.GetInstance('Content');
	 	contents[currentPage-1]=editor.GetXHTML(false);
		
		//У���������ݷǿ�
	  var content = contents.join();
	  content = content.replace(/<br \/>/g,"");
	  content = content.replace(/<p>\&nbsp;<\/p>/g,"");
	  if(content.trim()=="" && checkContentNull){
	  	Dialog.alert("�������ݲ���Ϊ��");
	  	return;
	  }
	  
	  content = contents.join(PAGE_SPLIT);
		
		pageTitles[currentPage-1]=$V("PageTitle");
	  dc.add("PageTitles",pageTitles.join(TITLE_SPLIT));
		
		dc.add("Content",content);
		
		dc.add("Type",$V("NewsType"));
		
		if(!$("TopFlag").checked){
			dc.add("TopFlag",0);
		}
		if(!$("TemplateFlag").checked){
			dc.add("TemplateFlag",0);
		}
		if(!$("CommentFlag").checked){
			dc.add("CommentFlag",0);
		}
		
		dc.add("ClusterTarget",$V("ClusterTarget"));
	  	
	  Server.sendRequest("com.nswt.cms.document.Article.checkBadWord",dc,function(response){
			if(response.Status == 1){
				var isBadWordExist = window.confirm("�����������д�," + response.get("BadMsg") + "�Ƿ񷢲����£�");
				if (isBadWordExist) {
          saveArticle("saveAndPublish",dc,response)
				}
			} else {
          saveArticle("saveAndPublish",dc,response)
			}
		});
	}
}


function applyStep(instanceID,nodeID){
	var dc = new DataCollection();
	dc.add("InstanceID",instanceID);
	dc.add("NodeID",nodeID);
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.nswt.cms.document.Article.applyStep",dc,function(response){
		var buttonDiv = response.get("buttonDiv");
		if(buttonDiv){
			$("buttonDiv").innerHTML=buttonDiv;
		}else{
			$("buttonDiv").innerHTML="";
		}
		Dialog.alert(response.Message);
	});
}

function htmlDecode(str) {
	return str.replace(/\&quot;/g,"\"").replace(/\&lt;/g,"<").replace(/\&gt;/g,">").replace(/\&nbsp;/g," ").replace(/\&amp;/g,"&");
}


//��ҳ����
function initPages(){
	var pageList = $("pageList");
	//contents[0] = htmlDecode(contents[0]);
	if(pages>1){//ֻ��һҳ������Ҫ��ҳ����
		$("trPageTitle").show();
	}
	for(var i = 1;i < pages;i++){
		var li = document.createElement("li");
		li.innerHTML = "<b>ҳ " + (i+1)+"</b>";
		li.id = "p" + (i+1);
		li.setAttribute("name","tabs");
		li.onclick = function(evt){
			changePage(evt);
		};	
		li.onmouseover = function(evt){
		  onOverPage(evt);
		};
	  	li.onmouseout = function(evt){
			onOutPage(evt);
		};
			
		var currentTab = $("p"+i);
		currentTab.insertAdjacentElement("afterEnd",li);
	}
	
}

function addPage(){
	pages = pages+1;
	
	for (var i=pages;i>currentPage+1;i--){
		var tab = $("p"+(i-1))
		tab.innerHTML = "<b>ҳ " + i+"</b>";
		tab.id = "p" + i;
	}
	
	var pageList = $("pageList");
	var li = document.createElement("li");
	li.innerHTML = "<b>ҳ " + (currentPage+1)+"</b>";
	li.id = "p" + (currentPage+1);
	li.setAttribute("name","tabs");
	li.onclick = function(evt){
		changePage(evt);
	};
	li.onmouseover = function(evt){
		onOverPage(evt);
	}
  	li.onmouseout = function(evt){
		onOutPage(evt);
	}
	
	var currentTab = $("p"+currentPage);
	currentTab.insertAdjacentElement("afterEnd",li);
	
	contents.insert(currentPage,"<p></p>");
	pageTitles.insert(currentPage,"");

  	setActivePage(currentPage+1);
	$("trPageTitle").show();
}

function setPageContent(pageNum, content){
	contents[pageNum-1] = content;
	if (currentPage == pageNum){
		editor.SetHTML(contents[pageNum-1]);
	}
}	

function setActivePage(page){
	var currentTab = $("p"+page);
	if(currentPage == page && currentTab.className == "current"){
		return;
	}
	
  editor = FCKeditorAPI.GetInstance('Content');
	var content = editor.GetXHTML(false);
	var pageTitle = $V("PageTitle");
	
	//����Ƿ��޸�
	isDirty = (content==contents[currentPage-1]) ? isDirty:true;
	isDirty = (pageTitle==pageTitles[currentPage-1]) ? isDirty:true;
	
	for (var i=0;i<pages;i++){
		var tab = $("p"+(i+1));
		if (tab.className=="current"){
			tab.className = "";
		  contents[currentPage-1] = content;
		  pageTitles[currentPage-1] = pageTitle;
			break;
		}
	}

	currentTab.className = "current";
	if(editorMode==0){
		var tmpContent = contents[page-1].trim();
		editor.SetHTML(tmpContent);
		
		var tmpTitle = pageTitles[page-1].trim();
		$S("PageTitle",tmpTitle);
	}else{
		editorMode = 0;
	}
	currentPage = page;
}

function deletePage(){
	if (pages==1){
		return;
	}
	var pageList = $("pageList");
	var currentTab = $("p"+currentPage);
	

	pageList.removeChild(currentTab);
	
  contents.splice(currentPage-1, 1);
  pageTitles.splice(currentPage-1, 1);
  
	for(var i=currentPage;i<pages;i++){
		var tab = $("p"+(i+1));
		tab.id = "p" + i;
		tab.innerHTML = "<b>ҳ " + i +"</b>";
	}
	
	pages = pages-1;
	if (currentPage>pages){
		 currentPage = pages;
	}
	setActivePage(currentPage);
	if(pages==1){//ֻ��һҳ������Ҫ��ҳ����
		$("trPageTitle").hide();
	}
}

function mergePage(){
	if (currentPage==1){
		return;
	}
	var pageList = $("pageList");
	var currentTab = $("p"+currentPage);

	pageList.removeChild(currentTab);
	
	contents[currentPage-1] = contents[currentPage-1] + editor.getXHTML(false);
  contents.splice(currentPage-1, 1);
  
	for(var i=currentPage;i<pages;i++){
		var tab = $("p"+(i+1));
		tab.id = "p" + i;
		tab.innerHTML = "<b>ҳ " + i +"</b>";
	}
	
	pages = pages-1;
	if (currentPage>pages){
		currentPage = pages;
	}
	setActivePage(currentPage);
}

function changePage(evt){
	var ele = getEvent(evt).srcElement;
	if(ele.nodeName == "B"){
		ele = ele.parentElement;
	}
  
	var arr = ele.parentElement.getElementsByTagName("li");
	for(var i=0,j=arr.length;i<j;i++){
		 if (arr[i].className=="current"){
	     arr[i].className = "";
	   }
	}
	ele.className="current";
	
	var eleId = ele.id;
	var pageNum = eleId.substr(1);
	pageNum = parseInt(pageNum);

	setActivePage(pageNum);
}

function onOverPage(evt){
	var ele = getEvent(evt).srcElement;
  	if(ele.nodeName == "B"){
  		ele = ele.parentElement;
  	}
	if(ele.className==""){
		ele.className="pagetabOver";
	}
}

function onOutPage(evt){
  	var ele = getEvent(evt).srcElement;
  	if(ele.nodeName == "B"){
  		ele = ele.parentElement;
  	}
	if(ele.className=="pagetabOver"){
	   ele.className="";
	}
}

function addBold(){
	var title = $V("Title");
	if(!title)return;
	if(title.indexOf("<b>")>-1||title.indexOf("</b>")>-1){
		title = title.replace("<b>","");
		title = title.replace("</b>","");
	}else{
		title = "<b>"+title+"</b>";
	}
	$S("Title",title);
}

//�ؼ���ѡ��
function keywordSelect(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "ѡ��ؼ���";
	diag.URL = "Document/ArticleKeywordDialog.jsp";
	diag.onLoad = function(){
		var keyword = $V("Keyword").replace("��"," ");
		keyword = keyword.replace(" "," ");
		$DW.setSelectedKeyword(keyword);
	};
	diag.OKEvent = addSelectKeyword;
	diag.show();
}

//��ӹؼ���
function addSelectKeyword(){
	var keywordSelected = $DW.getSelectedKeyword();
	var nowKeywordStr = $V("Keyword").replace("��"," ");
	nowKeywordStr = nowKeywordStr.replace(","," ");
	nowKeywordStr = nowKeywordStr.replace(" "," ");
	
	if(nowKeywordStr == ""){
		$S("Keyword",keywordSelected.join(" "));
	}else{
		nowKeyword = nowKeywordStr.split(" ");
		var newKeyword = nowKeywordStr.split(" ");
		for(var i = 0;i<keywordSelected.length;i++){
		    for(var j = 0;j<nowKeyword.length;j++){
			   if(nowKeyword[j] == keywordSelected[i]){
			      break;
			   }
			   if(j == nowKeyword.length-1){
			   	newKeyword.push(keywordSelected[i]);
			   }
			}
		}
		$S("Keyword",newKeyword.join(" "));
	}
	$D.close();
}

//�������Ĺؼ���
function addRecentKeyword(word){
	var nowKeywordStr = $V("Keyword").replace("��"," ");
	nowKeywordStr = nowKeywordStr.replace(","," ");
	nowKeywordStr = nowKeywordStr.replace(" "," ");
	if(nowKeywordStr == ""){
		$S("Keyword",word);
	}else{
		var nowKeyword = nowKeywordStr.split(" ");
		for(var i = 0;i < nowKeyword.length;i++){
			 if(nowKeyword[i] == word){
			      break;
			 }
			 if(i == nowKeyword.length-1){
			 	nowKeywordStr = nowKeywordStr+" "+word;
			 }
		}
		$S("Keyword",nowKeywordStr);
	}
}

function selectRefer(){
	var diag = new Dialog("DiagRefer");
	diag.Width = 840;
	diag.Height = 400;
	diag.Title = "ѡ��Tag";
	diag.URL = "Document/ReferDialog.jsp";
	diag.ShowButtonRow = false;
	diag.show();
}

function selectTag(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "ѡ��Tag";
	diag.URL = "Document/ArticleTagWordDialog.jsp";
	diag.onLoad = function(){
		var TagWord = $V("TagWord").replace("��"," ");
		TagWord = TagWord.replace(" "," ");
		$DW.setSelectedTagWord(TagWord);
	};
	diag.OKEvent = addSelectTagWord;
	diag.show();
}

//���TagWord
function addSelectTagWord(){
	var TagWordSelected = $DW.getSelectedTagWord();
	var nowTagWordStr = $V("TagWord").replace("��"," ");
	nowTagWordStr = nowTagWordStr.replace(","," ");
	nowTagWordStr = nowTagWordStr.replace(" "," ");
	
	if(nowTagWordStr == ""){
		$S("TagWord",TagWordSelected.join(" "));
	}else{
		nowTagWord = nowTagWordStr.split(" ");
		var newTagWord = nowTagWordStr.split(" ");
		for(var i = 0;i<TagWordSelected.length;i++){
		    for(var j = 0;j<nowTagWord.length;j++){
			   if(nowTagWord[j] == TagWordSelected[i]){
			      break;
			   }
			   if(j == nowTagWord.length-1){
			   	newTagWord.push(TagWordSelected[i]);
			   }
			}
		}
		$S("TagWord",newTagWord.join(" "));
	}
	$D.close();
}

//��������TagWord
function addRecentTagWord(TagWord){
	var nowTagWordStr = $V("TagWord").replace("��"," ");
	nowTagWordStr = nowTagWordStr.replace(","," ");
	nowTagWordStr = nowTagWordStr.replace(" "," ");
	if(nowTagWordStr == ""){
		$S("TagWord",TagWord);
	}else{
		var nowTagWord = nowTagWordStr.split(" ");
		for(var i = 0;i < nowTagWord.length;i++){
			 if(nowTagWord[i] == TagWord){
			      break;
			 }
			 if(i == nowTagWord.length-1){
			 	nowTagWordStr = nowTagWordStr+" "+TagWord;
			 }
		}
		$S("TagWord",nowTagWordStr);
	}
}

function FCKeditor_OnComplete( editorInstance ){
   editorInstance.LinkedField.form.onsubmit = save;
   return;
}

function checkDirty(){
	return FCKeditorAPI.GetInstance('Content').IsDirty();
}

function ChangeColor(){
	var color = $V("hTitleColor");
}

Page.onLoad(function(){
	//���ð�ť��Ȩ��
	try{
	    Application.setAllPriv("article",$V("InnerCode"));
	}catch(ex){
	}
	
	var st = new StyleToolbar('TitleStyle',$('Title'),'FontColor');
	st.show();
	st = new StyleToolbar('ShortTitleStyle',$('ShortTitle'),"FontColor,Bold,Italic,UnderLine");
	st.show();
	
	$('ShortTitleStyle_Bold').parentNode.onclick=function(){//���������������ʱ���޸��ּ���Ա�֤�־�ͱ���򱳾��ϵı�߿̶�һ��
		if($('ShortTitle').style.fontWeight=='bold')
			$('ShortTitle').style.letterSpacing='-1px'
		else
			$('ShortTitle').style.letterSpacing='';
		return true;
	}
	if($V("ShortTitle")){
		$("ShowShortTitle").checked=true;
		$("trShortTitle").show();
	}
	if($V("SubTitle")){
		$("ShowSubTitle").checked=true;
		$("trSubTitle").show();
	}
	
	
	var color = $V("hTitleStyle");
	if(color!=""){
		$S("TitleStyle",color);
	}
	
	color = $V("hShortTitleStyle");
	if(color!=""){
		$S("ShortTitleStyle",color);
	}	
	
	if($V("hNewsType")==""){
		$S("NewsType","1");
	}else{
		$S("NewsType",$V("hNewsType"));
	}
	
	changeDocType();
	$S("TopFlag",$V("hTopFlag"));
	$S("CommentFlag",$V("hCommentFlag"));
	if($V("ArticleID") == "" || $V("ArticleID") == "null"){
		$("CommentFlag").checked = true;
	}
	if($V("hPriority")==""){
		$S("Priority","1");
	}else{
		$S("Priority",$V("hPriority"));
	}
	$S("TemplateFlag",$V("hTemplateFlag"));
	showTemplate();

	editor = FCKeditorAPI.GetInstance('Content');
	contents = $V("_Contents").split(PAGE_SPLIT);
	pageTitles = $V("_PageTitles").split(TITLE_SPLIT);
	if($V("CopyImageFlag")==""||$V("CopyImageFlag")=="null"||$V("CopyImageFlag")=="Y"){
		$("LocalImageFlag").checked = true;
	}
	initPages();

    //�����Զ�����
	setInterval(autoSave, 1000*60*5);
 
	var ieHeight = 103;
	var height = (window.document.body.clientHeight-ieHeight)+"px";
	$("_DivContainer").style.height=height;
	initRelaImg();
});

window.onresize = function(){
	var ieHeight = 103;
	var height = (window.document.body.clientHeight-ieHeight)+"px";
	$("_DivContainer").style.height=height;
}

function reloadArticle(){
	var dc = new DataCollection();
  	dc.add("ArticleID",$V("ArticleID"));
  	Server.sendRequest("com.nswt.cms.document.Article.getArticle",dc,function(response){
		if(response.Status==1){
			var pageList = $("pageList");
			for(var i = 1;i<pages;i++){
			    var tab = $("p"+(i+1));
			    pageList.removeChild(tab);
	    	}
	    
		  pages = parseInt(response.get("Pages"));
		  currentPage = 1;
			contents = eval("["+response.get("ContentPages")+"]");
			pageTitles = eval("["+response.get("PageTitles")+"]");
			initPages();
				
			$S("NewsType",response.get("NewsType"));
		  	changeDocType();
			  
		  	if(response.get("TopFlag")=="1"){
				$("TopFlag").checked = true;
			}else{
				$("TopFlag").checked = false;
			}
		  	if(response.get("CommentFlag")=="1"){
				$("CommentFlag").checked = true;
			}else{
				$("CommentFlag").checked = false;
			}
	
			$S("Priority",response.get("Priority"));
			var templateFlag = response.get("TemplateFlag");
			if(templateFlag=="1"){
				$("TemplateFlag").checked = true;
			}else{
				$("TemplateFlag").checked = false;
			}
			showTemplate();
				
			$S("Template",response.get("Template"));
				
			$S("Summary",response.get("Summary"));
				
			editor.SetHTML(contents[0]);
			$S("PageTitle",pageTitles[0]);
			
			$S("ShortTitle",response.get("ShortTitle"));
			$S("Title",response.get("Title"));
			$S("SubTitle",response.get("SubTitle"));
			$S("Keyword",response.get("Keyword"));
			$S("Author",response.get("Author"));
			$S("RedirectURL",response.get("RedirectURL"));
			
			setActivePage(1);
		}else{
		}
	});
}

function preview(){
	if($V("Method")=="ADD"){
		Dialog.alert("������δ���棬����Ԥ��");
		return;
	}else{
	  window.open(Server.ContextPath+"Document/Preview.jsp?ArticleID="+$V("ArticleID")+"&currentPage="+currentPage);
  }
}

/***ͼƬ�ϴ�**/
var customPicName; //�Զ���ͼƬ���ϴ�
function upload(colsName){
	if(colsName){
		customPicName = colsName;
	}else{
		customPicName = null;
	}
	$S("ImageIDs",getImagesFromContent());
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "ͼƬ���/�ϴ�";
	if(colsName==null){
		diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=checkbox";
	} else {
		var url = "Resource/ImageLibDialogArticle.jsp?Search=Search&SelectType=radio&ImageIDs="+$V("ImageIDs");
		if(customPicName=="Logo"){
			url += "&RelaImageIDs="+$V("LogoID");
		}
		diag.URL = url;
	}	
	diag.onLoad = function(){
	};
	diag.OKEvent = OK;	
	diag.CancelEvent = function(){
		FCKeditorAPI.GetInstance("Content").Focus();
		$D.close();
	};
	diag.show();
}

function OK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("ImageUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("ImageBrowse")){
	 	currentTab.contentWindow.onReturnBack();
	}else if(currentTab==$DW.Tab.getChildTab("SelectImage")){
		currentTab.contentWindow.onReturnBack();
	}
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function onReturnBack(returnID){
	if(customPicName!=null){
		var dc = new DataCollection();
		dc.add("PicID",returnID);
		dc.add("Custom","1");
		dc.add("CatalogID", $V("CatalogID"));
		Server.sendRequest("com.nswt.cms.document.Article.getPicSrc",dc,function(response){
			$S(customPicName,response.get("1_picSrc"));
			$("Img"+customPicName).src = response.get("s_picSrc");
			if(customPicName=="Logo"){
				$S("LogoID",returnID);
			}
			customPic = 0;
			//$(customPicName).focus();
		})
	}else{
		var dc = new DataCollection();
		dc.add("PicID",returnID);
		dc.add("CatalogID", $V("CatalogID"));
		Server.sendRequest("com.nswt.cms.document.Article.getPicSrc",dc,function(response){
			var attributes = $NV("Attribute");
			if (!attributes) {
				attributes = "image";
			} else if (attributes.indexOf()==-1) {
				attributes += ",image";
			}
			var ele = $N("Attribute");
			var arr = attributes.split(",");
			for(var j=0;j<arr.length;j++){
				for(var i=0;i<ele.length;i++){
					if (ele[i].value == arr[j]) {
						ele[i].checked = true;
					}
				}
			}
			editor = FCKeditorAPI.GetInstance('Content');
			editor.InsertHtml( response.get("1_picSrc") || "" ) ;
			FCKeditorAPI.GetInstance("Content").Focus();
		})
	}
}

/***�ļ��ϴ�**/
var customFileName; //�Զ����ϴ�
function uploadFile(colsName){
	if(colsName){
		customFileName = colsName;
	}
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag1");
	diag.Width = 760;
	diag.Height = 400;
	diag.Title = "�ļ����/�ϴ�";
	diag.URL = "Resource/AttachmentLibDialog.jsp?Search=Search&SelectType=checkbox";
	diag.onLoad = function(){
	};
	diag.OKEvent = FileOK;	
	diag.CancelEvent = function(){
		FCKeditorAPI.GetInstance("Content").Focus();
		$D.close();
	};
	diag.show();
}

function FileOK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("AttachmentUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("AttachmentBrowse")){
	 	currentTab.contentWindow.onFileReturnBack();
	}
}

function onFileUploadCompleted(returnID){
	onFileReturnBack(returnID);
}

function onFileReturnBack(returnID){
	if(customFileName!=null){
		$S(customFileName,returnID);
		customFile = 0;
	}else{
		var dc = new DataCollection();
		dc.add("FileID",returnID);
		dc.add("CatalogID", $V("CatalogID"));
		Server.sendRequest("com.nswt.cms.document.Article.getFilePath",dc,function(response){
			var attributes = $NV("Attribute");
			if (!attributes) {
				attributes = "attchment";
			} else if (attributes.indexOf()==-1) {
				attributes += ",attchment";
			}
			var ele = $N("Attribute");
			var arr = attributes.split(",");
			for(var j=0;j<arr.length;j++){
				for(var i=0;i<ele.length;i++){
					if (ele[i].value == arr[j]) {
						ele[i].checked = true;
					}
				}
			}
			editor = FCKeditorAPI.GetInstance('Content');
			editor.InsertHtml( response.get("FilePath") || "" ) ;
			FCKeditorAPI.GetInstance("Content").Focus();
		})
	}
}

/***Flash�ϴ�**/
var customFlashName; //�Զ���Flash�ϴ�
function uploadFlash(colsName){
	if(colsName){
		customFlashName = colsName;
	}
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag1");
	diag.Width = 760;
	diag.Height = 400;
	diag.Title = "Flash���/�ϴ�";
	diag.URL = "Resource/FlashLibDialog.jsp?Search=Search&SelectType=checkbox";
	diag.onLoad = function(){
	};
	diag.OKEvent = FlashOK;	
	diag.CancelEvent = function(){
		FCKeditorAPI.GetInstance("Content").Focus();
		$D.close();
	};
	diag.show();
}

function FlashOK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("FlashUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("FlashBrowse")){
	 	currentTab.contentWindow.onFlashReturnBack();
	}
}

function onFlashUploadCompleted(returnID){
	onFlashReturnBack(returnID);
}

function onFlashReturnBack(returnID){
	if(customFlashName!=null){
		$S(customFlashName,returnID);
		customFile = 0;
	}else{
		var dc = new DataCollection();
		dc.add("FlashID",returnID);
		dc.add("CatalogID", $V("CatalogID"));
		Server.sendRequest("com.nswt.cms.document.Article.getFlashPath",dc,function(response){
			var attributes = $NV("Attribute");
			if (!attributes) {
				attributes = "attchment";
			} else if (attributes.indexOf()==-1) {
				attributes += ",attchment";
			}
			var ele = $N("Attribute");
			var arr = attributes.split(",");
			for(var j=0;j<arr.length;j++){
				for(var i=0;i<ele.length;i++){
					if (ele[i].value == arr[j]) {
						ele[i].checked = true;
					}
				}
			}
			editor = FCKeditorAPI.GetInstance('Content');
			editor.InsertHtml( response.get("FlashPath") || "" ) ;
			FCKeditorAPI.GetInstance("Content").Focus();
		})
	}
}

/**��Ƶ
*/
function uploadVideo(){
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag1");
	diag.Width = 760;
	diag.Height = 460;
	diag.Title = "��Ƶ���/�ϴ�";
	diag.URL = "Resource/VideoLibDialog.jsp?Search=Search&SelectType=checkbox";
	diag.onLoad = function(){
	};
	diag.OKEvent = uploadVideoOK;	
	diag.CancelEvent = function(){
		FCKeditorAPI.GetInstance("Content").Focus();
		$D.close();
	};	
	diag.show();
}

function uploadVideoOK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("VideoUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("VideoBrowse")){
	 	currentTab.contentWindow.onVideoReturnBack();
	}
}

function onUploadVideoCompleted(returnID){
	onVideoReturnBack(returnID);
}

function onVideoReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("VideoID",returnID);
	dc.add("CatalogID", $V("CatalogID"));
	Server.sendRequest("com.nswt.cms.document.Article.getVideoPath",dc,function(response){
		var attributes = $NV("Attribute");
		if (!attributes) {
			attributes = "video";
		} else if (attributes.indexOf()==-1) {
			attributes += ",video";
		}
		var ele = $N("Attribute");
		var arr = attributes.split(",");
		for(var j=0;j<arr.length;j++){
			for(var i=0;i<ele.length;i++){
				if (ele[i].value == arr[j]) {
					ele[i].checked = true;
				}
			}
		}
		editor = FCKeditorAPI.GetInstance('Content');
		editor.InsertHtml( response.get("VideoPath") || "" ) ;
		FCKeditorAPI.GetInstance("Content").Focus();
	});
}

function insertVote(){
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "�������ͶƱ";
	diag.URL = "DataService/VoteListDialog.jsp";
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�������в������ͶƱ";
	diag.Message = "�������в������󣬸õ����'JS����'����뵽���µ�Դ���뵱�С�";
	diag.OKEvent = insertVoteOK;	
	diag.show();
}

function insertVoteOK(){
	var arr = $DW.DataGrid.getSelectedValue("dg1");
	if(!arr){
		Dialog.alert("����ѡ����!");
		return;
	}
	$D.close();
	var dc = new DataCollection();
	dc.add("ID",arr.join());
	dc.add("CatalogID", $V("CatalogID"));
	Server.sendRequest("com.nswt.cms.document.Article.getJSCode",dc,function(response){
		editor = FCKeditorAPI.GetInstance('Content');
		editor.InsertHtml( response.get("JSCode") || "" ) ;
	});
}

function create(){
	if($V("Method")=="ADD"){
		Dialog.confirm("������δ���棬ȷ���½����£�",function(){
			window.location="Article.jsp?CatalogID="+$V("CatalogID");
		});
	}else{
		window.location="Article.jsp?CatalogID="+$V("CatalogID");
	}
}

function getKeywordOrSummary(type){
	var dc = new DataCollection();
	editor = FCKeditorAPI.GetInstance('Content');
	contents[currentPage-1]=editor.GetXHTML(false);	  
	var content = contents.join(PAGE_SPLIT);		
	dc.add("Content",content);
	dc.add("Title",$V("Title"));
	dc.add("Type",type);
	Server.sendRequest("com.nswt.cms.document.Article.getKeywordOrSummary",dc,function(response){
		if(response.Message==0){
			Dialog.alert(response.Message);
		}else{
			$S(type,response.get("Text"));
			$(type).focus();
			Dialog.alert("��ȡ�ɹ�!");
		}
	});
}

function checkMandatory(ele){
	var defaultValue = '${defaultImageValue}';
	var imagevalue = $V(ele);
	if (!ele || imagevalue==defaultValue) {
		return false;
	}
	return true;
}


function relaImagePlayer(){
	$S("ImageIDs",getImagesFromContent());
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 400;
	diag.Title = "����ͼƬ";
	diag.URL = "Document/RelaImageDialog.jsp?CatalogID="+$V("CatalogID")+"&RelaImagePlayerID="+$V("RelaImagePlayerID")+"&RelaImageIDs="+$V("RelaImageIDs")+"&ImageIDs="+$V("ImageIDs");
	diag.onLoad = function(){
		if(!$V("ImageIDs")){
			$DW.$("tdImages").innerHTML = "<font color='red' style='font-size:12px'><b>������û��ͼƬ!</b></font>";
			$DW.$("tdImages").align = "center";
			$DW.$("tdImages").valign = "middle";
			$DW.$("tdImages").width = "400";
			$DW.$("tdImages").height = "300";
		}
	}
	diag.OKEvent = relaImageSave;
	diag.show();
}

function relaImageSave(){
	var arr = $DW.$NV("ImageID");
	if(arr == null || arr.length==0){
		$S("RelaImagePlayerID","");
		$S("RelaImageIDs","");
		$("RelaImgPrew").innerHTML = "<img src='../Images/addpicture.jpg' width='80' height='60' border=0>";
		$("PlayerName").innerHTML = "����ͼƬ������";
		$D.close();
		return;
	}
	if(arr.length>6){
		arr = arr.slice(0,6);
	}
	var tempRelaImgID = $V("RelaImageIDs").split(",");
	if(tempRelaImgID==""||tempRelaImgID==null){
		$S("RelaChange","true");
	}else{
		if(tempRelaImgID.length==arr.length){
			for(var i=0;i<arr.length;i++){
				var flag = true;
				for(var j=0;j<tempRelaImgID.length;j++){
					if(arr[i]==tempRelaImgID[j]){
						flag = false;
					}
				}
				if(flag){
					$S("RelaChange","true");
					break;
				}
			}
		}else{
			$S("RelaChange","true");
		}
	}
	if($DW.$V("SelectPlayerID")==null||$DW.$V("SelectPlayerID")==""){
		Dialog.alert("��ѡ��ͼƬ��������");
		return;
	}
	$S("RelaImagePlayerID",$DW.$V("SelectPlayerID"));
	$S("RelaImageIDs",arr.join());
	initRelaImg();
	$D.close();
}

function initRelaImg(){;
	if($V("RelaImageIDs")==null||$V("RelaImageIDs")==""||$V("RelaImagePlayerID")==null||$V("RelaImagePlayerID")==""){
		return;
	}
	var dc = new DataCollection();
	dc.add("PicID",$V("RelaImageIDs"));
	dc.add("isRela","true");
	dc.add("CatalogID", $V("CatalogID"));
	Server.sendRequest("com.nswt.cms.document.Article.getPicSrc",dc,function(response){
		$("RelaImgPrew").innerHTML=response.get("1_picSrc");
	});
	dc.add("RelaImagePlayerID", $V("RelaImagePlayerID"));
	Server.sendRequest("com.nswt.cms.document.Article.getPlayerName",dc,function(response){
		$("PlayerName").innerHTML=response.get("PlayerName");
		$("PlayerName").style.display = "";
	});	
}

function getImagesFromContent(){
	var dc = new DataCollection();
	editor = FCKeditorAPI.GetInstance('Content');
	contents[currentPage-1]=editor.GetXHTML(false);	  
	var content = contents.join(PAGE_SPLIT);
	document.createElement("div");
	var div = document.getElementById("_TmpImageDiv");
	if(!div){
		div = document.createElement("div");
		div.style.display = 'none';
		div.id = "_TmpImageDiv";
	 	document.body.appendChild(div);
	}
	div.innerHTML = content;
	var arr = [];
	getImagesFromNode(div,arr);
	return arr;
}

function getImagesFromNode(pNode,arr){
	var nodes = pNode.childNodes;
	if(!nodes||nodes.length==0){
		return;
	}
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		if(node.tagName&&node.tagName.toLowerCase()=="img"){
			if(node.getAttribute("nswtpimagerela")){
				arr.push(node.getAttribute("nswtpimagerela"));
			}
		}
		getImagesFromNode(node,arr);
	}
}

function verifySameTitle(){
	var title = $V("Title");
	if(title&&title!=""){
		var dc = new DataCollection();
		dc.add("ID",$V("ArticleID"));
		dc.add("Title",title);
		dc.add("CatalogID",$V("CatalogID"));
		Server.sendRequest("com.nswt.cms.document.Article.verifySameTitle",dc,function(response){
			if(response.Status==1){
				$("trVerifyTitle").style.display = "";
			}else{
				$("trVerifyTitle").style.display = "none";
			}
		});	
	}
}
</script>
	</head>
	<body>
	<div id="wrapper">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div id="buttonDiv" class="divbtn">${buttonDiv}</div>
			<div><z:tbutton onClick="note()">
				<img src="../Icons/icon018a4.gif" />��ע</z:tbutton> <z:tbutton
				priv="article_modify" onClick="version()">
				<img src="../Icons/icon026a12.gif" width="20" height="20" />�汾</z:tbutton> <z:tbutton
				priv="article_modify" onClick="articleLog()">
				<img src="../Icons/icon_column.gif" width="20" height="20" />������ʷ</z:tbutton> <z:tbutton
				priv="article_modify" onClick="getKeywordOrSummary('Keyword')">
				<img src="../Icons/icon029a17.gif" width="20" height="20" />��ȡ�ؼ���</z:tbutton> <z:tbutton
				priv="article_modify" onClick="getKeywordOrSummary('Summary')">
				<img src="../Icons/icon029a28.gif" width="20" height="20" />��ȡժҪ</z:tbutton> <z:tbutton
				onClick="preview()">
				<img src="../Icons/icon403a3.gif" width="20" height="20" />Ԥ��</z:tbutton> <z:tbutton
				onClick="closeX()">
				<img src="../Icons/icon403a11.gif" />�ر�</z:tbutton> <z:tbutton
				priv="article_modify" onClick="create()">
				<img src="../Icons/icon003a2.gif" width="20" height="20" />�½�</z:tbutton></div>
			</td>
		</tr>
	</table>
	<form method="post" id="form1"><input type="hidden" id="SiteID"
		value="${SiteID}"> <input type="hidden" id="CatalogID"
		value="${CatalogID}"> <input type="hidden" id="InnerCode"
		value="${InnerCode}"> <input type="hidden" id="IssueID"
		value="${IssueID}"> <input type="hidden" id="hNewsType"
		value="${Type}"> <input type="hidden" id="hTopFlag"
		value="${TopFlag}"> <input type="hidden" id="hPriority"
		value="${Priority}"> <input type="hidden" id="hCommentFlag"
		value="${CommentFlag}"> <input type="hidden"
		id="hTemplateFlag" value="${TemplateFlag}"> <input
		type="hidden" id="ArticleID" value="${ID}"> 
	<input type="hidden" id="RelativeArticle" value="${RelativeArticle}">
	<input type="hidden" id="NoteContent" value=""> <input
		type="hidden" id="Method" value="${Method}"> <input
		type="hidden" id="hTitleStyle" value="${TitleStyle}"> <input
		type="hidden" id="hShortTitleStyle" value="${ShortTitleStyle}">
        <input type="hidden" id="RecommendArticle" value="${RecommendArticle}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;">
		<tr>
			<td>
			<div id="xToolbar" style="height: 52px;"></div>			</td>
		    <td>&nbsp;</td>
		    <td width="220" rowspan="2" valign="top">
			  <table width="100%" border="0" cellspacing="4" class="cellspacing"
				cellpadding="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="themleft">
						<tr>
							<td class="themright"
								onClick="displayAdvance('id_AdvanceTd2','id_AdvanceImg2','Images/thembitopen.gif','Images/thembitclose.gif');">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="5">&nbsp;</td>
									<td class="windowname">��������</td>
									<td width="20"><img src="Images/thembitclose.gif"
										name="id_img2" width="13" height="13" id="id_AdvanceImg2"></td>
									<td width="5">&nbsp;</td>
								</tr>
							</table>							</td>
						</tr>
						<tr>
							<td align="center" valign="top" class="thembg" id="id_AdvanceTd2">
							<table width="95%" border="0" cellpadding="0" cellspacing="4"
								class="cellspacing">
								<tr align="left" valign="middle">
									<td width="65"  align="right" nowrap>������Ŀ</td>
									<td width="123" valign="middle">
											<div id="CatalogName">${CatalogName}</div>
									</td>
								</tr>
								
								<tr align="left" valign="middle" ${ReferDisplay}>
									<td width="65"  align="right" nowrap>��Դ��Ŀ</td>
									<td width="123" valign="middle">
											<div>${ReferSourceName}</div>
									</td>
								</tr>
								<tr align="left" valign="top" id="trQuoteChannel">
									<td width="65" align="right">���Ƶ�</td>
									<td><a href="#" onclick="copyDialog()">��վ��Ŀ(<span class="redCount" id="spanReferCount">${ReferTargetCount}</span>)</a>
								      <input name="ReferType" type="hidden" id="ReferType" value="${ReferType}">
								    <input name="ReferTarget" type="hidden" id="ReferTarget" value="${ReferTarget}"></td>
								</tr>
								<tr align="left">
									<td align="right">&nbsp;</td>
									<td><a href="#" onclick="copyClusterDialog()">��վȺ��Ŀ(<span class="redCount" id="spanClusterCount">${ClusterTargetCount}</span>)</a>
										<input name="ClusterTarget" type="hidden" id="ClusterTarget" value="${ClusterTarget}">
								  </td>
								</tr>
								<tr align="left">
								  <td align="right" valign="top"><p>ͼƬ������</p>
							      </td>
								  <td><input type="hidden" id="ImageIDs" name="ImageIDs" value="${ImageIDs}"/>
                                    <input type="hidden" id="RelaImagePlayerID" name="RelaImagePlayerID" value="${RelaImagePlayerID}"/>
                                    <input type="hidden" id="RelaImageIDs" name="RelaImageIDs" value="${RelaImageIDs}"/>
                                    <input type="hidden" id="RelaChange" name="RelaChange" value="false"/>
                                    <a href="#;" onClick="relaImagePlayer()"><span id="RelaImgPrew"><img src="../Images/addpicture.jpg" width="80" height="60" border=0></span> <br>
                                  <font id="PlayerName">����ͼƬ������</font></a></td>
							  </tr>
								<tr align="left">
								  <td align="right" valign="top">����ͼ</td>
								  <td><a href="#;" onClick="upload('Logo')"><font id="PlayerName" color="#FF0000"><img src="${ImgLogo}" width="80" id="ImgLogo" border=0><input type="hidden" ID="Logo" value="${Logo}"><input type="hidden" ID="LogoID" value="${LogoID}"></font><br>��������ͼ</a></td>
							  </tr>
							</table>							</td>
						</tr>
					</table>					</td>
				</tr>
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="themleft">
						<tr>
							<td class="themright"
								onClick="displayAdvance('id_AdvanceTd3','id_AdvanceImg3','Images/thembitopen.gif','Images/thembitclose.gif');">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="5">&nbsp;</td>
									<td class="windowname">��������</td>
									<td width="20"><img src="Images/thembitclose.gif"
										width="13" height="13" id="id_AdvanceImg3"></td>
									<td width="5">&nbsp;</td>
								</tr>
							</table>							</td>
						</tr>
						<tr id="LeftContent_TR2">
							<td align="center" valign="top" class="thembg" id="id_AdvanceTd3">
							<table width="95%" border="0" cellpadding="0" cellspacing="4"
								class="cellspacing">
								<tr align="left" valign="top">
									<td width="16%" nowrap>ģ��</td>
									<td width="84%"><label for="TemplateFlag"> <input
										type="checkbox" name="TemplateFlag" value="1"
										id="TemplateFlag" onClick="showTemplate()"> ����ģ��</label></td>
								</tr>
								<tr align="left" valign="top" id="DivTemplate"
									style="display: none">
									<td width="16%" height="20">&nbsp;</td>
									<td><input name="Template" type="text" class="input1"
										id="Template" size="13" value="${Template}"
										verify="����ģ��|NotNull"
										condition="$('TemplateFlag').checked==true" /> <input
										name="Button22" type="button" class="input2" id="Button22"
										value="���..." onClick="browse('Template')"></td>
								</tr>
								<tr align="left" valign="top">
									<td>����</td>
									<td width="84%"><input name="text" type="text"
										class="input1" id="PublishDate" value="${PublishDate}"
										size="14" ztype="Date"
										style="font-family: Arial; font-size: 10px;" /> <input
										name="text" type="text" class="input1" id="PublishTime"
										value="${PublishTime}" size="10" ztype="Time"
										style="font-family: Arial; font-size: 9px;" /> <label
										for="label"></label></td>
								</tr>
								<tr align="left" valign="top">
									<td>�鵵</td>
									<td><input name="text3" type="text" class="input1"
										id="ArchiveDate" value="${ArchiveDate}" size="14"
										ztype="Date" style="font-family: Arial; font-size: 10px;" />
									<input name="text2" type="text" class="input1"
										id="ArchiveTime" value="${ArchiveTime}" size="10"
										ztype="Time" style="font-family: Arial; font-size: 9px;" /></td>
								</tr>
								<tr align="left" valign="top">
									<td valign="middle">ժҪ</td>
									<td><textarea name="Summary" cols="25" rows="3"
										id="Summary" style="width: 160px">${Summary}</textarea></td>
								</tr>
							</table>							</td>
						</tr>
					</table>					</td>
				</tr>
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="4" cellspacing="4"
						class="cellspacing" id="otherbtn">
						<tr>
							<td align="center" valign="middle"><a href="#;"
								onClick="addRelaArticle()"><img src="Images/icon2.gif"
								width="9" height="9">�������</a></td>
							<td align="center" valign="middle"><a href="#;"
								onClick="addRecArticle()"><img src="Images/icon4.gif"
								width="9" height="9">�Ƽ�����</a></td>
						</tr>
					</table></td>
				</tr>
			  </table></td>
		</tr>
		<tr>
			<td valign='top'><div id="_DivContainer"
				style="text-align: center; overflow: auto; height: 1200px; width: 100%; background-color: #666666; position: relative">
			  <table id="_Table1" width="750" border="0" cellpadding="20"
				bgcolor="#FFFFFF" style="margin: 5px auto">
			    <tr>
			      <td valign="top"><table width="100%" cellpadding="2" cellspacing="0">
			        <tr>
			          <td width="11%" height="28" align="center"><strong>����</strong></td>
			          <td colspan="3" align="left"><input name="Title" type="text"
								class="input1" id="Title"
								style="width:430px;background:url(Images/rule.gif) repeat-x left bottom;${TitleStyle}"
								value="${Title}" verify="����|NotNull&&Length<60" onBlur="verifySameTitle();" />
			            <input type="checkbox" value="checkbox" id="ShowShortTitle" onclick="$('trShortTitle').toggle()"><label for="ShowShortTitle">�̱���</label>
			            <input type="checkbox" value="checkbox" id="ShowSubTitle" onclick="$('trSubTitle').toggle()"><label for="ShowSubTitle">������</label>						</td>
			          </tr>
                      <tr id="trVerifyTitle" style="display:none;">
			          <td width="11%" align="center">&nbsp;</td>
			          <td colspan="3" align="left" valign="middle"><div class="notice" style="margin:0px;border-width:1px;width:80%;padding-top:2px;padding-bottom:2px"><font color="#FF6600">&nbsp;&nbsp;&nbsp;ע�⣬��ǰ��Ŀ��������ͬ��������¡�</font></div></td>
			          </tr>
			        <tr id="trShortTitle" style="display:none">
			          <td width="11%" height="28" align="center"><strong>�̱���</strong></td>
			          <td colspan="3" align="left"><input name="ShortTitle" type="text"
								class="input1"
								style="background:url(Images/rule.gif) repeat-x left bottom; width:300px;${ShortTitleStyle}"
								id="ShortTitle" size="50" value="${ShortTitle}" /></td>
			          </tr>
			        <tr id="trSubTitle" style="display:none">
			          <td height="28" align="center"><strong>������</strong></td>
			          <td colspan="3" align="left"><input name="SubTitle" type="text"
								class="input1"
								style="background: url(Images/rule.gif) repeat-x left bottom; width: 300px;"
								id="SubTitle" size="50" value="${SubTitle}" /></td>
			          </tr>
			        <tr>
			          <td height="28" align="center"><strong>��&nbsp;��</strong></td>
			          <td width="32%" align="left"><strong>
			            <input name="Author" type="text" class="input1" id="Author"
								size="40" value="${Author}" />
			          </strong></td>
			          <td width="9%" align="center"><strong>��&nbsp;Դ</strong></td>
			          <td width="48%" align="left">
                      <input type="text" id="ReferName" name="ReferName" size="40" value="${ReferName}" /> 
 			           <input name="Button" type="button" id="Button" value="ѡ��" onClick="selectRefer()">
                     </td>
			        </tr>
			        <tr>
			          <td height="28" align="center"><strong>�ؼ���</strong></td>
			          <td align="left"><p>
			            <input name="keyword" type="text" class="input1"
								id="Keyword" size="40" value="${Keyword}" />
			          </p></td>
			          <td align="center"><strong>Tag</strong></td>
			          <td align="left"><input name="TagWord" type="text" class="input1"
								id="TagWord" size="40" value="${Tag}" />
			            <input name="Button" type="button" id="Button" value="ѡ��" onClick="selectTag()">
						</td>
			        </tr>
			        <tr style="display: none">
			          <td height="28" align="center"><strong>URL��ַ</strong></td>
			          <td colspan="3" align="left"><input name="URL" id="URL" size="50"
								type="text" value="${URL}"></td>
			          </tr>
			        <tr>
			          <td height="28" align="center"><strong>����ѡ��</strong></td>
			          <td colspan="3" align="left">
					  ���ͣ�<z:select style="width:80px"><select id="NewsType" onChange="changeDocType()"> 
					  <option value="1">��ͨ����</option>
					  <option value="4">��������</option></select> </z:select>
					  	���ж�:
			            <z:select style="width:60px"><select id="Priority">
						<option value="1" selected>һ��</option>
						<option value="2">����</option>
						<option value="3">�ر�����</option></select></z:select>
			              <label for="TopFlag"><input name="TopFlag" type="checkbox" id="TopFlag" value="1">�����ö�</label>
			              <label for="CommentFlag"><input type="checkbox" name="CommentFlag" value="1" id="CommentFlag">��������</label>
			              <input name="LocalImageFlag" type="checkbox"
										id="LocalImageFlag" value="1">
                          <label
										for="LocalImageFlag">�Զ�����Զ��ͼƬ</label>
                          <input type="hidden"
										id="CopyImageFlag" name="CopyImageFlag"
										value="${CopyImageFlag}" />						</td>
			          </tr>
			        <tr>
			          <td height="28" align="center"><strong>��������</strong></td>
			          <td colspan="3" align="left">${Attribute}</td>
			          </tr>
			        ${CustomColumn}
                    <script type="text/javascript">
					function custom_img_upload(colsName){
						parent.upload(colsName);
					}
					</script>
			        </table>
			        <!--�߼�ѡ��-->
			        <div id="DivRedirect" style="display: none">
			          <table width="100%" cellpadding="2" cellspacing="0">
			            <tr>
			              <td width="8%" align="center"><strong>ת������</strong></td>
			              <td align="left"><input name="RedirectURL" type="text"
								class="input1" id="RedirectURL" size="50" value="${RedirectURL}" /></td>
		                </tr>
		              </table>
		            </div>
			        <div id="DivContent">
			          <table width="100%" cellpadding="2" cellspacing="0">
			            <tr id="trPageTitle" style="display:none">
			              <td width="8%" align="center"><strong>��ҳ����</strong></td>
			              <td align="left"><input name="PageTitle" type="text"
								id="PageTitle" size="50" value="${PageTitle}" />
			                <input
								name="_PageTitles" type="hidden" id="_PageTitles"
								value="${PageTitles}" /></td>
		                </tr>
		              </table>
			          <table width="100%" height="400" cellpadding="2" cellspacing="0">
			            <tr>
			              <td align="center"><textarea name="textarea" id="_Content"
								style="display: none">${Content}</textarea>
			                <textarea
								name="textarea" id="_Contents" style="display: none">${Contents}</textarea>
			                <script type="text/javascript">
								var sBasePath = Server.ContextPath+"Editor/" ;
								var oFCKeditor = new FCKeditor( 'Content' ) ;
								oFCKeditor.BasePath	= sBasePath ;
								oFCKeditor.Width = 650 ;
								oFCKeditor.Height = 800 ;
								oFCKeditor.Config['EditorAreaCSS'] = '${CssPath}';
								oFCKeditor.Config[ 'ToolbarLocation' ] = 'Out:xToolbar' ;
								oFCKeditor.Value = $V("_Content");
								oFCKeditor.Create() ;
							</script></td>
		                </tr>
		              </table>
		            </div></td>
		        </tr>
		      </table>
	      </div></td>
			<td width="6" rowspan="3" align="right" bgcolor="#F6F9FD"
				style="border-left: 1px solid #91A9BD; border-right: 1px solid #CFE6F2"><img
				src="Images/right_close.gif" width="6" height="60"
				style="cursor: pointer; display: inline;" title="����������Ϣ"
				onClick="displayAdvance('id_AdvanceTd',this,'Images/left_close.gif','Images/right_close.gif');"></td>
		</tr>
	</table>
	</form>
	</div>
	<div id="pageBarDiv" style="padding-right: 230px;">
	<table width="100%" id="pageBarTable">
		<tr>
			<td valign="middle" bgcolor="#F7F8FD" class="pagetab">
			<ul id="pageList">
				<li onClick="changePage(event)" onMouseOver="onOverPage(event)"
					onMouseOut="onOutPage(event)" class="current" id="p1"
					name="tabs"><b>ҳ 1</b></li>
			</ul>
			<span class="add"><a href="#;" onClick="addPage()"
				alt="�ڵ�ǰҳ�����"><img src="../Framework/Images/icon_plus.gif"
				border="0" alt="�ڵ�ǰҳ�����"></a></span> <span class="add"><a
				href="#;" onClick="deletePage()" alt="ɾ����ǰҳ"><img
				src="../Framework/Images/icon_minus.gif" border="0" alt="ɾ����ǰҳ"></a></span></td>
			<td width="250" valign="middle" align="right"
				style="padding-right: 10px;" bgcolor="#F7F8FD" class="pagetab">��󱣴�ʱ��:<span
				id="SaveTime">${LastModify}</span></td>
		</tr>
	</table>
	</div>
	</body>
	</html>
</z:init>

