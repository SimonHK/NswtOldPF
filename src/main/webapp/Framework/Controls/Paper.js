/**
 * ��ֽ����������
 * nswt)wangzhaohui
 **/

/*************************һЩ���÷���������****************************/
if(window.CONTEXTPATH&&window.$E){//�����ҳ��ͬʱ������Main.js����ʹ��Main.js�еĹ��÷���
	var ieVer = isIE ? parseInt(agt.split(";")[1].replace(/(^\s*)|(\s*$)/g,"").split(" ")[1]) : 0;
	var ielt7 = isIE && ieVer<7;
	var $id=$;
	var getPosition=$E.getPosition;
	var hasClassName=$E.hasClassName;
	var addClassName=$E.addClassName;
	var removeClassName=$E.removeClassName;
}else{//���ҳ��û�е���Main.js�������¶��幫�÷���
	var agt =   window.navigator.userAgent;
	var isIE = agt.toLowerCase().indexOf("msie") != -1;
	var isGecko = agt.toLowerCase().indexOf("gecko") != -1;
	var ieVer = isIE ? parseInt(agt.split(";")[1].replace(/(^\s*)|(\s*$)/g,"").split(" ")[1]) : 0;
	if(!!window.XDomainRequest&&!!document.documentMode)ieVer=8;//��ie8����ģʽ��userAgent�᷵��'MSIE 7'
	var ielt7 = isIE && ieVer<7;

	function $id(id){
		return typeof(id) == 'string'?document.getElementById(id):id;
	}
	function getEventPosition(evt){
		var evt=window.event||evt
		var pos = {};
		pos.x = evt.clientX+document.body.scrollLeft+document.documentElement.scrollLeft;
		pos.y = evt.clientY+document.body.scrollTop+document.documentElement.scrollTop;
		return pos;
	}
	function stopEvent(evt){//��ֹһ���¼�ִ��,���������Ĭ�ϵ��¼�
		evt = window.event||evt;
		if(!evt)
			return;
		if(isGecko){
			evt.preventDefault();
			evt.stopPropagation();
		}
		evt.cancelBubble = true
		evt.returnValue = false;
	}

	function getPosition(ele){
		ele = $id(ele);
		var doc = ele.ownerDocument;
		if(ele.parentNode===null||ele.style.display=='none')
			return false;
		var parent = null;
		var pos = [];
		var box;
		if(ele.getBoundingClientRect){//IE,FF3,���ܾ�ȷ������û�зǳ�ȷ������Ķ�λ
			box = ele.getBoundingClientRect();
			var scrollTop = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
			var scrollLeft = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
			var X = box.left + scrollLeft - doc.documentElement.clientLeft;
			var Y = box.top + scrollTop - doc.documentElement.clientTop;
			if(isIE){
				X--;
				Y--;
			}
			return {x:X, y:Y};
		}
	  if (ele.parentNode) {
		parent = ele.parentNode;
	  }else {
		parent = null;
	  }
	  while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML'){
		pos[0] -= parent.scrollLeft;
		pos[1] -= parent.scrollTop;
		if (parent.parentNode){
		  parent = parent.parentNode;
		}else{
		  parent = null;
		}
	  }
	  return {x:pos[0],y:pos[1]}
	}

	function hasClassName(strClassName,oElm){
		return (new RegExp(("(^|\\s)" + strClassName + "(\\s|$)"), "i").test(oElm.className));
	}
	function addClassName(strClassName,oElm){
		var strCurrentClass = oElm.className;
		if(!new RegExp(strClassName, "i").test(strCurrentClass)){
			oElm.className = strCurrentClass + ((strCurrentClass.length > 0)? " " : "") + strClassName;
		}
	}
	function removeClassName(strClassName,oElm){
		var oClassToRemove = new RegExp((strClassName + "\s?"), "i");
		oElm.className = oElm.className.replace(oClassToRemove, "").replace(/^\s?|\s?$/g, "");
	}
	if(!isIE&&HTMLElement){
		if(!HTMLElement.prototype.attachEvent){
			window.attachEvent = document.attachEvent = HTMLElement.prototype.attachEvent = function(evtName,func){
				evtName = evtName.substring(2);
				this.addEventListener(evtName,func,false);
			}
			window.detachEvent = document.detachEvent = HTMLElement.prototype.detachEvent = function(evtName,func){
				evtName = evtName.substring(2);
				this.removeEventListener(evtName,func,false);
			}
		}
		HTMLElement.prototype.__defineGetter__("currentStyle", function(){
			return this.ownerDocument.defaultView.getComputedStyle(this,null);
		});
	}

	var JSON = {};
	JSON.toString=function(O) {
		var string = [];
		var isArray = function(a) {
			var string = [];
			for(var i=0; i< a.length; i++) string.push(JSON.toString(a[i]));
			return string.join(',');
		};
		var isObject = function(obj) {
			var string = [];
			for (var p in obj){
				if(obj.hasOwnProperty(p) && p!='prototype'){
					string.push('"'+p+'":'+JSON.toString(obj[p]));
				}
			};
			return string.join(',');
		};
		if (!O) return false;
		if (O instanceof Function) string.push(O);
		else if (O instanceof Array) string.push('['+isArray(O)+']');
		else if (typeof O == 'object') string.push('{'+isObject(O)+'}');
		//else if (typeof O == 'string') string.push('"'+O.replace(/(\/|\")/gm,'\\$1')+'"');
		else if (typeof O == 'string') string.push('"'+O+'"');
		else if (typeof O == 'number' && isFinite(O)) string.push(O);
		return string.join(',');
	}
	JSON.evaluate=function(str) {
		return (typeof str=="string")?eval('(' + str + ')'):str;
	}
}
/*************************��ֽ��****************************/

