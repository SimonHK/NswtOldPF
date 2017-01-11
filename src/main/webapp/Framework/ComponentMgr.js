/****
�Զ������������
�Զ����������������ʵ��ʱ��ͳһע�ᵽcompCache���Է������
ComponentMgr�ı�Ҫ�ԣ����磺iframe�����ɵ�������domԪ����ʱ����topWindow�ڣ���menu�����dialog���������iframe�ر�ʱ���б�Ҫ�ѱ�ҳ�������������ȫ��destroy
����ע�ᵽcompCache�ڵ������ȷ����destroy������������destroy��������ȷ�������������domԪ�ء�
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
		getByDom:function(el,container){//����DOMԪ�������Ŀؼ��������ָ��container(containerΪdomԪ�ػ���el�Ӷ���Ŀؼ�ʵ��)���������container���ӿؼ�
		  var comp, bd = container?(container.tagName?container:container.el): document.body;
		  while( el !== bd){
			if(el.compid){//�ؼ��ڵ�domԪ�ر����Ѿ����compid����
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
	window.attachEvent('onunload',pub._unload);//��ҳ��ر�ʱ���compCache�д洢��������ͷ��ڴ�
	return pub;
}();
