<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../../common/include.jsp"%>
<style type="text/css">
</style>

<script>
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
				key : 'apName',
				placeholder : '名称'
			}
		]
	}
	
	
	$(document).ready(function(){
		$("#layer1").hide();
	});
	
	function initAdd(){
		$("#layer1").css("display","table")
		$(".errMsg").hide();
		$("#apForm input").val("");
		$("#submitButton").show();
	}
	
	function closeApTable(){
		$("#layer1").hide();
	}
	
	function submitApForm(){
		$("span[role='requiredError']").hide();
		if(!flag || !passwordFlag || !enablePasswordFlag){
			return;
		}else{
			var commonValid = validFormOnSubmitPrefab();
			if(commonValid){
				$("#apForm").submit();
			}
		}
	}
	
	//校验名称是否重复
	var flag = true;
	function chekcApNameRepeat(){
		var apName = $("#apName").val();
		if(checkisNotNull(apName)){
			var accountPasswordUuid = $("#accountPasswordUuid").val();
			$.ajax({
				 type:"post",
				 url:"${pageContext.request.contextPath}/backupConfiguration/checkApName",
				 data:{'apName':apName,'accountPasswordUuid':accountPasswordUuid},
				 // 必须同步
				 async:false,
				 success:function(result){
					 $("#apNameRequiredError").hide();
					 flag = result;
					 if(!flag){
						 $("#apNameRepeatError").show();
					 }else{
						 $("#apNameRepeatError").hide();
					 }
				 }
			  });
		}
	}
	
	//校验密码是否一致
	var passwordFlag = true;
	function checkPasswordMatch(){
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		if(checkisNotNull(confirmPassword)){
			if(confirmPassword != password){
				passwordFlag = false;
				$("#passwordNotMatchError").show();
			}else{
				passwordFlag = true;
				$("#passwordNotMatchError").hide();
			}
		}
	}
	
	//校验启用密码是否一致
	var enablePasswordFlag = true;
	function checkEnablePasswordMatch(){
		var enablePassword = $("#enablePassword").val();
		var confirmEnablePassword = $("#confirmEnablePassword").val();
		if(checkisNotNull(confirmEnablePassword)){
			if(confirmEnablePassword != enablePassword){
				enablePasswordFlag = false;
				$("#enablePasswordNotMatchError").show();
			}else{
				enablePasswordFlag = true;
				$("#enablePasswordNotMatchError").hide();
			}
		}
	}
	
	function showEdit(id){
		$("#layer1").css("display","table")
		$(".errMsg").hide();
		$("#submitButton").show();
		$.ajax({
			 type:"post",
			 url:"${pageContext.request.contextPath}/backupConfiguration/findAPByUuid",
			 data:{'accountPasswordUuid':id},
			 dataType:'json',
			 success:function(data){
				 $("#accountPasswordUuid").val(data.accountPasswordUuid);
				 $("#apName").val(data.apName);
				 $("#certificateName").val(data.certificateName);
				 $("#password").val(data.password);
				 $("#confirmPassword").val(data.confirmPassword);
				 $("#enablePassword").val(data.enablePassword);
				 $("#confirmEnablePassword").val(data.confirmEnablePassword);
			 }
		  });
	}
	
	function showDetail(id){
		$("#layer1").css("display","table");
		$(".errMsg").hide();
		$("#submitButton").hide();
		$.ajax({
			 type:"post",
			 url:"${pageContext.request.contextPath}/backupConfiguration/findAPByUuid",
			 data:{'accountPasswordUuid':id},
			 dataType:'json',
			 success:function(data){
				 $("#accountPasswordUuid").val(data.accountPasswordUuid);
				 $("#apName").val(data.apName);
				 $("#certificateName").val(data.certificateName);
				 $("#password").val(data.password);
				 $("#confirmPassword").val(data.confirmPassword);
				 $("#enablePassword").val(data.enablePassword);
				 $("#confirmEnablePassword").val(data.confirmEnablePassword);
			 }
		  });
	}
</script>

</head>

