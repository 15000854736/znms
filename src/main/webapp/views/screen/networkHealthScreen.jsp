<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<%
	String path=request.getContextPath();
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no"> 
<meta name="renderer" content="webkit">
<title>卓智运维管理系统</title>
<link  type="image/x-icon" href="<%=path%>/images/zos.ico"  rel="shortcut icon">
	<script src="<%=path %>/js/jquery.kxbdMarquee.js"></script>
<!-- jquery 主文件 -->
<script src="<%=path %>/js/jquery-1.10.2.min.js"></script>
<!-- 消息提示控件 start -->
<script src="<%=path %>/js/toastrplugins/toastr.js"></script>
<link href="<%=path %>/css/toastrplugins/toastr.min.css" rel="stylesheet"/>

<!-- 消息提示控件 end -->
<script type="text/javascript">
toastr.options = {
  "closeButton": true,
  "debug": true,
  "positionClass": "toast-top-full-width",
  "showDuration": "300",
  "hideDuration": "1000",
  "timeOut": "10000",
  "extendedTimeOut": "1000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
}

function getScreenCss(){
	//判断显示器分辨率
	var href = "<%=path%>/css/znms_style_netwok_show.css";
	return href;
}

$("<link>").attr({ rel: "stylesheet",
    id: "screen_css_base", 
 	type: "text/css",
     href: "<%=path%>/css/znms_style_netwok_show.css"
 }).appendTo($("head"));
$("<link>").attr({ rel: "stylesheet",
    id: "screen_css", 
 	type: "text/css",
     href: getScreenCss()
 }).appendTo($("head"));
</script>
<link id="main_css" href="<%=path%>/css/znms_style.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=path %>/js/heatmap.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<script type="text/javascript" src="<%=path %>/js/ejChart/ej.web.all.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/ejChart/properties.js"></script>
<script type="text/javascript" src="<%=path %>/js/nethealthscreen.js"></script>

<style type="text/css">
.screen_grid{
	display:table;
	width:100%;
}
.screen_grid > div >div{
	width:32%;
	height: auto;
	margin:10px;
    padding: 15px;
    border-radius: 5px;
    position: relative;
    box-shadow: 1px 1px 1px 1px #b4cadf;
    float:left;
}
.R{
    background: url(<%=path%>/images/bg_img.png) repeat;
	cursor:pointer;
	position:absolute;
	right:0px;
	top:0px;
}

</style>

