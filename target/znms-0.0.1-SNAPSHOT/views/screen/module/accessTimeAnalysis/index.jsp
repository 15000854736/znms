<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path=request.getContextPath();
%>
<style>
line,[opacity='0.15'] {display:none !important;}

</style>
<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<script>

    function showFullScreen_accessTimeAnalysis(){
        console.log("invoke accessTimeAnalysis");
        
         refreshAccessTimeAnalysis("<%=path%>/screen/module/accessTimeAnalysis/getAccessTime");
         refreshFlowData("<%=path%>/screen/module/index/getFlowData");
    }
	
	 refreshHander_1 = setInterval(function(){
       		refreshAccessTimeAnalysis("<%=path%>/screen/module/accessTimeAnalysis/getAccessTime");
       		refreshFlowData("<%=path%>/screen/module/index/getFlowData");
       }, 60000);
        
</script>
<!--  
<div class="a_access_timebox">
	<h1>每天接入时间分析</h1>
	<div class="a_access_everyday">
	<div class="a_zlog_data_a" id="a_everyday_data" style="display: initial;">
		<%-- <img src="<%=path%>/images/screen/module/accessTimeAnalysis/5.png" > --%>
	</div>
	<ul class="a_access_ulicon">
		<li class="a_access_a"><p class="font_2vw blod led_fonts" id="teacherWireless_pc"><i></i></p><p>老师有线</p></li>
		<li class="a_access_b"><p class="font_2vw blod led_fonts" id="studentWireless_mobile"><i></i></p><p>学生无线</p></li>
		<li class="a_access_c"><p class="font_2vw blod led_fonts" id="teacherWireless_mobile"><i></i></p><p>老师无线</p></li>
		<li class="a_access_d"><p class="font_2vw blod led_fonts" id="studentWireless_pc"><i></i></p><p>学生有线</p></li>
	</ul>
	<h1>每次接入时间分析</h1>
	<div class="every_timr_analysis" id="every_timr_analysis" >
		<%-- <img src="<%=path%>/images/screen/module/accessTimeAnalysis/6.png"  > --%>
	</div>
		<ul class="a_access_ulicon a_each_ulicon">
		<li class="a_access_a"><p class="font_2vw blod led_fonts" id="every_time_teacherWireless_pc"><i></i></p><p>老师有线</p></li>
		<li class="a_access_c"><p class="font_2vw blod led_fonts" id="every_time_teacherWireless_mobile"><i></i></p><p>老师无线</p></li>
		<li class="a_access_b"><p class="font_2vw blod led_fonts" id="every_time_studentWireless_mobile"><i></i></p><p>学生无线</p></li>
		<li class="a_access_d"><p class="font_2vw blod led_fonts" id="every_time_studentWireless_pc"><i></i></p><p>学生有线</p></li>

	</ul>
	</div>
</div>

<div class="a_access_timebox">
	<h1>流量分析</h1>
	<div class="zlog_flow_data_a" id="zlog_data_a">
   		<!--  <img src="images/img3.png" width="305" height="130">-->
   <!--	</div>
	<ul class="zlog_flow_data_list" id="zlog_data_li">
	</ul>
	<ul class="zlog_flow_data_b">
		<div id="flowCharts" class="flowCharts">
				
		</div>
	</ul>
</div>
-->

<div class="zlog_box">
	<h1>上网数据分析</h1>
	<div class="zlog_flow_data_a" id="zlog_data_a">
		<!-- <img src="images/img3.png" width="260" height="100"> -->
	</div>
	<ul class="zlog_data_list" id="zlog_data_li">
	</ul>
	<ul class="zlog_data_b">
	 	<div id="flowCharts" class="flowCharts"></div>
	</ul>
</div>