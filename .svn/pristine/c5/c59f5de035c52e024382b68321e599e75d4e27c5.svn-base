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
	var statusSource = [{text:"启用",value:1},{text:"禁用",value:0}];
	var graphSouce = [{text:"请选择图形",value:-1}];
	var flowDirectionSource = [{text:"下行流量",value:1},{text:"上行流量",value:2}];
	
	$(document).ready(function(){
		//阀值状态
		$("#dummyStatus").jqxDropDownList({
			source : statusSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 250
		});
		
		//查找主机列表
		$.ajax({
			url:"${pageContext.request.contextPath}/host/findHostJson",
			dataType:'json',
			type:"POST",
			async:false,
			success:function(data){
				$("#dummyHost").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					filterable: true,
					filterPlaceHolder: "请输入关键字",
					width : 250
				});
				
				$("#dummyHost").change(function(){
					if($("dummyHost").val() != "-1"){
						findGraphByHost();
					}
				});
			}
		});
		
		//查找图形列表
		$("#dummyGraph").jqxDropDownList({
			source : graphSouce,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			filterable: true,
			filterPlaceHolder: "请输入关键字",
			width : 250
		});
		
		//流向
		$("#dummyFlowDirection").jqxDropDownList({
			source : flowDirectionSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 250
		});
		
		$("#flowDirectionTd").hide();
		$("#flowDirectionTd2").hide();
		
		$("#dummyGraph").change(function(){
			if($("#dummyGraph").val() != "-1"){
				var graphUuid = $("#dummyGraph").val();
				//判断图形时基本图形还是接口图形
				$.ajax({
						url:"${pageContext.request.contextPath}/graph/checkGraphType",
						data:{"graphUuid":graphUuid},
						dataType:"json",
						type: "POST",
						async:false,
						success:function(data){
							if(data){
								//基本图形
								$("#flowDirectionTd").hide();
								$("#flowDirectionTd2").hide();
							}else{
								$("#flowDirectionTd").show();
								$("#flowDirectionTd2").show();
							}
						}
					});
			}
		});
		
		if('${page}' == "edit" || '${page}' == "detail"){
			//回显阀值状态
			$("#dummyStatus").val($("#status").val());
			//回显主机
			$("#dummyHost").val($("#hostUuid").val());
			//回显图形
			$("#dummyGraph").val($("#graphUuid").val());
			//回显流向
			$("#dummyFlowDirection").val($("#flowDirection").val());
		}
		
		if('${page}' == "edit"){
			//主机、图形、流入无法修改
			$("#dummyHost").jqxDropDownList({ disabled: true });
			$("#dummyGraph").jqxDropDownList({ disabled: true });
			$("#dummyFlowDirection").jqxDropDownList({ disabled: true });
		}else if('${page}' == "detail"){
			$("#submitButton").hide();
		}
	});
	
	//根据主机查找图形
	function findGraphByHost(){
		var hostUuid = $("#dummyHost").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/graph/findGraphByHost",
			data:{"hostUuid":hostUuid},
			dataType:"json",
			type: "POST",
			async:false,
			success:function(data){
				$("#dummyGraph").jqxDropDownList({
					source : null,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 250
				});
				
				$("#dummyGraph").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					filterable: true,
					filterPlaceHolder: "请输入关键字",
					width : 250
				});
			}
		})
	}
	
	function mergeDo(){
		$(".errMsg").hide();
		var commonValid = validFormOnSubmitPrefab();
		if(commonValid){
			//主机不能为空
			var hostUuid = $("#dummyHost").val();
			if(hostUuid == "-1"){
				$("#hostError").show();
				return;
			}
			
			//图形不能为空
			var graphUuid = $("#dummyGraph").val();
			if(graphUuid == "-1"){
				$("#graphError").show();
				return;
			}
			
			//阀值必须为正数
			var warningHighThresholdValue = $("#warningHighThresholdValue").val();
			if(!checkIsDouble(warningHighThresholdValue)){
				$("#warningHighError").show();
				return;
			}
			var dot = warningHighThresholdValue.indexOf(".");
            if(dot != -1){
                var dotCnt = warningHighThresholdValue.substring(dot+1,warningHighThresholdValue.length);
                if(dotCnt.length > 2){
                	$("#warningHighError2").show();
                	return ;
                }
            }
			
			var warningLowThresholdValue = $("#warningLowThresholdValue").val();
			if(!checkIsDouble(warningLowThresholdValue)){
				$("#warningLowError").show();
				return;
			}
			
			var dot2 = warningLowThresholdValue.indexOf(".");
            if(dot2 != -1){
                var dotCnt = warningLowThresholdValue.substring(dot2+1,warningLowThresholdValue.length);
                if(dotCnt.length > 2){
                	$("#warningLowError2").show();
                	return ;
                }
            }
			
			if(warningHighThresholdValue < warningLowThresholdValue){
				$("#warningHighLowError").show();
				return;
			}
			
			$("#status").val($("#dummyStatus").val());
			$("#hostUuid").val($("#dummyHost").val());
			$("#graphUuid").val($("#dummyGraph").val());
			
			if($("#flowDirectionTd2").is(":hidden")){
				$("#flowDirection").val(0);
			}else{
				$("#flowDirection").val($("#dummyFlowDirection").val());
			}
			
			var formdata = $("#thresholdValueForm").serialize();
			$.ajax({
				type:'post',
				url:"${pageContext.request.contextPath}/thresholdValue/mergeDo",
				data:formdata,
				dataType:'json',
				success:function(data){
					if(data.result){
						window.location.href = path+"/thresholdValue";
					}else{
						toastr.clear();
						toastr.error(data.msg);
					}
				}
			});
		}
	}
	
