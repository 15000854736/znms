<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
 <table class="nms_mod_list">
      <tr>
        <th class="wid_90"><label class="checkbox" for="checkbox1">
          <input type="checkbox" data-toggle="checkbox" value="1" id="allCheck" onclick="checkAll();"></label>
        </th>
        <th>索引</th>
        <th>状态</th>
        <th>描述</th>
        <th>名称</th>
        <th>别名</th>
        <th>类型</th>
        <th>速度</th>
        <th>硬件地址</th>
        <th>IP地址</th>
      </tr>
      <c:forEach items="${pager.rows }" var="inter">
		  <tr>
		    <td>
		    <label class="checkbox">
             <input type="checkbox" data-toggle="checkbox" name="indexs" value="${inter.index}" onclick="checkIsAllSelect();">
            </label>
		    </td>
	    	<td align="center">${inter.index }</td>
		    <td align="center">${inter.status }</td>
		    <td align="center">${inter.desc }</td>
		    <td align="center">${inter.name}</td>
		    <td align="center">${inter.alias}</td>
		    <td align="center">${inter.type}</td>
		    <td align="center">${inter.speed}</td>
		    <td align="center">${inter.mac}</td>
		    <td align="center">${inter.ip}</td>
		  </tr>
	  </c:forEach>
    </table>
			 <div class="page_mod_box">
			 	<div style="float:left;">
				    设备接口信息(共${pager.total}条)
				 </div>   
					 <a href="javascript:selectInterfaceByPageNumber(1,${pager.totalpage})">首页</a>
					 <a href="javascript:selectInterfaceByPageNumber(${pager.current-1},${pager.totalpage})">上一页</a>
					 
					 <!-- 分页 计算-->
				  <c:set  var="showPages" value="5" ></c:set>
				  <c:set var="lastPageNumber" value="${pager.totalpage }"></c:set>
				  <c:if test="${showPages>pager.totalpage}">
				      <c:set  var="showPages" value="${pager.totalpage }" ></c:set>
				  </c:if>
				  <c:set var="startShowPageNumber" value="${pager.current-(showPages/2) }"></c:set>
				  <c:if test="${startShowPageNumber> lastPageNumber - showPages + 1}">
				    <c:set var="startShowPageNumber" value="${lastPageNumber - showPages + 1 }"></c:set>
				  </c:if>
				  <c:if test="${startShowPageNumber<1 }">
				  	<c:set var="startShowPageNumber" value="1"></c:set>
				  </c:if>
				 <c:set var="end" value="${startShowPageNumber+showPages-1 }"></c:set>
			      <!-- 分页 计算-->
			       
				<c:forEach var="currentPage" begin="${startShowPageNumber }" end="${end}"> 
				 	<a href="javascript:selectInterfaceByPageNumber(${currentPage },${pager.totalpage})"  <c:if test="${currentPage==pager.current }"> class="page_hov"</c:if>   >${currentPage }</a>
				 </c:forEach>
					 <a href="javascript:selectInterfaceByPageNumber(${pager.current+1},${pager.totalpage})">下一页</a> 
					 <a href="javascript:selectInterfaceByPageNumber(${pager.totalpage},${pager.totalpage})">未页</a>
				 </div>
