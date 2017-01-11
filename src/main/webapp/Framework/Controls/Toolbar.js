/**************************************************
工具栏，
1、页面载入时查找所有className为z-toolbar的div；
2、根据查找到div实例化Toolbar类，如果工具栏中按钮超过工具栏宽度，则在工具栏末端显示“其他操作”
3、在window onload及onresize时对工具栏的布局进行调整
**************************************************/

Toolbar = function(config,existingEl){
    config = config || {};
    if (!existingEl) {
        alert("Toolbar：没有existingEl参数");
        return;
    }
	extra(this,config,{
			enableOverflow : true,//是否隐藏多余按钮
			triggerWidth: 70,//用于显示隐藏按钮的未端下拉按钮宽
			hiddenItems:[]
		});

	//Observable.call(this);
	//this.addEvents('overflowchange');自定义事件，待实现

    this.el = $(existingEl);
	this.id = 'comp_f_'+this.el.id,

	this.init();
	//this.el.on("onclick", function (evt) {console.log('toolbar clicked')});
	EventManager.on(window,'onload',this.fitToSize,this);
	EventManager.onWindowResize(this.fitToSize, this);
};

Toolbar.prototype={
	init:function(){
		var children=toArray(this.el.children);

		this.items=[];
		children.each(function(el){
			if($E.hasClassName('z-btn-intoolbar',el)){
				var btn=new Button({},el)
				this.items.push(btn);
			}
		},this);
	},
    hideItem : function(item) {
        this.hiddenItems.push(item);
        item.xtbHidden = true;
		item.xtbWidth = item.el.offsetWidth;
        //item.hide();
		item.el.bak_display=item.el.style.display;
		item.el.style.display = 'none';
    },
    unhideItem : function(item) {
        //item.show();
		item.el.style.display = item.el.bak_display;
        item.xtbHidden = false;
        this.hiddenItems.remove(item);
    },
    getItemWidth : function(c) {
        return c.xtbHidden ? (c.xtbWidth || 0) : c.el.offsetWidth;
    },
	fitToSize : function() {
        /**/
		if (this.enableOverflow === false) {
            return;
        }
        var width       = this.el.offsetWidth,
            clipWidth   = width - this.triggerWidth,
            lastWidth   = this.lastWidth || 0,
            hiddenItems = this.hiddenItems,
            hasHiddens  = hiddenItems.length != 0,
            isLarger    = width >= lastWidth;

        this.lastWidth  = width;
        if ( width != lastWidth) {
            var items     = this.items,
                len       = items.length,
                loopWidth = 0,
                item;

            for (var i = 0; i < len; i++) {
                item = items[i];
                if (!item.isFill) {
					if(this.getItemWidth(item)==0){
						//console.log(item)
					}
                    loopWidth += this.getItemWidth(item);
                    if (loopWidth > clipWidth) {
                        if (!(item.hidden || item.xtbHidden)) {
                            this.hideItem(item);
                        }
                    } else if (item.xtbHidden) {
                        this.unhideItem(item);
                    }
                }
            }
        }
        //test for number of hidden items again here because they may have changed above
        hasHiddens = hiddenItems.length != 0;

        if (hasHiddens) {
            this.initMore();
            if (!this.lastOverflow) {
                //this.el.fireEvent('overflowchange', this.el, true);
                this.lastOverflow = true;
            }
        } else if (this.more) {
            this.clearMenu();
            this.more.destroy();
            delete this.more;

            if (this.lastOverflow) {
                //this.el.fireEvent('overflowchange', this.el, false);
                this.lastOverflow = false;
            }
        }

		/**/
    },
    clearMenu : function(){
        var menu = this.moreMenu;
        if (menu && menu.items) {
            menu.items.each(function(item){
                delete item.menu;
            });
        }
    },
	initMore : function(){
        if (!this.more) {
            this.moreMenu = new DropMenu({
                ownerCt : this,
                listeners: {
                    beforeshow: this.beforeMoreShow,
                    scope: this
                }
            });
            this.more = new Button({
                text   : '其他操作<span style="font-size:8px; font-family:verdana; margin:0 0 0 4px">&#9660;</span>',
                cls  : 'z-btn-intoolbar z-toolbar-more',
                menu   : this.moreMenu,
                ownerCt: this
            });
            this.more.render(this.el,0);
        }
    },
	beforeMoreShow : function(menu) {
        var items = this.items,
            len   = items.length,
            item;

        this.clearMenu();
        menu.removeAll();
        for (var i = 0; i < len; i++) {
            item = items[i];
            if (item.xtbHidden) {
                this.addComponentToMenu(menu, item);
            }
        }

        // put something so the menu isn't empty if no compatible items found
        if (menu.items.length < 1) {
            menu.add('<div class="z-toolbar-no-items">(None)</div>');
        }
    },
    addComponentToMenu : function(menu, component) {
        /**目前只实现往菜单里添加按钮**/

        if (component.ztype=='button') {
        	var btnCls='z-btn-inmenu';
			if(component.el&&(component.el._disabled===true||component.el.disabled==='true')){
				btnCls='z-btn-inmenu z-btn-disabled';
			}
			var btnConfig=extraIf({cls:btnCls,onclick:''},component.initialConfig);
			menu.add(
				new Button(btnConfig)
			);
        }
    },
	clearMenu : function(){
        var menu = this.moreMenu;
        if (menu && menu.items) {
            menu.items.each(function(item){
                delete item.menu;
            });
        }
    }

}
Toolbar.pageOnReady=function(){
	var toolbarElems=$E.getElementsByClassName('z-toolbar-nowrap','DIV',document)
	if(toolbarElems.length){
		toolbarElems.each(function(el){
			new Toolbar({},el)
		});
	}
}

Page.onReady(Toolbar.pageOnReady);
