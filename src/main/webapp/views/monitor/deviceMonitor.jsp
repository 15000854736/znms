<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<script src="<%=path %>/js/vendor/flat-ui.min.js"></script>
<script src="<%=path %>/js/vendor/video.js"></script>
<script src="<%=path %>/js/prettify.js"></script>
<script src="<%=path %>/js/application.js"></script>
	<script type="text/javascript" src="<%=path %>/js/echart/echarts.js"></script>
<link href="<%=path%>/css/znms_style_new.css" rel="stylesheet" type="text/css">
<style type="text/css">
.input-sm{width:150px;}
</style>
<script type="text/javascript">
Date.prototype.format = function(format)
{
 var o = {
 "y+" : this.getYear(),
 "M+" : this.getMonth()+1, //month
 "d+" : this.getDate(),    //day
 "H+" : this.getHours(),   //hour
 "m+" : this.getMinutes(), //minute
 "s+" : this.getSeconds(), //second
 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 "S" : this.getMilliseconds() //millisecond
 }
 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
 for(var k in o)if(new RegExp("("+ k +")").test(format))
 format = format.replace(RegExp.$1,
 RegExp.$1.length==1 ? o[k] :
 ("00"+ o[k]).substr((""+ o[k]).length));
 return format;
}

	var latestChanged = "";
	var latestChangedPicker = "";

	$(document).ready(function(){
		$('#host_group, time_unit, host_sub_list, host_list, refresh_option').select2({dropdownCssClass: 'select-inverse-dropdown'});

		$('#time_unit').on('change', function(e){
			refreshDateRange('time_unit');	
		});
		
		$("#refresh_option").on('change', function(e){
			var option = $(this).val();
			var refresher = null;
			if(option == "2"){
				$("#refresh_btn").trigger("click");
				refresher = setInterval(function(){
									    $("#refresh_btn").trigger("click");
									},60000);
			} else {
				if(refresher != null){
					clearInterval(refresher);
				}
				refresher = null;
			}
		});
		
		if($("#host_group").length>0){
			$("#host_group").on("change", function(e){
				refreshCascadeList("host_group", "host_sub_list", "host_list", true);
			});
		}
		$("#host_sub_list").on("change", function(e){
			refreshCascadeList("host_sub_list", "host_list", null, true);
		});
		
		var refresher = null;
        $("#query_btn").click(function(){
        	if(!calculateDateRange()){
    			return;
    		}
        	var hostUuid = $("#host_list").val();
        	var hostNodeId = $("#host_sub_list").val();
        	if(hostUuid=="" && (hostNodeId=="" || (hostNodeId != "" && $("[uuid='"+hostNodeId+"']").attr("type")=="node"))){
        		toastr.clear();
        		toastr.error("请选择主机！");
        		return;
        	}
        	
        	query(false);
        	if(refresher != null){
    			clearInterval(refresher);
    			refresher = null;
    		}
        	if($("#refresh_option").val()=='2'){
        		var cycle = 60000;
        		refresher = setInterval(function(){
        			var fromTime = new Date($("#fromTime").val().replace(/-/g, "/"));
        			var toTime = new Date($("#toTime").val().replace(/-/g, "/"));
        			var newFromTime = new Date(fromTime.getTime()+cycle);
        			var newToTime = null;
        			if(toTime > new Date().getTime()){
        				newToTime = toTime;
        			} else {
	        			newToTime = new Date();
        			}
//         			$("#fromTime").val(newFromTime.format("yyyy-MM-dd HH:mm:ss"));
        			$("#toTime").val(newToTime.format("yyyy-MM-dd HH:mm:ss"));
				    query(true);
				}, cycle);
        	}
        });
        
        var firstNode = "${firstNode}";
        var secondNode = "${secondNode}";
        var thirdNode = "${thirdNode}";
        
        if(firstNode != ''){
        	$("#host_group").select2("val", firstNode);
        	refreshCascadeList("host_group", "host_sub_list", "host_list", false);
        }
        
        if(secondNode != ''){
        	$("#host_sub_list").select2("val", secondNode);
        	refreshCascadeList("host_sub_list", "host_list", null, false);
        }
        if(thirdNode != ''){
        	$("#host_list").select2("val", thirdNode);
        }
        $("#query_btn").trigger("click");
	});
	
	function query(isRefresh){
		if(!isRefresh){
			$("#graph_area").empty();
		}
		$.ajax({
			type : 'POST',
			url : "<%=path%>/monitor/query",
			data : $("#query_form").serialize(),
			success : function(data) {
				if(data.length==0){
					toastr.clear();
					toastr.warning("该主机下无可显示图形");
				}
				if(!isRefresh){
					var html = '';
					for(var i =0;i<data.length;i++) {
						html += createGraph(data[i]);
					}
					$(html).appendTo($("#graph_area"));
				}
				for(var i =0;i<data.length;i++) {
					createChart(data[i]);
				}
			}
		});
	}
	
	function refreshCascadeList(parent,child, grandChild, async){
		$.ajax({
			url : "<%=path%>/monitor/getSubHost",
			type : "POST",
			async : async,
			data : {"id":$("#"+parent).val()},
			success : function(data) {
				$("#"+child+" option[value!='']").remove();
				$("#"+child).select2("val","");
				if(grandChild != null){
					$("#"+grandChild+" option[value!='']").remove();
					$("#"+grandChild).select2("val", "");
					$("#s2id_"+grandChild).fadeOut(200);
				}
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						var graphTreeItem = data[i];
						var text = "";
						if(graphTreeItem.graphTreeItemType==1){
							text = graphTreeItem.title;
						} else {
							text = graphTreeItem.hostName;
						}
						if(grandChild != null){
							$("#"+child).append("<option uuid='"+graphTreeItem.graphTreeItemUuid+"'  value='"+graphTreeItem.graphTreeItemUuid+"' type='" + (graphTreeItem.graphTreeItemType==1? "node":"host") +"'>"+text+"</option>");
						} else {
							$("#"+child).append("<option value='"+graphTreeItem.graphTreeItemUuid+"'>"+text+"</option>");
						}
					}
					$("#s2id_"+child).fadeIn(200);
				} else {
					$("#s2id_"+child).fadeOut(200);
				}
			}
		});
	}
	
	

	function createGraph(graphItem){		
		var html = '';
		html += '<div class="images_center_mod">';
			html += '<div class="images_title_top">';
			html += '</div>';
			html += '<div id="'+graphItem.graphUuid+'" style="width:100%;height:400px;"></div>';
		html += '</div>';
		
		return html;
	}
	
	function createChart(graphItem){
		var myChart = echarts.init(document.getElementById(graphItem.graphUuid));
		// 指定图表的配置项和数据
		option = {
				title : {
					text : graphItem.hostIp+'-'+graphItem.graphName
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : graphItem.graphData.subitemLabels
				},
				xAxis : {
					type : 'category',
					boundaryGap : false,
					data : graphItem.graphData.xAxis
				},
				yAxis : {
					type : 'value',
					axisLabel : {
						formatter : function (value, index) {
							if(index==0){
								return "";
							}
							if(graphItem.graphSimpleName=="netStream"){
								if(value > 1024 * 1024 * 1024) {
									return (value/(1024*1024*1024)).toFixed(1) + "Gbps"
								} else if(value > 1024 * 1024) {
									return (value/(1024*1024)).toFixed(1) + "Mbps";
								} else if(value > 1024) {
									return (value/1024).toFixed(1) + "Kbps";
								} else {
									return value.toFixed(1) + "bps"
								}
							} else {
								return value;
							}
			            }
					}
				},
				series : graphItem.graphData.data
			};
	
			myChart.setOption(option);
			return myChart;
		}
	
		function calculateDateRange() {
			toastr.clear();
			var fromTime = $("#fromTime").val();
			var toTime = $("#toTime").val();
			if(fromTime != ''){
				if (new Date(fromTime.replace(/-/g, "/")).getTime() >= new Date().getTime()) {
					toastr.error("开始时间必须小于当前时间");
					return false;
				}
			}
			if (fromTime != '' && toTime != '') {
				if (new Date(fromTime.replace(/-/g, "/")).getTime() >= new Date(
						toTime.replace(/-/g, "/")).getTime()) {
					toastr.error("结束时间必须大于起始时间");
					return false;
				}
			}
	
			var time_unit = $("#time_unit").val();
	
			var noDatePicker = (toTime == '' && fromTime == '');
			if (!noDatePicker) {
			if (fromTime == '') {
				latestChangedPicker = 'toTime';
			}
			if (toTime == '') {
				latestChangedPicker = 'fromTime';
			}
		}
		if (noDatePicker) {
			if (time_unit == '0') {
				$("#time_unit").select2("val", "60");
				time_unit = $("#time_unit").val();
			}
			var now = new Date();
			$("#toTime").val(now.format("yyyy-MM-dd HH:mm:ss"));
			$("#fromTime").val(
					new Date(now.getTime() - (60000 * time_unit) + 1000)
							.format("yyyy-MM-dd HH:mm:ss"));
		} else {
			if (fromTime == "") {
				latestChanged = "toTime";
				latestChangedPicker = "toTime";
			} else if (toTime == "") {
				latestChanged = "fromTime";
				latestChangedPicker = "fromTime";
			}

			if (latestChanged == 'time_unit') {
				if (latestChangedPicker == 'fromTime') {
					var _fromTime = new Date(fromTime.replace(/-/g, "/"));
					$("#toTime").val(
							new Date(_fromTime.getTime() + (60000 * time_unit))
									.format("yyyy-MM-dd HH:mm:ss"));
				} else {
					var _toTime = new Date(toTime.replace(/-/g, "/"));
					$("#fromTime").val(
							new Date(_toTime.getTime() - (60000 * time_unit) + 1000)
									.format("yyyy-MM-dd HH:mm:ss"));
				}
			} else if (latestChanged == 'fromTime' && toTime == '') {
				if (time_unit == '0') {
					$("#time_unit").select2("val", "60");
					time_unit = $("#time_unit").val();
				}
				var _fromTime = new Date(fromTime.replace(/-/g, "/"));
				$("#toTime").val(
						new Date(_fromTime.getTime() + (60000 * time_unit))
								.format("yyyy-MM-dd HH:mm:ss"));
			} else if (latestChanged == 'toTime' && fromTime == '') {
				if (time_unit == '0') {
					$("#time_unit").select2("val", "60");
					time_unit = $("#time_unit").val();
				}
				var _toTime = new Date(toTime.replace(/-/g, "/"));
				$("#fromTime").val(
						new Date(_toTime.getTime() - (60000 * time_unit) + 1000)
								.format("yyyy-MM-dd HH:mm:ss"));
			}
		}
		fromTime = $("#fromTime").val();
		toTime = $("#toTime").val();
		if(new Date(fromTime.replace(/-/g, "/")).getTime() >= new Date().getTime()) {
			toastr.error("无法查询超出当前系统时间的数据");			
			return false;
		}
		return true;
	}

	function refreshDateRange(datePicker) {
		if (datePicker == 'time_unit' && $("#time_unit").val() == '0') {
			latestChanged = latestChangedPicker;
			return;
		}
		latestChanged = datePicker;
		if (datePicker == "fromTime" || datePicker == "toTime") {
			latestChangedPicker = datePicker;
			if ($("#" + datePicker + "Dummy").val() != $("#" + datePicker)
					.val()) {
				$("#time_unit").select2("val", "0");
			}
		}
	}
