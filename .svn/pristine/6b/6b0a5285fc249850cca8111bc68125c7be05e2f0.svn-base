  function showFullScreen_studentAccessType(){
        getStudentAccessTypeData();
        refreshHander_studentAccessType = setInterval(function(){
        	getStudentAccessTypeData();
        }, 60000);
    }
    
    function getStudentAccessTypeData(){
    	$.ajax({
    		type:'POST',
    		url:path+"/screen/module/studentAccessType/getStudentType",
    		dataType: "json",
    		success:function(data){
    			$("#studuentUl").html("");
    			$("#percent0").html("");
    			$("#percent1").html("");
    			$("#percent2").html("");
    			$("#percent3").html("");
    			var countMap = data.accessTypeCount;
    			var i = 0;
    			var colorMap = {};
    			var percentList = data.percent;
    			for(var index in percentList){
    				$("#percent"+index).html(percentList[index]+"%");
    			}
    			for(var key in countMap){
    				var html ="";
    				if(i==0){
    					colorMap[key] = "rgba(27,134,193,0.8)";
    					html = "<li class='s_student_c'>";
    				}else if(i==1){
    					colorMap[key] = "rgba(116,36,221,0.8)";
    					html = "<li class='s_student_d'>";
    				}else if(i==2){
    					colorMap[key] = "rgba(178,11,174,0.8)";
    					html = "<li class='s_student_a'>";
    				}else{
    					colorMap[key] = "rgba(216,22,64,0.8)";
    					html = "<li class='s_student_b'>";
    				}
    				$("#studuentUl").append(html+"<p class='font_2vw blod led_fonts'>"+countMap[key]+"</p><p>"+key+"</p></li>");
    				i++;
    			}
    			//绘制曲线图
    			var timeArray = [];
    			var serie = [];
    			var timeList = data.timeArray;
    			for(var index in timeList){
    				timeArray.push(timeList[index]);
    			}
    			var trendMap = data.trendData;
    			var colorArray = [];
    			var labelColor = [];
    			for(var key in trendMap){
    				var array = trendMap[key];
    				for(var colorKey in colorMap){
    					if(key==colorKey){
    						colorArray.push();
    						labelColor.push(colorMap[colorKey]);
    						var item={
     	        					name:key,
     	        					type:'line',
     	        					itemStyle : {normal : {lineStyle:{color:colorMap[colorKey]}}},
     	    			            areaStyle: {normal: {color:colorMap[colorKey]}},
     	        					stack: '接入数',
     	        					symbol:'none',
     	        					smooth: true,
     	        					data:array
     	        				};
    					}
    				}
    				serie.push(item);
    			}
    			var studentLineChart = echarts.init(document.getElementById('studentLineDiv'));
    			studentLineChart.setOption({
    				 title: {
    				        text: ''
    				    },
    				    tooltip : {
    				        trigger: 'axis'
    				    },
    				    grid: {
    				        left:0,
    						top:0,
    						width:'100%',
    						height:'100%',
    						show:false
    				    },
    				    xAxis : [
    				        {
    				            type : 'category',
    				            boundaryGap : false,
    				            axisLabel:{
    				            	show:false
    				            },
    				            data : timeArray
    				        }
    				    ],
    				    yAxis : {
    				            type : 'value',
	    						axisTick:{
	    							show:false
	    						},
	    						splitLine:{
	   			                 show:true,
	   			                 interval:0,
	   			                 lineStyle:{
	   			                     type:'dot',
	   			                     color:['rgba(128, 128, 128,  0.2)']
	   			                 }
	   			             	},
	    						axisLine:{
	    							lineStyle:{
	    								color:'#0066cc'
	    							}
	    						}
    				    },
    				    series : serie,
    				    color:labelColor
    			});
    		}
    	});
    }
    