<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		var v = new Verify();
		v.add($("Type"),"���","NotNull");
		v.add($("Name"),"����","NotNull");
		v.add($("Value"),"������ֵ","NotNull&&Length<100");
		if(!v.doVerify()){
			return;
		}
		dr.set("Type",$V("Type"));
		dr.set("Name",$V("Name"));
		dr.set("Value",$V("Value"));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 440;
	diag.Title = "�½�Web�ɼ�����";
	diag.URL = "DataChannel/FromWebDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.ShowButtonRow = true;
	window.EditFlag = false;
	diag.show();
}

function edit(tr){
	var dr;
	if(!tr){
		var dt = DataGrid.getSelectedData("dg1");
		if(dt.getRowCount()==0){
			Dialog.alert("����ѡ��Ҫ�޸ĵ���!");
			return;
		}
		dr = dt.getDataRow(0);
	}else{
		dr = $("dg1").DataSource.Rows[tr.rowIndex-1];
	}
	var xml = dr.get("ConfigXML");
	xml = xml.substr(xml.indexOf('\n')+1);
	var doc = toXMLDOM(xml);
	var nodes = doc.firstChild.childNodes;
	var len = nodes.length;
	var map = {};
	var j = 1,m=1;
	for(var i=0;i<len;i++){
		var ele = nodes[i];
		var name = ele.nodeName;
		if (name=="config") {
			map[ele.getAttribute("key")] = ele.getAttribute("value");
		}
		if (name=="urls") {
			map["URL" + ele.getAttribute("level")] = ele.firstChild.nodeValue;
		}
		if (name=="filterExpr") {
			map["FilterExpr"] = ele.firstChild.nodeValue;
		}
		if (name=="script") {
			map["ScriptLang"] = ele.getAttribute("language");
			map["Script"] = ele.firstChild.nodeValue;
		}
		if (name=="template") {
			map["RefCode" + j] = ele.getAttribute("code");
			map["Template" + j] = ele.firstChild.nodeValue;
			j++;
		}
		if (name=="filterBlock") {
			map["FilterBlock" + m] = ele.firstChild.nodeValue;
			m++;
		}
	}
	map["ID"] = dr.get("ID");
	map["Name"] = dr.get("Name");
	map["Intro"] = dr.get("Intro");
	map["Type"] = dr.get("Type");
	map["Status"] = dr.get("Status");
	
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 440;
	diag.Title = "�޸�Web�ɼ�����";
	diag.URL = "DataChannel/FromWebDialog.jsp?ID="+dr.get("ID");
	window.Map = map;
	window.EditFlag = true;
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var w = $DW.Tab.getChildTab("Info").contentWindow;
	var dc = w.Form.getData("form2");
	$DW.Tab.onChildTabClick("Info");
	if(w.Verify.hasError()){
		return;
	}
	
	if(dc.get("Type")=="D"){
		w = $DW.Tab.getChildTab("Template").contentWindow;
		$DW.Tab.onChildTabClick("Template");
		if(w.Verify.hasError()){
			return;
		}
		var arr = w.$N("RefCode");
		var str;
		for(var i=0;i<arr.length;i++){
			var ele = arr[i];
			var id = ele.id.replace("RefCode","Template");
			str = null;
			if(w.$(id).getText){
				str = w.$(id).getText();
			}
			if(str==null||str.trim()==""){
				if(!isIE){
					Dialog.hideAllFlash(w);
				}
				Dialog.alert("ƥ�������Ϊ�գ�",function(){
					if(!isIE){
						Dialog.showAllFlash(w);
					}
				});
				return;
			}
			dc.add(ele.id,$V(ele));
			dc.add(id,str);
		}
		w = $DW.Tab.getChildTab("Filter").contentWindow;
		$DW.Tab.onChildTabClick("Filter");
		arr = w.$T("Table");
		for(var i=0;i<arr.length;i++){
			var ele = arr[i];
			var id = ele.id.replace("Table","FilterBlock");
			str = null;
			if(w.$(id)&&w.$(id).getText){
				str = w.$(id).getText();
			}
			if(str==null||str.trim()==""){
				continue;
			}
			dc.add(id,str);
		}
	}
	//�ű�����Ϊ��
	/*w = $DW.Tab.getChildTab("Script").contentWindow;
	if(w.$("ScriptEditor").getText){
		var str = w.$("ScriptEditor").getText();
		if(str==null||str.trim()==""){
			Dialog.alert("�ű�����Ϊ�գ�");
			$DW.Tab.onChildTabClick("Config");
			return;		
		}
	}
	dc.add("Script",str);
	*/
	dc.add("Lang",w.$NV("Lang"));
	Server.sendRequest("com.nswt.datachannel.FromWeb.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message);
			$D.close();
			DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	var dc = new DataCollection();	
	dc.add("IDs",arr.join());
	Dialog.confirm("ȷ��Ҫɾ����������",function(){
		Server.sendRequest("com.nswt.datachannel.FromWeb.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
	});
} 

function delResult(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ��ղɼ����ݵ��У�");
		return;
	}
	Dialog.confirm("�ɼ�������ָ��ָ��URL���ص��ı��ļ���<br>ͼƬ�ļ�,<font color=red>�������Ѿ�ת����Ŀ������</font>��<br><br>ȷ��Ҫ��ո�����Ĳɼ�������",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.datachannel.FromWeb.delResult",dc,function(response){
			Dialog.alert(response.Message);
		});											  
	},null,350);
} 

