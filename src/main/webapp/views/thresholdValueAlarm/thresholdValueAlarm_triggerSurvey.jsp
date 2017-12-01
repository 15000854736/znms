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
		return [ 
				{
					type:'list',
					key:'thresholdValueUuid',
					items:getThresholdValueJSON(),
					width:130
				}
		]
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
			 <li id="1"><a href="${pageContext.request.contextPath }/thresholdValueAlarm">阈值触发日志</a></li>
			 <li id="2" class="tab_hov"><a href="#">阈值触发概况</a></li>
			 </ul>
			
			<!-- 表主体：start -->
			<s:table id="ZZOS_Common_Table" columns="阈值,最近一小时触发次数,最近一天触发次数,最近一周触发次数"
				ids="thresholdValueUuid,lastHourTriggerCount,lastDayTriggerCount,lastWeekTriggerCount"
				dataUrl="${pageContext.request.contextPath }/thresholdValueAlarm/triggerSurvey/search"
				columnShows="1,1,1,1" 
				isSubStrings="0,0,0,0"
				sortable="0,0,0,0"
				uniqueId="surveyUuid"
				singleDeleteUrl="${pageContext.request.contextPath }/thresholdValueAlarm/triggerSurvey/deleteSingle"
				singleDeletePrivilege="p_threshold_value_trigger_survey_delete"
				multiDeleteUrl="${pageContext.request.contextPath }/thresholdValueAlarm/triggerSurvey/delete"
				multiDeletePrivilege="p_threshold_value_trigger_survey_delete"
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
