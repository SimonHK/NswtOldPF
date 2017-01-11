/**************************************************
简单下拉菜单：(待完善后将与contextmenu合并)
**************************************************/

DropMenu = function(config){
    config = config || {};
    extra(this, config,{
	    ztype:'menu',
		id:"comp_menu"+(++ nswt.idSeed),
        items: [],
	    minWidth : 120,
	    shadow : 'sides',
	    defaultAlign : 'lb',
		reAlign:'hr',
	    defaultOffsets: [0, 0],
	    floating: true,
        zIndex: 15000,
	    hidden : true,
	    rendered : false,
		hideParent : false,
		container : nswt.rootDocument.body,
		showOnRootWindow : true
    });
	/**继承自定义事件机制**/
	Observable.call(this);
	this.addEvents('beforeshow','show','beforehide','hide','beforerender','render','afterrender','destroy');

    this.init();
	if(this.renderTo){
        this.render(this.renderTo);
        delete this.renderTo;
    }
};
DropMenu.prototype={
    init : function(){
		Menu.MenuMgr.register(this);
        if(this.floating){
            EventManager.onWindowResize(this.hide, this);
        }

        var items = this.items;
        var me=this;
        items.each(function(item){
            me.add(item);
        })
        delete this.items;
    },
    initItems : function(){
        if(!this.items){
            this.items = [];
        }
    },
    add : function(comp){
        comp.render(this.el);
        this.items.push(comp);
        return comp;
    },
    render :function(container, position){
        if(!this.rendered){
            this.rendered = true;
            this.onRender(this.container, position || null);
            this.fireEvent('render', this);
            this.afterRender(this.container);
        }
    },
    onRender : function(container, position){
        if(!container){
            container=document.body
        }
		this.ownerDocument=container.ownerDocument;
		this.ownerWindow=container.ownerDocument.parentWindow;
		if(this.ownerWindow==nswt.rootWindow){
			this.showOnRootWindow=true;
		}else{
			this.showOnRootWindow=false;
		}
        var thelayer= new Layer({
			cls:'z-menu',
            shadow: this.shadow,
            parentEl: container,
            zindex: this.zIndex
        });
        this.el = this.ownerWindow.$(thelayer.dom);
		this.el.on('onclick',this.onClick,this);
    },
	afterRender : function(){
    },
	/**
	以下onclick方法简单实现菜单点击后隐藏，
	如果需要多级菜单功能，或菜单内包含可输入文本框，则需要在onClick方法作出更多的处理
	**/
    onClick : function(e){
        var t = this.ownerWindow.getEvent(e).srcElement;
		t=$E.getParent('A',t);
        if(t){
            if(t.hasClassName('z-btn')){
                if(t.hasClassName('z-btn-haschildmenu') && this.ignoreParentClicks){
                    t.expandMenu();
                    e.preventDefault();
                }else if(t.hasClassName('z-btn-inmenu')){
                    this.hide();
                }
            }
        }
    },
    show : function(el, pos, parentMenu){
        this.parentMenu = parentMenu;
        if(!this.el){
            this.render();
            this.doLayout(false, true);
        }
		if(this.showOnRootWindow){
			var elXYinRootWin=$(el).getPositionEx(),
				elSize=el.getSize(),
				box=[elXYinRootWin.x,elXYinRootWin.y,elSize.width,elSize.height]
		}else{
			var box=el;
		}
        this.showAt(this.el.anchorPos(box, pos || this.defaultAlign, this.reAlign, this.defaultOffsets, true), parentMenu);
        this.fireEvent('show', this);
    },
    showAt : function(xy, parentMenu){
        if(this.fireEvent('beforeshow', this) !== false){
            this.parentMenu = parentMenu;
            if(!this.el){
                this.render();
            }
            //constrain to the viewport.
            this.el.setXY(xy);
            this.el.show();
            this.hidden = false;
        }
    },
    doLayout : function(shallow, force){
        if(this.rendered){
            this.onLayout(shallow, force);
        }
		this.hasLayout = true;
    },
    onLayout : function(ct, target){
        this.doAutoSize();
    },
    doAutoSize : function(){
    },
    hide : function(deep){
        if (!this.isDestroyed) {
            this.deepHide = deep;
	        if(this.fireEvent('beforehide', this) !== false){
	            this.doHide();
	            this.fireEvent('hide', this);
	        }
            delete this.deepHide;
        }
    },
    doHide: function(){
        this.hidden = true;
        if(this.rendered){
            this.onHide();
        }
    },
    onHide : function(){
        if(this.el && this.floating){
            this.el.hide();
        }
        var pm = this.parentMenu;
        if(this.deepHide === true && pm){
            if(pm.floating){
                pm.hide(true);
            }
        }
    },
    getVisibilityEl : function(){
        return this.hideParent ? this.container : this.el;
    },
    isVisible : function(){
        return this.rendered && this.getVisibilityEl().visible();
    },
    remove : function(comp){
        var c = comp;
        if(c){
            this.doRemove(c);
        }
        return c;
    },
    onRemove: function(c){
    },
    doRemove: function(c){
        this.items.remove(c);
		if(c.onRemoved)
			c.onRemoved();
        this.onRemove(c);
		c.destroy();
    },
    removeAll: function(autoDestroy){
		this.initItems();
       var item, rem = [], items = [];
        this.items.each(function(i){
            rem.push(i);
        });
        for (var i = 0, len = rem.length; i < len; ++i){
            item = rem[i];
            this.remove(item, autoDestroy);
            if(item.ownerCt !== this){
                items.push(item);
            }
        }
        return items;
    },
    beforeDestroy : function(){
    },
    onDestroy : function(){
		EventManager.removeResizeListener(this.hide, this);
		Menu.MenuMgr.unregister(this);
    	this.el.removeNode();
    },
    destroy : function(){
        if(!this.isDestroyed){
                this.destroying = true;
                this.beforeDestroy();
                this.onDestroy();
                this.fireEvent('destroy', this);
                this.purgeListeners();
                this.destroying = false;
                this.isDestroyed = true;
        }
    }

};

