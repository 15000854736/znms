<%--
  Created by IntelliJ IDEA.
  User: shenqilei
  Date: 2015/8/18
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.znet.info/tag/znms/view" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
	String  firstMenuIndex =request.getSession().getAttribute("firstMenuIndex").toString();
	String  secondMenuIndex =request.getSession().getAttribute("secondMenuIndex").toString();
%>

<script>
		//一级菜单点击事件
		function clickFirstGradeMenu(obj){
			
			if($(obj).attr("id") == "first6"){
				$("#settingUl").hide();
				$("#logUl").show();
			}else if($(obj).attr("id") == "first7"){
				$("#settingUl").show();
				$("#logUl").hide();
			}else{
				$("#settingUl").hide();
				$("#logUl").hide()
			}
			$(".nms_menu_b li").each(function(){
				$(this).removeClass("nms_menu_bhov");
			});
			
			$(".firstGrade").each(function(){
				$(this).removeClass("nms_icon_hov");
			});
			$(obj).addClass("nms_icon_hov");
			
			//往session里面写一级菜单选中的
			var firstMenuIndex = $(obj).attr("id");
			$.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/changeFirstMenu/"+firstMenuIndex,
				dataType:'json',
					success:function(jsonData){
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
				}
			});
		}
		
		//二级菜单点击事件
		function clickSecondGradeMenu(obj){
			$(".firstGrade").each(function(){
				$(this).removeClass("nms_icon_hov");
			});
			$(".nms_menu_b li").each(function(){
				$(this).removeClass("nms_menu_bhov");
			});
			$(obj).addClass("nms_menu_bhov");
			//往session里面写二级菜单选中的
			var secondMenuIndex = $(obj).attr("id");
			$.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/changeSecondMenu/"+secondMenuIndex,
				success:function(jsonData){
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
				}
			});
		}
		
		$(document).ready(function(){
			//回显一级菜单选中
			$(".firstGrade").each(function(){
				if('<%=firstMenuIndex%>' == $(this).attr("id")){
					$(this).addClass("nms_icon_hov");
				}else{
					$(this).removeClass("nms_icon_hov");
				}
			});
			
			//回显二级菜单选中
			$(".nms_menu_b li").each(function(){
				if('<%=secondMenuIndex%>' == $(this).attr("id")){
					$(this).addClass("nms_menu_bhov");
				}else{
					$(this).removeClass("nms_menu_bhov");
				}
			});
			
			var smi = '<%=secondMenuIndex%>';
			if(smi=="second61" || smi=="second62"){
				$("#logUl").show();
				$("#settingUl").hide();
			}else if(smi=="second71" || smi=="second72" || smi=="second73" || smi=="second74" 
					|| smi=="second75" || smi=="second76" || smi=="second77" || smi=="second78"
						|| smi=="second79" || smi=="second710"){
				$("#settingUl").show();
				$("#logUl").hide();
			}else if(smi == "0"){
				$("#settingUl").hide();
				$("#logUl").hide();
			}
		});
</script>

<div class="nms_left">
  
  <div class="nms_menu_box">
    <ul  class="nms_menu_a" >
      <li id="first1" class="nms_icon_a firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="${pageContext.request.contextPath}/home" >管理首页</a></li>
      <li id="first2" class="nms_icon_b firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="${pageContext.request.contextPath}/topology">网络拓扑</a></li>
      <li id="first3" class="nms_icon_c firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="${pageContext.request.contextPath}/monitor">设备监控</a></li>
      <li id="first4" class="nms_icon_g firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="${pageContext.request.contextPath}/hostListbox">主机列表</a></li>
      <li id="first5" class="nms_icon_d firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="${pageContext.request.contextPath}/thresholdValueAlarm">阈值日志</a></li>
      <li id="first6" class="nms_icon_e firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="#">系统日志</a></li>
      <ul class="nms_menu_b" style="display:none" id="logUl">
      	<li id="second61" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/systemLog" >日志列表</a></li>
      	<li id="second62" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/systemLogStatistics" >日志统计</a></li>
      </ul>
      <li id="first8" class="nms_icon_h firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="${pageContext.request.contextPath}/screen">大屏展示</a></li>
      <li id="first7" class="nms_icon_f firstGrade" onclick="clickFirstGradeMenu(this);"><i></i><a href="#">系统设置</a></li>
      <ul class="nms_menu_b" style="display:none" id="settingUl">
      	<li id="second71" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/adminManage" >用户管理</a></li>
        <li id="second72" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/rolePermission">角色管理</a></li>
        <li id="second73" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/host">主机管理</a></li>
        <li id="second74" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/graphTree">图形树</a></li>
        <li id="second75" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/systemOption">系统选项</a></li>
        <li id="second76" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/systemLogDeleteRule">日志规则</a></li>
        <li id="second77" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/backupConfiguration">配置备份</a></li>
        <li id="second78" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/graph">图形管理</a></li>
        <li id="second79" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/thresholdValue">阈值管理</a></li>
        <li id="second710" onclick='clickSecondGradeMenu(this);'><i></i><a href="${pageContext.request.contextPath}/apInformation">星光图</a></li>
      </ul>
      
    </ul>
  </div>
</div>
<div class="nms_box">



