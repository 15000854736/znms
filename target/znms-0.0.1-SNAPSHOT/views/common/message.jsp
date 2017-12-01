<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String msg_code="";
%>
<div id="system_message">
<c:if test="${ not empty r_message['error'] }">
<c:set var="code" value="${r_message['error'] }" scope="page" />
<%
	msg_code=(String)pageContext.getAttribute("code");
System.out.println(msg_code);
%>
<div class="wrong" id="dummy" style="width:70%;margin:0 auto;">
<spring:message code="<%=msg_code %>" />
</div>
</c:if>
<c:if test="${ not empty r_message['info'] }">
<c:set var="code" value="${r_message['info'] }" scope="page" />
<%
	msg_code=(String)pageContext.getAttribute("code");
System.out.println(msg_code);
%>
<div class="info" id="dummy" style="width:70%;margin:0 auto;">
<spring:message code="<%=msg_code %>" />
</div>
</c:if>
<c:if test="${ not empty r_message['success'] }">
<c:set var="code" value="${r_message['success'] }" scope="page" />
<%
	msg_code=(String)pageContext.getAttribute("code");
%>
<div class="alert alert-success" id="tt_mm_2" style="width:70%;margin:0 auto;">
<spring:message code="<%=msg_code %>" />
</div>
</c:if>
<c:if test="${ not empty r_message['exception'] }">
<c:set var="code" value="${r_message['exception'] }" scope="page" />
<%
	msg_code=(String)pageContext.getAttribute("code");
%>
<div class="alert alert-error" id="tt_mm_3" style="width:70%;margin:0 auto;">
<%=msg_code %>
</div>
</c:if>


<c:if test="${ not empty r_message['self_error'] }">
<c:set var="code" value="${r_message['self_error'] }" scope="page" />
<%
	msg_code=(String)pageContext.getAttribute("code");
%>
<div class="alert alert-error" id="tt_mm_4" style="width:70%;margin:0 auto;">
<%=msg_code %>
</div>
</c:if>


<c:if test="${ not empty r_message['self_success'] }">
<c:set var="code" value="${r_message['self_success'] }" scope="page" />
<%
	msg_code=(String)pageContext.getAttribute("code");
%>
<div class="alert alert-success" id="tt_mm_5" style="width:70%;margin:0 auto;">
<%=msg_code %>
</div>
</c:if>


<%if(request.getParameter("success")!=null){%>
<div class="alert alert-success" id="tt_mm_6" style="width:70%;margin:0 auto;">
	<spring:message code='<%=request.getParameter("success")%>' />
</div>
<%} %>

<%if(request.getParameter("error")!=null){%>
<div class="alert alert-error" id="tt_mm_7" style="width:70%;margin:0 auto;">
	<spring:message code='<%=request.getParameter("error") %>' />
</div>
<%} %>

<%if(request.getParameter("exception")!=null){%>
<div class="alert alert-error" id="tt_mm_8" style="width:70%;margin:0 auto;">
	<%=request.getParameter("exception") %>
</div>
<%} %>

</div>
