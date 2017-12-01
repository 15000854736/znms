<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../../common/include.jsp"%>
<style type="text/css">
</style>
</head>

<body>
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>

	<div class="znms_box">
		<p class="page_top_menu">设置&gt; 配置备份</p>

		<ul class="menu_mod_box">
			<li id="1"><a href="${pageContext.request.contextPath }/backupConfiguration">账户&密码</a></li>
			<li id="2"><a href="${pageContext.request.contextPath }/backupConfiguration/device">设备</a></li>
			<li id="3" class="tab_hov"><a href="${pageContext.request.contextPath }/backupRecord">备份</a></li>
			<li id="4"><a href="${pageContext.request.contextPath }/backupRecord/compare">比较</a></li>
		</ul>

		<table class="config_comparison_box">
			<tr>
				<th class="font_18px">${data.hostName} (${data.hostIp})</th>
			</tr>
			<tr>
				<td class="config_comparison_bg">备份时间: <fmt:formatDate value="${data.backupTime}" pattern="yyyy-MM-dd HH:mm:ss" /> 
					&emsp;文件: ${data.backupPath}/${data.fileName}</td>
			</tr>
			<tr>
				<td style="text-align:left;">${data.configContent}</td>
			</tr>
		</table>

	<%@ include file="../../common/footer.jsp"%>
	</div>
</body>
</html>
