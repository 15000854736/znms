<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<style type="text/css">
</style>

 <%
 	String addUrl = path + "/systemLogDeleteRule/add";
    String editUrl = path + "/systemLogDeleteRule/edit";
    String detailUrl = path + "/systemLogDeleteRule/detail";
    String deleteSingleUrl = path + "/systemLogDeleteRule/deleteSingle";
    String deleteUrl = path + "/systemLogDeleteRule/deleteRuleList";
    String enableUrl = path + "/systemLogDeleteRule/enable";
	String disableUrl = path + "/systemLogDeleteRule/disable";
  %>

<script>
	var addUrl = "<%=addUrl%>";
	var editUrl = "<%=editUrl%>";
	var detailUrl = "<%=detailUrl%>";
	var enableUrl = "<%=enableUrl %>"
	var disableUrl = "<%=disableUrl %>"

	var ruleStatusSource = [{text:'请选择启用状态',value:-1},{text:'启用',value:1},{text:'禁用',value:0}];
	var matchTypeSource = [{text:'请选择匹配类型',value:-1},{text:'以什么开始',value:1},{text:'包含',value:2},
	                       {text:'以什么结束',value:3},{text:'主机名是',value:4},{text:'功能是',value:5},
	                       {text:'SQL表达式',value:6}];
	
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
			key : 'ruleName',
			placeholder : '规则名'
		},{
			type:'list',
			key:'ruleStatus',
			items:ruleStatusSource,
			width:130
		},{
			type:'list',
			key:'matchType',
			items:matchTypeSource,
			width:130
		}
	]
	}

	function initAdd(){
		$(".errMsg").hide();
		$("#layer1").css("display","table");
		$("#mergeForm input[type=text]").val("");
		$("#addSpan").show();
		$("#editSpan").hide();
		$("#detailSpan").hide();
		$("#submitBtn").show();
      }
	
	function showEdit(id){
		$(".errMsg").hide();
		$("#layer1").css("display","table");
		$("#editSpan").show();
		$("#addSpan").hide();
		$("#detailSpan").hide();
		$("#submitBtn").show();
		$.ajax({
			type:'POST',
			url:"${pageContext.request.contextPath}/systemLogDeleteRule/search/"+id,
			dataType:'json',
			success:function(data){
				$("#systemLogDeleteRuleUuid").val(data.systemLogDeleteRuleUuid);
				$("#ruleName").val(data.ruleName);
				$("#dummyRuleStatus").val(data.ruleStatus);
				$("#dummyMatchType").val(data.matchType);
				$("#matchString").val(data.matchString);
				$("#note").val(data.note);
			}
		});
      }
	
	function showDetail(id){
		$(".errMsg").hide();
		$("#layer1").css("display","table");
		$("#detailSpan").show();
		$("#editSpan").hide();
		$("#addSpan").hide();
		$("#submitBtn").hide();
		$.ajax({
			type:'POST',
			url:"${pageContext.request.contextPath}/systemLogDeleteRule/search/"+id,
			dataType:'json',
			success:function(data){
				$("#systemLogDeleteRuleUuid").val(data.systemLogDeleteRuleUuid);
				$("#ruleName").val(data.ruleName);
				$("#dummyRuleStatus").val(data.ruleStatus);
				$("#dummyMatchType").val(data.matchType);
				$("#matchString").val(data.matchString);
				$("#note").val(data.note);
			}
		});
	}
	
	/**
	 * 启用规则
	 * 按钮点击事件
	 * @param selected
	 * @param ids
	 */
	function enableBatchRule(selected,ids){
			if(ids!=""&&ids!=null){
					$.ajax({
						type:'post',
						url:enableUrl,
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							$("#commonDialog").modal('hide');
	                    	toastr.clear();
	                    	toastr.success("启用删除规则成功!");
							$("#ZZOS_Common_Table").bootstrapTable('refresh', {
								silent: true
							});
						},
					    error:function(XMLHttpRequest, textStatus, errorThrown){
					    	$("#commonDialog").modal('hide');
	                    	toastr.clear();
					    	toastr.error("加载错误!");
						}
					});
			}else{
				toastr.clear();
				toastr.warning('请在下面的列表中选择要处理的记录！', '系统提示');
			}
	}

	/**
	 * 禁用规则
	 * 按钮点击事件
	 * @param selected
	 * @param ids
	 */
	function disableBatchRule(selected,ids){
			if(ids!=""&&ids!=null){
					$.ajax({
						type:'post',
						url:disableUrl,
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							$("#commonDialog").modal('hide');
	                    	toastr.clear();
							toastr.success("禁用删除规则成功!");
							$("#ZZOS_Common_Table").bootstrapTable('refresh', {
								silent: true
							});
						},
					    error:function(XMLHttpRequest, textStatus, errorThrown){
					    	$("#commonDialog").modal('hide');
	                    	toastr.clear();
					    	toastr.error("加载错误!");
						}
					});
			}else{
				toastr.clear();
				toastr.warning('请在下面的列表中选择要处理的记录！', '系统提示');
			}
	}

	
	var ruleStatusSourceAdd = [{text:'启用',value:1},{text:'禁用',value:0}];
	var matchTypeSourceAdd = [{text:'以什么开始',value:1},{text:'包含',value:2},
	                       {text:'以什么结束',value:3},{text:'主机名是',value:4},{text:'功能是',value:5},
	                       {text:'SQL表达式',value:6}];
	$(document).ready(function(){
		$("#dummyRuleStatus").jqxDropDownList({
			source : ruleStatusSourceAdd,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 136
		});
		
		$("#dummyMatchType").jqxDropDownList({
			source : matchTypeSourceAdd,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 136
		});
		
		$("#layer1").hide();
	});
	
	function closeAddTable(){
		$("#layer1").hide();
	}
	
	function submitForm(){
		$(".errMsg").hide();
		var commonValid = validFormOnSubmitPrefab();
		if(commonValid){
			var systemLogDeleteRuleUuid= $("#systemLogDeleteRuleUuid").val();
			var ruleName= $("#ruleName").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/systemLogDeleteRule/checkRuleRepeat",
				type:"POST",
				dataType:'json',
				data:{"systemLogDeleteRuleUuid":systemLogDeleteRuleUuid, "ruleName":ruleName},
				success:function(data){
					if(!data){
						$("#ruleStatus").val($("#dummyRuleStatus").val());
						$("#matchType").val($("#dummyMatchType").val());
						$("#mergeForm").submit();
					}else{
						$("#nameRepeatError").show();
						return;
					}
				}
			});
		}
	}
