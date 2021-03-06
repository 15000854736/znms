<%--
  Created by IntelliJ IDEA.
  User: shenqilei
  Date: 2016/11/30
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path=request.getContextPath();
%>
<style>
line,[opacity='0.15'] {display:none !important;}

</style>
<script src="<%=path %>/js/jquery.kxbdMarquee.js"></script>
<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<style>
#mainDeviceDiv{overflow:hidden;} 
#mainDeviceDiv ul li{float:left;} 

</style>
<script>

    /**
     * 全屏时调用
     */
    function showFullScreen_index(){
        refreshLeftUpData("<%=path%>/screen/module/index/getLeftUpAreaData");
        refreshCenterUpData();
        refreshRightUpData("<%=path%>/screen/module/index/getRightUpAreaData");
        refreshLeftDownData("<%=path%>/screen/module/index/getLeftDownAreaData", getLeftDiameter());
        getNetHeathData("<%=path%>/home/getNetHeathData");
        //refreshRightDownData("<%=path%>/screen/module/index/getRightDownAreaData", getRightDiameter());
		refreshAccessTimeAnalysis("<%=path%>/screen/module/accessTimeAnalysis/getAccessTime");
		
        setTimeout(function(){
            refreshLeftUpData("<%=path%>/screen/module/index/getLeftUpAreaData");
            refreshCenterUpData();
            refreshRightUpData("<%=path%>/screen/module/index/getRightUpAreaData");
            refreshLeftDownData("<%=path%>/screen/module/index/getLeftDownAreaData", getLeftDiameter());
            //refreshRightDownData("<%=path%>/screen/module/index/getRightDownAreaData", getRightDiameter());
        }, 100);
        refreshHander_1 = setInterval(function(){
            refreshLeftUpData("<%=path%>/screen/module/index/getLeftUpAreaData");
            refreshCenterUpData();
            refreshRightUpData("<%=path%>/screen/module/index/getRightUpAreaData");
            refreshLeftDownData("<%=path%>/screen/module/index/getLeftDownAreaData", getLeftDiameter());
        }, 60000);
        refreshHander_2 = setInterval(function(){
            //refreshRightDownData("<%=path%>/screen/module/index/getRightDownAreaData", getRightDiameter());
        }, 300000);

        refreshHander_3 = setInterval(function(){
            if($("#left_down_scroller").children().length<=3){
                return;
            }
            $("#left_down_scroller").animate({"margin-left":"-27.3%"}, "normal", "linear", function(){
                $("#left_down_scroller dl:eq(0)").appendTo($("#left_down_scroller"));
                $("#left_down_scroller").css("margin-left","0px");
            });
        }, 6000)
    }

    function getRightDiameter() {
        if(cssWidth == 1920) {
            return "360";
        } else if(cssWidth == 1600) {
            return "300"
        } else if(cssWidth == 1366) {
            return "265";
        } else if(cssWidth == 1280) {
            return "250";
        } else {
            return "230";
        }
    }

    function getLeftDiameter() {
        if(cssWidth == 1920) {
            return "260";
        } else if(cssWidth == 1600) {
            return "230"
        } else if(cssWidth == 1366) {
            return "150";
        } else if(cssWidth == 1280) {
            return "160";
        } else {
            return "290";
        }
    }

</script>

<div class="prj_top">
    <div class="online_box">
        <div class="online_turntabl_box">
            <h1>在线数</h1>
            <p class="led_fonts js-odoo">
                <script src="<%=path%>/js/vendor/odoo.js"></script>
                <script>
                    if('${leftUp}'.length > 0) {
                        odoo.default({ el:'.js-odoo',value:'${leftUp.totalOnlineUserCount}' });
                    } else {
                        odoo.default({ el:'.js-odoo',value:'0' });
                    }
                </script>
            </p>
            <div class="online_turntable_1"></div>
        </div>
        <ul class="online_people">
            <li>
                <div id="registerUserCount" class="online_people_circle"></div>
                <div class="numbe_a">注册人数</div>
            </li>
            <li>
                <div id="activeUserCount" class="online_people_circle"></div>
                <div class="active_b">活跃人数</div>
            </li>
            <li>
                <div id="freeUserCount" class="online_people_circle"></div>
                <div class="free_c">有线认证数</div>
            </li>
            <li>
                <div id="lostUserCount" class="online_people_circle"></div>
                <div class="scour_d">无线认证数</div>
            </li>
        </ul>
        <ul class="online_devices">
            <li class="mobile_devices">
                <i></i>移动设备
						<span id="wireless_count">
						 </span>
            </li>
            <li class="wired_devices">
                <i></i>PC设备
						<span id="wire_count">
						</span>
            </li>
            <li class="other_devices">
                <i></i>其他设备
						<span id="etc_count">
						</span>
            </li>
        </ul>
    </div>
    <div class="line_box"></div>


