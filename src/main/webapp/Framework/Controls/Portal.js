var Portal = {};
Portal.gridClassName = "portal-dashedBox";
Portal.dropGird =null;
Portal.dragFeedObj =null;

Portal.dragStart = function(evt){
	var thisObjArr=this.feedObj.parentObj.feeds;
	Portal.dragFeedObj=thisObjArr.remove(this.feedObj,1)[0];//��portalDate��ɾ�����ڱ��϶���portalet��Ӧ��feed
	//console.log("���Portal.dragFeedObj" + Portal.dragFeedObj);
	DragManager.doDrag(evt);
	Portal.getDropGird(this);
	this.style.filter = "alpha(opacity=70)";
	this.style.opacity = 0.7;
}

Portal.dragEnd = function(evt){
	if(Portal.dragFeedObj==null)return;
	if(this.nextSibling!=Portal.dropGird){
		Portal.dropGird.parentNode.insertBefore(this, Portal.dropGird);//��������ק��portalet���뵽���߿��ǰ��
	}
	var targetObj=Portal.dropGird.parentEle.feedObj,
	targetObjArr=targetObj.feeds,
	targetIndex=Portal.dropGird.feedIndex;
	//sourceObjArr=this.feedObj.parentObj.feeds;
	if(targetObjArr&&targetObjArr[targetIndex]!=this.feedObj){
		targetObjArr.insert(targetIndex,Portal.dragFeedObj);
		this.feedObj.parentObj=targetObj;
	}
	Portal.dragFeedObj =null;
	Portal.dropGird.style.display="none";
	this.style.position="";
	this.style.width="";
	this.style.filter = "";
	this.style.opacity = "";
	Portal.getDropRegion();
	//console.log(portalData);
}

Portal.portalData2JSON=function(){//����portalDataΪJSON��ʽ���ַ���
	if(!portalData){alert("�޷����������Ϣ��portalData��");return;}
	tmpData=portalData.clone();
	for(var i=0;i<tmpData.length;i++){
		delete tmpData[i].element;
		//console.log(tmpData[i].element);
		for(var j=0;j<tmpData[i].feeds.length;j++){
			delete tmpData[i].feeds[j].parentObj;
			delete tmpData[i].feeds[j].element;
		}
	}
	//console.log(JSON.toString(tmpData));
	//return JSON.toString(tmpData);
}

Portal.getDropGird=function(ele){
	var ele = ele||this;
	if(!Portal.dropGird){
		var box = document.createElement('div');
		box.className=Portal.gridClassName;
		Portal.dropGird=box;
	};
	Portal.dropGird.style.display="";
	Portal.dropGird.style.height=$E.getDimensions(ele).height-4+"px";
	ele.parentNode.insertBefore(Portal.dropGird,ele.nextSibling);
	
	var elefeedObj=ele.feedObj,
	elefeedParentObj=elefeedObj.parentObj;
	Portal.dropGird.PosAndDim=Portal.getPosAndDim(Portal.dropGird);
	Portal.dropGird.parentEle=elefeedParentObj.element;
	Portal.dropGird.feedIndex=elefeedParentObj.feeds.indexOf(elefeedObj);
	return Portal.dropGird;
}

Portal.getPosAndDim=function(ele){
	var pos=$E.getPosition(ele);
	var dim=$E.getDimensions(ele);
	return {x:parseInt(pos.x),y:parseInt(pos.y),w:dim.width,h:dim.height}
}

Portal.isInner=function(curPos,elePosAndDim){
	return (elePosAndDim.x<curPos.x && elePosAndDim.x+elePosAndDim.w>curPos.x && elePosAndDim.y<curPos.y && elePosAndDim.y+elePosAndDim.h>curPos.y)?true:false;
}

