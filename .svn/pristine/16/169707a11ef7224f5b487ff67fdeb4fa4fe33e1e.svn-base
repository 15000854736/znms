<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<style type="text/css">
</style>

<script>
setInterval("refresh()",5000);
function refresh(){
	$.ajax({
		url:"${pageContext.request.contextPath}/hostListbox/refresh",
		type:"post",
		dataType:"json",
		success:function(data){
			$("#mainDiv").html("");
			for(var i=0;i<data.length;i++){
				var bean = data[i];
				var row = "<div class='images_center_box' id='centerDiv'><div class='monitor_box '><h2>"+
				bean.graphTreeName+"</h2>";
				if(bean.hostList.length==0){
					row += "<span style='font-size: 30px'>暂无主机</span></div></div>";
				}else{
					for(var j=0;j<bean.hostList.length;j++){
						var host = bean.hostList[j];
						if(host.hostWorkStatus==1){
							row += "<dl class='net_monitor_mod net_monitor_yes '>";
						}else {
							row += "<dl class='net_monitor_mod net_monitor_no '>";
						}
						row += "<dt><i></i><div class='net_monitor_mod_bg'></div></dt><dd>名称："+host.hostName+"</dd>";

						if(host.hostWorkStatus==1){
							row +="<dd>状态：正常</dd>";
						}else{
							row += "<dd>状态：宕机</dd>";
						}
						row += "<dd>IP地址："+host.hostIp+"</dd>";
						row +="</dl>";
						if(i==bean.hostList.length-1){
							row += "</div></div>";
						}
					}
				}
				$("#mainDiv").append(row);
			}
		}
		
	});
 }
</script>

</head>

<body>
		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<div class="znms_box" id="mainDiv">
		<!-- 头部菜单 end-->
		
		<c:forEach items="${hostListboxBeanList }" var="bean">
			<div class="images_center_box" id="centerDiv">
				<div class="monitor_box  ">
				<h2>${bean.graphTreeName }</h2>
				<c:choose>
					<c:when test="${empty bean.hostList}"><span style="font-size: 30px">暂无主机</span></c:when>
					<c:otherwise>
						<c:forEach items="${bean.hostList }" var="host">
							<c:choose>
								<c:when test="${host.hostWorkStatus ==1}">
									<dl class="net_monitor_mod net_monitor_yes ">
								</c:when>
								<c:otherwise>
									<dl class="net_monitor_mod net_monitor_no ">
								</c:otherwise>
							</c:choose>
							<dt><i></i><div class="net_monitor_mod_bg"></div></dt>
							<dd>名称：${host.hostName }</dd>
							<dd>状态：
								<c:choose>
									<c:when test="${host.hostWorkStatus ==1}">正常</c:when>
									<c:otherwise>宕机</c:otherwise>
								</c:choose>
							</dd>
							<dd>IP地址：${host.hostIp }</dd>
							</dl>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</c:forEach>
		
	</div>
</body>
</html>
