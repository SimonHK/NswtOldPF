/**
�����ɵ���Ԫ�ؿ�/�ߵķָ���
dragElement ����ק�ķָ���
resizingElement ��ק���Ҫ�޸Ŀ�/�ߵ�Ԫ��
orientation ��ק����V��ֱ��Hˮƽ
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
	this.resetProxy=function(evt){
		var elSize=self.el.getSize();
		var elXY=self.el.getPosition();
		self.proxy.style.width=elSize.width+'px';
		self.proxy.style.height=elSize.height+'px';
		self.proxy.setXY(elXY.x,elXY.y);
		evt=fixEvent(getEvent(evt));
		if(evt){
			self.proxy.lastMouseX=evt.pageX;
			self.proxy.lastMouseY=evt.pageY;
		}
	}
	this.proxy.hide();
	if(window.DragManager){//��ק
		//this.proxy.setAttribute("dragstart","DragManager.doDrag");
		this.proxy.setAttribute("dragstart","SplitBar.dragStart");
		this.proxy.setAttribute("draging","SplitBar.draging");
		this.proxy.setAttribute("dragexit","SplitBar.dragExit");
		this.proxy.setAttribute("resizingElement",this.resizingEl.id);
		this.proxy.setAttribute("dragorientation",this.orientation);
		this.el.onmousedown = function(evt){
			self.resetProxy(evt)
			if(isIE){//��ֹ�����ק��iframe������ʱ����ʧ�¼�
				self.proxy.setCapture();
				document.attachEvent('onmouseup',function(){
					self.proxy.releaseCapture();
				})
			}
			DragManager.onMouseDown(evt,self.proxy);//ע����ק����
		};
	}else{
		throw '��ק��δ����';
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
	evt = fixEvent(getEvent(evt));
	this.show();
	this.lastX=this.style.left;
	this.lastY=this.style.top;
	this.lastMouseX=evt.pageX;
	this.lastMouseY=evt.pageY;
	console.log(evt.clientX)
	DragManager.doDrag(evt,this);
};
SplitBar.draging= function(evt){
	evt = getEvent(evt);
	if(this.getAttribute('dragorientation')=='H'){
		this.style.top=this.lastY;
	}else if(this.getAttribute('dragorientation')=='V'){
		this.style.left=this.lastX;
	}

};
SplitBar.dragExit= function(evt){
	evt = fixEvent(getEvent(evt));
	this.hide();
	var endMouseX=evt.pageX;
	var endMouseY=evt.pageY;
	//console.log(this.lastMouseX,endMouseX)
	if(this.lastMouseX==endMouseX && this.lastMouseY==endMouseY){return;}
	var resizingElement=$(this.getAttribute('resizingElement'));
	if(!resizingElement){
		throw "SplitBar.js->SplitBar.dragExit  û���ҵ���Ӧ��resizingElement";
		return
	}
	if(this.getAttribute('dragorientation')=='H'){
		var resizingElWidth=resizingElement.offsetWidth;
		var newWidth=Math.min(Math.max(resizingElWidth-this.lastMouseX+endMouseX,0),2000);
		resizingElement.width=isStrict?newWidth-resizingElement.getOuterW():newWidth;
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