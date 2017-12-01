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
function initTable(id, data){
	var table = $('#'+id);
	var tableHtml = doInit(data, 0);
	$(tableHtml).appendTo(table);
}
function doInit(data, nodeFloor){
	var tableHtml = '';
	for(var i=0;i<data.length;i++){
		tableHtml += createRow(nodeFloor, data[i]);
		if(data[i].childList != null) {
			tableHtml += doInit(data[i].childList, nodeFloor+1);
		}
	}
	return tableHtml;
}
function createRow(nodeFloor, data){
	var parentUuid = data.parentUuid;
	var title = data.title;
	var graphTreeItemUuid = data.graphTreeItemUuid;
	var type= data.graphTreeItemType;
	var row = '<tr parent="'+parentUuid+'" nodeFloor="' + nodeFloor + '">';
		row += '<td class="text_left " width="70%">';
			row += '<div style=" text-indent:'+nodeFloor+'em;">';
				row += '<img src="<%=path%>/images/jian.png" width="11" height="11" role="switch" status="open">';
				row += '<input type="hidden" value="' + graphTreeItemUuid + '">';
				row += '<span class="text_left"> ';
					row += '<span role="nodeHostSpan"';
					if(graphTreeItemUuid==''){
						row += ' style="display:none"';
					}
					if(type==1){
						row += '>'+title+'</span>';
					}else{
						row += '>'+data.host.hostName+'('+data.host.hostIp+')'+'</span>';
					}
					
					row += '<input role="titleInput" name="input2"';
					if(graphTreeItemUuid != ''){
						row += 'style="display:none"';
					}
					row += 'type="text" class="wb_mod wid_100" maxlength="255" value='+title+'>';
					row += '<button type="button" class="but_save margin_l10" role="save" ';
					if(graphTreeItemUuid !=''){
						row += 'style="display:none"';
					}
					row += '>保存</button>';
					row += '<button type="button" class="but_save margin_l10" role="cancel" style="display:none">取消</button>';
				row += '</span>';
			row += '</div>';
		row += '</td>';
		row += '<td>';
			row += '<ul class="table_icon">';
				if(graphTreeItemUuid != ''){
					if(type==1){
						row += '<li class="table_icon_a"><a href="#" role="modify"><i></i>修改</a></li>';
					}
				}
				row += '<li class="table_icon_b"><a href="#" role="delete"><i></i>删除</a></li>';
				if(graphTreeItemUuid != ''){
					if(type==1){
						row += '<li><a href="#" role="addSub" id='+graphTreeItemUuid+'><span class="icon-laptop font_14px"></span>新建子节点</a></li>&emsp; ';
					}
					row += '<li><a href="#" role="upperShift">↑上移</a></li>';
					row += '<li><a href="#" role="downShift">↓下移</a></li>';
				}
			row += '</ul>';
		row += '</td>';
	row += '</tr>';
	return row;
}

