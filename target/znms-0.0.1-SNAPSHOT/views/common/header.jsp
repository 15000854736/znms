<%@ taglib uri="http://www.znet.info/tag/znms/view" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="info.zznet.znms.web.util.ConfigUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="info.zznet.znms.base.util.DateUtil" %>
<%@ page import="info.zznet.znms.web.module.security.bean.SessionBean" %>
<%
	String basePathUrl = request.getContextPath();
	
	String authTime = ConfigUtil.getString("SYS_EFFECTIVE_TIME",null);
	Boolean isFail = false;//是否失效
	if(authTime!=null){
		Date authTime2 = DateUtil.strToDate(authTime);
		isFail = (authTime2.getTime()-(new Date()).getTime())>0?false:true;
	}
	Boolean isHaveUnbindPri = ConfigUtil.getBoolean("SYS_ACTIVATION_STATE",null);;
	
%>
<!-- echart控件 start -->
<script type="text/javascript" src="<%=path %>/js/echart/shine.js"></script>
<!-- echart控件 end -->

<!-- ejchart 3d控件 start -->
<%--<script type="text/javascript" src="<%=path %>/js/ejChart/ej.web.all.min.js"></script>--%>
<script type="text/javascript" src="<%=path %>/js/ejChart/properties.js"></script>
<!-- ejchart 3d控件 end -->

<div class="nms_logo">
	<img src="<%=basePathUrl %>/images/indexlogo.png" >
	<ul class="top_loginbox">
		<li>
			<span class="fui-user"></span>
			欢迎您，<a href="#">${sessionScope.SESSION_BEAN.smAdmin.adminId}</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath }/host?hostWorkStatus=0">宕机<span class="top_wrong_font" id="bad_status_host_count"></span></a>
		</li>
		<c:if test="${mapImage == null }">
		<li>
			<a href="javascript:void(0);" id="about_system">关于</a>
		</li>
		</c:if>
		<li> 
			<a href="${pageContext.request.contextPath }/logout"><span class="fui-arrow-right"></span>退出</a>
		</li>
	</ul>
</div>
<c:if test="${mapImage == null}">
<div class="layer_box_dummy" style="text-align:center; position: fixed; z-index:9999; width:100%; height:100%;display: none !important;" id="about_system_layer">
	<div class="layer_center">
		<div class="layer_text bg_aboutme">
			<div class="layer_text_bg">
				<h1>
					<div class="close" onclick="about_system_layer.style.display='none'">
						<span class="fui-cross"></span>
					</div>
					<span><img src="<%=basePathUrl%>/images/loginlogo.png" width="200"
						height="42"></span>
				</h1>
				<table border="0" cellspacing="0" cellpadding="0"
					class=" padding_10">
					<tr>
						<td class="nms_data_right font_14px" id="system_version"></td>
					</tr>
				</table>
				<div id="noteffected">
					<div id="weijihuo">
						<p>未激活的产品，请在三十分钟内激活</p>
					</div>
					<div id="shixiao">
						<p>失效的产品，请在三十分钟内激活 </p>
						<p>有效期：<%=authTime%></p>
					</div>
					<p> <a id="activateButton" style="cursor: pointer;text-decoration:underline;" herf="#"> <span>激活产品</span></a></p>
				</div>
				<div id="effected">
					<p>有效期：<%=authTime%></p>
					<p id="canUnbind">激活的产品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="unbindDevice" style='cursor: pointer;text-decoration:underline;' herf="#"> <span>解绑产品</span></a></p>
				    <p id="canNotUnbind">激活的产品</p>
				</div>
			</div>
		</div>
	</div>
	<div class="layer_bg filter"></div>
</div>
<!-- 激活的div -->
<div class="yes_box" id="activate_div" style="display: none;">
	<div class="closeb" id="activate_close">
		<a href="#">
			<span>关闭</span>
		</a>
	</div>
	<p>
		请输入授权码：<input id="serialNumberValue" class="wb_mod" style="width: 250px;">
	</p>
	<p>
		<a id="effctivButton" style="cursor: pointer;text-decoration:underline;" herf="#"> <span>激活</span></a>
	</p>
</div>
<!-- 解绑的div -->
<div class="yes_box" id="system_unbind_div" style="display: none;">
	<div class="closeb" id="unbind_close">
		<a href="#">
			<span>关闭</span>
		</a>
	</div>
	<p>
		<select id="unbindHours" class="wb_mod" style="width: 100px;">
			<option>0</option>
			<option>2</option>
			<option>6</option>
			<option>12</option>
			<option>24</option>
		</select>小时后绑定失效
	</p>
	<p>
		<a id="unbindButoon" style='cursor: pointer;text-decoration:underline;' herf="#"> <span>解绑</span></a>
	</p>
</div>

