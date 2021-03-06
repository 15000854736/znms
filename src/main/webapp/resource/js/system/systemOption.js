
//提交保存系统选项
function editOption(){
	$(".errMsg").hide();
	
	//校验邮件2次密码输入是否一致
	var smtpPassword = $("#smtpPassword").val();
	var smtpPasswordConfirm = $("#smtpPasswordConfirm").val();
	$("#logRemainTime").val($("#dummyLogRemainTime").val());
	var smtpPasswordValid = true
	if(smtpPasswordConfirm != smtpPassword){
		smtpPasswordValid = false;
		$("#passwordError").show();
	}
	if(smtpPasswordValid){
		$("#systemOptionForm").submit();
	}
}

//添加cpu模板
function submitCpuForm(){
	$(".errMsg").hide();
	var graphTemplateUuid = $("#dummyCpuTemplate").val();
	if(graphTemplateUuid == "-1"){
		$("#cpuTemplateNull").show();
		return;
	}else{
		$.ajax({
			url:path+"/systemOption/checkCpuGraphTemplateRepeat",
			type:"POST",
			dataType:'json',
			data:{"graphTemplateUuid":graphTemplateUuid},
			success:function(data){
				if(!data){
					$("#cpuGraphTemplateUuid").val(graphTemplateUuid);
					$("#cpuForm").submit();
				}else{
					$("#cpuTemplateRepeat").show();
				}
			}
		});
	}
	
}

function showCpuAddTable(){
	$("#layer1").css("display","table");
	$(".errMsg").hide();
	//查找所有图形模板
	findAllGraphTemplate("dummyCpuTemplate");
}

//查找所有图形模板
function findAllGraphTemplate(id){
	$.ajax({
		url:path+"/graph/findGraphTemplateJson",
		type:"POST",
		dataType:'json',
		success:function(data){
			$("#"+id).jqxDropDownList({
				source : null,
				displayMember : 'text',
				valueMember : 'value',
				selectedIndex : 0,
				width : 250
			});
			
			$("#"+id).jqxDropDownList({
				source : data,
				displayMember : 'text',
				valueMember : 'value',
				selectedIndex : 0,
				filterable: true,
				filterPlaceHolder: "请输入关键字",
				width : 250
			});
		}
	});
}

function closeCpuTable(){
	$("#layer1").hide();
}

//删除cpu模板
function deleteCpu(obj){
	var cpuTemplateUuid = $(obj).next().val();
	$.ajax({
		url:path+"/systemOption/deleteCpuTemplate",
		type:"POST",
		dataType:'json',
		data:{"cpuTemplateUuid":cpuTemplateUuid},
		success:function(data){
			 if(data){
				 location.reload();
			 }else{
				 toastr.error("删除失败");
			 }
		}
	});
}


function showMemoryAddTable(){
	$("#layer2").css("display","table");
	$(".errMsg").hide();
	//查找所有图形模板
	findAllGraphTemplate("dummyMemoryTemplate");
}


function submitMemoryForm(){
	$(".errMsg").hide();
	var graphTemplateUuid = $("#dummyMemoryTemplate").val();
	if(graphTemplateUuid == "-1"){
		$("#memoryTemplateNull").show();
		return;
	}else{
		$.ajax({
			url:path+"/systemOption/checkMemoryGraphTemplateRepeat",
			type:"POST",
			dataType:'json',
			data:{"graphTemplateUuid":graphTemplateUuid},
			success:function(data){
				if(!data){
					$("#memoryGraphTemplateUuid").val(graphTemplateUuid);
					$("#memoryForm").submit();
				}else{
					$("#memoryTemplateRepeat").show();
				}
			}
		});
	}
}


function closeMemoryTable(){
	$("#layer2").hide();
}


