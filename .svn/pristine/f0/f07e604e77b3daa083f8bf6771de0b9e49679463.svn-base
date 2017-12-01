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

	var useStatusSource = [{text:"请选择启用状态",value:-1},{text:"启用",value:1},{text:"禁用",value:0}];
	
	var backupCycleSource = [{text:"每天",value:1},{text:"每周",value:2},{text:"每月",value:3}];

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
				type : 'list',
				key : 'useStatus',
				items:useStatusSource,
				width:130
			}
		]
	}
	
	
	$(document).ready(function(){
		$("#layer1").hide();
		//查找账户&密码
		$.ajax({
			url:"${pageContext.request.contextPath}/backupConfiguration/findAllAccountPassword",
			type:"POST",
			dataType:"json",
			success:function(data){
				$("#dummyAccountPassword").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 136
				});
			}
		});
		
		$("#dummyBackupCycle").jqxDropDownList({
			source : backupCycleSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 136
		});
		
		$("#useStatus").change(function(){
			if($('#useStatus').is(':checked')){
				 $('#useStatus').val(true);
			  }else{
				  $('#useStatus').val(false);
			  }
		});
		
	});
	
	function initAdd(){
		$("#addTitleSpan").show();
		$("#editTitleSpan").hide();
		$("#detailTitleSpan").hide();
		$("#layer1").css("display","table");
		$(".errMsg").hide();
		$("#deviceForm input[type='text']").val("");
		//查找可用主机
		findApplicableHost("");
		$("#dummyHost").jqxDropDownList({ disabled: false });
	}
	
	function findApplicableHost(deviceUuid){
		
		//清空主机列表
		$("#dummyHost").jqxDropDownList({
			source : null,
			placeHolder:"请选择主机",
			selectedIndex : 0,
			width : 136
		});
		
		//重新构建主机列表
		$.ajax({
			url:"${pageContext.request.contextPath}/host/findDeviceHost",
			type:"POST",
			dataType:"json",
			data:{"deviceUuid":deviceUuid},
			async:false,
			success:function(data){
				$("#dummyHost").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 136
				});
			}
		});
	}
	
	function closeDeviceTable(){
		$("#layer1").hide();
	}
	
	function submitDeviceForm(){
		$(".errMsg").hide();
		var commonValid = validFormOnSubmitPrefab();
		if(commonValid){
			//判断是否有可用主机
			if(!checkisNotNull($("#dummyHost").val())){
				$("#hsotSpan").show();
				return;
			}else{
				$("#accountPasswordUuid").val($("#dummyAccountPassword").val());
				$("#hostUuid").val($("#dummyHost").val());
				$("#backupCycle").val($("#dummyBackupCycle").val());
	 			$("#deviceForm").submit();
			}
		}
	}
	
	
	function showEdit(id){
		$("#layer1").css("display","table");
		$(".errMsg").hide();
		$("#editTitleSpan").show();
		$("#addTitleSpan").hide();
		$("#detailTitleSpan").hide();
		$("#submitButton").show();
		$.ajax({
			 type:"post",
			 url:"${pageContext.request.contextPath}/backupConfiguration/findDeviceByUuid",
			 data:{'deviceUuid':id},
			 dataType:'json',
			 async:false,
			 success:function(data){
				 $("#deviceUuid").val(data.deviceUuid);
				 $("#useStatus").val(data.useStatus);
				 $("#dummyAccountPassword").val(data.accountPasswordUuid);
				 findApplicableHost(data.deviceUuid);
				 $("#dummyHost").val(data.hostUuid);
				 $("#dummyBackupCycle").val(data.backupCycle);
				 $("#content").val(data.content);
				 $("#description").val(data.description);
				 $("#dummyHost").jqxDropDownList({ disabled: true });
				 if($("#useStatus").val()=='true'){
						$("#useStatus").parent().removeClass("switch-animate switch-off").addClass("switch-on switch-animate");
					}else{
						$("#useStatus").parent().removeClass("switch-animate switch-on").addClass("switch-off switch-animate");
					}
			 }
		  });
	}
	
	function showDetail(id){
		$("#layer1").css("display","table");
		$(".errMsg").hide();
		$("#submitButton").hide();
		$("#detailTitleSpan").show();
		$("#addTitleSpan").hide();
		$("#editTitleSpan").hide();
		$.ajax({
			 type:"post",
			 url:"${pageContext.request.contextPath}/backupConfiguration/findDeviceByUuid",
			 data:{'deviceUuid':id},
			 dataType:'json',
			 success:function(data){
				 $("#deviceUuid").val(data.deviceUuid);
				 $("#useStatus").val(data.useStatus);
				 $("#dummyAccountPassword").val(data.accountPasswordUuid);
				 findApplicableHost(data.deviceUuid);
				 $("#dummyHost").val(data.hostUuid);
				 $("#dummyBackupCycle").val(data.backupCycle);
				 $("#content").val(data.content);
				 $("#description").val(data.description);
				 $("#dummyHost").jqxDropDownList({ disabled: true });
				 if($("#useStatus").val()=='true'){
						$("#useStatus").parent().removeClass("switch-animate switch-off").addClass("switch-on switch-animate");
					}else{
						$("#useStatus").parent().removeClass("switch-animate switch-on").addClass("switch-off switch-animate");
					}
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
				<span id="addTitleSpan">添加设备</span>
				<span id="editTitleSpan" style="display:none">修改设备</span>
				<span id="detailTitleSpan" style="display:none">查看设备</span>
				</h1>
				<form:form id="deviceForm" action="${pageContext.request.contextPath }/backupConfiguration/mergeDevice" method="post" modelAttribute="device">
					<form:input path="deviceUuid" type="hidden" id="deviceUuid"/>
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">启用状态</td>
							<td>
							<form:checkbox path="useStatus" id="useStatus" data-toggle="switch"/>
							<td class="layer_table_left bold">账户&密码</td>
							<td>
								<div id="dummyAccountPassword"></div>
								<input type="hidden" name="accountPasswordUuid" id="accountPasswordUuid">
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">主机</td>
							<td>
								<div id="dummyHost"></div>
								<input type="hidden" name="hostUuid" id="hostUuid">
								<span id="hsotSpan" class="errMsg"> 
									主机不能为空
								</span>
							</td>
							<td class="layer_table_left bold">备份周期</td>
							<td>	
								<div id="dummyBackupCycle"></div>
								<input type="hidden" name="backupCycle" id="backupCycle">
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">目录</td>
							<td>
							<form:input type="text" id="content" path="content" maxlength="255" required="true" class="form-control input-sm unfilter"/>
							<span role="requiredError" class="errMsg">
								不能为空
							</span>
							<td class="layer_table_left bold">描述</td>
							<td>
							<form:input type="text" id="description" path="description" maxlength="32" class="form-control input-sm unfilter"/>
							</td>
						</tr>
					</table>
				</form:form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitDeviceForm();" id="submitButton" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeDeviceTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
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
			 <li id="1"><a href="${pageContext.request.contextPath }/backupConfiguration">账户&密码</a></li>
			 <li id="2"  class="tab_hov"><a href="#">设备</a></li>
			 <li id="3"><a href="${pageContext.request.contextPath }/backupRecord">备份</a></li>
			 <li id="4"><a href="${pageContext.request.contextPath }/backupRecord/compare">比较</a></li>
			 </ul>
			
			<!-- 表主体：start -->
			<s:table id="ZZOS_Common_Table" columns="启用状态,账户&密码,主机,备份周期,目录,描述"
				ids="useStatus,accountPasswordUuid,host.hostName,backupCycle,content,description"
				dataUrl="${pageContext.request.contextPath }/backupConfiguration/device/search"
				columnShows="1,1,1,1,1,1" 
				isSubStrings="0,0,0,0,0,0"
				sortable="0,0,0,0,0,0"
				uniqueId="deviceUuid"
				singleDeleteUrl="${pageContext.request.contextPath }/backupConfiguration/device/deleteSingle"
				singleDeletePrivilege="p_backup_configuration_device_delete"
				singleDetailUrl="showDetail"
				singleDetailPrivilege="p_backup_configuration_device_detail"
				singleEditUrl="showEdit"
				singleEditPrivilege="p_backup_configuration_device_edit"
				multiDeleteUrl="${pageContext.request.contextPath }/backupConfiguration/device/delete"
				multiDeletePrivilege="p_backup_configuration_device_delete"
				addUrl="initAdd"
				addPrivilege="p_backup_configuration_device_add"
				addTitle="${addAlias}"
				showExport="false" 
				/>
			<!-- 表主体：end -->
			
			<%@ include file="../../common/footer.jsp"%>
		</div>
</body>
</html>
