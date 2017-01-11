
var $E = {};

$E.getTopLevelWindow = function(){
	var pw = window;
	while(pw!=pw.parent){
		if(!pw.parent.nswt){
			return pw;
		}
		pw = pw.parent;
	}
	return pw;
}
nswt.rootWindow=$E.getTopLevelWindow();
nswt.rootDocument=nswt.rootWindow.document;

$E.$A = function(attr,ele) {
	ele = $(ele) || this;
return ele.getAttribute?ele.getAttribute(attr):null;
}

$E.$T = function(tagName,ele){
	ele = $(ele) || this;
	return window.$T(tagName,ele);
}

$E.visible = function(ele) {
	ele = $(ele) || this;
	if(ele.style.display=="none"){
		return false;
	}
  return true;
}

$E.toggle = function(ele) {
	ele = $(ele) || this;
$E[$E.visible(ele) ? 'hide' : 'show'](ele);
}

$E.toString = function(flag,index,ele) {//flag��ʾ�Ƿ���ʾ��������
	ele = ele || this;
	var arr = [];
	var i = 0;
	for(var prop in ele){
		if(!index||i>=index){
			var v = null;
			try{v = ele[prop];}catch(ex){}//gecko�¿��ܻᱨ��
			if(!flag){
				if(typeof(v)=="function"){
					v = "function()";
				}else if((""+v).length>100){
					v = (""+v).substring(0,100)+"...";
				}
			}
			arr.push(prop+":"+v);
		}
		i++;
	}
  return arr.join("\n");
}

$E.focusEx = function(ele) {
	ele = $(ele) || this;
try{
  	ele.focus();
	}catch(ex){}
}

$E.addListener=function(ele, eventName, fn, scope, options){
	if(typeof(eventName)!='string'){//����ڶ���������Ϊ�ַ��ͣ�����Ϊ��ʡ����ele����������Ĳ���˳��ǰ��
		options=scope;
		scope=fn;
		fn=eventName;
		eventName=ele;
		ele=this;
	}else{
		ele = $(ele);
	}
	EventManager.on(ele, eventName, fn, scope || ele, options);
}
$E.removeListener=function(ele, eventName, fn, scope){
	if(typeof(eventName)!='string'){//����ڶ���������Ϊ�ַ��ͣ�����Ϊ��ʡ����ele����������Ĳ���˳��ǰ��
		options=scope;
		scope=fn;
		fn=eventName;
		eventName=ele;
		ele=this;
	}else{
		ele = $(ele);
	}
	EventManager.removeListener(ele, eventName, fn, scope || ele);
}
$E.on = $E.addListener;
$E.un = $E.removeListener;

$E.data = function(key, value) {
	ele = $(this);
     if (!ele) {
         return null
     }
     var c = nswt.elCache[ele.id].data;
     if (arguments.length == 1) {
         return c[key]
     } else {
         return (c[key] = value)
     }
}

$E.getForm = function(ele) {
	ele = $(ele) || this;
if(isIE){
		ele = ele.parentElement;
	}else if(isGecko){
		ele = ele.parentNode;
	}
	if(!ele){
		return null;
	}
	if(ele.tagName.toLowerCase()=="form"){
		return ele
	}else{
		return $E.GetForm(ele);
	}
}

