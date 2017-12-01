<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../../common/include.jsp"%>
<fmt:setBundle basename="i18n/host" var="hostBundle"/>
<fmt:message var="statusUse" key="host.option.statusUse" bundle="${hostBundle}"/>
<fmt:message var="statusForbidden" key="host.option.statusForbidden" bundle="${hostBundle}"/>
<style type="text/css">
</style>
<script>
var path = "<%=path %>";
var statusSource = [{text:'${statusUse}',value:1},{text:'${statusForbidden}',value:0}];

var methodSource = [{text:'无',value:0},{text:'SNMP',value:1},{text:'Ping',value:2}];

var snmpVersionSource = [{text:'不使用',value:0},{text:'v1',value:1},{text:'v2',value:2},{text:'v3',value:3}];

var snmpAuthProtocolSource = [{text:'MD5(default)',value:'MD5(default)'},{text:'SHA',value:'SHA'}];

var snmpPrivProtocolSource = [{text:'[无]',value:'[无]'},{text:'DES(默认)',value:'DES(默认)'},{text:'AES',value:'AES'}];

var typeSource = [{text:'出口',value:1},{text:'核心',value:2},
                      		{text:'无线控制器',value:3},{text:'接入',value:4},{text:'汇聚',value:5},{text:'其他',value:6}];
                      		
