<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<style type="text/css">
</style>

<script>
var xAxis="";
var yAxis="";
var width_before = "";
var height_before = "";
var height_real = "";
var width_real = "";
var percent = "";
	$(document).ready(function(){
		
		var screenImage = $("#image");
		var theImage = new Image();
		theImage.src = screenImage.attr("src");
		width_before = theImage.width;
		height_before = theImage.height;
		 
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
		 $("#apListDiv").css("height", height_before/percent-40-$("#acTd").height()-10-$("#apNameTd").height()-10-$("#searchButtonTd").height()-10-40)
		//查找ac
		searchAc();
		
		//查找ap
		searchAp();
		
		$('#image').on('click', setRedBall());
	});
	
	//查找ac
	function searchAc(){
		$.ajax({
			type:'post',
			async:false,
			url:"${pageContext.request.contextPath}/host/findAcJson",
			dataType:'json',
			success:function(data){
				$("#dummyAc").jqxDropDownList({
					source : data,
					displayMember : 'text',
					valueMember : 'value',
					selectedIndex : 0,
					width : 100
				});
			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
	
	//查找ap
	function searchAp(){
		var acIp = $("#dummyAc").val();
		var apName = $("#apName").val();
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/apInformation/findApByCondition",
			data:{"acIp":acIp, "apName":apName},
			dataType:'json',
			success:function(data){
				$("#apTbody").html("");
				for(var i=0;i<data.length;i++){
					$("#apTbody").append("<tr><td><label class='checkbox'>"+
							"<input type='checkbox' data-toggel='checkbox' class='custom-checkbox'>"+
							"<span class='icons'><span class='icon-unchecked'></span>"+
							"<span class='icon-checked'></span></span><label><td>"+
							"<td>"+data[i].apName+"<input type='hidden' value='"+data[i].apInformationUuid+"'></td></tr>");
				}
				$(".custom-checkbox").click(function(){
					if($(this).is(":checked")){
						$(".custom-checkbox").each(function(){
							$(this).prop("checked",false);
						});
						$(this).prop("checked",true);
						//回显该ap在地图中的位置
						var obj = $(this).parent().parent().next().next().find("input").val();
						displayApPosition(obj);
					}
				});
				
			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
	
	//查找ap位置坐标
	function displayApPosition(apInformationUuid){
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/apInformation/findApAxis",
			dataType:'json',
			data:{"apInformationUuid":apInformationUuid},
			success:function(data){
				$(".points_img").each(function(){
					$(this).remove();
				});
				var offset = $("#image").offset();
				if(data.apAxis!=""){
					var arr=new Array();
					arr = data.apAxis.split(',');
					xAxis = arr[0];
					yAxis = arr[1];
					$('<span class="points_img">').css({
				          left: parseInt(arr[0])/percent+parseInt(offset.left),
				          top: parseInt(arr[1])/percent+parseInt(offset.top)
				        }).appendTo($("#imageDiv"));
				}
			}
		});
	}
	
	function setRedBall() {
	    var x, y, offset;

	    return function (e) {
	    	$("#imageDiv span").each(function(){
				$(this).remove();
			});
	      offset = $("#image").offset();
	       xAxis = (e.pageX - offset.left)*percent;
	       yAxis = (e.pageY - offset.top)*percent;
	      x = e.pageX;
	      y = e.pageY;
	      $('<span class="points_img">').css({
	          left: x,
	          top: y
	        }).appendTo($("#imageDiv"));
	      
	      changePosition();
	    }
	  }
	
	function changePosition(){
		toastr.clear();
		var check = false;
		var apInformationUuid = "";
		$(".custom-checkbox").each(function(){
			if($(this).is(":checked")){
				check = true;
				apInformationUuid = $(this).parent().parent().next().next().find("input").val();
			}
		});
		if(!check){
			toastr.error("请选择AP");
			return;
		}
		if(xAxis==""){
			toastr.error("请选择AP对应的位置");
			return;
		}
		
		$.ajax({
			type:'post',
			url:"${pageContext.request.contextPath}/apInformation/changePosition",
			data:{"apInformationUuid":apInformationUuid, "apAxis":xAxis+","+yAxis},
			dataType:'json',
			success:function(data){
				toastr.success("设置成功！");
			},
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	toastr.error(XMLHttpRequest+"="+textStatus+"="+errorThrown);
			}
		});
	}
</script>

</head>

<body>
	<!-- 头部菜单 start-->
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/leftMenu.jsp"%>
	<div class="znms_box">
		<!-- 头部菜单 end-->

		<!-- 表主体：start -->
		<div class="nms_box_center">
			<table border="0" cellspacing="0" cellpadding="0" class="nms_ap_box">
				<tr>
					<td class=" text-center">	
						<div id="imageDiv">
							<img src="images/dongbeidaxue.jpg" id="image">
						</div>
					</td>
					<td align="center" valign="top" class="nms_ap_width ">
						<table width="0" border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod  nms_ap_notable">
							<tr>
								<td class=" text_right" id="adTd">AC设备：</td>
								<td>
									<div id="dummyAc"></div>
								</td>
							</tr>
							<tr>
								<td class=" text_right" id="apNameTd">AP名称：</td>
								<td><input type="text" name="apName" id="apName" class="form-control input-sm" style="width:100px"/></td>
							</tr>
							<tr>
								<td colspan="2" id="searchButtonTd">
									<button type="button" class="btn btn-primary btn-xs" onclick="searchAp();">搜索</button>
								</td>
							</tr>

							<tr id="apListTr">
								<td colspan="2" id="apListTd">
									<div class="nms_ap_auto" id="apListDiv">
										<table class="nms_mod_list">
											<tbody id="apTbody">

											</tbody>
										</table>
									</div>

								</td>
							</tr>
						</table>

					</td>

				</tr>
			</table>
		</div>
	</div>

</body>
</html>
