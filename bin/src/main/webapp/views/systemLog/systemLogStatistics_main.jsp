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
		<div class="znms_box" style="height:100">
			<p class="page_top_menu">系统日志&gt; <span class="bold">系统日志统计</span></p>
		<!-- 头部菜单 end-->
		
		<table class="nms_mod_table log_height_100">
			  <tr>
			    <td height="100%" align="center" valign="top" class="wh_20b">
			    	<div class="indexwrong_box" id="piePriorityDiv">
			         <h1 class="L">按级别</h1> <div class=" statistics_box">
			      
			 
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="piePriority">
				<option value="-1" selected="selected">全部级别</option>
				<option value="0" >紧急(emerg)</option>
				<option value="1">警报(alert)</option>
				<option value="2">严重(crit)</option>
				<option value="3">错误(err)</option>
				<option value="4">警告(warning)</option>
				<option value="5">通知(notice)</option>
				<option value="6">信息(info)</option>
				<option value="7">调试(debug)</option>
				<option value="8">其他(other)</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="piePriorityHost">
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
			   <script type="text/javascript">
			        // 基于准备好的dom，初始化echarts实例
			        var piePriorityChart = echarts.init(document.getElementById('priorityPic'));
			        // 指定图表的配置项和数据
			        var option = {
					    title : {
					        text: '',
					        subtext: '',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    }
					    
					};
			    </script>
			  
			  
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
			<option value="0">kern</option>
			<option value="1">user</option>
			<option value="2">mail</option>
			<option value="3">daemon</option>
			<option value="4">auth</option>
			<option value="5">syslog</option>
			<option value="6">lpr</option>
			<option value="7">news</option>
			<option value="8">uucp</option>
			<option value="10">autnpriv</option>
			<option value="11">ftp</option>
			<option value="15">cron</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="pieFacilityHost">
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
			  <script type="text/javascript">
			        // 基于准备好的dom，初始化echarts实例
			        var pieFacilityChart = echarts.init(document.getElementById('facilityPic'));
			        // 指定图表的配置项和数据
			        var option = {
					    title : {
					        text: '',
					        subtext: '',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    }
					    
					};
			  </script>
			  
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
				<option value="0" >紧急(emerg)</option>
				<option value="1">警报(alert)</option>
				<option value="2">严重(crit)</option>
				<option value="3">错误(err)</option>
				<option value="4">警告(warning)</option>
				<option value="5">通知(notice)</option>
				<option value="6">信息(info)</option>
				<option value="7">调试(debug)</option>
				<option value="8">其他(other)</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="lineFacility">
			<option value="-1" selected="selected">所有类型</option>
			<option value="0">kern</option>
			<option value="1">user</option>
			<option value="2">mail</option>
			<option value="3">daemon</option>
			<option value="4">auth</option>
			<option value="5">syslog</option>
			<option value="6">lpr</option>
			<option value="7">news</option>
			<option value="8">uucp</option>
			<option value="10">autnpriv</option>
			<option value="11">ftp</option>
			<option value="15">cron</option>
			</select>
			<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="lineHost">
				<option value="-1" selected="selected">全部主机</option>
			</select>
			<button type="button" class="search_but" id="lineSearchButton">
				<i></i>
			</button>
			  </div>
			  
			  				  <div class="log_statistics_box">
			  
			   <!-- 初始化折线图表 -->
			  <div class="log_statistics_chart" id="linePic" style="width:70%"></div>
			  <script type="text/javascript">
			        // 基于准备好的dom，初始化echarts实例
			        var lineChart = echarts.init(document.getElementById('linePic'));
			        
			  </script>
			  
			 </div>
			  
			    </div>
			   </td>
			    </tr>
	</table>
		
	
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