Portal.draging = function(evt){
	var curPos = getEventPositionLocal(evt);
	for(var i=0;i<portalData.length;i++){
		if(Portal.isInner(curPos,portalData[i].element.PosAndDim)){//�����ĳһ����
		//console.log("��ǰ����portalet����Ϊ"+portalData[i].feeds.length)
			if(portalData[i].feeds.length<1){//�����һ����û��portalet
				portalData[i].element.appendChild(Portal.dropGird);
				Portal.dropGird.parentEle=portalData[i].element;
				Portal.dropGird.feedIndex=0;
				Portal.getDropRegion();	
				return;
			}
			if(Portal.isInner(curPos,Portal.dropGird.PosAndDim))return;//����������λ��Ϊ���߿����ڵ�λ��
			var feed=null,where=null;//�����жϹ�����������Ӧ�� feed���󡢶�Ӧ��portalet����ҳԪ�أ�����ҳԪ�ص��ϰ벿���°벿��
			for(var j=0;j<portalData[i].feeds.length;j++){
				//if(portalData[i].feeds[j].element==this) continue;//������ק��portalet�����������
				if(Portal.isInner(curPos,portalData[i].feeds[j].element.PosAndDim)){//���������ק��portalet��ĳ��feed����ǵ�������
					feed=portalData[i].feeds[j];
					if(Portal.dropGird.nextSibling==feed.element){
						//console.log("���Ԫ�������߿������");
						where="after";
					}else{
						where=(curPos.y<(feed.element.PosAndDim.y+feed.element.PosAndDim.h/2))?"before":"after";
					}
					Portal.dropGird.parentEle=portalData[i].element;
					Portal.dropGird.feedIndex= where=="after"?j+1:j;
					break;
				}
			}
			if(!feed){//�����ڣ�ȴ�������ڵ�ĳ��portalet��
				var curColFeeds=portalData[i].feeds;
				var dostIndex=curColFeeds.length-1;
				feed=curColFeeds[dostIndex];//��ȡ���е����һ��feed
				if(feed.element==this){dostIndex--;feed=curColFeeds[dostIndex];}//������ק��portalet�����������
				if(!feed)return;
				if(curPos.y>feed.element.PosAndDim.y+feed.element.PosAndDim.h){//������϶���portalet��ĳһ���ڣ�������һ�е�portalet����Ŀհ�����
					if(this.feedObj.parentObj==portalData[i]&&this.nextSibling==Portal.dropGird)return;//������϶���portalet���Ǳ��е����һ���������߿��Ѿ������򲻽��в���
					//console.log("����ק����"+portalData[i].id+"�ĵײ�");
					where="after";
					Portal.dropGird.parentEle=portalData[i].element;
					Portal.dropGird.feedIndex=dostIndex+1;
				}else{
					return;
				}
			}
			//console.log("Portal.dropGird.feedIndex: "+Portal.dropGird.feedIndex);
			Portal.notifyOver(feed.element,where);
			break;
		}
	}
}

Portal.notifyOver=function(ele,where){//���Ƶ�һ��portalet��ʱ���������߿��λ��
	switch(where){
		case "before":
			ele.parentNode.insertBefore(Portal.dropGird,ele);
			break;
		case "after":
			ele.nextSibling?ele.parentNode.insertBefore(Portal.dropGird,ele.nextSibling):ele.parentNode.appendChild(Portal.dropGird);
			break;
	}
	Portal.getDropRegion();	
}

