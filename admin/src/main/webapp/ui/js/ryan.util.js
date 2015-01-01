/*
 * 加命名空间
 */
(function() {
  if (!window.Ryan.CommonUtil)
    window.Ryan.CommonUtil = {};
})();
(function() {
  if (!window.Ryan.SelectUtil)
    window.Ryan.SelectUtil = {};
})();

Ryan.CommonUtil.removeLeftZero = function(num){
	while(0==num.substring(0, 1)){
		num=num.substring(1);
	};
	return num;
};

//js中去空格  
//所有
Ryan.CommonUtil.trim = function($value){ 
	return $value.replace(/(\s*)/g, ""); 
};

//左右
Ryan.CommonUtil.leftRightTrim = function($value) {
	return $value.replace(/(^\s*)|(\s*$)/g, "");
};

// 左
Ryan.CommonUtil.leftTrim = function($value) {
	return $value.replace(/(^\s*)/g, "");
};

// 右
Ryan.CommonUtil.rightTrim = function($value) {
	return $value.replace(/(\s*$)/g, "");
};

// 去千位分隔符
Ryan.CommonUtil.deleteThousandsSeparator = function($value) {
	return $value.replace(/(,*)/g, "");
};


//改变元素size
Ryan.CommonUtil.elementSizeChange = function($element,size){
	if($element.value.length>size){
		$element.size=$element.value.length+1;
	}else{
		$element.size=size;
	}
};


//改变元素size
Ryan.CommonUtil.paginationSubmit = function(pageSize,targetPage,formId,submitType){
	document.getElementById(formId).innerHTML+='<input type="hidden" id="page.pageSize" name="page.pageSize" value="'+pageSize+'" />';
	document.getElementById(formId).innerHTML+='<input type="hidden" id="page.targetPage" name="page.targetPage" value="'+targetPage+'" />';
	//调用DWZ框架的提交form的方法
	if(submitType=="dialog"){
		dialogSearch(document.getElementById(formId));
	}else{
		navTabSearch(document.getElementById(formId));
	}
};

//改变元素size
Ryan.CommonUtil.searchSubmit = function(action,formId,submitType){
	document.getElementById(formId).action=action;
	//调用DWZ框架的提交form的方法
	if(submitType=="dialog"){
		dialogSearch(document.getElementById(formId));
	}else{
		navTabSearch(document.getElementById(formId));
	}
};

	
//把选中的objSelect的值 赋值给objTarget
Ryan.SelectUtil.copySelectValue = function(objSelect,objTargetName){ 
	var options = $sourceElement.options;
	for(var i = 0; i < objSelect.options.length; i++){
		if(objSelect.options[i].selected){
			document.getElementById(objTargetName).value = objSelect.options[i].value;
		}
	}
};

//判断select选项中 是否存在Value="paraValue"的Item        
Ryan.SelectUtil.selectIsExitItem = function(objSelect, objItemValue) {        
  var isExit = false;        
  for (var i = 0; i < objSelect.options.length; i++) {        
      if (objSelect.options[i].value == objItemValue) {        
          isExit = true;        
          break;        
      }        
  }        
  return isExit;        
};

//判断select选项中 是否存在Value="paraValue"的Item,存在选中它      
Ryan.SelectUtil.selectIsExitItemAndSelected = function(objSelect, objItemValue) {        
  var isExit = false;        
  for (var i = 0; i < objSelect.options.length; i++) {        
      if (objSelect.options[i].value == objItemValue) {
    	  objSelect.options[i].selected="selected";
          isExit = true;        
          break;        
      }        
  }        
  return isExit;        
};


//向select选项中 加入多个Item   
Ryan.SelectUtil.addItemToSelects = function(selectedListName,addCheckBoxName) {
	var selectedList = document.getElementById(selectedListName);
    var length = document.all(addCheckBoxName).length;   
    var exitsItems="";
    for(i=0;i<length;i++){
    	if(document.all(addCheckBoxName)[i].checked){
    		//判断是否存在        
    		if (Ryan.SelectUtil.selectIsExitItem(selectedList, document.all(addCheckBoxName)[i].value)) {
    			exitsItems=exitsItems+","+document.all(addCheckBoxName)[i].value.split(":")[1];
    		}else{
        		Ryan.SelectUtil.addItemToSelect(selectedList,document.all(addCheckBoxName)[i].value.split(":")[1],document.all(addCheckBoxName)[i].value,"reverse");
        	}
    	}
    }
    if(exitsItems!=""){
    	alert(exitsItems.substring(1)+":已经存在！");
    }
};

