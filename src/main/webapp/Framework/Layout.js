/**************************************************
自动布局，
1、页面载入时查找所有className为layoutTable的表格；
2、以每个表格实例化一个TableLayout类
3、在window onload及onresize时对布局表格各个单元格进行调整
**************************************************/
Layout={}
Layout.layoutInstance=[];
Layout.isAutoResizeBodyHeight=false;//是否自动调整页面高度
Layout.autoLayout=function(){
	var tables=document.getElementsByTagName('TABLE');
	Layout.layoutTables=[];
	for(var i=0,l=tables.length;i<l;i++){
		if(tables[i].className=='layoutTable'){
			Layout.layoutTables.push(tables[i]);
			if(tables[i].parentNode.tagName=='BODY'){
				var tHeight=tables[i].getAttribute('height');
				if(tHeight=='*'||tHeight.indexOf("%")>0){
					Layout.isAutoResizeBodyHeight=true;
				}
			}
		}
	}
	if(Layout.isAutoResizeBodyHeight){
		EventManager.on(window,'onload',Layout.resizeBodyHeight);
		EventManager.onWindowResize(Layout.resizeBodyHeight);
	}
}

Layout.resizeBodyHeight=function(evt){
		//console.log(event?event.type:'resize',location.pathname)
		var evt=getEvent(evt);
		//console.log(evt?evt.type:'resize',location.pathname,Layout.layoutInstance)
		if(evt&&evt.type=='load'){
			var LT=Layout.layoutTables;
			//console.log(evt?evt.type:'resize',location.pathname,Layout.layoutTables.length)
			for(var i=0;i<LT.length;i++){
				var layoutInstance=new Layout.TableLayout({el:LT[i]});
				layoutInstance.configureItem();
				Layout.layoutInstance.push(layoutInstance);
			}
		}
		//console.log(evt?evt.type:'resize',location.pathname,Layout.layoutInstance.length)
		if(window!=window.parent&&(!window.parent.Page.loadComplete||!window.top.Page.loadComplete)){//如果iframe的父窗口没有载入完成，则不进行页面布局的调整
			return;
		}
		var ch=isIE ? (isStrict ? document.documentElement.clientHeight: document.body.clientHeight) : self.innerHeight;
		if(window==window.top&&ch<500){
			ch=500;//让最顶层页面（Application.jsp）高度不小于500
		}
		if(parseInt(document.body.currentStyle.height)!=ch){
			document.body.style.height=ch+'px';
		}
		var layoutInsta=Layout.layoutInstance;
		for(var j=0;j<layoutInsta.length;j++){
			layoutInsta[j].onLayout();
		}
		if(isIE6){//在ie6下，iframe大小改变，没有激发resize事件。
		}
	}

