<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../../common/include.jsp"%>
<fmt:setBundle basename="i18n/host" var="hostBundle"/>
<fmt:message var="pleaseSelectStatus" key="host.option.pleaseSelectStatus" bundle="${hostBundle}"/>
<fmt:message var="statusUse" key="host.option.statusUse" bundle="${hostBundle}"/>
<fmt:message var="statusForbidden" key="host.option.statusForbidden" bundle="${hostBundle}"/>
<fmt:message var="id" key="host.entity.id" bundle="${hostBundle}"/>
<fmt:message var="hostIp" key="host.entity.hostIp" bundle="${hostBundle}"/>
<fmt:message var="hostName" key="host.entity.hostName" bundle="${hostBundle}"/>
<fmt:message var="status" key="host.entity.status" bundle="${hostBundle}"/>
<fmt:message var="curTime" key="host.entity.curTime" bundle="${hostBundle}"/>
<fmt:message var="avgTime" key="host.entity.avgTime" bundle="${hostBundle}"/>
<fmt:message var="availability" key="host.entity.availability" bundle="${hostBundle}"/>
<fmt:message var="enable" key="host.enable" bundle="${hostBundle}"/>
<fmt:message var="disable" key="host.disable" bundle="${hostBundle}"/>
<fmt:message var="hostWorkStatus" key="host.hostWorkStatus" bundle="${hostBundle}"/>
<fmt:message var="pleaseSelecthostWorkStatus" key="host.option.pleaseSelecthostWorkStatus" bundle="${hostBundle}"/>
<fmt:message var="downtime" key="host.option.down" bundle="${hostBundle}"/>
<fmt:message var="normal" key="host.option.up" bundle="${hostBundle}"/>
<fmt:message var="unknown" key="host.option.unknown" bundle="${hostBundle}"/>
<style type="text/css">
</style>

 <%
 	String addUrl = path + "/host/add";
    String editUrl = path + "/host/edit";
    String detailUrl = path + "/host/detail";
    String deleteSingleUrl = path + "/host/delete";
    String deleteUrl = path + "/host/deleteHostList";
    String enableUrl = path + "/host/enable";
	String disableUrl = path + "/host/disable";
  %>

<script>
	var addUrl = "<%=addUrl%>";
	var editUrl = "<%=editUrl%>";
	var detailUrl = "<%=detailUrl%>";
	var enableUrl = "<%=enableUrl %>"
	var disableUrl = "<%=disableUrl %>"

	var statusSource = [{text:'${pleaseSelectStatus}',value:-1},{text:'${statusUse}',value:1},{text:'${statusForbidden}',value:0}];
	var workStatusSource = [{text:'${pleaseSelecthostWorkStatus}',value:-1},{text:'${normal}',value:1},{text:'${downtime}',value:0},{text:'${unknown}',value:2}];
	
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
			key : 'hostName',
			placeholder : '${hostName}'
		}, {
			type : 'input',
			key : 'hostIp',
			placeholder : '${hostIp}'
		},{
			type:'list',
			key:'status',
			items:statusSource,
			width:130
		},{
			type:'list',
			key:'hostWorkStatus',
			items:workStatusSource,
			width:130
		}
	]
	}

	function initAdd(){
        window.location.href=addUrl;
      }
	
	function showEdit(id){
        window.location.href=editUrl+"/"+id;
      }
	
	function showDetail(id){
		window.location.href=detailUrl+"/"+id;
	}
	
	/**
	 * 启用主机
	 * 按钮点击事件
	 * @param selected
	 * @param ids
	 */
	function enableBatchHost(selected,ids){
			if(ids!=""&&ids!=null){
					$.ajax({
						type:'post',
						url:enableUrl,
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							$("#commonDialog").modal('hide');
	                    	toastr.clear();
	                    	toastr.success("启用主机成功!");
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
	 * 禁用主机
	 * 按钮点击事件
	 * @param selected
	 * @param ids
	 */
	function disableBatchHost(selected,ids){
			if(ids!=""&&ids!=null){
					$.ajax({
						type:'post',
						url:disableUrl,
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							$("#commonDialog").modal('hide');
	                    	toastr.clear();
							toastr.success("禁用主机成功!");
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

	function importHost(selected,ids){
		location.href="<%=path%>/host/import";
	}

	
</script>

</head>

<body>
		<!-- 头部菜单 start-->
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.host" bundle="${menuBundle}" var="Text2" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="${hostIp},${hostName},${status},${hostWorkStatus}"
			ids="hostIp,hostName,status,hostWorkStatus"
			dataUrl="${pageContext.request.contextPath }/host/search?hostWorkStatus=${req_data}"
			columnShows="1,1,1,1，1" 
			isSubStrings="0,0,0,0,0"
			sortable="0,0,0,0,0"
			uniqueId="id"
			singleDeleteUrl="${pageContext.request.contextPath }/host/deleteSingle"
			singleDeletePrivilege="p_host_delete"
			singleDetailUrl="showDetail"
			singleDetailPrivilege="p_host_detail"
			singleEditUrl="showEdit"
			singleEditPrivilege="p_host_edit"
			multiDeleteUrl="${pageContext.request.contextPath }/host/delete"
			multiDeletePrivilege="p_host_delete"
			addUrl="initAdd"
			addPrivilege="p_host_add"
			addTitle="${addAlias}"
			showExport="false" 
			
			optionBtnName_1="enableBtn"
			 optionBtnTitle_1="${enable}"
			 optionBtnOpenType_1="_custom"
			 optionBtnUrl_1="_custom"
			 optionBtnCustomName_1="enableBatchHost"
			 optionBtnPrivilege_1="p_host_enable"
			 
			 optionBtnName_2="disableBtn"
			 optionBtnTitle_2="${disable}"
			 optionBtnOpenType_2="_custom"
			 optionBtnUrl_2="_custom"
			 optionBtnCustomName_2="disableBatchHost"
			 optionBtnPrivilege_2="p_host_disable"
			 
			 optionBtnName_3="importBtn"
			 optionBtnTitle_3="导入"
			 optionBtnOpenType_3="_custom"
			 optionBtnUrl_3="_custom"
			 optionBtnCustomName_3="importHost"
			 optionBtnPrivilege_3="p_host_add"
			/>
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
