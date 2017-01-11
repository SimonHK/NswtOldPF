var _nswtp_d,_nswtp_s,_nswtp_c,_nswtp_l,_nswtp_t,_nswtp_s;
var _nswtp_st=new Date().getTime();
var _nswtp_stat = function(param){
	var p = {};
	if(param){
		var arr = param.split("&");
		for(var i=0;i<arr.length;i++){
			if(arr[i]){
				var arr2 = arr[i].split("=");
				if(arr2[0]){
					p[arr2[0]] = arr2[1];
				}
			}
		}
	}
	_nswtp_d = p["Dest"];
	_nswtp_s = p["SiteID"];
	_nswtp_c = p["CatalogInnerCode"];
	_nswtp_l = p["LeafID"];
	_nswtp_t = p["Type"];
	p["sr"] = screen.width+"x"+screen.height;
	p["cd"] = screen.colorDepth;
	p["fv"] = _nswtp_stat.fv();
	p["ce"] = _nswtp_stat.ce();	
	p["je"] = _nswtp_stat.je();
	p["la"] = navigator.language?navigator.language:navigator.browserLanguage;
	p["la"] = p["la"]?p["la"]:navigator.systemLanguage;
	p["cs"] = document.charset;
	
	p["vq"] = _nswtp_stat.vq();	
	p["Referer"] = _nswtp_stat.eu(document.referrer);
	p["Title"] = _nswtp_stat.eu(document.title);
	p["URL"] = _nswtp_stat.eu(location.href);
	p["Host"] = location.host;
	var dest = _nswtp_d;
	p["Dest"] = false;
	dest = dest+"?"+_nswtp_stat.mq(p);
	var s = document.createElement("script");
	s.src = dest;
	(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(s);
};

_nswtp_stat.eu =  function(str){
	return encodeURI(str).replace(/=/g,"%3D").replace(/\+/g,"%2B").replace(/\?/g,"%3F").replace(/\&/g,"%26");
}

_nswtp_stat.mq = function(map){
	var sb = [];
	for(var prop in map){
		if(map[prop]){
			sb.push(prop+"="+map[prop]);
		}
	}	
	return sb.join("&");
}

_nswtp_stat.trim = function(str){
	return str.replace(/(^\s*)|(\s*$)/g,"");
}


_nswtp_stat.je = function(){
	var je="";
	var n=navigator;
	je = n.javaEnabled()?1:0;
	return je;
} 

_nswtp_stat.fv = function(){
	var f="",n=navigator;	
	if(n.plugins && n.plugins.length){
		for(var ii=0;ii<n.plugins.length;ii++){
			if(n.plugins[ii].name.indexOf('Shockwave Flash')!=-1){
				f=n.plugins[ii].description.split('Shockwave Flash ')[1];
				break;
			}
		}
	}else if(window.ActiveXObject){
		for(var ii=10;ii>=2;ii--){
			try{
				var fl=eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash."+ii+"');");
				if(fl){
					f=ii + '.0'; break;
				}
			}catch(e){} 
		} 
	}
	return f;
}

_nswtp_stat.ce = function(){
	var c_en = (navigator.cookieEnabled)? 1 : 0;
	return c_en;
}

_nswtp_stat.vq = function(){
  var cs = document.cookie.split("; ");
  var name = _nswtp_s+"_vq";
  var vq = 1;
  for(i=0; i<cs.length; i++){
	  var arr = cs[i].split("=");
	  var n = _nswtp_stat.trim(arr[0]);
	  var v = arr[1]?_nswtp_stat.trim(arr[1]):"";
	  if(n==name){
	  	vq = parseInt(v)+1;
	  	break;
	  }
	}
	var expires = new Date(new Date().getTime()+365*10*24*60*60*1000).toGMTString();
	var cv = name+"="+vq+";expires="+expires+";path=/;";
	document.cookie = cv;
	return vq;
}

function _nswtp_bu(){
	if(_nswtp_d){ 
		var p = {};
		p["Event"] = "Unload";
		p["LeafID"] = _nswtp_l;
		p["SiteID"] = _nswtp_s;
		p["CatalogInnerCode"] = _nswtp_c;
		if(_nswtp_c&&!_nswtp_l){
	  	//p["Trace"] = pos.join(";");//will implement in 2.0
			p["Type"] = _nswtp_t;
		}
		var t = new Date().getTime();
		if(t-_nswtp_lt>30000){
			_nswtp_nt += (t-_nswtp_lt);
		}
		p["StickTime"] = (t-_nswtp_st-_nswtp_nt)/1000;
		var dest = _nswtp_d+"?"+_nswtp_stat.mq(p);
		var s = document.createElement("script");
		s.src = dest;
		(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(s);
	}
}

var _nswtp_lt = new Date().getTime();
var _nswtp_lt_ka = new Date().getTime();
var _nswtp_nt = 0;
function _nswtp_ka(){
	var t = new Date().getTime();
	if(t-_nswtp_lt_ka>60000){
		_nswtp_lt_ka = t;
		var p = {};
		p["Event"] = "KeepAlive";
		p["SiteID"] = _nswtp_s;
		var dest = _nswtp_d+"?"+_nswtp_stat.mq(p);
		var s = document.createElement("script");
		s.src = dest;
		(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(s);
	}
	if(t-_nswtp_lt>60000){
		_nswtp_nt += (t-_nswtp_lt);
	}
	_nswtp_lt = t;
}

var pos = [];
function _nswtp_cr(evt){
	var x = evt.clientX, y=evt.clientY;
	var src = evt.srcElement;
	if(!src){
		var node = evt.target;
    while(node&&node.nodeType!=1)node=node.parentNode;
    src =  node;
	}
	var win;
	if(src.ownerDocument.defaultView){
		win = src.ownerDocument.defaultView;
	}else{
		win = src.ownerDocument.parentWindow;
	}
	x += Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
	y += Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
	pos.push([x,y]);
}

if(window.attachEvent){
	window.attachEvent("onbeforeunload",_nswtp_bu);
	window.attachEvent("onclick",_nswtp_ka);
	window.attachEvent("onkeydown",_nswtp_ka);
	window.attachEvent("onmousemove",_nswtp_ka);
	window.attachEvent("onscroll",_nswtp_ka);
}else if(window.addEventListener){
	window.addEventListener('beforeunload',_nswtp_bu,false);
	window.addEventListener("click",_nswtp_ka,false);
	window.addEventListener("click",_nswtp_cr,false);
	window.addEventListener("keydown",_nswtp_ka,false);
	window.addEventListener("mousemove",_nswtp_ka,false);
	window.addEventListener("scroll",_nswtp_ka,false);
}