<script type="text/javascript">	

	var refresher1;
	var refresher2;
	var scroller;
	
	$(document).ready(function(){
		document.addEventListener("fullscreenchange", function(e) {
			if(document.fullScreenElement!=null && !document.fullScreenElement) {
				hideFullScreen();
				document.exitFullScreen;
			}
		});
		document.addEventListener("mozfullscreenchange", function(e) {
			if(!document.mozFullScreen) {
				hideFullScreen();
				document.mozCancelFullScreen();
			}
		});
		document.addEventListener("webkitfullscreenchange", function(e) {
			if(!document.webkitIsFullScreen) {
				hideFullScreen();
				document.webkitCancelFullScreen();
			}			
		});
		document.addEventListener("msfullscreenchange", function(e) {
			if(!document.msFullScreenElement) {
				hideFullScreen();
				document.msExitFullscreen();
			}
		});
		hideFullScreen();
	});

	function showFullScreen(){
		$("body").css("background", "url('<%=path%>/image/${bgImage}') repeat center 0");
		
		$("#screen_css").attr("href", getScreenCss());
		$("#screen_css_base").attr("href", "<%=path%>/css/znms_style_netwok_show.css");
		$("#main_css").attr("href", "<%=path%>/css/blank.css");
		$("#full_screen").siblings("div").hide();
		
		refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
		refreshNetHeathData("<%=path%>/home/getNetHeathData");	
		refreshDowntimeData("<%=path%>/host/getShutDownHostData");	
		refreshSystemLogData("<%=path%>/systemLog/getSystemLog");	
		refreshthResholdData("<%=path%>/thresholdValue/getThresholdValueData");	
		
		setTimeout(function(){
			refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
			refreshNetHeathData("<%=path%>/home/getNetHeathData");	
			refreshDowntimeData("<%=path%>/host/getShutDownHostData");	
			refreshSystemLogData("<%=path%>/systemLog/getSystemLog");	
			refreshthResholdData("<%=path%>/thresholdValue/getThresholdValueData");			
		}, 100);
		setTimeout(function(){
			refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
			refreshNetHeathData("<%=path%>/home/getNetHeathData");	
			refreshDowntimeData("<%=path%>/host/getShutDownHostData");	
			refreshSystemLogData("<%=path%>/systemLog/getSystemLog");	
			refreshthResholdData("<%=path%>/thresholdValue/getThresholdValueData");			
		}, 100);
		refresher1 = setInterval(function(){
			refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
			refreshNetHeathData("<%=path%>/home/getNetHeathData");	
			refreshDowntimeData("<%=path%>/host/getShutDownHostData");	
			refreshSystemLogData("<%=path%>/systemLog/getSystemLog");	
			refreshthResholdData("<%=path%>/thresholdValue/getThresholdValueData");			
		}, 60000);
		refresher1 = setInterval(function(){
			refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
			refreshNetHeathData("<%=path%>/home/getNetHeathData");	
			refreshDowntimeData("<%=path%>/host/getShutDownHostData");	
			refreshSystemLogData("<%=path%>/systemLog/getSystemLog");	
			refreshthResholdData("<%=path%>/thresholdValue/getThresholdValueData");			
		}, 60000);
		
		$("#full_screen").show();
		
		var docElm = document.documentElement;
		//W3C 
		if (docElm.requestFullscreen) { 
		  docElm.requestFullscreen(); 
		}
		//FireFox 
		else if (docElm.mozRequestFullScreen) { 
		  docElm.mozRequestFullScreen(); 
		}
		//Chrome等 
		else if (docElm.webkitRequestFullScreen) { 
		  docElm.webkitRequestFullScreen(); 
		}
		//IE11
		else if (elem.msRequestFullscreen) {
		 elem.msRequestFullscreen();
		}
	}
	
	function hideFullScreen(){
		toastr.clear();
		$("body").css("background","url('<%=path%>/images/indexbg.jpg') no-repeat fixed");
		if(refresher1 != null) {
			clearInterval(refresher1);
			refresher1 = null;
		}
		if(refresher2 != null) {
			clearInterval(refresher2);
			refresher2 = null;
		}
		if(scroller != null) {
			clearInterval(scroller);
			scroller = null;
		}
		$("#screen_css").attr("href", "<%=path%>/css/blank.css");
		$("#screen_css_base").attr("href", "<%=path%>/css/blank.css");
		$("#main_css").attr("href", "<%=path%>/css/znms_style.css");
		$("#full_screen").hide();
		$("#full_screen").siblings("div").show();
	}

	
</script>

	<script>
		(function(){
			$("#shutDownDesc").kxbdMarquee({direction:"left"});
		})();
	</script>
