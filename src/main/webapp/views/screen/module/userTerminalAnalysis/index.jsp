<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path=request.getContextPath();
%>
<style>
</style>
<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<script>

    function showFullScreen_userTerminalAnalysis(){
        console.log("invoke userTerminalAnalysis");
        refreshUserTerminalAnalysis('<%=path%>/screen/module/userTerminalAnalysis/getUserTerminal');
    }
 	refreshHander_1 = setInterval(function(){
       		refreshUserTerminalAnalysis('<%=path%>/screen/module/userTerminalAnalysis/getUserTerminal');
       }, 60000);
</script>
<div class="u_terminal_box">
	<h1>用户终端占比分析</h1>
	<div class="u_terminal_t">
	<h2>老师在线终端数</h2>
	<ul class="u_terminal_ult " id="teacher_online_terminal"></ul>
	<div class="u_terminal_doughnut" id="teacher_terminal">
		<%-- <img src="<%=path%>/images/screen/module/userTerminalAnalysis/1.png" > --%>
	</div>
		<ul class="u_terminal_iconbox">
		<li class="u_tericon_a"><i></i><p class="u_font_1vw led_fonts" id="iso_teacher_terminal"></p><p>iOS</p></li>
		<li class="u_tericon_b"><i></i><p class="u_font_1vw led_fonts" id="android_teacher_terminal"></p><p>Android</p></li>
		<li class="u_tericon_c"><i></i><p class="u_font_1vw led_fonts" id="windows_teacher_terminal"></p><p>Windows</p></li>
		<li class="u_tericon_d"><i></i><p class="u_font_1vw led_fonts" id="other_teacher_terminal"></p><p>其他</p></li>
	</ul>
	</div>
	
	<div class="u_terminal_t">
	<h2>学生在线终端数</h2>
	<ul class="u_terminal_ult " id="student_online_terminal"></ul>
	<div class="u_terminal_doughnut"  id="student_terminal">
		<%-- <img src="<%=path%>/images/screen/module/userTerminalAnalysis/1.png" > --%>
	</div>
	<ul class="u_terminal_iconbox">
		<li class="u_tericon_a"><i></i><p class="u_font_1vw led_fonts" id="iso_student_terminal"></p><p>iOS</p></li>
		<li class="u_tericon_b"><i></i><p class="u_font_1vw led_fonts" id="android_student_terminal"></p><p>Android</p></li>
		<li class="u_tericon_c"><i></i><p class="u_font_1vw led_fonts" id="windows_student_terminal"></p><p>Windows</p></li>
		<li class="u_tericon_d"><i></i><p class="u_font_1vw led_fonts" id="other_student_terminal"></p><p>其他</p></li>
	</ul>
	</div>
</div>