/*
 * HTML5 crop image in polygon shape
 * author: netplayer@gmx.com
 * file : crop.js
   github version
 */
var pointArray = [];
var inludeSize = 10;
var apRegionCoordinate = "";

var xAxis="";
var yAxis="";
var width_before = "";
var height_before = "";
var height_real = "";
var width_real = "";
var percent = "";
var apMapPath = "";
var isCircle = false;
var imageObj = new Image();

var regionName = "";
var flag = 0;
var ctx;

//地图点击事件类型  1：新建区域   2：直接设置ap位置
var clickType = "";
$(document).ready(function() {
    var points = [];//holds the mousedown points
    var canvas = document.getElementById('myCanvas');
    this.isOldIE = (window.G_vmlCanvasManager);
    $(function() {
            if (this.isOldIE) {
                G_vmlCanvasManager.initElement(myCanvas);
            }
             ctx = canvas.getContext('2d');

            function init() {
                canvas.addEventListener('mousedown', mouseDown, false);
                canvas.addEventListener('mouseup', mouseUp, false);
                canvas.addEventListener('mousemove', mouseMove, false);
            }

            // Draw  image onto the canvas
            imageObj.onload = function() {
        		width_before = imageObj.width;
        		height_before = imageObj.height;
        		 
        		 height_real = $(window).height()-70;
        		 width_real = $(window).width()-$(".nms_left").width()-$(".nms_ap_width").width()-20;
        		 var widthPercent = width_before/width_real;
        		 var hegihtPercent = height_before/height_real;
        		 if(widthPercent >=hegihtPercent){
        			 percent = widthPercent;
        		 }else{
        			 percent = hegihtPercent;
        		 }
        		 $("#image").css("width", width_before/percent);
         		 $("#image").css("height", height_before/percent);
         		var canvas=document.getElementById('myCanvas');
         		canvas.width=width_before/percent;
         		canvas.height=height_before/percent;
         		if(type=="ap"){
        			 $("#apListDiv").css("height", height_before/percent-30-$("#acTd").height()-
        					 $("#apPositionTd").height()-5-
        					 ($("#createRegionTd").height()-10)*2-140-48);
        		}else{
        			 $("#apListDiv").css("height", height_before/percent-30-$("#acTd").height()-
        					 $("#apPositionTd").height()-5-
        					 ($("#createRegionTd").height()-10)*2-140-48-37);
        		}
            	ctx.drawImage(imageObj, 0, 0, width_before/percent, height_before/percent);

            };
            imageObj.src = "image/"+apMapPath;
            imageObj.id="image";
            

            // Switch the blending mode
            ctx.globalCompositeOperation = 'destination-over';

            //mousemove event
            $('#myCanvas').mousemove(function(e) {
                    ctx.beginPath();
                    for(var i=0;i<pointArray.length;i++){
                    	var zuobiao = pointArray[i].split("-");
                    	if((parseInt(zuobiao[0])+inludeSize*percent>e.offsetX && parseInt(zuobiao[0])-inludeSize*percent<e.offsetX) && 
                    			(parseInt(zuobiao[1])+inludeSize*percent>e.offsetY && parseInt(zuobiao[1])-inludeSize*percent<e.offsetY)){
                    		e.offsetX = parseInt(zuobiao[0]);
                    		e.offsetY = parseInt(zuobiao[1]);
                    		break;
                    	}
                	}
                    $('#posx').html(e.offsetX);
                    $('#posy').html(e.offsetY);
            });
            
            //mousedown event
            $('#myCanvas').mousedown(function(e) {
            	toastr.clear();
            	if(clickType ==""){
            		toastr.error("请选择AP、主机或者新建区域");
            		return;
            	}
            	var offset = $("#myCanvas").offset();
            	var x,y;
     	       xAxis = (e.pageX - offset.left)*percent;
     	       yAxis = (e.pageY - offset.top)*percent;
            	if(clickType=="1"){
            		//规划区域
                     if (e.which == 1) {
                         var pointer = $('<span class="spot pointercss">').css({
                             'position': 'absolute',
                             'background-color': '#00F',
                             'width': '5px',
                             'height': '5px',
                             'top': e.pageY,
                             'left': e.pageX
                         });
                         
                         $('.pointercss').mousedown(function(e) {
                                 if (e.which == 1) {
                                     //ctx.globalCompositeOperation = 'destination-out';
                                     ctx.globalCompositeOperation = 'source-over';
                                     var oldposx = $('#oldposx').html();
                                     var oldposy = $('#oldposy').html();
                                     var posx = $('#posx').html();
                                     var posy = $('#posy').html();
                                     ctx.beginPath();
                                     ctx.moveTo(oldposx, oldposy);
                                     var circle = false;
                                     if(pointArray.length>=3){
                                     	circle = true;
                                     	flag++;
                                     	if(circle && flag==1){
                                     		addRegion();
                                     	}
                                     }
                                     if (oldposx != '' && circle) {
                                         ctx.lineTo(posx, posy);
                                         ctx.strokeStyle = 'red'; 

                                         ctx.stroke();
                                     }
                                     $('#oldposx').html(e.offsetX);
                                     $('#oldposy').html(e.offsetY);
                                 }
                                 $('#posx').html(e.offsetX);
                                 $('#posy').html(e.offsetY);
                                 $(".pointercss").unbind("mousedown");
                         });
                         
                         var isInclude = false;
                         for(var i=0;i<pointArray.length;i++){
                         	var zuobiao = pointArray[i].split("-");
                         	if((parseInt(zuobiao[0])+inludeSize>e.offsetX && parseInt(zuobiao[0])-inludeSize<e.offsetX) && (parseInt(zuobiao[1])+inludeSize>e.offsetY && parseInt(zuobiao[1])-inludeSize<e.offsetY)){
                         		e.offsetX = parseInt(zuobiao[0]);
                         		e.offsetY = parseInt(zuobiao[1]);
                         		isInclude = true;
                         		if(i==0 && apRegionCoordinate !="" && pointArray.length>=3){
                         			addRegion();
                        	}
                         		break;
                         	}
                     	}
                        
                         if(!isInclude){
                             points.push(e.pageX, e.pageY);
                         	pointArray.push(e.offsetX+"-"+e.offsetY);
                         	apRegionCoordinate += xAxis+"-"+yAxis+",";
                         }
                         ctx.globalCompositeOperation = 'source-over';
                         var oldposx = $('#oldposx').html();
                         var oldposy = $('#oldposy').html();
                         var posx = $('#posx').html();
                         var posy = $('#posy').html();
                         ctx.beginPath();
                         ctx.moveTo(oldposx, oldposy);
                         var circle = false;
                         if(pointArray.length>=3){
                         	circle = true;
                         }
                         if (oldposx != '' && (!isInclude || (isInclude && circle))) {
                             ctx.lineTo(posx, posy);
                             ctx.strokeStyle = 'red'; 
                             ctx.stroke();
                         }
                         $('#oldposx').html(e.offsetX);
                         $('#oldposy').html(e.offsetY);
                     }
                     if(!isInclude){
                     	$(document.body).append(pointer);
                     }
                     $('#posx').html(e.offsetX);
                     $('#posy').html(e.offsetY);
                     //判断是否规划区域完成
            	}else{
            		//设置ap位置
            	$("#myCanvas span").each(function(){
        				$(this).remove();
        			});
        	      x = e.pageX;
        	      y = e.pageY;
        	      $('<span class="points_img">').css({
        	          left: x,
        	          top: y
        	        }).appendTo($("#myCanvas"));
        	      
        	      changePosition();
            	}
	           
                    
            });

    });

});


