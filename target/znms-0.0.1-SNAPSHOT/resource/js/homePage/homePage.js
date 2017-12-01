function getOnlineUserData(){
		$.ajax({
			type:'post',
			url:path+"/home/getOnlineUserData",
			dataType:'json',
			success:function(data){
				if(null!=data){
					//设置注册总人数
					$("#totalRegistUser").html(data.registerUserCount+"人");
					var totalUserCount = data.totalOnlineUserCount+"";

					var k=(totalUserCount.length-1)>4?4:(totalUserCount.length-1);
					for(var i=0;i<5;i++){
						if(i<totalUserCount.length){
							$("#totalUserLi"+k).html(totalUserCount.charAt(i));
							k--;
						}else{
							$("#totalUserLi"+i).html("0");
						}


					}
					$("#pcUser").html(data.wireUserCount+"人");
					$("#wirelessUser").html(data.wirelessUserCount+"人");
					$("#otherUser").html(data.etcUserCount+"人");
				}
			}
		});
	}


//查找在线用户折线图数据
function getOnlineUserDataByCategory(){
	$.ajax({
		url:path+"/home/getOnlineUserDataByCategory",
		type:"POST",
		dataType:"json",
		success:function(data){
			var timeArray = [];
			for(var i=1; i< data.timeList.length;i++){
				 timeArray.push(data.timeList[i].slice(10,16));
            }
			var wireArray = [];
			var wirelessArray = [];
			var otherArray = [];
			for(var i=0; i< data.userCountList[0].length;i++){
				wireArray.push(data.userCountList[0][i]);
           }
			for(var i=0; i< data.userCountList[1].length;i++){
				wirelessArray.push(data.userCountList[1][i]);
           }
			for(var i=0; i< data.userCountList[2].length;i++){
				otherArray.push(data.userCountList[2][i]);
           }
			var onlineUserChart = echarts.init(document.getElementById('onlineUserPic'));
			onlineUserChart.setOption({
				 title: {
				        text: ''
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
//				    legend: {
//				        data:['电脑','移动','其他']
//				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : timeArray
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'电脑',
				            type:'line',
				            stack: '在线人数',
				            areaStyle: {normal: {}},
				            smooth: true,
				            data:wireArray
				        },
				        {
				            name:'移动',
				            type:'line',
				            stack: '在线人数',
				            areaStyle: {normal: {}},
				            smooth: true,
				            data:wirelessArray
				        },
				        {
				            name:'其他',
				            type:'line',
				            stack: '在线人数',
				            areaStyle: {normal: {}},
				            smooth: true,
				            data:otherArray
				        }
				        
				    ],
				    color:['#00adec','#8cd6f6','#ccebfa']
			});
		}
	});
}

//获取设备统计数据
function getDeviceData(){
	$.ajax({
		type:'post',
		url:path+"/home/getDeviceData",
		dataType:'json',
		success:function(data){
			for(var i=0;i<data.length;i++){
				var countList = data[i];
				var onlinePercent = "0%";
				var downPercent = "0%";
				if(countList[0]!=0){
					onlinePercent = parseInt(countList[2])/parseInt(countList[0])*100+"%";
					downPercent = parseInt(countList[1])/parseInt(countList[0])*100+"%";
				}
				if(i==0){
					//设置交换设备
					$("#exchangeDeviceTotal").html("<img src='images/z-switch.png'><p>交换设备</p>"+countList[0]);
					$("#exchangeDeviceOnline").html(countList[2]+"台");
					$("#exchangeDeviceDown").html(countList[1]+"台");
					$("#exchangeOnlinePercent").width(onlinePercent);
					$("#exchangeDownPercent").width(downPercent);
				}else if(i ==1){
					//设置网关设备
					$("#gatewayDeviceTotalCount").html(" <img src='images/z-gateway.png'><p>网关设备</p>"+countList[0]);
					$("#gatewayDeviceOnlineCount").html(countList[2]+"台");
					$("#gatewayDeviceDownCount").html(countList[1]+"台");
					$("#gatewayOnlinePercent").width(onlinePercent);
					$("#gatewayDownPercent").width(downPercent);
				}else if(i ==2){
					//设置无线网关
					$("#wirelessDeviceTotalCount").html("<img src='images/z-ap.png'><p>无线网关</p>"+countList[0]);
					$("#wirelesDeviceOnlineCount").html(countList[2]+"台");
					$("#wirelesDeviceDownCount").html(countList[1]+"台");
					$("#wirelessOnlinePercent").width(onlinePercent);
					$("#wirelessDownPercent").width(downPercent);
				}else if(i ==3){
					//设置AP
					$("#otherDeviceTotalCount").html("<img src='images/z-elog.png'><p>AP</p>"+countList[0]);
					$("#otherDeviceOnlineCount").html(countList[2]+"台");
					$("#otherDeviceDownCount").html(countList[1]+"台");
					$("#otherOnlinePercent").width(onlinePercent);
					$("#otherDownPercent").width(downPercent);
				}
			}
			
		}
	});
}