function save(){
	Dialog.alert("���ܱ���!");
}

function execute(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���У�");
		return;
	}
	var taskID;
	Dialog.confirm("ȷ��Ҫִ�и�������",function(){
		var dc = new DataCollection();	
		dc.add("ID",arr[0]);
		Server.sendRequest("com.nswt.datachannel.FromWeb.execute",dc,function(response){
			if(response.Status==1){
				taskID = response.get("TaskID");
				var p = new Progress(taskID,"����ץȡWeb�ļ�...",700,150);
				p.show();
			}else{
				Dialog.alert(response.Message);	
			}
		});
	});
}

function cancel(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫ��ִֹ�и�������",function(){
		var dc = new DataCollection();	
		dc.add("ID",arr[0]);
		Server.sendRequest("com.nswt.datachannel.FromWeb.cancel",dc,function(response){
			Dialog.alert(response.Message);
		});
	});
}

function dealData(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���У�");
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",arr[0]);
	Server.sendRequest("com.nswt.datachannel.FromWeb.dealData",dc,function(response){
		if(response.Status==1){
			taskID = response.get("TaskID");
			var p = new Progress(taskID,"���ڴ���ɼ�����Web�ļ�...",700,150);
			p.show();
		}else{
			Dialog.alert(response.Message);	
		}
	});
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon007a10.gif" width="20" height="20" /> Web�ɼ������б�</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon007a2.gif" width="20" height="20" />�½�</z:tbutton> 
				<z:tbutton onClick="edit()"><img src="../Icons/icon007a4.gif" width="20" height="20" />�޸�</z:tbutton> 
				<z:tbutton onClick="del()"><img src="../Icons/icon007a3.gif" width="20" height="20" />ɾ��</z:tbutton> 
				<z:tbutton onClick="delResult()"><img src="../Icons/icon007a17.gif" width="20" height="20" />��ղɼ�����</z:tbutton>
				<z:tbutton onClick="dealData()"><img src="../Icons/icon026a8.gif" width="20" height="20" />��������</z:tbutton> 
				<z:tbutton onClick="execute()"><img src="../Icons/icon403a12.gif" width="20" height="20" />ִ������</z:tbutton> 
				<z:tbutton onClick="cancel()"><img src="../Icons/icon404a3.gif" width="20" height="20" />��ִֹ��</z:tbutton></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.nswt.datachannel.FromWeb.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="4%" ztype="RowNo"><b>���</b></td>
							<td width="3%" ztype="selector" field="ID">&nbsp;</td>
							<td width="4%"><b>ID</b></td>
							<td width="25%"><b>����</b></td>
							<td width="8%"><b>״̬</b></td>
							<td width="9%"><b>�ɼ����</b></td>
							<td width="35%"><b>��ʼ��ַ</b></td>
							<td width="12%"><b>�߳���</b></td>
						</tr>
						<tr onDblClick='edit(this);' style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${ID}</td>
							<td>${Name}</td>
							<td>${StatusName}</td>
							<td>${TypeName}</td>
							<td>${StartURL}</td>
							<td>${ThreadCount}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
