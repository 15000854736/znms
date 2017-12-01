	function findPieDataByPriority(){
		piePriority = $("#piePriority option:selected").val();
		piePriorityHost = $("#piePriorityHost option:selected").val();
		piePriorityInsertTimeFrom = $("#piePriorityInsertTimeFrom").val();
		piePriorityInsertTimeTo = $("#piePriorityInsertTimeTo").val();
		//获取按级别日志
		$.ajax({
			url:path+"/systemLogStatistics/findPieData",
			type:"POST",
			dataType:"json",
			data:{"id":piePriority, "hostId":piePriorityHost, "insertTimeFrom":piePriorityInsertTimeFrom, "insertTimeTo":piePriorityInsertTimeTo, "type":1},
			success:function(data){
				$("#piePrioritySort li").remove();
				var legendDataArr = [];
				var seriesArr = [];
			  for(var i=0; i< data.length;i++){
                	 var element ={
                             name: data[i].name,
                             value:data[i].value
                         };
                    legendDataArr.push(data[i].name);
                    seriesArr.push(element);
                    $("#piePrioritySort").append("<li><i>"+(parseInt(i)+1)+"</i>"+data[i].name+"<div class='R'>"+data[i].value+"</div></li>");
                }

				
				//填入数据
                piePriorityChart.setOption({
					legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: legendDataArr
				    },
				    series : [
						        {
						            name: '按级别',
						            type: 'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:seriesArr,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: ''
						                }
						            }
						        }
						    ]
			    });
			}
		});
	}
	
	
	function findPieDataByFacility(){
		pieFacility = $("#pieFacility option:selected").val();
		pieFacilityHost = $("#pieFacilityHost option:selected").val();
		pieFacilityInsertTimeFrom = $("#pieFacilityInsertTimeFrom").val();
		pieFacilityInsertTimeTo = $("#pieFacilityInsertTimeTo").val();
		//获取按级别日志
		$.ajax({
			url:path+"/systemLogStatistics/findPieData",
			type:"POST",
			dataType:"json",
			data:{"id":pieFacility, "hostId":pieFacilityHost, "insertTimeFrom":pieFacilityInsertTimeFrom, "insertTimeTo":pieFacilityInsertTimeTo, "type":2},
			success:function(data){
				$("#pieFacilitySort li").remove();
				var legendDataArr = [];
				var seriesArr = [];
				 for(var i=0; i< data.length;i++){
                	 var element ={
                             name: data[i].name,
                             value:data[i].value
                         };
                    legendDataArr.push(data[i].name);
                    seriesArr.push(element);
                    $("#pieFacilitySort").append("<li><i>"+(parseInt(i)+1)+"</i>"+data[i].name+"<div class='R'>"+data[i].value+"</div></li>");
                }

				
				//填入数据
                pieFacilityChart.setOption({
					legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: legendDataArr
				    },
				    series : [
						        {
						            name: '按级别',
						            type: 'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:seriesArr,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: ''
						                }
						            }
						        }
						    ]
			    });
				
			}
		});
	}
	
	
	function findLineData(){
		linePriority = $("#linePriority option:selected").val();
		lineFacility = $("#lineFacility option:selected").val();
		lineHost = $("#lineHost option:selected").val();
		//获取按级别日志
		$.ajax({
			url:path+"/systemLogStatistics/findLineData",
			type:"POST",
			dataType:"json",
			data:{"priorityId":linePriority, "facilityId":lineFacility, "hostId":lineHost,},
			success:function(data){
				var xAxisDataArr = [];
				var seriesDataArr = [];
                for(var i=0; i< data.length;i++){
                	 xAxisDataArr.push(data[i].name);
                	 seriesDataArr.push(data[i].value);
                }
				
				//填入数据
                lineChart.setOption({
                	title: {
				        text: ''
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['']
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    yAxis : [
				        {
				        	name: '日志数量(条)',
				            type : 'value'
				        }
				    ],
            	  xAxis : [
					        {
					        	name:'时间',
					            type : 'category',
					            boundaryGap : false,
						            data : xAxisDataArr
					        }
					    ],
				    series : [
						        {
						            name:'日志数量',
						            type:'line',
						            stack: '总量',
						            areaStyle: {normal: {}},
						            data:seriesDataArr
						        }
						    ]
			    });
				
			}
		});
	}