Layout.TableLayout=function(config){
	extra(this, config,{
		  ztype:'LayoutTable',
		  el:null,
		  container:null
	});
	if(!this.el)
		alert('Layout.js#TableLayout: 参数错误，没有传入dom对象');
	this.container=this.container;
	if(!this.container)
		this.container=this.el.parentNode;
	if(!this.layoutTarget)
		this.layoutTarget=this.container;
	/**继承自定义事件机制**/
	Observable.call(this);
	this.addEvents('beforelayout','afterlayout');
	//this.addListener('beforelayout',function(){alert('激发自定义事件beforelayout')})
	//this.addListener('afterlayout',function(){alert('激发自定义事件afterlayout')})
};
Layout.TableLayout.prototype={
	configureItem:function(){//初始化，记录各个单元格的布局特性（固定/百分比/自适应）
		var cells=[],rows=this.el.rows;
		this.fixHeightCells=[];
		this.percentHeightCells=[];
		this.autoHeightCells=[];
		for(var r=0;r<rows.length;r++){
			cells.push(rows[r].cells[0]);
		}
		for(var i=0;i<cells.length;i++){
			var height=cells[i].getAttribute('height');

			if(height=='*'||height=='auto'){
				this.autoHeightCells.push(cells[i]);
			}else if(height!==null&&height.indexOf("%")>0){
				cells[i]._backupInitHeight=height;
				this.percentHeightCells.push(cells[i]);
			}else if(height==''||height==null){//没有设置高度时在ie下返回''在firefox下返回null
				var cell=cells[i];
				var boxfix=isBorderBox ? 0 : $E.getOuterH(cell);
				cell._backupInitHeight = cell.clientHeight-boxfix;//仅作备份，调整布局时以offsetHeight为准，
				this.fixHeightCells.push(cells[i])
			}else{
				this.fixHeightCells.push(cells[i])
			}
		}
		//this.onLayout();
	},
	getLayoutTargetHeight : function() {//
		var height;
		if((this.layoutTarget.tagName=='TD'||this.layoutTarget.tagName=='BODY')&&/^[0-9.]+px$/i.test(this.layoutTarget.style.height)){
			height=parseInt(this.layoutTarget.style.height);
		}else{
			height=this.layoutTarget.clientHeight;
		}
		height-=isBorderBox?0:$E.getOuterH(this.layoutTarget);
		//console.log(this.layoutTarget.tagName,height)
		return height;
    },
    onLayout : function() {
		this.fireEvent('beforelayout', this);
		//console.log(this.el)
		var fixHeightCellsH=0,percentHeightCellsH=0,
		fixHC=this.fixHeightCells,perHC=this.percentHeightCells,autoHC=this.autoHeightCells;
		var odd;
		var tableHeight=this.el.getAttribute('height');
		if(tableHeight===null)return;
		if(tableHeight==''||tableHeight=='*')
			tableHeight='100%';
		if(tableHeight.indexOf("%") != -1){
			odd=tableHeight=this.getLayoutTargetHeight()*parseInt(tableHeight,10)*0.01;
		}
		for(var i=0;i<fixHC.length;i++){
			odd-=fixHC[i].offsetHeight;
		}
		for(var j=0;j<perHC.length;j++){
			var newHeight=parseInt(perHC[j]._backupInitHeight,10)*tableHeight*0.01;
			if(newHeight>odd)newHeight=odd;
			odd-=newHeight;
			var boxfix=isBorderBox?0:$E.getOuterH(perHC[j]);
			perHC[i].style.height=newHeight-boxfix+'px';
			
			var nextCell=perHC[i].nextElement();
			while(nextCell&&nextCell.tagName=='TD'){
				nextCell.style.height=perHC[i].style.height;
				nextCell.style.zoom=1;
				nextCell=nextCell.nextElement();
			}
			
		}
		for(var k=0,autoHCLength=autoHC.length;k<autoHCLength;k++){
			var boxfix=isBorderBox?0:$E.getOuterH(autoHC[k]);
			autoHC[k].style.height=parseInt(odd/autoHCLength)-boxfix+'px';
			//console.log(event?event.type:'resize',location.pathname,autoHC[k].getAttribute('height'),autoHC[k].style.height)
		}
		if(isIE6){//ie6的bug，resize事件仅在body尺寸变化时激发,而不在iframe元素尺寸变化时激发
			var iframes=this.el.document.getElementsByTagName('IFRAME');
			iframes=toArray(iframes)
			iframes.each(function(iframe){
				  var innerWin=iframe.contentWindow,innerDoc=innerWin.document;
				  if(innerWin.Layout.isAutoResizeBodyHeight==true){//如果iframe需要自动调整body高度，则对body高度进行调整，在ie6会激发window.resize事件
					  innerDoc.body.style.height=(innerWin.isStrict ? innerDoc.documentElement.clientHeight: innerDoc.body.clientHeight)+'px';
				  }
			})
		}
		this.fireEvent('afterlayout', this);
    }
}

Page.onReady(Layout.autoLayout);

