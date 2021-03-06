<%--
  Created by IntelliJ IDEA.
  User: shenqilei
  Date: 2016/11/30
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path=request.getContextPath();
%>
<style>
line,[opacity='0.15'] {display:none !important;}
#star_map{
    background: url("<%=path%>/image/SCHOOL-BG.png") no-repeat;
    background-size: contain;
}
</style>
<script src="<%=path%>/js/echart/echarts-all.js"></script>
<script>
    var myChart;
    var mapWidth,mapHeight,scale=1;

    function getStarMapBgScale(){
        var logo_height = ${logoHeight};
        if(logo_height>50){
            logo_height=50;
        }

        var screenWidth  = winWidth;
        var screenHeight = winHeight-logo_height;

        var starMapWidth = ${starMapBgWidth};
        var starMapHeight = ${starMapBgHeight};

        var widthScale = screenWidth/starMapWidth;
        var heightScale = screenHeight/starMapHeight;

        if(widthScale>heightScale){
            scale = heightScale;
        }else{
            scale = widthScale;
        }
    }

    //随机散列点
    //随机散列点
    function randomPoint(r, ox, oy, count){
        //r=10;
        //ox=737.0077460282914;
        //oy=486.99518701075885;
        var data = [];
        for( i = 0; i < count; i++){

            var x_direction = Math.round(Math.random());
            var y_direction = Math.round(Math.random());

            var x,y;
            if(x_direction>0){
                x = Math.floor(Math.random()*r)+1+ox
            }else{
                x = ox-(Math.floor(Math.random()*r)+1);
            }

            if(y_direction>0){
                y = Math.floor(Math.random()*r)+1+oy
            }else{
                y = oy-(Math.floor(Math.random()*r)+1);
            }
            data.unshift({x:x,y:y});
        }
        return data;
    }


    function generatePoint(apData){
        var result = new Array(2);
        var data_strong = [];
        var data_normal = [];
        var data_week = [];
        var data_user = [];
        var data_user30 = [];
        var data_user80 = [];

        for(var i=0;i<apData.length;i++){
            var x=apData[i].x*scale;
            var y=(${starMapBgHeight}-apData[i].y)*scale;
            if(apData[i].value>40){
                data_strong.push({
                    'value': apData[i].value,
                    'xAxis': x,
                    'yAxis': y
                });
            }else if(apData[i].value>10){
                data_normal.push({
                    'value': apData[i].value,
                    'xAxis': x,
                    'yAxis': y
                });
            }else{
                data_week.push({
                    'value': apData[i].value,
                    'xAxis': x,
                    'yAxis': y
                });
            }
            var random_data = randomPoint(${radius},x,y, Math.round(${countScale}*apData[i].value));
            // console.log("{name:'"+x+"',geoCoord:["+x+","+y +" ]},");
            for(var k=0;k<random_data.length;k++) {
                var xvalue=random_data[k].x;
                var yvalue=random_data[k].y;

               if(data_user80.length>=data_user30.length){
                    if(data_user30.length>=data_user.length){
                        data_user.push({
                            'value': apData[i].value,
                            'xAxis': xvalue,
                            'yAxis': yvalue
                        });
                    }else{
                        data_user30.push({
                            'value': apData[i].value,
                            'xAxis': xvalue,
                            'yAxis': yvalue
                        });
                    }
               }else{
                   data_user80.push({
                       'value': apData[i].value,
                       'xAxis': xvalue,
                       'yAxis': yvalue
                   });
               }
               // console.log(data_user80.length+"  "+data_user30.length + "   "+data_user.length);

                /*if( apData[i].value>25){

                    data_user80.push({
                        'value': apData[i].value,
                        'xAxis': xvalue ,
                        'yAxis': yvalue
                    });
                }else if(apData[i].value>5&& apData[i].value<15){
                    data_user30.push({
                        'value': apData[i].value,
                        'xAxis': xvalue,
                        'yAxis': yvalue
                    });
                }else{
                    data_user.push({
                        'value': apData[i].value,
                        'xAxis': xvalue,
                        'yAxis': yvalue
                    });
                }*/

            }
        }
        result[0] = data_strong;
        result[1] = data_normal;
        result[2] = data_week;
        result[3] = data_user;
        result[4] = data_user30;
        result[5] = data_user80;
        return result;
    }



    function refreshStarMap() {
        $.ajax({
            type:'POST',
            url:'<%=path%>/screen/module/starMap/getApData',
            cache:false,
            success:function(apData){

                var data = generatePoint(apData);

                myChart.setOption({
                    series : [
                    /*{
                        name:'data_strong',
                        type:'scatter',
                        large: true,
                        data: (function () {
                            var d = [];
                            d.push([0,0]);
                            return d;
                        })(),
                        markPoint : {
                            symbol : 'diamond',
                            symbolSize: 6,
                            large: true,
                            effect : {
                                show: true
                            },

                            data:data[0]
                        }
                    },{
                        name:'data_normal',
                        type:'scatter',
                        large: true,
                        data: (function () {
                            var d = [];
                            d.push([0,0]);
                            return d;
                        })(),
                        markPoint : {
                            symbolSize: 3,
                            large: true,
                            effect : {
                                show: true,
                            },

                            data:data[1]
                        }
                    },{
                        name:'data_week',
                        type:'scatter',
                        large: true,
                        data: (function () {
                            var d = [];
                            d.push([0,0]);
                            return d;
                        })(),
                        markPoint : {
                            symbolSize: 2,
                            large: true,
                            effect : {
                                show: true,
                            },

                            data:data[2]
                        }
                    }*/
                        {
                            name:'data_user',
                            type:'scatter',
                            large: true,
                            data: (function () {
                                var d = [];
                                d.push([0,0]);
                                return d;
                            })(),
                            markPoint : {
                                symbolSize: ${pointSize},
                                large: true,
                                effect : {
                                    show: true,
                                    period: 30,
                                    scaleSize : 2,
                                    type: 'scale',
                                    //bounceDistance: 20,
                                },

                                data:data[3]
                            }
                        }
                ]
                });


            }
        });
    }
    function refreshOnlineUser() {
        $.ajax({
            type:'POST',
            url:'<%=path%>/screen/module/starMap/getRightUpAreaData',
            cache:false,
            success:function(data){
            	//console.log(data);
            	var totalOnlineUserCount=data[0].totalOnlineUserCount;
            	
            	var apTotalOnlineNumber=data[0].apTotalOnlineNumber;
            	var apOnlineCount=data[0].apOnlineCount;
            	var wirelessUserCount=data[0].wirelessUserCount;
            	var wireUserCount=data[0].wireUserCount;
            	var wireless1x=data[0].wireless1X;
            	var wirelessPortal=data[0].wirelessPortal;
            	var wirelessTotalCount=wireless1x+wirelessPortal;
            	var android=data[0].android;
            	var ios=data[0].ios;
            	var pc=data[0].pc;
            	
            	var wiredCount=totalOnlineUserCount-wirelessTotalCount;
            	if(wiredCount<0){
            		wiredCount=0;
            	}
            	//console.log(totalOnlineUserCount +":"+ wirelessUserCount+":"+wireUserCount );
            	$("#totalOnlineUserCount").html(apTotalOnlineNumber);
            	$("#apOnlineCount").html(apOnlineCount);
            	
            	$("#wirelessRatio").html(wirelessTotalCount);
            	$("#wireRatio").html(wiredCount);
            	
            	$("#wireless1x").html(wireless1x);
            	$("#wirelessPortal").html(wirelessPortal);
            	
            	$("#androidRatio").html(android);
            	$("#iosRatio").html(ios);
            	$("#pcRatio").html(pc);
            	terminalRation(data);
            	wirelessRation(data);
            }
        });
    }
	
	
    /**
     * 全屏时调用
     */
    function showFullScreen_starMap(){
        //计算背景图缩放比例
        getStarMapBgScale();
        mapWidth = scale*${starMapBgWidth};
        mapHeight = scale*${starMapBgHeight};


        $("#star_map").height(mapHeight);
        $("#star_map").width(mapWidth);

        $.ajax({
            type:'POST',
            url:'<%=path%>/screen/module/starMap/getApData',
            cache:false,
            success:function(apData){

                var data = generatePoint(apData);

                myChart = echarts.init(document.getElementById('star_map'));

                var option = {
                    //backgroundColor: '#1b1b1b',
                    color: [
                        //'rgba(255, 255, 255, 0.8)',
                        //'rgba(229, 229, 20, 0.8)',
                        //'rgba(14, 241, 242, 0.8)',

//                        'rgba(255,255,255, 0.7)',
//                        'rgba(14, 241, 242, 0.6)',
//                        'rgba(127,255,0, 0.8)'

                        'rgba(255, 255, 255, 0.8)',
                        'rgba(14, 241, 242, 0.8)',
                        'rgba(124,252,0, 0.8)'

//                        'rgba(255, 255, 255, 0.8)',
//                        'rgba(14, 241, 242, 0.8)',
//                        'rgba(37, 140, 249, 0.8)'
                    ],
                    tooltip : {
                        show:false,
                        trigger: 'axis',
                        showDelay : 0,
                        axisPointer:{
                            show: true,
                            type : 'cross',
                            lineStyle: {
                                type : 'dashed',
                                width : 1
                            }
                        }
                    },
                    legend: {
                        show:false,
                        data:['sin','cos']
                    },
                    toolbox: {
                        show : false,
                        feature : {
                            mark : {show: true},
                            dataZoom : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis : [
                        {
                            type : 'value',
                            show:false,
                            position:'top',
                            min:0,
                            max:mapWidth
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            show:false,
                            min:0,
                            max:mapHeight,
                        }
                    ],
                    grid : {
                        x:0,
                        y:0,
                        x2:0,
                        y2:0,
                        borderWidth:0
                    },
                    series : [
                        /*{
                         name:'data_strong',
                         type:'scatter',
                         large: true,
                         data: (function () {
                         var d = [];
                         d.push([0,0]);
                         return d;
                         })(),
                         markPoint : {
                         symbol : 'diamond',
                         symbolSize: 6,
                         large: true,
                         effect : {
                         show: true,
                         },

                         data:data[0]
                         }
                         },{
                         name:'data_normal',
                         type:'scatter',
                         large: true,
                         data: (function () {
                         var d = [];
                         d.push([0,0]);
                         return d;
                         })(),
                         markPoint : {
                         symbolSize: 4,
                         large: true,
                         effect : {
                         show: true,
                         },

                         data:data[1]
                         }
                         },*//*{
                         name:'data_week',
                         type:'scatter',
                         large: true,
                         data: (function () {
                         var d = [];
                         d.push([0,0]);
                         return d;
                         })(),
                         markPoint : {
                         symbolSize: 2,
                         large: true,
                         effect : {
                         show: true,
                         },

                         data:data[2]
                         }
                         },*/
                        {
                            name:'data_user',
                            type:'scatter',
                            large: true,
                            data: (function () {
                                var d = [];
                                d.push([0,0]);
                                return d;
                            })(),
                            markPoint : {
                                symbolSize: 3,
                                large: true,
                                effect : {
                                    show: true,
                                    scaleSize:2,
                                    period:15
                                },

                                data:data[3]
                            }
                        },{
                            name: 'data_user30',
                            type: 'scatter',
                            large: true,
                            data: (function () {
                                var d = [];
                                d.push([0,0]);
                                return d;
                            })(),
                            markPoint : {
                                symbolSize: 3,
                                large: true,
                                effect : {
                                    show: true,
                                    scaleSize:2,
                                    period:15
                                },

                                data:data[4]
                            }
                        },{
                            name: 'data_user80',
                            type: 'scatter',
                            large: true,
                            data: (function () {
                                var d = [];
                                d.push([0,0]);
                                return d;
                            })(),
                            markPoint : {
                                symbol : 'diamond',
                                symbolSize: 2,
                                large: true,
                                effect : {
                                    show: true,
                                    scaleSize:2,
                                    period:15
                                },

                                data:data[5]
                            }
                        }
                    ]
                };

                myChart.setOption(option);













                /*var canvas = document.createElement("canvas");
                canvas.width =  mapWidth;
                canvas.height = mapHeight;
                var context =canvas.getContext("2d");
                context.scale(scale,scale);//设置缩放比例

                var img = new Image();
                img.onload = function() {
                    context.drawImage(img, 0, 0);
                    myChart = echarts.init(document.getElementById("star_map"));
                    //坐标点
                    var data = generatePoint(apData);
                    var option = {
                        animation:false,
                        backgroundColor: {
                            type: "pattern",
                            repeat: "no-repeat",
                            image: canvas,
                            //image: img,
                        },
                        grid: {
                            x: 0,
                            y: 0,
                            x2:mapWidth,
                            y2:mapHeight,
                            bottom:0,
                            left:0,
                            top:0,
                            right:0
                        },
                        toolbox: {
                            show : false,
                            feature : {
                                mark : {show: true},
                                dataZoom : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        xAxis : [
                            {

                                min:'0',
                                max: img.width,
                                inverse:false,
                                //axisLabel :false,
                                axisLine :false,
                                splitLine :false,
                                type : 'value',
                                scale:false
                            }
                        ],
                        yAxis : [
                            {

                                min:'0',
                                max: img.height,
                                inverse:true,
                                axisLine :false,
                                splitLine :false,
                                type : 'value',
                                scale:false
                            }
                        ],
                        series : [
                            {
                                name:'user',
                                type:'scatter',
                                large: true,
                                itemStyle: {
                                    normal: {
                                        shadowBlur: 10,
                                        shadowColor: 'rgba(14, 241, 242, 0.8)',
                                        color: 'rgba(84, 246, 56, 0.8)'
                                    }
                                },
                                symbolSize: pointSize,
                                data:data[0]
                            },{
                                name:'ap',
                                type:'effectScatter',
                                effectType:'ripple',
                                rippleEffect: {
                                    //brushType: 'stroke',
                                    brushType: 'fill',
                                    period:10,
                                    scale:5
                                },
                                itemStyle: {
                                    normal: {
                                        shadowBlur: 5,
                                        shadowColor: 'rgba(14, 241, 242, 0.8)',
                                        color: 'rgba(84, 246, 56, 0.8)'
                                    }
                                },
                                /!*itemStyle: {
                                    normal: {
                                        color: '#f4e925',
                                        shadowBlur: 10,
                                        shadowColor: '#333'
                                    }
                                },*!/
                                symbolSize:5,
                                data:data[1]
                            }
                        ],
                        itemStyle: {
                            normal: {
                                color: '#0FD6D7'
                            }
                        }
                    };
                    myChart.setOption(option);
                };
                img.src = "image/SCHOOL-BG.png";*/




				
				$.ajax({
            	type:'POST',
            	url:'<%=path%>/screen/module/starMap/getRightUpAreaData',
            	cache:false,
            	success:function(data){
            		//console.log(data);
            		var apTotalOnlineNumber=data[0].apTotalOnlineNumber;
            		var apOnlineCount=data[0].apOnlineCount;
            		var wirelessUserCount=data[0].wirelessUserCount;
            		var wireUserCount=data[0].wireUserCount;
            		var wireless1x=data[0].wireless1X;
            		var wirelessPortal=data[0].wirelessPortal;
            		var android=data[0].android;
            		var ios=data[0].ios;
            		var pc=data[0].pc;
            		
            		var totalOnlineUserCount=data[0].totalOnlineUserCount;
            		var wirelessTotalCount=wireless1x+wirelessPortal;
                	var wiredCount=totalOnlineUserCount-wirelessTotalCount;
                	if(wiredCount<0){
                		wiredCount=0;
                	}
                	
            		//console.log(totalOnlineUserCount +":"+ wirelessUserCount+":"+wireUserCount );
            		
            		$("#totalOnlineUserCount").html(apTotalOnlineNumber);
            		$("#apOnlineCount").html(apOnlineCount);
            	
            		$("#wirelessRatio").html(wirelessTotalCount);
            		$("#wireRatio").html(wiredCount);
            	
            		$("#wireless1x").html(wireless1x);
            		$("#wirelessPortal").html(wirelessPortal);
            	
            		$("#androidRatio").html(android);
            		$("#iosRatio").html(ios);
            		$("#pcRatio").html(pc);
            		var totalOnlineUserCount=data[0].totalOnlineUserCount;
            		var other=totalOnlineUserCount-android-ios-pc;
            		$("#otherRatio").html(other);
            		terminalRation(data) ;
            		wirelessRation(data);
            		}
            	});
                //设置刷新
                refreshHander_1 = setInterval(function(){
                    refreshStarMap();
                }, 60*1000);
                
                refreshHander_2 = setInterval(function(){
                    refreshOnlineUser();
                }, 60*1000);
            }
        });
    }
    
  	function terminalRation(data) {
  	  try{
		var android=data[0].android;
		var ios=data[0].ios;
		var pc=data[0].pc;
		var totalOnlineUserCount=data[0].totalOnlineUserCount;
		var other=totalOnlineUserCount-android-ios-pc;
		//var sum=data[0].android+data[0].ios+data[0].pc;
		
		var androidPercent;
		var iosPercent;
		var pcPercent;
		var otherPercent;
		try{
		androidPercent=android/totalOnlineUserCount*100;
		iosPercent=ios/totalOnlineUserCount*100;
		pcPercent=pc/totalOnlineUserCount*100;
		otherPercent=100-(androidPercent+iosPercent+pcPercent);
		}catch(err){
			console.log("计算终端比例错误");
		}
		
		var winWidth  = window.screen.width;
		var winHeight = window.screen.height;
		var width;
		var height;
		if(winWidth==1920 && winHeight==1080){
			width='310';
			height='240';
		}else if(winWidth==1600 && winHeight==900){
			width='300';
			height='210';
		}else if(winWidth==1366 && winHeight==768){
			width='255';
			height='180';
		}else if(winWidth==1280 && winHeight==1024){
			width='208';
			height='180';
		}
		
		
		var pointData=[];
		if(ios>0){
			pointData.push({ x: "IOS", text: iosPercent.toPrecision(3)+"%", y: ios ,fill:'#CDCFDE'}); 
		}
		if(android>0){
			 pointData.push({ x: "Android", text: androidPercent.toPrecision(3)+"%", y: android  ,fill:'#8DEA0D'}); 
		}
		if(pc>0){
		 	pointData.push({ x: "PC", text: pcPercent.toPrecision(3)+"%", y: pc  ,fill:'#03BDF6'});
		}
		if(other>0){
		 	pointData.push({ x: "Othre", text: otherPercent.toPrecision(3)+"%", y: other ,fill:'#FFA800'});
		}
		
		
			$("#terminalRation").ejChart(
			{   
				commonSeriesOptions: 
				{
					labelPosition: 'outside',                          
					tooltip: { visible: true, format: "#point.x# : #point.y#%" },
					marker: 
					{
						dataLabel: 
						{ 
							shape: 'none', 
							visible: true, 
							textPosition: 'top', 
							border: { width: 1}, 
							font :{color : "rgb(255,255,255)",size:"1",fontWeight : "bold"},
							connectorLine: { height: -5, stroke:"black" } 
						}
					}
				},
				
				series: 
				[
					{
						points: pointData, 
						
						//explodeIndex: 0,                              
						border: { width: 2, color: 'white' },
						type: 'doughnut', 
						//font :{color : "rgb(140,198,63)",size:"1",fontWeight : "bold"},
						//labelPosition: 'outside', 
						startAngle: 100
					}
				],
				
				//Enabling 3D Chart			
				enable3D: true,
				enableRotation:false,
				//enableRotation: false,	
				depth: 20,
				tilt: -60,
				rotation: 0,
				perspectiveAngle: 0,                      
				canResize: false,
				//load: "onchartload",                      
				//title:{text:"终端比例"},            
				size: { height: height, width:width},                     
				legend: { visible: false }
			});
			}catch(err){
				console.log("终端比例图标加载失败");
			}
			
		}
	
	function wirelessRation(data){
		
		var totalOnlineUserCount=data[0].totalOnlineUserCount;
		var wireless1x=data[0].wireless1X;
		var wirelessPortal=data[0].wirelessPortal;
		var wirelessTotalCount=wireless1x+wirelessPortal;
    	var wiredCount=totalOnlineUserCount-wirelessTotalCount;
    	if(wiredCount<0){
    		wiredCount=0;
    	}
		var wirelessUserCount=data[0].wirelessUserCount;
        
        var winWidth  = window.screen.width;
		var winHeight = window.screen.height;
		var width;
		var height;
		if(winWidth==1920 && winHeight==1080){
			width='341';
			height='177';
		}else if(winWidth==1600 && winHeight==900){
			width='261';
			height='117';
		}else if(winWidth==1366 && winHeight==768){
			width='224';
			height='110';
		}else if(winWidth==1280 && winHeight==1024){
			width='208';
			height='170';
		}
        $("#wirelessDiv").ejChart(
        {
			//Initializing Primary X Axis	
            primaryXAxis:
            {                                
				//valueType: 'category',
                //labelFormat:"MMM",
                //title: { text: "Minerals" },
             	 visible : false,
             	 crossesAt: 90,
                majorGridLines: { visible: true, color: "#BACF25", opacity: 0.1 }
            },
			
			//Initializing Primary Y Axis	
            primaryYAxis:
            {         
             	crossesAt: 1,
				crossesInAxis: 'secondaryYAxis',
               	visible : false,
               //rangePadding : "none" ,
				majorGridLines: { visible: true, color: "#BACF25", opacity: 0.1}
            },
          	
            
			//Initializing Common Properties for all the series
			commonSeriesOptions:
			{
              	columnFacet: "cylinder", 
              
				//tooltip: { visible: false, format: "#series.name#<br/>#point.x# : #point.y#mg" },                
                type: "column"     ,
               dataLabel: 
				{ 
					shape: 'none', 
					visible: true, 
					textPosition: 'top', 
                  	font :{color : "rgb(17,93,163)",size:"20",fontWeight : "bold"},
					border: { width: 1},
				},
            },
             
			//Initializing Series
			series: 
			[                                                                                   
                {
                 
                  points: [{ x: "有线", y: wiredCount,fill:'#E18D0D'},{ x: "无线", y: wirelessUserCount,fill:'#9DEA0D'}] 
				  //name: 'Minerals Content in Apple'
                }                            
            ],
			
			//Enabling 3D Chart
            enable3D: true,	
			enableRotation: false,			
            depth: 100,
            wallSize: 2,
            //tilt:20,
            //rotation: -3,
            tilt:6,
            rotation: 0,
            perspectiveAngle: 90,
            sideBySideSeriesPlacement: true,
			//load: "loadTheme",
            //title: { text: 'Fruits Nutrients' },            
            isResponsive: false,
            canResize: false,
            size: { height: height,width:width},
            legend: { visible: false, size :{width : "100%"}  }
        });
		//$("#sampleProperties").ejPropertiesPanel();
    }
</script>
<div id="star_map_container">
    <div id="star_map" class="L">

    </div>
    <table width="0" border="0" cellspacing="0" cellpadding="0" class="sm_star_right_menu">
        <tr>
            <td class="sm_width_50b sm_height_15b"  ><div class="sm_adapt_mod sm_fontcol_f60"><i class="sm_online_no"></i><p class="led_fonts sm_font_2vw" id="totalOnlineUserCount"></p><p>在线数</p></div></td>
            <td class="sm_width_50b"><div class="sm_adapt_mod sm_fontcol_9f0"><i class="sm_online_ap_no"></i><p class="led_fonts sm_font_2vw" id="apOnlineCount"></p><p>AP在线数</p></div></td>
        </tr>
        <tr>
            <td colspan="2" class="sm_height_35b">
                <h1 class="sm_title_h">有线/无线比例</h1>
                <ul class="sm_wired_wireless_b">
   					 <li>
   					 	<div style="width:5px;float:left">&nbsp;&nbsp;</div>
   					 	<div class="sm_data_amod"  id="wirelessDiv"></div>
   					 </li>
    				 <li class="wired_wireless">
    				 	<div class="sm_adapt_mod_b sm_wireless_c90 L">
    				 		<i class="sm_wireless_no"></i><p class="led_fonts sm_font_2vw" id="wireRatio"></p>
    				 	<p>有线</p>
    				 	</div>
    				 	
    				 	<div class="sm_adapt_mod_b sm_wired_9f0 L"><i class="sm_wired_no"></i>
    				 	<p class="led_fonts sm_font_2vw" id="wirelessRatio"></p>
    				 		<p>无线</p>
    				 	</div>
    				 	
    				 </li>
      			</ul>
            </td>
        </tr>
        <tr>
            <td colspan="2"  class="sm_height_15b">  <ul class="sm_wired_wireless">
                <li><div class="sm_adapt_mod sm_wired1x_0cf"><i class="sm_wired1x_no"></i><p class="led_fonts sm_font_2vw" id="wireless1x"></p><p>无线1x</p></div></li>
                <li><div class="sm_adapt_mod sm_portal_ff0"><i class="sm_portal_no"></i><p class="led_fonts sm_font_2vw" id="wirelessPortal"></p><p>无线Portal</p></div></li>
            </ul></td>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="sm_height_35b">
                <h1 class="sm_title_h">终端比例</h1>
                <div class="sm_text_center" id="terminalRation" >
                	<!--<img src="<%=path%>/images/screen/module/starMap/3.png" width="49%" height="10%" >-->
                </div>
                <ul class="sm_wired_wireless sm_terminal">
                    <li><div class="sm_adapt_mod sm_apple_ccc"><i class="sm_apple_no"></i><p class="led_fonts sm_font_2vw" id="iosRatio"></p><p>苹果</p></div></li>
                    <li><div class="sm_adapt_mod sm_android_9f0"><i class="sm_android_no"></i><p class="led_fonts sm_font_2vw" id="androidRatio"></p><p>安卓</p></div></li>
                    <li><div class="sm_adapt_mod sm_pc_0cf"><i class="sm_pc_no"></i><p class="led_fonts sm_font_2vw" id="pcRatio"></p><p>PC</p></div></li>
                    <li><div class="sm_adapt_mod sm_more_f90"><i class="sm_more_no"></i><p class="led_fonts sm_font_2vw" id="otherRatio"></p><p>其他</p></div></li>
                </ul></td>
        </tr>
    </table>
</div>


