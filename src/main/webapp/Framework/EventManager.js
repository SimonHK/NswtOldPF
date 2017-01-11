EventManager = function(){
    var DOC = document,
        WINDOW = window,
		specialElCache = [];

  function getId(el) {
     var id = false,
     i = 0,
     len = specialElCache.length,
     id = false,
     skip = false,
     o;
     if (el) {
         if (el.getElementById || el.navigator) {
             for (; i < len; ++i) {
                 o = specialElCache[i];
                 if (o.el === el) {
                     id = o.id;
                     break;
                 }
             }
             if (!id) {
                 id = "guid_"+(++ nswt.idSeed);
				 el.id=id;//给window或document添加id，兼容性待测试
                 specialElCache.push({
                     id: id,
                     el: el
                 });
                 skip = true;
             }
         } else {
             id = $(el).id;
         }
         if (!nswt.elCache[id]) {
             Core.addToCache(el, id);
             if (skip) {
                 nswt.elCache[id].skipGC = true;
             }
         }
     }
     return id;
  };

    function addListener(el, ename, fn, task, wrap, scope){
        el = $(el);
        var id = getId(el),
            es = nswt.elCache[id].events,
            wfn;
        //wfn = Lib.Event.on(el, ename, wrap);
		el.attachEvent(ename, wrap);
        wfn = wrap;
        es[ename] = es[ename] || [];
        
        es[ename].push([fn, wrap, scope, wfn, task]);
        
        if(el == DOC && ename == "onmousedown"){
            EventManager.stoppedMouseDownEvent.addListener(wrap);
        }
    };

    function createDelayed(h, o, fn){
        return function(e){
			var e=getEvent(e);
            var task = new Util.DelayedTask(h);
            if(!fn.tasks) {
                fn.tasks = [];
            }
            fn.tasks.push(task);
            task.delay(o.delay || 10, h, null, [e]);
        };
    };

    function listen(element, ename, opt, fn, scope){
        var o = (!opt || typeof opt == "boolean") ? {} : opt,
            el = $(element), task;

        fn = fn || o.fn;
        scope = scope || o.scope;

        if(!el){
            throw "Error listening for \"" + ename + '\". Element "' + element + '" doesn\'t exist.';
        }
        function h(e){
            
            //e = EventObject.setEvent(e);
            var t = e.srcElement;
            if (o.stopEvent) {
				if(isGecko){
					e.preventDefault();
					e.stopPropagation();
				}
				e.cancelBubble = true;
				e.returnValue = false;
            }
            if (o.preventDefault) {
				if(isGecko){
					e.preventDefault();
				}
				e.returnValue = false;
            }
            if (o.stopPropagation) {
				if(isGecko){
					e.stopPropagation();
				}
				e.cancelBubble = true;
            }
            fn.call(scope || el, e, t, o);
        };
        if(o.delay){
            h = createDelayed(h, o, fn);
        }
        addListener(el, ename, fn, task, h, scope);
        return h;
    };

    var pub = {
        
        addListener : function(element, eventName, fn, scope, options){
                listen(element, eventName, options, fn, scope);
        },

        
        removeListener : function(el, eventName, fn, scope){
            el = $(el);
            var id = getId(el),
                f = el && (nswt.elCache[id].events)[eventName] || [],
                wrap, i, l, k, len, fnc;

            for (i = 0, len = f.length; i < len; i++) {

                
                if (isArray(fnc = f[i]) && fnc[0] == fn && (!scope || fnc[2] == scope)) {
                    if(fnc[4]) {
                        fnc[4].cancel();
                    }
                    k = fn.tasks && fn.tasks.length;
                    if(k) {
                        while(k--) {
                            fn.tasks[k].cancel();
                        }
                        delete fn.tasks;
                    }
                    wrap = fnc[1];
                    //Lib.Event.un(el, eventName, fnc[3] || wrap);
					el.detachEvent(eventName, fnc[3] || wrap);
                    
                    if(wrap && el == DOC && eventName == "onmousedown"){
                        EventManager.stoppedMouseDownEvent.removeListener(wrap);
                    }

                    f.splice(i, 1);
                    if (f.length === 0) {
                        delete nswt.elCache[id].events[eventName];
                    }
                    for (k in nswt.elCache[id].events) {
                        return false;
                    }
                    nswt.elCache[id].events = {};
                    return false;
                }
            }
        },

        
        removeAll : function(el){
            el = $(el);
            var id = getId(el),
                ec = nswt.elCache[id] || {},
                es = ec.events || {},
                f, i, len, ename, fn, k, wrap;

            for(ename in es){
                if(es.hasOwnProperty(ename)){
                    f = es[ename];
                    
                    for (i = 0, len = f.length; i < len; i++) {
                        fn = f[i];
                        if(fn[4]) {
                            fn[4].cancel();
                        }
                        if(fn[0].tasks && (k = fn[0].tasks.length)) {
                            while(k--) {
                                fn[0].tasks[k].cancel();
                            }
                            delete fn.tasks;
                        }
                        wrap =  fn[1];
                        //Lib.Event.un(el, ename, fn[3] || wrap);
						el.detachEvent(ename, fn[3] || wrap);
                        
                        if(wrap && el == DOC &&  ename == "onmousedown"){
                            EventManager.stoppedMouseDownEvent.removeListener(wrap);
                        }
                    }
                }
            }
            if (nswt.elCache[id]) {
                nswt.elCache[id].events = {};
            }
        },

        getListeners : function(el, eventName) {
            el = $(el);
            var id = getId(el),
                ec = nswt.elCache[id] || {},
                es = ec.events || {},
                results = [];
            if (es && es[eventName]) {
                return es[eventName];
            } else {
                return null;
            }
        },

        purgeElement : function(el, recurse, eventName) {
            el = $(el);
            var id = getId(el),
                ec = nswt.elCache[id] || {},
                es = ec.events || {},
                i, f, len;
            if (eventName) {
                if (es && es.hasOwnProperty(eventName)) {
                    f = es[eventName];
                    for (i = 0, len = f.length; i < len; i++) {
                        EventManager.removeListener(el, eventName, f[i][0]);
                    }
                }
            } else {
                EventManager.removeAll(el);
            }
            if (recurse && el && el.childNodes) {
                for (i = 0, len = el.childNodes.length; i < len; i++) {
                    EventManager.purgeElement(el.childNodes[i], recurse, eventName);
                }
            }
        },

        _unload : function() {
            var el;
            for (el in nswt.elCache) {
                EventManager.removeAll(el);
            }
            nswt.elCache={};
        }
        
    };
     
    pub.on = pub.addListener;
    
    pub.un = pub.removeListener;
	
	window.attachEvent('onunload',pub._unload);//清除nswt.elCache中存储的事件，释放内存
    pub.stoppedMouseDownEvent = new Util.Event();
    return pub;
}();

