/**
 * 取得选中的权限，以字符串形式返回权限ID
 * 
 * @param rootId
 */
function getTreePermission(rootId) {
	var data = "";
	$("#" + rootId).find("div.tree_menu_hov").each(function() {
		if ($(this).children("img").length == 0) {
			var chkEle = $(this).find("[type='checkbox']");
			if (chkEle.prop("checked")) {
				data += "," + ($(this).children("[type='hidden']").val());
			}
		}
	});
	return data;
}

/**
 * 设置权限树选中状态
 */
function setTreePermission(rootId, permissions){
	var permissionArr;
	
	if(permissions != null && permissions.length >0){
		permissionArr = permissions.split(",");
	}
	// 先清空
	clearTreePermission(rootId);
	
	if(permissionArr != null){
		for(var iter =0;iter<permissionArr.length;iter++){
			var permission = permissionArr[iter];
			if(permission.length > 0){
				var targetInput = $("#"+rootId).find("input[value="+permission+"]");
				if(targetInput.length>0){
					targetInput.siblings("div").addClass("checkbox_yes");
					targetInput.siblings("div").children("input").prop("checked", true);
				}
			}
		}
	}
}

/**
 * 清空权限树选中状态
 */
function clearTreePermission(rootId){
	$("#"+rootId).find("div.tree_menu_hov").each(function(){
		var idInput = $(this).children("[type='hidden']");
		idInput.siblings(".checkbox").removeClass("checkbox_yes");
		idInput.siblings(".checkbox").children("input").prop("checked", false);
	})
}


/**
 * 初始化权限树
 * 
 * @param rootId
 */
function initSnmsTree(rootId) {
	$("#" + rootId).find("div.tree_menu_hov").each(
			function() {
				$(this).find("span").click(
						function() {
							var thisCheckbox = $(this).siblings("input");
							var brotherSpan = $(this).parent().parent().siblings("ul.tree_menu_ul").find("span");
							if(thisCheckbox.prop("checked")){
								$(this).parent().removeClass("checkbox_yes");		
								thisCheckbox.prop("checked", false);
								brotherSpan.parent().removeClass("checkbox_yes");
								brotherSpan.siblings("input").prop("checked", false);
							} else {
								$(this).parent().addClass("checkbox_yes");
								$(this).siblings("input").prop("checked", true);
								brotherSpan.parent().addClass("checkbox_yes");
								brotherSpan.siblings("input").prop("checked", true);
							}
						});
				if ($(this).children("img").length != 0) {
					$(this).children("img").click(
							function() {
								var branch = $(this).parent().siblings("ul.tree_menu_ul");
								if (branch.length != 0) {
									if (branch.is(":visible")) {
										branch.slideUp(200);
										$(this).attr("src","images/jia.png");
									} else {
										branch.slideDown(200);
										$(this).attr("src","images/jian.png");
									}
								}
							});
				}
			});
}