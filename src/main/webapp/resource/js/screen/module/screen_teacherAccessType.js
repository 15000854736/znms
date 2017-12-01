  function showFullScreen_teacherAccessType(){
        getTeacherAccessTypeData();
        refreshHander_teacherAccessType = setInterval(function(){
        	getTeacherAccessTypeData();
        }, 60000);
    }
    
    function getTeacherAccessTypeData(){
    	$.ajax({
    		type:'POST',
    		url:path+"/screen/module/teacherAccessType/getTeacherType",
    		dataType: "json",
    		success:function(data){
    			$("#teacherUl").html("");
    			var countMap = data.accessTypeCount;
    			var i = 0;
    			var colorMap = {};
    			var pieArray = [];
    			var percentList = data.percent;
    			for(var key in countMap){
    				var html ="";
    				var item;
    				var ydata = parseFloat(percentList[i]);
    				var textValue = percentList[i]+"%";
    				if(i==0){
    					colorMap[key] = "rgba(251,176,59,0.8)";
    					html = "<li class='t_teacher_a'>";
    					item = { x: "1", text:textValue , y: ydata,fill:"#ff9933"};
    				}else if(i==1){
    					colorMap[key] = "rgba(41,171,226,0.8)";
    					html = "<li class='t_teacher_b'>";
    					item = { x: "1", text:textValue , y: ydata,fill:"#3399cc"};
    				}else if(i==2){
    					colorMap[key] = "rgba(10,113,188,0.8)";
    					item = { x: "1", text:textValue , y: ydata,fill:"#0066cc"};
    					html = "<li class='t_teacher_c'>";
    				}else{
    					colorMap[key] = "rgba(140,198,63,0.8)";
    					html = "<li class='t_teacher_d'>";
    					item = { x: "1", text:textValue , y: ydata,fill:"#99cc33"};
    				}
    				pieArray.push(item);
    				$("#teacherUl").append(html+"<p class='font_2vw blod led_fonts'>"+countMap[key]+"</p><p>"+key+"</p></li>");
    				i++;
    			}
    			
    			//绘制饼图
    			$("#teacherPie").ejChart({
    	            commonSeriesOptions: {
    	               labelPosition: 'inside',
    	    	        marker:
    	    			{
    	    				dataLabel: 
    	    				{ 
    	    					shape: 'none', 
    	    					visible: true, 
    	    					textPosition: 'top', 
    	                      	font :{color : "#FFFFFF",size:"10",fontWeight : "bold"},
    	    					border: { width: 1},
    	    				},
    	                }
    	            },
    	    		series: 
    	    		[
    	                {
    	                  points: pieArray,
    	    				type: 'doughnut', 
    	    				doughnutCoefficient: 0.5, 
    	    				doughnutSize: 1.0,
    	                  	explodeOffset:0
    	                }
    	            ], 
    	            enable3D: true,	
    	    		enableRotation: false,	
    	            depth: 20,
    	            tilt: -30,
    	            rotation: -30,
    	            perspectiveAngle: 0,                       
//    	            size: { height: '195px', width:'185px'},               
    	            legend: { visible: false}
    	        });
    			
    			//绘制曲线图
    			var timeArray = [];
    			var serie = [];
    			var timeList = data.timeArray;
    			for(var index in timeList){
    				timeArray.push(timeList[index]);
    			}
    			var labelColor = [];
    			var trendMap = data.trendData;
    			var colorArray = [];
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
    			var teacherLineChart = echarts.init(document.getElementById('teacherLineDiv'));
    			teacherLineChart.setOption({
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
    
