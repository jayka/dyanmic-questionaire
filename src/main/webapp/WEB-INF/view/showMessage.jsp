<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="q" tagdir="/WEB-INF/tags" %>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	</head> 
	<body>
		<script type="text/javascript">
			var radioGroup = {};
			function registerRadioGroup(groupid, radioid, value) {
				if(!radioGroup[groupid]) {
					radioGroup[groupid]={};
				}
// 				radioGroup[groupid][radioid]
			}
		</script>
		
		<c:if test="${not empty saved and saved eq true }">
			Form Saved!!!
		</c:if>
		<c:if test="${not empty saved and saved eq false }">
			Some failure!! check logs
		</c:if>
		<spring:form>
		<c:forEach var="node" items="${qlist}" varStatus="index">
			<q:qfragment node="${node}"/>
		</c:forEach>
		<br/><br/>
		<input type="submit" value="Save"/>
		</spring:form>
	</body>
</html>