$(document).ready(function(){
	var graphTreeUuid = $("#graphTreeUuid").val();
		$.ajax({
			url:"<%=path%>/graphTree/loadGraphTreeItem/"+graphTreeUuid,
			type:"POST",
			success:function(data){
				eval('var data='+data);
				initTable('graphTreeItemTable',data);
				bindEvent();
			}
		});
	
	function findLastSubCate(graphTreeItemUuid){
		var aa;
		if($('[parent="'+graphTreeItemUuid+'"]').length==0){
			return graphTreeItemUuid;
		}else{
			//有子类
			graphTreeItemUuid = $("tr[parent='"+graphTreeItemUuid+"']:last").find("td").find("div").find("input").val();
			aa = findLastSubCate(graphTreeItemUuid);
		}
		return aa;
	}
	
	function bindEvent(){
		bindSwitch();
		bindShift();
		$('[role="addSub"]').click(function(){
			$(".errMsg").hide();
			$("#Layer2").show();
			//加载主机列表
			var nodeUuid = $(this).attr("id");
			$("#parentUuid2").val(nodeUuid);
				$.ajax({
					url:"<%=path%>/graphTree/getHost/"+nodeUuid,
					type:"POST",
					dataType:'json',
					success:function(data){
						$("#dummyHost2").jqxDropDownList({
							source : data,
							displayMember : 'text',
							valueMember : 'value',
							selectedIndex : 0,
							placeHolder : "请选择",
							width : 250
						});
					}
				});
		});
		bindDelete();
		bindSave();
		$('[role="modify"]').click(function(){
			$(this).parents('tr:first').find('[role="nodeHostSpan"]').hide();
			$(this).parents('tr:first').find('[role="titleInput"]').show();
			$(this).parents('tr:first').find('[role="save"]').show();
			$(this).parents('tr:first').find('[role="cancel"]').show();
		});
		$('[role="cancel"]').click(function(){
			location.reload( );
		});
	}
	
	function bindShift(){
		$('a[role*="Shift"]').click(function(){
			var direction = $(this).attr('role')=='upperShift';
			var graphTreeItemUuid = $(this).parents('tr:first').find('input[type="hidden"]').val();
			$.ajax({
				url:"<%=path%>/graphTree/shiftGraphTreeItem",
				type:"POST",
				data:{"graphTreeItemUuid":graphTreeItemUuid,"direction":direction},
				success:function(result){
					eval('var result='+result);
					toastr.clear();
					if(!result.result){
						toastr.error(result.msg);
						return;
					}
					location.reload( );
				}
			});
		});
	}
	
	function switchStatus(graphTreeItemUuid, isOpen){
		var childSize = $('tr[parent="'+graphTreeItemUuid+'"]').length;
		for(var i=0;i<childSize;i++){
			var childId = $('tr[parent="'+graphTreeItemUuid+'"]').eq(i).find('input[type="hidden"]').val();
			if(isOpen){
				$('tr[parent="'+graphTreeItemUuid+'"').eq(i).show();
			} else {
				$('tr[parent="'+graphTreeItemUuid+'"').eq(i).hide();
			}
			switchStatus(childId, isOpen);
		}
	}
	
	function bindSwitch(){
		$('[role="switch"]').click(function(){
			if($(this).attr('status')=='open'){
				switchStatus($(this).siblings("input").val(), false);
				$(this).attr('src','<%=path%>/images/jia.png');
				$(this).attr('status','closed');
			} else {
				switchStatus($(this).siblings("input").val(), true);
				$(this).attr('src','<%=path%>/images/jian.png');
				$(this).attr('status','open');
			}
		});
	}
	
	function bindDelete(){
		$('[role="delete"]').click(function(){
        	var obj = $(this);
        	$(".modal-dialog").modal('show');
        	$("#deleteConfirm").off('click').click(function(){
        		$(".modal-dialog").modal("hide");
        		var graphTreeItemUuid = obj.parents("td:first").siblings("td:first").find('input[type="hidden"]').val();
    			if(graphTreeItemUuid==''){
    				obj.parents('tr:first').remove();
    				bindSwitch();
    			} else {
    				$.ajax({
    					url:"<%=path%>/graphTree/deleteGraphTreeItem",
    					type:"POST",
    					data:{"graphTreeItemUuid":graphTreeItemUuid},
    					success:function(result){
    						eval('var result='+result);
    						toastr.clear();
    						if(!result.result){
    							toastr.error(result.msg);
    							return;
    						}
    						location.reload();
    					}
    				});
    			}
        	})
			
		});
	}
	function bindSave(){
		$('[role="save"]').click(function(){
			var title = $(this).siblings('input[role="titleInput"]').val();
			if(title.length==0){
				toastr.clear();
				toastr.error("请输入节点标题");
				return;
			}
			if(title.length > 255){
				toastr.clear();
				toastr.error("节点标题长度不可超过255位");
				return;
			}
			var graphTreeItemUuid = $(this).parents('tr:first').find('input[type="hidden"]').val();
			var parentUuid = $("#graphTreeUuid").val();
			$.ajax({
				url:"<%=path%>/graphTree/updateGraphTreeItem",
				type:"POST",
				async: false,
				data:{"title":title,"graphTreeItemUuid":graphTreeItemUuid,"parentUuid":parentUuid},
				success:function(result){
					eval('var result='+result);
					toastr.clear();
					if(!result.result){
						toastr.error(result.msg);
						return;
					}
					location.reload();
				}
			});
		});
	}
	
	$("#Layer1").hide();
	$("#Layer2").hide();
	
	$("#addNewRoot").click(function(){
		$(".errMsg").hide();
		$("#Layer1").show();
		//隐藏主机
		$("#hostTr").hide();
		
		//加载上层节点数据
		var graphTreeUuid = $("#graphTreeUuid").val();
		$.ajax({
			url:"<%=path%>/graphTree/getNode/"+graphTreeUuid,
			type:"POST",
			dataType:'json',
			success:function(data){
				
				$("#dummyGraphTreeItem").jqxDropDownList({
					source : null,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 250
				});
				
				$("#dummyGraphTreeItem").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 250
				});
			}
		});
		
		//上层节点切换事件
		$(document).on("change","#dummyGraphTreeItem",function(event){
			//上层节点切换事件  节点不为root时只能选择主机
			if(graphTreeUuid!=$("#dummyGraphTreeItem").val()){
				//只能选主机
				$("#graphTreeItemType").empty();
				$("#graphTreeItemType").append("<option value='2'>主机</option>");
				$("#hostTr").show();
				$("#titleTr").hide();
				var nodeUuid = $("#dummyGraphTreeItem").val();
				$.ajax({
					url:"<%=path%>/graphTree/getHost/"+nodeUuid,
					type:"POST",
					dataType:'json',
					success:function(data){
						//情况主机下拉列表重新加载数据
						$("#dummyHost").jqxDropDownList({
							source : null
						})
						$("#dummyHost").jqxDropDownList({
							source : data,
							displayMember : 'text',
							valueMember : 'value',
							selectedIndex : 0,
							placeHolder : "请选择",
							width : 250
						});
					}
				});
			}else{
				//可以选择节点和主机
				$("#graphTreeItemType").empty();
				$("#graphTreeItemType").append("<option value='1'>节点</option><option value='2'>主机</option>");
				$("#hostTr").hide();
				$("#titleTr").show();
			}
		});
		
		//加载主机数据
		$('#graphTreeItemType').change(function(){
			if($("#graphTreeItemType").val()==2){
				//节点对象类型为主机
				$("#hostTr").show();
				$("#titleTr").hide();
				//加载主机下拉列表
				var nodeUuid = $("#dummyGraphTreeItem").val();
				$.ajax({
					url:"<%=path%>/graphTree/getHost/"+nodeUuid,
					type:"POST",
					dataType:'json',
					success:function(data){
						$("#dummyHost").jqxDropDownList({
							source : data,
							displayMember : 'text',
							valueMember : 'value',
							selectedIndex : 0,
							placeHolder : "请选择",
							width : 250
						});
					}
				});
			}else{
				$("#hostTr").hide();
				$("#titleTr").show();
			}
		});
		
	});
	
	if($("#graphTreeUuid").val()=="11111111111111111111111111111111"){
		//修改页面标题无法修改
		if('${page}'=='edit'){
			$("#graphTreeName").attr("readonly","readonly");
		}
	}
	
});

