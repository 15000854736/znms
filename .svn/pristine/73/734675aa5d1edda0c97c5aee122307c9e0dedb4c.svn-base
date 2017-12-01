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

    function generatePoint(deviceData){
    	var seriesData = [];
    	for(var key in deviceData){
    		var array = deviceData[key];
    		var data_device = [];
    		for(var index in array){
    			var x=array[index].x*scale;
                var y=(${starMapBgHeight}-array[index].y)*scale;
                data_device.push({
                    'xAxis': x,
                    'yAxis': y
                });
    		}
    		seriesData.push(  {
                name:'data_user',
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
                        show: true
                    },
                    itemStyle:{
                    	normal:{
                    		color:key
                    	}
                    },
                    data:data_device
                }
            });
    	}
    	
        return seriesData;
    }
    
    function refreshDeviceStarMap() {
        $.ajax({
            type:'POST',
            url:'<%=path%>/screen/module/deviceStarMap/getDeviceData',
            cache:false,
            dataType:'json',
            success:function(deviceData){
				
                var data = generatePoint(deviceData[0]);

                myChart = echarts.init(document.getElementById('star_map'));

                var option = {
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
                    series : data
                };

                myChart.setOption(option);
				
                //设置右侧数据
                var rightData = deviceData[1];
                for(var key in rightData){
                	var dataMap = rightData[key];
                	var up = dataMap["up"];
                	var down = dataMap["down"];
                	if(dataMap["up"] == undefined){
                		up = 0;
                	}
                	if(dataMap["down"] == undefined){
                		down = 0;
                	}
                	$("#total_"+key).html(parseInt(up)+parseInt(down));
                	$("#up_"+key).html(up);
                	$("#down_"+key).html(down);
                }
                
            }
        });
    }
	
    /**
     * 全屏时调用
     */
    function showFullScreen_deviceStarMap(){
        //计算背景图缩放比例
        getStarMapBgScale();
        mapWidth = scale*${starMapBgWidth};
        mapHeight = scale*${starMapBgHeight};

        $("#star_map").height(mapHeight);
        $("#star_map").width(mapWidth);
        refreshDeviceStarMap();
        refreshHander_1 = setInterval(function(){
        	refreshDeviceStarMap();
        }, 60000);

    }
    
	
</script>
<div id="star_map_container">
    <div id="star_map" class="L">

    </div>
    
    <table width="0" border="0" cellspacing="0" cellpadding="0" class="sm_star_right_menu">
	  <tr>
	    <td class="sm_width_50b"  >
	    	<div class="sm_adapt_modb sm_fontcol_f60">
	    		<i class="sm_online_exit"></i>
	    		<p class="led_fonts sm_font_2vw" id="total_1"></p>
	    		<p>出口</p>
	    		<ul class="online_downtime_box">
	    			<li><span id="up_1"></span><p>在线</p></li>
	    			<li><span id="down_1"></span><p>宕机</p></li>
	    		</ul>
	    	</div>
	    </td>
	    <td class="sm_width_50b">
	    	<div class="sm_adapt_modb sm_fontcol_f93">
	    		<i class="sm_online_core"></i>
	    		<p class="led_fonts sm_font_2vw" id="total_2"></p>
	    		<p>核心</p>
	    		<ul class="online_downtime_box">
		    		<li><span id="up_2"></span><p>在线</p></li>
		    		<li><span id="down_2"></span><p>宕机</p></li>
	    		</ul>
	    	</div>
	    </td>
	  </tr>
	  <tr>
	    <td class="sm_width_50b "  >
	    	<div class="sm_adapt_modb sm_fontcol_6ff">
	    		<i class="sm_online_wireless "></i>
	    		<p class="led_fonts sm_font_2vw" id="total_3"></p>
	    		<p>无线控制器</p>
	    		<ul class="online_downtime_box">
	    			<li><span id="up_3"></span><p>在线</p></li>
	    			<li><span id="down_3"></span><p>宕机</p></li>
	    			</ul>
	    	</div>
	    </td>
	    <td class="sm_width_50b">
	    	<div class="sm_adapt_modb sm_fontcol_39f">
	    		<i class="sm_online_access"></i>
	    		<p class="led_fonts sm_font_2vw" id="total_4"></p>
	    		<p>接入</p>
	    		<ul class="online_downtime_box">
	    			<li><span id="up_4"></span><p>在线</p></li>
	    			<li><span id="down_4"></span><p>宕机</p></li>
	    		</ul>
	    	</div>
	    </td>
	  </tr>
	  <tr>
	    <td class="sm_width_50b "  >
	    	<div class="sm_adapt_modb sm_fontcol_9f0">
	    		<i class="sm_online_union"></i>
	    		<p class="led_fonts sm_font_2vw" id="total_5"></p>
	    		<p>汇聚</p>
	    		<ul class="online_downtime_box">
	    			<li><span id="up_5"></span><p>在线</p></li>
	    			<li><span id="down_5"></span><p>宕机</p></li>
	    			</ul>
	    	</div>
	    </td>
	    <td class="sm_width_50b">
	    	<div class="sm_adapt_modb sm_fontcol_c6f">
	    		<i class="sm_online_other"></i>
	    		<p class="led_fonts sm_font_2vw" id="total_6"></p>
	    		<p>其他</p>
	    		<ul class="online_downtime_box">
		    		<li><span id="up_6"></span><p>在线</p></li>
		    		<li><span id="down_6"></span><p>宕机</p></li>
	    		</ul>
	    	</div>
	    </td>
	  </tr>
	
	</table>
</div>