<body>

		<!-- 添加账户&密码：start -->
		<div class="layer_box" style="display:none;" id="layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加账户&密码</span>
				</h1>
				<form:form id="apForm" action="${pageContext.request.contextPath }/backupConfiguration/mergeAccountPassword" method="post" modelAttribute="accountPassword">
					<form:input path="accountPasswordUuid" type="hidden" id="accountPasswordUuid"/>
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">名称</td>
							<td>
							<form:input type="text" id="apName" path="apName" maxlength="32" required="true" class="form-control input-sm unfilter" onblur="chekcApNameRepeat();"/>
							<span role="requiredError" class="errMsg" id="apNameRequiredError">
								不能为空
							</span>
							<span id="apNameRepeatError" class="errMsg">
								名称重复
							</span>
							<td class="layer_table_left bold">认证用户名</td>
							<td>
							<form:input type="text" id="certificateName" path="certificateName" maxlength="32" required="true" class="form-control input-sm unfilter"/>
							<span role="requiredError" class="errMsg">
								不能为空
							</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">密码</td>
							<td>
							<form:input type="password" id="password" path="password" maxlength="32" required="true" class="form-control input-sm unfilter"/>
							<span role="requiredError" class="errMsg">
								不能为空
							</span>
							<td class="layer_table_left bold">确认密码</td>
							<td>
							<form:input type="password" id="confirmPassword" path="confirmPassword" maxlength="32" required="true" class="form-control input-sm unfilter" onblur="checkPasswordMatch();"/>
							<span role="requiredError" class="errMsg" id="confirmPasswordRequiredError">
								不能为空
							</span>
							<span id="passwordNotMatchError" class="errMsg">
								密码不一致
							</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">启用密码</td>
							<td>
							<form:input type="password" id="enablePassword" path="enablePassword" maxlength="32" required="true" class="form-control input-sm unfilter"/>
							<span role="requiredError" class="errMsg">
								不能为空
							</span>
							<td class="layer_table_left bold">确认启用密码</td>
							<td>
							<form:input type="password" id="confirmEnablePassword" path="confirmEnablePassword" maxlength="32" required="true" class="form-control input-sm unfilter" onblur="checkEnablePasswordMatch();"/>
							<span role="requiredError" class="errMsg" id="confirmEnablePasswordRequiredError">
								不能为空
							</span>
							<span id="enablePasswordNotMatchError" class="errMsg">
								密码不一致
							</span>
							</td>
						</tr>
					</table>
				</form:form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitApForm();" id="submitButton" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeApTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加账户&密码：end -->
	
			
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		
		<div class="znms_box">
			<p class="page_top_menu">设置&gt; 配置备份</p>
			
			<ul class="menu_mod_box">
			 <li id="1"  class="tab_hov"><a href="#">账户&密码</a></li>
			 <li id="2"><a href="${pageContext.request.contextPath }/backupConfiguration/device">设备</a></li>
			 <li id="3"><a href="${pageContext.request.contextPath }/backupRecord">备份</a></li>
			 <li id="4"><a href="${pageContext.request.contextPath }/backupRecord/compare">比较</a></li>
			 </ul>
			
			<!-- 表主体：start -->
			<s:table id="ZZOS_Common_Table" columns="名称,认证用户名"
				ids="apName,certificateName"
				dataUrl="${pageContext.request.contextPath }/backupConfiguration/accountPassword/search"
				columnShows="1,1" 
				isSubStrings="0,0"
				sortable="0,0"
				uniqueId="accountPasswordUuid"
				singleDeleteUrl="${pageContext.request.contextPath }/backupConfiguration/accountPassword/deleteSingle"
				singleDeletePrivilege="p_backup_configuration_ap_delete"
				singleDetailUrl="showDetail"
				singleDetailPrivilege="p_backup_configuration_ap_detail"
				singleEditUrl="showEdit"
				singleEditPrivilege="p_backup_configuration_ap_edit"
				multiDeleteUrl="${pageContext.request.contextPath }/backupConfiguration/accountPassword/delete"
				multiDeletePrivilege="p_backup_configuration_ap_delete"
				addUrl="initAdd"
				addPrivilege="p_backup_configuration_ap_add"
				addTitle="${addAlias}"
				showExport="false" 
				/>
			<!-- 表主体：end -->
			
			<%@ include file="../../common/footer.jsp"%>
		</div>
</body>
</html>
