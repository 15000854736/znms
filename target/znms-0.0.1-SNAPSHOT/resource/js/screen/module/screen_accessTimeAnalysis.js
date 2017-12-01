/**
 * Created by shenqilei on 2016/11/30.
 */
function refreshAccessTimeAnalysis(url){
	$.ajax({
        type:'POST',
        url:url,
        cache:false,
        success:function(data){
        	//console.log(data);
        	//每天
        	$("#teacherWireless_pc").html((data.teacher.pcOnlineTimePerDay).toFixed(2));
        	$("#studentWireless_mobile").html((data.student.wirelessOnlineTimePerDay).toFixed(2));
        	$("#teacherWireless_mobile").html((data.teacher.wirelessOnlineTimePerDay).toFixed(2));
        	$("#studentWireless_pc").html((data.student.pcOnlineTimePerDay).toFixed(2));
        	//每次
        	$("#every_time_teacherWireless_pc").html((data.teacher.pcOnlineTimePerRecord).toFixed(2));
        	$("#every_time_studentWireless_mobile").html((data.student.wirelessOnlineTimePerRecord).toFixed(2));
        	$("#every_time_teacherWireless_mobile").html((data.teacher.wirelessOnlineTimePerRecord).toFixed(2));
        	$("#every_time_studentWireless_pc").html((data.student.pcOnlineTimePerRecord).toFixed(2));
        	pieChart(data);
        	categoryChart(data);
        }
	});
}
function pieChart(data){
	var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='300';
		height='260';
	}else if(winWidth==1600 && winHeight==900){
		width='200';
		height='190';
	}else if(winWidth==1366 && winHeight==768){
		width='180';
		height='180';
	}else if(winWidth==1280 && winHeight==1024){
		width='180';
		height='230';
	}
	var teacherPcPer=((data.teacher.pcOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var studentPcPer=((data.student.pcOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var teacherWirelessPer=((data.teacher.wirelessOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var studentWirelessPer=((data.student.wirelessOnlineTimePerDay/data.totalCountOnline)*100).toFixed(1);
	var points=[];
	if(teacherPcPer>0){
		points.push({ x: "老师有线", text: teacherPcPer+"%", y: data.teacher.pcOnlineTimePerDay ,fill:'#FFCC00'});
	}
	if(studentPcPer>0){
		points.push({ x: "学生有线", text: studentPcPer+"%", y: data.student.pcOnlineTimePerDay ,fill:'#00FFFF'});
	}
	if(teacherWirelessPer>0){
		points.push( { x: "老师无线", text: teacherWirelessPer+"%", y: data.teacher.wirelessOnlineTimePerDay ,fill:'#66CC33'});
	}
	if(studentWirelessPer>0){
		points.push( { x: "学生无线", text: studentWirelessPer+"%", y: data.student.wirelessOnlineTimePerDay , fill:'#FF6600'});
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
					font :{color : "rgb(255,255,255)",size:"1",fontWeight : "bold"},
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

function categoryChart(data){
	var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='700';
		height='160';
	}else if(winWidth==1600 && winHeight==900){
		width='523';
		height='175';
	}else if(winWidth==1366 && winHeight==768){
		width='455';
		height='145';
	}else if(winWidth==1280 && winHeight==1024){
		width='426';
		height='170';
	}
 	var teacher_pcOnlineTimePerRecord=Math.ceil(data.teacher.pcOnlineTimePerRecord);
 	var student_wirelessOnlineTimePerRecord=Math.ceil(data.student.wirelessOnlineTimePerRecord);
 	var teacher_wirelessOnlineTimePerRecord=Math.ceil(data.teacher.wirelessOnlineTimePerRecord);
 	var student_pcOnlineTimePerRecord=Math.ceil(data.student.pcOnlineTimePerRecord);
 	
	 $("#every_timr_analysis").ejChart(
		{
		//Initializing Primary X Axis	
        primaryXAxis:
        {                                
			//valueType: 'category',
            //labelFormat:"MMM",
            //title: { text: "Minerals" },
         	 visible : false,
         	 crossesAt: 90,
            majorGridLines: { visible: true, color: "#BACF25", opacity: 1 }
        },
		
		//Initializing Primary Y Axis	
        primaryYAxis:
        {         
         	crossesAt: 1,
			crossesInAxis: 'secondaryYAxis',
           	visible : false,
           //rangePadding : "none" ,
			majorGridLines: { visible: true, color: "#BACF25", opacity: 1}
        },
      	
        
		//Initializing Common Properties for all the series
		commonSeriesOptions:
		{
          	//columnFacet: "cylinder", 
          
			//tooltip: { visible: false, format: "#series.name#<br/>#point.x# : #point.y#mg" },  
		   columnWidth: 0.2,
           type: "column"     ,
           dataLabel: 
			{ 
				shape: 'none', 
				visible: false, 
				textPosition: 'top', 
              	font :{color : "rgb(17,93,163)",size:"20",fontWeight : "bold"},
				border: { width: 1},
			},
        },
        columnDefinitions :[{ columnWidth : 220 }] , 
		//Initializing Series
        series: 
			[                                                                                   
                {
                    points: [{ x: "老师有线", y: teacher_pcOnlineTimePerRecord,fill:'#FFCC00' },
                             { x: "老师无线", y: teacher_wirelessOnlineTimePerRecord,fill:'#40B811'},
                             { x: "学生无线", y: student_wirelessOnlineTimePerRecord,fill:'#FF6600' },
                             { x: "学生有线", y: student_pcOnlineTimePerRecord,fill:'#00F9FB'}
                             ],  
							 
					name: 'Minerals Content in Apple'
                }            
           ],
		
		//Enabling 3D Chart
        enable3D: true,	
		enableRotation: false,			
        depth: 100,
        wallSize: 2,
        //tilt:20,
        //rotation: -3,
        tilt:0,
        rotation: 0,
        perspectiveAngle: 90,
        sideBySideSeriesPlacement: true,
		//load: "loadTheme",
        //title: { text: 'Fruits Nutrients' },            
        isResponsive: false,
        //canResize: true,
        //size: { height: '100'},
        size: { height: height,width:width},
        legend: { visible: false}
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
        	console.log(data);
        	var wireFlow=(data[0].wireFlow/data[0].flowCount)*100;
        	var wirelessFlow=(data[0].wirelessFlow/data[0].flowCount)*100;
        	timeAxisData=data[0].timeAxis;
        	$("#zlog_data_li").children().filter('li').remove();
        	var warn=1;
        	for(var key in data[0].userGroupFlow){
        		if(warn>8){break;}
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
			width='460';
			height='400';
		}else if(winWidth==1600 && winHeight==900){
			width='380';
			height='290';
		}else if(winWidth==1366 && winHeight==768){
			width='340';
			height='240';
		}else if(winWidth==1280 && winHeight==1024){
			width='240';
			height='260';
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
			        left: '5%',
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
			            itemStyle : {normal : {lineStyle:{color:'rgba(41,171,226, 0.1)'}}},
			            areaStyle: {normal: {color:'rgba(41,171,226, 0.8)'}},
			            smooth:true,
			            symbol:'none',
			            data:pcFlowTrendData
			        },
			        {
			            name:'无线',
			            type:'line',
			            //itemStyle : {normal : {lineStyle:{color:'rgba(251,176,59, 0.5)'}}},
			            //areaStyle: {normal: {color:'rgba(251,176,59, 0.5)'}},
			            itemStyle : {normal : {lineStyle:{color:'rgba(251,176,59, 0.1)'}}},
			            areaStyle: {normal: {color:'rgba(251,176,59, 0.8)'}},
			            smooth:true,
			            symbol:'none',
			            data:wirelessFlowTrendData
			        }]
			};
	    myChart.setOption(option);
}