<div class="diagram_box">
<ul class="monitoring_ulbox">
<li class="monitoring_ioon_a">交换设备<p class="led_fonts font_35pxb" id="totalCount0"></p><i></i><ul><li><p class=" font_18px" id="onlineCount0"></p><p>在线</p></li><li><p class=" font_18px" id="downCount0"></p><p>宕机</p></li></ul></li>
<li class="monitoring_ioon_b">网关设备<p class="led_fonts font_35pxb" id="totalCount1"></p><i></i><ul><li><p class=" font_18px" id="onlineCount1"></p><p>在线</p></li><li><p class=" font_18px" id="downCount1"></p><p>宕机</p></li></ul></li>
<li class="monitoring_ioon_c">无线网关<p class="led_fonts font_35pxb" id="totalCount2"></p><i></i><ul><li><p class=" font_18px" id="onlineCount2"></p><p>在线</p></li><li><p class=" font_18px" id="downCount2"></p><p>宕机</p></li></ul></li>
<li class="monitoring_ioon_d">AP<p class="led_fonts font_35pxb" id="totalCount3"></p><i></i><ul><li><p class=" font_18px" id="onlineCount3"></p><p>在线</p></li><li><p class=" font_18px" id="downCount3"></p><p>宕机</p></li></ul></li>
</ul>

	<div class="equipment_box" id="mainDeviceDiv">
	<ul id="equipment_box">
	
	</ul>
	</div>
	
</div>


    <div class="line_box"></div>
<div class="network_health_box">
    <c:choose>
        <c:when test="${rightUp < 60}">
            <div class="network_health" style="color:red">
                <h1 id="network_health_title">网络状态不良</h1>
                <span class="led_fonts" id="network_health_value">${rightUp}</span>
                <div class="network_health_imgbg"></div>
            </div>
        </c:when>
        <c:when test="${rightUp < 80}">
            <div class="network_health" style="color:yellow">
                <h1 id="network_health_title">网络状态正常</h1>
                <span class="led_fonts" id="network_health_value">${rightUp}</span>
                <div class="network_health_imgbg"></div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="network_health">
                <h1 id="network_health_title">网络状态健康</h1>
                <span class="led_fonts" id="network_health_value">${rightUp}</span>
                <div class="network_health_imgbg"></div>
            </div>
        </c:otherwise>
    </c:choose>
