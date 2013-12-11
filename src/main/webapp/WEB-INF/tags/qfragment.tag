<%@ tag description="Fragment of a question" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" tagdir="/WEB-INF/tags" %>

<%@attribute name="node" type="com.modria.questionaire.model.Element"%>

<c:set var="ansvar" value="answersMap"/>
<c:choose>
	<%-- Text node --%>
	<c:when test="${node.type eq 1}">
		<div><c:out value="${node.displayText}"></c:out></div>
	</c:when>
	<%-- Text box --%>
	<c:when test="${node.type eq 2}">
		<div><c:out value="${node.displayText}"></c:out>
			<input type="text" name="${ansvar}[${node.id}] " value="${aMap[node.id]}"/>
		</div>
	</c:when>
	<%-- radio & check box--%>
	<c:when test="${node.type eq 3 or node.type eq 4}">
		<%-- if radio is in a group then answer is saved under the group's id --%>
		<c:choose>
		<c:when test="${not empty node.parent and node.parent.type eq 5}">
			<c:set var="radioid" value="${node.parent.id}"/>
		</c:when>
		<c:otherwise>
			<c:set var="radioid" value="${node.id}"/>
		</c:otherwise>
		</c:choose>
		
		<c:if test="${node.type eq 4 }">
			<c:set var="radiotype" value="checkbox"/>
		</c:if>
		<c:if test="${node.type eq 3 }">
			<c:set var="radiotype" value="radio"/>
		</c:if>
		
		<input type="${radiotype }" name="${ansvar}[${radioid}]" value="${node.value }"
		  <c:if test="${not empty aMap[radioid] and aMap[radioid] eq node.value}"> checked="checked"</c:if>
		/>${node.displayText}
	</c:when>
	<%-- group --%>
	<c:when test="${node.type eq 5 }">
		<%-- some group box here --%>
	</c:when>
</c:choose>
<%-- recurse child nodes --%>
<c:forEach items="${node.children}" var="child">
	<q:qfragment node="${child}"></q:qfragment>
</c:forEach>
<br/>