function doMerge(){
	$(".errMsg").hide();
	var commmonValid = validFormOnSubmitPrefab();
	if(commmonValid){
		var graphTreeName = $("#graphTreeName").val();
		var graphTreeUuid = $("#graphTreeUuid").val();
		//校验图形树名称是否重复
		$.ajax({
				url:"<%=path%>/graphTree/checkNameRepeat/",
				type:"POST",
				dataType:'json',
				data:{"graphTreeName":graphTreeName,"graphTreeUuid":graphTreeUuid},
				success:function(data){
					if(!data){
						$("#graphTreeForm").submit();
					}else{
						$("#nameRepeatError").show();
					}
				}
			});
	}
}

function closeWindow(){
	$("#Layer1").hide();
}


function closeWindow2(){
	$("#Layer2").hide();
}

function submitItem(){
	$(".errMsg").hide();
	var commonValid = validFormOnSubmitPrefab();
	var titleValid = true;
	var hostValid = true;
	if(commonValid){
		$("#parentUuid").val($("#dummyGraphTreeItem").val());
		if($("#graphTreeItemType").val()==1){
			titleValid = checkisNotNull($("#title").val());
			if(!titleValid){
				$("#titleError").show();
			}
			$("#hostId").val("");
		}else{
			$("#hostId").val($("#dummyHost").val());
			if($("#hostId").val()=="请选择" || $("#hostId").val() == ""){
				hostValid =false;
				$("#hostRequire").show();
			}
			$("#title").val("");
		}	
		if(titleValid && hostValid){
			var formdata = $("#graphTreeItemForm").serialize();
			$.ajax({
				url:"<%=path%>/graphTree/addGraphTreeItem",
				type:"POST",
				data:formdata,
				dataType:'json',
				success:function(data){
					location.reload();
				}
			});
		}
	}
	
}

//新建子类
function submitItem2(){
	$(".errMsg").hide();
	$("#hostId2").val($("#dummyHost2").val());
	if($("#hostId2").val()!="请选择"){
		var formdata = $("#subGraphTreeItemForm").serialize();
		$.ajax({
			url:"<%=path%>/graphTree/addGraphTreeItem",
			type:"POST",
			data:formdata,
			dataType:'json',
			success:function(data){
				location.reload();
			}
		});
	}else{
		$("#host2Require").show();
		return;
	}
}
</script>

</head>

