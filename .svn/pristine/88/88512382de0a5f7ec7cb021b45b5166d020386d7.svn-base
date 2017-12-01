<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://www.znet.info/tag/znms/view" prefix="s"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<fmt:setBundle basename="i18n/rolePermission" var="rolePermissionBundle"/>

<fmt:message var="status" key="rolePermission.entity.status" bundle="${rolePermissionBundle}"/>
<fmt:message var="repeatRoleName" key="rolePermission.err.repeatRoleName" bundle="${rolePermissionBundle}"/>
<fmt:message var="roleName" key="rolePermission.entity.roleName" bundle="${rolePermissionBundle}"/>
<fmt:message var="editTitle" key="rolePermission.title.edit" bundle="${rolePermissionBundle}"/>
<fmt:message var="addTitle" key="rolePermission.title.add" bundle="${rolePermissionBundle}"/>
<fmt:message var="detailTitle" key="rolePermission.title.detail" bundle="${rolePermissionBundle}"/>

<fmt:message var="selectStatus" key="rolePermission.option.selectStatus" bundle="${rolePermissionBundle}"/>
<fmt:message var="notActivated" key="rolePermission.option.status.notActivated" bundle="${rolePermissionBundle}"/>
<fmt:message var="activated" key="rolePermission.option.status.activated" bundle="${rolePermissionBundle}"/>
<fmt:message var="disabled" key="rolePermission.option.status.disabled" bundle="${rolePermissionBundle}"/>

<fmt:message var="confirmOperationBtn" key="view.warn.operate" bundle="${comBundle}"/>
<fmt:message var="close" key="view.button.close" bundle="${comBundle}"/>

