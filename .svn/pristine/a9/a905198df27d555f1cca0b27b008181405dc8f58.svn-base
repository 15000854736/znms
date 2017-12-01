/**
 * Created by shenqilei on 2016/11/30.
 */
function refreshFlowAnalysis(url){
	var timeAxisData=[];
	var studentPcFlowTrend=[];
	var studentWirelessFlowTrend=[];
	var teacherFlowTrend=[];
	var teacherPcFlowTrend=[];
	$.ajax({
        type:'POST',
        url:url,
        cache:false,
        success:function(data){
        	//console.log("流量获取");
        	console.log(data);
        	//每天
        	$("#student_wireless").html(data[0].studentFlow.wirelessFlowPerDay+"MB");
        	$("#student_wireless_pc").html(data[0].studentFlow.pcFlowPerDay+"MB");
        	$("#teacher_wireless").html(data[0].teacherFlow.wirelessFlowPerDay+"MB");
        	$("#teacher_wireless_pc").html(data[0].teacherFlow.pcFlowPerDay+"MB");
        	try{
        		doughnutChart(data);
        	}catch (e) {
				console.log(e);
			}
        	
        	//每次
        	$("#student_wireless_record").html(data[0].studentFlow.wirelessFlowPerRecord);
        	$("#student_wireless_pc_record").html(data[0].studentFlow.pcFlowPerRecord);
        	$("#teacher_wireless_record").html(data[0].teacherFlow.wirelessFlowPerRecord);
        	$("#teacher_wireless_pc_record").html(data[0].teacherFlow.pcFlowPerRecord);
        	try{
        		everyTimeChart(data);
        	}catch (e) {
				console.log(e);
			}
        	try{
        		var totalCount=(data[0].studentFlow.wirelessFlowPerDay+data[0].studentFlow.pcFlowPerDay+data[0].teacherFlow.wirelessFlowPerDay+data[0].teacherFlow.pcFlowPerDay);
        		//console.log(totalCount);
        		$("#totalFlow").html(Math.ceil(totalCount/1024));
        	}catch (e) {
				console.error("Total flow calculation error");
			}
        	
        	/*if(data[0].studentPcFlowTrend>0){
        		studentPcFlowTrend.push(data[0].studentPcFlowTrend);
        	}
        	if(data[0].studentWirelessFlowTrend>0){
        		studentWirelessFlowTrend.push(data[0].studentWirelessFlowTrend);
        	}
        	if(data[0].teacherFlowTrend>0){
        		teacherFlowTrend.push(data[0].teacherFlowTrend);
        	}
        	if(data[0].teacherPcFlowTrend>0){
        		teacherPcFlowTrend.push(data[0].teacherPcFlowTrend);
        	}*/
        	flowTrend(data[0].timeAxisList,data[0].studentPcFlowTrend,data[0].studentWirelessFlowTrend,data[0].teacherFlowTrend,data[0].teacherPcFlowTrend);
        }
	});
}

