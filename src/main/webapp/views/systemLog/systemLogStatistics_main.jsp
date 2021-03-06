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
<script src="<%=path %>/js/systemLog/systemLogStatistics.js"></script>
	<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<script>
	var path = "<%=path %>";
	var piePriority = "";
	var piePriorityHost = "";
	var piePriorityInsertTimeFrom = "";
	var piePriorityInsertTimeTo = "";
	var pieFacility = "";
	var pieFacilityHost = "";
	var pieFacilityInsertTimeFrom = "";
	var pieFacilityInsertTimeTo = "";
	var linePriority = "";
	var lineFacility = "";
	var lineHost = "";
	var lineChart,pieFacilityChart,piePriorityChart;



	$(document).ready(function(){
		
		//获取主机列表
		$.ajax({
			url:"${pageContext.request.contextPath}/host/findAll",
			type:"POST",
			dataType:"json",
			async:false,
			success:function(data){
				for(var i=0;i<data.length;i++){
					$("#piePriorityHost").append("<option value='"+data[i].id+"'>"+data[i].hostName+"</option>");
					$("#pieFacilityHost").append("<option value='"+data[i].id+"'>"+data[i].hostName+"</option>");
					$("#lineHost").append("<option value='"+data[i].id+"'>"+data[i].hostName+"</option>");
				}
			}
		});
		var windowHeight = $(window).height();
		var windowWidth = $(window).width();
		var contentHeight = (windowHeight-117)/2;
		var contentwidth = (windowWidth-270)/4;

		$("#linePic").height(contentHeight-100);

		$("#facilityPic").height(contentHeight-100);
		//$("#facilityPic").width(contentwidth);
		$("#priorityPic").height(contentHeight-100);
		//$("#priorityPic").width(contentwidth);

		lineChart = echarts.init(document.getElementById('linePic'));

		pieFacilityChart = echarts.init(document.getElementById('facilityPic'));

		piePriorityChart = echarts.init(document.getElementById('priorityPic'));

		//查找按级别饼图数据
		findPieDataByPriority();

		//查找按类型饼图数据
		findPieDataByFacility();

		//查找折线图数据
		findLineData();
		
		//按级别搜索点击事件
		$("#piePrioritySearchButton").click(function(){
			findPieDataByPriority();
		});
		
		
		//按类型搜索点击事件
		$("#pieFacilitySearchButton").click(function(){
			findPieDataByFacility();
		});
		
		//折线搜索点击事件
		$("#lineSearchButton").click(function(){
			findLineData();
		});
	});	


</script>

</head>

