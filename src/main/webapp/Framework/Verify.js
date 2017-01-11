var Verify = {};

//Select.js��DateTime.js�Զ����ع���
Verify.autoCloseOther = function(evt,ele){
	if(!ele){
		evt = getEvent(evt);
		ele = $(evt.srcElement);
	}
	var ztype = ele.$A("ztype");
	if(ztype){
		ztype = ztype.toLowerCase();
	}
	if(ztype!="select"){
		Selector.close();
	}
	if(ztype!="date"){
		Calendar.close();
	}
	if(ztype!="time"){
		TimeSelector.close();
	}
}

Verify.check = function(evt,ele){//evt,ele����ֻ��һ��
	if(!ele){
		evt = getEvent(evt);
		ele = $(evt.srcElement);
	}
	var v = ele.$A("verify");
	if(!v){//verify���Կ����б䶯
		Verify.closeTip(ele);
		return;
	}
	var condition = ele.$A("condition");
	if(condition&&!eval(condition)){
		Verify.closeTip(ele);
		return;
	}
	var msg = [];

	var anyFlag = false;
	var Features = v.split("\&\&");		
	var value = $V(ele);
	if(ele.$A("ztype")&&ele.$A("ztype").toLowerCase()=="select"){
		value = $V(ele.parentElement);
	}
	if(value){
		value = (""+value).trim();
	}
	for(var i = 0; i < Features.length; i++) {
		var arr = Features[i].split("\|");
		var name = "";
		var rule;
		if(arr.length==2){
			name = arr[0];
			rule = arr[1];
		}else{
			rule = Features[i];
		}
		var op = "=";
		if(rule.indexOf("=")<0){
			if(rule.indexOf('>') > 0) {
				op = ">";
			}else if (rule.indexOf('<') > 0) {
				op = "<";
			}		
		}else{
			if(rule.charAt(rule.indexOf("=")-1)=='>') {
				op = ">=";
			}else if(rule.charAt(rule.indexOf("=")-1)=='<') {
				op = "<=";
			}		
		}
		var fName = null;
		var fValue = null;
		if(rule.indexOf(op)>0) {
			fName = rule.substring(0,rule.indexOf(op));
			fValue = rule.substring(rule.indexOf(op)+1);
		}else{
			fName = rule;
		}
		if(fName=="Any") {
			anyFlag = true;
		}else if (fName=="Regex") {
			fValue = rule.substring(6);
			if (value==null||value==""||!fValue) {continue;}
			var reg = fValue;
			if(!reg.startWith("^")){
				reg = "^"+reg;
			}
			if(!reg.endWith("$")){
				reg += "$";
			}
			if(!new RegExp(reg).test(value)){
				msg.push(name);
			}
		}else if (fName=="Script") {
			if (!fValue) {continue;}
			if(!eval(fValue)){
				msg.push(name);
			}
		}else if (fName=="NotNull") {
			if (value==null||value=="") {
				if(ele.$A("ztype")&&ele.$A("ztype").toLowerCase()=="select"){
					if(ele.value.length==0){//������ʱ�Ѿ����ַ��˵�valueֵΪ��
						msg.push("����ѡ��"+name);
					}
				}else{
					msg.push(name+"����Ϊ��");
				}
			}
		}else if (fName=="Number") {
			if (value==null||value=="") {continue;}
			if(!isNumber(value)){
				msg.push(name+"����������");
			}
		}else if (fName=="Time") {
			if (value==null||value=="") {continue;}
			if(!isTime(value)){
					msg.push(name+"��ֵ"+value+"������ȷ��ʱ��!");
			}
		}else if (fName=="Int") {
			if (value==null||value=="") {continue;}
			if(!isInt(value)){
				msg.push(name+"����������");
			}
		}else if (fName=="Date") {
			if (value==null||value=="") {continue;}
			if(!isDate(value)){
				msg.push(name+"��������ȷ������");
			}
		}else if (fName=="DateTime") {
			if (value==null||value=="") {continue;}
			if(!isDateTime(value)){
				msg.push(name+"��������ȷ������");
			}
		}else if(fName=="Email") {
			if (value==null||value=="") {continue;}
			var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if(value&&value.match(pattern)==null){
			  msg.push(name+"������ȷ�ĵ��������ַ");
			}
		}else if(fName=="Length") {
			if (value==null||value=="") {continue;}
			if(isNaN(fValue)) {
				msg.push("У��������Length�������������");
			}else{
				try{
					var len = parseInt(fValue);
					if(op=="="&&value.length!=len) {
					  msg.push(name+"���ȱ�����" + len + "���ַ���");
					}else if (op==">"&&value.length<=len) {
						msg.push(name+"���ȱ������" + len  + "���ַ���");
					}else if (op=="<"&& value.length>=len) {
						msg.push(name+"���ȱ���С��" + len  + "���ַ���");
					}
				} catch (ex) {
					msg.push("У��������Length�������������"+ex.message);
				}
			}
		}
	}
	if(!anyFlag&&ele.$A("ztype")&&ele.$A("ztype").toLowerCase()=="select"&&ele.parentNode.input){
		if(!ele.parentNode.$A("listURL")&&!Selector.verifyInput(ele)){
			msg.push("�����ֵ���ǿ�ѡ��");
		}
	}
		
	if(msg.length>0){
		var txt = msg.join("<br>");
		if(txt!=ele._VerifyMsg){
			Verify.closeTip(ele);
			var tip;
			var afterEle = ele.$A("element");
			if(afterEle){
				tip = Tip.show($(afterEle),txt);
			}else{
				tip = Tip.show(ele,txt);
			}
			
			ele._VerifyTip = tip;
			ele._VerifyMsg = txt;
		}
	}else{
		Verify.closeTip(ele);
	}
}

