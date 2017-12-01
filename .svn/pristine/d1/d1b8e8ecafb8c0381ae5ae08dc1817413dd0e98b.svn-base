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
					type:'list',
					key:'hostUuid',
					items:getHostJSON(),
					width:130
				},
				{
					type:'list',
					key:'thresholdValueUuid',
					items:getThresholdValueJSON(),
					width:130
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
	
	//查找阀值
	function getThresholdValueJSON(){
		var thresholdValueList ={};
		 $.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/thresholdValue/findThresholdValueJson",
				dataType:'json',
				success:function(data){
					thresholdValueList =data;
				},
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
				}
			});
		 return thresholdValueList;
	}
</script>

</head>

<body>

		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		
		<div class="znms_box">
			
			<ul class="menu_mod_box">
			 <li id="1"  class="tab_hov"><a href="#">阈值触发日志</a></li>
			 <li id="2"><a href="${pageContext.request.contextPath }/thresholdValueAlarm/triggerSurvey">阈值触发概况</a></li>
			 </ul>
			
			<!-- 表主体：start -->
			<s:table id="ZZOS_Common_Table" columns="主机名,阈值,报警值,当前值,描述,时间"
				ids="hostUuid,thresholdValueUuid,alarmValue,currentValue,description,createTime"
				dataUrl="${pageContext.request.contextPath }/thresholdValueAlarm/triggerLog/search"
				columnShows="1,1,1,1,1,1" 
				isSubStrings="0,0,0,0,0,0"
				sortable="0,0,0,0,0,0"
				uniqueId="logUuid"
				singleDeleteUrl="${pageContext.request.contextPath }/thresholdValueAlarm/triggerLog/deleteSingle"
				singleDeletePrivilege="p_threshold_value_trigger_log_delete"
				multiDeleteUrl="${pageContext.request.contextPath }/thresholdValueAlarm/triggerLog/delete"
				multiDeletePrivilege="p_threshold_value_trigger_log_delete"
				showExport="false" 
				showAdd="false"
				showSingleDetailBtn="false"
				showSingleEditBtn="false"
				/>
			<!-- 表主体：end -->
			
			<%@ include file="../common/footer.jsp"%>
		</div>
</body>
</html>