var Paper=function(id,func,hotareaData,drawable){//idΪͼƬ��id��ͼƬ����hotareaHashΪ��ʼ��ʱҪԤ�õ�������JSON�ַ������ݣ�funcΪ˫����ֽ����ʱҪ���õķ�����drawableΪ�Ƿ���ڱ�ֽ�ϻ���������
	this.init(id,func,hotareaData,drawable);
}
Paper.getInstance=function(id){
	return $id(id).PaperInstance;
};
Paper.prototype={
	init:function(id,func,hotareaData,drawable){
		var ele=$id(id);
		if(!ele)return alert('������Ԫ��'+id);
		if(ele.PaperInstance){
			document.getElementsByTagName('BODY')[0].removeChild(ele.PaperInstance.elem.parentNode);
			ele.PaperInstance=null;
		}
		ele.PaperInstance=this;
		if(drawable)this.drawable=true;
		var pos=getPosition(ele);
		var posDiv=document.createElement('div');
		posDiv.style.cssText='position:absolute;border:solid black 1px;left:'+pos.x+'px;top:'+pos.y+'px;';
		posDiv.innerHTML='<div id="_paper_'+ele.id+'" class="blankBg" style="position:relative;cursor:default;width:'+ele.width+'px;height:'+ele.height+'px;-moz-user-select:-moz-none;"></div>';
		document.getElementsByTagName('BODY')[0].appendChild(posDiv)
		this.callFunc=func;
		this.elem=$id('_paper_'+ele.id);
		this.rectArray=[];
		if(hotareaData)
			this.initFromJOSN(hotareaData);
		this.attachBehaviors()
	},
	initFromJOSN : function (hotareaData) {
		if(typeof(hotareaData)=='string')
			var hotareaData=JSON.evaluate(hotareaData);
		for(var i=0,len=hotareaData.length;i<len;i++){
			var hotarea=hotareaData[i];
			var r=new Rect();
			if(this.drawable)
				r.editable=true;
			r.container=this.elem;
			r.callFunc=this.callFunc;
			r.rectArray=this.rectArray;
			var ele=r.create(hotarea.hashData);
			if(this.drawable){
				ele.className='editableRect';
				r.addTitle(hotarea.hashData);
			}else{
				ele.className='rect';
			}
			ele.style.cssText=hotarea.css;
			r.attachBehaviors();
		}
	},
	attachBehaviors : function () {
		var self=this;
		if(this.drawable){
			this.elem.attachEvent("onmousedown", function(evt){self.onMouseDown(evt)});
		}
	},
	onMouseDown:function(evt){
		var evt=window.event||evt
		var target = evt.srcElement||evt.target;
		//console.log(target);
		if(this.elem==target){//�������ǵ��ڱ�ֽ�ϣ��������������ϣ��Ϳ�ʼ������
			var r=new Rect();
			if(this.drawable)r.editable=true;
			r.container=this.elem;
			r.callFunc=this.callFunc;
			r.rectArray=this.rectArray;
			r.start(evt);
		}
	},
	hotarea2JSON : function () {
		var tempObj=[];
		for(var i=0,len=this.rectArray.length;i<len;i++){
			if(this.rectArray[i]==0)continue;
			var r=this.rectArray[i].elem;
			tempObj.push({id:r.id,css:r.style.cssText,hashData:JSON.evaluate($id(r.id+'_hashData').value)});
		}
		var json2Str=JSON.toString(tempObj);
		//console.log(json2Str);
		return json2Str;
	}
}

