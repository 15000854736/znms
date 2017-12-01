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

	<!-- jquery 主文件 -->
	<script src="<%=path %>/js/jquery-1.10.2.min.js"></script>
	<!-- 消息提示控件 start -->
	<script src="<%=path %>/js/toastrplugins/toastr.js"></script>
	<link href="<%=path %>/css/toastrplugins/toastr.min.css" rel="stylesheet"/>
	<!-- 消息提示控件 end -->

	<link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<script src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/3.5.0/lodash.min.js"></script>
	<script src="<%=path %>/js/gridstack/gridstack.js"></script>
	<script src="<%=path %>/js/gridstack/gridstack.jQueryUI.js"></script>
	<link href="<%=path %>/css/gridstack/gridstack.css" rel="stylesheet" media="screen"></link>

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
		var winWidth  = window.screen.width;
		var winHeight = window.screen.height;
		var resolutionOk = ((winWidth==1920 && winHeight == 1080) || (winWidth == 1600 && winHeight == 900) || (winWidth == 1366 && winHeight == 768) || (winWidth == 1280 && winHeight == 1024));

		if(winWidth>=1920) {
			winWidth = 1920;
			winHeight = 1080;
		} else if(winWidth >= 1600) {
			winWidth = 1600;
			winHeight = 900;
		} else if(winWidth >= 1366) {
			winWidth = 1366;
			winHeight = 768;
		} else {
			winWidth = 1280;
			winHeight = 1024;
		}

		function getScreenCss(){
			//判断显示器分辨率
			var href = "<%=path%>/css/znms_style_show_"+winWidth+"_"+winHeight+".css";
			return href;
		}

		$("<link>").attr({ rel: "stylesheet",
			id: "screen_css_base",
			type: "text/css",
			href: "<%=path%>/css/znms_style_show_base.css"
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
	<script type="text/javascript" src="<%=path %>/js/screen.js"></script>

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

		.grid-stack {
			background: lightgoldenrodyellow;
		}

		.grid-stack-item-content {
			color: #2c3e50;
			text-align: center;
			background-color: #18bc9c;
		}
	</style>

	<script type="text/javascript">


		var rightDownPieFill = ["rgb(51, 204, 204)","rgb(51, 153, 0)","rgb(255, 153, 0)","rgb(204, 0, 51)","rgb(255, 102, 0)","rgb(102, 102, 153)","rgb(0, 153, 255)","rgb(255, 153, 51)","rgb(255, 34, 0)","rgb(0, 204, 0)"];
		var registerUserCircle;
		var activeUserCircle;
		var freeUserCircle;
		var lostUserCircle;

		var centerUpHeatMap;
		var centerUpHeatMap1;

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
			if(!resolutionOk) {
				toastr.clear();
				toastr.warning("当前分辨率非推荐分辨率，画面可能出现显示不正常的现象");
			}
			$("body").css("background", "url('<%=path%>/image/${bgImage}') repeat center 0");

			$("#screen_css").attr("href", getScreenCss());
			$("#screen_css_base").attr("href", "<%=path%>/css/znms_style_show_base.css");
			$("#main_css").attr("href", "<%=path%>/css/blank.css");
			$("#full_screen").siblings("div").hide();

			var apMaxUserCount = "${apMaxUserCount}";
			if(isNaN(apMaxUserCount)) {
				apMaxUserCount = 150;
			}
			var heatMapRadius = "${heatMapRadius}";
			if(isNaN(heatMapRadius)) {
				heatMapRadius = 20;
			}

			refreshLeftUpData("<%=path%>/screen/getLeftUpAreaData");
			refreshCenterUpData("<%=path%>/screen/getCenterUpAreaData", apMaxUserCount, heatMapRadius);
			refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
			refreshLeftDownData("<%=path%>/screen/getLeftDownAreaData", getLeftDiameter());
			refreshRightDownData("<%=path%>/screen/getRightDownAreaData", getRightDiameter());

			setTimeout(function(){
				refreshLeftUpData("<%=path%>/screen/getLeftUpAreaData");
				refreshCenterUpData("<%=path%>/screen/getCenterUpAreaData", apMaxUserCount, heatMapRadius);
				refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
				refreshLeftDownData("<%=path%>/screen/getLeftDownAreaData", getLeftDiameter());
				refreshRightDownData("<%=path%>/screen/getRightDownAreaData", getRightDiameter());
			}, 100);
			refresher1 = setInterval(function(){
				refreshLeftUpData("<%=path%>/screen/getLeftUpAreaData");
				refreshCenterUpData("<%=path%>/screen/getCenterUpAreaData", apMaxUserCount, heatMapRadius);
				refreshRightUpData("<%=path%>/screen/getRightUpAreaData");
				refreshLeftDownData("<%=path%>/screen/getLeftDownAreaData", getLeftDiameter());
			}, 60000);
			refresher2 = setInterval(function(){
				refreshRightDownData("<%=path%>/screen/getRightDownAreaData", getRightDiameter());
			}, 300000);

			scroller = setInterval(function(){
				if($("#left_down_scroller").children().length<=3){
					return;
				}
				$("#left_down_scroller").animate({"margin-left":"-27.3%"}, "normal", "linear", function(){
					$("#left_down_scroller dl:eq(0)").appendTo($("#left_down_scroller"));
					$("#left_down_scroller").css("margin-left","0px");
				});
			}, 6000)

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
			$("#full_screen").siblings("div").show();
			$(".full_screen").hide();
		}


		function getRightDiameter() {
			if(winWidth == 1920) {
				return "360";
			} else if(winWidth == 1600) {
				return "300"
			} else if(winWidth == 1366) {
				return "265";
			} else if(winWidth == 1280) {
				return "250";
			} else {
				return "230";
			}
		}
		function getLeftDiameter() {
			if(winWidth == 1920) {
				return "220";
			} else if(winWidth == 1600) {
				return "190"
			} else if(winWidth == 1366) {
				return "160";
			} else if(winWidth == 1280) {
				return "140";
			} else {
				return "120";
			}
		}

		function showHeatBoxFullScreen(){
			if(!resolutionOk) {
				toastr.clear();
				toastr.warning("当前分辨率非推荐分辨率，画面可能出现显示不正常的现象");
			}
			$("body").css("background", "url('<%=path%>/image/${bgImage}')  repeat center 0");

			$("#screen_css").attr("href", getScreenCss());
			$("#screen_css_base").attr("href", "<%=path%>/css/znms_style_show_base.css");
			$("#main_css").attr("href", "<%=path%>/css/blank.css");
			$("#full_screen_heatbox").siblings("div").hide();

			$("#full_screen_heatbox").show();
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

			$(".grid-stack-item-content").each(function (index,node) {
				var moduleName = $(node).attr("data");
				if(moduleName!='undefined'){
					$.get("<%=path%>/screen/module/"+moduleName,function(data){
						$(node).html(data);
					});
				}
			});
		}
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

		<div>
			<div>
				<div class="R" onclick="javascript:showHeatBoxFullScreen();">
					<a href="javascript:void(0);" title="推荐分辨率:&#10;1920 &#215; 1024&#10;1600 &#215; 900&#10;1366 &#215; 768">
						<img src="<%=path%>/images/fullimg.png"/>
					</a>
				</div>
				<img src="<%=path%>/images/preview.png" style="max-height:500px;max-width:100%;"/>
			</div>
		</div>

	</div>

</div>

<div id="full_screen" class="full_screen">
	<div class="prj_logo">
		<img src="<%=path%>/image/${logoImage}"/>
	</div>
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
			<div id="heat_map">
				<img src="<%=path%>/image/${mapImage}"
					 style="width: 100%; height: 100%;">
			</div>
		</div>

		<div class="line_box"></div>

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
	</div>
	<div class="line_bottom"></div>
	<div class="prj_bottom">
		<div id="left_down" style="position:relative;">
			<div style="width:100%;position:absolute;overflow:hidden;">
				<div style="width:10000px;" id="left_down_scroller">
					<c:forEach items="${leftDown}" var="exportStreamInfo">
						<dl class="export_utilization">
							<dt>${exportStreamInfo.name}</dt>
							<dd class="scale_diagram_a">
								<div id="${exportStreamInfo.divId}" class="doughnut_container"></div>
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
		<!-- 		<div class="line_box"></div> -->

		<div class="zlog_box">
			<h1>上网数据分析</h1>
			<div class="zlog_data_a" id="url_ranking">
			</div>
			<ul class="zlog_data_list" id="zlog_data_list">
				<c:forEach items="${rightDown.urlRankingData.urlRankingList}" var="urlRanking"
						   varStatus="status">
					<li>
						<div class="zlogdata_url">${urlRanking.url}</div>
						<div class="zlogdata_num">
							<fmt:formatNumber type="number"
											  value="${(urlRanking.count * 10000 )/(rightDown.urlRankingData.total * 100)}"
											  maxFractionDigits="2" minFractionDigits="2"/>
							%
						</div>
						<div class="data_img_box">
							<div class="percentage_box warn_${status.index+1}"
								 style="width: <fmt:formatNumber type="number"
																 value="${(urlRanking.count * 10000 )/(rightDown.urlRankingData.total * 100)}"
																 maxFractionDigits="2" />%;"></div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<ul class="zlog_data_b">
				<li style="position:relative;">
					<div class="zlog_title">CPU使用率  <span id="zlog_cpu_title"></span></div>
					<div id="zlog_cpu" class="zlog_chart"></div>
				</li>
				<li style="position:relative;">
					<div class="zlog_title">内存使用率  <span id="zlog_mem_title"></span></div>
					<div id="zlog_mem" class="zlog_chart"></div>
				</li>
				<li style="position:relative;">
					<div class="zlog_title">最近日志量  <span id="zlog_log_count_title"></span></div>
					<div id="zlog_log_count" class="zlog_chart"></div>
				</li>
			</ul>
		</div>

	</div>



	<div class="line_bottom"></div>

	<div class="content-container-fluid">
		<div class="row">
			<div class="cols-sample-area">
				<div id="container"></div>
			</div>
		</div>
	</div>
</div>




<div id="full_screen_heatbox" style="display:none" class="full_screen">
	<div class="prj_logo">
		<img src="<%=path%>/image/${logoImage}"/>
	</div>
	<div class="grid-stack">
	</div>
	<script type="text/javascript">
		$(function () {
			var options = {
				float: false,
				disableDrag:true,
				disableResize:true,
				height:3,
				width:12,
				removable:false,
				verticalMargin:0,
				cellHeight:336
			};
			$('.grid-stack').gridstack(options);

			new function () {

				this.serializedData = [
					{x: 0, y: 0, width: 8, height: 2,source:'title1'},
					{x: 8, y: 1, width: 4, height: 2,source:'test'},
					{x: 8, y: 0, width: 4, height: 1,source:'title1'},
					{x: 0, y: 2, width: 8, height: 1,source:'test'},
				];
				this.loadGrid = function () {
					this.grid.removeAll();
					var items = GridStackUI.Utils.sort(this.serializedData);
					_.each(items, function (node) {
						this.grid.addWidget($('<div><div class="grid-stack-item-content " data="'+node.source+'"></div></div>'),
								node.x, node.y, node.width, node.height);
					}, this);
					return false;
				}.bind(this);

				this.grid = $('.grid-stack').data('gridstack');
				this.loadGrid();
			};
		});
	</script>
</div>



</body>
</html>
