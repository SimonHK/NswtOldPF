/****
自定义组件管理类
自定义组件可以在生成实例时，统一注册到compCache内以方便管理，
ComponentMgr的必要性，例如：iframe内生成的组件相关dom元素有时是在topWindow内（如menu组件、dialog组件），在iframe关闭时，有必要把本页面生成所有组件全部destroy
所以注册到compCache内的组件请确定有destroy方法，并且在destroy方法中正确地清除了组件相关dom元素。
**/
ComponentMgr = function() {
    var compCache = new DataCollection();
    var pub={
        register : function(compid,comp){
			if(comp&&typeof(compid)=="string"){
            	compCache.add(compid,comp)
			}else{
            	compCache.add(compid.id,compid)
			}
        },
        unregister : function(compid){
            compCache.remove(compid);
        },
        get : function(compid){
            return compCache.get(compid);
        },
		getByDom:function(el,container){//返回DOM元素所处的控件，如果已指定container(container为dom元素或有el子对象的控件实例)，则必须是container的子控件
		  var comp, bd = container?(container.tagName?container:container.el): document.body;
		  while( el !== bd){
			if(el.compid){//控件内的dom元素必须已经添加compid属性
			  comp = ComponentMgr.get(el.compid);
			  if(comp && !comp.delegated){
				if(container){
				  if(comp.container === container)
					return comp;
				}else{
					return comp;
				}
			  }
			}
			el = el.parentNode;
		  }
		  return null;  
		},
        _unload : function() {
            var compid,comp;
            for (compid in compCache.map) {
				comp=compCache[compid];
				if(!comp.isDestroyed){
					comp.destroy();
				}
				compCache.remove(compid);
            }
            compCache=null;
        }

	}
	pub.add=pub.register;
	pub.remove=pub.unregister;
	pub.getById=pub.get;
	window.attachEvent('onunload',pub._unload);//在页面关闭时清除compCache中存储的组件，释放内存
	return pub;
}();