//删除内存模板
function deleteMemory(obj){
	var memoryTemplateUuid = $(obj).next().val();
	$.ajax({
		url:path+"/systemOption/deleteMemoryTemplate",
		type:"POST",
		dataType:'json',
		data:{"memoryTemplateUuid":memoryTemplateUuid},
		success:function(data){
			 if(data){
				 location.reload();
			 }else{
				 toastr.error("删除失败");
			 }
		}
	});
}

function closeWirelessTable(){
	$("#layer3").hide();
}

function submitWirelessForm(){
	$(".errMsg").hide();
	var apTemplateUuid = $("#dummyWirelessApTemplate").val();
	if(apTemplateUuid=="-1"){
		$("#apTemplateNull").show();
		return;
	}
	var userTemplateUuid = $("#dummyWirelessUserTemplate").val();
	if(userTemplateUuid=="-1"){
		$("#userTemplateNull").show();
		return;
	}
	$("#wirelessHostUuid").val($("#dummyWirelessHost").val());
	//校验是否选择主机
	var hostUuid = $("#wirelessHostUuid").val();
	if(hostUuid != "-1"){
		
		$.ajax({
			url:path+"/systemOption/checkWirelessTemplateRepeat",
			type:"POST",
			dataType:'json',
			data:{"hostUuid":hostUuid,"apTemplateUuid":apTemplateUuid,"userTemplateUuid":userTemplateUuid},
			success:function(data){
				if(!data){
					$("#apTemplateUuid").val(apTemplateUuid);
					$("#userTemplateUuid").val(userTemplateUuid);
					$("#wirelessHostUuid").val(hostUuid);
					$("#wirelessForm").submit();
				}else{
					$("#wirelessRepeat").show();
					return;
				}
			}
		});
	}else{
		$("#hostError").show();
		return;
	}
}


function showWirelessAddTable(){
	$("#layer3").css("display","table");
	findAllGraphTemplate("dummyWirelessApTemplate");
	findAllGraphTemplate("dummyWirelessUserTemplate");
	//查找所用主机
	findHostJson("dummyWirelessHost");
}

//查找所有
function findHostJson(id){
	$.ajax({
		url:path+"/host/findHostJson",
		type:"POST",
		dataType:'json',
		success:function(data){
			$("#"+id).jqxDropDownList({
				source : null,
				displayMember : 'text',
				valueMember : 'value',
				selectedIndex : 0,
				width : 300
			});
			
			$("#"+id).jqxDropDownList({
				source : data,
				displayMember : 'text',
				valueMember : 'value',
				selectedIndex : 0,
				filterable: true,
				filterPlaceHolder: "请输入关键字",
				width : 300
			});
		}
	});
}

function deleteWireless(obj){
	var wirelessStatisticalConfigurationUuid = $(obj).next().val();
	$.ajax({
		url:path+"/systemOption/deleteWireless",
		type:"POST",
		dataType:'json',
		data:{"wirelessStatisticalConfigurationUuid":wirelessStatisticalConfigurationUuid},
		success:function(data){
			 if(data){
				 location.reload();
			 }else{
				 toastr.error("删除失败");
			 }
		}
	});
}

//显示添加出口链路配置窗口
function showExportLinkAddTable(){
	$("#layer4").css("display","table");
	findHostJson("dummyExportLinkHost");
	var graphSource = [{text:"请选择图形",value:"-1"}];
	//加载图形
	$("#dummyGraph").jqxDropDownList({
		source : graphSource,
		displayMember : 'text',
		valueMember : 'value',
		selectedIndex : 0,
		filterable: true,
		filterPlaceHolder: "请输入关键字",
		width : 300
	});
	$("#dummyExportLinkHost").change(function(){
		var hostUuid = $("#dummyExportLinkHost").val();
		if(hostUuid != "-1"){
			//重新加载图形
			$.ajax({
				url:path+"/graph/findGraphByHost",
				type:"POST",
				dataType:'json',
				data:{"hostUuid":hostUuid},
				success:function(data){
					$("#dummyGraph").jqxDropDownList({
						source : null,
						displayMember : 'text',
						valueMember : 'value',
						selectedIndex : 0,
						width : 300
					});
					
					$("#dummyGraph").jqxDropDownList({
						source : data,
						displayMember : 'text',
						valueMember : 'value',
						selectedIndex : 0,
						filterable: true,
						filterPlaceHolder: "请输入关键字",
						width : 300
					});
				}
			});
		}
	});
}

