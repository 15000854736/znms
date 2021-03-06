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

    function showFullScreen_flowAnalysis(){
        console.log("invoke flowAnalysis");
        refreshFlowAnalysis('<%=path%>/screen/module/flowAnalysis/getFlowAnalysis');
    }
	refreshHander_1 = setInterval(function(){
       		 refreshFlowAnalysis('<%=path%>/screen/module/flowAnalysis/getFlowAnalysis');
       }, 60000);
</script>

<div class="f_flow_box">
	<h1>用户流量使用分析</h1>
	<div class="f_total_flow"><p class="led_fonts font_3vw f_marginbox" id="totalFlow"></p><p>总流量/Gb/天</p>
		<div class="f_data_bgimg f_zlog_data_a" id="total_flow" >
			<%-- <img src="<%=path%>/images/screen/module/flowAnalysis/2.png"  > --%>
		</div>
	</div>
	<div class="f_box_internet_traffic">
	<div class="f_daily_internet">
	<h2>每天上网流量</h2>
	<ul class="f_device_ul">
		<li class="f_device_a"><i></i>学生无线<span id="student_wireless"></span></li>
		<li  class="f_device_b"><i></i>学生有线<span id="student_wireless_pc"></span></li>
		<li class="f_device_c"><i></i>老师无线<span id="teacher_wireless"></span></li>
		<li class="f_device_d"><i></i>老师有线<span id="teacher_wireless_pc"></span></li>
	 </ul>
	<div class="f_device_data" id="every_day_flow">
		<%-- <img src="<%=path%>/images/screen/module/flowAnalysis/4.png"> --%>
	</div>
	</div>
	<div class="f_times_internet">
	<h2>每次上网流量</h2>
	<div class="f_cylinder_data" id="every_time_flow">
		<%-- <img src="<%=path%>/images/screen/module/flowAnalysis/3.png"> --%>
	</div>
		<ul class="f_device_ul f_device_ul_b">
		<li class="f_device_a"><span class="led_fonts" id="student_wireless_record"></span>MB<p>学生无线</p></li>
		<li class="f_device_b"><span class="led_fonts" id="student_wireless_pc_record"></span>MB<p>学生有线</p></li>
		<li class="f_device_c"><span class="led_fonts" id="teacher_wireless_record"></span>MB<p>老师无线</p></li>
		<li class="f_device_d"><span class="led_fonts" id="teacher_wireless_pc_record"></span>MB<p>老师有线</p></li>
		</ul>
	</div>
	</div>
</div>