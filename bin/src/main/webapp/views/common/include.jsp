<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="info.zznet.znms.web.util.AuthUtil"%>
<%
	String path=request.getContextPath();
	String commonUri = AuthUtil.getRequestURIWithJsp(request);
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<title>Z-NMS</title>
<!-- title的icon  start-->
<link  type="image/x-icon" href="<%=path%>/images/zos.ico"  rel="shortcut icon">
<!-- title的icon  end-->

<!-- jquery 主文件 -->
<script src="<%=path %>/js/jquery-1.10.2.min.js"></script>

<!-- jquery validate控件  start-->
<script src="<%=path %>/js/jquery.validate.min.js"></script>
<script src="<%=path %>/js/messages_zh.js"></script>
<script src="<%=path %>/js/jquery.validate.extend.js"></script>
<!-- jquery validate控件  end-->

<!-- Metadata - jQuery plugin for parsing metadata from elements -->
<script src="<%=path %>/js/jquery.metadata.min.js"></script>

<!-- 实现字符串的Base64编码和解码 -->
<script src="<%=path %>/js/jquery.base64.js"></script>

<!-- jquery form表单的自动填充 -->
<script src="<%=path %>/js/jquery.formFill.js"></script>

<!-- md5加密 -->
<script src="<%=path %>/js/md5-min.js"></script>

<!-- 上方导航菜单的效果实现js -->
<script src="<%=path %>/js/menu.js"></script>

<!-- 消息提示控件 start -->
<script src="<%=path %>/js/toastrplugins/toastr.js"></script>
<link href="<%=path %>/css/toastrplugins/toastr.min.css" rel="stylesheet"/>
<!-- 消息提示控件 end -->

<!-- bootstrap主文件以及控件  start -->
<script src="<%=path %>/js/bootstrap.min.js"></script>
<script src="<%=path %>/js/bootstrap-table.js"></script>
<script src="<%=path %>/js/bootstrap-switch.js"></script>
<!-- bootstrap主文件以及控件  end -->

<script src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js"></script>

<!-- 解决json.stringify在ie8下的冲突问题 -->
<script src="<%=path %>/js/json2.js"></script>

<!-- 解决placeholder在ie8下的冲突问题 -->
<script src="<%=path %>/js/jquery.jplaceholder.js"></script>

<!-- datepicker控件 -->
<script src="<%=path %>/js/datepicker/WdatePicker.js"></script>

<link href="<%=path %>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/bootstrap-table.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/message.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/switch.css" rel="stylesheet" media="screen">
<%-- <link href="<%=path %>/css/znms_layout.css" rel="stylesheet" type="text/css"> --%>
<link href="<%=path %>/css/adjust.css" rel="stylesheet" type="text/css">
<fmt:setBundle basename="i18n/common" var="commonBundle"/>
<fmt:setBundle basename="i18n/viewCommon" var="viewCommonBundle"/>


<!-- progress控件 start -->
<script src="<%=path%>/js/progress/msgbox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/progress/msgbox.css">
<!-- progress控件 end -->

<!-- jqwidget控件 start -->
<script src="<%=path%>/js/jqx/jqxcore.js"></script>
<script src="<%=path%>/js/jqx/jqxdropdownlist.js"></script>
<script src="<%=path%>/js/jqx/jqxlistbox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/jqxstyles/jqx.base.css">
<script type="text/javascript" src="<%=path%>/js/jqx/jqxbuttons.js"></script>
<script type="text/javascript" src="<%=path%>/js/jqx/jqxscrollbar.js"></script>
<script type="text/javascript" src="<%=path%>/js/jqx/jqxcheckbox.js"></script>
<!-- jqwidget控件 end -->

<!-- echart控件 start -->
<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<script type="text/javascript" src="<%=path %>/js/echart/shine.js"></script>
<!-- echart控件 end -->

<script type="text/javascript" src="<%=path%>/js/common-validator.js"></script>

<!-- Loading Bootstrap -->
<!-- <link href="css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->
<!-- Loading Flat UI -->
<link href="<%=path%>/css/flat-ui.min.css" rel="stylesheet">
<link rel="shortcut icon" href="<%=path%>/images/favicon.ico">
<link href="<%=path%>/css/znms_style.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/css/znms_custom.css" rel="stylesheet" type="text/css">


<fmt:setBundle basename="i18n/viewCommon" var="comBundle"/>
<fmt:setBundle basename="i18n/menu" var="menuBundle"/>
<script>
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
		
$(document).ready(function() {
        // Switch
        $("[data-toggle='switch']").wrap('<div class="switch" />').parent().bootstrapSwitch();
    });
    //基本搜索项
    function basicQueryItems(params){
        return []
    }

    //高级搜索项
    function advancedQueryItems(params){
        return []
    }
</script>
<%if(request.getParameter("result_msg")!=null && "save_success".equals(request.getParameter("result_msg"))){ %>
<script>
$(document).ready(function() {
	toastr.success($("#addSuccess").text());
});
</script>
<%}else if(request.getParameter("result_msg")!=null && "update_success".equals(request.getParameter("result_msg"))){%>
<script>
$(document).ready(function() {
	toastr.success($("#updateSuccess").text());
});
</script>
<%}else if(request.getParameter("result_msg")!=null && "update_failed".equals(request.getParameter("result_msg"))){%>
<script>
$(document).ready(function() {
	toastr.error($("#updateFailed").text());
});
</script>
<%}else if(request.getParameter("result_msg")!=null && "save_failed".equals(request.getParameter("result_msg"))){%>
<script>
$(document).ready(function() {
	toastr.error($("#addFailed").text());
});
</script>
<%}%>
<body>
<span id="addSuccess" style="display: none;">
 	<fmt:message key="common.info.add.success" bundle="${comBundle}"/>
</span>
<span id="updateSuccess" style="display: none;">
 	<fmt:message key="common.info.update.success" bundle="${comBundle}"/>
</span>
<span id="addFailed" style="display: none;">
 	<fmt:message key="common.info.add.failed" bundle="${comBundle}"/>
</span>
<span id="updateFailed" style="display: none;">
 	<fmt:message key="common.info.update.failed" bundle="${comBundle}"/>
</span>
<input id="common_uri" value="<%=commonUri %>" type="hidden">
</body>
