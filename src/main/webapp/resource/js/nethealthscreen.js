function drawLeftUpCircle(domId, color, percent, count){
	var data = new Array();
	if(percent>1){
		percent = 1;
	}
	if(count == 0){
		count = "0";
	}
	data.push({value:percent, name: count, itemStyle:{normal:{color:color},emphasis:{color:color}}});
	if(percent != "1") {
		data.push({value:1-percent, itemStyle:{normal:{color:'#eee'},emphasis:{color:'#eee'}}});
	}
	
	var fontSize = '35';
	if(count > 9999) {
		fontSize = '22';
	} else if(count > 999) {
		fontSize = '30';
	} else if(count > 99) {
		fontSize = '33';
	} else if(count > 9) {
		fontSize = '37';
	} else {
		fontSize = '40';
	}
	
	var dom = echarts.init(document.getElementById(domId));
	option = {
		    animation:false,
		    series: {
		        	startAngle :360,
		            type:'pie',
		            radius: ['75%', '100%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
		                    textStyle: {
		                    	color: "#fff",
		                        fontSize: fontSize,
		                        fontFamily: 'ds-digitalbold'
		                    }
		                },
		                emphasis: {
		                    show: true,
		                    position: 'center',
		                    textStyle: {
		                    	color: "#fff",
		                        fontSize: fontSize,
		                        fontFamily: 'ds-digitalbold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:data
		        }
		};
	dom.setOption(option);
	return dom;
}



function drawLeftDownCircle(domId, percent, diameter){
	var text = percent.toFixed(2);
	if(percent>100){
		percent = 100;
		text = "100";
	} else if(percent <= 0){
		percent = 0.01;
		text = "0";
	}
	$("#"+domId).ejChart({
        commonSeriesOptions: {
           labelPosition: 'inside',
	        marker:
			{
				dataLabel: 
				{ 
					shape: 'none', 
					visible: true, 
					textPosition: 'top', 
                  	font :{color : "rgb(17,93,163)",size:"20",fontWeight : "bold"},
					border: { width: 1},
				},
            }
        },
		series: 
		[
            {
              points: [{ x: "1", text: text+"%", y: percent,fill:"rgb(41,171,226)" },{ x: "1", y: 100-percent,fill:"rgb(251,176,59)",marker :{dataLabel :{visible : false}} }],
				type: 'doughnut', 
				doughnutCoefficient: 0.5, 
				doughnutSize: 0.8,
              	explodeOffset:0
            }
        ], 
        enable3D: true,	
		enableRotation: false,	
        depth: 20,
        tilt: -30,
        rotation: -30,
        perspectiveAngle: 0,                       
        size: { height: diameter, width:diameter},               
        legend: { visible: false}
    });
}

function drawLeftDownChart(domId, graphData){
	var myChart = echarts.init(document.getElementsByName(domId)[0]);
	
	var series = graphData.data;
	var itemStyle1 = {itemStyle : {normal : {lineStyle:{color:'red'}}}};
	var itemStyle2 = {itemStyle : {normal : {lineStyle:{color:'green'}}}};
	var data1 = $.extend(series[0],itemStyle1);
	var data2 = $.extend(series[1],itemStyle2);
	var _series = new Array();
	_series.push(data1);
	_series.push(data2);
	
	// 指定图表的配置项和数据
	option = {
		tooltip : {
			trigger : 'axis'
		},
		xAxis : {
			splitLine:{
				show:true,
				interval:2,
				lineStyle:{
					type:'dot',
					color:['grey']
				}
			},
			axisLabel: {
	            show: false
	        },
			type : 'category',
			boundaryGap : false,
			axisLine:{
	            lineStyle:{
	                color:['white'],
	                width:2
	            }
	        },
	        axisTick:{
	            show:false
	        },
			data : graphData.xAxis
		},
		yAxis : {
			type : 'value',
			splitNumber:10,
			splitLine:{
				show:true,
				interval:0,
				lineStyle:{
					type:'dot',
					color:['grey']
				}
			},
			boundaryGap: false,
			axisLine:{
	            lineStyle:{
	                color:['white'],
	                width:2
	            }
	        },
			axisLabel: {
	            show: false
	        },
	        axisTick:{
	            show:false
	        }
		},
		grid:{
			left:0,
			top:0,
			width:"100%",
			height:"100%"
		},
		series : _series
	};

	myChart.setOption(option);
	return myChart;
}

function drawRightDownPie(domId, data, diameter) {
	if(data == null) {
		return;
	}
	var total = data.total;
	var list = data.urlRankingList;
	var points = new Array();
	for(var i = 0;i<list.length;i++) {
		points.push({x:"1", text:"1", y: list[i].count/total, fill:rightDownPieFill[i], marker:{dataLabel:{visible:false}}});
	}
	$("#"+domId).ejChart({
		series: 
		[
            {
              points: points,
			   type: 'pie', 
              explodeOffset:0
            }
        ], 
		//Enabling 3D Chart
        enable3D: true,	
        depth: 30,
        tilt: 110,
        size: { height: diameter, width:diameter},               
        legend: { visible: false}
    });
}

function drawRightDownSystemStatusChart(domId, type, data, xaxis) {
	var _data = new Array();
	for(var i=0;i<data.length;i++) {
		if(type=='cpu') {
			_data.push(data[i].cpuCerc.toFixed(2));				
		} else {
			if(data[i].memTotal==0){
				_data.push(0);
			} else {
				_data.push(((data[i].memUsed * 100)/data[i].memTotal).toFixed(2));		
			}
		}
	}
	var myChart = echarts.init(document.getElementById(domId));
	var option = {
		tooltip : {
			trigger : 'axis'
		},
		xAxis : {
			type : 'category',
			boundaryGap:false,
			axisLabel:{
				show:false	
			},
			axisTick:{
				show:false
			},
			data : xaxis
		},
		yAxis : {
			type : 'value',
			boundaryGap:false,
			splitNumber: 10,
			spliteLine:{
				show:true,
				interval:0
			},
			axisLine:{
				show:false
			},
			axisTick:{
				show:false
			}
		},
		grid:{
			left:0,
			top:0,
			width:'100%',
			height:'100%'
		},
		series:{
			name: type=='cpu'?'cpu使用率':'内存使用率',
			type: 'line',
			data: _data
		}
	};
	myChart.setOption(option);
	$("#"+domId+"_title").text(_data[_data.length - 1] + "%");
}

function drawRightDownLogCountChart(domId, data){
	var myChart = echarts.init(document.getElementById(domId));
	var option = {
		tooltip : {
			trigger : 'axis'
		},
		xAxis : {
			type: 'category',
			boundaryGap:false,
			axisLabel:{
				show:false
			},
			axisTick:{
				show:false
			},
			data: data.logCountXaxis
		},
		yAxis:{
			type: 'value',
			boundaryGap:false,
			splitNumber: 10,
			spliteLine:{
				show:true,
				interval:0
			},
			axisLine:{
				show:false
			},
			axisTick:{
				show:false
			}
		},
		grid:{
			left:0,
			top:0,
			width:'100%',
			height:'100%'
		},
		series:[
	        {
				name: 'nat日志量',
				type:'line',
				data:data.natLogCountData,
				itemStyle:{
					normal:{
						lineStyle:{
							color:'red'
						}
					}
				}
			},{
				name: 'url日志量',
				type: 'line',
				data:data.urlLogCountData,
				itemStyle:{
					normal:{
						lineStyle:{
							color:'green'
						}
					}
				}
			}
		]
	};
	myChart.setOption(option);
	var lastCount = data.natLogCountData[data.natLogCountData.length - 1] + data.urlLogCountData[data.urlLogCountData.length - 1];
	if(lastCount/(10000 * 10000) >= 1) {
		$("#"+domId+"_title").text((lastCount/(10000 * 10000)).toFixed(1)+"亿");		
	} else if(lastCount/10000 >= 1) {
		$("#"+domId+"_title").text((lastCount/10000).toFixed(1)+"万");
	} else {
		$("#"+domId+"_title").text(lastCount);
	}
}

function replaceJsOrCssfile(oldfilename, newfilename, filetype){
	var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none"
	var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none"
	var allsuspects=document.getElementsByTagName(targetelement)
	for (var i=allsuspects.length; i>=0; i--){
		if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(oldfilename)!=-1){
		   var newelement=createjscssfile(newfilename, filetype)
		   allsuspects[i].parentNode.replaceChild(newelement, allsuspects[i])
		}
	}
}

function drawHeatGraph(domId, data, formerHeatmap, maxValue, radius){		
	if(data == null || data.length==0){
		return;
	}
	var dom = $("#"+domId);
	var width = dom.width();
	var height = dom.height();
	var heatmap = null;
	if(formerHeatmap == null) {
		heatmap = h337.create({container: dom[0], radius:radius});
	} else {
		heatmap = formerHeatmap;
	}
	var points = [];
	
	for(var i=0;i<data.length;i++) {
		var point = {
			x: data[i].x * width,
			y: data[i].y * height,
			value: data[i].value
		};
		points.push(point);
	}
	var data = {
	  max: maxValue,
	  data: points
	};
	heatmap.setData(data);
	return heatmap;
}

function refreshLeftUpData(url){
	$.ajax({
		type:'POST',
		url: url,
		success:function(data){
			if(data != null && typeof(data) == "object") {	
				var total = data.registerUserCount;
				if(data.totalOnlineUserCount==0) {
					odoo.default({ el:'.js-odoo',value:0});
					$("#wireless_count").text("0%");
					$("#wire_count").text("0%");
					$("#etc_count").text("0%");
				} else {
					odoo.default({ el:'.js-odoo',value:data.totalOnlineUserCount });
					$("#wireless_count").text((data.wirelessUserCount * 100/data.totalOnlineUserCount).toFixed(1)+"%");
					$("#wire_count").text((data.wireUserCount * 100/data.totalOnlineUserCount).toFixed(1)+"%");
					$("#etc_count").text((data.etcUserCount * 100/data.totalOnlineUserCount).toFixed(1)+"%");
				}
				if(total == 0) {
					drawLeftUpCircle("registerUserCount", "#06c", "1", 0);
					drawLeftUpCircle("activeUserCount", "#39c", 0, 0);
					drawLeftUpCircle("freeUserCount", "#3c3", 0, 0);
					drawLeftUpCircle("lostUserCount", "#f93", 0, 0);					
				} else {
					drawLeftUpCircle("registerUserCount", "#06c", "1", total);
					drawLeftUpCircle("activeUserCount", "#39c", (data.activeUserCount/total).toFixed(2), data.activeUserCount);
					drawLeftUpCircle("freeUserCount", "#3c3", (data.wireUserCount/total).toFixed(2), data.wireUserCount);
					drawLeftUpCircle("lostUserCount", "#f93", (data.wirelessUserCount/total).toFixed(2), data.wirelessUserCount);					
				}
			} else {
				drawLeftUpCircle("registerUserCount", "#06c", "1", 0);
				drawLeftUpCircle("activeUserCount", "#39c", 0, 0);
				drawLeftUpCircle("freeUserCount", "#3c3", 0, 0);
				drawLeftUpCircle("lostUserCount", "#f93", 0, 0);
				odoo.default({ el:'.js-odoo',value:0});
				$("#wireless_count").text("0%");
				$("#wire_count").text("0%");
				$("#etc_count").text("0%");
			}
		}
	});
}

function refreshCenterUpData(url, maxValue, radius){
	$.ajax({
		type:'POST',
		url:url,
		success:function(data){
			centerUpHeatMap = drawHeatGraph("heat_map", data, centerUpHeatMap, maxValue, radius);
		}
	});
}

function refreshCenterUpData1(url){
	$.ajax({
		type:'POST',
		url:url,
		cache:false,
		success:function(data){
			centerUpHeatMap1 = drawHeatGraph("heat_map1", data, centerUpHeatMap1);
		}
	});
}
function drawHeatGraph(domId, data, formerHeatmap){	
	console.debug(domId +" : "+ data +" : "+formerHeatmap)
	if(data == null || data.length==0){
		return;
	}
	var dom = $("#"+domId);
	var width = dom.width();
	var height = dom.height();
	var heatmap = null;
	if(formerHeatmap == null) {
		heatmap = h337.create({container: dom[0]});
	} else {
		heatmap = formerHeatmap;
	}
	var points = [];
	var maxValue = 100;
	
	for(var i=0;i<data.length;i++) {
		var point = {
			x: data[i].x * width,
			y: data[i].y * height,
			value: maxValue * (data[i].value)
		};
		points.push(point);
	}
	var data = {
	  max: maxValue,
	  data: points
	};
	heatmap.setData(data);
	return heatmap;
}
function refreshRightUpData(url){
	$.ajax({
		type:'POST',
		url:url,
		success:function(data){
			if(data<60) {
				$(".network_health").css("color", "red");
				$("#network_health_title").text("网络状态不良");
			} else if(data<80){
				$(".network_health").css("color", "yellow");
				$("#network_health_title").text("网络状态正常");
			} else {
				$(".network_health").css("color", "#6f0");
				$("#network_health_title").text("网络状态健康");
			}
			$("#network_health_value").text(data);
		}
	});
}

function refreshNetHeathData(url){
	$.ajax({
		type:'post',
		url:url,
		dataType:'json',
		success:function(data){
			for(var i=0;i<data.length;i++){
				if(i==0){
					$("#netHeathLi"+i).html("<p>"+ data[i]+"</p>紧急告警");
				}else if(i==1){
					$("#netHeathLi"+i).html("<p>"+ data[i]+"</p>高级告警");
				}else if(i==2){
					$("#netHeathLi"+i).html("<p>"+ data[i]+"</p>中级告警");
				}else if(i==3){
					$("#netHeathLi"+i).html("<p>"+ data[i]+"</p>低级急告警");
				}else if(i==4){
					$("#netHeath").html(data[i]);
				}
				
			}
		}
	});
}
function refreshDowntimeData(url){
	$.ajax({
		type:'post',
		url:url,
		dataType:'json',
		success:function(data){
			console.debug("宕机数："+data.length);
			if(data.length==0){
				$("#shutDownNum").html("<p>"+ data.length +"</p>宕机");
				$("#shutDownDesc").html("正常<p>暂无设备宕机</p>");
				$("#shutDownDesc").addClass("downtime_box_yes");
			}else{
				for(var i=0;i<data.length;i++){
					$("#shutDownNum").html("<p>"+ data.length +"</p>宕机");
					$("#shutDownDesc").html("宕机<p>"+data[i].hostName+"</p>");
				}
			}
		}
	});
}
function refreshSystemLogData(url){
	$.ajax({
		type:'post',
		url:url,
		dataType:'json',
		success:function(data){
			console.debug("系统日志："+data.length);
			$("#systemLogNum").html("<p>"+data.length+"</p>系统日志");
			var logData="<ul> ";
			for(var i=0;i<data.length;i++){
				logData+="<li>"+data[i].hostId+"</li>";
			}
			logData+="</ul>";
			console.info(logData);
			$("#systemLog1").html(logData);
		}
	});
}
function refreshthResholdData(url){
	$.ajax({
		type:'post',
		url:url,
		dataType:'json',
		success:function(data){
			console.debug("阀值："+data.length);
			$("#thResholdNum").html("<p>"+data.length+"</p>阈值");
			var thResholdData="<ul> ";
			for(var i=0;i<data.length;i++){
				thResholdData+="<li>"+data[i].thresholdValueName+"</li>";
			}
			thResholdData+="</ul>";
			$("#thReshold1").html(thResholdData);
		}
	});
}

function refreshLeftDownData(url, diameter){
	$.ajax({
		type:'POST',
		url:url,
		success:function(data){
			if(data != null) {
				for(var i=0;i<data.length;i++) {
					var exportStreamInfo = data[i];
					var percent = (exportStreamInfo.downStream * 100)/exportStreamInfo.maxBandWidth;
					var downStreamView = "";
					var upStreamView = "";
					if(exportStreamInfo.downStream/(1024*1024*1024)>1){
						downStreamView = (exportStreamInfo.downStream/(1024*1024*1024)).toFixed(1) + "Gbps";
					} else if(exportStreamInfo.downStream/(1024*1024)>1){
						downStreamView = (exportStreamInfo.downStream/(1024*1024)).toFixed(1) + "Mbps";
					} else if(exportStreamInfo.downStream/(1024)>1){
						downStreamView = (exportStreamInfo.downStream/(1024)).toFixed(1) + "Kbps";
					} else{
						downStreamView = (exportStreamInfo.downStream.toFixed(1)) + "bps";
					} 
					if(exportStreamInfo.upStream/(1024*1024*1024)>1){
						upStreamView = (exportStreamInfo.upStream/(1024*1024*1024)).toFixed(1) + "Gbps";
					} else if(exportStreamInfo.upStream/(1024*1024)>1){
						upStreamView = (exportStreamInfo.upStream/(1024*1024)).toFixed(1) + "Mbps";
					} else if(exportStreamInfo.upStream/(1024)>1){
						upStreamView = (exportStreamInfo.upStream/(1024)).toFixed(1) + "Kbps";
					} else{
						upStreamView = (exportStreamInfo.upStream.toFixed(1)) + "bps";
					} 
					$("p[down][_id='"+exportStreamInfo.divId+"']").text(downStreamView);
					$("p[up][_id='"+exportStreamInfo.divId+"']").text(upStreamView);
					
					drawLeftDownCircle(exportStreamInfo.divId, percent, diameter);
					drawLeftDownChart(exportStreamInfo.divId, exportStreamInfo.graph);
				}
			}
		}
	});
}

function refreshRightDownData(url, diameter){
	$.ajax({
		type:'POST',
		url:url,
		success:function(data){
			if(data != null) {
				var urlRankingList = data.urlRankingData.urlRankingList;
				var urlTotal = data.urlRankingData.total;
				var html = '';
				for(var i=0;i<urlRankingList.length;i++){
					var urlRanking = urlRankingList[i];
					var percent = ((urlRanking.count * 100)/urlTotal).toFixed(2);
					html += '<li>';
					html += '<div class="zlogdata_url">'+urlRanking.url+'</div>';
					html += '<div class="zlogdata_num">'+percent+' %</div>';
					html += '<div class="data_img_box">';
					html += '<div class="percentage_box warn_' + (i+1)+'" style="width:'+percent+'%;"></div>';
					html += '</div>';
					html += '</li>';
				}
				$("#zlog_data_list").empty();
				$(html).appendTo($("#zlog_data_list"));
				drawRightDownPie("url_ranking", data.urlRankingData, diameter);
				drawRightDownSystemStatusChart("zlog_cpu", "cpu", data.systemStatusData, data.systemStatusXaxis);
				drawRightDownSystemStatusChart("zlog_mem", "mem", data.systemStatusData, data.systemStatusXaxis);
				drawRightDownLogCountChart("zlog_log_count", data);
			}
		}
	});
}