function doughnutChart(data){
	var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='450';
		height='450';
	}else if(winWidth==1600 && winHeight==900){
		width='300';
		height='300';
	}else if(winWidth==1366 && winHeight==768){
		width='340';
		height='260';
	}else if(winWidth==1280 && winHeight==1024){
		width='300';
		height='270';
	}
	var student_pcFlowPerDay=Math.ceil(data[0].studentFlow.pcFlowPerDay);
	var student_wirelessFlowPerDay=Math.ceil(data[0].studentFlow.wirelessFlowPerDay);
	var teacher_wirelessFlowPerDay=Math.ceil(data[0].teacherFlow.wirelessFlowPerDay);
	var teacher_pcFlowPerDay= Math.ceil(data[0].teacherFlow.pcFlowPerDay);
	 var points=[];
	 if(data[0].studentFlow.pcFlowPerDay>0){
		 points.push({ x: "学生无线电脑", text: "学生无线电脑", y: student_pcFlowPerDay ,fill:"#0071BC"});
	 }
	 if(data[0].studentFlow.wirelessFlowPerDay>0){
		 points.push({ x: "教师无线移动", text: "教师无线移动", y:teacher_wirelessFlowPerDay ,fill:"#8CC63F"});
	 }
	 if(data[0].teacherFlow.wirelessFlowPerDay>0){
		 points.push({ x: "学生无线移动", text: "学生无线移动", y: student_wirelessFlowPerDay ,fill:"#00D5DF"});
	 }
	 if(data[0].teacherFlow.pcFlowPerDay>0){
		 points.push({ x: "教师无线电脑", text: "教师无线电脑", y: teacher_pcFlowPerDay ,fill:"#FBB03B"});
	 }
	 if(points.length==0){
		 points.push({ x: "暂无数据", text: "暂无数据", y: "0.1" });
	 }
 $("#total_flow").ejChart(
    {
        //Initializing Common Properties for all the series         
        commonSeriesOptions: 
		{
            labelPosition: 'outside',
            tooltip: { visible: true, format: "#point.x# : #point.y#%" },
	        marker:
			{
				dataLabel: 
				{ 
					shape: 'none', 
					visible: false, 
					textPosition: 'top', 
					border: { width: 1},
					connectorLine: { height: 30, stroke:'black' } 
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
				doughnutCoefficient: 0.6, 
				doughnutSize: 	0.8
            }
        ], 
		
		//Enabling 3D Chart
        enable3D: true,	
		enableRotation: false,			
        depth: 30,
        tilt: 0,
        rotation: 10,
        perspectiveAngle: 90,                       
        //canResize: true,
        load: "onchartload",                    
        size: { height: height,width:width},               
        legend: { visible: false}
    });
}
function flowTrend(timeAxisData,studentPcFlowTrend,studentWirelessFlowTrend,teacherFlowTrend,teacherPcFlowTrend){
	var myChart = echarts.init(document.getElementById("every_day_flow"));
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
			              data : timeAxisData
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
			            name:'教师有线',
			            type:'line',
			            itemStyle : {normal : {lineStyle:{color:'rgba(231,163,64,0.1)'}}},
			            areaStyle: {normal: {color:'rgba(231,163,64,0.8)'}},
			            //itemStyle : {normal : {lineStyle:{color:'red'}}},
			            //areaStyle: {normal: {}},
			            symbol:'none',
			            smooth:true,
			            data:teacherPcFlowTrend
			        },
			        {
			            name:'学生有线',
			            type:'line',
			            itemStyle : {normal : {lineStyle:{color:'rgba(0,153,204,0.1)'}}},
			            areaStyle: {normal: {color:'rgba(0,153,204, 0.8)'}},
			            //itemStyle : {normal : {lineStyle:{color:'green'}}},
			            //areaStyle: {normal: {}},
			            symbol:'none',
			            smooth:true,
			            data:studentPcFlowTrend
			        },
			        {
			            name:'学生无线',
			            type:'line',
			            itemStyle : {normal : {lineStyle:{color:'rgba(0,204,204, 0.1)'}}},
			            areaStyle: {normal: {color:'rgba(0,204,204, 0.8)'}},
			            //itemStyle : {normal : {lineStyle:{color:'blue'}}},
			            //areaStyle: {normal: {}},
			            symbol:'none',
			            smooth:true,
			            data:studentWirelessFlowTrend
			        },
			        {
			            name:'教师无线',
			            type:'line',
			            itemStyle : {normal : {lineStyle:{color:'rgba(102,204,51, 0.1)'}}},
			            areaStyle: {normal: {color:'rgba(102,204,51, 0.8)'}},
			            //itemStyle : {normal : {lineStyle:{color:'gray'}}},
			            //areaStyle: {normal: {}},
			            symbol:'none',
			            smooth:true,
			            data:teacherFlowTrend
			        }]
			};
	    myChart.setOption(option);
}
function everyTimeChart(data){
	var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='856';
		height='158';
	}else if(winWidth==1600 && winHeight==900){
		width='710';
		height='190';
	}else if(winWidth==1366 && winHeight==768){
		width='520';
		height='151';
	}else if(winWidth==1280 && winHeight==1024){
		width='520';
		height='215';
	}
	var student_wirelessFlowPerRecord=Math.ceil(data[0].studentFlow.wirelessFlowPerRecord);
	var student_pcFlowPerRecord=Math.ceil(data[0].studentFlow.pcFlowPerRecord);
	var teacher_wirelessFlowPerRecord=Math.ceil(data[0].teacherFlow.wirelessFlowPerRecord);
	var teacher_pcFlowPerRecord=Math.ceil(data[0].teacherFlow.pcFlowPerRecord);
	

 $("#every_time_flow").ejChart(
	{
		//Initializing Primary X Axis	
        primaryXAxis:
        {                                
			valueType: 'category',
            //labelFormat:"MMM",
            //title: { text: "Minerals" },
           visible : false,
            majorGridLines: { visible: true, color: "#a9a9a9", opacity: 0.12 }
        },
		
		//Initializing Primary Y Axis	
        primaryYAxis:
        {        
        	visible : false,
			majorGridLines: { visible: true, color: "#a9a9a9", opacity: 0.12 }
        },
        
		//Initializing Common Properties for all the series
		commonSeriesOptions:
		{
          columnWidth: 0.6,
          columnFacet: "cylinder",
			//tooltip: { visible: true, format: "#series.name#<br/>#point.x# : #point.y#mg" },                
            type: "column"                                                               
        },
        
		//Initializing Series
		series: 
		[                                                                                   
            {
                points: [{ x: "学生无线", y: student_wirelessFlowPerRecord ,fill:"#5AB7BD"},
                         { x: "学生有线", y: student_pcFlowPerRecord ,fill:"#14B3DD"},
                         { x: "老师无线", y: teacher_wirelessFlowPerRecord,fill:"#AAC413"},
                         { x: "老师有线", y: teacher_pcFlowPerRecord ,fill:"#F0D102"} ]

            }                           
        ],
      columnDefinitions :[{ columnWidth : 800 }] , 
		
		//Enabling 3D Chart
        enable3D: true,	
		enableRotation: true,			
        depth: 100,
        wallSize: 2,
        tilt: 0,
        rotation: 0,
        perspectiveAngle: 90,
        sideBySideSeriesPlacement: false,
		load: "loadTheme",       
        isResponsive: false,
        canResize: false,
        size: { height: height,width:width},
        legend: { visible: false}
	});
}

