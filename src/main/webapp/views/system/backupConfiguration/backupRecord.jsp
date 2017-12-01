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

<script>
	function queryParams(params) {
		return {
			limit : params.limit,
			offset : params.offset,
			search : params.search
		};
	}
	//基本搜索项
	function basicQueryItems(params) {
		var hostList = '${hostList}';
		eval("var hostList="+hostList);
		return [ {
				type : 'list',
				key : 'hostUuid',
				items : hostList,
				width : '180px'
			}
		]
	}
	
	function showDetail(id){
		location.href="${pageContext.request.contextPath }/backupRecord/"+id;
	}
</script>
</head>

<body>
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		
		<div class="znms_box">
			<p class="page_top_menu">设置&gt; 配置备份</p>
			
			<ul class="menu_mod_box">
			 <li id="1" ><a href="${pageContext.request.contextPath }/backupConfiguration">账户&密码</a></li>
			 <li id="2"><a href="${pageContext.request.contextPath }/backupConfiguration/device">设备</a></li>
			 <li id="3" class="tab_hov"><a href="javascript:void(0)">备份</a></li>
			<li id="4"><a href="${pageContext.request.contextPath }/backupRecord/compare">比较</a></li>
			 </ul>
			
			<!-- 表主体：start -->
			<s:table id="ZZOS_Common_Table" columns="主机名,IP地址,目录,文件名,备份时间"
				ids="hostName,hostIp,backupPath,fileName,backupTime"
				dataUrl="${pageContext.request.contextPath }/backupRecord/query"
				columnShows="1,1,1,1,1" 
				isSubStrings="0,0,0,0,0"
				sortable="0,0,0,0,0"
				uniqueId="id"
				singleDetailUrl="showDetail"
				singleDetailPrivilege="p_backup_configuration_ap_detail"
				showExport="false" 
				showSingleDeleteBtn="false"
				showAdd="false"
				showSingleEditBtn="false"
				/>
			<!-- 表主体：end -->
			
			<%@ include file="../../common/footer.jsp"%>
		</div>
</body>
</html>