function getMainDeviceMonitorData(){
	$.ajax({
		type:'post',
		url:path+"/home/getMainDeviceMonitorData",
		dataType:'json',
		success:function(data){
			$("#mainDiv").html("");
			for(var i=0;i<data.length;i++){
				var host = data[i];
				var row = "";
				var status = "";
				if(host.hostWorkStatus==1){
					//正常
					row +="<li><dl class='net_monitor_mod net_monitor_yes '>"
					status = "正常";
				}else{
					row +="<li><dl class='net_monitor_mod net_monitor_no '>";
					status = "宕机";
				}
				row += "<dt><i></i><div class='net_monitor_mod_bg'></div></dt><dd>名称："+host.hostName+"</dd>"+
				"<dd>状态："+status+"</dd><dd>IP地址："+host.hostIp+"</dd></li>";
				$("#mainDiv").append(row);
			}
			$("#mainDeviceMonitorDiv").kxbdMarquee({direction:"left"});
		}
	});
}


function getNetHeathData(){
	$.ajax({
		type:'post',
		url:path+"/home/getNetHeathData",
		dataType:'json',
		success:function(data){
			for(var i=0;i<data.length;i++){
				if(i==0){
					$("#netHeathLi"+i).html(data[i]+"<p>紧急告警</p>");
				}else if(i==1){
					$("#netHeathLi"+i).html(data[i]+"<p>高级告警</p>");
				}else if(i==2){
					$("#netHeathLi"+i).html(data[i]+"<p>中级告警</p>");
				}else if(i==3){
					$("#netHeathLi"+i).html(data[i]+"<p>低级告警</p>");
				}else if(i==4){
					$("#netHeath").html(data[i]);
				}
				
			}
		}
	});
}

function transformFlow(flow) {
	var result = '';
	if(flow/(1024*1024*1024)>=1) {
		result += (flow/(1024*1024*1024)).toFixed(1) + "Gbps";
	} else if(flow/(1024*1024)>=1) {
		result += (flow/(1024*1024)).toFixed(1) + "Mbps";
	} else if(flow/(1024) >=1 ){
		result += (flow/(1024)).toFixed(1) + "Kbps";
	} else {
		result += parseInt(flow) + "bps";
	}
	return result;
}