<body>
				<!-- 修改图形树  新增窗口：start -->
	<div class="layer_box" style="display:table;" id="Layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="Layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加节点</span>
				</h1>
				<form id="graphTreeItemForm">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">上层节点</td>
							<td>
							<div id="dummyGraphTreeItem"></div>
							<input id="parentUuid" name="parentUuid" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">节点对象类型</td>
							<td>
							<select id="graphTreeItemType" name="graphTreeItemType" class="unfilter form-control select select-default mrs mbm" style="width:250px" data-toggle="select">
								<option value="1">节点</option>
								<option value="2">主机</option>
							</select>
							
							</td>
						</tr>
						<tr id="titleTr">
							<td class="layer_table_left bold">标题</td>
							<td>
							<input id="title" name="title" type="text" maxlength="255" class="form-control input-sm" />
							<span role="requiredError" class="errMsg" id="titleError">
								不能为空
							</span>
							</td>
						</tr>
						<tr id="hostTr">
							<td class="layer_table_left bold">主机</td>
							<td>
							<div id="dummyHost"></div>
							<input id="hostId" name="hostId" type="hidden" />
							<span id="hostRequire" class="errMsg">
								无可用主机
							</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" id="submitBtn" onclick="submitItem();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" onclick="closeWindow();" type="button" ><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 修改窗口：end -->
	
	<!-- 修改图形树  新建子类窗口：start -->
	<div class="layer_box" style="display:table;" id="Layer2">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="Layer2.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加子节点</span>
				</h1>
				<form id="subGraphTreeItemForm">
					<input type="hidden" name="parentUuid" id="parentUuid2" >
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">节点对象类型</td>
							<td>
							<select id="graphTreeItemType2" name="graphTreeItemType" class="unfilter form-control select select-default mrs mbm" style="width:250px" data-toggle="select">
								<option value="2">主机</option>
							</select>
							
							</td>
						</tr>
						<tr id="hostTr">
							<td class="layer_table_left bold">主机</td>
							<td>
							<div id="dummyHost2"></div>
							<input id="hostId2" name="hostId" type="hidden" />
							<span id="host2Require" class="errMsg">
								无可用主机
							</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" id="submitBtn2" onclick="submitItem2();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" onclick="closeWindow2();" type="button" ><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 新建子类窗口：end -->

		<!-- 头部菜单 start-->
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		
		<div class="modal-dialog" style="width: 350px;display:none;position: absolute;left: 0;right: 0;" >
			<div class="modal-content"><div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				</button>
		        <h4 class="modal-title" id="myModalLabel">系统提示</h4>
		        </div>
		        <div class="modal-body">是否确定要删除指定节点？</div>
		        <div class="modal-footer">
		        	<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
		        	<button type="button" class="btn btn-primary" id="deleteConfirm">确定</button>
		        </div>
	        </div>
      </div>
		
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.graphTree" bundle="${menuBundle}" var="Text2" />
		    <fmt:message key="menu.item.setting.graphTree.edit" bundle="${menuBundle}" var="Text3" />
			<p class="page_top_menu">${Text1}<span class="bold">&gt; ${Text2}</span><span class="bold">&gt; ${Text3}</span></p>
		<!-- 头部菜单 end-->
			
			<form:form action="${pageContext.request.contextPath }/graphTree/edit" id="graphTreeForm" modelAttribute="graphTree" method="post">
				<form:input path="graphTreeUuid" type="hidden" id="graphTreeUuid"/>
				<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg">
					<tr>
						<td class="table_td_bg">名称</td>
						<td align="left" class="table_td_txtleft" colspan="4">
							<form:input type="text" path="graphTreeName" id="graphTreeName" maxlength="32" required="true" />
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
							<span id="nameRepeatError" class="errMsg">
								名称重复
							</span>
						</td>
					</tr>
				</table>
				
				<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg">
					<tr>
						<td class="table_td_bg">节点</td>
						<td align="left" class="table_td_txtleft" colspan="4">
							<table border="0" cellspacing="0" cellpadding="0" class="crm_table_box" id="graphTreeItemTable">
								<tr>
									<td colspan="2"><button class="but_mod" type="button" id="addNewRoot">新增</button></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<div class="but_mod_box">
			            <button class="but_mod" type="button" onclick="window.location.href='${pageContext.request.contextPath}/graphTree'"><fmt:message key="common.ui.button.return" bundle="${commonBundle}"/></button>
						<c:if test="${!disabled}">
			             	<button class="but_mod" type="button" onclick="doMerge();"><fmt:message key="common.ui.button.save" bundle="${commonBundle}"/></button>
						</c:if>
			     </div>
					
			</form:form>
			
	
		<jsp:include page="${path}/views/common/footer.jsp" />
	</div>
</body>
</html>
