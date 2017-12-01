<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<fmt:setBundle basename="i18n/adminManage" var="adminManageBundle"/>
<fmt:message var="adminId" key="smAdmin.entity.adminId" bundle="${adminManageBundle}"/>
<fmt:message var="adminName" key="smAdmin.entity.adminName" bundle="${adminManageBundle}"/>
<fmt:message var="adminPwd" key="smAdmin.entity.adminPwd" bundle="${adminManageBundle}"/>
<fmt:message var="contactNumber" key="smAdmin.entity.contactNumber" bundle="${adminManageBundle}"/>
<fmt:message var="roleUuid" key="smAdmin.entity.roleUuid" bundle="${adminManageBundle}"/>
<fmt:message var="roleName" key="smAdmin.entity.roleName" bundle="${adminManageBundle}"/>
<fmt:message var="status" key="smAdmin.entity.status" bundle="${adminManageBundle}"/>

<fmt:message var="selectStatus" key="smAdmin.option.selectStatus" bundle="${adminManageBundle}"/>
<fmt:message var="notActivated" key="smAdmin.option.status.notActivated" bundle="${adminManageBundle}"/>
<fmt:message var="activated" key="smAdmin.option.status.activated" bundle="${adminManageBundle}"/>
<fmt:message var="disabled" key="smAdmin.option.status.disabled" bundle="${adminManageBundle}"/>