<body>
		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<div class="znms_box" style="height:100%">
			<p class="page_top_menu">系统日志&gt; <span class="bold">系统日志统计</span></p>
		<!-- 头部菜单 end-->

		<table class="nms_mod_table log_height_100">
			  <tr>
			    <td height="100%" align="center" valign="top" class="wh_20b">
			    	<div class="indexwrong_box" id="piePriorityDiv">
			         <h1 class="L">按级别</h1> <div class=" statistics_box">


			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="piePriority">
				<option value="-1" selected="selected">全部级别</option>
				<option value="0" >紧急(EMERG)</option>
				<option value="1">警报(ALERT)</option>
				<option value="2">严重(CRITICAL)</option>
				<option value="3">错误(ERROR)</option>
				<option value="4">警告(WARNING)</option>
				<option value="5">通知(NOTICE)</option>
				<option value="6">信息(INFO)</option>
				<option value="7">调试(DEBUG)</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="piePriorityHost" style="width:200px">
				<option value="-1" selected="selected">全部主机</option>
			</select>
			<input type="text" id="piePriorityInsertTimeFrom" class="form-control input-sm" style="width:186px" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${timeStart }"/>
			<input type="text" id="piePriorityInsertTimeTo" class="form-control input-sm" style="width:186px" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${timeEnd }">
			<button type="button" class="search_but" id="piePrioritySearchButton">
				<i></i>
			</button>

			  </div>
			  <div class="log_statistics_box">

			  <!-- 初始化按级别图表 -->
			  <div class="log_statistics_chart" id="priorityPic" style="width: 394px;height:250px;">

			  </div>


			  <div class="log_statistics_list">
			  <ul id="piePrioritySort">

			  </ul>
			  </div>

			 </div>
			    </div> </td>
			    <td height="100%" align="center" valign="top" class="wh_20b">

			     <div class="indexwrong_box">
			         <h1 class="L">按类型</h1> <div class=" statistics_box">


			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="pieFacility">
			<option value="-1" selected="selected">所有类型</option>
			<option value="0">KERN</option>
			<option value="1">USER</option>
			<option value="2">MAIL</option>
			<option value="3">DAEMON</option>
			<option value="4">AUTH</option>
			<option value="5">SYSLOG</option>
			<option value="6">LPR</option>
			<option value="7">NEWS</option>
			<option value="8">UUCP</option>
			<option value="9">CRON</option>
			<option value="10">AUTHPRIV</option>
			<option value="11">FTP</option>
			<option value="12">NTP</option>
			<option value="13">LOG_AUDIT</option>
			<option value="14">LOG_ALERT</option>
			<option value="15">CLOCK</option>
			<option value="16">LOCAL0</option>
			<option value="17">LOCAL1</option>
			<option value="18">LOCAL2</option>
			<option value="19">LOCAL3</option>
			<option value="20">LOCAL4</option>
			<option value="21">LOCAL5</option>
			<option value="22">LOCAL6</option>
			<option value="23">LOCAL7</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="pieFacilityHost" style="width:200px">
				<option value="-1" selected="selected">全部主机</option>
			</select>
			<input type="text" id="pieFacilityInsertTimeFrom" class="form-control input-sm" style="width:186px" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${timeStart }"/>
			<input type="text" id="pieFacilityInsertTimeTo" class="form-control input-sm" style="width:186px" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${timeEnd }">
			<button type="button" class="search_but" id="pieFacilitySearchButton">
				<i></i>
			</button>
			  </div>

			<div class="log_statistics_box">

			   <!-- 初始化按类型图表 -->
			  <div class="log_statistics_chart" id="facilityPic" style="width: 394px;height:250px;"></div>

			  <div class="log_statistics_list">
			  <ul id="pieFacilitySort">

			  </ul>
			 </div>
			 </div>

			   </td>
			  </tr>
			  <tr>
			    <td height="100%" colspan="2" align="center" valign="top">

			    <div class="indexwrong_box">
			         <h1 class="L">累计统计折线图</h1> <div class=" statistics_box">


			 <select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="linePriority">
				<option value="-1" selected="selected">全部级别</option>
				<option value="0" >紧急(EMERG)</option>
				<option value="1">警报(ALERT)</option>
				<option value="2">严重(CRITICAL)</option>
				<option value="3">错误(ERROR)</option>
				<option value="4">警告(WARNING)</option>
				<option value="5">通知(NOTICE)</option>
				<option value="6">信息(INFO)</option>
				<option value="7">调试(DEBUG)</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="lineFacility">
			<option value="-1" selected="selected">所有类型</option>
			<option value="0">KERN</option>
			<option value="1">USER</option>
			<option value="2">MAIL</option>
			<option value="3">DAEMON</option>
			<option value="4">AUTH</option>
			<option value="5">SYSLOG</option>
			<option value="6">LPR</option>
			<option value="7">NEWS</option>
			<option value="8">UUCP</option>
			<option value="9">CRON</option>
			<option value="10">AUTHPRIV</option>
			<option value="11">FTP</option>
			<option value="12">NTP</option>
			<option value="13">LOG_AUDIT</option>
			<option value="14">LOG_ALERT</option>
			<option value="15">CLOCK</option>
			<option value="16">LOCAL0</option>
			<option value="17">LOCAL1</option>
			<option value="18">LOCAL2</option>
			<option value="19">LOCAL3</option>
			<option value="20">LOCAL4</option>
			<option value="21">LOCAL5</option>
			<option value="22">LOCAL6</option>
			<option value="23">LOCAL7</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="lineHost" style="width:200px">
				<option value="-1" selected="selected">全部主机</option>
			</select>
			<button type="button" class="search_but" id="lineSearchButton">
				<i></i>
			</button>
			  </div>

			  				  <div class="log_statistics_box">

			   <!-- 初始化折线图表 -->
			  <div class="log_statistics_chart" id="linePic" style="width:100%"></div>
			 </div>

			    </div>
			   </td>
			    </tr>
	</table>
	</div>
</body>
</html>
