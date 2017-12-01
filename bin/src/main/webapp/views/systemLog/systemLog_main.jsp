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
	var prioritySource = [{text:"所有级别",value:-1},{text:"紧急(emerg)",value:0},{text:"警报(alert)",value:1},{text:"严重(crit)",value:2},
	                      {text:"错误(err)",value:3},{text:"警告(warning)",value:4},{text:"通知(notice)",value:5},
	                      {text:"信息(info)",value:6},{text:"调试(debug)",value:7},{text:"其他(other)",value:8}];
	
	var facilitySource = [{text:"所有类型",value:-1},{text:"kern",value:0},{text:"user",value:1},{text:"mail",value:2},
	                      {text:"daemon",value:3},{text:"auth",value:4},{text:"syslog",value:5},
	                      {text:"lpr",value:6},{text:"news",value:7},{text:"uucp",value:8},
	                      {text:"autnpriv",value:10},{text:"ftp",value:11},{text:"cron",value:15}];
	function queryParams(params) {
		return {
			limit : params.limit,
			offset : params.offset,
			search : params.search
		};
	}
	//基本搜索项
	function basicQueryItems(params) {
		return [ {
					type : 'input',
					key : 'message',
					placeholder : '消息'
					},{
						type:'list',
						key:'priorityId',
						items:prioritySource,
						width:130
					},{
						type:'list',
						key:'facilityId',
						items:facilitySource,
						width:130
					},{
			            type: 'dateRange',
			            keyfrom: 'logTimeFrom',
			            keyto: "logTimeTo",
			            placeholderfrom:  '日志生成时间开始',
			            placeholderto: '日志生成时间结束',
			            placeholder: '日志生成时间',
			            initFromValue:'${defaultLogTimeFrom}',
						initToValue:'${defaultLogTimeTo}',
			    		width:150
				    }
				]
	}

	function initAdd(){
        window.location.href=addUrl;
      }
	
	function showEdit(id){
        window.location.href=editUrl+"/"+id;
      }
	
	function showDetail(id){
		window.location.href=detailUrl+"/"+id;
	}
	
</script>

</head>

<body>
		<!-- 头部菜单 start-->
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		<div class="znms_box">
			<p class="page_top_menu">系统日志&gt; <span class="bold">系统日志列表</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="级别,类型,主机,消息,时间"
			ids="priorityId,facilityId,host.hostName,message,logTime"
			dataUrl="${pageContext.request.contextPath }/systemLog/search"
			columnShows="1,1,1,1,1" 
			isSubStrings="0,0,0,0,0"
			sortable="0,0,0,0,0"
			uniqueId="seq"
			singleDeleteUrl="${pageContext.request.contextPath }/systemLog/deleteSingle"
			singleDeletePrivilege="p_system_log_delete"
			multiDeleteUrl="${pageContext.request.contextPath }/systemLog/delete"
			multiDeletePrivilege="p_system_log_delete"
			showAdd="false"
			showSingleEditBtn="false"
			showSingleDetailBtn="false"
			exportTitle="导出"
			exportUrl="${pageContext.request.contextPath }/systemLog/export"
			exportPrivilege="p_system_log_export"
			/>
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
