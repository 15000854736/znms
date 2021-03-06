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
<script src="<%=path %>/js/lodash.min.js"></script>
<script src="<%=path %>/js/gridstack/gridstack.js"></script>
<script src="<%=path %>/js/gridstack/gridstack.jQueryUI.js"></script>
<script src="<%=path %>/js/echart/echarts.js"></script>
<link href="<%=path %>/css/gridstack/gridstack.css" rel="stylesheet" media="screen">
<link id="main_css" href="<%=path%>/css/znms_style.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/css/screen/screen.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/css/blank.css" rel="stylesheet" type="text/css" id="module_base_css">
<link rel="stylesheet" href="<%=path%>/css/fullPage/jquery.fullPage.css">
<script src="<%=path%>/js/fullPage/jquery.fullPage.min.js"></script>

<script src="<%=path %>/js/jquery.form.min.js"></script>
<link href="<%=path%>/css/flat-ui.min.css" rel="stylesheet" type="text/css">

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

$(document).ready(function(){
	$("#Layer1").hide();
	$("#screen_module").hide();
	$("#returnBtn").click(function(){$(".layer_box").fadeOut(50);});
	
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


var path = "<%=path %>";

function showUploadThumbnail(id){
	$("#screenId").val(id);
	$("#Layer1").show();
}
function closeUploadThumbnail(){
	$("#import_file").val("");
	$("#Layer1").hide();
}

function addScreenModule(){
	$("#screen_module").show();
}
function closeScreenModule(){
	$("#row").val("");
	$("#col").val("");
	$("#screen_module").hide();
}
$(document).bind("keydown", "esc", function(e){
		if(e.keyCode == 27){
			$(".layer_box").fadeOut(200);
		}
	});
	
function doMerge(){
	var row=$("#row").val();
	var col=$("#col").val();
	if(/\D/.test(row)){
		toastr.error('只能输入整数');
		return;
	}
	if(/\D/.test(col)){
		toastr.error('只能输入整数');
		return;
	}
	//console.log("row:"+row+" col:"+col);
	if(row>4||col>4){
		toastr.clear();
		toastr.error("行跟列不能大于4");
		return;
	}
	var name=$("#name").val();
	$.ajax({
		url:"${pageContext.request.contextPath}/screen/merge/do",
		data:$("#detailForm").serialize(),
		type:'POST',
		success:function(data){
			if(data.result){
				window.location.href = path+"/screen/toAddScreenModule?id="+data.id;
			}else{
				toastr.clear();
				toastr.error(data.msg);
			}
		}
	});		
}
function uploadThumbnail(){
	var id=$("#screenId").val();
	$("#detailUploadForm").ajaxSubmit({
        type: 'post',
        url: path+"/screen/uploadThumbnailFile?id="+id,
        success: function(data) {
       		toastr.clear();
        	if(data.result) {
        		location.reload();
        	} else {
        		toastr.error(data.msg);
        	}
        }  
   });
}	
function deleteScreen(id){
	$("#deleteDialog").show();
	$('.btn-default').off('click').on('click', function () {
		$("#deleteDialog").hide();
	});
	$('#confirm').off('click').on('click', function () {
		$.ajax({
		url:"${pageContext.request.contextPath}/screen/deleteScreen?id="+id,
		type:'POST',
		success:function(data){
			if(data.result){
				toastr.error(data.msg);
				location.reload();
			}else{
				toastr.clear();
				toastr.error(data.msg);
			}
		}
	});		
  });
}
</script>
<style type="text/css">

</style>
</head>
<body>
	<div id="screen_menu">
		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<ul class="big_screen_ul">
			<li>
				<div class="big_screen_img" style="cursor:pointer;" onclick="addScreenModule()"><img src="images/jia_b.png" ></div>
			</li>
			<c:forEach items="${screenList}" var="screen">
				<li>
					<h1>
						${screen.name}
						<div class="big_screen_menu">
						<c:if test="${screen.name ne '首页大屏' && screen.name ne '星光图' && screen.name ne '设备星光图'}">
							<i class="edit_img" title="上传缩略图" onclick="showUploadThumbnail('${screen.id}')"></i>
							<i class="del_img" title="删除" onclick="deleteScreen('${screen.id}')"></i>
						</c:if>
							
						<i class="full_screen_" title="推荐分辨率:&#10;1920 &#215; 1024&#10;1600 &#215; 900&#10;1366 &#215; 768&#10;1280 &#215; 1024"
							onclick="javascript:showFullScreen('${screen.code}');"></i>
						</div>
					</h1>
					<div class="big_screen_img">
						<c:if test="${not empty screen.thumbnail}">
							<img src="<%=path%>/image/thumbnail/${screen.thumbnail}" class="thumbnail">
						</c:if>	
						<c:if test="${empty screen.thumbnail}">
							<img src="<%=path%>/image/thumbnail/no_img.png" class="thumbnail"/>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>

		</div>
	 </div>

	 <div class="modal fade in" id="deleteDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display:none;">
	 		<div class="modal-dialog" style="width:350px;"><div class="modal-content">
	 			<div class="modal-header">
	 				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	 				</button><h4 class="modal-title" id="myModalLabel">系统提示</h4>
	 			</div>
	 			<div class="modal-body">是否确定要删除指定记录？</div>
	 			<div class="modal-footer">
	 				<button type="button" class="btn btn-primary" id="confirm">确定</button>
	 				<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
				</div>
			 </div>
		 </div>
		<div class="layer_bg filter"></div>
  		</div>
	 </div>
	 <!--弹出添加模块层-->
	 <div class="layer_box" style="display:none;" id="screen_module">
	 	<div style="margin-top:170px;">
			<div class="layer_text">
        		<div class="layer_text_bg">
				<h1>
					<div class="close"  onclick="closeScreenModule()">
						<span class="fui-cross"></span>
				</div>
			  <span >添加投屏模块</span>
			</h1>
			<form id="detailForm"  >
						<input path="id" type="hidden"/>
						<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
									<input id="row" name="row" type="hidden"  maxlength=1 value="2" class="form-control input-sm"/>
									<input id="col" name="col" type="hidden" maxlength=1 value="3"  class="form-control input-sm"/>
							<tr>
								<td class="layer_table_left bold">名称</td>
								<td>
									<input id="name" name="name" type="text" maxlength=64 class="form-control input-sm"/>
								</td>
							</tr>
						</table>
				</form>
 				<div class="mod_buttom_box">
 					<button type="button" class="btn btn-primary btn-xs" onclick="doMerge();">确定</button>
					<button class="layer_input_btn btn btn-default btn-sm" id="returnBtn" type="button" >返回</button>
 				</div>
       	 </div>
       </div>
  	</div>
  <div class="layer_bg filter"></div>
  </div>
	 <!--弹出上传缩略图层-->
	 <div class="layer_box" style="display:none;" id="Layer1">
	 	<div style="margin-top:170px;">
			<div class="layer_text">
        		<div class="layer_text_bg">
				<h1>
					<div class="close"  onclick="Layer1.style.display='none'">
						<span class="fui-cross"></span>
				</div>
			  <span >上传缩略图</span>
			</h1>
			<form id="detailUploadForm"  enctype="multipart/form-data" >
				<table border="0" cellspacing="0" cellpadding="0"  class="nms_data_tablemod padding_10">
 				 <tr>
    				<td class=" text-right"><span class=" bold">选择缩略图</span>
					</td>
    				<td><input type="file" id="import_file" name="import_file" size="40" class="bk_a selinput_doc"></td>
    				<input type=hidden name="id" id="screenId" >
  				</tr>
  			</form>
		</table>
 		<div class="mod_buttom_box">
 			<button type="button" class="btn btn-primary btn-xs" onclick="uploadThumbnail()";>确定</button>
 			<button type="button" class="btn btn-default btn-sm" onclick="closeUploadThumbnail()">取消</button>
 		</div>
        </div>
       </div>
  </div>
  <div class="layer_bg filter"></div>
  </div>
	
	<c:forEach items="${screenList}" var="screen">
		<div id="full_screen_${screen.code}" style="display:none" class="full_screen">
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
								if(node.x==0){
									if(node.height==2){
										this.grid.addWidget($('<div><div class="grid-stack-item-content " data="'+node.moduleName+'"></div></div>'),
										node.x, node.y, node.width, node.height);
									}else{
										this.grid.addWidget($('<div><div class="line_box1"></div><div class="grid-stack-item-content " data="'+node.moduleName+'"></div><div class="line_bottom"></div></div>'),
											node.x, node.y, node.width, node.height);
									}
								}else{
									this.grid.addWidget($('<div><div class="line_box"></div><div class="grid-stack-item-content " data="'+node.moduleName+'"></div><div class="line_bottom"></div></div>'),
										node.x, node.y, node.width, node.height);
								}
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

</body>
</html>
