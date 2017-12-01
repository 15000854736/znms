<%--
  Created by IntelliJ IDEA.
  User: shenqilei
  Date: 2016/12/9
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path=request.getContextPath();
%>
<style>
</style>
<script>


</script>
<div class="s_student_accessbox">
    <h1>学生用户接入方式统计</h1>
    <div class="s_student_access_center">
        <div class="s_student_data">
			<div class="s_student_no_a" id="percent3"></div><div class="s_student_no_b" id="percent2"></div><div class="s_student_no_c" id="percent1"></div><div class="s_student_no_d" id="percent0"></div>
        </div>
        <ul class="s_student_ulicon" id="studuentUl">
        </ul>
    </div>
    <div class="s_student_databg" id="studentLineDiv">
    </div>
</div>
