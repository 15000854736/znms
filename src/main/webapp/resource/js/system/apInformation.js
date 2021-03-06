	
//新建区域
function createRegion(){
	$(".errMsg").hide();
	$("#layer1").css('display','table'); 
}

function refreshMap(obj){
	ctx.clearRect(0,0,10000,10000);
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
 		if(obj=="ap"){
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
}

//规划区域
function drawApRegion(){
	$("div[name='pointerDiv']").each(function(){
		$(this).remove();
	});
	refreshMap(type);
	regionName = "";
	$(".errMsg").hide();
	var apRegionName = $("#apRegionName").val();
	//区域名不能为空
	if(!checkisNotNull(apRegionName)){
		$("#nameNullError").show();
		return;
	}
	//区域名不能重复
	var nameRepeat = false;
	$.ajax({
		type:'post',
		async:false,
		url:path+"/apInformation/checkRegionNameRepeat",
		data:{"apRegionName":apRegionName},
		dataType:'json',
		success:function(data){
			if(data){
				$("#nameRepeatError").show();
				nameRepeat = true;
			}
		},
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
		}
	});
	if(!nameRepeat){
		$("#layer1").css("display","none");
		clickType = "1";
		regionName = apRegionName;
	}
}

function closeAddRegionTable(){
	$("#layer1").hide();
}

//区域管理
function regionManage(){
	$(".errMsg").hide();
	$("#layer2").css("display", "table");
	findRegion(1);
}

function findRegion(current){
	var apRegionName = $("#regionManageName").val();
	$.ajax({
		type:'post',
		async:false,
		url:path+"/apInformation/findRegion",
		data:{"current":current,"apRegionName":apRegionName},
		dataType:'html',
		success:function(html){
			$("#regionDiv").html("");
			$("#regionDiv").html(html);
		},
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
		}
	});
}


/**
 * 区域翻页
 */
function selectRegionByPageNumber(current,totalpage){
   if(current>totalpage){
   	return;
   }
   if(current<1){
   	return;
   }
  //查询
   findRegion(current);
}

//删除区域
function deleteRegion(obj){
	var check = $(obj).parent().prev().children().first();
	var isCascadeDelete = 0;
	var apRegionName = $("#regionManageName").val();
	if(check.is(':checked')){
		isCascadeDelete = 1;
	}else{
		isCascadeDelete = 0;
	}
	var apRegionUuid = $(obj).next().val();
	$.ajax({
		type:'post',
		async:false,
		url:path+"/apInformation/deleteRegion",
		data:{"apRegionUuid":apRegionUuid, "apRegionName":apRegionName, "isCascadeDelete":isCascadeDelete},
		dataType:'html',
		success:function(html){
			$("#regionDiv").html("");
			$("#regionDiv").html(html);
		},
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
		}
	});
}


//通过区域设置ap、主机位置
function relateAp(){
	toastr.clear();
	var apRegionUuid = $("#dummyApRegion").val();
	if(apRegionUuid == '-1'){
		toastr.error("请选择区域");
		return;
	}
	
	var check = false;
	var indexes = "";
	$(".custom-checkbox:gt(0)").each(function(){
		if($(this).is(":checked")){
			check = true;
			var obj = $(this).parent().parent().next().find("input").val();
			indexes = indexes + "_" +obj;
		}
	});
	if(!check){
		toastr.error("请选择AP或者主机");
		return;
	}
	$.ajax({
		type:'post',
		url:path+"/apInformation/changePositionByRegion",
		data:{"indexes":indexes, "apRegionUuid":apRegionUuid,"deviceType":type},
		dataType:'json',
		success:function(data){
			toastr.success("设置成功！");
		},
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
		}
	});
	
}


function closeRegionManage(){
	$("#layer2").css("display","none");
	window.location.href = path+"/apInformation?type="+type;
}

//直接设置ap位置
function changePosition(){
	toastr.clear();
	var check = false;
	var indexes = new Array();
	$(".custom-checkbox:gt(0)").each(function(){
		if($(this).is(":checked")){
			check = true;
			indexes.push($(this).parent().parent().next().find("input").val());
		}
	});
	if(!check){
		toastr.error("请选择AP或者主机或者新建区域");
		return;
	}
	if(xAxis==""){
		toastr.error("请选择AP或者主机对应的位置");
		return;
	}
	
	$.ajax({
		type:'post',
		url:path+"/apInformation/changePosition",
		data:{"indexes":indexes.join("_"), "apAxis":xAxis+","+yAxis, "devcieType":type},
		dataType:'json',
		success:function(data){
			toastr.success("设置成功！");
		},
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
		}
	});
}

function addRegion(){
	if(!checkisNotNull(regionName)){
		toastr.error("区域名不能为空");
		return;
	}
	$("#apRegionCoordinate").val(apRegionCoordinate);
	$.ajax({
		type:'post',
		async:false,
		url:path+"/apInformation/addRegion",
		data:{"apRegionCoordinate":apRegionCoordinate,"apRegionName":regionName},
		dataType:'json',
		success:function(data){
			if(data){
				window.location.href = path+"/apInformation?type="+type;
			}
		},
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
		}
	});
}

function changeTab(obj){
	$(".points_img").each(function(){
		$(this).remove();
	});
	refreshMap(obj);
	$("#apTbody").html("");
	if(obj=="ap"){
		type = "ap";
		$("#apTab").attr("class","tab_hov");
		$("#hostTab").removeAttr("class");
		$("#hostRelate1").hide();
		$("#apRelateTr1").show();
		$("#hostTypeTr").hide();
	}else{
		type="host";
		$("#hostTab").attr("class","tab_hov");
		$("#apTab").removeAttr("class");
		$("#hostRelate1").show();
		$("#apRelateTr1").hide();
		$("#hostTypeTr").show();
	}
	$("#dummyApRegion").val("-1");
	searchAp();
}