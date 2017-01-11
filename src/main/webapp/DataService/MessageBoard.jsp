<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ա��Ϣ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function add(param){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 220;
	diag.Title = "�½����԰�";
	diag.URL = "DataService/MessageBoardDialog.jsp?RelaCatalogID="+param[1];
	diag.OKEvent = editSave;
	diag.show();
}

function edit(param){
	if(!param||param.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ����԰壡");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 220;
	diag.Title = "���԰��޸�";
	diag.URL = "DataService/MessageBoardDialog.jsp?ID="+param[0]+ "&RelaCatalogID="+param[1];
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("IsOpen",$DW.$NV("IsOpen"));
	Server.sendRequest("com.nswt.cms.dataservice.MessageBoard.save",dc,function(){
		var response = Server.getResponse();
		if(response.Status==1){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","boardID",currentTreeItem.getAttribute("boardID"))});
				}else{
					Tree.loadData("tree1");
				}
				DataList.loadData('dg1');
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function delBoard(param){
	if(!param||param.length==0){
		Dialog.alert("����ѡ��Ҫɾ�������԰壡");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ��ѡ�е����԰���",function(){
		var dc = new DataCollection();	
		dc.add("IDs",param[0]);
		Server.sendRequest("com.nswt.cms.dataservice.MessageBoard.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				Tree.loadData("tree1",function(){Tree.select("tree1","boardID",currentTreeItem.getAttribute("boardID"))});
				DataList.loadData("dg1");
			}
		});
	});
}

var currentTreeItem;
function onTreeClick(ele){
	currentTreeItem = ele;
	var boardID =  ele.getAttribute("boardID");
	DataList.setParam("dg1","BoardID",boardID);
	DataList.loadData("dg1");
}

function doSearch(){
	DataList.setParam("dg1",Constant.PageIndex,0);
	DataList.setParam("dg1","PublishFlag",$V("PublishFlag"));
	DataList.setParam("dg1","ReplyFlag",$V("ReplyFlag"));
	DataList.loadData("dg1");
}

function showMenu(event,ele){
	var boardID = ele.getAttribute("boardID")?ele.getAttribute("boardID"):"0";
	var rela = ele.getAttribute("rela");
	if(!boardID){
		return ;
	}
	Tree.selectNode(ele,true);
	var menu = new Menu();
	var param = [];
	param.push(boardID);
	param.push(rela);
	menu.setEvent(event);
	menu.setParam(param);
	if(!rela){
		menu.addItem("����",add,"Icons/icon034a17.gif");
	}
	menu.addItem("�޸�",edit,"Icons/icon034a6.gif");
	menu.addItem("ɾ��",delBoard,"Icons/icon034a18.gif");
	menu.show();
}

function doReply(MsgID){
	if(MsgID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	if($V("ReplyArea_"+MsgID).trim()==""){
		Dialog.alert("����д�ظ�����");
		return;
	}
	var dc = new DataCollection();
	dc.add("MsgID",MsgID);
	dc.add("ReplyContent",$V("ReplyArea_"+MsgID));
	Server.sendRequest("com.nswt.cms.dataservice.MessageBoard.saveReply",dc,function(){
		var response = Server.getResponse();
		if(response.Status==1){
			DataList.loadData('dg1');
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function editReply(MsgID){
	if(MsgID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	$("ReplyContentArea_"+MsgID).style.display="none";
	$("ReplyDivArea_"+MsgID).style.display="";
	$("DO_"+MsgID).style.display="none";
}

function delReply(MsgID){
	if(MsgID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	if(!MsgID||MsgID==""){
		Dialog.alert("����ѡ��Ҫɾ���Ļظ���");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ���ûظ���",function(){
		var dc = new DataCollection();	
		dc.add("MsgID",MsgID);
		Server.sendRequest("com.nswt.cms.dataservice.MessageBoard.delReply",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				DataList.loadData("dg1");
			}
		});
	});
} 

function delMsg(MsgID){
	if(MsgID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	var arr = new Array();
	if(!MsgID||MsgID==""){
		arr = $NV("BoardMessageID");
	}else{
		arr[0] = MsgID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�������ԣ�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ��ѡ�е�������",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.MessageBoard.delMessage",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataList.loadData("dg1");
			}
		});
	});
} 

function doCheck(MsgID){
	if(MsgID===0){
		Dialog.alert("û�м�¼!");
		return;
	}
	var arr = new Array();
	if(!MsgID||MsgID==""){
		arr = $NV("BoardMessageID");
	}else{
		arr[0] = MsgID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��˵����ԣ�");
		return;
	}
	var dc = new DataCollection();	
	dc.add("IDs",arr.join(","));
	Server.sendRequest("com.nswt.cms.dataservice.MessageBoard.doCheck",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			DataList.loadData('dg1');
		}
	});
}

