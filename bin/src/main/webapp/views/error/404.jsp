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

</head>
<script type="text/javascript">
    function backToHomePakge() {
        location.href = "<%=path%>" + "/index";//location.href实现客户端页面的跳转
    }
</script>
<body>
<div class="wrong_bg">
    <div class="wrong_box">
        <h1>系统错误</h1>

        <p>当前页面不存在！</p>

        <button class="submit_btn" onclick="backToHomePakge()">返回</button>

    </div>
</div>
</body>
</html>