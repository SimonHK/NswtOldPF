function nswtpAD(PositionID) {
  this.ID        = PositionID;
  this.PosID  = 0; 
  this.ADID		  = 0;
  this.ADType	  = "";
  this.ADName	  = "";
  this.ADContent = "";
  this.PaddingLeft = 0;
  this.PaddingTop  = 0;
  this.Width = 0;
  this.Height = 0;
  this.IsHitCount = "N";
  this.UploadFilePath = "";
  this.URL = "";
  this.SiteID = 0;
  this.ShowAD  = showADContent;
}

function adClick(SiteID,URL,ADID,ADURL) {
	var sp = document.createElement("SCRIPT");
	sp.src = URL+"?SiteID="+SiteID+"&ADID="+ADID+"&URL="+ADURL;
	document.body.appendChild(sp);
}

function showADContent() {
  var content = this.ADContent;
  var str = "<div id='nswtpAD_"+this.PosID+"'>";
  var AD = eval('('+content+')');
  var count = 0;
  if(AD.ADText.length){
	  count = AD.ADText.length;
  }
  for(var i=0;i<count;i++){
	  str += "<li><a href="+AD.ADText[i].textLinkUrl+" onClick='adClick(\""+this.SiteID+"\",\""+this.URL+"\",\""+AD.ADText[i].textID+"\",\""+AD.ADText[i].textLinkUrl+"\")' style='color:"+AD.ADText[i].TextColor+";' target='_blank' title='"+AD.ADText[i].textContent+"'>"+AD.ADText[i].textContent+"</a></li>";
	  }
  str += "</div>";
  document.write(str);
}
