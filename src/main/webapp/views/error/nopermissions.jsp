<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path=request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>卓智运维管理系统</title>
<link href="<%=path %>/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path %>/css/znms_style.css" rel="stylesheet" type="text/css">
<link href="<%=path %>/css/flat-ui.min.css" rel="stylesheet">
<style type="text/css">
body{ background:url(images/loginimg2.png) no-repeat center 20%;}
.no_404{ margin:10% auto; float:none;} 
</style>
</head>
<body>
<div class="login_box no_404">
<div class="icon_no_font"><span class="fui-alert-circle"></span></div>
<p class="font_no_text ">您无权访问该页面</p><br>
<br>
<div class="mod_buttom_box "><button type="button" onclick="javascript:history.go(-1);" class="btn btn-default btn-sm">返回</button></div>
</div>
</body>
</html>