</script>

</head>

<body>

		<!-- 添加/修改删除规则窗口：start -->
		<div class="layer_box" style="display:none;" id="layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="addSpan">添加删除规则</span>
				<span id="editSpan" style="display:none">编辑删除规则</span>
				<span id="detailSpan" style="display:none">查看删除规则</span>
				</h1>
				<form:form id="mergeForm" action="${pageContext.request.contextPath }/systemLogDeleteRule/merge" method="post" modelAttribute="systemLogDeleteRule">
					<form:input path="systemLogDeleteRuleUuid" type="hidden" id="systemLogDeleteRuleUuid"/>
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">名称</td>
							<td>
							<form:input id="ruleName" path="ruleName" type="text" maxlength="32" class="form-control input-sm" required="true"/>
							<span role="requiredError" class="errMsg">
								不能为空
							</span>
							<span id="nameRepeatError" class="errMsg">
								名称重复
							</span>
							</td>
							<td class="layer_table_left bold">启用状态</td>
							<td>
							<div id="dummyRuleStatus"></div>
							<form:input id="ruleStatus" path="ruleStatus" type="hidden" />
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">匹配类型</td>
							<td>
							<div id="dummyMatchType"></div>
							<form:input id="matchType" path="matchType" type="hidden"/>
							</td>
							<td class="layer_table_left bold">匹配字符</td>
							<td>
							<form:input id="matchString" path="matchString" class="form-control input-sm" maxlength="1000" required="true"/>
							<span role="requiredError" class="errMsg">
								不能为空
							</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">说明</td>
							<td>
							<form:input id="note" path="note" class="form-control input-sm" maxlength="1000"/>
							</td>
						</tr>
					</table>
				</form:form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" id="submitBtn" onclick="submitForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" id="returnBtn" type="button" onclick="closeAddTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加/修改删除规则窗口：end -->

		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<div class="znms_box">
			<p class="page_top_menu">设置&gt; <span class="bold">系统日志删除规则</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="名称,启用状态,匹配类型,匹配字符串,说明"
			ids="ruleName,ruleStatus,matchType,matchString,note"
			dataUrl="${pageContext.request.contextPath }/systemLogDeleteRule/search"
			columnShows="1,1,1,1,1" 
			isSubStrings="0,0,0,0,0"
			sortable="0,0,0,0,0"
			uniqueId="systemLogDeleteRuleUuid"
			singleDeleteUrl="${pageContext.request.contextPath }/systemLogDeleteRule/deleteSingle"
			singleDeletePrivilege="p_system_log_delete_rule_delete"
			singleDetailUrl="showDetail"
			singleDetailPrivilege="p_system_log_delete_rule_detail"
			singleEditUrl="showEdit"
			singleEditPrivilege="p_system_log_delete_rule_edit"
			multiDeleteUrl="${pageContext.request.contextPath }/systemLogDeleteRule/deleteRuleList"
			multiDeletePrivilege="p_system_log_delete_rule_delete"
			addUrl="initAdd"
			addPrivilege="p_system_log_delete_rule_add"
			addTitle="${addAlias}"
			showExport="false" 
			
			optionBtnName_1="enableBtn"
			 optionBtnTitle_1="启用"
			 optionBtnOpenType_1="_custom"
			 optionBtnUrl_1="_custom"
			 optionBtnCustomName_1="enableBatchRule"
			 optionBtnPrivilege_1="p_system_log_delete_rule_enable"
			 
			 optionBtnName_2="disableBtn"
			 optionBtnTitle_2="禁用"
			 optionBtnOpenType_2="_custom"
			 optionBtnUrl_2="_custom"
			 optionBtnCustomName_2="disableBatchRule"
			 optionBtnPrivilege_2="p_system_log_delete_rule_disable"
			/>
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