//向select选项中 加入一个Item    
Ryan.SelectUtil.addItemToSelect = function(objSelect, objItemText, objItemValue,order) {
	if(order=="reverse"){
		var length = objSelect.options.length;
		if(length==0){
			var varItem = new Option(objItemText, objItemValue);      
			objSelect.options.add(varItem);  
		}
	    for(var i = length; i>=0; i--){  
	    	if(i==0){
	    		objSelect.options.item(i).text = objItemText;
	    		objSelect.options.item(i).value = objItemValue; 
	    	}else if(i == length){
				var varItem = new Option(objSelect.options.item(i-1).text, objSelect.options.item(i-1).value);  
				objSelect.options.add(varItem);     
	    	}else{
	    		objSelect.options.item(i).text = objSelect.options.item(i-1).text;
	    		objSelect.options.item(i).value = objSelect.options.item(i-1).value;
	    	}
	    }  
	}else{     
		var varItem = new Option(objItemText, objItemValue);      
		objSelect.options.add(varItem);  
	}     
};

//向select选项中 加入一个Item,并选中它 
Ryan.SelectUtil.addItemToSelectAndSelected = function(objSelect, objItemText, objItemValue,order) {
	var length = objSelect.options.length;
	if(order=="reverse"){
		if(length==0){
			var varItem = new Option(objItemText, objItemValue);      
			objSelect.options.add(varItem);  
			objSelect.options[0].selected="selected";
		}
	    for(var i = length; i>=0; i--){  
	    	if(i==0){
	    		objSelect.options.item(i).text = objItemText;
	    		objSelect.options.item(i).value = objItemValue; 
	    		objSelect.options[i].selected="selected";
	    	}else if(i == length){
				var varItem = new Option(objSelect.options.item(i-1).text, objSelect.options.item(i-1).value);  
				objSelect.options.add(varItem);     
	    	}else{
	    		objSelect.options.item(i).text = objSelect.options.item(i-1).text;
	    		objSelect.options.item(i).value = objSelect.options.item(i-1).value;
	    	}
	    }  
	}else{     
		var varItem = new Option(objItemText, objItemValue);      
		objSelect.options.add(varItem);  
		objSelect.options[length].selected="selected";
	}     
};


//从select选项中 删除一个Item        
Ryan.SelectUtil.removeItemFromSelect = function(objSelect, objItemValue) {        
    //判断是否存在        
    if (Ryan.SelectUtil.selectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options.remove(i);        
                break;        
            }        
        }               
    } else {        
        alert("该select中 不存在该项");        
    }        
};

//删除select中选中的项  
Ryan.SelectUtil.removeSelectedItemFromSelect = function(objSelect) {
    var length = objSelect.options.length - 1;    
    for(var i = length; i>=0; i--){    
        if(objSelect[i].selected == true){    
            objSelect.options[i] = null;    
        }    
    }    
};

//修改select选项中 value="paraValue"的text为"paraText"        
Ryan.SelectUtil.updateItemToSelect = function(objSelect, objItemText, objItemValue) {        
    //判断是否存在        
    if (Ryan.SelectUtil.selectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options[i].text = objItemText;        
                break;        
            }        
        }               
    } else {        
        alert("该select中 不存在该项");        
    }        
};

//设置select中text="paraText"的第一个Item为选中        
Ryan.SelectUtil.selectItemByValue = function(objSelect, objItemText) {            
    //判断是否存在        
    var isExit = false;        
    for (var i = 0; i < objSelect.options.length; i++) {        
        if (objSelect.options[i].text == objItemText) {        
            objSelect.options[i].selected = true;        
            isExit = true;        
            break;        
        }        
    }              
    //Show出结果        
    if (!isExit) {       
        alert("该select中 不存在该项");        
    }        
}; 