</head>
<body>
	<div>
		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<div class="screen_grid">
			<div>
				<div>
					<div class="R" onclick="javascript:showFullScreen();">
						<a href="javascript:void(0);" title="推荐分辨率:&#10;1920 &#215; 1080&#10;1600 &#215; 900&#10;1366 &#215; 768&#10;1280 &#215; 1024">
							<img src="<%=path%>/images/fullimg.png"/>
						</a>
					</div>
					<img src="<%=path%>/images/preview.png" style="max-height:500px;max-width:100%;"/>
				</div>
			</div>
		</div>
	</div>
	
		<div id="full_screen">
			<div class="health_box_bg">
			<div class="health_font_da">
			<div class="health_font_a"><div class="width_health" id="netHeathLi0"></div><div class="width_health_bg"></div></div>
			<div class="health_font_b"><div class="width_health" id="netHeathLi1"></div><div class="width_health_bg"></div></div>
			<div class="health_font_c"><div class="width_health" id="netHeathLi2"></div><div class="width_health_bg"></div></div>
			<div class="health_font_d"><div class="width_health" id="netHeathLi3"></div><div class="width_health_bg"></div></div>
		</div>

			<c:choose>
				<c:when test="${rightUp < 60}">
					<div class="network_health_b" style="color:red">
					<h1>网络状态不良</h1>
					<span class="led_fonts">${rightUp}</span>
					<div class="network_health_imgbg_b"></div></div>
					
				</c:when>
				<c:when test="${rightUp < 80}">
					<div class="network_health_b" style="color:yellow"> 
						<h1>网络状态正常</h1>
						<span class="led_fonts">${rightUp}</span>
						<div class="network_health_imgbg_b"></div></div>
				</c:when>
				<c:otherwise>
					<div class="network_health_b">
						<h1 >网络状态健康</h1>
						<span class="led_fonts">${rightUp}</span>
						<div class="network_health_imgbg_b"></div></div>
					</div>
				</c:otherwise>
			</c:choose>
			

		<div class="health_analysis_box">
			<div class="health_analysis_mod analysis_absolute_a">
				<div class="health_analysis_modbox">
					<div class="width_analysis" id="shutDownNum"></div>
						<div class="width_analysis_box" id="">
					<div class="downtime_box " id="shutDownDesc">
						宕机<p>N18K</p>
					</div>
					
				</div>
				</div>
			</div>
		
			<div class="health_analysis_mod analysis_absolute_b ">
				<div class="health_analysis_modbox">
					<div class="width_analysis" id="systemLogNum"></div>
					
						<!--滚动开始-->
			<div id="systemLog" style="overflow:hidden;height:170px;font-size:0.8vw;padding:2% 5%;">
			    <div id="systemLog1">
			    </div>
			<div id="systemLog2"></div>
			</div>
			   <script>
			   var speed=50
			   systemLog2.innerHTML=systemLog1.innerHTML
			   function Marquee(){
			   if(systemLog2.offsetTop-systemLog.scrollTop<=0)
				   systemLog.scrollTop-=systemLog1.offsetHeight
			   else{
				   systemLog.scrollTop++
			   }
			   }
			   var MyMar=setInterval(Marquee,speed)
			   systemLog.onmouseover=function() {clearInterval(MyMar)}
			   systemLog.onmouseout=function() {MyMar=setInterval(Marquee,speed)}
			   </script>
				<!--滚动结束-->
				</div>
		</div>
			
			
	<div class="health_analysis_mod analysis_absolute_c">
		<div class="health_analysis_modbox"><div class="width_analysis" id="thResholdNum"></div>
				<!--滚动开始-->
				<div id="thReshold" style="overflow:hidden;height:170px;font-size:0.8vw;padding:2% 5%;white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; ">
				    <div id="thReshold1">
				    </div>
				<div id=thReshold2></div>
				</div>
				   <script>
				   var speed=50
				   thReshold2.innerHTML=thReshold1.innerHTML
				   function Marquee(){
				  		if(thReshold2.offsetTop-thReshold.scrollTop<=0)
				   				thReshold.scrollTop-=thReshold1.offsetHeight
			   			else{
				   				thReshold.scrollTop++
				   			}
				   		}
				   		var MyMar=setInterval(Marquee,speed)
			   			thReshold.onmouseover=function(){
				   			clearInterval(MyMar)
				   			}
				   		thReshold.onmouseout=function(){
				   			MyMar=setInterval(Marquee,speed)
				   			}
				   </script>
				<!--滚动结束-->
			</div>
		</div>
	</div>
	</div>
</body>
</html>