</c:if>
<script>
	refreshBadStatusHostCount();
	setInterval(function(){
		refreshBadStatusHostCount();
	}, 60000)
	function refreshBadStatusHostCount(){
		$.ajax({
			url:"<%=basePathUrl%>/home/getBadStatusHostCount",
			type:"POST",
			success:function(data){
				if(data > 0) {
					$("#bad_status_host_count").text("("+data+")");
					$("#bad_status_host_count").addClass("top_wrong_font");
				} else {
					$("#bad_status_host_count").text("");
					$("#bad_status_host_count").removeClass("top_wrong_font");
				}
			}
		})
	}
	<c:if test="${mapImage == null}">
	$(document).ready(function(){
		if(document.URL)
		$("#about_system").click(function(){
			$("#about_system_layer").css("display","table")	
		});
		$.ajax({
			url:"<%=basePathUrl%>/home/getSystemVersion",
			type:"POST",
			success:function(data){
				$("#system_version").text("版本号："+data);
			}
		});

		$.ajax({
			url:"<%=path %>/js/ejChart/ej.web.all.min.js",
			type:"GET",
			success:function(data){
				console.log("get ejChart js:"+new Date().toDateString());
			}
		});
	})
	</c:if>
	
	var authTime = "<%=authTime%>";
	var isFail = "<%=isFail%>";
	var isHaveUnbindPri = "<%=isHaveUnbindPri%>";
	
	//激活按钮 点击弹出激活层
	$("#activateButton").click(function(){
		$('#aboutUs_div').fadeOut(200);
		$('#activate_div').fadeIn(200);

	});

	//激活层关闭按钮
	$("#activate_close").click(function(){
		$('#activate_div').fadeOut(200);
		$("#serialNumberValue").val("");

	});

	//解绑设备
	$("#unbindDevice").click(function(){
		$('#aboutUs_div').fadeOut(200);
		$("#system_unbind_div").fadeIn(200);
	});

	//关闭解绑层
	$("#unbind_close").click(function(){
		$("#system_unbind_div").fadeOut(200);

	});
	
	//激活按钮点击事件
	$("#effctivButton").click(function(){
		var serialNumberValue= $("#serialNumberValue").val();
		if(serialNumberValue!=null && checkisNotNull(serialNumberValue)){
			$.ajax({
				url:"<%=basePathUrl %>/authResult/check/serialNumber",
				data: {"serialNumber":$("#serialNumberValue").val()},
				type: "POST",
				dataType: "json",
				async: false,
				success: function (data) {
					if(data.result==true){
						$('#activate_div').fadeOut(200);
						$("#reminderDiv").fadeIn(200);
						toastr.success("激活成功！");
						

					}else{
						$('#activate_div').fadeOut(200);

						if(data.msg!=null && checkisNotNull(data.msg)){
							toastr.error("激活失败，"+data.msg);
						}else{
							toastr.error("激活失败!");
						}
					}
					$("#serialNumberValue").val("");



				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					toastr.error("激活失败！");
					$("#serialNumberValue").val("");

					$('#activate_div').fadeOut(200);

				}

			});
		}else{
			toastr.warning("授权码不能为空！");
		}
	});


	$("#unbindButoon").click(function(){
		//解绑操作
		$.ajax({
			url:"<%=basePathUrl %>/authResult/unbind",
			data: {"unbindHours":$("#unbindHours").val()},
			type: "POST",
			dataType: "json",
			async: false,
			success: function (data) {
				console.log(data);
				if(data.result==true){
					$('#system_unbind_div').fadeOut(200);

					$("#reminderDiv").fadeIn(200);
					toastr.success("解绑成功！");

				}else{

					if(result.msg!=null && checkisNotNull(result.msg)){
						toastr.error("解绑失败,"+result.msg);
						$('#system_unbind_div').fadeOut(200);
					}else{
						toastr.error("解绑失败!");
						$('#system_unbind_div').fadeOut(200);
					}
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				toastr.error("解绑失败！");
				$('#system_unbind_div').fadeOut(200);
			}

		});
	});
	
	console.log("isHaveUnbindPri---"+isHaveUnbindPri)
	if(isHaveUnbindPri=="true"){
		$("#canUnbind").show();
		$("#canNotUnbind").hide();
	}else{
		$("#canUnbind").hide();
		$("#canNotUnbind").show();
	}

	if(authTime!=null &&  authTime !="null" && checkisNotNull(authTime)){
		if(isFail=="true"){
			$("#effected").hide();
			$("#noteffected").show();
			$("#shixiao").show();
			$("#weijihuo").hide();
		}else{
			$("#effected").show();
			$("#noteffected").hide();
		}

	}else{
		$("#effected").hide();
		$("#noteffected").show();
		$("#shixiao").hide();
		$("#weijihuo").show();
	}
</script>
