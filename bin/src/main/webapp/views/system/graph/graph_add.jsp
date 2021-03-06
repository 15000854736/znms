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
	var path = "<%=path %>";
	var graphTypeSource = [{text:"基本图形", value:"1"},{text:"接口图形", value:"2"}];

	$(document).ready(function(){
		//查找主机列表
		$.ajax({
			url:"${pageContext.request.contextPath}/host/findHostJson",
			dataType:'json',
			type:"POST",
			success:function(data){
				$("#dummyHost").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 250
				});
			}
		});
		
		
		$("#dummyGraphType").jqxDropDownList({
			source : graphTypeSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 250
		});
		
		//查找图形模板列表
		$.ajax({
			url:"${pageContext.request.contextPath}/graph/findGraphTemplateJson",
			dataType:'json',
			type:"POST",
			success:function(data){
				$("#dummyGraphTemplate").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 250
				});
			}
		});
		
		//图形类型切换事件
		$("#dummyGraphType").change(function(){
			var host = $("#dummyHost").jqxDropDownList('getItemByValue', $("#dummyHost").val());
			if($("#dummyGraphType").val()==1){
				$("#graphTemplateTd").show();
				$("#graphTemplateTd2").show();
				$("#interfaceDiv").hide();
				if($("#dummyHost").val() != "-1" && $("#dummyGraphTemplate").val() != "-1"){
					//自动生成图形名称
					var template = $("#dummyGraphTemplate").jqxDropDownList('getItemByValue', $("#dummyGraphTemplate").val());
					$("#graphName").val(host.label+"-"+template.label);
				}
			}else{
				$("#graphTemplateTd").hide();
				$("#graphTemplateTd2").hide();
				$("#interfaceDiv").show();
				if($("#dummyHost").val() != "-1"){
					findInterfaceList(1);
					//自动生成图形名称
					$("#graphName").val(host.label+"-接口流量-{索引}");
				}
			}
		});
		
		//主机切换事件
		$("#dummyHost").change(function(){
				var host = $("#dummyHost").jqxDropDownList('getItemByValue', $("#dummyHost").val());
				if($("#dummyGraphType").val() == "2"){
					$("#interfaceDiv").show();
					findInterfaceList(1);
					//自动生成图形名称
					$("#graphName").val(host.label+"-流量-{索引}");
				}else{
					if($("#dummyGraphTemplate").val() != "-1"){
						//自动生成图形名称
						var template = $("#dummyGraphTemplate").jqxDropDownList('getItemByValue', $("#dummyGraphTemplate").val());
						$("#graphName").val(host.label+"-"+template.label);
					}
				}
			
		});
		
		//图形模板切换事件
		$("#dummyGraphTemplate").change(function(){
			if($("#dummyHost").val() != "-1"){
				//自动生成图形名称
				var host = $("#dummyHost").jqxDropDownList('getItemByValue', $("#dummyHost").val());
				var template = $("#dummyGraphTemplate").jqxDropDownList('getItemByValue', $("#dummyGraphTemplate").val());
				$("#graphName").val(host.label+"-"+template.label);
			}
			
		});
		
		
	});
	
	function findInterfaceList(current){
		var hostUuid = $("#dummyHost").val();
		$.ajax({
			type:'post',
			async:false,
			url:"${pageContext.request.contextPath}/graph/findInterfaceList?timestamp="+new Date().getTime(),
			data:{"hostUuid":hostUuid,"current":current},
			dataType:'html',
			success:function(html){
				$("#interfaceDiv").html("");
				$("#interfaceDiv").html(html);
			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
	
	//保存图形
	function addGraph(){
		$(".errMsg").hide();
		if(!checkisNotNull($("#graphName").val())){
			$("#graphNameRequiredError").show();
			return;
		}
		var hostUuid = $("#dummyHost").val();
		//主机不能为空
		if(hostUuid == "-1"){
			$("#hostError").show();
			return;
		}else{
			$("#hostUuid").val(hostUuid);
		}
		var graphType = $("#dummyGraphType").val();
		if(graphType == "1"){
			//图形模板不能为空
			if($("#dummyGraphTemplate").val()=="-1"){
				$("#graphTemplateError").show();
				return;
			}else{
				$("#graphTemplateId").val($("#dummyGraphTemplate").val());
			}
			
		}else{
			//接口图形
			$("#graphTemplateId").val("");
			//至少选择一个接口
			var haveSelect = false;
			$("input[name='indexs']").each(function(){
				if($(this).is(':checked')){
					haveSelect = true;
				}
			});
			if(!haveSelect){
				toastr.clear();
		    	toastr.error("请至少选择一个接口!");
				return;
			}
		}
		$("#graphType").val($("#dummyGraphType").val());
		
		var formdata = $("#graphForm").serialize();
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/graph/addGraph",
			data:formdata,
			dataType:'json',
			success:function(data){
				if(data.result){
					window.location.href = path+"/graph";
				}else{
					toastr.clear();
					toastr.error(data.msg);
				}
			}
		});
	}
	
	/**
	 * 选择设备接口
	 */
	function selectInterfaceByPageNumber(current,totalpage){
	   if(current>totalpage){
	   	return;
	   }
	   if(current<1){
	   	return;
	   }
	  //查询
	  findInterfaceList(current);
	}
	
	//当前页全选
	function checkAll(){
		if($("#allCheck").is(':checked')){
			$("input[name='indexs']").each(function(){
				$(this).prop("checked",true); 
			});
		}else{
			$("input[name='indexs']").each(function(){
				$(this).prop("checked",false); 
			});
		}
		
	}
	
	//判断是否全部选中
	function checkIsAllSelect(){
		var allSelect = true;
		$("input[name='indexs']").each(function(){
			if(!$(this).is(':checked')){
				allSelect = false
			}
		});
		if(allSelect){
			$("#allCheck").prop("checked",true); 
		}else{
			$("#allCheck").prop("checked",false); 
		}
	}
	
</script>

</head>

<body>

		<!-- 头部菜单 start-->
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		<div class="znms_box">
			<p class="page_top_menu">系统管理&gt; <span class="bold">图形管理&gt;</span> <span class="bold">添加图形</span></p>
		<!-- 头部菜单 end-->
		<form id="graphForm" action="${pageContext.request.contextPath }/graph/addGraph" method="post">
			<table border="0" cellspacing="0" cellpadding="0"  class="nms_data_tablemod">
		        <tr>
		          
				  <td class="nms_data_right">
				  	<span class=" bold">主机</span>
				  </td>          
				  <td>
					<div id="dummyHost"></div>  
					<input type="hidden" name="hostUuid" id="hostUuid">
					<span id="hostError" class="errMsg">不能为空</span>
				  </td>
				   <td class="nms_data_right">
					  	<span class=" bold">图形类型</span>
					  </td>          
					  <td>
						<div id="dummyGraphType"></div>  
						<input type="hidden" name="graphType" id="graphType">
					  </td>
		          </tr>
		        <tr>
					  <td class="nms_data_right" id="graphTemplateTd">
					  	<span class=" bold">图形模板</span>
					  </td>          
					  <td id="graphTemplateTd2">
						<div id="dummyGraphTemplate"></div>  
						<input type="hidden" name="graphTemplateId" id="graphTemplateId">
						<span class="errMsg" id="graphTemplateError">不能为空</span>
					  </td>
					   <td class="nms_data_right">
			          	<span class=" bold ">图形名称</span>
			  		  </td>
			          <td>
			          	<input type="text" class="form-control input-sm" name="graphName" id="graphName" maxlength="100" readonly="readonly" />
			          	<span class="errMsg" id="graphNameRequiredError">不能为空</span>
			          </td>
		        </tr>
		  </table>
		  
		  
		  <div class="nms_table_bg" id="interfaceDiv">
						 
		  </div>
		  
		  <div class="mod_buttom_box">
		  	<button type="button" class="btn btn-default btn-sm" onclick="history.back(-1)">返回</button>
		  	<button type="button" class="btn btn-primary btn-xs" onclick="addGraph();">保存</button>
		  </div>
		</form>
			
			
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