/**** ������ ****/
var Rect=function(){
	this.init();
}
Rect.selected=null;

Rect.attachBehaviors = function () {
	document.attachEvent("onkeydown", Rect.onKeyDown);
};

Rect.onKeyDown = function (evt) {
	var evt=window.event||evt
	if (evt.keyCode == 46) { //Delete��
		var target = evt.srcElement||evt.target;
		//console.log(target.tagName)
		if(target.tagName=='INPUT')//���������״̬��
			return;
		if(Rect.selected)Rect.selected.remove();
	}
};

Rect.prototype={
	init:function(){
		this.rectArray = null;
		this.container=null;
		this.callFunc=null;
		this.editable=false;
	},
	start:function(evt){
		Rect.selected&&Rect.selected.blur();
		var pos=this.container.pos=getPosition(this.container);
		var evtPos=getEventPosition(evt);
		var ele=this.create();
		ele.className='editableRect';
		ele.style.left=evtPos.x-pos.x+'px';
		ele.style.top=evtPos.y-pos.y+'px';
		ele.style.height = 1 + "px";
		ele.style.width = 1 + "px";
		//mouseBeginX��mouseBeginY�Ǹ�����������¼����갴��ʱ��λ��
		ele.mouseBeginX = evtPos.x;
		ele.mouseBeginY = evtPos.y;
		ele.className='editableRect drawing';
		var self=this;
		this._fMove=function(evt){self.move(evt)}
		this._fFinish=function(evt){self.finish(evt)}
		document.attachEvent("onmousemove", this._fMove);
		document.attachEvent("onmouseup", this._fFinish);
	},
	create:function(hashData){
		this.id = this.rectArray.length + "";
		var ele=this.elem = document.createElement('div');
		ele.style.position = "absolute";
		ele.style.padding = "0";
		ele.style.zIndex = "10";
		ele.id='_rect'+this.id;
		ele.innerHTML='<input type="hidden" id="'+ele.id+'_hashData" value="{title:\'˫���༭����\'}" />';
		this.container.appendChild(ele);
		this.hashDataEle=$id(ele.id+'_hashData');
		if(hashData)
			this.hashDataEle.value=typeof(hashData)=='string'?hashData:JSON.toString(hashData);
		if(!window.RectAttachBehaviors){
			Rect.attachBehaviors();
			window.RectAttachBehaviors=true;
		}
		this.rectArray.push(this);
		return ele;
	},
	move:function(evt){
		var evt=window.event||evt
		var ele = this.elem;
		//dx��dy������ƶ��ľ���
		var dx = getEventPosition(evt).x - ele.mouseBeginX;
		var dy = getEventPosition(evt).y - ele.mouseBeginY;
		//���dx��dy <0,˵����곯���Ͻ��ƶ�����Ҫ���ر�Ĵ���
		var el=getEventPosition(evt).x-this.container.pos.x;
		var et=getEventPosition(evt).y-this.container.pos.y;
		if(dx<0&&el>0){
			ele.style.left = el+'px';
			ele.style.width = Math.abs(dx)+'px';
		}else if(dx>0&&ele.offsetLeft+dx<this.container.clientWidth){
			ele.style.width = Math.abs(dx)+'px';
		}
		if(dy<0&&et>0){
			ele.style.top = et+'px';
			ele.style.height = Math.abs(dy)+'px';
		}else if(dy>0&&ele.offsetTop+dy<this.container.clientHeight){
			ele.style.height = Math.abs(dy)+'px';
		}
	},
	finish:function(evt){
		var evt=window.event||evt
		var self=this;
		//onmouseupʱ�ͷ�onmousemove��onmouseup�¼����
		document.detachEvent("onmousemove", this._fMove);
		document.detachEvent("onmouseup", this._fFinish);
		if(this.elem.clientWidth<30&&this.elem.clientHeight<20){
			this.remove();
			return;
		}
		removeClassName('drawing',this.elem);
		this.addTitle();
		this.attachBehaviors();
		this.focus();
	},
	attachBehaviors:function(){
		var self=this;
		if(this.editable){
			this.elem.attachEvent("ondblclick", function(evt){self.callFunc(self.hashDataEle,evt)})
			this.elem.attachEvent("onmousedown", function(evt){self.focus(evt)});
			if (window.Drag)
				new Drag(this.elem,{Limit:true, mxContainer:this.container});//ע����ק����
			if (window.Resize)
				new Resize(this.elem,{ Max: true, mxContainer:this.container});//ע��Ϊ�ɵ�����С
		}else{
			this.elem.attachEvent("onclick", function(evt){self.callFunc(self.hashDataEle,evt)});
			this.elem.attachEvent("onmouseover", function(evt){self.focus(evt),self.tipOn(evt);;});
			this.elem.attachEvent("onmousemove", function(evt){self.focus(evt),self.tipMove(evt);});
			this.elem.attachEvent("onmouseout", function(evt){self.blur(evt),self.tipOff(evt);});
		}
	},
	remove:function(evt){
		var evt=window.event||evt
		this.container.removeChild(this.elem);
		Rect.selected=null;
		for(var i=0,len=this.rectArray.length;i<len;i++){
			if(this == this.rectArray[i]){
				this.rectArray[i]=0;
			}
		}
	},
	addTitle:function(){
		var ele=this.titleDiv=document.createElement('div');
		ele.className='titleArea';
		ele.innerHTML='<span id="'+this.elem.id+'_title">˫���༭����</span>';
		this.elem.appendChild(ele);
		this.titleEle=$id(this.elem.id+'_title');
		var self=this;
		if(isIE){
			this.hashDataEle.attachEvent("onpropertychange", function(evt){self.onHashDataChange(evt)})
		}else{
			this.hashDataEle.attachEvent("onDOMAttrModified", function(evt){self.onHashDataChange(evt)})
		}
			this.onHashDataChange();
	},
	onHashDataChange:function(evt){
		var evt=window.event||evt
		this.titleEle.innerHTML=JSON.evaluate(this.hashDataEle.value).title;
	},
	focus:function(){
		Rect.selected&&Rect.selected.blur();
		if(this.editable){
			this.elem.className='editableRectActive';
		}else{
			this.elem.className='rectHover';
		}
		Rect.selected=this;
	},
	blur:function(){
		if(this.editable){
			this.elem.className='editableRect';
		}else{
			this.elem.className='rect';
		}
		Rect.selected=null;
	},
	tipOn:function(evt){
		var evt=window.event||evt;
		var tiper=this.tiper=$id('_paper_tip');
		if(!tiper){
			tiper=this.tiper=document.createElement('div');
			tiper.id='_paper_tip';
			tiper.style.position='absolute';
			document.getElementsByTagName('BODY')[0].appendChild(tiper)
		}
		tiper.style.display='block';
		tiper.innerHTML=JSON.evaluate(this.hashDataEle.value).title;
		this.tipMove(evt);
	},
	tipMove:function(evt){
		if(!this.tiper)return;
		var evt=window.event||evt;
		var evtPos=getEventPosition(evt);
		this.tiper.style.left=evtPos.x+10+"px"
		this.tiper.style.top=evtPos.y+"px"
	},
	tipOff:function(evt){
		$id('_paper_tip').style.display="none";
	}
}

