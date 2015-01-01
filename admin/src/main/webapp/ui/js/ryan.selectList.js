(function($) {
    $.extend($.fn, {
	    appendSelectElement:function(hidSelectedName,viewSelectedName,selectList){
	    	if(selectList.length>0){
		    	var selectedElementStr="";
		    	var selectElements = selectList.split(",");  
		    	var existSelectedElement="";
		    	var i=0;
		        $.each(selectElements,function() {  
		        	if($("#"+viewSelectedName+(selectElements[i].split(":"))[0]).length>0){
		        		existSelectedElement = existSelectedElement+(selectElements[i].split(":"))[1];
		        	}else{
			        	var selectedElementTempStr =
				        	"<div id='"+viewSelectedName+(selectElements[i].split(":"))[0]+"' >" +
				        	"<input type='checkbox' id='"+viewSelectedName+"' name='"+viewSelectedName+"' value='"+viewSelectedName+(selectElements[i].split(":"))[0]+"' />" +(selectElements[i].split(":"))[1]+
				        	"<input type='hidden' id='"+hidSelectedName+"' name='"+hidSelectedName+"' value='"+selectElements[i]+"'/>" +
				        	"</div>" ;
			        	selectedElementStr=selectedElementTempStr + selectedElementStr;
		        	}
			        i++;
		        });  
		        if(existSelectedElement.length>0){
		        	alert("["+existSelectedElement+"]已经在选中列表中，无需重复添加！");
		        }else{
			        this.html(selectedElementStr+this.html()); 
		        }
	    	}
	    },
	    
	    
	    deleteSelectOrganization:function(){
	    	var i=0;
    	    $(this+"[checked]").each(function(){
    	    	$("#"+this.value).remove();
	    	});
	    },
	    
	    
	    addSelectOrganization:function(hidSelectedName,viewSelectedName,selectedOrganizationList){
	    	$(this+"[checked]").each(function(){
	    		$("#"+selectedOrganizationList).appendSelectElement(hidSelectedName,viewSelectedName,this.value);
	    	});
	    }
	    
    });
})(jQuery);