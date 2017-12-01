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

 <%
 	String addUrl = path + "/graph/add";
    String editUrl = path + "/graph/edit";
    String detailUrl = path + "/graph/detail";
  %>

<script>
	var addUrl = "<%=addUrl%>";
	var editUrl = "<%=editUrl%>";
	var detailUrl = "<%=detailUrl%>";
	
	var graphTypeSource = [{text:"请选择图形类型", value:"-1"},{text:"基本图形", value:"1"},{text:"接口图形", value:"2"}];
	
	function queryParams(params) {
		return {
			limit : params.limit,
			offset : params.offset,
			search : params.search
		};
	}
	//基本搜索项
	function basicQueryItems(params) {
		return [ 
		         {
					type:'list',
					key:'hostUuid',
					items:getHostJSON(),
					width:130
				},
				{
					type:'list',
					key:'graphTemplateId',
					items:getGraphTemplateJSON(),
					width:150
				},
				{
					type:'list',
					key:'graphType',
					items:graphTypeSource,
					width:130
				},
				{
					type : 'input',
					key : 'graphName',
					placeholder : '图形名称'
				}
			   ]
	}
	
	//查找主机
	function getHostJSON(){
		var hostList ={};
		 $.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/host/findHostJson",
				dataType:'json',
				success:function(data){
					hostList =data;
				},
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
				}
			});
		 return hostList;
	}
	
	//查找图形模板
	function getGraphTemplateJSON(){
		var graphTemplateList ={};
		 $.ajax({
				type:'post',
				async:false,
				url:"${pageContext.request.contextPath}/graph/findGraphTemplateJson",
				dataType:'json',
				success:function(data){
					graphTemplateList =data;
				},
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
				}
			});
		 return graphTemplateList;
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
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		<div class="znms_box">
			<p class="page_top_menu">系统管理&gt; <span class="bold">图形管理</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="主机,图形类型,图形名,图形模板名"
			ids="host.hostName,graphType,graphName,graphTemplateName"
			dataUrl="${pageContext.request.contextPath }/graph/search"
			columnShows="1,1,1,1" 
			isSubStrings="0,0,0,0"
			sortable="0,0,0,0"
			uniqueId="graphUuid"
			singleDeleteUrl="${pageContext.request.contextPath }/graph/deleteSingle"
			singleDeletePrivilege="p_graph_delete"
			multiDeleteUrl="${pageContext.request.contextPath }/graph/delete"
			multiDeletePrivilege="p_graph_delete"
			addUrl="initAdd"
			addPrivilege="p_graph_tree_add"
			addTitle="${addAlias}"
			showExport="false" 
			showSingleEditBtn="false"
			showSingleDetailBtn="fasle"
			/>
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