<div class="network_health_grade">
<ul class="health_grade_ulbox">
<li class="health_grade_a"><span class="led_fonts font_35px" id="emergency_alarm"></span><p>紧急告警</p><div class="health_gradebg_a"></div></li>
<li class="health_grade_b"><span class="led_fonts font_35px" id="advanced_alarm"></span><p>高级告警</p><div class="health_gradebg_b"></div></li>
<li class="health_grade_c"><span class="led_fonts font_35px" id="intermediate_alarm"></span><p>中级告警</p><div class="health_gradebg_c"></div></li>
<li class="health_grade_d"><span class="led_fonts font_35px" id="low_level_alarm"></span><p>低级告警</p><div class="health_gradebg_d"></div></li>
</ul>
</div>
</div>
</div>
<div class="line_bottom"></div>
<div class="prj_bottom">
    <div id="left_down" style="position:relative;float: left;">
        <div style="width:100%;position:absolute;overflow:hidden;">
            <div style="width:10000px;" id="left_down_scroller">
                <c:forEach items="${leftDown}" var="exportStreamInfo">
                    <dl class="export_utilization">
                        <dt>${exportStreamInfo.name}</dt>
                        <dd class="scale_diagram_a">
                            <div id="${exportStreamInfo.divId}"  class="doughnut_container"></div>
                        </dd>
                        <dd class="traffic_data">
                            <ul style="display:table;text-align:center;width:100%;">
                                <li style="display:table-cell;text-align:center;">
                                    <h2>下行流量</h2>
                                    <p down _id="${exportStreamInfo.divId}">
                                        <c:choose>
                                            <c:when
                                                    test="${exportStreamInfo.downStream/(1024 * 1024 * 1024) >= 1}">
                                                <fmt:formatNumber type="number"
                                                                  value="${exportStreamInfo.downStream/(1024 * 1024 * 1024)}"
                                                                  maxFractionDigits="2" minFractionDigits="2"/>Gbps
                                            </c:when>
                                            <c:when
                                                    test="${exportStreamInfo.downStream/(1024 * 1024) >= 1}">
                                                <fmt:formatNumber type="number"
                                                                  value="${exportStreamInfo.downStream/(1024 * 1024)}"
                                                                  maxFractionDigits="2" minFractionDigits="2"/>Mbps
                                            </c:when>
                                            <c:when test="${exportStreamInfo.downStream/(1024) >= 1}">
                                                <fmt:formatNumber type="number"
                                                                  value="${exportStreamInfo.downStream/(1024)}"
                                                                  maxFractionDigits="2" minFractionDigits="2"/>Kbps
                                            </c:when>
                                            <c:otherwise>
                                                ${exportStreamInfo.downStream}bps
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </li>
                                <li style="display:table-cell;text-align:center;">
                                    <h2>上行流量</h2>
                                    <p up _id="${exportStreamInfo.divId }">
                                        <c:choose>
                                            <c:when
                                                    test="${exportStreamInfo.upStream/(1024 * 1024 * 1024) >= 1}">
                                                <fmt:formatNumber type="number"
                                                                  value="${exportStreamInfo.upStream/(1024 * 1024 * 1024)}"
                                                                  maxFractionDigits="2" minFractionDigits="2"/>Gbps
                                            </c:when>
                                            <c:when test="${exportStreamInfo.upStream/(1024 * 1024) >= 1}">
                                                <fmt:formatNumber type="number"
                                                                  value="${exportStreamInfo.upStream/(1024 * 1024)}"
                                                                  maxFractionDigits="2" minFractionDigits="2"/>Mbps
                                            </c:when>
                                            <c:when test="${exportStreamInfo.upStream/(1024) >= 1}">
                                                <fmt:formatNumber type="number"
                                                                  value="${exportStreamInfo.upStream/(1024)}"
                                                                  maxFractionDigits="2" minFractionDigits="2"/>Kbps
                                            </c:when>
                                            <c:otherwise>
                                                ${exportStreamInfo.upStream}bps
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </li>
                            </ul>
                        </dd>
                        <dd class="scale_diagram_b">
                            <div name="${exportStreamInfo.divId}" ></div>
                        </dd>
                    </dl>
                </c:forEach>
            </div>
        </div>
    </div>
    
    <div class="line_box"></div>
   <!-- <div class="zlog_box"><h1>流量分析</h1>
   		<div class="zlog_data_a" id="zlog_data_a">
   			<img src="images/img3.png" width="305" height="130">
   		</div>
		<ul class="zlog_data_list" id="zlog_data_li">
		</ul>
		<ul class="zlog_data_b">
			<div id="flowCharts" class="flowCharts">
				
			</div>
		</ul>
  </div> -->
   
 <div class="a_access_timebox">
<h1>每天接入时间/分钟</h1>
<div class="a_access_everyday">
<div class="ejchart_pie"  id="a_everyday_data" style="display: initial;">
	<!-- <img src="access_time/images/5.png" width="260" height="210" > -->
</div>
<ul class="a_access_ulicon" id="everyday_data">

</ul>
<h1 class="a_access_h1">每次接入时间/分钟</h1>
<ul class="a_access_ulicon a_each_ulicon a_access_ulicon_b" id="time_ul">

</ul>
</div>
</div>
<div class="line_bottom"></div>