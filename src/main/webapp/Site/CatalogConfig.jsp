<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
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
		Application.setAllPriv(ctype,$V("InnerCode"));
	}else{
		Application.setAllPriv("site",Application.CurrentSite);
	}
	if($NV("PlanType")=="Period"){
		var expr = $V("CronExpression");
		var arr = expr.split(" ");
		if(arr[0].indexOf("/")>0){
			$S("PeriodType","Minute");
			$S("Period",arr[0].substring(arr[0].indexOf("/")+1));
		}
		if(arr[1].indexOf("/")>0){
			$S("PeriodType","Hour");
			$S("Period",arr[1].substring(arr[1].indexOf("/")+1));
		}
		if(arr[2].indexOf("/")>0){
			$S("PeriodType","Day");
			$S("Period",arr[2].substring(arr[2].indexOf("/")+1));
		}
		if(arr[3].indexOf("/")>0){
			$S("PeriodType","Month");
			$S("Period",arr[3].substring(arr[3].indexOf("/")+1));
		}
		if(arr[4].indexOf("/")>0){
			$S("PeriodType","Year");
			$S("Period",arr[4].substring(arr[4].indexOf("/")+1));
		}
	}
	onPlanChange();
	onIsUsingChange();
});

function onPlanChange(){
	if($NV("PlanType")=="Period"){
		$("Period").enable();
		$("PeriodType").enable();
		$("trCron").hide();
	}else{
		$("Period").disable();
		$("PeriodType").disable();
		$("trCron").show();
	}
}

function save(){
	if(Verify.hasError()){
		return;
	}
	var dc = Form.getData($F("form2"));
	Server.sendRequest("com.nswt.cms.site.CatalogConfig.save",dc,function(response){
		Dialog.alert(response.Message);
	});
}

function generatePassword(id){
	var dc = new DataCollection();
	Server.sendRequest("com.nswt.cms.site.CatalogConfig.generatePassword",dc,function(response){
		Dialog.alert(response.Message);
		$S(id,response.get("Password"));
	});
}

