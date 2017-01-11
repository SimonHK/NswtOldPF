/**************************************************
µ¯³ö²ã£º
**************************************************/

Layer = function(config, existingEl){
    config = config || {};
    extra(this, config,{
	    ztype:'Layer',
        id: "comp_layer"+(++ nswt.idSeed)
    });
    var cp = config.parentEl, 
		pel = cp || document.body;
    if(existingEl){
		this.ownerDocument=existingEl.ownerDocument;
		this.ownerWindow=existingEl.ownerDocument.parentWindow;
        this.dom = this.ownerWindow.$(existingEl);
    }
    if(!this.dom){
		this.ownerDocument=pel.ownerDocument;
		this.ownerWindow=pel.ownerDocument.parentWindow;
        var o = this.ownerDocument.createElement("div");
        o.className='z-layer';
        this.dom = this.ownerWindow.$(pel.appendChild(o));
    }
    if(this.cls){
        this.dom.className += ' '+this.cls;
    }
    this.constrain = config.constrain !== false;
    this.dom.style.zIndex = config.zindex || this.getZIndex();
    this.dom.style.position='absolute';
    this.dom.hide();
};
Layer.prototype={
    getZIndex : function(){
        return this.zindex || parseInt((this.getShim() || this.dom).currentStyle.zIndex, 10) || 11000;
    },
    destroy : function(){
        this.dom.removeNode();
        delete this.dom;
    },

    remove : function(){
        this.destroy();
    },

    constrainXY : function(){
        if(this.constrain){
            var v=$E.getViewport(this.ownerWindow),
                vw = v.width,
                vh = v.height;
            var st = Math.max(this.ownerDocument.documentElement.scrollTop, this.ownerDocument.body.scrollTop);
            var sl = Math.max(this.ownerDocument.documentElement.scrollLeft, this.ownerDocument.body.scrollLeft);

            var xy = this.dom.getPosition();
            var x = xy.x, y = xy.y;
            var so = this.shadowOffset||0;
            var w = this.dom.offsetWidth+so, h = this.dom.offsetHeight+so;
            // only move it if it needs it
            var moved = false;
            // first validate right/bottom
            if((x + w) > vw+sl){
                x = vw - w - so;
                moved = true;
            }
            if((y + h) > vh+st){
                y = vh - h - so;
                moved = true;
            }
            // then make sure top/left isn't negative
            if(x < sl){
                x = sl;
                moved = true;
            }
            if(y < st){
                y = st;
                moved = true;
            }
            if(moved){
                if(this.avoidY){
                    var ay = this.avoidY;
                    if(y <= ay && (y+h) >= ay){
                        y = ay-h-5;
                    }
                }
                xy = [x, y];
                this.storeXY(xy);
                this.dom.setXY(xy);
            }
        }
        return this;
    },

    storeXY : function(xy){
        delete this.lastLT;
        this.lastXY = xy;
    }
};