/**
Menu.MenuMgr 静态方法
对同一页面内所有菜单的显示与隐藏，以及层级之间的关系进行管理
**/
if(!Menu)
	Menu=function(){};
Menu.MenuMgr = function(){
   var menus, active, groups = {}, attached = false, lastShow = new Date();
   function init(){
       menus = {};
       active = [];
   }
   function hideAll(){
       if(active && active.length > 0){
           active.each(function(m){
               m.hide();
           });
           return true;
       }
       return false;
   }
   function onHide(m){
       active.remove(m);
       if(active.length < 1){
		   if(this.showOnRootWindow){
				ScreenEventManager.un('onmousedown',onMouseDown)
		   }else{
         	  EventManager.un(document,"onmousedown", onMouseDown);
		   }
           attached = false;
       }
   }
   function onShow(m){
       var last = active[active.length-1];
       lastShow = new Date();
       active.push(m);
       if(!attached){
		   if(this.showOnRootWindow){
				ScreenEventManager.on('onmousedown',onMouseDown)
		   }else{
			   EventManager.on(document,"onmousedown", onMouseDown);
		   }
           attached = true;
       }
       if(m.parentMenu){
          m.el.style.zIndex = parseInt(m.parentMenu.el.style.zIndex, 10) + 3;
          m.parentMenu.activeChild = m;
       }else if(last && !last.isDestroyed && last.isVisible()){
          m.el.style.zIndex = parseInt(last.el.style.zIndex, 10) + 3;
       }
   }
   function onBeforeHide(m){
       if(m.activeChild){
           m.activeChild.hide();
       }
       if(m.autoHideTimer){
           clearTimeout(m.autoHideTimer);
           delete m.autoHideTimer;
       }
   }
   function onBeforeShow(m){
       var pm = m.parentMenu;
       if(!pm && !m.allowOtherMenus){
           hideAll();
       }else if(pm && pm.activeChild){
           pm.activeChild.hide();
       }
   }
   function onMouseDown(e){
       if(new Date().getTime()-lastShow.getTime() > 50 && active.length > 0){
		   var e=getEvent(e);
		   var elem = e.srcElement;
		   if(e.srcElement.ownerDocument!=this.ownerDocument&&e.srcElement.ownerDocument!=nswt.rootDocument){//如果是在其它窗体内点击，则直接关闭菜单
		   		return hideAll();
		   }
		   while(elem){//如果在本窗体点击
			   if($E.hasClassName('z-menu',elem)){
					return;
				}
				elem=elem.parentElement;
		   }
           hideAll();
       }
   }
   return {
       hideAll : function(){
            return hideAll();
       },
       register : function(menu){
           if(!menus){
               init();
           }
           menus[menu.id] = menu;
           menu.on({
               beforehide: onBeforeHide,
               hide: onHide,
               beforeshow: onBeforeShow,
               show: onShow
           });
       },
       unregister : function(menu){
           delete menus[menu.id];
           menu.un("beforehide", onBeforeHide);
           menu.un("hide", onHide);
           menu.un("beforeshow", onBeforeShow);
           menu.un("show", onShow);
       }
   }
}();
