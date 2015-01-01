/**
 * 处理navTab上的查询, 会重新载入当前navTab
 * @param {Object} form
 */
function navTabSearchSubmit(formId, action, navTabId){
	var form = document.getElementById(formId);
	var $form = $(form);
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	navTab.reload(action, {data: $form.serializeArray(), navTabId:navTabId});
	return false;
}

function dialogSearchSubmit(formId,action){
	var form = document.getElementById(formId);
	var $form = $(form);
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	$.pdialog.reload(action, {data: $form.serializeArray()});
	return false;
}

function mySubmit(formId,action,method){
	var form = document.getElementById(formId);
	form.action=action;
	form.method=method;
	form.submit();
}