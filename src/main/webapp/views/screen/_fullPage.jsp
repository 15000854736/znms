<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<%
	String path = request.getContextPath();
%>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<title>卓智运维管理系统</title>
<link type="image/x-icon" href="<%=path%>/images/zos.ico" 	rel="shortcut icon">

<link rel="stylesheet" href="<%=path%>/css/fullPage/jquery.fullPage.css">
<style>

</style>
<script src="<%=path%>/js/fullPage/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/js/fullPage/jquery.fullPage.min.js"></script>


<link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js"></script>
<script src="<%=path %>/js/lodash.min.js"></script>
<script src="<%=path %>/js/gridstack/gridstack.js"></script>
<script src="<%=path %>/js/gridstack/gridstack.jQueryUI.js"></script>
<link href="<%=path %>/css/gridstack/gridstack.css" rel="stylesheet" media="screen">
<link id="main_css" href="<%=path%>/css/znms_style.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/css/screen/screen.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/css/blank.css" rel="stylesheet" type="text/css" id="module_base_css">



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
var refreshHander_1,refreshHander_2,refreshHander_3,refreshHander_4,refreshHander_5,refreshHander_6,refreshHander_studentAccessType,refreshHander_teacherAccessType;
	
//退出全屏
function hideFullScreen(){
	toastr.clear();
	$("body").css("background","url('<%=path%>/images/indexbg.jpg') no-repeat fixed");
	$("#module_base_css").attr("href","<%=path%>/css/blank.css");
	if(refreshHander_1!=null){
		clearInterval(refreshHander_1);
		refreshHander_1 = null;
	}
	if(refreshHander_2!=null){
		clearInterval(refreshHander_2);
		refreshHander_2 = null;
	}

	if(refreshHander_3!=null){
		clearInterval(refreshHander_3);
		refreshHander_3 = null;
	}

	if(refreshHander_4!=null){
		clearInterval(refreshHander_4);
		refreshHander_4 = null;
	}

	if(refreshHander_5!=null){
		clearInterval(refreshHander_5);
		refreshHander_5 = null;
	}

	if(refreshHander_6!=null){
		clearInterval(refreshHander_6);
		refreshHander_6 = null;
	}
	
	if(refreshHander_studentAccessType!=null){
		clearInterval(refreshHander_studentAccessType);
		refreshHander_studentAccessType = null;
	}
	
	if(refreshHander_teacherAccessType!=null){
		clearInterval(refreshHander_teacherAccessType);
		refreshHander_teacherAccessType = null;
	}

	//$("#main_css").attr("href", "<%=path%>/css/znms_style.css");
	$('link[id^="screen_module_css_"]').remove();
	$('script[id^="screen_module_js_"]').remove();
	$("#main_css").attr("href", "<%=path%>/css/znms_style.css");
	$("#screen_menu").show();
	$(".full_screen").hide();
}

function appendScript(pathToScript,id) {
	var head = document.getElementsByTagName("head")[0];
	var js = document.createElement("script");
	js.type = "text/javascript";
	js.src = pathToScript;
	js.id = id;
	head.appendChild(js);
}

