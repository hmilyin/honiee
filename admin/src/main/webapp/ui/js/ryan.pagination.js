/*
 * 加命名空间Ryan
 */
(function() {
  if (!window.Ryan)
    window.Ryan = {};
})();

Ryan.Config = {
		dotTop                   : '../ui/themes/default/images/pagination/dot_top.gif',
		dotPre                   : '../ui/themes/default/images/pagination/dot_pre.gif',
		dotNext                  : '../ui/themes/default/images/pagination/dot_next.gif',
		dotEnd                   : '../ui/themes/default/images/pagination/dot_end.gif',
		dotTopUnavailable        : '../ui/themes/default/images/pagination/dot_top_.gif',
		dotPreUnavailable        : '../ui/themes/default/images/pagination/dot_pre_.gif',
		dotNextUnavailable       : '../ui/themes/default/images/pagination/dot_next_.gif',
		dotEndUnavailable        : '../ui/themes/default/images/pagination/dot_end_.gif',
		dotGo                    : '../ui/themes/default/images/pagination/dot_go.gif',
		dotOk                    : '../ui/themes/default/images/pagination/dot_ok.gif'
};

Ryan.Pagination = function(pageSize, targetPage, totalRecord, totalPage, paginationBarId, formId ,submitType,paginationName){
    this.paginationBar = document.getElementById(paginationBarId);
    this.pageSizeName = formId+".pageSize";
    this.targetPageName = formId+".targetPage";
    this.pageSizeHiddenName = formId+".pageSizeHidden";
    this.targetPageHiddenName = formId+".targetPageHidden";
    this.render = function(){
        var paginationBarStr=
        	"\u6bcf\u9875\u663e\u793a&nbsp;" +
        	"<input type='text' id="+this.pageSizeName+" name="+this.pageSizeName+" onKeyUp="+paginationName+".checkPageSize(this,'"+this.pageSizeHiddenName+"') size='4' value="+pageSize+" />&nbsp;" +
    		"<img src='"+Ryan.Config.dotOk+"' width='14' height='13' onclick="+paginationName+".paginationSubmit('"+this.pageSizeName+"','"+this.targetPageName+"','"+formId+"','"+submitType+"') />"+
    		"\u6761&nbsp;" +
    		",\u5171"+totalRecord+"\u6761&nbsp;&nbsp;&nbsp;\u7b2c"+targetPage+"\u9875&frasl;\u5171"+totalPage+"\u9875&nbsp;&nbsp;&nbsp;";
        if(parseInt(Ryan.CommonUtil.deleteThousandsSeparator(targetPage))<=1){
            paginationBarStr = paginationBarStr+"<img src='"+Ryan.Config.dotTopUnavailable+"' width='15' height='11'>&nbsp;&nbsp;&nbsp;" +
    			"<img src='"+Ryan.Config.dotPreUnavailable+"' width='6' height='11'>&nbsp;&nbsp;&nbsp;" ;
        } else{
            paginationBarStr = paginationBarStr+"<img src='"+Ryan.Config.dotTop+"' width='15' height='11' onclick="+paginationName+".topPageSubmit('"+this.pageSizeName+"','"+formId+"','"+submitType+"') >&nbsp;&nbsp;&nbsp;" +
    			"<img src='"+Ryan.Config.dotPre+"' width='6' height='11' onclick="+paginationName+".prePageSubmit('"+this.pageSizeName+"','"+this.targetPageName+"','"+formId+"','"+submitType+"') >&nbsp;&nbsp;&nbsp;";
        }
        if(parseInt(Ryan.CommonUtil.deleteThousandsSeparator(targetPage))>=parseInt(Ryan.CommonUtil.deleteThousandsSeparator(totalPage))){
            paginationBarStr = paginationBarStr+"<img src='"+Ryan.Config.dotNextUnavailable+"' width='6' height='11'>&nbsp;&nbsp;&nbsp;" +
    			"<img src='"+Ryan.Config.dotEndUnavailable+"' width='15' height='11'>&nbsp;&nbsp;&nbsp;";
        }else{
            paginationBarStr = paginationBarStr+"<img src='"+Ryan.Config.dotNext+"' width='6' height='11' onclick="+paginationName+".nextPageSubmit('"+this.pageSizeName+"','"+this.targetPageName+"','"+formId+"','"+submitType+"') >&nbsp;&nbsp;&nbsp;" +
    			"<img src='"+Ryan.Config.dotEnd+"' width='15' height='11' onclick="+paginationName+".endPageSubmit('"+this.pageSizeName+"','"+totalPage+"','"+formId+"','"+submitType+"') >&nbsp;&nbsp;&nbsp;";
        }
        paginationBarStr = paginationBarStr+"\u8df3\u8f6c\u5230&nbsp;" +
    		"<input type='text' id="+this.targetPageName+" name="+this.targetPageName+" onKeyUp="+paginationName+".checkTargetPage(this,'"+this.targetPageHiddenName+"'); size='2' value="+targetPage+" />&nbsp;" +
    		"<img src='"+Ryan.Config.dotGo+"' width='16' height='16' onclick="+paginationName+".paginationSubmit('"+this.pageSizeName+"','"+this.targetPageName+"','"+formId+"','"+submitType+"') />"+
    		"&nbsp;\u9875" +
        	"<input type='hidden' id="+this.pageSizeHiddenName+" name="+this.pageSizeHiddenName+" value='"+pageSize+"' />" +
    		"<input type='hidden' id="+this.targetPageHiddenName+" name="+this.targetPageHiddenName+" value='"+targetPage+"' />";
        this.paginationBar.innerHTML=paginationBarStr;
    };

	//跳转首页
	this.topPageSubmit = function(pageSizeName,formId,submitType){
	  	var pageSize = document.getElementById(pageSizeName).value;
	  	var targetPage = 1;
	  	if(Ryan.CommonUtil.trim(pageSize)==""){
	  		alert("每页显示数量不能为空！");
	  		return false;
	  	}
	  	Ryan.CommonUtil.paginationSubmit(pageSize,targetPage,formId,submitType);
  	};

	//跳转上一页
	this.prePageSubmit = function(pageSizeName,targetPageName,formId,submitType){
	  	var pageSize = Ryan.CommonUtil.deleteThousandsSeparator(document.getElementById(pageSizeName).value);
	  	var targetPage = Ryan.CommonUtil.deleteThousandsSeparator(document.getElementById(targetPageName).value);
	  	if(Ryan.CommonUtil.trim(pageSize)==""){
	  		alert("每页显示数量不能为空！");
	  		return false;
	  	}
	  	if(Ryan.CommonUtil.trim(targetPage)==""){
	  		alert("跳转目标页不能为空！");
	  		return false;
	  	}
	  	if(parseInt(targetPage)>1){
		  	targetPage = parseInt(Ryan.CommonUtil.deleteThousandsSeparator(targetPage))-1;
		  	Ryan.CommonUtil.paginationSubmit(pageSize,targetPage,formId,submitType);
	  	}else{
	  		return false;
	  	}
  	};

	//跳转下一页
	this.nextPageSubmit = function(pageSizeName,targetPageName,formId,submitType){
	  	var pageSize = document.getElementById(pageSizeName).value;
	  	var targetPage = document.getElementById(targetPageName).value;
	  	if(Ryan.CommonUtil.trim(pageSize)==""){
	  		alert("每页显示数量不能为空！");
	  		return false;
	  	}
	  	if(Ryan.CommonUtil.trim(targetPage)==""){
	  		alert("跳转目标页不能为空！");
	  		return false;
	  	}
	  	targetPage = parseInt(Ryan.CommonUtil.deleteThousandsSeparator(targetPage))+1;
	  	Ryan.CommonUtil.paginationSubmit(pageSize,targetPage,formId,submitType);
  	};

	//跳转末页
	this.endPageSubmit = function(pageSizeName,totalPage,formId,submitType){
	  	var pageSize = document.getElementById(pageSizeName).value;
	  	var targetPage = totalPage;
	  	if(Ryan.CommonUtil.trim(pageSize)==""){
	  		alert("每页显示数量不能为空！");
	  		return false;
	  	}
	  	if(Ryan.CommonUtil.trim(targetPage)==""){
	  		alert("跳转目标页不能为空！");
	  		return false;
	  	}
	  	if(parseInt(targetPage)==0){
	  		targetPage=1;
	  	}
	  	Ryan.CommonUtil.paginationSubmit(pageSize,targetPage,formId,submitType);
  	};

	this.paginationSubmit = function(pageSizeName,targetPageName,formId,submitType){
	  	var pageSize = document.getElementById(pageSizeName).value;
	  	var targetPage = document.getElementById(targetPageName).value;
	  	Ryan.CommonUtil.paginationSubmit(pageSize,targetPage,formId,submitType);
  	};

  	this.checkPageSize = function($element,pageSizeHiddenName){
	  	if(event.keyCode!="37"&&event.keyCode!="39"){
	  		var num =Ryan.CommonUtil.deleteThousandsSeparator(Ryan.CommonUtil.trim($element.value));
	  		$element.value=num;
	  		if(typeof num!='undefined'&&num!=""){
	  			if(num==0){
	  				$element.value=document.getElementById(pageSizeHiddenName).value;
	  			}else{
	  				$element.value = Ryan.CommonUtil.removeLeftZero($element.value);
	  				if (/\D/g.test($element.value)){
	  					$element.value=document.getElementById(pageSizeHiddenName).value;
	  				}else{
	  					document.getElementById(pageSizeHiddenName).value=$element.value;
	  				};
	  			};
	  		}else{
	  			document.getElementById(pageSizeHiddenName).value=$element.value;
	  		};
	  	}
  	};

  	this.checkTargetPage = function($element,targetPageHiddenName){
	  	if(event.keyCode!="37"&&event.keyCode!="39"){
	  		var num =Ryan.CommonUtil.deleteThousandsSeparator(Ryan.CommonUtil.trim($element.value));
	  		$element.value=num;
	  		if(typeof num!='undefined'&&num!=""){
	  			if(num==0){
	  				$element.value=document.getElementById(targetPageHiddenName).value;
	  			}else{
	  				$element.value = Ryan.CommonUtil.removeLeftZero($element.value);
	  				if (/\D/g.test($element.value)){
	  					$element.value=document.getElementById(targetPageHiddenName).value;
	  				}else{
	  					document.getElementById(targetPageHiddenName).value=$element.value;
	  				};
	  			}
	  		}else{
	  			document.getElementById(targetPageHiddenName).value=$element.value;
	  		};
	  	}
	  	Ryan.CommonUtil.elementSizeChange($element,2);
  	};
};