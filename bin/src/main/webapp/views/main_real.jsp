<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="common/include.jsp"%>

<script>

</script>

</head>

<body>

	<%@ include file="common/header.jsp"%>
	<%@ include file="common/leftMenu.jsp"%>
<div>
	<div>
<div class="nms_box_center">

<table class="nms_mod_table">
  <tr>
    <td align="center" valign="top" class="">
   <div class="topn_mod_box">
       <div class="nms_top_titlebox "><h1>CPU使用率统计</h1></div>
       
	   <ul class="topn_mod_ul topn_schedule_cpu">
	   <c:forEach items="${cpuMap }" var="map">
       		<li><div class="topn_number" style="height:${map.value.cpuUsagePercent }%;"><div class="topn_number_font">${map.value.cpuUsagePercent }%</div></div><div class="topn_font">${map.value.hostName }</div></li>
       </c:forEach>
	   </ul>
   </div> 
   <div class="topn_mod_box">
     <div class="nms_top_titlebox "> <h1>内存使用率统计</h1></div>
   <ul class="topn_mod_ul topn_schedule_ram">
   <li><div class="topn_number" style="height:100%;"><div class="topn_number_font">100%</div></div><div class="topn_font">N18K</div></li>
   <li><div class="topn_number" style="height:70%;"><div class="topn_number_font">70%</div></div><div class="topn_font">N18K</div></li>
   <li><div class="topn_number" style="height:60%;"><div class="topn_number_font">60%</div></div><div class="topn_font">N18K</div></li>
   <li><div class="topn_number" style="height:20%;"><div class="topn_number_font">20%</div></div><div class="topn_font">N18K</div></li>
   <li><div class="topn_number" style="height:10%;"><div class="topn_number_font">10%</div></div><div class="topn_font">N18K</div></li>
   </ul>
 
    


  </div>
</td>
    <td align="center" valign="top" class="wh_20b">  <div class="nms_top_titlebox width_100"><span class="fui-arrow-right R"></span><span class="fui-arrow-left L"></span><h1>设备</h1></div>
      <div class="list_cpudataram">
      	<c:forEach items="${deviceMap }" var="device">
      		<c:choose>
      			<c:when test="${device.key == '1' }">
      				<div class="list_modbox">
			          <div class="list_fontbox">
			            <div class="L">在线</div><div class="R">${device.value.count }</div>
			          </div>
			          <div class="list_modcolor">
			          	<div class="ram_per" style="width:${device.value.statusPercent }%"></div>
			          </div>
			        </div>
      			</c:when>
      			<c:when test="${device.key == '2' }">
      				<div class="list_modbox">
			          <div class="list_fontbox">
			            <div class="L">宕机</div><div class="R">${device.value.count }</div></div>
			          <div class="list_modcolor"><div class="data_per" style="width:${device.value.statusPercent }%"></div></div>
			        </div>
      			</c:when>
      			<c:otherwise>
      				<div class="list_modbox">
			          <div class="list_fontbox"><div class="L">其他</div><div class="R">${device.value.count }</div></div>
			          <div class="list_modcolor"><div class="cpu_per" style="width:${device.value.statusPercent }%"></div></div>
			        </div>
      			</c:otherwise>
      		</c:choose>
      	</c:forEach>
        
        </div>
      
    </td>
    <td align="center" valign="top" rowspan="2" class="wh_30b">
 <div class="nms_top_titlebox width_100"><span class="fui-arrow-right R"></span><span class="fui-arrow-left L"></span><h1>在线STA统计</h1></div>
<div class="sta_top">
<ul><li>120000<p>在线STA</p></li><li>2114<p>AP设备</p></li><li>9<p>AC设备</p></li></ul>
</div>
<div class="sta_bottom"></div>
    </td>
  </tr>
  <tr>
    <td align="center" valign="top" colspan="2"  style="height:250px">
          <div class="indexwrong_box"><h1>出口利用率</h1></div>

    </td>
    </tr>
  <tr>
    <td align="center" valign="top" colspan="3"  style="height:380px;">     <div class="indexwrong_box"><h1>出口实时流量</h1></div>	<!--  http://echarts.baidu.com/echarts2/doc/example/line.html#macarons  -->
    </td>
    </tr>
</table>
</div>
</div>
    <!-- jQuery (necessary for Flat UI's JavaScript plugins) -->
    <script src="js/vendor/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/vendor/video.js"></script>
    <script src="js/flat-ui.min.js"></script>
    <script src="js/prettify.js"></script>
    <script src="js/application.js"></script>
    <script>
      $(document).ready(function(){
        $('select[name="inverse-dropdown"], select[name="inverse-dropdown-optgroup"], select[name="inverse-dropdown-disabled"]').select2({dropdownCssClass: 'select-inverse-dropdown'});
        $('select[name="searchfield"]').select2({dropdownCssClass: 'show-select-search'});
        $('select[name="inverse-dropdown-searchfield"]').select2({dropdownCssClass: 'select-inverse-dropdown show-select-search'});
      });
    </script>
</body>

<%@ include file="common/footer.jsp"%>
</div>
</body>


</html>
