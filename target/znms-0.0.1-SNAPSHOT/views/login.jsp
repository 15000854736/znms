<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta name="description" content="">
<meta name="author" content="">
<title>卓智运维管理系统</title>
<%@ include file="common/include.jsp"%>
<script src="<%=path %>/js/login_js/three.min.js"></script>
<script src="<%=path %>/js/login_js/canvas-renderer.js"></script>
<%--<script src="<%=path %>/js/login_js/color.js"></script>--%>
<script src="<%=path %>/js/login_js/projector.js"></script>


<script type="text/javascript">

$(function(){
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	doLogin();
	     }
	}
	});  

	function changidateCode(obj) {
		var timenow = new Date().getTime();
		obj.src = "checkcode/captcha-image?d=" + timenow;
	}
	function doLogin(){
		$("#password").val(hex_md5($("#password").val()));
		$("#login_form").submit();
	}
</script>
</head>
<body>
	
	<div class="login_logo"><img src="images/loginlogo.png"></div>
	<div class="login_znmsbox"> 
		<div class="login_middle">
			<form:form class="form-signin" action="login" method="post" modelAttribute="loginBean" id="login_form">
				<div class="login_box"> 
					<div class="wrong">
						<!--输出错误提示要在个层里面-->
						<%@ include file="common/message.jsp"%>
					</div>
					<div class="login_name">
						<i></i>
						<form:input path="loginName" type="text" class="input_col icon_a" value="用户名" onfocus="if (value =='用户名'){value ='';this.type = 'text'}" onblur="if (value ==''){value='用户名';this.type = 'text'}" style="color:#555;"/>
					</div>
					<div class="login_password">
						<i></i>
						<form:input path="password" type="password" value="密码" class="input_col icon_b" id="password" onfocus="if (value =='密码'){value ='';this.type = 'password'}" onblur="if (value ==''){value='密码';this.type = 'password'}"
								 style="color:#555;"/>
					</div>
					<div class="login_code">
						<i></i>
						<form:input path="validCode" id="validCode" type="text" class="input_col icon_c" value="验证码" onfocus="if (value =='验证码'){value ='';this.type = 'text'}" onblur="if (value ==''){value='验证码';this.type = 'text'}" style="color:#555;" />
						<div class="input_yzm">
						<a href="#">
						<img src="<%=path%>/checkcode/captcha-image" width="67" height="33" title="看不清换一张" onclick="changidateCode(this);">
						</a>
						</div>
					</div>
					<div class="login_input">
						<input name="" type=button value="登录" class="input_btn" onclick="doLogin()">
					</div>
				</div>
			</form:form>
		</div>
		<div  id="canvas" class="gradient"> <script src="<%=path %>/js/login_js/3d-lines-animation.js"></script></div>
	</div>
	<p class="copy">卓智网络科技有限公司 版权所有</p>


</body>

</html>
