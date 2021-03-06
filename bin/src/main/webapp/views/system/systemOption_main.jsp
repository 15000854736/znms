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

<script src="<%=path %>/js/system/systemOption.js"></script>
<script>
var path = "<%=path %>";

	$(document).ready(function(){
		<c:if test="${not empty update }">
			toastr.clear();
			toastr.success("更新成功");
		</c:if>
		
		
		//系统日志刷新周期
		var flushCyclesource = [{text:'Never',value:'Never'},{text:'1分钟',value:'1分钟'},{text:'2分钟',value:'2分钟'},{text:'5分钟',value:'5分钟'},{text:'10分钟',value:'10分钟'}];
		$("#dummyFlushCycle").jqxDropDownList({
			source : flushCyclesource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
		$("#dummyFlushCycle").val($("#flushCycle").val());
		
		//日志保留时间
		var logRemainTimeSource = [{text:'Indefinate',value:'Indefinate'},{text:'1天',value:'1天'},
		                           {text:'2天',value:'2天'},{text:'3天',value:'3天'},{text:'4天',value:'4天'},
		                           {text:'5天',value:'5天'},{text:'1周',value:'1周'},{text:'2周',value:'2周'},
		                           {text:'1月',value:'1月'},{text:'2月',value:'2月'},{text:'3月',value:'3月'},
		                           {text:'4月',value:'4月'},{text:'5月',value:'5月'},{text:'6月',value:'6月'},
		                           {text:'1年',value:'年'}];
		$("#dummyLogRemainTime").jqxDropDownList({
			source : logRemainTimeSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
		$("#dummyLogRemainTime").val($("#logRemainTime").val());
			
		$("#LiTypeUl li").click(function(){
		     var $li = $(this);
		     var liId = $li.attr("id");
		     //先移除li选中样式
		     $("#LiTypeUl li").each(function(){
		       $(this).removeClass("tab_hov");
		     });
		     //首页配置
		     if(liId=="1"){
		       $("div[id^='liContent']").hide();  
		       $("#liContent1").show();
		       $li.addClass("tab_hov");
		     }
		     //邮件
		     if(liId=="3"){
		       $("div[id^='liContent']").hide();  
		       $("#liContent3").show();
		       $li.addClass("tab_hov");
		     }
		   //报警阀值
		     if(liId=="4"){
		       $("div[id^='liContent']").hide();  
		       $("#liContent4").show();
		       $li.addClass("tab_hov");
		     }
		   //系统日志
		     if(liId=="5"){
		       $("div[id^='liContent']").hide();  
		       $("#liContent5").show();
		       $li.addClass("tab_hov");
		     }
		   //杂项
		     if(liId=="6"){
		       $("div[id^='liContent']").hide();  
		       $("#liContent6").show();
		       $li.addClass("tab_hov");
		     }
		     
	   });
		
		
		$("#disableAllThresholdValue").change(function(){
			if($('#disableAllThresholdValue').is(':checked')){
				 $('#disableAllThresholdValue').val(true);
			  }else{
				  $('#disableAllThresholdValue').val(false);
			  }
		});
		
		$("#useStatus").change(function(){
			if($('#useStatus').is(':checked')){
				 $('#useStatus').val(true);
			  }else{
				  $('#useStatus').val(false);
			  }
		});
		
		if('${systemOptionData.disableAllThresholdValue }'=='true'){
			$("#disableAllThresholdValue").parent().removeClass("switch-animate switch-off").addClass("switch-on switch-animate");
		}else{
			$("#disableAllThresholdValue").parent().removeClass("switch-animate switch-on").addClass("switch-off switch-animate");
		}
		
		if('${systemOptionData.useStatus }'=='true'){
			$("#useStatus").parent().removeClass("switch-animate switch-off").addClass("switch-on switch-animate");
		}else{
			$("#useStatus").parent().removeClass("switch-animate switch-on").addClass("switch-off switch-animate");
		}
		
		$("#memoryTr").show();
		$("#wirelessTr").show();
		$("#exportLinkTr").show();
		
		$.ajax({
			url:path+"/systemOption/getCpuTemplate",
			type:"POST",
			success:function(data){
				var obj = eval('(' + data + ')');
				$("#cpuTable tr:gt(0)").remove("");
				for(var i=0;i<obj.length;i++){
					var cpuTemplate = obj[i];
					$("#cpuTable").append("<tr><td>"+cpuTemplate.graphTemplate.graphTemplateName+"</td>"+
							"<td><div class='nms_table_icon'>"+
							"<span class='fui-cross-circle' onclick='deleteCpu(this);'>"+
							"</span><input type='hidden' value='"+cpuTemplate.cpuTemplateUuid+"' /></div></td></tr>");
				}
			}
		});
		
		$.ajax({
			url:path+"/systemOption/getMemoryTemplate",
			type:"POST",
			success:function(data){
				var obj = eval('(' + data + ')');
				$("#memoryTable tr:gt(0)").remove("");
				for(var i=0;i<obj.length;i++){
					var memoryTemplate = obj[i];
					$("#memoryTable").append("<tr><td>"+memoryTemplate.graphTemplate.graphTemplateName+"</td>"+
							"<td><div class='nms_table_icon'>"+
							"<span class='fui-cross-circle' onclick='deleteMemory(this);'>"+
							"</span><input type='hidden' value='"+memoryTemplate.memoryTemplateUuid+"' /></div></td></tr>");
				}
			}
		});
		
		//显示无线统计配置主页面数据
		$.ajax({
			url:path+"/systemOption/getWireless",
			type:"POST",
			success:function(data){
				var obj = eval('(' + data + ')');
				$("#wirelessTable tr:gt(0)").remove("");
				for(var i=0;i<obj.length;i++){
					var wireless = obj[i];
					$("#wirelessTable").append("<tr><td>"+wireless.host.hostName+"</td>"+
							"<td>"+wireless.apTemplate.graphTemplateName+"</td>"+
							"<td>"+wireless.userTemplate.graphTemplateName+"</td>"+
							"<td><div class='nms_table_icon'>"+
							"<span class='fui-cross-circle' onclick='deleteWireless(this);'>"+
							"</span><input type='hidden' value='"+wireless.wirelessStatisticalConfigurationUuid+"' /></div></td></tr>");
				}
			}
		});
		
		//显示出口链路主页面数据
		$.ajax({
			url:path+"/systemOption/getExportLink",
			type:"POST",
			success:function(data){
				var obj = eval('(' + data + ')');
				$("#exportTable tr:gt(0)").remove("");
				for(var i=0;i<obj.length;i++){
					var exportLink = obj[i];
					$("#exportTable").append("<tr><td>"+exportLink.host.hostName+"</td>"+
							"<td>"+exportLink.graph.graphName+"</td>"+
							"<td>"+exportLink.maxBandWidth+"</td>"+
							"<td>"+exportLink.exportLinkDescription+"</td>"+
							"<td><div class='nms_table_icon'>"+
							"<span class='fui-cross-circle' onclick='deleteExportLink(this);'>"+
							"</span><input type='hidden' value='"+exportLink.exportLinkUuid+"' /></div></td></tr>");
				}
			}
		});
	});
	
</script>

</head>

<body>

		<!-- 添加cpu模板：start -->
		<div class="layer_box" style="display:none;" id="layer1">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer1.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加CPU模板</span>
				</h1>
				<form id="cpuForm" action="${pageContext.request.contextPath }/systemOption/addCpuTemplate" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10" id="cpuWindow">
						<tr>
							<td class="layer_table_left bold">CPU模板</td>
							<td>
								<div id="dummyCpuTemplate"></div>
								<input type="hidden" name="graphTemplateUuid" id="cpuGraphTemplateUuid">
								<span class="errMsg" id="cpuTemplateNull">不能为空</span>
								<span class="errMsg" id="cpuTemplateRepeat">已存在该CPU模板</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitCpuForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeCpuTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加cpu模板：end -->
	
	<!-- 添加内存模板：start -->
		<div class="layer_box" style="display:none;" id="layer2">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer2.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加内存统计模板</span>
				</h1>
				<form id="memoryForm" action="${pageContext.request.contextPath }/systemOption/addMemoryTemplate" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10" id="memoryWindow">
						<tr>
							<td class="layer_table_left bold">内存模板</td>
							<td>
								<div id="dummyMemoryTemplate"></div>
								<input type="hidden" name="graphTemplateUuid" id="memoryGraphTemplateUuid">
								<span class="errMsg" id="memoryTemplateNull">不能为空</span>
								<span class="errMsg" id="memoryTemplateRepeat">已存在该内存模板</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitMemoryForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeMemoryTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加内存模板：end -->
	
	
	<!-- 添加无线统计配置：start -->
		<div class="layer_box" style="display:none;" id="layer3">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer3.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加无线统计配置</span>
				</h1>
				<form id="wirelessForm" action="${pageContext.request.contextPath }/systemOption/addWireless" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10" id="wirelessWindow">
						<tr>
							<td class="layer_table_left bold">在线AP模板</td>
							<td>
								<div id="dummyWirelessApTemplate"></div>
								<input type="hidden" name="apTemplateUuid" id="apTemplateUuid">
								<span class="errMsg" id="apTemplateNull">不能为空</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">在线用户模板</td>
							<td>
								<div id="dummyWirelessUserTemplate"></div>
								<input type="hidden" name="userTemplateUuid" id="userTemplateUuid">
								<span class="errMsg" id="userTemplateNull">不能为空</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">主机</td>
							<td>
							<div id="dummyWirelessHost"></div>
							<input id="wirelessHostUuid" name=hostUuid type="hidden" id="wirelessHostUuid"/>
							<span id="hostError" class="errMsg">
								不能为空
							</span>
							<span id="wirelessRepeat" class="errMsg">
								已存在该主机该无线统计配置
							</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitWirelessForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeWirelessTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加无线统计配置：end -->
	
	<!-- 添加出口链路配置：start -->
		<div class="layer_box" style="display:none;" id="layer4">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer4.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">添加出口链路配置</span>
				</h1>
				<form id="exportLinkForm" action="${pageContext.request.contextPath }/systemOption/addExportLink" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10" id="exportLinkWindow">
						<tr>
							<td class="layer_table_left bold">主机</td>
							<td>
								<div id="dummyExportLinkHost"></div>
								<input type="hidden" name="hostUuid" id="exportLinkHostUuid">
								<span class="errMsg" id="exportLinkHostNull">不能为空</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">图形</td>
							<td>
								<div id="dummyGraph"></div>
								<input type="hidden" name="graphUuid" id="graphUuid">
								<span class="errMsg" id="graphNull">不能为空</span>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">出口链路描述</td>
							<td>
							<input type="text" id="exportLinkDescription" name="exportLinkDescription" type="text" maxlength="255" class="form-control input-sm unfilter"/>
							</td>
						</tr>
						<tr>
							<td class="layer_table_left bold">最大带宽(bps)</td>
							<td>
							<input type="text" id="maxBandWidth" name="maxBandWidth" type="text" class="form-control input-sm unfilter"/>
							<span id="maxBandWidthNullError" class="errMsg">
								不能为空
							</span>
							<span id="maxBandWidthError" class="errMsg">
								必须是小于等于100000000的正整数
							</span>
							<span id="exportLinkRepeatError" class="errMsg">
								该主机该种出口链路配置已存在
							</span>
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitExportLinkForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeExportLinkTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加出口链路配置：end -->
	
			
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.systemOption" bundle="${menuBundle}" var="Text2" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span></p>
			
			<ul class="menu_mod_box" id="LiTypeUl">
			 <li id="1"  class="tab_hov"><a href="#">首页配置</a></li>
			 <li id="3"><a href="#">邮件</a></li>
			 <li id="4"><a href="#">报警/阀值</a></li>
			 <li id="5"><a href="#">系统日志</a></li>
			 <li id="6"><a href="#">杂项</a></li>
			 </ul>
			
			<form:form modelAttribute="systemOptionData" method="post" id="systemOptionForm" action="${pageContext.request.contextPath}/systemOption/update">
			
			<!--首页配置-->
			<div id="liContent1">
				<div class="mod_title_a font_14px text_left">CPU统计模版配置</div>
				<div class="nms_menu_c">
				 	<div class="R">
				 		<button type="button" class="btn btn-primary btn-xs" onclick="showCpuAddTable();">+添加</button>
					</div>
				</div>
				
				<table border="0" cellspacing="0" cellpadding="0" class="nms_mod_list" id="cpuTable">
					 <tr>
				        <th>CPU模版</th>
				        <th>操作</th>
				      </tr>
				</table>
				
				<div class="mod_title_a font_14px text_left">内存统计模版配置</div>
				<div class="nms_menu_c">
				 	<div class="R">
				 		<button type="button" class="btn btn-primary btn-xs" onclick="showMemoryAddTable();">+添加</button>
					</div>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_mod_list" id="memoryTable">
					<tr id="memoryTr">
						<th>内存模板</th>
				        <th>操作</th>
				     </tr>
				</table>
				
				<div class="mod_title_a font_14px text_left">无线统计配置</div>
				<div class="nms_menu_c">
				 	<div class="R">
				 		<button type="button" class="btn btn-primary btn-xs" onclick="showWirelessAddTable();">+添加</button>
					</div>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_mod_list" id="wirelessTable">
					<tr id="wirelessTr">
						<th>主机名</th>
						<th>在线AP模板</th>
						<th>在线用户模板</th>
				        <th>操作</th>
				     </tr>
				</table>
				
				<div class="mod_title_a font_14px text_left">出口链路配置</div>
				<div class="nms_menu_c">
				 	<div class="R">
				 		<button type="button" class="btn btn-primary btn-xs" onclick="showExportLinkAddTable();">+添加</button>
					</div>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_mod_list" id="exportTable">
					<tr id="exportLinkTr">
						<th>主机名</th>
						<th>图形</th>
						<th>最大带宽(bps)</th>
				        <th>出口链路描述</th>
				        <th>操作</th>
				     </tr>
				</table>
			</div>
			<!--邮件-->
			<div id="liContent3" style="display: none;">
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">SMTP服务器主机名</span></td>
						<td>
							<form:input path="smtpServerHostName" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold">SMTP端口</span></td>
						<td>
							<form:input path="smtpPort" type="text" class="form-control input-sm"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">SMTP用户名</span></td>
						<td>
							<form:input path="smtpUserName" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold">SMTP密码</span></td>
						<td>
							<form:input path="smtpPassword" type="password" class="form-control input-sm" maxlength="255"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">SMTP密码确认</span></td>
						<td>
							<form:input path="smtpPasswordConfirm" type="password" class="form-control input-sm" maxlength="255"/>
							<span id="passwordError" class="errMsg">
								两次密码不一致
							</span>
						</td>
					</tr>
				</table>
			</div>
			
			<!--报警/阀值-->
			<div id="liContent4" style="display: none;">
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">禁用所有阀值</span></td>
						<td>
                      		<form:checkbox path="disableAllThresholdValue" id="disableAllThresholdValue" data-toggle="switch" />
						</td>
						<td class="nms_data_right"><span class="bold">保留天数</span></td>
						<td>
							<form:input path="remainDays" type="text" class="form-control input-sm" />
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">主机宕机通知邮件</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="hostShutDownNotifyEmail">${systemOptionData.hostShutDownNotifyEmail }</textarea>
						</td>
						<td class="nms_data_right"><span class="bold">使用主机主题</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="useHostTheme">${systemOptionData.useHostTheme }</textarea>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">主机宕机消息</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="hostShutDownMessage">${systemOptionData.hostShutDownMessage }</textarea>
						</td>
						<td class="nms_data_right"><span class="bold">恢复主机-主题</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="recoverHostTheme">${systemOptionData.recoverHostTheme }</textarea>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">恢复主机-信息</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="recoverHostMessage">${systemOptionData.recoverHostMessage }</textarea>
						</td>
						<td class="nms_data_right"><span class="bold">报警文本信息</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="alarmText">${systemOptionData.alarmText }</textarea>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">阈值警告</span></td>
						<td>
								<textarea rows="" cols="" class="textarea_box" maxlength="1000" name="thresholdValueAlarm">${systemOptionData.thresholdValueAlarm }</textarea>
						</td>
					</tr>
				</table>
			</div>
			
			<!--系统日志-->
			<div id="liContent5" style="display: none;">
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">启用状态</span></td>
						<td>
							<form:checkbox path="useStatus" id="useStatus" data-toggle="switch"/>
						</td>
						<td class="nms_data_right"><span class="bold">按域名清除</span></td>
						<td>
							<form:input path="clearByDomainName" type="text" class="form-control input-sm" maxlength="1000"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">刷新周期</span></td>
						<td>
							<div id="dummyFlushCycle"></div>
							<form:input path="flushCycle" id="flushCycle" type="hidden" />
						</td>
						<td class="nms_data_right"><span class="bold">日志保留时间</span></td>
						<td>
							<div id="dummyLogRemainTime"></div>
							<form:input path="logRemainTime" id="logRemainTime" type="hidden" />
						</td>
					</tr>
				</table>
			</div>
			
			<!--杂项-->
			<div id="liContent6" style="display: none;">
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">TFTP服务器</span></td>
						<td>
							<form:input path="tftpServer" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold">备份路径</span></td>
						<td>
							<form:input path="backupPath" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">邮箱地址</span></td>
						<td>
							<form:input path="emailAddress" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold">备份天数</span></td>
						<td>
							<form:input path="backupDays" type="text" class="form-control input-sm"/>
						</td>
					</tr>
				</table>
			</div>
			
				<div class="mod_buttom_box">
			       	<button class="btn btn-default btn-sm" type="button"  onclick="editOption();">确定</button>
			       	<button class="btn btn-default btn-sm" type="button"  onclick="history.back()">返回</button>
				</div>
					
			</form:form>
			
			<%@ include file="../common/footer.jsp"%>
		</div>
</body>
</html>