//全屏
function showFullScreen(code){
	if(!resolutionOk) {
		toastr.clear();
		toastr.warning("当前分辨率非推荐分辨率，画面可能出现显示不正常的现象");
	}
	$("body").css("background", "url('<%=path%>/image/${bgImage}')  repeat center 0");
	if(code=="index"){
		$("#module_base_css").attr("href","");
	}else{
		$("#module_base_css").attr("href","<%=path%>/css/screen/module/module_style.css");
	}
	$("#main_css").attr("href", "<%=path%>/css/blank.css");
	$("#screen_menu").hide();
	$("#full_screen_"+code).show();


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

	$("#grid-stack-"+code+" .grid-stack-item-content").each(function (index,node) {
		var moduleName = $(node).attr("data");
		if(moduleName!='undefined'){
			$("<link>").attr({ rel: "stylesheet",
				id: "screen_module_css_"+moduleName,
				type: "text/css",
				href: "<%=path%>/css/screen/module/"+moduleName+"/screen_"+moduleName+"_base.css"
			}).appendTo($("head"));

			$("<link>").attr({ rel: "stylesheet",
				id: "screen_module_css_"+moduleName,
				type: "text/css",
				href: "<%=path%>/css/screen/module/"+moduleName+"/screen_"+moduleName+"_"+cssWidth+"_"+cssHeight+".css"
			}).appendTo($("head"));

			appendScript("<%=path%>/js/screen/module/screen_"+moduleName+".js","screen_module_js_"+moduleName);

			$.get("<%=path%>/screen/module/"+moduleName,function(data){
				$(node).html(data);
				//调用模块方法
				if(typeof window["showFullScreen_"+moduleName] == "function") {
					window["showFullScreen_"+moduleName]();
				}
			});
		}
	});

}	
	
	
	
	
	
	$(function() {
		$('#dowebok').fullpage({
			//sectionsColor : ['#1bbc9b', '#4BBFC3', '#7BAABE', '#f90' ],
			navigationPosition:"left",
			paddingTop:"0",
			controlArrowColor:"red",
			keyboardScrolling:true,
			animateAnchor:true
		});
	});
</script>
</head>

<body>
	<div id="screen_menu">
		<!-- 头部菜单 start-->
		<%-- <%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%> --%>
		<div id="dowebok">
			<div class="section">
		<c:forEach items="${screenList}" var="screen">
		<div id="full_screen_${screen.code}" style="display:block" class="full_screen">
			<div class="screen_logo">
				<img src="<%=path%>/image/${logoImage}"/>
			</div>
			<div class="grid-stack" id="grid-stack-${screen.code}">
			</div>
			<script type="text/javascript">
			var logo_height = "";
				$(function () {
					logo_height = ${logoHeight};
					if(logo_height>50){
						logo_height=50;
					}
					var options = {
							float: false,
							disableDrag:true,
							disableResize:true,
							height:${screen.row},
							width:12,
							removable:false,
							verticalMargin:0,
							cellHeight:(winHeight-logo_height)/${screen.row}
					};
					$('#grid-stack-${screen.code}').gridstack(options);

					new function () {
						this.serializedData = ${screen.configData};
						this.loadGrid = function () {
							this.grid.removeAll();
							var items = GridStackUI.Utils.sort(this.serializedData);
							_.each(items, function (node) {
								this.grid.addWidget($('<div><div class="grid-stack-item-content " data="'+node.moduleName+'"></div></div>'),
										node.x, node.y, node.width, node.height);
							}, this);
							return false;
						}.bind(this);

						this.grid = $('#grid-stack-${screen.code}').data('gridstack');
						this.loadGrid();
					};
				});
			</script>
		</div>
	</c:forEach>
	
	
			<%-- <div style="width: 540px;height: 540px;">
				<img src="<%=path%>/image/HOME-BG.jpg" alt="test" style="width: 100%;height: 100%;"/>
			</div>
			<div style="width: 540px;height: 540px;">
				<img src="<%=path%>/image/a.jpg" alt="test" style="width: 100%;height: 100%;"/>
			</div> --%>
				
			</div>
			<div class="section">
				<div class="slide">
					<div style="width: 100%;height: 100%;">
						<img src="<%=path%>/image/c.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
				</div>
				<div class="slide">
					<div style="width: 100%;height: 100%;">
						<img src="<%=path%>/image/d.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
				</div>
				<div class="slide">
					<div style="width: 100%;height: 100%;">
						<img src="<%=path%>/image/e.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
				</div>
			</div>
			<div class="section">
				<div style="width: 100%;height: 100%;">
						<img src="<%=path%>/image/b.jpg" alt="test" style="width: 100%;height: 100%;"/>
					</div>
			</div>
			<div class="section">
				<h3>第四屏</h3>
				<p>这是最后一屏</p>
			</div>
		</div>
	</div>
</body>
</html>