function getExportFlowData(){
	var charMap = new Map();

	$.ajax({
		type:'post',
		url:path+"/home/getExportFlowData",
		dataType:'json',
		success:function(data){
			$("#exportDiv").html("");


			for(var i=0;i<data.length;i++) {
				var exportFlow = data[i];
				var row =
					"<li><dl class='export_utilization'><dt class='exportName' data='"+i+"'></dt>" +
					"<dd class='scale_diagram_a'><div class='exportPlate' data='" + i + "'>" +
					"</div></dd><dd class='traffic_data'><ul><li><h2>下行</h2><p class='downFlow' data='"+i+"'></p></li><li><h2>上行</h2><p class='upFlow' data='"+i+"'></p></li></ul>"+
					"</dd><dd class='scale_diagram_b'><div class='exportLine' data='"+i+"'></div></dd></dl></li>"
					;
				$("#exportDiv").append(row);
			}

			$("#exportFlow").kxbdMarquee({direction:"left"});

			//设置名称
			$(".exportName").each(function () {
				var index = $(this).attr("data");
				var exportFlow = data[index];
				$(this).html(exportFlow.name);
			});



			var countMap = new Map();
			//设置仪表盘
			$(".exportPlate").each(function () {
				var index = $(this).attr("data");
				var exportFlow = data[index];


				var FlowData = 0;
				var FlowDataReal = 0;
				if(exportFlow.graph != null){
					FlowData = exportFlow.graph.data[exportFlow.graph.data.length-1];
					FlowDataReal = parseInt((FlowData.data[FlowData.data.length-1]*100)/exportFlow.maxBandWidth);
					if(FlowDataReal >100){
						FlowDataReal = 100;
					}
				}

				var char = echarts.init($(this)[0]);
				char.setOption({
					tooltip : {
						formatter: "{a} <br/>{b} : {c}%"
					},
					series: [
						{
							radius:"100%",
							name: '',
							type: 'gauge',
							detail: {formatter:'{value}%'},
							axisLine:{
								lineStyle:{
									color:[[0.2, '#228822'], [0.8, '#4488bb'], [1, '#ff4400']],
									width:10
								}
							},
							axisTick:{
								splitNumber: 10,
								length:5
							},
							pointer:{
								length:'100%'
							},
							splitLine: {           // 分隔线
								length:20,         // 属性length控制线长
								lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
									color: 'auto'
								}
							},
							title:{
								offsetCenter:[0,'-20%']
							},
							data: [{value: FlowDataReal, name: '出口利用率'}]
						}
					]
				});
				var exportPlateCount = countMap.get("exportPlateCount_"+exportFlow.name);
				if(null==exportPlateCount){
					exportPlateCount = 0;
					countMap.set("exportPlateCount_"+exportFlow.name,exportPlateCount+1);
				}
				charMap.set("exportPlate_"+exportFlow.name+"_"+exportPlateCount,char);
			});

			//设置下行流量

			$(".downFlow").each(function () {
				var index = $(this).attr("data");
				var exportFlow = data[index];
				$(this).html(transformFlow(exportFlow.downStream));
				var downFlowCount = countMap.get("downFlowCount_"+exportFlow.name);
				if(null==downFlowCount){
					downFlowCount = 0;
					countMap.set("downFlowCount_"+exportFlow.name,downFlowCount+1);
				}
				charMap.set("downFlow_"+exportFlow.name+"_"+downFlowCount,$(this));
			});

			//设置上行流量
			$(".upFlow").each(function () {
				var index = $(this).attr("data");
				var exportFlow = data[index];
				$(this).html(transformFlow(exportFlow.upStream));

				var upFlowCount = countMap.get("upFlowCount_"+exportFlow.name);
				if(null==upFlowCount){
					upFlowCount = 0;
					countMap.set("upFlowCount_"+exportFlow.name,upFlowCount+1);
				}
				charMap.set("upFlow_"+exportFlow.name+"_"+upFlowCount,$(this));
			});


			//设置流量折线
			$(".exportLine").each(function () {
				var index = $(this).attr("data");
				var exportFlow = data[index];


				var timeArray = [];
				var inflowArray = [];
				var outflowArray = [];
				if(exportFlow.graph!=null){
					for(var j=0;j<exportFlow.graph.xAxis.length;j++){
						timeArray.push(exportFlow.graph.xAxis[j].slice(10));
					}
					var xArray1 = exportFlow.graph.data[0].data;
					for(var q=0;q<xArray1.length;q++){
						outflowArray.push(xArray1[q]);
					}
					var xArray2 = exportFlow.graph.data[1].data;
					for(var q=0;q<xArray2.length;q++){
						inflowArray.push(xArray2[q]);
					}
				}
//				加载流量折线图
				var char = echarts.init($(this)[0])
				char.setOption({
					title: {
						text: ''
					},
					tooltip : {
						trigger: 'axis'
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						top: '3%',
						containLabel: true
					},
					xAxis : [
						{
							type : 'category',
							boundaryGap : false,
							data : timeArray
						}
					],
					yAxis : [
						{
							type : 'value',
							axisLabel : {
								formatter : function (value, index) {
									if(index==0){
										return "";
									}
									return transformFlow(value);
								}
							}
						}
					],
					series : [
						{
							symbolOffset:[0,'10%'],
							name:'流入',
							type:'line',
							stack: '流入',
							areaStyle: {normal: {}},
							data:inflowArray
						},
						{
							symbolOffset:[0,'10%'],
							name:'流出',
							type:'line',
							stack: '流出',
							areaStyle: {normal: {}},
							data:outflowArray
						}

					],
					color:['#00adec','#8cd6f6']
				});


				var exportLineCount = countMap.get("exportLineCount_"+exportFlow.name);
				if(null==exportLineCount){
					exportLineCount = 0;
					countMap.set("exportLineCount_"+exportFlow.name,exportLineCount+1);
				}
				charMap.set("exportLine_"+exportFlow.name+"_"+exportLineCount,char);
			});


			//刷新
			setInterval(function(){


				$.ajax({
					type: 'post',
					url: path + "/home/getExportFlowData",
					dataType: 'json',
					success: function (data) {

						for(var i=0;i<data.length;i++) {
							var exportFlow = data[i];
							var name = exportFlow.name;


							//更新下行流量
							var downFlow_0 = charMap.get("downFlow_"+name+"_0");
							var downFlow_1 = charMap.get("downFlow_"+name+"_1");

							if(null!=downFlow_0){
								$(downFlow_0[0]).html(transformFlow(exportFlow.downStream));
							}

							if(null!=downFlow_1){
								$(downFlow_1[0]).html(transformFlow(exportFlow.downStream));
							}

							//更新上行流量
							var upFlow_0 = charMap.get("upFlow_"+name+"_0");
							var upFlow_1 = charMap.get("upFlow_"+name+"_1");

							if(null!=upFlow_0){
								$(upFlow_0[0]).html(transformFlow(exportFlow.upStream));
							}

							if(null!=upFlow_1){
								$(upFlow_1[0]).html(transformFlow(exportFlow.upStream));
							}

							//更新仪表盘
							var exportPlate_0 = charMap.get("exportPlate_"+name+"_0");
							var exportPlate_1 = charMap.get("exportPlate_"+name+"_1");

							var FlowData = 0;
							var FlowDataReal = 0;
							if(exportFlow.graph != null){
								FlowData = exportFlow.graph.data[exportFlow.graph.data.length-1];
								FlowDataReal = parseInt((FlowData.data[FlowData.data.length-1]*100)/exportFlow.maxBandWidth);
								if(FlowDataReal >100){
									FlowDataReal = 100;
								}
							}

							var exportPlateOption = {
								tooltip : {
									formatter: "{a} <br/>{b} : {c}%"
								},
								series: [
									{
										radius:"100%",
										name: '',
										type: 'gauge',
										detail: {formatter:'{value}%'},
										axisLine:{
											lineStyle:{
												color:[[0.2, '#228822'], [0.8, '#4488bb'], [1, '#ff4400']],
												width:10
											}
										},
										axisTick:{
											splitNumber: 10,
											length:5
										},
										pointer:{
											length:'100%'
										},
										splitLine: {           // 分隔线
											length:20,         // 属性length控制线长
											lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
												color: 'auto'
											}
										},
										title:{
											offsetCenter:[0,'-20%']
										},
										data: [{value: FlowDataReal, name: '出口利用率'}]
									}
								]
							};

							if(null!=exportPlate_0){
								exportPlate_0.setOption(exportPlateOption);
							}

							if(null!=exportPlate_1){
								exportPlate_1.setOption(exportPlateOption);
							}


							//更新折线图
							var exportLine_0 = charMap.get("exportLine_"+name+"_0");
							var exportLine_1 = charMap.get("exportLine_"+name+"_1");

							var timeArray = [];
							var inflowArray = [];
							var outflowArray = [];
							if(exportFlow.graph!=null){
								for(var j=0;j<exportFlow.graph.xAxis.length;j++){
									timeArray.push(exportFlow.graph.xAxis[j]);
								}
								var xArray1 = exportFlow.graph.data[0].data;
								for(var q=0;q<xArray1.length;q++){
									outflowArray.push(xArray1[q]);
								}
								var xArray2 = exportFlow.graph.data[1].data;
								for(var q=0;q<xArray2.length;q++){
									inflowArray.push(xArray2[q]);
								}
							}

							var exportLineOption = {
								title: {
									text: ''
								},
								tooltip : {
									trigger: 'axis'
								},
								grid: {
									left: '3%',
									right: '4%',
									bottom: '3%',
									top: '3%',
									containLabel: true
								},
								xAxis : [
									{
										type : 'category',
										boundaryGap : false,
										data : timeArray
									}
								],
								yAxis : [
									{
										type : 'value',
										axisLabel : {
											formatter : function (value, index) {
												if(index==0){
													return "";
												}
												return transformFlow(value);
											}
										}
									}
								],
								series : [
									{
										symbolOffset:[0,'10%'],
										name:'流入',
										type:'line',
										stack: '流入',
										areaStyle: {normal: {}},
										data:inflowArray
									},
									{
										symbolOffset:[0,'10%'],
										name:'流出',
										type:'line',
										stack: '流出',
										areaStyle: {normal: {}},
										data:outflowArray
									}

								],
								color:['#00adec','#8cd6f6']
							};

							if(null!=exportLine_0){
								exportLine_0.setOption(exportLineOption);
							}
							if(null!=exportLine_1){
								exportLine_1.setOption(exportLineOption);
							}
						}
					}
				});

			}, 60*1000);


		}
	});
}