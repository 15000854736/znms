<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<style>
 canvas { box-shadow: 0 0 10px black; margin: 0px; }​
</style>
<script src="<%=path %>/js/system/apInformation.js"></script>
<script type='text/javascript' src="<%=path %>/js/crop.js"></script>
<style type="text/css">
</style>

<script>
var path = "<%=path %>";
var selectedApUuid = "";
var type = "ap";
var hostTypeSource = [{text:'请选择类型',value:-1},{text:'出口',value:1},{text:'核心',value:2},
            		{text:'无线控制器',value:3},{text:'接入',value:4},{text:'汇聚',value:5},{text:'其他',value:6}];
var apPositionSource = [{text:"请选择状态", value:-1}, {text:"已设置", value:1}, {text:"未设置",value:0}];
	$(document).ready(function(){
		if('${type}' !=""){
			type='${type}'
			
		}
		if(type=="ap"){
			type = "ap";
			$("#apTab").attr("class","tab_hov");
			$("#hostTab").removeAttr("class");
			$("#hostRelate1").hide();
			$("#apRelateTr1").show();
			$("#hostTypeTr").hide();
		}else{
			$("#hostTab").attr("class","tab_hov");
			$("#apTab").removeAttr("class");
			$("#hostRelate1").show();
			$("#apRelateTr1").hide();
			$("#hostTypeTr").show();
		}
		apMapPath = '${apMapPath}';
		$("#dummyApPosition").jqxDropDownList({
			source : apPositionSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 100
		});
		
		$("#dummyHostType").jqxDropDownList({
			source : hostTypeSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 100
		});
		
		//查找ac
		searchAc();
		
		//查找ap
		searchAp();
		
		//查找区域
		searchApRegion();
		
	});
	
	//查找ac
	function searchAc(){
		$.ajax({
			type:'post',
			async:false,
			url:"${pageContext.request.contextPath}/host/findAcJson",
			dataType:'json',
			success:function(data){
				$("#dummyAc").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 100
				});
			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
	
	//查找ap
	function searchAp(){
		var acIp = $("#dummyAc").val();
		var apName = $("#apName").val();
		var apPositionStatus = $("#dummyApPosition").val();
		var hostName = $("#hostName").val();
		var hostIp = $("#hostIp").val();
		var hostType = $("#dummyHostType").val();
		var apRegionUuid = $("#dummyApRegion").val();
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/apInformation/findApByCondition",
			data:{"acIp":acIp, "apName":apName, "apPositionStatus":apPositionStatus, 
				"hostName":hostName, "hostIp":hostIp, "hostType":hostType,
				"apRegionUuid":apRegionUuid,"deviceType":type},
			dataType:'json',
			success:function(data){
				$("#apTbody").html("");
				$("#apTbody").append("<tr><td><label class='checkbox'>"+
						"<input type='checkbox' data-toggel='checkbox' class='custom-checkbox'>"+
						"<span class='icons'><span class='icon-unchecked'></span>"+
						"<span class='icon-checked'></span></span><label>"+
						"<td>全选</td></tr>");
				if(type=="ap"){
					for(var i=0;i<data.length;i++){
						var _apName = data[i].apName;
						if(_apName != null && _apName.length > 25) {
							_apName = _apName.substring(0,24);
						}
						$("#apTbody").append("<tr><td><label class='checkbox'>"+
								"<input type='checkbox' data-toggel='checkbox' class='custom-checkbox'>"+
								"<span class='icons'><span class='icon-unchecked'></span>"+
								"<span class='icon-checked'></span></span><label>"+
								"<td>"+_apName+"<input type='hidden' value='"+data[i].apInformationUuid+","+data[i].apMac+"'></td></tr>");
					}
					$(".custom-checkbox:gt(0)").click(function(){
						clickType = "2";
						if($(this).is(":checked")){
							//回显该ap在地图中的位置
							var obj = $(this).parent().parent().next().find("input").val();
							displayApPosition(obj);
						}else{
							$(".custom-checkbox:eq(0)").prop("checked", false);
						}
					});
					$(".custom-checkbox:eq(0)").click(function() {
						clickType = "2";
						if($(this).is(":checked")){
							$(".custom-checkbox:gt(0)").prop("checked", true);
						} else {
							$(".custom-checkbox:gt(0)").prop("checked", false);
						}
					});
				}else{
					for(var i=0;i<data.length;i++){
						var hostName = data[i].hostName;
						if(hostName != null && hostName.length > 25) {
							hostName = hostName.substring(0,24);
						}
						$("#apTbody").append("<tr><td><label class='checkbox'>"+
								"<input type='checkbox' data-toggel='checkbox' class='custom-checkbox'>"+
								"<span class='icons'><span class='icon-unchecked'></span>"+
								"<span class='icon-checked'></span></span><label>"+
								"<td>"+hostName+"<input type='hidden' value='"+data[i].id+","+data[i].id+"'></td></tr>");
					}
					$(".custom-checkbox:gt(0)").click(function(){
						clickType = "2";
						if($(this).is(":checked")){
							//回显设备在地图中的位置
							var obj = $(this).parent().parent().next().find("input").val();
							displayHostPosition(obj);
						}else{
							$(".custom-checkbox:eq(0)").prop("checked", false);
						}
					});
					$(".custom-checkbox:eq(0)").click(function() {
						clickType = "2";
						if($(this).is(":checked")){
							$(".custom-checkbox:gt(0)").prop("checked", true);
						} else {
							$(".custom-checkbox:gt(0)").prop("checked", false);
						}
					});
				}
				
			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
	
	//查找ap区域
	function searchApRegion(){
		$.ajax({
			type:'post',
			async:false,
			url:"${pageContext.request.contextPath}/apInformation/findApRegion",
			dataType:'json',
			success:function(data){
				$("#dummyApRegion").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					filterable: true,
					filterPlaceHolder: "请输入关键字",
					width : 100
				});
				
				$("#dummyApRegion").change(function(){
					var apRegionUuid = $("#dummyApRegion").val();
					$("div[name='pointerDiv']").each(function(){
						$(this).remove();
					});
					refreshMap(type);
					showRegion(apRegionUuid);
					
				});

			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
	
	function showRegion(apRegionUuid){
		if(apRegionUuid != "-1"){
			//显示区域范围
			$.ajax({
				url:path+"/apInformation/drawApRegion",
				type:"POST",
				dataType:'json',
				data:{"apRegionUuid":apRegionUuid},
				success:function(data){
					var apRegionCoordinate = data.apRegionCoordinate;
					var array = apRegionCoordinate.split(",");
					var lastzuobiao = "";
					ctx.beginPath();
					for(var index in array){
						var zuobiao = array[index];
						if(zuobiao != ""){
							var zuobiaoArray = zuobiao.split("-");
							if(index==0){
								ctx.moveTo(parseFloat(zuobiaoArray[0])/percent, parseFloat(zuobiaoArray[1])/percent);
							}
							var offset = $("#myCanvas").offset();
							 var pointer = $('<div name="pointerDiv" id="'+zuobiaoArray[0]+zuobiaoArray[1]+'"><span class="spot pointercss"></div>').css({
	                             'position': 'absolute',
	                             'background-color': '#00F',
	                             'width': '5px',
	                             'height': '5px',
	                             'top': parseFloat(zuobiaoArray[1])/percent+offset.top+"px",
	                             'left': parseFloat(zuobiaoArray[0])/percent+offset.left+"px"
	                         });
							$(document.body).append(pointer);
							if(index>0){
		                        ctx.lineTo(parseFloat(zuobiaoArray[0])/percent, parseFloat(zuobiaoArray[1])/percent);
							}
						}
					}
					ctx.fillStyle="#0066FF";
                    ctx.fill();
					ctx.strokeStyle = '#0066FF'; 
                    ctx.stroke();
				}
			});
		}
	}
	//查找ap位置坐标
	function displayApPosition(index){
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/apInformation/findApAxis",
			dataType:'json',
			data:{"index":index},
			success:function(data){
				$(".points_img").each(function(){
					$(this).remove();
				});
				var offset = $("#myCanvas").offset();
				if(data.apAxis!=""){
					var arr=new Array();
					arr = data.apAxis.split(',');
					xAxis = arr[0];
					yAxis = arr[1];
					$('<span class="points_img">').css({
				          left: parseInt(arr[0])/percent+parseInt(offset.left),
				          top: parseInt(arr[1])/percent+parseInt(offset.top)
				        }).appendTo($("#imageDiv"));
				}
			}
		});
	}
	
	//查找主机位置坐标
	function displayHostPosition(index){
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/host/findByUuid",
			dataType:'json',
			data:{"index":index},
			success:function(data){
				$(".points_img").each(function(){
					$(this).remove();
				});
				var offset = $("#myCanvas").offset();
				if(data.hostAxis!=""){
					var arr=new Array();
					arr = data.hostAxis.split(',');
					xAxis = arr[0];
					yAxis = arr[1];
					$('<span class="points_img">').css({
				          left: parseInt(arr[0])/percent+parseInt(offset.left),
				          top: parseInt(arr[1])/percent+parseInt(offset.top)
				        }).appendTo($("#imageDiv"));
				}
			}
		});
	}
	
	
</script>


</head>

<body>
		
		<!-- 添加区域窗口：start -->
		<div class="layer_box" style="display:none;" id="layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close" id="createRegionDiv" onclick="layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">新建区域</span>
				</h1>
				<form id="addRegionForm" action="${pageContext.request.contextPath }/apInformation/addRegion" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10">
						<tr>
							<td class="layer_table_left bold">名称</td>
							<td>
							<input id="apRegionName" name="apRegionName" type="text" maxlength="32" class="form-control input-sm" required="true"/>
							<span id="nameNullError" class="errMsg">
								不能为空
							</span>
							<span id="nameRepeatError" class="errMsg">
								名称重复
							</span>
							<input type='hidden' name="apRegionCoordinate" id="apRegionCoordinate">
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" id="submitBtn" onclick="drawApRegion();" type="button">规划区域</button>
				<button class="layer_input_btn" id="returnBtn" type="button" onclick="closeAddRegionTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加区域窗口：end -->
	
	
	<!-- 区域管理窗口：start -->
		<div class="layer_box" style="display:none" id="layer2">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close" onclick="closeRegionManage();">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">区域管理</span>
				</h1>
				
				<div class="text-right margin_10">
					 <div class="input-group input_search">
						  <input type="text" class="form-control" placeholder="区域名" id="regionManageName" name="regionManageName"/>
						  <span class="input-group-btn">
						    <button type="submit" class="btn" onclick="findRegion(1);"><span class="fui-search"></span></button>
						  </span>
					  </div> 
				</div>
				
				
				<div class="nms_table_bg" id="regionDiv">
					
				</div>
	 		
            </div>
            </div>
		</div>
	</div>
	<!-- 区域管理窗口：end -->

	<!-- 头部菜单 start-->
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/leftMenu.jsp"%>
	<div class="znms_box">
		<!-- 头部菜单 end-->

		<!-- 表主体：start -->
		<div class="nms_box_center">
			<table border="0" cellspacing="0" cellpadding="0" class="nms_ap_box">
				<tr>
					<td class=" text-center">	
						<div id="imageDiv">
							<canvas id="myCanvas" ></canvas>
						</div>
						<div id="oldposx" style="display:none;"></div>
						<div id="oldposy" style="display:none;"></div>
						<div id="posx" style="display:none;"></div>
						<div id="posy" style="display:none;"></div>
					</td>
					<td align="center" valign="top" class="nms_ap_width ">
						<table width="0" border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod  nms_ap_notable">
							<ul class="menu_mod_box" id="LiTypeUl">
							 <li id="apTab"  class="tab_hov"><a href="?type=ap" style="width:127.5px" onclick="changeTab('ap');">AP</a></li>
							 <li id="hostTab"><a href="?type=host" style="width:130px" onclick="changeTab('host');">其他设备</a></li>
							 </ul>
							<tr id="apRelateTr1">
								<td>
									<div id="dummyAc"></div>
								</td>
								
								<td><input type="text" name="apName" id="apName" class="form-control input-sm" style="width:100px" placeholder="AP名称"/></td>
								
							</tr>
							
							<tr style="display:none" id="hostRelate1">
								<td>
									<input type="text" name="hostIp" id="hostIp" class="form-control input-sm" style="width:100px" placeholder="主机IP"/>
								</td>
								
								<td><input type="text" name="hostName" id="hostName" class="form-control input-sm" style="width:100px" placeholder="主机名称"/></td>
								
							</tr>
							
							<tr>
								<td>
									<div id="dummyApPosition"></div>
								</td>
								<td>
									<div id="dummyApRegion"></div>
								</td>
							</tr>
							<tr id="hostTypeTr" style="display:none">
								<td>
									<div id="dummyHostType"></div>
								</td>
							</tr>
							<tr>
								<td id="searchButtonTd">
									<button type="button" class="btn btn-primary btn-xs" style="width:100px" onclick="searchAp();">搜索</button>
								</td>
								<td>
									<button type="button" class="btn btn-primary btn-xs" style="width:100px" onclick="relateAp();">关联</button>
								</td>
							</tr>

							<tr id="apListTr">
								<td colspan="2" id="apListTd">
									<div class="nms_ap_auto" id="apListDiv">
										<table class="nms_mod_list">
											<tbody id="apTbody">

											</tbody>
										</table>
									</div>

								</td>
							</tr>
							
							<tr>
								<td id="createRegionTd">
									<button type="button" class="btn btn-primary btn-xs" style="width:100px" onclick="createRegion();">新建区域</button>
								</td>
								
								<td>
									<button type="button" class="btn btn-primary btn-xs" style="width:100px" onclick="regionManage();">区域管理</button>
								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>
