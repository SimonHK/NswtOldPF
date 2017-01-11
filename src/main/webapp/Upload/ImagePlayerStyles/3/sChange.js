/**
 * sChange 1.0 20100803
 * ������ www.wangzhaohui.com
 * QQ��4845587 E-mail:wzh@wangzhaohui.com
 * ���������2010-8-3
 **/

if(!top.execScript&&HTMLElement){
	HTMLElement.prototype.__defineGetter__("currentStyle", function(){
		return this.ownerDocument.defaultView.getComputedStyle(this,null);
	});
}
if(!Array.prototype.each){
	Array.prototype.each = function (func, scope) {
		var len = this.length;
		for (var i = 0; i < len; i++) {
			try {
				func.call(scope || this[i], this[i], i, this)
			} catch (ex) {}
		}
	}
}
if(!String.prototype.trim){
	String.prototype.trim = function () {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
}
/**
 * "mini" Selector Engine by James Padolsey
 * ����Ԫ��ѡ������֧������ѡ��ʽ
	* tag
	* tag > .className
	* tag > tag
	* #id > tag.className
	* .className tag
	* tag, tag, #id
	* tag#id.className
	* .className
	* span > * > b
 */
var miniSelector = (function () {
    var snack = /(?:[\w\-\\.#]+)+(?:\[\w+?=([\'"])?(?:\\\1|.)+?\1\])?|\*|>/ig,
        exprClassName = /^(?:[\w\-_]+)?\.([\w\-_]+)/,
        exprId = /^(?:[\w\-_]+)?#([\w\-_]+)/,
        exprNodeName = /^([\w\*\-_]+)/,
        na = [null, null];

    function _find(selector, context) {
        context = context || document;
        var simple = /^[\w\-_#]+$/.test(selector);
        if (!simple && context.querySelectorAll) {
            return realArray(context.querySelectorAll(selector));
        }
        if (selector.indexOf(',') > -1) {
            var split = selector.split(/,/g),
                ret = [],
                sIndex = 0,
                len = split.length;
            for (; sIndex < len; ++sIndex) {
                ret = ret.concat(_find(split[sIndex], context));
            }
            return unique(ret);
        }
        var parts = selector.match(snack),
            part = parts.pop(),
            id = (part.match(exprId) || na)[1],
            className = !id && (part.match(exprClassName) || na)[1],
            nodeName = !id && (part.match(exprNodeName) || na)[1],
            collection;
        if (className && !nodeName && context.getElementsByClassName) {
            collection = realArray(context.getElementsByClassName(className));
        } else {
            collection = !id && realArray(context.getElementsByTagName(nodeName || '*'));
            if (className) {
                collection = filterByAttr(collection, 'className', RegExp('(^|\\s)' + className + '(\\s|$)'));
            }
            if (id) {
                var byId = context.getElementById(id);
                return byId ? [byId] : [];
            }
        }
        return parts[0] && collection[0] ? filterParents(parts, collection) : collection;
    }

    function realArray(c) {
        try {
            return Array.prototype.slice.call(c);
        } catch (e) {
            var ret = [],
                i = 0,
                len = c.length;
            for (; i < len; ++i) {
                ret[i] = c[i];
            }
            return ret;
        }
    }

    function filterParents(selectorParts, collection, direct) {
        var parentSelector = selectorParts.pop();
        if (parentSelector === '>') {
            return filterParents(selectorParts, collection, true);
        }
        var ret = [],
            r = -1,
            id = (parentSelector.match(exprId) || na)[1],
            className = !id && (parentSelector.match(exprClassName) || na)[1],
            nodeName = !id && (parentSelector.match(exprNodeName) || na)[1],
            cIndex = -1,
            node, parent, matches;
        nodeName = nodeName && nodeName.toLowerCase();
        while ((node = collection[++cIndex])) {
            parent = node.parentNode;
            do {
                matches = !nodeName || nodeName === '*' || nodeName === parent.nodeName.toLowerCase();
                matches = matches && (!id || parent.id === id);
                matches = matches && (!className || RegExp('(^|\\s)' + className + '(\\s|$)').test(parent.className));
                if (direct || matches) {
                    break;
                }
            } while ((parent = parent.parentNode));
            if (matches) {
                ret[++r] = node;
            }
        }
        return selectorParts[0] && ret[0] ? filterParents(selectorParts, ret) : ret;
    }
    var unique = (function () {
        var uid = +new Date();
        var data = (function () {
            var n = 1;
            return function (elem) {
                var cacheIndex = elem[uid],
                    nextCacheIndex = n++;
                if (!cacheIndex) {
                    elem[uid] = nextCacheIndex;
                    return true;
                }
                return false;
            };
        })();
        return function (arr) {
            var length = arr.length,
                ret = [],
                r = -1,
                i = 0,
                item;
            for (; i < length; ++i) {
                item = arr[i];
                if (data(item)) {
                    ret[++r] = item;
                }
            }
            uid += 1;
            return ret;
        };
    })();

    function filterByAttr(collection, attr, regex) {
        /**
         * Filters a collection by an attribute.
         */
        var i = -1,
            node, r = -1,
            ret = [];
        while ((node = collection[++i])) {
            if (regex.test(node[attr])) {
                ret[++r] = node;
            }
        }
        return ret;
    }
    return _find;
})();
/*
 *	ͼƬ�ֻ�
 *	ʵ��˼·Դ��bujichong��jQuery�����л����sGallery
 *	http://www.ceshile.cn/lxProject/ceshi/sGallery/sGallery.html
 */
var sChange = (function () {
    function extra(o, c) { //���ƶ���c�ĳ�Ա������o
        if (!o) {
            o = {};
        }
        if (o && c && typeof c == 'object') {
            for (var p in c) {
                o[p] = c[p]
            }
        }
        return o
    }
	function setOpacity(elem,alpha){
        if (top.execScript) {//isIE
            elem.style.filter = 'alpha(opacity=' + alpha + ')';
        } else {
            elem.style.opacity = alpha / 100;
        }
	}
	function getOpacity(elem){
        var alpha;
        if (top.execScript) {//isIE
            alpha = elem.currentStyle.filter.indexOf("opacity=") >= 0 ? (parseFloat(elem.currentStyle.filter.match(/opacity=([^)]*)/)[1])) + '' : '100';
        } else {
            alpha = 100 * elem.ownerDocument.defaultView.getComputedStyle(elem, null)['opacity'];
        }
        setOpacity(elem,alpha);
        return alpha;
	}
    function fade(element, transparency, speed, callback) { //͸���Ƚ��䣺transparency:͸���� 0(ȫ͸)-100(��͸)��speed:�ٶ�1-100��Ĭ��Ϊ1
        if (typeof(element) == 'string') element = document.getElementById(element);
        if (!element.effect) {
            element.effect = {};
            element.effect.fade = 0;
        }
        clearInterval(element.effect.fade);
        var speed = speed || 1;
        var start = getOpacity(element);
        element.effect.fade = setInterval(function () {
            start = start < transparency ? Math.min(start + speed, transparency) : Math.max(start - speed, transparency);
            setOpacity(element,start)
            if (Math.round(start) == transparency) {
	            setOpacity(element,transparency)
                clearInterval(element.effect.fade);
                if (callback) callback.call(element);
            }
        }, 20);
    };

    function $(el) {
        extra(el, {
            hasClass: function (c) {
                return (' '+this.className+' ').indexOf(' '+c+' ') != -1;
            },
            addClass: function (c) {
                if (!this.hasClass(c)) {
                    this.className += " " + c
                };
                return this
            },
            removeClass: function (c) {
                if (this.hasClass(c)) {
                    this.className = (" " + this.className + " ").replace(" " + c + " ", " ").trim();
                    return this
                }
            },
            hide: function () {
            	this.olddisplay=this.currentStyle.display=='none'?'block':this.currentStyle.display;
                this.style.display = "none";
                return this
            },
            show: function () {
                this.style.display = this.olddisplay?this.olddisplay:"";
                return this
            },
           	opacity:function(a){
           		if(a!== undefined){
           			setOpacity(this,a);
           			return this
           		}else{
           			return getOpacity(this)
           		}
           	},
            fade: function (transparency, speed, callback) {
                fade(this, transparency, speed, callback)
            }
        });
        return el;
    }

    function _change(o) {
        o = extra({
            changeObj: null,//�л�����
            thumbObj: null,//��������
            botPrev: null,//��ť��һ��
            botNext: null,//��ť��һ��
            thumbNowClass: 'now',//��������ǰ��class,Ĭ��Ϊnow
            thumbOverEvent: true,//��꾭��thumbObjʱ�Ƿ��л�����Ĭ��Ϊtrue��Ϊfalseʱ��ֻ�������thumbObj���л�����
            slideTime: 1000,//ƽ������ʱ�䣬Ĭ��Ϊ1000ms
            autoChange: true,//�Ƿ��Զ��л���Ĭ��Ϊtrue
            clickFalse: true,//����������������ӣ�����Ƿ�������Ч�����Ƿ񷵻�return false��Ĭ����return false������Ч����thumbOverEventΪfalseʱ���������Ϊtrue������������¼���ͻ
            overStop: true,//��꾭���л�����ʱ���л������Ƿ�ֹͣ�л�����������뿪�������Զ��л���ǰ�����ѿ����Զ��л�
            changeTime: 5000,//�Զ��л�ʱ��
            delayTime: 300 //��꾭��ʱ�����л�����ʱ�䣬�Ƽ�ֵΪ300ms
        }, o || {});
        var changeObjs = miniSelector(o.changeObj);
        var thumbObjs;
        var size = changeObjs.length;
        var nowIndex = 0; //����ȫ��ָ��
        var index; //����ȫ��ָ��
        var startRun; //Ԥ�����Զ����в���
        var delayRun; //Ԥ�����ӳ����в���
        /**���л�����**/

        function fadeAB() {
            if (nowIndex != index) {
                if (o.thumbObj != null) {
                    thumbObjs = miniSelector(o.thumbObj);
                    thumbObjs.each(function (thumbElm) {
                        thumbElm.removeClass(o.thumbNowClass);
                    });
					if(thumbObjs[index] == undefined)alert([o.changeObj,o.thumbObj,'�л������뵼��������������']);
                    thumbObjs[index].addClass(o.thumbNowClass);
                }
                if (o.slideTime <= 0) {
                    changeObjs[nowIndex].hide();
                    changeObjs[index].show();
                } else {
                    changeObjs[nowIndex].fade(0, 2000 / o.slideTime, function () {
                        this.hide()
                    });
                    changeObjs[index].opacity(0);
                    changeObjs[index].show();
                    changeObjs[index].fade(100, 2000 / o.slideTime);
                }
                nowIndex = index;
                if (o.autoChange == true) {
                    clearInterval(startRun); //�����Զ��л�����
                    startRun = setInterval(runNext, o.changeTime);
                }
            }
        }
        /**�л�����һ��**/
        function runNext() {
            index = (nowIndex + 1) % size;
            fadeAB();
        }
        /**��ʼ��**/
        changeObjs.each(function (changElm) {
            $(changElm).hide()
        })
        changeObjs[0].show();
        /**�����һͼƬ**/
        if (o.thumbObj != null) {
            /**��ʼ��thumbObj**/
            thumbObjs = miniSelector(o.thumbObj);
            thumbObjs.each(function (thumbElm) {
                $(thumbElm).removeClass(o.thumbNowClass);
            })
            thumbObjs[0].addClass(o.thumbNowClass);
            thumbObjs.each(function (thumbElm, i) {
                thumbElm._index = i;
                thumbElm.onclick = function () {
                    index = this._index;
                    fadeAB();
                    if (o.clickFalse == true) {
                        return false;
                    }
                }
                if (o.thumbOverEvent == true) {
                    thumbElm.onmouseover = function () {
                        index = this._index;
                        delayRun = setTimeout(fadeAB, o.delayTime);
                    };
                    thumbElm.onmouseout = function () {
                        clearTimeout(delayRun);
                    };
                }
            })
        }
        /**�����һ��**/
        if (o.botNext != null) {
            miniSelector(o.botNext)[0].onclick = function () {
                if (changeObjs.length > 1) {
                    runNext();
                }
                return false;
            };
        }
        /**�����һ��**/
        if (o.botPrev != null) {
            miniSelector(o.botPrev)[0].onclick = function () {
                if (changeObjs.length > 1) {
                    index = (nowIndex + size - 1) % size;
                    fadeAB();
                }
                return false;
            };
        }
        /**�Ƿ��Զ�����**/
        if (o.autoChange == true) {
            startRun = setInterval(runNext, o.changeTime);
            if (o.overStop == true) {
                changeObjs.each(function (changeObj) {
                    changeObj.onmouseover = function () {
                        clearInterval(startRun); //�����Զ��л�����
                    };
                    changeObj.onmouseout = function () {
                        startRun = setInterval(runNext, o.changeTime);
                    };
                })
            }
        }
    }
    return _change;
})();