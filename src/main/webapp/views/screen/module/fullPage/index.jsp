<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path=request.getContextPath();
%>
<style>
/*.section { text-align: center; font: 50px "Microsoft Yahei"; color: #fff;}*/


</style>
<link rel="stylesheet" href="<%=path%>/css/screen/module/starMap/screen_starMap_base.css">
<link rel="stylesheet" href="<%=path%>/css/screen/module/flowAnalysis/screen_flowAnalysis_1920_1080.css">
<link rel="stylesheet" href="<%=path%>/css/screen/module/index/screen_index_1920_1080.css">
<link rel="stylesheet" href="<%=path%>/css/screen/module/accessTimeAnalysis/screen_accessTimeAnalysis_1920_1080.css">

<script src="<%=path %>/js/screen/module/screen_accessTimeAnalysis.js"></script>
<script src="<%=path %>/js/screen/module/screen_flowAnalysis.js"></script>
<!--<script src="<%=path %>/js/screen/module/screen_index.js"></script>-->
<script src="<%=path %>/js/screen/module/screen_starMap.js"></script>
<script>
	
	 function showFullScreen_fullPage(){
        console.log("invoke fullPage");
   var dom = echarts.init(document.getElementById("test"));     
   option = {
    title: {
        text: '堆叠区域图'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'邮件营销',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'联盟广告',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'视频广告',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'直接访问',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'搜索引擎',
            type:'line',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            areaStyle: {normal: {}},
            data:[820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
};
 dom.setOption(option);
 refreshAccessTimeAnalysis("<%=path%>/screen/module/accessTimeAnalysis/getAccessTime");        
    }

 
 refreshHander_1 = setInterval(function(){
       		refreshAccessTimeAnalysis("<%=path%>/screen/module/accessTimeAnalysis/getAccessTime");
       }, 60000);
       
    $(function() {
        $('#dowebok').fullpage({
			//sectionsColor : ['#1bbc9b', '#4BBFC3', '#7BAABE', '#f90' ],
			navigationPosition:"left",
			paddingTop:"0",
			paddingBottom:"0",
			slidesColor:"#1bbc9b",
			//controlArrowColor:"red",
			scrollOverflow:false,
			keyboardScrolling:true,
			animateAnchor:true
		});
	});
</script>
		<div id="dowebok" >
			<div class="section" >
				<div class="slide">
					<div class="a_access_timebox" style="width:33%">
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
				</div>
				<div class="slide">
					<h3>第二屏图片加文字</h3>
					<div style="style="display:inline">
						<div style="width:600px;height:600px; float:left;">
							<img src="<%=path%>/image/c.jpg" alt="test" style="width: 100%;height: 100%;"/>
						</div>
						<div id="test" style="width:1200px;height:560px;float:right;">
							
						</div>
					</div>
				</div>
				<div class="slide">
					
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
				</div>
				<div class="slide" >
					<div >
						<img src="<%=path%>/image/c.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
				</div>
				<div class="slide">
					<div >
						<img src="<%=path%>/image/d.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
				</div>
				<div class="slide">
					<div >
						<img src="<%=path%>/image/e.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
				</div>
			</div>
		</div>