var selectFlag = false;
function selectAll(){
	if(selectFlag){
		selectFlag = false;
	}else{
		selectFlag = true;
	}
	var arr = $N("BoardMessageID");
	for(var i=0;i<arr.length;i++){
		arr[i].checked = selectFlag;
	}
}
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td width="10%"><table width="100" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      <tr>
        <td style="padding:6px;" class="blockTd"><z:tree id="tree1"
														 style="height:450px;width:100px;"
														 method="com.nswt.cms.dataservice.MessageBoard.treeDataBind" level="2"
														 lazy="true">
          <p boardid='${ID}' rela='${RelaCatalogID}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
        </z:tree></td>
      </tr>
    </table></td>
    <td width="90%"><table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon034a12.gif" />�����б�</td>
      </tr>
      <tr>
        <td style="padding:8px 10px;"><div style="float: right"> �ظ�״̬��
          <z:select id="ReplyFlag" name="ReplyFlag" style="width:80px" listWidth="80" onChange="doSearch();">
                    <select name="select">
                      <option value=""></option>
                      <option value="N">δ�ظ�</option>
                      <option value="Y">�ѻظ�</option>
                    </select>
                  </z:select>
          &nbsp;&nbsp;
          ���״̬��
          <z:select id="PublishFlag" name="PublishFlag" style="width:80px" listWidth="80" onChange="doSearch();">
                                    <select name="select2">
                                      <option value=""></option>
                                      <option value="N">�ȴ����</option>
                                      <option value="Y">���ͨ��</option>
                                    </select>
                                  </z:select>
        </div>
              <z:tbutton onClick="selectAll()"><img src="../Icons/icon034a11.gif" />ȫѡ</z:tbutton>
              <z:tbutton onClick="delMsg()"><img src="../Icons/icon034a3.gif" />ɾ��</z:tbutton>
              <z:tbutton onClick="doCheck()"><img src="../Icons/icon404a4.gif"/>���ͨ��</z:tbutton>
              <z:tbutton onClick="add([0,0])"><img src="../Icons/icon034a17.gif" />�½����԰�</z:tbutton>
        </td>
      </tr>
      <tr>
        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"><z:datalist id="dg1" method="com.nswt.cms.dataservice.MessageBoard.MessageDataBind" size="10">
          <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
            <tr ztype="head" class="dataTableHead">
              <td width="7%" align="right"><input type="checkbox" name="BoardMessageID" value="${ID}" /></td>
              <td width="63%"><b>${Title}</b></td>
              <td width="30%" align="right" style=" font-weight:normal;"><img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="doCheck(${ID})">���ͨ��</a> <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="delMsg(${ID})">ɾ��</a> </td>
            </tr>
            <tr>
              <td align="right">���ݣ�</td>
              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;">${Content}</td>
              <td rowspan="2" height="140"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="41%" align="right" style=" border-bottom:none;">�����ˣ�</td>
                  <td width="59%" style=" border-bottom:none;">${AddUser}</td>
                </tr>
                <tr>
                  <td style=" border-bottom:none;" align="right">E-Mail��</td>
                  <td style=" border-bottom:none;"><a href="mailto:${EMail}">${Email}</a></td>
                </tr>
                <tr>
                  <td style=" border-bottom:none;" align="right">QQ��</td>
                  <td style=" border-bottom:none;"><a href="tencent://message/?uin=${QQ}&Menu=yes">${QQ}</a></td>
                </tr>
                <tr>
                  <td style=" border-bottom:none;" align="right">IP��</td>
                  <td style=" border-bottom:none;">${IP}</td>
                </tr>
                <tr>
                  <td style=" border-bottom:none;" align="right">����ʱ�䣺</td>
                  <td style=" border-bottom:none;">${AddTime}</td>
                </tr>
                <tr>
                  <td style=" border-bottom:none;" align="right">���״̬��</td>
                  <td style=" border-bottom:none;">${PublishFlagName}</td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td align="right">�ظ���</td>
              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;"><div id="ReplyContentArea_${ID}" style="display:${ReplyContentAreaDisplay};white-space:normal">${ReplyContent}</div>
                      <div id="ReplyDivArea_${ID}" style="display:${ReplyAreaDisplay}">
                        <textarea id="ReplyArea_${ID}" name="ReplyArea_${ID}" style="height:50px;" cols="65">${ReplyContent}</textarea>
                        <img src="../Images/doreply.gif" width="60" height="45" style="cursor:pointer" onClick="doReply(${ID});"/></div>
                ${Do} </td>
            </tr>
          </table>
        </z:datalist>
              <z:pagebar target="dg1" />
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