</script>
</head>


<body>
	<!-- 头部菜单 start-->
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/leftMenu.jsp"%>
		<div class="nms_box_center">
			<form id="query_form">
				<div class="nms_menu_c">
					<select data-toggle="select" id="time_unit" class="form-control select select-default mrs mbm">
						<option value="0">自定义</option>
						<option value="60">1小时</option>
						<option value="120">2小时</option>
						<option value="240">4小时</option>
						<option value="360">6小时</option>
						<option value="720">12小时</option>
						<option value="1440">1天</option>
						<option value="2880">2天</option>
						<option value="4320">3天</option>
						<option value="5760">4天</option>
						<option value="10080">1周</option>
						<option value="20160">2周</option>
						<option value="44640">1个月</option>
						<option value="89280">2个月</option>
						<option value="133920">3个月</option>
						<option value="178560">4个月</option>
						<option value="267840">6个月</option>
						<option value="527040">1年</option>
						<option value="1054080">2年</option>
					</select>
					<c:if test="${treeList != null && treeList.size()>0 }">	
						<select data-toggle="select" id="host_group" name="hostGroup" class="form-control select select-default mrs mbm">
							<option value="">请选择节点</option>
							<c:forEach items="${treeList}" var="tree">
								<option value="${tree.graphTreeUuid}">${tree.graphTreeName}</option>							
							</c:forEach>
						</select> 
					</c:if> 
					<select data-toggle="select" name="subNodeId"
						class="form-control select select-default mrs mbm" id="host_sub_list" style="display:none;">
						<option value="">请选择主机/节点</option>
					</select> 
					<select data-toggle="select" name="hostUuid"
						class="form-control select select-default mrs mbm" id="host_list" style="display:none;">
						<option value="">请选择主机</option>
					</select> 
					<select data-toggle="select" class="form-control select select-default mrs mbm" id="refresh_option">
						<option value="1">不刷新</option>
						<option value="2">刷新</option>
					</select> 
					<input type="text" class="form-control input-sm" placeholder="开始时间"
						onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(dp){refreshDateRange('fromTime');}})"
						name="fromTime" id="fromTime" />
					<input type="text" class="form-control input-sm" placeholder="结束时间"
						onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(dp){refreshDateRange('toTime');}})"
						name="toTime" id="toTime" />
					<div class="input-group input_search">
						<input type="text" class="form-control" name="graphName" id="graph_name" placeholder="搜索"  /> 
						<span class="input-group-btn">
							<button type="button" class="btn" id="query_btn">
								<span class="fui-search"></span>
							</button>
						</span>
					</div>
				</div>
			</form>
			
			<div class="nms_table_bg ">
				<h1></h1>
				<div class="images_center_box overflow" id="graph_area">
				</div>
			</div>
		</div>
</body>
</html>