<fmt:message var="export" key="view.button.export" bundle="${comBundle}"/>
<fmt:message var="close" key="view.button.close" bundle="${comBundle}"/>
<fmt:message var="wrong" key="common.ui.label.wrong" bundle="${comBundle}"/>
<style type="text/css">
.layer_box,.wrong_nobox,#schoolListDiv,#firstLine,#secondLine{display:none;}
#schoolList{margin-bottom : 5px;}
</style>
<script>

	var dummyStatusSource = [{text:'${notActivated}',value:0},{text:'${activated}',value:1},{text:'${disabled}',value:2}];
	var dummyRoleSource = $.parseJSON('${roleList}');
	var roleSource = $.parseJSON('${roleSource}');
	
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
			key : 'adminId',
			placeholder : '${adminId}'
		}, {
			type : 'input',
			key : 'adminName',
			placeholder : '${adminName}'
		},{
			type:'list',
			key:'roleUuid',
			items:roleSource,
			width:130
		}
	]
	}

	// 显示编辑框
	function showEdit(id){
		//显示修改密码功能
		$("#editPasswordDiv").show();
		
		$("#editTitle").show();
		$("#detailTitle").hide();
		$("#addTitle").hide();
		$("#dummyAdminPwd").val();
		$("#confirmAdminPwd").val();
		$("span.valid_error").remove();
		$("#firstLine").hide();
		$("#secondLine").hide();
		$(".valid_error_highlight").removeClass("valid_error_highlight");
		$("#submitBtn").show();
		$("#password").hide();
		$(".layer_box").find("input").removeAttr("readonly");
		$("#adminId").prop("readonly","true");
		$(".layer_box").find("select").removeAttr("disabled");
		$("#changePwd").show();
		$("#hideChangePwd").hide();
		$(".layer_box").find("div[role='combobox']").jqxDropDownList({ disabled: false }); 
		initAdminData(id,true);
	}
	// 显示详情窗口
	function showDetail(id){
		$("#editTitle").hide();
		$("#detailTitle").show();
		$("#addTitle").hide();
		$("span.valid_error").remove();
		$("#firstLine").hide();
		$("#secondLine").hide();
		$(".valid_error_highlight").removeClass("valid_error_highlight");
		$("#submitBtn").hide();
		$("#password").hide();
		$("#changePwd").hide();
		$("#hideChangePwd").hide();
		$(".layer_box").find("input").prop("readonly","true");
		$(".layer_box").find("select").prop("disabled","disabled");
		$(".layer_box").find("div[role='combobox']").jqxDropDownList({ disabled: true }); 
		initAdminData(id,false);
	}
	function initAdminData(id,isEdit){
		$.ajax({
			url:"<%=path%>/adminManage/search/"+id,
			type:"POST",
			success:function(data){
				eval("var data="+data);
				$("#adminId").val(data.adminId);
				$("#adminName").val(data.adminName);
				$("#formerAdminPwd").val(data.adminPwd);
				$("#contactNumber").val(data.contactNumber);
				$("#adminUuid").val(data.adminUuid);
				$("#dummyRole").val(data.roleUuid);
				$("#dummyStatusFlag").val(data.status);
				if(isEdit){
					if(data.adminId=='admin'){
						$("#dummyRole").jqxDropDownList({ disabled: true }); 
						$("#dummyStatusFlag").jqxDropDownList({ disabled: true }); 
					}
				}
				$(".layer_box").css("display","table");
			}
		});
	} 
	
	// 显示新增窗口
	function initAdd(){
		$("#editTitle").hide();
		$("#detailTitle").hide();
		$("#addTitle").show();
		$("#submitBtn").show();
		$("#password").show();
		$("#changePwd").hide();
		$("#hideChangePwd").hide();
		$(".layer_box").css("display","table");
		$(".layer_box").find("input").removeAttr("readonly");
		$("#adminId").removeAttr("disabled");
		$(".layer_box").find("select").removeAttr("disabled");
		$(".layer_box").find("input[name]").val("");
		$(".layer_box").find("div[role='combobox']").jqxDropDownList({ disabled: false }); 
	}
	
	$(document).bind("keydown", "esc", function(e){
		if(e.keyCode == 27){
			$(".layer_box").fadeOut(200);
		}
	});

	$(document).ready(function(){
		
		//隐藏修改密码功能
		$("#editPasswordDiv").hide();
		
		var flag = '${result_msg}';
		if(flag == "update_success"){
			toastr.success($("#updateSuccess").text());
		}else if(flag == "update_failed"){
			toastr.error($("#updateFailed").text());
		}else if(flag == "save_success"){
			toastr.success($("#addSuccess").text());
		}else if(flag == "save_failed"){
			toastr.error($("#addFailed").text());
		}
		
		$("#detailForm").validate({
			rules : {
				adminId : {
					maxlength : 64,
					required : true,
					remote : "<%=path%>/adminManage/checkAccount"+$("#adminId").val()
				},
				adminName : {
					maxlength : 64,
					required : true
				},
				dummyAdminPwd : {
					maxlength : 64,
					conditionRequired : "formerAdminPwd",
					minlength : 6
				},
				confirmAdminPwd : {
					maxlength : 64,
					conditionRequired : "formerAdminPwd",
					minlength : 6,
					equalTo : "#dummyAdminPwd"
				},
				contactNumber : {
					maxlength : 64,
					isNum : true
				}
			},
			messages : {
				adminId : {
					remote : "该管理员已存在"
				}
			},
			errorElement : "span",
			errorPlacement:function(error,element) {
				var id=error.attr("id");
				if(id=='adminName-error'){
					error.appendTo($("#adminNameMsg"));
					$("#firstLine").show();
				} else if(id=="adminId-error"){
					error.appendTo($("#adminIdMsg"));
					$("#firstLine").show();
				} else if(id=="dummyAdminPwd-error"){
					error.appendTo($("#dummyAdminPwdMsg"));
					$("#secondLine").show();
				} else if(id=="confirmAdminPwd-error"){
					error.appendTo($("#confirmAdminPwdMsg"));
					$("#secondLine").show();
				} else if(id=="contactNumber-error"){
					error.appendTo($("#contactNumberMsg"));
					$("#thirdLine").show();
				}
			},
			highlight: function(element) {
				 $(element).addClass("valid_error_highlight");
			},
			unhighlight: function(element) {
				 $(element).removeClass("valid_error_highlight");
			},
	    	onfocusout: false
		});
		
		$("#returnBtn,#closeDetailWindow").click(function(){$(".layer_box").fadeOut(50);});	
		$("#submitBtn").click(function(){
			$("#adminPwd").val($("#dummyAdminPwd").val());
			$("#inputStatusFlag").val(($("#dummyStatusFlag").val()));
			$("#adminPwd").val(hex_md5($("#dummyAdminPwd").val()));
			
			if($("#password").is(":visible") && $.trim($("#dummyAdminPwd").val()) != ""){
				// do nothing
			} else {
				$("#adminPwd").val($("#formerAdminPwd").val());
			}
			
			$("#formRoleUuid").val($("#dummyRole").val());
			if($("#detailForm").valid()){
				$.ajax({
					url:"<%=path%>/adminManage/merge/do",
					data:$("#detailForm").serialize(),
					type:'POST',
					success:function(data){
						$(".layer_box").hide();
						$(".operation_ribbon .search_but").trigger("click");
					}
				})
			}
		});
		$("#changePwd").click(function(){
			$("#password").fadeIn(450);
		});
		$("#changePwd").click(function(){
			$("#password").fadeIn(450);
			$("#changePwd").hide();
			$("#hideChangePwd").show();
		});
		$("#hideChangePwd").click(function(){
			$("#password").fadeOut(450);
			$("#hideChangePwd").hide();
			$("#changePwd").show();
			$("#dummyAdminPwdMsg").text('');
			$("#confirmAdminPwdMsg").text('');
		});

		$("#dummyRole").jqxDropDownList({
				source : dummyRoleSource,
				displayMember : 'roleName',
				valueMember : 'roleUuid',
				selectedIndex : 0,
				width : 136
			});
		$("#dummyStatusFlag").jqxDropDownList({
			source : dummyStatusSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
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
				<span id="addTitle">添加用户</span>
				<span id="editTitle" style="display:none">修改用户</span>
				<span id="detailTitle" style="display:none">查看用户</span>
				</h1>
				<form id="detailForm">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.adminId" bundle="${adminManageBundle}"/></td>
							<td><input id="adminId" name="adminId" type="text" maxlength="64" class="form-control input-sm"/></td>
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.adminName" bundle="${adminManageBundle}"/></td>
							<td><input id="adminName" name="adminName" type="text" maxlength="64" class="form-control input-sm"/></td>
						</tr>
						<tr id="firstLine">
							<td id="adminIdMsg" colspan="2" align="center"></td>
							<td id="adminNameMsg" colspan="2" align="center"></td>
						</tr>
						<tr id="password">
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.adminPwd" bundle="${adminManageBundle}"/></td>
							<td>
								<input id="dummyAdminPwd" name="dummyAdminPwd" type="password" maxlength=64 class="form-control input-sm"/>
								<input id="adminPwd" name="adminPwd" type="hidden"/>
								<input id="formerAdminPwd" type="hidden"/>
							</td>
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.confirmAdminPwd" bundle="${adminManageBundle}"/></td>
							<td><input id="confirmAdminPwd" type="password" name="confirmAdminPwd" maxlength=64 class="form-control input-sm"></td>
						</tr>
						<tr id="secondLine">
							<td id="dummyAdminPwdMsg" colspan="2" align="center"></td>
							<td id="confirmAdminPwdMsg" colspan="2" align="center"></td>
						</tr>
						<tr>
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.contactNumber" bundle="${adminManageBundle}"/></td>
							<td><input id="contactNumber" name="contactNumber" type="text" maxlength=64 class="form-control input-sm"/></td>
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.roleName" bundle="${adminManageBundle}"/></td>
							<td>
								<div id="dummyRole"></div>
								<input id="formRoleUuid" name="roleUuid" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td id="contactNumberMsg" colspan="2" align="center"></td>
							<td colspan="2" align="center"></td>
						</tr>
						<tr id="thirdLine">
							<td class="layer_table_left bold"><fmt:message key="smAdmin.ui.label.status" bundle="${adminManageBundle}"/></td>
							<td>
								<div id="dummyStatusFlag"></div>
								<input id="inputStatusFlag" name="status" type="hidden">
							</td>
							<td>&nbsp;</td>
							<td>
								<div class="R" id="editPasswordDiv">
									<a href="#" id="changePwd">&gt;&gt;<fmt:message key="smAdmin.ui.label.changePwd" bundle="${adminManageBundle}"/></a>
									<a href="#" id="hideChangePwd" style="display: none">&lt;&lt;<fmt:message key="smAdmin.ui.label.hideChangePwd" bundle="${adminManageBundle}"/></a>
								</div>
							</td>
						</tr>
						<tr></tr>
						<input id="adminUuid" name="adminUuid" type="hidden"/>
					</table>
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
		    <fmt:message key="menu.item.setting.userManage" bundle="${menuBundle}" var="Text2" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="${adminId},${adminName},${contactNumber},${roleName},${status}"
			ids="adminId,adminName,contactNumber,roleName,status"
			dataUrl="${pageContext.request.contextPath }/adminManage/search"
			columnShows="1,1,1,1,1" 
			isSubStrings="1,1,0,1,0"
			maxLengths="10,5,10,10,10"
			sortable="0,0,0,0,0"
			uniqueId="adminUuid"
			singleDeleteUrl="${pageContext.request.contextPath }/adminManage/deleteSingle"
			singleDeletePrivilege="p_admin_manage_delete"
			singleDetailUrl="showDetail"
			singleDetailPrivilege="p_admin_manage_detail"
			singleEditUrl="showEdit"
			singleEditPrivilege="p_admin_manage_edit"
			multiDeleteUrl="${pageContext.request.contextPath }/adminManage/delete"
			multiDeletePrivilege="p_admin_manage_delete"
			addUrl="initAdd"
			addPrivilege="p_admin_manage_add"
			addTitle="${addAlias}"
			showExport="false" />
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