<script src="<%=path %>/js/snms-tree.js"></script>
<style type="text/css">
.checkbox_yes span, .checkbox_yes span:hover {background-position: -5px -58px;}
.checkbox span, .radio span {display: block;width: 16px;height: 16px;background: url(<%=path %>/images/formimg.png) no-repeat;}
.checkbox span {background-position: -5px -5px;cursor:pointer;}
.checkbox_yes span,.checkbox_yes span:hover{background-position:-5px -58px ;}
#myTree{text-align:left;}
</style>
<script>
/* var resetObject = null; */
isAdd = true;
var dummyStatusSource = [{text:'启用',value:1},{text:'禁用',value:0}];
	function queryParams(params) {
		return {
			limit : params.limit,
			offset : params.offset,
			search : params.search
		};
	}
	//基本搜索项
	function basicQueryItems(params) {
		return [ {
			type : 'input',
			key : 'roleName',
			placeholder : '${roleName}'
		}]
	}
	function showEdit(id){
		if(id=='1'){
			toastr.options = {
					  "closeButton": true,
					  "debug": true,
					  "positionClass": "toast-top-full-width",
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "2000",
					  "extendedTimeOut": "1000",
					  "showEasing": "swing",
					  "hideEasing": "linear",
					  "showMethod": "fadeIn",
					  "hideMethod": "fadeOut"
					};
			toastr.clear();
			toastr.error("无法修改系统预配置角色");
			return;
		}
		$("#titleSpan").text('${editTitle}');
		$("td.valid_error").remove();
		$(".valid_error_highlight").removeClass("valid_error_highlight");
		$("#submitBtn").show();
		initTable(true, id);
		$("#Layer1").find("input[name]").removeAttr("disabled");
 		$("#dummyStatusFlag").jqxDropDownList({disabled:false});
 		isAdd=false;
	}
	function showDetail(id){
		$("#titleSpan").text('${detailTitle}');
		$("td.valid_error").remove();
		$(".valid_error_highlight").removeClass("valid_error_highlight");
		$("#submitBtn").hide();
		initTable(false, id);
		$("#Layer1").find("input[name]").prop("disabled","disabled");
 		$("#dummyStatusFlag").jqxDropDownList({disabled:true});
	}
	function initTable(isEdit, id){
		$.ajax({
			url:"<%=path%>/rolePermission/search/"+id,
			type:"POST",
			success:function(data){
				eval("var data="+data);
				$("#formRoleName").val(data.roleName);
				$("#formRoleUuid").val(data.roleUuid);
 				$("#dummyStatusFlag").val(data.status);
				setTreePermission("myTree", data.permissions);
				$("#Layer1").css("display","table");
				isAdd = false;
			}
		});
	}
	
	function initAdd(){
		$("td.valid_error").remove();
		$(".valid_error_highlight").removeClass("valid_error_highlight");
		$("#titleSpan").text('${addTitle}');
		$("#submitBtn").show();
 		$("#dummyStatusFlag").jqxDropDownList({disabled:false});
		$("#Layer1").css("display","table");
		$("#Layer1").find("input").removeAttr("disabled");
		$("#formRoleName").val("");
		$("#formRoleUuid").val("");
 		$("#dummyStatusFlag").val(1);
		setTreePermission("myTree", "");
		isAdd = true;
	}
	
	$(document).bind("keydown", "esc", function(e){
		if(e.keyCode == 27){
			$("#Layer1").fadeOut(200);
		}
	});
	
	$(document).ready(function(){
		$("#detailForm").validate({
			rules : {
				roleName : {
					maxlength : 64,
					required : true,
					roleNameDuplicateCheck : true
				},
				description : {
					maxlength : 256
				}
			},
			messages : {
				roleName : {
					roleNameDuplicateCheck : '${repeatRoleName}'
				}
			},
			errorPlacement:function(error,element) {
				error.attr("align", "center")
				error.appendTo(element.parent().parent().next("tr"));
			},
			highlight: function(element) {
				 $(element).addClass("valid_error_highlight");
			},
			unhighlight: function(element) {
				 $(element).removeClass("valid_error_highlight");
			},
	    	onfocusout: false
		});
		
		initSnmsTree("myTree");
		$("#returnBtn").click(function(){
			$("#Layer1").fadeOut(200);
			});	
		$("#submitBtn").click(function(){
			// 先check
			$("#permissions").val(getTreePermission("myTree"));
			$("input[name]").each(function(){
				$(this).val($.trim($(this).val()));
			});
			$("#inputStatusFlag").val(($("#dummyStatusFlag").val()));
			if($("#detailForm").validate()){
				   if(isAdd){
					   $.ajax({
							url:"<%=path%>/rolePermission/merge/do",
							data:$("#detailForm").serialize(),
							type:'POST',
							success:function(data){
								$(".layer_box").hide();
								$(".operation_ribbon .search_but").trigger("click");
							}
						})
				   }
				   else{
					    $('#deleteDialog').modal({
				 			keyboard: true
				     	})
			    		$('#deleteDialog').find(".modal-body").html('${confirmOperationBtn}')
			    		$('#deleteDialog').modal('show');
				 		$('.btn-primary').off('click').on('click', function () {
				 			$.ajax({
								url:"<%=path%>/rolePermission/merge/do",
								data:$("#detailForm").serialize(),
								type:'POST',
								success:function(data){
									$(".layer_box").hide();
									$('#deleteDialog').modal('hide');
									$(".operation_ribbon .search_but").trigger("click");
								}
							})
				 		});
				   }
		 	}
		});
		$("#dummyStatusFlag").jqxDropDownList({
			source : dummyStatusSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
		$("#Layer1").hide();
	});
</script>

</head>

<body>
	<!-- 详细/修改用窗口：start -->
	<div class="layer_box" style="display:none;" id="Layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="Layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">编辑角色</span>
				</h1>
				<form id="detailForm">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td>
								<fmt:message key="rolePermission.entity.roleName" bundle="${rolePermissionBundle}"/>
							</td>
							<td>
								<input id="formRoleName" name="roleName"  type="text"  class="form-control input-sm" maxlength=64> 
							</td>
						</tr>
						<tr>	</tr>
						<tr>
							<td>
								<fmt:message key="rolePermission.entity.status" bundle="${rolePermissionBundle}"/>
							</td>
							<td>
								<div id="dummyStatusFlag" name="dummyStatusFlag"></div>
								<input id="inputStatusFlag" name="status" type="hidden">
							</td>
						</tr>
						<tr></tr>
					</table>
					<h1><fmt:message key="rolePermission.ui.label.assignPermissions" bundle="${rolePermissionBundle}"/></h1>
					<s:tree id="myTree"/>
					<input type="hidden" name="permissions" id="permissions"/>
					<input type="hidden" name="roleUuid" id="formRoleUuid">
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" id="submitBtn" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" id="returnBtn" type="button" ><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 详细/修改用窗口：end -->
		
	<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.rolePermission" bundle="${menuBundle}" var="Text2" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span></p>
		<!-- 头部菜单 end-->

		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="${roleName},${status}"
			ids="roleName,status"
			dataUrl="${pageContext.request.contextPath }/rolePermission/search"
			columnShows="1,1,1" 
			sortable="0,0,0"
			uniqueId="roleUuid"
			singleDeleteUrl="${pageContext.request.contextPath }/rolePermission/deleteSingle"
			singleDeletePrivilege="p_role_permission_delete"
			singleDetailUrl="showDetail"
			singleDetailPrivilege="p_role_permission_detail"
			singleEditUrl="showEdit"
			singleEditPrivilege="p_role_permission_edit"
			multiDeleteUrl="${pageContext.request.contextPath }/rolePermission/delete"
			multiDeletePrivilege="p_role_permission_delete"
			addUrl="initAdd"
			addPrivilege="p_role_permission_add"
			isSubStrings="0,0,0"
			addTitle="${addAlias}" />
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