</script>

</head>

<body>

		<!-- 头部菜单 start-->
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		<div class="znms_box">
			<p class="page_top_menu">系统管理&gt; <span class="bold">阈值管理&gt;</span> <span class="bold">添加阈值</span></p>
		<!-- 头部菜单 end-->
		<form:form id="thresholdValueForm" action="${pageContext.request.contextPath }/thresholdValue/mergeDo" method="post" modelAttribute="thresholdValue">
			<form:input path="thresholdValueUuid" type="hidden"/>
			<table border="0" cellspacing="0" cellpadding="0"  class="school_table_box no_bg">
				<tr>
					<th colspan="6">基本设置</th>
				</tr>
		        <tr>
		          <td class="nms_data_right">
		          	<span class=" bold ">阈值名称</span>
		  		  </td>
		          <td>
		          	<form:input type="text" path="thresholdValueName" id="thresholdValueName" maxlength="100" required="true"  />
		          	<span role='requiredError' class="errMsg">不能为空</span>
		          </td>
				  <td class="nms_data_right">
				  	<span class=" bold">阈值状态</span>
				  </td>          
				  <td>
					<div id="dummyStatus"></div>  
					<form:input type="hidden" path="status" id="status" />
				  </td>
		          </tr>
		        <tr>
			        <td class="nms_data_right">
					  	<span class=" bold">主机</span>
					  </td>          
					  <td>
						<div id="dummyHost"></div>  
						<form:input type="hidden" path="hostUuid" id="hostUuid" />
						<span id="hostError" class="errMsg">不能为空</span>
					  </td>
					  <td class="nms_data_right">
					  	<span class=" bold">图形</span>
					  </td>          
					  <td>
						<div id="dummyGraph"></div>  
						<form:input type="hidden" path="graphUuid" id="graphUuid" />
						<span id="graphError" class="errMsg">不能为空</span>
					  </td>
		        </tr>
		        <tr>
					  <td class="nms_data_right" id="flowDirectionTd">
					  	<span class=" bold">流向</span>
					  </td>          
					  <td id="flowDirectionTd2">
						<div id="dummyFlowDirection"></div>  
						<form:input type="hidden" path="flowDirection" id="flowDirection" />
					  </td>
		        </tr>
		        
		        <tr>
					<th colspan="6">触发警告条件</th>
				</tr>
				<tr>
			        <td class="nms_data_right">
					  	<span class=" bold">高阈值</span>
					  </td>          
					  <td>
						<form:input type="text" path="warningHighThresholdValue" id="warningHighThresholdValue" required="true"/>
						<span role='requiredError' class="errMsg">不能为空</span>
						<span id='warningHighError' class="errMsg">必须为正数</span>
						<span id='warningHighLowError' class="errMsg">高阈值必须大于低阈值</span>
						<span id="warningHighError2" class="errMsg">最多只能有2位小数</span>
					  </td>
					  <td class="nms_data_right">
					  	<span class=" bold">低阈值</span>
					  </td>          
					  <td>
						<form:input type="text" path="warningLowThresholdValue" id="warningLowThresholdValue" required="true"/>
						<span role='requiredError' class="errMsg">不能为空</span>
						<span id='warningLowError' class="errMsg">必须为正数</span>
						<span id="warningLowError2" class="errMsg">最多只能有2位小数</span>
					  </td>
		        </tr>
		        
		  </table>
		  
		  <div class="mod_buttom_box">
			  <button type="button" class="btn btn-primary btn-xs" onclick="mergeDo();" id="submitButton">确定</button>
		  	<button type="button" class="btn btn-default btn-sm" onclick="history.back(-1)">返回</button>
		  </div>
		</form:form>
			
			
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
