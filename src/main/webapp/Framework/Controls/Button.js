/**************************************************
按钮：
仅用作在页面内动态生成按钮
**************************************************/

Button = function(config,existingEl){
    config = config || {};
	this.initialConfig=config;
    extra(this, config,{
	    ztype:'button',
		id: "comp_button"+(++ nswt.idSeed),
		text : '按钮',
		cls:'',
		onclick:'',//值类型为字符串
		handler:null,//值类型为function对象
		rendered : false
	});
    if(existingEl){
        this.el = $(existingEl);
		this.initialConfig = {
			ztype:'button',
			text : this.el.innerText,
			id: 'comp_f_'+this.el.id,
			cls:this.el.className,
			onclick: this.el.getAttributeNode('onClick').value,//值类型为字符串
			handler:function(){$(existingEl).fireEvent('onclick');},//传递点击事件到existingEl上
			rendered : false
		};
		if(this.el._disabled===true||this.el.disabled==='true'){
			this.initialConfig.disabled=true;
			this.cls+=' z-btn-disabled';
		}
		var iconEl=this.el.getElementsByTagName('IMG');
		if(iconEl.length){
			this.initialConfig.icon=iconEl[0].src;
		}
		this.rendered = true;
		this.el.compid=this.id;
    }
	if(this.icon && /.*\.(gif|png|jpg)$/i.test(this.icon)){
		this.iconHtml=String.format('<img src="{0}" width="20" height="20" />',this.icon)
	}else{
		this.iconHtml='';
	}
	Observable.call(this);
	this.addEvents('beforeshow','show','beforehide','hide','beforerender','render','afterrender','destroy');

};
Button.buttonTemplate ='<a onclick="{2};return false;" id="{0}" onselectstart="return false" tabindex="-1" hidefocus="true" class="z-btn {4}" ztype="button" href="javascript:void(0);">{3}<b>{1}</b></a>';
Button.buttonTemplate_NoIcon ='<a onclick="{2};return false;" id="{0}" class="z-btn {4}" href="javascript:;">{3}<b>{1}</b></a>';
Button.prototype={
    render : function(container, position){
        if(!this.rendered){
            if(!container && this.el){
                container = this.el.parentNode;
                this.allowDomMove = false;
            }
			this.ownerDocument=container.ownerDocument;
			this.ownerWindow=container.ownerDocument.parentWindow;
            this.container = this.ownerWindow.$(container);
            if(this.ctCls){
                this.container.addClassName(this.ctCls);
            }
            this.rendered = true;
            if(position !== undefined){
                if(isNumber(position)){
                    position = this.container.childNodes[position];
                }else{
                    position = $(position);
                }
            }
            this.onRender(this.container, position || null);
            //this.fireEvent('render', this);


            this.afterRender(this.container);


            if(this.disabled){
                // pass silent so the event doesn't fire the first time.
                this.disable(true);
            }

            //this.fireEvent('afterrender', this);
        }
        return this;
    },

    // private
    onRender : function(ct, position){
        if(!this.template){
            this.template = Button.buttonTemplate;
        }
        var btn, targs = [this.id];
		this.html=String.format(this.template,this.id,this.text,this.onclick,this.iconHtml,this.cls);
        if(position){
            btn = position.insertAdjacentHTML('beforeBegin',this.html);//在Main.js里已经给Gecko内核浏览器的HTMLElement添加insertAdjacentHTML方法
			if(isIE)btn=position.previousSibling;
        }else{
            btn = ct.insertAdjacentHTML('beforeEnd',this.html);//在Main.js里已经给Gecko内核浏览器的HTMLElement添加insertAdjacentHTML方法
			if(isIE)btn=ct.lastChild;
        }
        this.rendered = true;
        this.initButtonEl(btn, this.btnEl);
    },
	afterRender : function(){
    },
	initButtonEl:function(btn,btnEl){
		this.el = btn;
    	if(this.menu||this.handler){
    		$(this.el).on('onclick', this.onClick, this);
    	}
		if(this.menu){
			this.menu.on('show',this.onMenuShow,this)
			this.menu.on('hide',this.onMenuHide,this)
		}
    },
	beforeDestroy:function(){
    },
	onDestroy:function(){
    },
    /**
     * Show this button's menu (if it has one)
     */
    showMenu : function(){
        if(this.rendered && this.menu){
            if(this.menu.isVisible()){
                this.menu.hide();
            }
            this.menu.ownerCt = this;
            this.menu.show(this.el);
        }
        return this;
    },

    /**
     * Hide this button's menu (if it has one)
     */
    hideMenu : function(){
        if(this.hasVisibleMenu()){
            this.menu.hide();
        }
        return this;
    },
    hasVisibleMenu : function(){
        return this.menu && this.menu.ownerCt == this && this.menu.isVisible();
    },
    destroy : function(){
        if(!this.isDestroyed){
            //if(this.fireEvent('beforedestroy', this) == false){
			//	return false;
            //}
			this.destroying = true;
			this.beforeDestroy();
			if(this.rendered){
				this.el.parentNode.removeChild(this.el);
			}
			this.onDestroy();
			if(this.purgeListeners)
				this.purgeListeners();
			this.destroying = false;
			this.isDestroyed = true;
        }
    },
    onClick : function(e){
    	var e=this.ownerWindow.getEvent(e);
        if(e){
            e.returnValue = false;
        }
        if(e.button !== 0){
            return;
        }
        if(!this.disabled){
			//console.log(!this.ignoreNextClick)
            if(this.menu && !this.hasVisibleMenu() && !this.ignoreNextClick){
                this.showMenu();
            }
            if(this.handler){
                //this.el.removeClass('x-btn-over');
                this.handler.call(this.scope || this, this, e);
            }
        }
    },
    onMenuShow : function(e){
        if(this.menu.ownerCt == this){
            this.menu.ownerCt = this;
            this.ignoreNextClick = 0;
            //this.fireEvent('menushow', this, this.menu);
        }
    },
    onMenuHide : function(e){
        if(this.menu.ownerCt == this){
			var me=this;
            this.ignoreNextClick = setTimeout(function(){me.restoreClick()}, 250);//菜单开始隐藏后在1/4秒内不会响应点击再次展开，即双击事件等同于单击。
            //this.fireEvent('menuhide', this, this.menu);
            delete this.menu.ownerCt;
        }
    },
	restoreClick : function(){
        this.ignoreNextClick = 0;
    }


}
