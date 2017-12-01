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

 <%
 	String addUrl = path + "/thresholdValue/add";
    String editUrl = path + "/thresholdValue/edit";
    String detailUrl = path + "/thresholdValue/detail";
  %>

<script>
	var addUrl = "<%=addUrl%>";
	var editUrl = "<%=editUrl%>";
	var detailUrl = "<%=detailUrl%>";
	
	var statusSource = [{text:"请选择启用状态",value:-1},{text:"禁用",value:0},{text:"启用",value:1}];
	
	function queryParams(params) {
		return {
			limit : params.limit,
			offset : params.offset,
			search : params.search
		};
	}
	//基本搜索项
	function basicQueryItems(params) {
		return [ 
		         {
					type:'list',
					key:'hostUuid',
					items:getHostJSON(),
					width:130
				},
				{
					type:'list',
					key:'graphUuid',
					items:getGraphJSON(),
					width:150
				},
				{
					type:'list',
					key:'status',
					items:statusSource,
					width:130
				},
				{
					type : 'input',
					key : 'thresholdValueName',
					placeholder : '阈值名称'
				}
			   ]
	}
	
	//查找主机
	function getHostJSON(){
		var hostList ={};
		 $.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/host/findHostJson",
				dataType:'json',
				success:function(data){
					hostList =data;
				},
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
				}
			});
		 return hostList;
	}
	
	//查找图形
	function getGraphJSON(){
		var graphList ={};
		 $.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/graph/findGraphJson",
				dataType:'json',
				success:function(data){
					graphList =data;
				},
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
				}
			});
		 return graphList;
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
	 * 启用阈值
	 * 按钮点击事件
	 * @param selected
	 * @param ids
	 */
	function enableBatchThresholdValue(selected,ids){
			if(ids!=""&&ids!=null){
					$.ajax({
						type:'post',
						url:"${pageContext.request.contextPath}/thresholdValue/enable",
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							$("#commonDialog").modal('hide');
	                    	toastr.clear();
	                    	toastr.success("启用阈值成功!");
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
	 * 禁用阀值
	 * 按钮点击事件
	 * @param selected
	 * @param ids
	 */
	function disableBatchThresholdValue(selected,ids){
			if(ids!=""&&ids!=null){
					$.ajax({
						type:'post',
						url:"${pageContext.request.contextPath}/thresholdValue/disable",
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							$("#commonDialog").modal('hide');
	                    	toastr.clear();
							toastr.success("禁用阈值成功!");
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
	
</script>

</head>

<body>

		<!-- 头部菜单 start-->
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		<div class="znms_box">
			<p class="page_top_menu">系统管理&gt; <span class="bold">阈值管理</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="阈值名称,主机,图形,流向,启用状态,警告高阈值,警告低阈值"
			ids="thresholdValueName,host.hostName,graph.graphName,flowDirection,status,warningHighThresholdValue,warningLowThresholdValue"
			dataUrl="${pageContext.request.contextPath }/thresholdValue/search"
			columnShows="1,1,1,1,1,1,1" 
			isSubStrings="0,0,0,0,0,0,0"
			sortable="0,0,0,0,0,0,0,"
			uniqueId="thresholdValueUuid"
			singleDeleteUrl="${pageContext.request.contextPath }/thresholdValue/deleteSingle"
			singleDeletePrivilege="p_threshold_value_delete"
			multiDeleteUrl="${pageContext.request.contextPath }/thresholdValue/delete"
			multiDeletePrivilege="p_threshold_value_delete"
			addUrl="initAdd"
			addPrivilege="p_threshold_value_add"
			addTitle="${addAlias}"
			showExport="false" 
			singleDetailUrl="showDetail"
			singleDetailPrivilege="p_threshold_value_detail"
			singleEditUrl="showEdit"
			singleEditPrivilege="p_threshold_value_edit"
			
			optionBtnName_1="enableBtn"
			 optionBtnTitle_1="启用"
			 optionBtnOpenType_1="_custom"
			 optionBtnUrl_1="_custom"
			 optionBtnCustomName_1="enableBatchThresholdValue"
			 optionBtnPrivilege_1="p_threshold_value_enable"
			 
			 optionBtnName_2="disableBtn"
			 optionBtnTitle_2="禁用"
			 optionBtnOpenType_2="_custom"
			 optionBtnUrl_2="_custom"
			 optionBtnCustomName_2="disableBatchThresholdValue"
			 optionBtnPrivilege_2="p_threshold_value_disable"
			/>
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
