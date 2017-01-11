<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>���۹���</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function del(CID){
	if(CID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�������ۣ�");
		return;
	}
	Dialog.confirm("��ȷ��Ҫɾ��������?",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.Comment.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}
			});
		});
	})
}

function changeVerifyStatus(){
	DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
	DataList.loadData("dg1");
}

function changeCatalogList(){
	if(isIE){
		$("SelectCatalogID").listURL="DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType");
	}else{
		$("SelectCatalogID").setAttribute("listURL","DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType"));
	}
	Selector.setValueEx("SelectCatalogID",'','');
	$S("CatalogID","");
	DataList.setParam("dg1","CatalogID","");
	DataList.setParam("dg1","CatalogType",$V("CatalogType"));
	DataList.loadData("dg1");
}

function setCatalogID(){
	if($V("SelectCatalogID")!=0){
		$S("CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogType",$V("CatalogType"));
		DataList.loadData("dg1");
	}
}

function reloadList(){
	DataList.loadData("dg1");
}

function Pass(CID){
	if(CID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��˵����ۣ�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","Pass");
	Server.sendRequest("com.nswt.cms.dataservice.Comment.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function NoPass(CID){
	if(CID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��˵����ۣ�");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","NoPass");
	Server.sendRequest("com.nswt.cms.dataservice.Comment.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

var selectFlag = false;
function selectAll(){
	if(selectFlag){
		selectFlag = false;
	}else{
		selectFlag = true;
	}
	var arr = $N("CommentID");
	for(var i=0;i<arr.length;i++){
		arr[i].checked = selectFlag;
	}
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> ���۹���</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    <z:tbutton onClick="selectAll()"><img src="../Icons/icon018a11.gif" />ȫѡ</z:tbutton>
                    <z:tbutton onClick="Pass()"><img src="../Icons/icon018a2.gif" />���ͨ��</z:tbutton>
                    <z:tbutton onClick="NoPass()"><img src="../Icons/icon018a1.gif" />��˲�ͨ��</z:tbutton>
                    <z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton>
                    &nbsp;&nbsp;<font color="#999999">ѡ����Ŀ����</font>
                    <z:select id="CatalogType" style="width:60px;" onChange="changeCatalogList();">
                        <span value="1">�ĵ�</span>
                        <span value="4">ͼƬ</span>
                        <span value="5">��Ƶ</span>
                        <span value="6">��Ƶ</span>
                        <span value="7">����</span>
                    </z:select>
                    &nbsp;<font color="#999999">ѡ����Ŀ</font>
                    <z:select id="SelectCatalogID" style="width:120px" onChange="setCatalogID()" listWidth="200" listHeight="300" listURL="DataService/CommentCatalogSelector.jsp?Type=1"> </z:select>
                     &nbsp;<font color="#999999">���״̬</font>
                    <z:select id="VerifyStatus" style="width:60px;" onChange="changeVerifyStatus();">
                        <span value="X">δ���</span>
                        <span value="Y">��ͨ��</span>
                        <span value="N">δͨ��</span>
                        <span value="All">ȫ��</span>
                    </z:select>
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/>
                    </td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<z:datalist id="dg1" method="com.nswt.cms.dataservice.Comment.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
                                <td width="8%" align="right"><input type="checkbox" name="CommentID" value="${ID}" /></td>
								<td width="65%"><a href="${PreLink}" title="${Name}" target="_blank"><b>${Name}</b></a>&nbsp;&nbsp;
                                ����ID: ${RelaID}</td>
                                <td width="27%" align="right" style=" font-weight:normal;">
                                <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Pass(${ID})">���ͨ��</a>
                                <img src="../Icons/icon404a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="NoPass(${ID})">��˲�ͨ��</a>
                                <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="del(${ID})">ɾ��</a>
                                </td>
							</tr>
                            <tr>
                            	<td align="right">���۱��⣺</td>
                                <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;" height="20"><span title="${Title}">${Title}</span></td>
                                <td rowspan="2" height="120">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td width="41%" align="right" style=" border-bottom:none;">�����ߣ�</td>
                                        <td width="59%" style=" border-bottom:none;">${AddUser}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">������Ŀ��</td>
                                        <td style=" border-bottom:none;"><span title="${CatalogName}">${CatalogName}</span></td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">IP��</td>
                                        <td style=" border-bottom:none;">${AddUserIP}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">����ʱ�䣺</td>
                                        <td style=" border-bottom:none;">${AddTime}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">���״̬��</td>
                                        <td style=" border-bottom:none;"><span title="${VerifyFlagName}" style="color:${FlagColor}">${VerifyFlagName}</span></td>
                                    </tr>
                                </table>
                                </td>
                            </tr>
                            <tr>
                              <td align="right">���ݣ�</td>
                              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal" height="100">
                              <span title="${Content}">${Content}</span>
                              </td>
                            </tr>
						</table>
				  </z:datalist>
                  <z:pagebar target="dg1" />
                  </td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
	</body>
</html>
