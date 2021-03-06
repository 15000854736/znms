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
<script src="<%=path %>/js/jquery.form.min.js"></script>
<script src="<%=path %>/js/system/systemOption.js"></script>
<script>
var path = "<%=path %>";

	$(document).ready(function(){
		<c:if test="${not empty update }">
			toastr.clear();
			toastr.success("更新成功");
		</c:if>
		
		
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
		   if(liId=="7"){
			   $("div[id^='liContent']").hide();  
		       $("#liContent7").show();
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
							"<td>"+exportLink.maxBandWidthText+"</td>"+
							"<td>"+exportLink.exportLinkDescription+"</td>"+
							"<td><div class='nms_table_icon'>"+
							"<span class='fui-cross-circle' onclick='deleteExportLink(this);'></span>"+
							"&nbsp;&nbsp;&nbsp;<a href='#' onclick='editExportLink(this);'>编辑</a>"+
							"<input type='hidden' value='"+exportLink.exportLinkUuid+"' /></div></td></tr>");
				}
			}
		});
		
	});
	
</script>

</head>

<body>
	
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
							<input type="text" id="maxBandWidth" maxlength="10" name="maxBandWidthText" type="text" class="form-control input-sm unfilter"/>
							<span id="maxBandWidthNullError" class="errMsg">
								不能为空
							</span>
							<span id="maxBandWidthError" class="errMsg">
								格式错误
							</span>
							<span id="exportLinkRepeatError" class="errMsg">
								该主机该种出口链路配置已存在
							</span>
							</td>
						</tr>
					</table>
					<input type="hidden" name="exportLinkUuid" id="exportLinkUuid">
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
	
	
	
	<!-- 添加ap地图配置：start -->
		<div class="layer_box" style="display:none;" id="layer5">
		<div class="layer_bg filter"></div>
	 	<div class="layer_center">
			<div class="layer_text">
        		<div class="layer_text_bg" >

				<h1>
				<div class="close"  onclick="layer5.style.display='none'">
					<span class="fui-cross"></span>
				</div>
				<span id="titleSpan">上传系统图片</span>
				</h1>
				<form id="apMapForm" enctype="multipart/form-data">
					<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod padding_10" id="apMapWindow" >
						<tr>
							<td class="layer_table_left bold">系统图片</td>
							<td>
							<input type="file" name="apMapFile" class="form-control input-sm unfilter">
							<input type=hidden name="imgName" id="imgName" >
							</td>
						</tr>
					</table>
				</form>
	 		<div class="mod_buttom_box">
	 			<button class="layer_input_btn" onclick="submitApMapForm();" type="button"><fmt:message key="view.button.confirm" bundle="${comBundle}"/></button>
				<button class="layer_input_btn" type="button" onclick="closeApMapTable();"><fmt:message key="view.button.back" bundle="${comBundle}"/></button>
	 		</div>
            </div>
            </div>
		</div>
	</div>
	<!-- 添加ap地图配置：end -->
	
			
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftMenu.jsp"%>
		
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.systemOption" bundle="${menuBundle}" var="Text2" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span></p>
			
			<ul class="menu_mod_box" id="LiTypeUl">
			 <li id="1"  class="tab_hov"><a href="#">首页配置</a></li>
			 <li id="3"><a href="#">邮件</a></li>
			 <li id="4"><a href="#">杂项</a></li>
			 <li id="6"><a href="#">配置备份</a></li>
			 <li id="7"><a href="#">系统对接</a></li>
			 </ul>
			
			<form:form modelAttribute="systemOptionData" method="post" id="systemOptionForm" action="${pageContext.request.contextPath}/systemOption/update">
			
			<!--首页配置-->
			<div id="liContent1">
				<!-- <div class="mod_title_a font_14px text_left">CPU统计模版配置</div>
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
				 -->
			<!-- <div class="mod_title_a font_14px text_left">无线统计配置</div>
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
				-->
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
				
				<div class="mod_title_a font_14px text_left">系统图片配置</div>
				
				<table border="0" cellspacing="0" cellpadding="0" class="nms_mod_list" id="apMapTable">
					<tr id="apMapTr">
						<th>缩略图</th>
						<th>图片名</th>
						<th>尺寸</th>
						<th>修改日期</th>
						<th>备注</th>
						<th>操作</th>
				     </tr>
				     
				     <tr>
				    	 <td><img alt="" src="<%=path %>/image/SCHOOL-BG.png" width="40" height="20"/></td>
				     	<td>学校地图</td>
				     	<td>${systemOptionData.schoolBgSize}</td>
				     	<td>${systemOptionData.schoolBgModifyDate}</td>
				     	<td>只允许PNG格式的图片</td>
				     	<td><button type="button" class="btn btn-primary btn-xs" onclick="showApMapAddTable('SCHOOL-BG');">更换</button></td>
				     </tr>
				     <tr>
				     	 <td><img alt="" src="<%=path %>/image/HOME-BG.png" width="40" height="20"/></td>
				     	<td>首页投屏背景图</td>
				     	<td>${systemOptionData.homeBgSize}</td>
				     	<td>${systemOptionData.homeBgModifyDate}</td>
			     		<td>只允许PNG格式的图片</td>
				     	<td><button type="button" class="btn btn-primary btn-xs" onclick="showApMapAddTable('HOME-BG');">更换</button></td>
				     </tr>
				     <tr>
				     	 <td><img alt="" src="<%=path %>/images/topo-bg.jpg" width="40" height="20"/></td>
				     	<td>拓扑背景图</td>
				     	<td>${systemOptionData.topolBgSize}</td>
				     	<td>${systemOptionData.topolBgModifyDate}</td>
				     	<td>只允许JPG格式的图片</td>
				     	<td><button type="button" class="btn btn-primary btn-xs" onclick="showApMapAddTable('topo-bg');">更换</button></td>
				     </tr>
				     <tr>
				     	 <td><img alt="" src="<%=path %>/image/INDEX-LOGO.png" width="40" height="20"/></td>
				     	<td>首页投屏lOGO</td>
				     	<td>${systemOptionData.indexBgSize}</td>
				     	<td>${systemOptionData.indexBgModifyDate}</td>
				     	<td>只允许PNG格式的图片</td>
				     	<td><button type="button" class="btn btn-primary btn-xs" onclick="showApMapAddTable('INDEX-LOGO');">更换</button></td>
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
						
						<td class="nms_data_right"><span class="bold">邮箱地址</span></td>
						<td>
							<form:input path="emailAddress" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
					</tr>
				</table>
			</div>
			
			<!--杂项-->
			<div id="liContent4" style="display: none;">
			
				<div class="mod_title_a font_14px text_left">报警/阈值</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">禁用所有阈值</span></td>
						<td>
                      		<form:checkbox path="disableAllThresholdValue" id="disableAllThresholdValue" data-toggle="switch" />
						</td>
						<td class="nms_data_right"><span class="bold">保留天数</span></td>
						<td>
							<form:input path="remainDays" type="text" class="form-control input-sm" />
						</td>
					</tr>
				</table>
				<div class="mod_title_a font_14px text_left">系统日志</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">启用状态</span></td>
						<td>
							<form:checkbox path="useStatus" id="useStatus" data-toggle="switch"/>
						</td>
						<td class="nms_data_right"><span class="bold">日志保留时间</span></td>
						<td>
							<div id="dummyLogRemainTime"></div>
							<form:input path="logRemainTime" id="logRemainTime" type="hidden" />
						</td>
					</tr>
				</table>
				<div class="mod_title_a font_14px text_left">星光图相关</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">用户数缩放系数</span></td>
						<td>
							<form:input path="point" type="text" class="form-control input-sm" maxlength="4"/>
						</td>	
						<td class="nms_data_right"><span class="bold">散发半径</span></td>
						<td>
							<form:input path="radius" type="text" class="form-control input-sm" maxlength="5"/>
						</td>					
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">点大小</span></td>
						<td>
							<form:input path="pointSize" type="text" class="form-control input-sm" maxlength="4"/>
						</td>	
					</tr>
				</table>
				
				<div class="mod_title_a font_14px text_left">主机相关</div>
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">学校代码</span></td>
						<td>
							<form:input path="schoolCode" type="text" class="form-control input-sm" maxlength="10" cssStyle="width:676px"/>
						</td>	
					</tr>
				</table>
			</div>
			
			
			<!--杂项-->
			<div id="liContent6" style="display: none;">
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">备份路径</span></td>
						<td>
							<form:input path="backupPath" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">备份天数</span></td>
						<td>
							<form:input path="backupDays" type="text" class="form-control input-sm"/>
						</td>
					</tr>
				</table>
			</div>
			
			<!--系统对接配置-->
			<div id="liContent7" style="display: none;">
				<table border="0" cellspacing="0" cellpadding="0" class="nms_data_tablemod">
					<tr>
						<td class="nms_data_right"><span class="bold">ZOS服务器IP</span></td>
						<td>
							<form:input path="zosIp" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold">ZOS服务器端口</span></td>
						<td>
							<form:input path="zosPort" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">ZDAS服务器IP</span></td>
						<td>
							<form:input path="zlogIp" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold">ZDAS服务器端口</span></td>
						<td>
							<form:input path="zlogPort" type="text" class="form-control input-sm"/>
						</td>
					</tr>
					<tr>
						<td class="nms_data_right"><span class="bold">Kafka服务器IP</span></td>
						<td>
							<form:input path="kafkaIp" type="text" class="form-control input-sm" maxlength="255"/>
						</td>
						<td class="nms_data_right"><span class="bold"></span></td>
						<td>
						</td>
					</tr>
				</table>
			</div>
			
				<div class="mod_buttom_box">
			       	<button class="btn btn-default btn-sm" type="button"  onclick="editOption();">确定</button>
			       	<button class="btn btn-default btn-sm" type="button"  onclick="history.back()">返回</button>
				</div>
				
				<form:input path="systemVersion" type="hidden" class="form-control input-sm"/>
			</form:form>
			
			<%@ include file="../common/footer.jsp"%>
		</div>
</body>
</html>