//关闭添加出口链路配置窗口
function closeExportLinkTable(){
	$("#layer4").hide();
	$("#edit_layer4").hide();
}

//提交出口链路配置表单
function submitExportLinkForm(){
	$(".errMsg").hide();
	//判断主机
	var hostUuid = $("#dummyExportLinkHost").val();
	if(hostUuid == "-1"){
		$("#exportLinkHostNull").show();
		return;
	}
	
	//判断图形
	var graphUuid = $("#dummyGraph").val();
	if(graphUuid == "-1"){
		$("#graphNull").show();
		return;
	}
	
	//判断最大带宽
	var maxBandWidth = $("#maxBandWidth").val();
	if(checkisNotNull(maxBandWidth)){
		//带宽小于等于100000000
		if(!maxBandWidth.match(/\d+/g)){
			$("#maxBandWidthError").show();
			return;
		}
	}else{
		$("#maxBandWidthNullError").show();
		return;
	}
	
	//判断该主机是否存在该种出口链路配置
	$.ajax({
		url:path+"/systemOption/checkExportLinkRepeat",
		type:"POST",
		dataType:'json',
		data:{"hostUuid":hostUuid, "graphUuid":graphUuid},
		success:function(data){
			if(data){
				$("#exportLinkRepeatError").show();
				return;
			}else{
				$("#exportLinkHostUuid").val(hostUuid);
				$("#graphUuid").val(graphUuid);
				$("#exportLinkForm").submit();
			}
		}
	});
}

//删除出口链路配置
function deleteExportLink(obj){
	var exportLinkUuid = $(obj).next().val();
	$.ajax({
		url:path+"/systemOption/deleteExportLink",
		type:"POST",
		dataType:'json',
			data:{"exportLinkUuid":exportLinkUuid},
		success:function(data){
			 if(data){
				 location.reload();
			 }else{
				 toastr.error("删除失败");
			 }
		}
	});
}

function editExportLink(obj){
	$("#layer4").css("display","table");
	var exportLinkUuid = $(obj).next().val();
	$.ajax({
		url:path+"/systemOption/editExportLink",
		type:"POST",
		dataType:'json',
		data:{"exportLinkUuid":exportLinkUuid},
		success:function(data){
			console.log(data);
			$("#exportLinkDescription").val(data.exportLink.exportLinkDescription)
			$("#maxBandWidth").val(data.exportLink.maxBandWidth)
			$("#exportLinkUuid").val(data.exportLink.exportLinkUuid)


			$("#dummyExportLinkHost").jqxDropDownList({
				source : data.select,
				displayMember : 'text',
				valueMember : 'value',
				selectedIndex : 1,
				width : 300
			});
			$("#dummyExportLinkHost").jqxDropDownList({ disabled: true });

			$("#dummyGraph").jqxDropDownList({
				source : data.select,
				displayMember : 'text',
				valueMember : 'value',
				selectedIndex : 0,
				filterable: true,
				filterPlaceHolder: "请输入关键字",
				width : 300
			});
			$("#dummyGraph").jqxDropDownList({ disabled: true });
		}
	});
}



function closeApMapTable(){
	$("#layer5").hide();
}	
//添加apMap
function showApMapAddTable(systemOptionKey){
	$("#imgName").val(systemOptionKey);
	$("#layer5").css("display","table");
}

function submitApMapForm(){
	$("#apMapForm").ajaxSubmit({
        type: 'post',
        url: path+"/systemOption/addApMap",
        success: function(data) {
       		toastr.clear();
        	if(data.result) {
        		location.reload();
        	} else {
        		toastr.error(data.msg);
        	}
        }  
   });
}