$E.hide = function(ele) {
	ele = $(ele)||this;
	if(ele.tagName.toLowerCase()=="input"&&ele.type=="button"){
		if(ele.parentElement&&ele.parentElement.getAttribute("ztype")=="zInputBtnWrapper"){
			ele.parentElement.style.display = 'none';
		}
	}
	if(ele.currentStyle&&ele.currentStyle.display!='none'){
		ele._display=ele.currentStyle.display;
	}else{
		switch(ele.tagName.toLowerCase()){
		  case "table":
		  case "tr":
		  case "td":
		  case "th":
		  case "button":
		  case "textarea":
		  case "input":
		  case "select":
			ele._display = '';
		    break;
		  default :
			ele._display = 'block';
		}
	}
	ele.style.display = 'none';
}
$E.show = function(ele) {
	ele = $(ele)||this;
	if(ele.tagName.toLowerCase()=="input"&&ele.type=="button"){
		if(ele.parentElement&&ele.parentElement.getAttribute("ztype")=="zInputBtnWrapper"){
			ele.parentElement.style.display = '';
		}
	}
   ele.style.display = ele._display?ele._display:'';
}
$E.unselectable = function(ele) {
	ele = $(ele) || this;
	ele.unselectable = "on";
	ele.onselectstart = function(){return false};
	if(isGecko){ele.style['-moz-user-select']='none'};
}
$E.disable = function(ele) {
	ele = $(ele) || this;
if(ele.tagName.toLowerCase()=="form"){
		var elements = ele.elements;
		for (var i = 0; i < elements.length; i++) {
		  var element = elements[i];
		  ele.blur();
		  if(ele.hasClassName("z-btn-intoolbar")){
			  ele.addClassName("z-btn-disabled");
			  if(ele.onclick){
				 ele.onclickbak = ele.onclick;
			  }
			  ele.onclick=null;
			  ele._disabled = true;
		  }else{
			  ele.disabled = 'true';
		  }
		}
	}else{
		if(ele.$A("ztype")&&ele.$A("ztype").toLowerCase()=="select"){
			Selector.setDisabled(ele,true);
		}else if(ele.hasClassName("z-btn-intoolbar")){
			ele.addClassName("z-btn-disabled");
			if(ele.onclick){
				ele.onclickbak = ele.onclick;
			}
			ele.onclick=null;
			ele._disabled = true;
		}else if(ele.hasClassName("zPushBtn")){//���ݾɵ���ʽ����2.0����Ǩ�ƺ�ɾ��
			ele.addClassName("zPushBtnDisabled");
			if(ele.onclick){
				ele.onclickbak = ele.onclick;
			}
			ele.onclick=null;
		}else{
				ele.addClassName("disabled");
	    	ele.disabled = 'true';
		}
	}
}

$E.enable = function(ele) {
	ele = $(ele) || this;
if(ele.tagName.toLowerCase()=="form"){
		var elements = ele.elements;
	    for (var i = 0; i < elements.length; i++) {
	      var element = elements[i];
			if(ele.hasClassName("z-btn-disabled")){
				ele.removeClassName("z-btn-disabled");
				if(ele.onclickbak){
					ele.onclick = ele.onclickbak;
				}
			}else{
		    	ele.disabled = '';
			}
	    }
	}else{
		if(ele.$A("ztype")&&ele.$A("ztype").toLowerCase()=="select"){
			Selector.setDisabled(ele,false);
		}else if(ele.hasClassName("z-btn-disabled")){
			ele.removeClassName("z-btn-disabled");
			if(ele.onclickbak){
				ele.onclick = ele.onclickbak;
			}
		}else if(ele.hasClassName("zPushBtnDisabled")){//���ݾɵ���ʽ����2.0����Ǩ�ƺ�ɾ��
			ele.className="zPushBtn";
			if(ele.onclickbak){
				ele.onclick = ele.onclickbak;
			}
		}else{
			if(ele.hasClassName("disabled")){
				ele.removeClassName("disabled");
			}
	    ele.disabled = '';
		}
	}
}

