/**
 * Created by shenqilei on 2016/11/30.
 */
//var centerUpHeatMap;

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
                    font :{color : "rgb(252,252,252)",size:"20",fontWeight : "bold"},
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
                    color:['rgba(128, 128, 128, 0.2)']
                }
            },
            axisLabel: {
                show: false
            },
            type : 'category',
            boundaryGap : false,
            axisLine:{
                lineStyle:{
                    color:['rgba(252,252,252, 0.2)'],
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
                    color:['rgba(128, 128, 128, 0.2)']
                }
            },
            boundaryGap: false,
            axisLine:{
                lineStyle:{
                    color:['rgba(252,252,252, 0.2)'],
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

//function drawHeatGraph(domId, data, formerHeatmap, maxValue, radius){
//    if(data == null || data.length==0){
//        return;
//    }
//    var dom = $("#"+domId);
//    var width = dom.width();
//    var height = dom.height();
//    var heatmap = null;
//    if(formerHeatmap == null) {
//        heatmap = h337.create({container: dom[0], radius:radius});
//    } else {
//        heatmap = formerHeatmap;
//    }
//    var points = [];
//
//    for(var i=0;i<data.length;i++) {
//        var point = {
//            x: data[i].x * width,
//            y: data[i].y * height,
//            value: data[i].value
//        };
//        points.push(point);
//    }
//    var data = {
//        max: maxValue,
//        data: points
//    };
//    heatmap.setData(data);
//    return heatmap;
//}

//获取中间图上部的上半部分图数据
function drawCenterUpUpGraph(){
	$.ajax({
		type:'post',
		url:path+"/home/getDeviceData",
		dataType:'json',
		success:function(data){
			for(var i=0;i<data.length;i++){
					var countList = data[i];
					$("#totalCount"+i).html(countList[0]);
					$("#onlineCount"+i).html(countList[2]);
					$("#downCount"+i).html(countList[1]);
				}
				
			}
	});
}

function drawCenterUpDownGraph(){
		$.ajax({
			type:'post',
			url:path+"/home/getMainDeviceMonitorData",
			dataType:'json',
			success:function(data){
					if($("#equipment_box").find("li").length >0){
						for(var i=0;i<data.length;i++){
							var host = data[i];
							if(host.hostWorkStatus==1){
								$("div[name='div"+host.hostIp+"']").each(function(){
									$(this).attr("class", "normal_bg");
								});
								
							}else{
								$("div[name='div"+host.hostIp+"']").each(function(){
									$(this).attr("class", "downtime_bg");
								});
							}
						}
					}else{
						for(var i=0;i<data.length;i++){
							var host = data[i];
							var row = "";
							var status = "";
							if(host.hostWorkStatus==1){
								//正常
								row +="<li><div name='div"+host.hostIp+"' class=\"normal_bg\"><div class=\"equipment_iconbox\"><i></i><div class=\"equipment_iconbga\"></div></div><p>"+host.hostName+"</p></div></li>"
							}else{
								row +="<li><div name='div"+host.hostIp+"' class=\"downtime_bg\"><div class=\"equipment_iconbox\"><i></i><div class=\"equipment_iconbga\"></div></div><p>"+host.hostName+"</p></div></li>";
							}
							$("#equipment_box").append(row);
							
						}
						$('#mainDeviceDiv').kxbdMarquee({direction:"left"});
					}
			}
		});
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
                    var wireless_count = Math.floor((data.wirelessUserCount * 100/data.totalOnlineUserCount));
                    var wire_count = Math.floor((data.wireUserCount * 100/data.totalOnlineUserCount));
                    $("#wireless_count").text(wireless_count+"%");
                    $("#wire_count").text(wire_count+"%");
                    $("#etc_count").text((100-wireless_count-wire_count)+"%");
                }
                if(total == 0) {
                    drawLeftUpCircle("registerUserCount", "#06c", "1", 0);
                    drawLeftUpCircle("activeUserCount", "#39c", 0, 0);
                    drawLeftUpCircle("freeUserCount", "#3c3", 0, 0);
                    drawLeftUpCircle("lostUserCount", "#f93", 0, 0);
                } else {
                    drawLeftUpCircle("registerUserCount", "#06c", "1", total);
                    drawLeftUpCircle("activeUserCount", "#39c", data.activeUserCount/total, data.activeUserCount);
                    drawLeftUpCircle("freeUserCount", "#3c3", data.wireUserCount/total, data.wireUserCount);
                    drawLeftUpCircle("lostUserCount", "#f93", data.wirelessUserCount/total, data.wirelessUserCount);
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

function refreshCenterUpData(){
	drawCenterUpUpGraph();
	drawCenterUpDownGraph();
	 
}

//function getPoint(r, ox, oy, count){
//    var point = [];
//    for( i = 0; i < count; i++){
//
//        var x_direction = Math.round(Math.random());
//        var y_direction = Math.round(Math.random());
//
//        var x,y;
//
//        if(x_direction>0){
//            x = Math.floor(Math.random()*r)+1+ox
//        }else{
//            x = ox-(Math.floor(Math.random()*r)+1);
//        }
//
//        if(y_direction>0){
//            y = Math.floor(Math.random()*r)+1+oy
//        }else{
//            y = oy-(Math.floor(Math.random()*r)+1);
//        }
//
//        point.unshift({x:x,y:y});
//    }
//    return point;
//}

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

function refreshFlowData(url){
	var timeAxisData=[];
	var wirelessFlowTrendData=[];
	var pcFlowTrendData=[];
	$.ajax({
         type:'POST',
         url:url,
         cache:false,
         success:function(data){
        	// console.log(data);
        	var wireFlow=(data[0].wireFlow/data[0].flowCount)*100;
        	var wirelessFlow=(data[0].wirelessFlow/data[0].flowCount)*100;
        	timeAxisData=data[0].timeAxis;
        	$("#zlog_data_li").children().filter('li').remove();
        	var warn=1;
        	for(var key in data[0].userGroupFlow){
        		if(warn==5){break;}
        		var userGroupFlowPer=(data[0].userGroupFlow[key]/data[0].userGroupFlowCount*100).toFixed(2);
        		$("#zlog_data_li").append("<li><div class=\"zlogdata_url\">"+key+"</div><div class=\"zlogdata_num\">"+ data[0].userGroupFlow[key]+" M</div><div class=\"data_img_box\"><div class=\"percentage_box warn_"+warn+"\" style=\"width:"+userGroupFlowPer+"%;\"></div></div></li>");
        		warn+=1;
        	}
        	
        	for(var key in data[0].wirelessFlowTrend){
        		wirelessFlowTrendData.push(data[0].wirelessFlowTrend[key].value);
        	}
        	//console.log(wirelessFlowTrendData);
        	
        	for(var key in data[0].pcFlowTrend){
        		pcFlowTrendData.push(data[0].pcFlowTrend[key].value);
        	}
        	try{
        		flowPie(wireFlow,wirelessFlow);
        	}catch (e) {
        		console.error(e);
			}
       	 //************************
       	flowTrendChart(timeAxisData,wirelessFlowTrendData,pcFlowTrendData);
         }
     });
}

function flowPie(wireFlow,wirelessFlow){
	 var winWidth  = window.screen.width;
		var winHeight = window.screen.height;
		var width;
		var height;
		if(winWidth==1920 && winHeight==1080){
			width='500';
			height='400';
		}else if(winWidth==1600 && winHeight==900){
			width='400';
			height='330';
		}else if(winWidth==1366 && winHeight==768){
			width='340';
			height='240';
		}else if(winWidth==1280 && winHeight==1024){
			width='300';
			height='290';
		}
		
	var points = new Array();
	if(wireFlow>0){
		points.push({ x: "有线流量", text: wireFlow.toFixed(1)+"%", y: Math.ceil(wireFlow),fill:"rgb(41,171,226)" });
	}
	if(wirelessFlow>0){
		points.push({ x: "无线流量", text: wirelessFlow.toFixed(1)+"%", y: Math.ceil(wirelessFlow) ,fill:"rgb(251,176,59)"});
	}
	if(points.length==0){
		points.push({x:"暂无数据",text:"0%",y:"0.1"});
	}
	//console.log(points);
	 $("#zlog_data_a").ejChart(
        {   
		//Initializing Common Properties for all the series     		
    	commonSeriesOptions: 
		{
			labelPosition: 'inside',                          
			//tooltip: { visible: true, format: "#point.x# : #point.y#%" },
			marker: 
			{
				dataLabel: 
				{ 
					shape: 'none', 
					visible: true, 
					textPosition: 'top', 
					border: { width: 3}, 
					font :{color : "rgb(252,252,252)",size:"20",fontWeight : "bold"},
					connectorLine: { height: -60, stroke:"none" } 
				}
			}
		},
		//Initializing Series
		series: 
		[
            {
                points: points, 
				
				//explodeIndex: 0,                              
                border: { width: 2, color: 'white' },
                type: 'doughnut', 
				labelPosition: 'outside', 
				startAngle: 145
            }
        ],
        //Enabling 3D Chart			
        enable3D: true,
		enableRotation:false,
        depth: 30,
        tilt: -60,
        rotation: 0,
        perspectiveAngle: 0,                     
        canResize: false,
		//load: "onchartload",                      
        size: { height: height, width:width},                     
        legend: { visible: false }
    });
}
function flowTrendChart(timeAxisData,wirelessFlowTrendData,pcFlowTrendData){
	 var myChart = echarts.init(document.getElementById("flowCharts"));
	 option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			        	  splitLine:{
			                  show:true,
			                  interval:2,
			                  lineStyle:{
			                      type:'dot',
			                      color:['rgba(128, 128, 128,  0.2)']
			                  }
			              },
			              axisLabel: {
			                  show: false
			              },
			              type : 'category',
			              boundaryGap : false,
			              axisLine:{
			                  lineStyle:{
			                      color:['rgba(252,252,252,  0.2)'],
			                      width:2
			                  }
			              },
			              axisTick:{
			                  show:false
			              },
			            data :timeAxisData 
			        }
			    ],
			    yAxis : [
			        {
			        	 type : 'value',
			             splitNumber:10,
			             splitLine:{
			                 show:true,
			                 interval:0,
			                 lineStyle:{
			                     type:'dot',
			                     color:['rgba(128, 128, 128,  0.2)']
			                 }
			             },
			             boundaryGap: false,
			             axisLine:{
			                 lineStyle:{
			                     color:['rgba(252,252,252,  0.2)'],
			                     width:2
			                 }
			             },
			             axisLabel: {
			                 show: false
			             },
			             axisTick:{
			                 show:false
			             }
			        }
			    ],
			    grid:{
		            left:0,
		            top:0,
		            width:"100%",
		            height:"100%"
		        },
			    series : [
			        {
			            name:'有线',
			            type:'line',
			            //itemStyle : {normal : {lineStyle:{color:'rgba(41,171,226, 0.5)'}}},
			            //areaStyle: {normal: {color:'rgba(41,171,226,0.5)'}},
			            itemStyle : {normal : {lineStyle:{color:'red'}}},
			            areaStyle: {normal: {}},
			            data:pcFlowTrendData
			        },
			        {
			            name:'无线',
			            type:'line',
			            //itemStyle : {normal : {lineStyle:{color:'rgba(251,176,59, 0.5)'}}},
			            //areaStyle: {normal: {color:'rgba(251,176,59, 0.5)'}},
			            itemStyle : {normal : {lineStyle:{color:'green'}}},
			            areaStyle: {normal: {}},
			            data:wirelessFlowTrendData
			        }]
			};
	    myChart.setOption(option);
}

