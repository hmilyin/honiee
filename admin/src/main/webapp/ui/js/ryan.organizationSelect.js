function selectOrganizationOnClosed(param) {
	alert(document.getElementById(param.selectedOrganization));
	return true;
}

function deleteSelectOrganization(selectedListName) {
	var selectedList = document.getElementById(selectedListName);
	new Ryan.SelectUtil.removeSelectedItemFromSelect(selectedList);
	return false;
};

function generateSelectOrganization(selectedListName) {
	var selectedList = document.getElementById(selectedListName);
	new Ryan.SelectUtil.removeSelectedItemFromSelect(selectedList);
	return false;
};

function addSelectOrganization(selectedListName,addCheckBoxName) {
	new Ryan.SelectUtil.addItemToSelects(selectedListName,addCheckBoxName);
    return false;
};