$E.scrollTo = function(ele) {
	ele = $(ele) || this;
var x = ele.x ? ele.x : ele.offsetLeft,
      y = ele.y ? ele.y : ele.offsetTop;
  window.scrollTo(x, y);
}
$E.getOuterW=function(ele){//�õ�padding+border ��ռ���
	ele = $(ele) || this;
	var curStyle=ele.currentStyle;
	ele.outerW =(parseInt(curStyle.borderLeftWidth,	 10)||0)
				+(parseInt(curStyle.borderRightWidth,10)||0)
				+(parseInt(curStyle.paddingLeft,     10)||0)
				+(parseInt(curStyle.paddingRight,    10)||0);
	return ele.outerW;
}
$E.getOuterH=function(ele){//�õ�padding+border ��ռ�߶�
	ele = $(ele) || this;
	var curStyle=ele.currentStyle;
	if(!ele.currentStyle)console.log(location.pathname,ele)
        ele.outerH=(parseInt(curStyle.borderTopWidth, 10)||0)
				+(parseInt(curStyle.borderBottomWidth,10)||0)
				+(parseInt(curStyle.paddingTop,       10)||0)
				+(parseInt(curStyle.paddingBottom,    10)||0);
	return ele.outerH;
}
$E.getContentSize = function(ele){
	ele = $(ele) || this;
	var dim = $E.getDimensions(ele);
	return {width:dim.width - $E.getOuterW(ele), height:dim.height - $E.getOuterH(ele)};
}

$E.getDimensions = function(ele){
	ele = $(ele) || this;
	var dim;
  if((/^(head|meta|script|link|area|param)$/i).test(ele.tagName)){
  	dim = {width:0,height:0};
  }else if ($E.visible(ele)){
		if(isIE && ele.offsetWidth ==0 && ele.offsetHeight ==0){
			var curStyle=ele.currentStyle;
			if(isBorderBox){
				dim = {width: curStyle.pixelWidth, height: curStyle.pixelHeight};
			}else{
				dim = {width:+curStyle.pixelWidth+$E.getOuterW(ele),
					 height:+curStyle.pixelHeight+$E.getOuterH(ele)
				};
			}
		}else{
			dim = {width: ele.offsetWidth, height: ele.offsetHeight};
		}
  }else{
	  var style = ele.style;
	  var vis = style.visibility;
	  var pos = style.position;
	  var dis = style.display;
	  style.visibility = 'hidden';
	  style.position = 'absolute';
	  switch(ele.tagName.toLowerCase()){
		  case "table":
			style.display = 'table';
		    break;
		  case "tr":
			style.display = 'table-row';
		    break;
		  case "td":
		  case "th":
			style.display = 'table-cell';
		    break;
		  case "button":
		  case "textarea":
		  case "input":
		  case "select":
			style.display = 'inline-block';
		    break;
		  default :
			style.display = 'block';
	  }
	  var w = ele.offsetWidth;
	  var h = ele.offsetHeight;
	  style.display = dis;
	  style.position = pos;
	  style.visibility = vis;
	  dim = {width: w, height: h};
	}
	return dim;
}
$E.getViewport = function (win) {
	var win = win || window,
		doc = win.document,
		viewportWidth,
		viewportHeight;
	if(isIE){
		viewportWidth=isQuirks?doc.body.clientWidth :doc.documentElement.clientWidth ;
		viewportHeight=isQuirks?doc.body.clientHeight :doc.documentElement.clientHeight;
	}else{
		viewportWidth=win.innerWidth;
		viewportHeight=win.innerHeight;
	}
	return {width:viewportWidth, height:viewportHeight};
}
/**
����Ԫ��λ��
�������ݵļ��ַ�ʽsetXY([x,y]),setXY(x,y),setXY([x,y],element),setXY(x,y,element)
��x��y����Ϊfalse��nullʱ���򲻽����޸�
**/
$E.setXY = function(x,y,ele){
    if(isArray(x)){
        if(y!==undefined){
            ele=y;
        }
        y=x[1];
        x=x[0]
    }
	ele = $(ele) || this;
	if(x !== false && x !== null){
        ele.style.left = x+'px';
        ele.left = x;
	}
	if(y !== false && y !== null){
        ele.style.top = y+'px';
        ele.top = y;
	}
    return ele;
}