/**
创建可调整元素宽/高的分隔条
dragElement 可拖拽的分隔条
resizingElement 拖拽完后要修改宽/高的元素
orientation 拖拽方向，V垂直，H水平
**/
SplitBar = function(dragElement, resizingElement, orientation, placement, existingProxy){
	this.el = $(dragElement);
    this.resizingEl = $(resizingElement);
	this.orientation = orientation || 'H';
	this.minSize = 0;
    this.maxSize = 2000;
    if(!existingProxy){
        /** @private */
        this.proxy = SplitBar.createProxy(this.orientation);
    }else{
        this.proxy = $(existingProxy);
    }
	var self=this;
	this.resetProxy=function(){
		var elSize=self.el.getSize();
		var elXY=self.el.getPosition();
		self.proxy.style.width=elSize.width+'px';
		self.proxy.style.height=elSize.height+'px';
		self.proxy.setXY(elXY.x,elXY.y);
	}
	this.proxy.hide();
	if(window.DragManager){//拖拽
		//this.proxy.setAttribute("dragstart","DragManager.doDrag");
		this.proxy.setAttribute("dragstart","SplitBar.dragStart");
		this.proxy.setAttribute("draging","SplitBar.draging");
		this.proxy.setAttribute("dragexit","SplitBar.dragExit");
		this.proxy.setAttribute("resizingElement",this.resizingEl.id);
		this.proxy.setAttribute("dragorientation",this.orientation);
		this.el.onmousedown = function(evt){
			self.resetProxy()
			DragManager.onMouseDown(evt,self.proxy);//注册拖拽方法
		};
	}else{
		throw '拖拽类未载入';
	}
    if(this.orientation == 'H'){
        this.el.addClassName("z-splitbar-h");
    }else{
        this.el.addClassName("z-splitbar-v");
    }
}
SplitBar.createProxy = function(dir){
    var proxy = document.createElement("div");
	proxy.className='z-splitbar-proxy';
    document.body.appendChild(proxy);
	$(proxy).unselectable();
    return proxy;
};
SplitBar.dragStart= function(evt){
	this.show();
	this.lastX=this.style.left;
	this.lastY=this.style.top;
	this.lastMouseX=evt.pageX;
	this.lastMouseY=evt.pageY;
	DragManager.doDrag(evt,this);
};
SplitBar.draging= function(evt){
	if(this.getAttribute('dragorientation')=='H'){
		this.style.top=this.lastY;
	}else if(this.getAttribute('dragorientation')=='V'){
		this.style.left=this.lastX;
	}
	
};
SplitBar.dragExit= function(evt){
	this.hide();
	var endMouseX=evt.pageX;
	var endMouseY=evt.pageY;
	var resizingElement=$(this.getAttribute('resizingElement'));
	if(!resizingElement){
		throw "SplitBar.js->SplitBar.dragExit  没有找到对应的resizingElement";
		return
	}
	if(this.getAttribute('dragorientation')=='H'){
		var resizingElWidth=resizingElement.offsetWidth;
		var newWidth=Math.min(Math.max(resizingElWidth-this.lastMouseX+endMouseX,0),2000);
		resizingElement.width=(isStrict?newWidth-resizingElement.getOuterW():newWidth);
	}else if(this.getAttribute('dragorientation')=='V'){
		var resizingElHeight=resizingElement.offsetHeight;
		var newHeight=Math.min(Math.max(resizingElHeight-this.lastMouseX+endMouseX,0),2000);
		resizingElement.height=(isStrict?newHeight-resizingElement.getOuterW():newHeight);
		
	}
};
SplitBar.instantiationByElement=function(){
	var splitBars_H=$E.getElementsByClassName('z-splitbar-h','td');
	for(var i=0;i<splitBars_H.length;i++){
		var splitTd=splitBars_H[i];
		var resizeTd=splitTd.prevElement();
		new SplitBar(splitTd,resizeTd);
	}
};
Page.onReady(SplitBar.instantiationByElement)