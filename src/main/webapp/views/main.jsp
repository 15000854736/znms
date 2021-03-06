<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="./common/include.jsp"%>
<script src="<%=path %>/js/jquery.kxbdMarquee.js"></script>
<script src="<%=path %>/js/homePage/homePage.js"></script>
	<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<style>

.network_health{height:420px !important;}

.traffic_data li{
	float:none !important;
}
#exportFlow{overflow:hidden;}
#exportFlow ul li{float:left;}
#mainDeviceMonitorDiv{overflow:hidden;} 
#mainDeviceMonitorDiv ul li{float:left;} 

</style>
<script>

var winWidth  = window.screen.width;
var winHeight = window.screen.height;
var resolutionOk = ((winWidth==1920 && winHeight == 1080) || (winWidth == 1600 && winHeight == 900) || (winWidth == 1366 && winHeight == 768) || (winWidth == 1280 && winHeight == 1024));

var cssWidth,cssHeight;
if(winWidth>=1920) {
	cssWidth = 1920;
	cssHeight = 1080;
} else if(winWidth >= 1600) {
	cssWidth = 1600;
	cssHeight = 900;
} else if(winWidth >= 1366) {
	cssWidth = 1366;
	cssHeight = 768;
} else {
	cssWidth = 1280;
	cssHeight = 1024;
}


$("<link>").attr({ rel: "stylesheet",
	id: "home_css",
	type: "text/css",
	href: "<%=path%>/css/home_"+cssWidth+"_"+cssHeight+".css"
}).appendTo($("head"));





var path = "<%=path %>";
var height = "";
var width = "";
	$(document).ready(function(){
		getExportFlowData();
		//设置在线用户统计分析数据
		getOnlineUserData();
		getOnlineUserDataByCategory();
		
		//设置设备统计数据
		getDeviceData();
		
		//查找网络主要设备监控数据
		getMainDeviceMonitorData();
		
		//查询网络健康数据
		getNetHeathData();
	});
	
</script>


</head>

<body>

	<%@ include file="common/header.jsp"%>
	<%@ include file="common/leftMenu.jsp"%>
	
<div class="nms_box_center">

<table class="nms_mod_table">

  <tr>
    <td align="center" valign="top">
      	<div class="indexwrong_box">
        <h1>出口流量</h1>
        </div>
        <div class="height_500">
	        <div id="exportFlow" class="box4">
	        	<ul class=" index_monitor_box_b" id="exportDiv"></ul>
	      	</div>
      </div>	
     </td>	
    
    
    <td align="center" valign="top" class="wh_30b">
      <div class="indexwrong_box">
      	<h1>在线用户统计分析</h1>
      </div>
      <div class="online_user_boxb">
        <div class="online_user_p ">注册总人数：<span class="bold" id="totalRegistUser"></span></div>
        <ul class="online_user_ul">
          <li id="totalUserLi4"></li><li id="totalUserLi3"></li><li id="totalUserLi2"></li><li id="totalUserLi1"></li><li id="totalUserLi0"></li>
        </ul>
        <ul class="online_user_sort">
        	<li title="电脑" class="online_user_pc"><div class="L">电脑</div><span id="pcUser" class="bold"></span></li>
        	<li title="移动设备" class="online_user_m"><div class="L">移动</div><span class="bold" id="wirelessUser"></span></li>
        	<li title="其他" class="online_user_other"><div class="L">其他</div><span class="bold" id="otherUser"></span></li>
        </ul>
      </div>   
      <div class="nms_data_statistics" id="onlineUserPic">
      	
      </div>
     </td>
    
    
  </tr>
    <tr>
      <td align="center" valign="top" class="">
        
  <div class="indexwrong_box ">
    <h1>网络主要设备监控</h1></div>
    <div >
    
	    <div  id="mainDeviceMonitorDiv" class="monitor_box index_monitor_box ">
				<ul id="mainDiv">
				
				</ul>
	     </div>
	     
    	</div>
        </td>
      <td rowspan="2" align="center" valign="top" class="wh_30b">
        <div class="indexwrong_box "><h1>设备统计</h1></div>
        
                <div class="sta_top indexwrong_device">
          <ul>
            <li>
            	<div class="znms_sbimg_icon L impact" id="exchangeDeviceTotal" style="">
            	</div>
	            <div class="list_cpudataram">
			        <div class="list_modbox">
			          <div class="list_fontbox">
			            <div class="L">在线</div><div class="R" id="exchangeDeviceOnline"></div></div>
			          <div class="list_modcolor"><div class="ram_per" id="exchangeOnlinePercent"></div></div>
			        </div>
		        
		        	<div class="list_modbox">
			          <div class="list_fontbox">
			            <div class="L">宕机</div><div class="R"  id="exchangeDeviceDown"></div></div>
			          <div class="list_modcolor"><div class="data_per" id="exchangeDownPercent"></div></div>
			        </div>
	        	</div>
        	</li>
        	
            <li>
	            <div class="znms_sbimg_icon L impact" id="gatewayDeviceTotalCount">
	            </div>
            
            	<div class="list_cpudataram">
		        <div class="list_modbox">
		          <div class="list_fontbox">
		            <div class="L">在线</div><div class="R" id="gatewayDeviceOnlineCount"></div></div>
		          <div class="list_modcolor"><div class="ram_per" id="gatewayOnlinePercent"></div></div>
		          </div>
		        <div class="list_modbox">
		          <div class="list_fontbox">
		            <div class="L">宕机</div><div class="R" id="gatewayDeviceDownCount"></div></div>
		          <div class="list_modcolor"><div class="data_per" id="gatewayDownPercent"></div></div>
		          </div>
		        </div>
		    </li>
		    
            <li>
            	<div class="znms_sbimg_icon L impact" id="wirelessDeviceTotalCount">
            	</div>
            	<div class="list_cpudataram">
		        <div class="list_modbox">
		          <div class="list_fontbox">
		            <div class="L">在线</div><div class="R" id="wirelesDeviceOnlineCount"></div></div>
		          <div class="list_modcolor"><div class="ram_per" id="wirelessOnlinePercent"></div></div>
		          </div>
		        <div class="list_modbox">
		          <div class="list_fontbox">
		            <div class="L">宕机</div><div class="R" id="wirelesDeviceDownCount"></div></div>
		          <div class="list_modcolor"><div class="data_per" id="wirelessDownPercent"></div></div>
		          </div>
		        </div>
		    </li>
		    
            <li>
            	<div class="znms_sbimg_icon L impact" id="otherDeviceTotalCount">
            	</div>
            	<div class="list_cpudataram">
			        <div class="list_modbox">
			          <div class="list_fontbox">
			            <div class="L">在线</div><div class="R" id="otherDeviceOnlineCount"></div></div>
			          <div class="list_modcolor"><div class="ram_per" id="otherOnlinePercent"></div></div>
			          </div>
			        <div class="list_modbox">
			          <div class="list_fontbox">
			            <div class="L">宕机</div><div class="R" id="otherDeviceDownCount"></div></div>
			          <div class="list_modcolor"><div class="data_per" id="otherDownPercent"></div></div>
			          </div>
			     </div>
        	</li>
        	
            </ul>
          </div>
        </td>
    </tr>
</table>
</div>

</body>


</html>