$E.getPosition = function(ele){
	ele = $(ele) || this;
var doc = ele.ownerDocument;
  if(ele.parentNode===null||ele.style.display=='none'){
    return false;
  }
  var parent = null;
  var pos = [];
  var box;
  if(ele.getBoundingClientRect){//IE,FF3
    box = ele.getBoundingClientRect();
    var scrollTop = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
    var scrollLeft = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
    var X = box.left + scrollLeft - doc.documentElement.clientLeft-doc.body.clientLeft;
    var Y = box.top + scrollTop - doc.documentElement.clientTop-doc.body.clientTop;
    return {x:X, y:Y};
  }else if(doc.getBoxObjectFor){ // FF2
    box = doc.getBoxObjectFor(ele);
    var borderLeft = (ele.style.borderLeftWidth)?parseInt(ele.style.borderLeftWidth):0;
    var borderTop = (ele.style.borderTopWidth)?parseInt(ele.style.borderTopWidth):0;
    pos = [box.x - borderLeft, box.y - borderTop];
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

$E.getPositionEx = function(ele){
	ele = $(ele) || this;
	var pos = $E.getPosition(ele);
	var win = window;
	var sw,sh;
	while(win!=win.parent){
		if(win.frameElement&&win.parent.nswt){
			pos2 = $E.getPosition(win.frameElement);
			pos.x += pos2.x;
			pos.y += pos2.y;
		}
		sw = Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		sh = Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		pos.x -= sw;
		pos.y -= sh;
		if(!win.parent.nswt){
			break;
		}
		win = win.parent;
	}
	return pos;
}

$E.getParent = function(tagName,ele){
	ele = $(ele) || this;
	while(ele){
		if(ele.tagName.toLowerCase()==tagName.toLowerCase()){
			return $(ele);
		}
		ele = ele.parentElement;
	}
	return null;
}

$E.getParentByAttr = function(attrName,attrValue,ele){
	ele = $(ele) || this;
	while(ele&&ele.nodeType){
		if(ele.getAttribute(attrName)==attrValue){
			return $(ele);
		}
		ele = ele.parentElement;
	}
	return null;
}

$E.nextElement = function(ele){
	ele = $(ele) || this;
var x = ele.nextSibling;
	while (x&&x.nodeType!=1){
		x = x.nextSibling;
	}
	return $(x);
}

$E.previousElement = function(ele){
	ele = $(ele) || this;
var x = ele.previousSibling;
	while (x&&x.nodeType!=1){
		x = x.previousSibling;
	}
	return $(x);
}


$E.hasClassName = function(className,ele){
	ele = $(ele) || this;
return (new RegExp(("(^|\\s)" + className + "(\\s|$)"), "i").test(ele.className));
}

$E.addClassName = function(className,ele){
	ele = $(ele) || this;
var currentClass = ele.className;
    currentClass = currentClass?currentClass:"";
    if(!new RegExp(("(^|\\s)" + className + "(\\s|$)"), "i").test(currentClass)){
        ele.className = currentClass + ((currentClass.length > 0)? " " : "") + className;
    }
    return ele.className;
}

$E.removeClassName = function(className,ele){
	ele = $(ele) || this;
var classToRemove = new RegExp(("(^|\\s)" + className + "(?=\\s|$)"), "i");
  ele.className = ele.className.replace(classToRemove, "").replace(/^\s+|\s+$/g, "");
  return ele.className;
}
$E.getElementsByClassName=function(className,tagName,ele) {
	ele = ele?$(ele):(this.tagName?this:document);
	var children = ele.getElementsByTagName(tagName||'*');
  var elements = [];
  for (var i = 0; i < children.length; i++) {
	if($E.hasClassName(className,children[i])){
        elements.push(children[i]);
    }
  }
  return elements;
}
/*
����p1(x1/y1)��p2(x2/y2)��p1��p2�����Ϸ�(Ҳ���غ�)������һ����ʼ���꣬
ʹ��Ԫ��ele(��Ϊw,��Ϊh)�ܹ�ȫ����p1֮�ϣ�����p2֮�£����Ҿ�������ʾele��ȫ��
flag="all"��ʾele�ܹ���ʾ��x1�����߻���x2������
flag="left"��ʾele�ܹ���ʾ��x1����߻���x2�����
flag="right"��ʾele�ܹ���ʾ��x1���ұ߻���x2���ұ�
�Ҽ��˵������ڿؼ���������ؼ���Ҫ�������
*/
$E.computePosition = function(x1,y1,x2,y2,flag,w,h,win){
	var doc = win?win.document:document;
	var cw = isQuirks?doc.body.clientWidth:doc.documentElement.clientWidth;
	var ch = isQuirks?doc.body.clientHeight:doc.documentElement.clientHeight;
	var sw = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
	var sh = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
	if(!flag||flag.toLowerCase()=="all"){
		//�ȿ���p2
		if(y2-sh+h-ch<0){
			if(x2-sw+w-cw<0){//��P2����չ������
				return {x:x2,y:y2};
			}else{//����չ��
				return {x:x2-w,y:y2};
			}
		}
		//����p1
		if(x1-sw+w-cw<0){//��P1����չ������
			return {x:x1,y:y1-h};
		}else{//����չ��
			return {x:x1-w,y:y1-h};
		}
	}else	if(flag.toLowerCase()=="right"){
		//�ȿ���p2
		if(y2-sh+h-ch<0){
			if(x2-sw+w-cw<0){//��P2����չ������

				return {x:x2,y:y2};
			}
		}
		//����p1
		return {x:x1,y:y1-h};
	}else if(flag.toLowerCase()=="left"){
		//�ȿ���p2
		if(y2-sh+h-ch<0){
			if(x2-sw-w>0){//��P2����չ������
				return {x:x2,y:y2};
			}
		}
		//����p1
		return {x:x1-w,y:y1-h};
	}
}
/**
  ��ÿؼ����ҳ��Ԫ�ػ����귽�������,����߶�δ��,������ʾ�ؼ����ٵ��ø÷�����λ.
  ele {Element|Array} Ŀ��Ԫ��,��һ�����η���[x,y,width,height]
  dir {String} ê׼λ��,��ѡֵ��l, t, r, b���,��lt,rb
  rdir {String} ˮƽ��ֱ��ת,��ѡֵ��v,h,u,d,l,r,��vu��ʾ��ֱ���Ϸ�ת,hrˮƽ��ת
  offset {Array} ��λ���ƫ�Ƹ���ֵ, ���㷽ʽ:[new x + off[0], new y + off[1]]
  reanchor {Boolean} �Ƿ����������ӷ�Χ��
  moveto {Boolean} �Ƿ���λ��Ӧ�õ��ؼ���
  ����ֵ {Array} [new x, new y]
**/
$E.anchorPos = function(ele, dir, rdir, off, rean, move, me){
	ele=$(ele);
    if(ele.tagName){
      var bxy = $E.getPosition(ele), bsz = $E.getDimensions(ele);
      ele = [bxy.x, bxy.y, bsz.width, bsz.height];
    }
    var me = $(me) || this,
		sz = $E.getDimensions(me),
        w  = sz.width, h  = sz.height,
        bx = ele[0], by = ele[1],
        bw = ele[2], bh = ele[3],
        nx, ny;

      nx = dir.charAt(0) === 'l' ? bx - w : bx + bw;
      ny = dir.charAt(1) === 't' ? by - h : by + bh;

      if(rdir){
        if(rdir.charAt(0) === 'h'){
          nx = rdir.charAt(1) === 'l' ? nx - w : nx + w;
        }else ny = rdir.charAt(1) === 'u' ? ny - h : ny + h;
      }

      if(off){
        nx += off[0];
        ny += off[1];
      }
      //reanchor into view
      if(rean){
        //this��ele�Ƿ��غ�(�Խ��жϷ���)
        var vp = $E.getViewport(),
            vh = vp.height, vw = vp.width;
        if(nx < 0){
          nx = 0;
          if(by+bh>ny && by<ny+h)
            ny = by - h;
        }

        if(nx + w > vw)
          nx = by+bh>ny && by<ny+h ? bx - w : vw - w;

        if(ny < 0)
          ny = bx+bw>nx && bx<nx+w ? by+bh : 0;

        if(ny + h > vh)
          ny = bx+bw>nx && bx<nx+w ? by - h : vh - h;
      }

      if(move){
        me.setXY(nx,ny)
	  }

      return [nx, ny];
  }
/*
ͨ������html��ʽ�ַ�������Element����
$E.createElementByHtml('<div id="div1"></div>')
$E.createElementByHtml('<dl><dt>tt</dt><dd>dd</dd></dl>')
$E.createElementByHtml('This is a TextNode')
*/
$E.createElementByHtml=function(sHtml){
    var quickExpr = /^(<[\w\W]+>)$/;
    var rsingleTag = /^<(\w+)\s*\/?>(?:<\/\1>)?$/;

    var rhtml = /<|&#?\w+;/;
	var elem;
    var match = quickExpr.exec(sHtml);
    if ( match && match[1] ) {
		var ret = rsingleTag.exec( sHtml );
		if ( ret ) {
			elem = document.createElement( ret[1] ) ;
		} else {
			var tempDiv = document.createElement("div");
			tempDiv.innerHTML = sHtml ;
			elem = tempDiv.firstChild;
		}
    }else if( !rhtml.test( sHtml ) ) {
		elem = document.createTextNode( sHtml );
	}else{
		alert(['createElementByHtml������������', sHtml, elem]);
	}
	if ( elem&&elem.nodeType ) {
	        return elem;
	} else {
		alert(['createElementByHtml������������', sHtml, elem]);
	}
};
$E.removeNode=function(ele){
	ele=$(ele)||this;
	if(isIE && !isIE8){
        if (ele.tagName != "BODY") {
        	if(window.EventManager)
        		EventManager.removeAll(ele)
	        var RycDiv = document.getElementById("_RycDiv");
	        if (!RycDiv) {
	            RycDiv = document.createElement("div");
	            RycDiv.id = "_RycDiv";
	        }
	        RycDiv.appendChild(ele);
	        RycDiv.innerHTML = "";
			RycDiv=null;
			delete nswt.elCache[ele.id]
        }
	}else{
    	if(window.EventManager)
    		EventManager.removeAll(ele)
	    if (ele.parentNode && ele.tagName != "BODY") {
	        ele.parentNode.removeChild(ele)
			delete nswt.elCache[ele.id]
	    }
	}
}

var ExtendElement={}
extra(ExtendElement,{//���¸�����Ϊ$E�����ռ��¿��Ը��Ӹ�domԪ�صķ���
	$A:$E.$A,
	$T:$E.$T,
	visible:$E.visible,
	toggle:$E.toggle,
	focusEx:$E.focusEx,
	addListener:$E.addListener,
	removeListener:$E.removeListener,
	on:$E.addListener,
	un:$E.removeListener,
	data:$E.data,
	removeNode:$E.removeNode,
	getForm:$E.getForm,
	hide:$E.hide,
	show:$E.show,
	unselectable:$E.unselectable,
	disable:$E.disable,
	enable:$E.enable,
	scrollTo:$E.scrollTo,
	getDimensions:$E.getDimensions,
	getSize:$E.getDimensions,
	getOuterW:$E.getOuterW,
	getOuterH:$E.getOuterH,
	setXY:$E.setXY,
	getPosition:$E.getPosition,
	getPositionEx:$E.getPositionEx,
	getParent:$E.getParent,
	getParentByAttr:$E.getParentByAttr,
	nextElement:$E.nextElement,
	previousElement:$E.previousElement,
	prevElement:$E.previousElement,
	hasClassName:$E.hasClassName,
	addClassName:$E.addClassName,
	removeClassName:$E.removeClassName,
	getElementsByClassName:$E.getElementsByClassName,
	getAlignToXY:$E.getAlignToXY,
	getAnchorXY:$E.getAnchorXY,
	anchorPos:$E.anchorPos
})