function getNetHeathData(url){
	$.ajax({
		type:'post',
		url:url,
		dataType:'json',
		success:function(data){
			for(var i=0;i<data.length;i++){
				if(i==0){
					$("#emergency_alarm").html(data[i]);
				}else if(i==1){
					$("#advanced_alarm").html(data[i]);
				}else if(i==2){
					$("#intermediate_alarm").html(data[i]);
				}else if(i==3){
					$("#low_level_alarm").html(data[i]);
				}
			}
		}
	});
}

function refreshAccessTimeAnalysis(url){
	$.ajax({
        type:'POST',
        url:url,
        cache:false,
        success:function(data){
        	//console.log(data);
        	//每天
        	var teacher_wired_every_day="<li class=\"a_access_a\"><p class=\"font_35px blod led_fonts\" id=\"teacherWireless_pc\"><i></i>"+Math.ceil(data.teacher.pcOnlineTimePerDay)+"</p><p>老师有线</p></li>";
        	var student_wireless_every_day="<li class=\"a_access_b\"><p class=\"font_35px blod led_fonts\" id=\"studentWireless_mobile\"><i></i>"+Math.ceil(data.student.wirelessOnlineTimePerDay)+"</p><p>学生无线</p></li>";
        	var teacher_wireless_every_day="<li class=\"a_access_c\"><p class=\"font_35px blod led_fonts\" id=\"teacherWireless_mobile\"><i></i>"+Math.ceil(data.teacher.wirelessOnlineTimePerDay)+"</p><p>老师无线</p></li>";
        	var student_wired_every_day="<li class=\"a_access_d\"><p class=\"font_35px blod led_fonts\" id=\"studentWireless_pc\"><i></i>"+Math.ceil(data.student.pcOnlineTimePerDay)+"</p><p>学生有线</p></li>";
        	$("#everyday_data").append(teacher_wired_every_day);
        	$("#everyday_data").append(student_wired_every_day);
        	$("#everyday_data").append(teacher_wireless_every_day);
        	$("#everyday_data").append(student_wireless_every_day);
        	
        	//每次
        	var teacherWried=data.teacher.pcOnlineTimePerRecord;
        	var teacherWireless=data.teacher.wirelessOnlineTimePerRecord;
        	var studentWried=data.student.pcOnlineTimePerRecord
        	var studentWireless=data.student.wirelessOnlineTimePerRecord;
        	
        	/*$("#every_time_teacherWireless_pc").html((teacherWried).toFixed(2));
        	$("#every_time_studentWireless_mobile").html((studentWireless).toFixed(2));
        	$("#every_time_teacherWireless_mobile").html((teacherWireless).toFixed(2));
        	$("#every_time_studentWireless_pc").html((studentWried).toFixed(2));*/
        	
        	
        	var timeRecordCount=teacherWried+teacherWireless+studentWried+studentWireless;
        	var teacherWriedPer=(teacherWried/timeRecordCount)*100;
        	var teacherWirelessPer=(teacherWireless/timeRecordCount)*100;
        	var studentWriedPer=(studentWried/timeRecordCount)*100;
        	var studentWirelessPer=(studentWireless/timeRecordCount)*100;
        	$("#time_ul").children().filter('li').remove();
        	var teacher_wried_every_time="<li class=\"a_access_a\"><p class=\"font_35px blod led_fonts\" ><i></i>"+Math.ceil(teacherWried)+"</p><p>老师有线</p><div class=\"a_access_box\"><div class=\"a_access_a_bg\" style=\"width:"+teacherWriedPer+"%;\"></div></div></li>" +
        			"<li class=\"a_access_d\"><p class=\"font_35px blod led_fonts\" ><i></i>"+Math.ceil(studentWried)+"</p><p>学生有线</p><div class=\"a_access_box\"><div class=\"a_access_d_bg\" style=\"width:"+studentWriedPer+"%;\"></div></div></li>" +
        					"<li class=\"a_access_c\"><p class=\"font_35px blod led_fonts\" ><i></i>"+Math.ceil(teacherWireless)+"</p><p>老师无线</p><div class=\"a_access_box\"><div class=\"a_access_c_bg\" style=\"width:"+teacherWirelessPer+"%;\"></div></div></li>" +
        							"<li class=\"a_access_b\"><p class=\"font_35px blod led_fonts\" ><i></i>"+Math.ceil(studentWireless)+"</p><p>学生无线</p><div class=\"a_access_box\"><div class=\"a_access_b_bg\" style=\"width:"+studentWirelessPer+"%;\"></div></div></li>";
        	$("#time_ul").append(teacher_wried_every_time);
        	pieChart(data);
        }
	});
}
function pieChart(data){
	var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='360';
		height='360';
	}else if(winWidth==1600 && winHeight==900){
		width='330';
		height='260';
	}else if(winWidth==1366 && winHeight==768){
		width='300';
		height='240';
	}else if(winWidth==1280 && winHeight==1024){
		width='230';
		height='230';
	}
	var teacherPcPer=((data.teacher.pcOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var studentPcPer=((data.student.pcOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var teacherWirelessPer=((data.teacher.wirelessOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var studentWirelessPer=((data.student.wirelessOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var points=[];
	if(teacherPcPer>0){
		points.push({ x: "老师有线", text: teacherPcPer+"%", y: data.teacher.pcOnlineTimePerDay ,fill:'#19DDDB'});
	}
	if(studentPcPer>0){
		points.push({ x: "学生有线", text: studentPcPer+"%", y: data.student.pcOnlineTimePerDay ,fill:'#0071BD'});
	}
	if(teacherWirelessPer>0){
		points.push( { x: "老师无线", text: teacherWirelessPer+"%", y: data.teacher.wirelessOnlineTimePerDay ,fill:'#22AD38'});
	}
	if(studentWirelessPer>0){
		points.push( { x: "学生无线", text: studentWirelessPer+"%", y: data.student.wirelessOnlineTimePerDay , fill:'#FBB03B'});
	}
	if(points.length==0){
		points.push( { x: "暂无数据", text: "0%", y: "0.1" });
	}

	$("#a_everyday_data").ejChart({
        //Initializing Common Properties for all the series         
        commonSeriesOptions: 
		{
            labelPosition: 'inside',
            //tooltip: { visible: false, format: "#point.x# : #point.y#%" },
	        marker:
			{
				dataLabel: 
				{ 
					shape: 'none', 
					visible: true, 
					textPosition: 'top', 
					border: { width: 1}, 
					font :{color : "rgb(255,255,255)",size:"20",fontWeight : "bold"},
					connectorLine: { height: 0, stroke:"black" } 
				}
            }
        },
		
		//Initializing Series
		series: 
		[
            {
                points: points,

				type: 'doughnut', 
				//explodeIndex: 4, 
				doughnutCoefficient: 0.5, 
				doughnutSize: 0.8
            }
        ], 
		
		//Enabling 3D Chart
        enable3D: true,	
		enableRotation: false,			
        depth: 30,
        tilt: -50,
        rotation: 0,
        perspectiveAngle: 0,                      
        canResize: false,
        load: "onchartload",                    
        size: { height: height,width:width},               
        legend: { visible: false}
    });
}
