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
<link id="main_css" href="<%=path%>/css/znms_style.css" rel="stylesheet" type="text/css">

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
<script type="text/javascript">
var path = "<%=path %>";
	
	function addScreen(){
		$("#addScreen").show();
	}
$(document).bind("keydown", "esc", function(e){
		if(e.keyCode == 27){
			$(".layer_box").fadeOut(200);
		}
	});
$(document).ready(function(){
	$("#returnBtn").click(function(){$(".layer_box").fadeOut(50);});	
	
		
	
});

function doMerge(){
	var screenWidth=$("#screenWidth").val();
	var screenHeight=$("#screenHeight").val();
	$.ajax({
		url:"${pageContext.request.contextPath}/screenConfig/merge/do?screenWidth="+screenWidth+"&screenHeight="+screenHeight,
		data:$("#screenConfigForm").serialize(),
		type:'POST',
		success:function(data){
				console.log("************"+data);
				window.location.href = path+"/screenConfig/add?screenId="+data;
			if(data.result){
				//toastr.error(data.msg);
				window.location.href = path+"/screenConfig/add?screenId="+data.id;
			}else{
				toastr.clear();
				toastr.error(data.msg);
			}
		}
	});		
}
</script>


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

</head>
<body>
		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		
		 <div class="screen_grid">
			<div>
				<div>
						<a href="javascript:void(0);" onclick="addScreen()"  title="添加定制大图">
						<img src="<%=path%>/images/plus.png" style="max-height:400px;max-width:60%;"/>
					</a>
				</div>
			</div>
		</div>
		<!-- 详细/修改用窗口：start -->
		<div class="layer_box" style="display:none;" id="addScreen">
			<div class="layer_bg filter"></div>
	 			<div class="layer_center">
					<div class="layer_text">
						<div class="layer_text_bg" >
							<h1>
								<div class="close"  onclick="Layer1.style.display='none'">
									<span class="fui-cross"></span>
								</div>
								<span id="addTitle">添加大屏页面</span>
							</h1>
					<form:form action="${pageContext.request.contextPath }/screenConfig/merge/do" id="screenConfigForm" modelAttribute="screenConfig" method="post">
						<form:input path="screenId" type="hidden"/>
						<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
							<tr>
								<td class="layer_table_left bold">宽</td>
								<td>
									<input id="screenWidth" name="screenWidth" type="text" maxlength=64 class="form-control input-sm"/>
								</td>
								<td class="layer_table_left bold">高</td>
								<td>
									<input id="screenHeight" name="screenHeight" type="text" maxlength=64 class="form-control input-sm"/>
								</td>
							</tr>
						</table>
					</form:form>
				<div class="mod_buttom_box">
	 				<button class="layer_input_btn" id="submitBtn"  type="button"  onclick="doMerge();">确定</button>
					<button class="layer_input_btn" id="returnBtn" type="button" >返回</button>
	 		</div>
           	 	</div>
			</div>
		</div>
</body>
</html>
