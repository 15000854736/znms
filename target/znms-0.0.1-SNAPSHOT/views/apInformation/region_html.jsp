<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
 <table class="nms_mod_list">
      <tr>
        <th>区域名</th>
        <th>清除AP位置</th>
        <th>操作</th>
      </tr>
      <c:forEach items="${pager.rows }" var="region">
		  <tr>
	    	<td align="center">${region.apRegionName }</td>
	    	<td align="center">
		    	<input type="checkbox" data-toggle="checkbox" required/>
			</td>
		    <td align="center">
		    <div class="nms_table_icon" onclick="deleteRegion(this);"><span class="fui-cross-circle"></span></div>
<!-- 		    <button type="button" class="btn btn-primary btn-xs" onclick="deleteRegion(this);">删除</button> -->
		    <input type="hidden" value="${region.apRegionUuid }">
		    </td>
		  </tr>
	  </c:forEach>
    </table>
			 <div class="page_mod_box">
			 	 
					 <a href="javascript:selectRegionByPageNumber(1,${pager.totalpage})">首页</a>
					 <a href="javascript:selectRegionByPageNumber(${pager.current-1},${pager.totalpage})">上一页</a>
					 
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
				 	<a href="javascript:selectRegionByPageNumber(${currentPage },${pager.totalpage})"  <c:if test="${currentPage==pager.current }"> class="page_hov"</c:if>   >${currentPage }</a>
				 </c:forEach>
					 <a href="javascript:selectRegionByPageNumber(${pager.current+1},${pager.totalpage})">下一页</a> 
					 <a href="javascript:selectRegionByPageNumber(${pager.totalpage},${pager.totalpage})">未页</a>
				 </div>
				 
				 <div class="mod_buttom_box">
				 <button type="button" class="btn btn-default btn-sm" onclick="closeRegionManage();">返回</button>
				 </div>