Portal.Init = function(){//��ҳ���ϵ�portalet��portalData�����
	if(!portalData){alert("�޷����������Ϣ��portalData��");return;}
	for(var i=0;i<portalData.length;i++){
		var colEle=$(portalData[i].id);
		if(!colEle){alert("��ǰҳ���Ҳ���������Ϣ�����ṩ��idΪ "+portalData[i].id+" ��ҳ��Ԫ��");return;}
		colEle.feedObj=portalData[i];
		colEle.PosAndDim=Portal.getPosAndDim(colEle);
		portalData[i].element=colEle;
		//console.log(portalData[i].element);
		for(var j=0;j<portalData[i].feeds.length;j++){
			portalData[i].feeds[j].parentObj=portalData[i];
			var modEle=$(portalData[i].feeds[j].id);
			if(!modEle){alert("��ǰҳ���Ҳ���������Ϣ�����ṩ�� #"+portalData[i].id+" ��idΪ "+portalData[i].feeds[j].id+" ��ҳ��Ԫ��");return;}
			modEle.feedObj=portalData[i].feeds[j];			
			modEle.PosAndDim=Portal.getPosAndDim(modEle);
			portalData[i].feeds[j].element=modEle;
			//console.log(portalData[i].feeds[j].element);
		}
	}

	if(isIE6){
		var arrLi=$T("li");
		for(var i=0,len=arrLi.length;i<len;i++){
			if(arrLi[i].className=="portal-mnu"){//ie6�¶ԡ����á���ť��������˵�����
				arrLi[i].onmouseenter=function() {
					this.style.position="relative";
					this.childNodes[1].style.display = "block";
				};
				arrLi[i].onmouseleave=function() {
					this.style.position="static";
					this.childNodes[1].style.display = "none";
				};
			}
		}
	}
}

Portal.getDropRegion = function(){//������portalet���������portalData��Ӧ�Ķ����
	if(!portalData){alert("�޷����������Ϣ��portalData��");return;}
	for(var i=0,plen=portalData.length;i<plen;i++){
		if(!portalData[i])alert("ERROR: portalData["+i+"] " + portalData[i]);
		var colEle=portalData[i].element;
		colEle.PosAndDim=Portal.getPosAndDim(colEle);
		for(var j=0,flen=portalData[i].feeds.length;j<flen;j++){
			if(!portalData[i].feeds[j])alert("ERROR: portalData["+i+"].feeds["+j+"] " + portalData[i].feeds[j]);
			var modEle=portalData[i].feeds[j].element;
			modEle.PosAndDim=Portal.getPosAndDim(modEle);
		}
	}
	if(Portal.dropGird)	Portal.dropGird.PosAndDim=Portal.getPosAndDim(Portal.dropGird);
}

Portal.removePortalet=function(id){
	var ele=$(id);
	var feedobj=ele.feedObj;
	feedobj.parentObj.feeds.remove(feedobj);
	ele.parentNode.removeChild(ele);
	Portal.getDropRegion();	
}

Portal.togglePortalet=function(ele,id){
	if(ele.className=="portal-exp"){
		ele.className="portal-col";
		ele.title="չ��";
		$(id).addClassName("portal-hiddenBody");
	}else{
		ele.className="portal-exp";
		ele.title="�۵�";
		$(id).removeClassName("portal-hiddenBody");
	}
}
Portal.addPortlet=function(feedObj){
	var id=feedObj.id,widgetType=feedObj.widgetType,contentUrl=feedObj.contentUrl,title=feedObj.title;
	if(id==undefined||widgetType==undefined){Dialog.alert("����Ĳ���"); return;}
	if($(id)){Dialog.alert("�Ѿ�������Ϊ"+title+"��ģ����!");return;}
	
	var dc = new DataCollection();
	dc.add("Code",id);
	Server.sendRequest("com.nswt.portal.Portal.getPortletHtml",dc,function(response){
			var newMod=document.createElement("div");
			newMod.style.display="none";
			portalData[0].element.insertBefore(newMod,portalData[0].element.firstChild);
			newMod.outerHTML=response.get("Html");
			
			portalData[0].feeds.insert(0,feedObj);
			portalData[0].feeds[0].parentObj=portalData[0];
			var modEle=$(portalData[0].feeds[0].id);
			if(!modEle){alert("��ǰҳ���Ҳ���������Ϣ�����ṩ�� #"+portalData[0].id+" ��idΪ "+portalData[0].feeds[0].id+" ��ҳ��Ԫ��");return;}
			modEle.feedObj=portalData[0].feeds[0];
			modEle.PosAndDim=Portal.getPosAndDim(modEle);
			portalData[0].feeds[0].element=modEle;
			Portal.getDropRegion();
	});
	
}

Page.onLoad(function(){
	Portal.Init();
},1);
