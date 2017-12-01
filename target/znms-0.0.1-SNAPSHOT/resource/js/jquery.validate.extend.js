/**
* jQuery Validate扩展验证方法   
*/
$(function(){
    // IP地址验证   
    jQuery.validator.addMethod("ip", function(value, element) {    
      var ipArray = value.split(',');
      var flag = true;
      for(var i = 0;i<ipArray.length;i++){
    	  flag = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test($.trim(ipArray[i]));
    	  if(!flag){
    		  return false;
    	  }
      }
      return this.optional(element) || flag;    
    }, "请填写正确的IP地址。");
    
    jQuery.validator.addMethod("isIp", function(value, element) {
    	return this.optional(element) ||
    	/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value);
    },"请填写正确的IP地址。");
    
    // IP地址唯一性验证   
    jQuery.validator.addMethod("duplicateIpCheck", function(value, element) {    
      var ipArray = value.split(',');
      var flag = true;
      ipArray.sort();
      for(var i = 0; i < ipArray.length - 1; i++) {
         if ($.trim(ipArray[i]) == $.trim(ipArray[i+1])) {
             flag = false;
             break;
          }
      }
      if(flag){
    	  var contextPath = window.location.pathname.split("/")[1];
    	  $.ajax({
    		 type:"post",
    		 url:"/"+contextPath+"/SystemManage/deviceManage/validIp",
    		 data:{'ip':value,'originalIp':$('#originalIp').val()},
    		 // 必须同步
    		 async:false,
    		 success:function(result){
    			 flag = result;
    		 }
    	  });
      }
      return this.optional(element) || flag;    
    }, "所填IP存在重复");
    
    // 大于0
    jQuery.validator.addMethod("largerThanZero", function(value, element){
    	return this.optional(element) || ($.trim(value) != "" && value > 0) || $.trim(value) == "";
	},"只能输入正整数")
	
	jQuery.validator.addMethod("largerThanDesign", function(value,element,param){
    	var small = param[0];
    	var big = param[1];
    	return this.optional(element) || ($.trim(value) != "" && value >= small && value<=big) || $.trim(value) == "";
	},"只能输入指定范围内数字")
	
    jQuery.validator.addMethod("isNum", function(value, element){
    	var exp = /^\+?[0-9]*$/;
    	return exp.test($.trim(value));
	},"只能输入数字")
    
    // 确认密码校验
    jQuery.validator.addMethod("confirmPassword", function(value, element){
    	return this.optional(element) || $("[name='newPassword']").val() == value;
    },"两次输入的密码不一致");

    // 浮点数是否符合指定格式
    jQuery.validator.addMethod("checkFormat", function(value, element, param){
    	var flag = true;
    	
    	var integer = param.split(",")[0];
    	var decimal = param.split(",")[1];
    	if(value.indexOf(".") == -1){
    		flag = !isNaN(value) && value.toString().length <= integer;
    	} else{
    		flag = !isNaN(value.split(".")[0]) && value.split(".")[0].toString().length <=integer
    					&& !isNaN(value.split(".")[1]) && value.split(".")[1].length <= decimal;
    	}
    	return this.optional(element) || flag;
    },"格式不正确");
    
    // 有条件的必须check
    jQuery.validator.addMethod("conditionRequired", function(value, element, param){
    	var flag = true;
    	if($("#"+param).val() == ""){
    		flag = $.trim(value) != "";
    	}
    	return flag;
    },"不能为空");
    
    // 有条件的必须check2
    jQuery.validator.addMethod("conditionRequired2", function(value, element, param){
    	var flag = true;
    	if($("#"+param).val() == "2"){
    		flag = $.trim(value) != "";
    	}
    	return flag;
    },"不能为空");
    
    // 用户名check
    jQuery.validator.addMethod("checkUserName", function(value, element, param){
    	if(value=='')return true;
    	if(/^\+?[0-9]*$/.test(value)){
    		return false;
    	} else {
    		return /^[0-9a-zA-Z_]+$/.test(value);
    	}
    },"用户名");
    
    // 手机号check
    jQuery.validator.addMethod("checkPhoneNumber", function(value, element, param){
    	if(value=='')return true;
		return /^[1]([358][0-9]{1})[0-9]{8}$/.test(value);
    },"用户名");
    
    // 邮箱check
    jQuery.validator.addMethod("checkEmail", function(value, element, param){
    	if(value=='')return true;
		return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value);
    },"用户名");
    
    // 日期小于
    jQuery.validator.addMethod("availableLarger", function(value, element, param){
    	var availableTime = $("#mergeAvailableTime").val() == ""?0:$.trim($("#mergeAvailableTime").val());
    	var availableTimeUnit = $("#dummyAvailableTimeUnit").val();
    	if(availableTimeUnit==1){
    		availableTime = availableTime * 365 * 24 * 60;
    	} else if(availableTimeUnit==2){
    		availableTime = availableTime * 30 * 24 * 60;
    	} else if(availableTimeUnit==3){
    		availableTime = availableTime * 24 * 60;
    	} else if(availableTimeUnit==4){
    		availableTime = availableTime * 60;
    	}
    	var limitTime = $("#mergeLimitTime").val() == ""?0:$.trim($("#mergeLimitTime").val());
    	var limitTimeUnit = $("#dummyLimitTimeUnit").val();
    	if(limitTimeUnit==1){
    		limitTime = limitTime * 365 * 24 * 60;
    	} else if(limitTimeUnit==2){
    		limitTime = limitTime * 30 * 24 * 60;
    	} else if(limitTimeUnit==3){
    		limitTime = limitTime * 24 * 60;
    	} else if(limitTimeUnit==4){
    		limitTime = limitTime * 60;
    	}
    	
    	return availableTime>=limitTime;
    },"不能为空");
    
    // 角色名重复check
    jQuery.validator.addMethod("roleNameDuplicateCheck", function(value, element, param){
		  var contextPath = window.location.pathname.split("/")[1];
		  var flag = true;
		  $.ajax({
			 type:"post",
			 url:"/"+contextPath+"/rolePermission/checkRole",
			 data:{'roleName':value,'roleUuid':$('#formRoleUuid').val()},
			 // 必须同步
			 async:false,
			 success:function(result){
				 flag = result;
			 }
		  });
    	return flag;
    },"不能为空");
    
    // 角色名重复check
    jQuery.validator.addMethod("roleNameDuplicateCheck", function(value, element, param){
		  var contextPath = window.location.pathname.split("/")[1];
		  var flag = true;
		  $.ajax({
			 type:"post",
			 url:"/"+contextPath+"/rolePermission/checkRole",
			 data:{'roleName':value,'roleUuid':$('#formRoleUuid').val()},
			 // 必须同步
			 async:false,
			 success:function(result){
				 flag = result;
			 }
		  });
    	return flag;
    },"不能为空");
    
    // 角色名重复check
    jQuery.validator.addMethod("userDuplicateCheck", function(value, element, param){
		  var contextPath = window.location.pathname.split("/")[1];
		  var flag = true;
		  $.ajax({
			 type:"post",
			 url:"/"+contextPath+"/userManage/checkKeyValue",
			 data:{'keyValue':value,'userUuid':$('#userUuid').val()},
			 // 必须同步
			 async:false,
			 success:function(result){
				 flag = result;
			 }
		  });
    	return flag;
    },"不能为空");
    
    // 金币限制重复check
    jQuery.validator.addMethod("goldLimitDuplicateCheck", function(value, element, param){
		  var contextPath = window.location.pathname.split("/")[1];
		  var limitTargetValue = "";
		  if($("#dummyLimitTarget").val()==1){
			  limitTargetValue = $("#dummySchool").val();
		  } else if($("#dummyLimitTarget").val()==2){
			  limitTargetValue = $("#dummyZid");
		  }
		  var flag = true;
		  $.ajax({
			 type:"post",
			 url:"/"+contextPath+"/gold/goldLimit/checkExists",
			 data:{'limitTarget':$("#dummyLimitTarget").val(),
				 		'limitTargetValue':limitTargetValue,
				 		'goldLimitUuid':$("#baseInfoDiv").find("input[name='goldLimitUuid']").val()},
			 // 必须同步
			 async:false,
			 success:function(result){
				 flag = result;
			 }
		  });
  	return flag;
  },"不能为空");
});