extra(EventManager, function(){
   var resizeEvent,
       resizeTask,
       curWidth = 0,
       curHeight = 0;
   return {
       doResizeEvent:function(){
           var view=$E.getViewport(),
		       h=view.height,
			   w=view.width;
            if(curHeight != h || curWidth != w){//修正ie6下body尺寸改变会触发window的resize事件的问题
               resizeEvent.fire(curWidth = w, curHeight = h);
            }
       },
	   onWindowResize : function(fn, scope, options){
           if(!resizeEvent){
               resizeEvent = new Util.Event();
               resizeTask = new Util.DelayedTask(this.doResizeEvent);
               EventManager.on(window, "onresize", this.fireWindowResize, this);
           }
           resizeEvent.addListener(fn, scope, options);
       },
       fireWindowResize : function(){
           if(resizeEvent){
               resizeTask.delay(100);
           }
       },
       removeResizeListener : function(fn, scope){
           if(resizeEvent){
               resizeEvent.removeListener(fn, scope);
           }
       }
	}
}());



/***
ScreenEventManager
协调在当前屏幕内的事件，使mousedown mousemove等事件跨iframe传递。
典型应用如：在有menu控件处于显示状态时，在当前屏幕内任意一个iframe内点击，都应该去调用menu组件的onMouseDown方法，以隐藏menu控件。
**/

ScreenEventManager = function(){
	var pub = {
		screenEvents : {
			onmousedown: new Util.Event(this,'onmousedown')//可注册为全屏的事件，可视需要自行添加。
		},
        addListener : function(eventName, fn, scope, options){
			pub.screenEvents[eventName].addListener(fn, scope, options);
        },
        removeListener : function(eventName, fn, scope){
			pub.screenEvents[eventName].removeListener(fn, scope);
        },
		removeAll : function(){
		 	  for(var ename in pub.screenEvents){
				  pub.screenEvents[ename].clearListeners();
			  }

		 },
		 fire:function(eventName,evt){
			 pub.screenEvents[eventName].fire(evt);
		},
		_unload : function() {
            removeAll();
        }
    };
    pub.on = pub.addListener;
    pub.un = pub.removeListener;
    return pub;
}();
if(window!=nswt.rootWindow){
	ScreenEventManager=nswt.rootWindow.ScreenEventManager;
}
for(ename in ScreenEventManager.screenEvents){
	document.attachEvent(ename,function(evt){ScreenEventManager.fire(ename,evt)});
}
