<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@ include file="../common/include.jsp" %>
    <link href="<%=path%>/css/layout.css" rel="stylesheet" type="text/css">
    <style type="text/css"></style>
    <!--[if IE 6]>
    <script src="DD_belatedPNG_0.0.8a.js" mce_src="DD_belatedPNG_0.0.8a.js"></script>
    <script type="text/javascript">DD_belatedPNG.fix('*');</script>
    <![endif]-->
<title>卓智运维管理系统</title>
<link href="<%=path %>/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path %>/css/znms_style.css" rel="stylesheet" type="text/css">
<link href="<%=path %>/css/flat-ui.min.css" rel="stylesheet">
<style type="text/css">
body{ background:url(images/loginimg2.png) no-repeat center 20%;}
.no_404{ margin:10% auto; float:none;} 
</style>
</head>
<script type="text/javascript">
    function backToHomePakge() {
        location.href = "<%=path%>" + "/index";//location.href实现客户端页面的跳转
    }
</script>
<body>

<div class="login_box no_404">
<div class="icon_no_font"><span class="fui-alert-circle"></span></div>
<p class="font_no_text ">当前页面不存在！</p><br>
<br>
<div class="mod_buttom_box "><button type="button" onclick="javascript:history.go(-1);" class="btn btn-default btn-sm">返回</button></div>
</div>
</body>
</html>