function onIsUsingChange(){
	if($NV("IsUsing")=="N"){
		$("StartDate").disable();
		$("StartTime").disable();
		$("PlanType1").disable();
		$("PlanType2").disable();
		$("Period").disable();
		$("PeriodType").disable();
		$("CronExpression").disable();
	}else{
		$("StartDate").enable();
		$("StartTime").enable();
		$("PlanType1").enable();
		$("PlanType2").enable();
		$("Period").enable();
		$("PeriodType").enable();
		$("CronExpression").enable();
	}
}
</script>
</head>
<body>
<z:init method="com.nswt.cms.site.CatalogConfig.init">
	<div style="padding:2px;">
	<table width="100%">
		<tr>
			<td><z:tbutton id="BtnSave" priv="site_manage" onClick="save();"><img src="../Icons/icon002a2.gif" width="20" height="20" />����</z:tbutton></td>
		</tr>
	</table>
    <form id="form2">
	<input name="SiteID" type="hidden" id="SiteID" value="${SiteID}"/>
	<input name="CatalogID" type="hidden" id="CatalogID" value="${CatalogID}" />
	<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" />
    <input name="ID" type="hidden"  id="ID" value="${ID}" />
    <input name="Type" type="hidden" id="Type" value="${Type}" />
	<table width="650" border="1" cellpadding="4" cellspacing="0" bordercolor="#eeeeee">
		<tr>
		  <td  class="tdgrey1"><b>��ʱ������</b></td>
		  <td  class="tdgrey1">����״̬��</td>
		  <td class="tdgrey2"><label for="IsUsing1">
            <input type="radio" name="IsUsing" value="Y" id="IsUsing1" onclick="onIsUsingChange()" ${IsUsingYCheck}>
		    ����</label>
              <label for="IsUsing0">
              <input type="radio" name="IsUsing" value="N" id="IsUsing0" onclick="onIsUsingChange()" ${IsUsingNCheck}>
                ͣ��</label></td>
		</tr>
		<tr>
			<td width="100"  class="tdgrey1">&nbsp;</td>
			<td width="200"  class="tdgrey1">��ʼʱ�䣺</td>
			<td class="tdgrey1"><span class="tdgrey2">
              <input name="StartDate" type="text" class="inputText" id="StartDate" ztype="Date" value="${StartDate}" size=14/>
			  </span> <span class="tdgrey2">
              <input name="StartTime" type="text" class="inputText"id="StartTime" ztype="Time" value="${StartTime}" size=14/>
	      </span></td>
		</tr>
		<tr>
			<td class="tdgrey1">&nbsp;</td>
			<td class="tdgrey1">ִ�����ڣ�</td>
			<td class="tdgrey2"><label>
			  <input name="PlanType" id="PlanType1" type="radio" value="Period" onClick="onPlanChange();" ${PeriodCheck}>
			  ÿ��</label>
                <input name="Period" type="text" value="1" class="inputText" id="Period" size=4 verify="NotNull" />
                <z:select id="PeriodType" style="width:50px;"> <span value="Minute">����</span> <span value="Hour">Сʱ</span> <span value="Day" selected="true">��</span> <span value="Month">��</span> </z:select></td>
		</tr>
		<tr>
			<td class="tdgrey1"></td>
			<td class="tdgrey1"></td>
			<td class="tdgrey2"><label>
			  <input type="radio" name="PlanType" id="PlanType2"  value="Cron" onClick="onPlanChange();" ${CronCheck}>
			  ʹ��Cron���ʽ</label>
                <div id="trCron" style="display:none">
                  <input name="CronExpression" type="text" value="${CronExpression}" class="inputText" id="CronExpression" size=40 verify="NotNull"/>
                </div></td>
		</tr>
	</table>
	<table width="650" border="1" cellpadding="4" cellspacing="0" bordercolor="#eeeeee">
		<tr>
			<td width="100"><b>�鵵����</b>��</td>
			<td width="200" height="28">�鵵���ޣ�</td>
		  <td><z:select id="ArchiveTime"> ${ArchiveTimeOptions}</z:select></td>
		</tr>
		<tr style="display:${sitedisplay};">
			<td>&nbsp;</td>
			<td height="28">�鵵�������ã�</td>
		  <td align="left"><z:select id="SiteArchiveExtend" style="width:150px;"> <span value="1">������</span> <span value="2">������Ŀ������ͬ����</span> </z:select></td>
		</tr>
		<tr style="display:${catalogdisplay};">
			<td>&nbsp;</td>
			<td height="28">�鵵�������ã�</td>
		  <td align="left"><z:select id="CatalogArchiveExtend" style="width:150px;"> <span value="1">������Ŀ</span> <span value="2">��������Ŀ������ͬ����</span> <span value="3">������Ŀ������ͬ����</span> </z:select></td>
		</tr>
        <tr>
            <td class="tdgrey1"><b>�ȵ������:</b></td>
            <td height="28" class="tdgrey1">�ȵ�ʻ����ã�</td>
            <!--td class="tdgrey2"><label for="KeyWordUsing1">
              <input type="radio" name="HotWordFlag" value="Y" id="KeyWordUsing1" ${KeyWordYCheck}>
              �����ȵ�ʻ�</label>
                <label for="KeyWordUsing0">
                  <input type="radio" name="HotWordFlag" value="N" id="KeyWordUsing0" ${KeyWordNCheck}>
                  �������ȵ�ʻ�</label>
                <label for="KeyWordUsing2">
                  <input type="radio" name="HotWordFlag" value="S" id="KeyWordUsing2" ${KeyWordSCheck}>
                  �̳��ϼ���Ŀ����</label>
            </td-->
            <td>
            	<z:select id="keywordType" style="width:114px" value="${HotWordType}" code="#com.nswt.cms.site.KeywordType.loadKeywordType"></z:select>            </td>
        </tr>
        <tr style="display:${sitedisplay};">
			<td>&nbsp;</td>
			<td height="28">�ȵ�ʻ㴦�����ã�</td>
		  <td align="left"><z:select id="SiteHotWordExtend" style="width:150px;"> <span value="1">������</span> <span value="2">������Ŀ������ͬ����</span> </z:select></td>
        </tr>
		<tr style="display:${catalogdisplay};">
			<td>&nbsp;</td>
			<td height="28">�ȵ�ʻ㴦�����ã�</td>
		  <td align="left"><z:select id="CatalogHotWordExtend" style="width:150px;"> <span value="1">������Ŀ</span> <span value="2">��������Ŀ������ͬ����</span> <!--span value="3">������Ŀ������ͬ����</span>--> </z:select></td>
		</tr>
		<tr>
			<td class="tdgrey1"><b>��������:</b></td>
			<td height="28" class="tdgrey1">���������Ƿ�ʹ��ԭʼ�ļ�����</td>
			<td class="tdgrey2">${AttachDownFlagRadios}</td>
		</tr>
		<tr>
			<td class="tdgrey1"><b>����ѡ��:</b></td>
			<td height="28" class="tdgrey1">�Ƿ�ֻ�����������</td>
			<td class="tdgrey2">${BranchManageFlagRadios}</td>
		</tr>
		<tr>
		  <td class="tdgrey1"><strong>��վȺ�ɼ���</strong></td>
		  <td height="28" class="tdgrey1">������վȺ�ɼ���</td>
		  <td class="tdgrey2">${AllowInnerGather}</td>
		</tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">ϵ��վȺ�ɼ���Կ��</td>
		  <td class="tdgrey2"><input name="InnerGatherPassword" type="text" class="inputText" id="InnerGatherPassword" size=35/>
	      <input type="button" name="Submit" value="�������" onclick="generatePassword('InnerGatherPassword')"></td>
	    </tr>
		<tr>
		  <td class="tdgrey1"><strong>��վȺ�ַ���</strong></td>
		  <td height="28" class="tdgrey1">������վȺ�ַ���</td>
		  <td class="tdgrey2">${AllowInnerDeploy}</td>
	    </tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">��վȺ�ַ���Կ��</td>
		  <td class="tdgrey2"><input name="InnerDeployPassword" type="text" class="inputText" id="InnerDeployPassword" size=35/>
	      <input type="button" name="Submit2" value="�������" onClick="generatePassword('InnerDeployPassword')"></td>
		</tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">����ַ���Ŀ��ӣ�</td>
		  <td class="tdgrey2">${SyncCatalogInsert}</td>
		</tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">����ַ���Ŀ�޸�/ɾ����</td>
		  <td class="tdgrey2">${SyncCatalogModify}</td>
	    </tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">����ַ������޸�/ɾ����</td>
		  <td class="tdgrey2">${SyncArticleModify}</td>
	    </tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">���ηַ�������״̬��</td>
		  <td class="tdgrey2"><z:select id="AfterSyncStatus"> ${AfterSyncStatus}</z:select></td>
	    </tr>
		<tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">�ٴηַ�������״̬��</td>
		  <td class="tdgrey2"><z:select id="AfterModifyStatus"> ${AfterModifyStatus}</z:select></td>
	    </tr>
        <tr>
		  <td class="tdgrey1"><strong>�������ã�</strong></td>
		  <td height="28" class="tdgrey1">�������ۣ�</td>
		  <td class="tdgrey2">${AllowComment}</td>
	    </tr>
        <tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">��ֹ�������ۣ�</td>
		  <td class="tdgrey2">${CommentAnonymous}</td>
	    </tr>
        <tr>
		  <td class="tdgrey1">&nbsp;</td>
		  <td height="28" class="tdgrey1">��������ˣ�</td>
		  <td class="tdgrey2">${CommentVerify}</td>
	    </tr>
	</table>
    </form>
	</div>
</z:init>
</body>
</html>