var vendorCodeSource =$.parseJSON('${acSource}');
                      		

	window.onload = function(){
		if('${page}'=='detail'){
			$("#dummyStatus").jqxDropDownList({ disabled: ${disabled} }); 
			$("#vendorCode").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummyType").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummyAvailabilityMethod").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummySnmpVersion").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummySnmpAuthProtocol").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummySnmpPrivProtocol").jqxDropDownList({ disabled: ${disabled} }); 
			$("#snmpCommunity").attr("disabled",true);
			$("#snmpUserName").attr("disabled",true);
			$("#snmpPassword").attr("disabled",true);
			$("#snmpPrivPassphrase").attr("disabled",true);
			$("#snmpContext").attr("disabled",true);
		}else{
			$("#dummyStatus").jqxDropDownList({ disabled: ${disabled} }); 
			$("#vendorCode").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummyType").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummyAvailabilityMethod").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummySnmpVersion").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummySnmpAuthProtocol").jqxDropDownList({ disabled: ${disabled} }); 
			$("#dummySnmpPrivProtocol").jqxDropDownList({ disabled: ${disabled} }); 
			$("#snmpCommunity").attr("disabled",false);
			$("#snmpUserName").attr("disabled",false);
			$("#snmpPassword").attr("disabled",false);
			$("#snmpPrivPassphrase").attr("disabled",false);
			$("#snmpContext").attr("disabled",false);
		}
	}  
	$(document).ready(function(){
		$("#acvendor").hide();
		$("#dummyStatus").jqxDropDownList({
			source : statusSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
		$("#vendorCode").jqxDropDownList({
			source : vendorCodeSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 136
		});
		
		$("#dummyType").jqxDropDownList({
			source : typeSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 0,
			width : 136
		});
		
		
		$("#dummyAvailabilityMethod").jqxDropDownList({
			source : methodSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 2,
			width : 136
		});
		
		$("#dummySnmpVersion").jqxDropDownList({
			source : snmpVersionSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 2,
			width : 136
		});
		
		$("#dummySnmpAuthProtocol").jqxDropDownList({
			source : snmpAuthProtocolSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
		
		$("#dummySnmpPrivProtocol").jqxDropDownList({
			source : snmpPrivProtocolSource,
			displayMember : 'text',
			valueMember : 'value',
			selectedIndex : 1,
			width : 136
		});
		
		if('${page}'=='add'){
			$("#dummyStatus").val(1);
			$("#dummyAvailabilityMethod").val(2);
			$("#dummySnmpVersion").val(2);
			$("#addTitle").show();
			$("#editTitle").hide();
			$("#detailTitle").hide();
		}else{
			$("#addTitle").hide();
			if('${page}'=='edit'){
				$("#editTitle").show();
				$("#detailTitle").hide();
				//主机ip不能修改
				$("#hostIp").attr("readonly","readonly");
			}else{
				$("#editTitle").hide();
				$("#detailTitle").show();
			}
			$("#dummySnmpVersion").val($("#snmpVersion").val());
			$("#dummyStatus").val($("#status").val());
			$("#dummyType").val($("#type").val());
			$("#dummyAvailabilityMethod").val($("#availabilityMethod").val());
			$("#dummySnmpAuthProtocol").val($("#snmpAuthProtocol").val());
			$("#dummySnmpPrivProtocol").val($("#snmpPrivProtocol").val());
		}
		
		snmpVersionChange();
		
		$(document).on("change","#dummySnmpVersion",function(event){
			//snmp版本切换事件
			snmpVersionChange();
		});
		
	});
	
	function doMerge(){
		$(".errMsg").hide();
		var commonValid = validFormOnSubmitPrefab();
		if(commonValid){
			//校验主机ip
			var hostIp = $("#hostIp").val();
			if(checkIpv4(hostIp) || checIpv6(hostIp)){
				//设置主机状态
				$("#status").val($("#dummyStatus").val());
				//设置主机状态
				$("#code").val($("#vendorCode").val());
				//设置默认接入
				$("#type").val($("#dummyType").val());
				//设置检测方法
				$("#availabilityMethod").val($("#dummyAvailabilityMethod").val());
				//设置版本
				$("#snmpVersion").val($("#dummySnmpVersion").val());
				if($("#snmpVersion").val()==3){
					//设置验证协议v3
					$("#snmpAuthProtocol").val($("#dummySnmpAuthProtocol").val());
					//设置私有协议v3
					$("#snmpPrivProtocol").val($("#dummySnmpPrivProtocol").val());
				}
				
				var formdata = $("#hostForm").serialize();
				$.ajax({
					type:'post',
					url:"${pageContext.request.contextPath}/host/merge/do",
					data:formdata,
					dataType:'json',
					success:function(data){
						if(data.result){
							window.location.href = path+"/host";
						}else{
							toastr.clear();
							toastr.error(data.msg);
						}
					}
				});
			}else{
				$("#hostIpError").show();
			}
		}
	}
	
	function snmpVersionChange(){
		if($("#dummySnmpVersion").val()==0){
			$("#v1Andv2Tr").hide();
			$("#v3Tr1").hide();
			$("#v3Tr2").hide();
			$("#v3Tr3").hide();
			$("#snmpVersionTable input[type='text']").each(function(){
				$(this).attr("disabled","disabled");
				$(this).removeAttr("required");
			});
		}else if($("#dummySnmpVersion").val()==1 || $("#dummySnmpVersion").val()==2){
			$("#v1Andv2Tr").show();
			$("#v3Tr1").hide();
			$("#v3Tr2").hide();
			$("#v3Tr3").hide();
			$("#snmpVersionTable input[type='text']").each(function(){
				$(this).attr("disabled","disabled");
				$(this).removeAttr("required");
			});
			$("#snmpCommunity").removeAttr("disabled");
			$("#snmpCommunity").attr("require",true);
		}else if($("#dummySnmpVersion").val()==3){
			$("#v1Andv2Tr").hide();
			$("#v3Tr1").show();
			$("#v3Tr2").show();
			$("#v3Tr3").show();
			$("#snmpVersionTable input[type='text']").each(function(){
				$(this).removeAttr("disabled");
				$(this).attr("required",true);
			});
			$("#snmpCommunity").attr("disabled","disabled");
			$("#snmpCommunity").removeAttr("required");
		}
	}
	
	function selectChange(){
		var type=$("#dummyType").val();
		//console.info(type);
		if(type==3){
			$("#acvendor").show();
		}else{
			$("#acvendor").hide();
		}
	}
</script>

</head>

<body>
		<%@ include file="../../common/header.jsp"%>
		<%@ include file="../../common/leftMenu.jsp"%>
		
		<div class="znms_box">
			<fmt:message key="menu.item.setting" bundle="${menuBundle}" var="Text1"/>
		    <fmt:message key="menu.item.setting.host" bundle="${menuBundle}" var="Text2" />
		    <fmt:message key="menu.item.setting.host.add" bundle="${menuBundle}" var="Text3" />
		    <fmt:message key="menu.item.setting.host.edit" bundle="${menuBundle}" var="Text4" />
		    <fmt:message key="menu.item.setting.host.detail" bundle="${menuBundle}" var="Text5" />
			<p class="page_top_menu">${Text1}&gt; <span class="bold">${Text2}</span>
			<span class="bold" id="addTitle">&gt; ${Text3}</span>
			<span class="bold" id="editTitle">&gt; ${Text4}</span>
			<span class="bold" id="detailTitle">&gt; ${Text5}</span>
			</p>
			
			<form:form action="${pageContext.request.contextPath }/host/merge/do" id="hostForm" modelAttribute="host" method="post">
				<form:input path="id" type="hidden"/>
				<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg">
					<tr>
						<th colspan="6">常规选项</th>
					</tr>
					<tr>
						<td class="table_td_bg">主机IP</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<form:input type="text" path="hostIp" id="hostIp" maxlength="150" disabled="${disabled }" class="unfilter" required="true"/>
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
							<span class="errMsg" id="hostIpError">
								<fmt:message key="view.error.unfit" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
						<td class="table_td_bg">主机名称</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<form:input type="text" path="hostName" id="hostName" maxlength="16" disabled="${disabled }" required="true" />
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
					</tr>
					<tr>
						<td class="table_td_bg">主机状态</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<div id="dummyStatus"></div>
							<form:input type="hidden" path="status" id="status" />
						</td>
						<td class="table_td_bg">主机类型</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<div id="dummyType" onchange="selectChange()"></div>
							<form:input type="hidden" path="type" id="type" />
						</td>
					</tr>
					<tr id="acvendor" >
						<td class="table_td_bg">AC品牌</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<div id="vendorCode"></div>
							<form:input type="hidden" path="acBrand" id="acBrand" />
						</td>
					</tr>
				</table>
				
				<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg">
					<tr>
						<th colspan="6">可用性选项</th>
					</tr>
					<tr>
						<td class="table_td_bg">检测方法</td>
						<td align="left" class="table_td_txtleft" colspan="5">
							<div id="dummyAvailabilityMethod"></div>
							<form:input type="hidden" path="availabilityMethod" id="availabilityMethod" />
						</td>
						
					</tr>
				</table>
				
				<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg" id="snmpVersionTable">
					<tr>
						<th colspan="6">SNMP选项</th>
					</tr>
					<tr>
						<td class="table_td_bg">版本</td>
						<td align="left" class="table_td_txtleft" colspan="4">
							<div id="dummySnmpVersion"></div>
							<form:input type="hidden" path="snmpVersion" id="snmpVersion" />
						</td>
					</tr>
					<tr id="v1Andv2Tr">
						<td class="table_td_bg">团体名</td>
						<td align="left" class="table_td_txtleft" colspan="4">
							<form:input type="text" path="snmpCommunity" id="snmpCommunity" maxlength="100" required="true"  disabled="${disabled }" cssStyle="width:670px" cssClass="unvalidate" />
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
					</tr>
					<tr id="v3Tr1">
						<td class="table_td_bg">用户名(v3)</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<form:input type="text" path="snmpUserName" id="snmpUserName" maxlength="50"  disabled="${disabled }" required="true"/>
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
						<td class="table_td_bg">密码(v3)</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<form:input type="text" path="snmpPassword" id="snmpPassword" maxlength="50"  disabled="${disabled }" required="true"/>
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
					</tr>
					<tr id="v3Tr2">
						<td class="table_td_bg">验证协议(v3)</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<div id="dummySnmpAuthProtocol"></div>
							<form:input type="hidden" path="snmpAuthProtocol" id="snmpAuthProtocol" class="unfilter"/>
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
						<td class="table_td_bg">私有密码短语(v3)</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<form:input type="text" path="snmpPrivPassphrase" id="snmpPrivPassphrase" maxlength="200"  disabled="${disabled }" required="true"/>
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
					</tr>
					<tr id="v3Tr3">
						<td class="table_td_bg">私有协议(v3)</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<div id="dummySnmpPrivProtocol"></div>
							<form:input type="hidden" path="snmpPrivProtocol" id="snmpPrivProtocol" class="unfilter"/>
						</td>
						<td class="table_td_bg">上下文</td>
						<td align="left" class="table_td_txtleft" colspan="2">
							<form:input type="text" path="snmpContext" id="snmpContext" maxlength="64"  disabled="${disabled }" required="true"/>
							<span role="requiredError" class="errMsg">
								<fmt:message key="view.error.null" bundle="${viewCommonBundle}"></fmt:message>
							</span>
						</td>
					</tr>
				</table>
				
				<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg">
					<tr>
						<th colspan="6">额外选项</th>
					</tr>
					<tr>
						<td class="table_td_bg">说明</td>
						<td align="left" class="table_td_txtleft" colspan="4">
							<form:input type="text" path="notes" maxlength="255" id="notes" disabled="${disabled }" cssStyle="width:670px" />
						</td>
					</tr>
				</table>
				
				<div class="but_mod_box">
					<c:if test="${!disabled}">
						<button class="but_mod" type="button" onclick="doMerge();"><fmt:message key="common.ui.button.save" bundle="${commonBundle}"/></button>
					</c:if>
					<button class="but_mod" type="button" onclick="history.back();"><fmt:message key="common.ui.button.return" bundle="${commonBundle}"/></button>

			     </div>
					
			</form:form>
			
			<%@ include file="../../common/footer.jsp"%>
		</div>
</body>
</html>