/**** ��ק�� ****/
var Drag=function(drag, options){
	this.init(drag, options)
}
Drag.prototype = {
  //�ϷŶ���
  init: function(drag, options) {
	this.Drag = $id(drag);//�ϷŶ���
	this._x = this._y = 0;//��¼�������ϷŶ����λ��
	this._marginLeft = this._marginTop = 0;//��¼margin
	var self=this;
	this._fM = function(evt){self.Move(evt)};
	this._fS = function(){self.Stop()};

	this.SetOptions(options);

	this.Limit = !!this.options.Limit;
	this.mxLeft = parseInt(this.options.mxLeft);
	this.mxRight = parseInt(this.options.mxRight);
	this.mxTop = parseInt(this.options.mxTop);
	this.mxBottom = parseInt(this.options.mxBottom);

	this.LockX = !!this.options.LockX;
	this.LockY = !!this.options.LockY;
	this.Lock = !!this.options.Lock;

	this.onStart = this.options.onStart;
	this.onMove = this.options.onMove;
	this.onStop = this.options.onStop;

	this._Handle = $id(this.options.Handle) || this.Drag;
	this._mxContainer = $id(this.options.mxContainer) || null;

	this.Drag.style.position = "absolute";
	//͸��
	if(isIE && !!this.options.Transparent){
		//����ϷŶ���
		with(this._Handle.appendChild(document.createElement("div")).style){
			width = height = "100%"; backgroundColor = "#fff"; filter = "alpha(opacity:0)"; fontSize = 0;
		}
	}
	//������Χ
	this.Repair();
	this._Handle.attachEvent("onmousedown", function(evt){self.Start(evt)})
  },
  //����Ĭ������
  SetOptions: function(options) {
	this.options = {//Ĭ��ֵ
		Handle:			"",//���ô������󣨲�������ʹ���ϷŶ���
		Limit:			false,//�Ƿ����÷�Χ����(Ϊtrueʱ�����������,�����Ǹ���)
		mxLeft:			0,//�������
		mxRight:		9999,//�ұ�����
		mxTop:			0,//�ϱ�����
		mxBottom:		9999,//�±�����
		mxContainer:	"",//ָ��������������
		LockX:			false,//�Ƿ�����ˮƽ�����Ϸ�
		LockY:			false,//�Ƿ�������ֱ�����Ϸ�
		Lock:			false,//�Ƿ�����
		Transparent:	false,//�Ƿ�͸��
		onStart:		function(){},//��ʼ�ƶ�ʱִ��
		onMove:			function(){},//�ƶ�ʱִ��
		onStop:			function(){}//�����ƶ�ʱִ��
	};
	if(options)
	for (var property in options) {
		this.options[property] = options[property];
	}
  },
  //׼���϶�
  Start: function(oEvent) {
	if(this.Lock){ return; }
	this.Repair();
	//��¼�������ϷŶ����λ��
	this._x = oEvent.clientX - this.Drag.offsetLeft;
	this._y = oEvent.clientY - this.Drag.offsetTop;
	//��¼margin
	this._marginLeft = parseInt(this.Drag.currentStyle.marginLeft) || 0;
	this._marginTop = parseInt(this.Drag.currentStyle.marginTop) || 0;
	//mousemoveʱ�ƶ� mouseupʱֹͣ
	document.attachEvent("onmousemove", this._fM)
	document.attachEvent("onmouseup", this._fS)
	if(isIE){
		//���㶪ʧ
		this._Handle.attachEvent("onlosecapture",this._fS)
		//������겶��
		this._Handle.setCapture();
	}else{
		//���㶪ʧ
		window.attachEvent("onblur", this._fS)
		//��ֹĬ�϶���
		oEvent.preventDefault();
	};
	//���ӳ���
	this.onStart();
  },
  //������Χ
  Repair: function() {
	if(this.Limit){
		//��������Χ����
		this.mxRight = Math.max(this.mxRight, this.mxLeft + this.Drag.offsetWidth);
		this.mxBottom = Math.max(this.mxBottom, this.mxTop + this.Drag.offsetHeight);
		//�����������������positionΪrelative��absolute����Ի���Զ�λ�����ڻ�ȡoffset֮ǰ����
		!this._mxContainer || this._mxContainer.currentStyle.position == "relative" || this._mxContainer.currentStyle.position == "absolute" || (this._mxContainer.style.position = "relative");
	}
  },
  //�϶�
  Move: function(oEvent) {
	//�ж��Ƿ�����
	if(this.Lock){ this.Stop(); return; };
	//���ѡ��
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	//�����ƶ�����
	var iLeft = oEvent.clientX - this._x, iTop = oEvent.clientY - this._y;
	//���÷�Χ����
	if(this.Limit){
		//���÷�Χ����
		var mxLeft = this.mxLeft, mxRight = this.mxRight, mxTop = this.mxTop, mxBottom = this.mxBottom;
		//�����������������������Χ����
		if(!!this._mxContainer){
			mxLeft = Math.max(mxLeft, 0);
			mxTop = Math.max(mxTop, 0);
			mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
			mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
		};
		//�����ƶ�����
		iLeft = Math.max(Math.min(iLeft, mxRight - this.Drag.offsetWidth), mxLeft);
		iTop = Math.max(Math.min(iTop, mxBottom - this.Drag.offsetHeight), mxTop);
	}
	//����λ�ã�������margin
	if(!this.LockX){ this.Drag.style.left = iLeft - this._marginLeft + "px"; }
	if(!this.LockY){ this.Drag.style.top = iTop - this._marginTop + "px"; }
	//���ӳ���
	this.onMove();
  },
  //ֹͣ�϶�
  Stop: function() {
	//�Ƴ��¼�
	document.detachEvent("onmousemove", this._fM);
	document.detachEvent("onmouseup", this._fS);
	if(isIE){
		//removeEventHandler(this._Handle, "losecapture", this._fS);
		this._Handle.detachEvent("onlosecapture", this._fS)
		this._Handle.releaseCapture();
	}else{
		//removeEventHandler(window, "blur", this._fS);
		window.detachEvent("onblur", this._fS)
	};
	//���ӳ���
	this.onStop();
  }
};