Verify.closeTip = function(ele,evt){
	if(!ele){
		evt = getEvent(evt);
		ele = $(evt.srcElement);
	}
	if(ele.type == "blur"){
		ele = $(ele.srcElement);
	}
	if(ele._VerifyTip){
		ele._VerifyTip.close();
		ele._VerifyTip = null;
		ele._VerifyMsg = null;
	}
}

Verify.hasError = function(noCheckArr,ele){
	var arr;
	if(ele){
		ele = $(ele);
		arr =  ele.$T("input").concat(ele.$T("textarea"));
	}else{
		arr = $T("input").concat($T("textarea"));
	}
	var firstEle = false;
	for(var i=0;i<arr.length;i++){
		var c = $(arr[i]);
		var id = c.id;
		if(c.$A("ztype")=="select"){
			id = c.parentElement.id;
		}
		var flag = false;
		if(noCheckArr){
			for(var j=0;j<noCheckArr.length;j++){
				if(id==$(noCheckArr[j]).id){
					flag = true;
				}
			}
		}
		if(flag){
			Verify.closeTip(c);
			continue;
		}
		Verify.check(null,c);
		if(!firstEle&&c._VerifyTip){
			firstEle = c;
		}
	}
	if(firstEle){
		Dialog.alert("����δ��ȷ��д����������ʾ�޸�!",function(){
			$(firstEle).focusEx();	
		});
		return true;
	}
	return false;
}

Verify.initCtrl = function(ele){
	ele = $(ele);
	ele.attachEvent("onfocus",Verify.autoCloseOther);
	var v = ele.$A("verify");
	if(v){
		ele.attachEvent("onfocus",Verify.check);
		ele.attachEvent("onkeyup",Verify.check);
		ele.attachEvent("onchange",Verify.check);
		ele.attachEvent("onblur",Verify.closeTip);
		var condition = ele.$A("condition");
		if(v.indexOf("NotNull")>=0&&!condition){
			var ztype = ele.$A("ztype");
			if(ztype){
				ztype = ztype.toLowerCase();
			}
			if(ztype=="select"){
				ele = ele.getParent("div");
			}
			if(ztype=="date"||ztype=="time"){
				ele = ele.nextSibling;
			}
			if(!ele.nextSibling||!ele.nextSibling.getAttribute||ele.nextSibling.getAttribute("ztype")!="Verify"){
				var display = '';
				if(!$E.visible(ele)){
					display = 'display:none';
				}
				ele.insertAdjacentHTML("afterEnd","<span style='color:red;padding-left:2px;padding-top:13px;"+display+"' ztype='Verify'>*</span>");
			}
		}
	}
}

window.setInterval(function(){
	var arr = $T("input").concat($T("textarea"));
	arr.each(function(ele){
		ele = $(ele);
		if(ele && ele._VerifyTip){//IE�´��ں�����δִ���굫���ڼ��رյ����������ele�����п���Ϊ��
			Verify.check(null,ele);
		}
	});
},500);