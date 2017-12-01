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
 	String addUrl = path + "/graphTree/add";
    String editUrl = path + "/graphTree/edit";
    String detailUrl = path + "/graphTree/detail";
  %>

<script>
	var addUrl = "<%=addUrl%>";
	var editUrl = "<%=editUrl%>";
	var detailUrl = "<%=detailUrl%>";
	
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
			key : 'graphTreeName',
			placeholder : '名称'
		}
	]
	}

	function initAdd(){
		$("#Layer1").css('display','table'); 
      }
	
	function showEdit(id){
        window.location.href=editUrl+"/"+id;
      }
	
	function showDetail(id){
		window.location.href=detailUrl+"/"+id;
	}
	
	function submitForm(){
		$(".errMsg").hide();
		var graphTreeName = $("#graphTreeName").val();
		if(!checkisNotNull(graphTreeName)){
			$("#nameNullError").show();
			return;
		}
			//校验图形树名称是否重复
			$.ajax({
					url:"<%=path%>/graphTree/checkNameRepeat/",
					type:"POST",
					dataType:'json',
					data:{"graphTreeName":graphTreeName,"graphTreeUuid":""},
					success:function(data){
						if(!data){
							$("#addForm").submit();
						}else{
							$("#nameRepeatError").show();
						}
					}
				});
	}
	
	function closeAddTable(){
		$("#Layer1").css('display','none'); 
	}
</script>

</head>

<body>

		<!-- 添加图形树窗口：start -->
		<div class="layer_box" style="display:none;" id="Layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="Layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加图形树</span>
				</h1>
				<form id="addForm" action="${pageContext.request.contextPath }/graphTree/add" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">名称</td>
							<td>
							<input id="graphTreeName" name="graphTreeName" type="text" maxlength="32" class="form-control input-sm" required="true"/>
							<span id="nameNullError" class="errMsg">
								不能为空
							</span>
							<span id="nameRepeatError" class="errMsg">
								名称重复
							</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" id="submitBtn" onclick="submitForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" id="returnBtn" type="button" onclick="closeAddTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加窗口：end -->

		<!-- 头部菜单 start-->
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.graphTree" bundle="${menuBundle}" var="Text2" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span></p>
		<!-- 头部菜单 end-->
	
		<!-- 表主体：start -->
		<s:table id="ZZOS_Common_Table" columns="名称"
			ids="graphTreeName"
			dataUrl="${pageContext.request.contextPath }/graphTree/search"
			columnShows="1" 
			isSubStrings="0"
			sortable="0"
			uniqueId="graphTreeUuid"
			singleDeleteUrl="${pageContext.request.contextPath }/graphTree/deleteSingle"
			singleDeletePrivilege="p_graph_tree_delete"
			singleEditUrl="showEdit"
			singleEditPrivilege="p_graph_tree_edit"
			multiDeleteUrl="${pageContext.request.contextPath }/graphTree/delete"
			multiDeletePrivilege="p_graph_tree_delete"
			addUrl="initAdd"
			addPrivilege="p_graph_tree_add"
			addTitle="${addAlias}"
			showExport="false" 
			showSingleDetailBtn="false"
			/>
			
		<!-- 表主体：end -->
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
